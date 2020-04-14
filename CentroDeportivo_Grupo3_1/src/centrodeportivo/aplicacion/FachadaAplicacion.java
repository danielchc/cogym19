package centrodeportivo.aplicacion;

import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.Material;
import centrodeportivo.aplicacion.obxectos.RexistroFisioloxico;
import centrodeportivo.aplicacion.obxectos.actividades.TipoActividade;
import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.aplicacion.obxectos.incidencias.Incidencia;
import centrodeportivo.aplicacion.obxectos.tarifas.Cuota;
import centrodeportivo.aplicacion.obxectos.tipos.ContasPersoa;
import centrodeportivo.aplicacion.obxectos.tipos.TipoIncidencia;
import centrodeportivo.aplicacion.obxectos.usuarios.PersoaFisica;
import centrodeportivo.aplicacion.obxectos.usuarios.Persoal;
import centrodeportivo.aplicacion.xestion.*;
import centrodeportivo.funcionsAux.Criptografia;
import centrodeportivo.aplicacion.obxectos.Mensaxe;
import centrodeportivo.aplicacion.obxectos.tarifas.Tarifa;
import centrodeportivo.aplicacion.obxectos.tipos.TipoUsuario;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.baseDatos.FachadaBD;
import centrodeportivo.gui.FachadaGUI;
import centrodeportivo.gui.controladores.vLoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public class FachadaAplicacion extends Application {
    private FachadaGUI fachadaGUI;
    private FachadaBD fachadaBD;
    private XestionUsuarios xestionUsuarios;
    private XestionMensaxes xestionMensaxes;
    private XestionTarifas xestionTarifas;
    private XestionIncidencias xestionIncidencias;
    private XestionInstalacions xestionInstalacions;
    private XestionActividades xestionActividades;

    public FachadaAplicacion() throws IOException, SQLException {
        this.fachadaGUI=new FachadaGUI(this);
        try {
            this.fachadaBD=new FachadaBD(this);
        } catch (ExcepcionBD excepcionBD) {
            System.out.println(excepcionBD.getMessage());
            System.exit(1);
        }
        this.xestionUsuarios=new XestionUsuarios(fachadaGUI,fachadaBD);
        this.xestionMensaxes=new XestionMensaxes(fachadaGUI,fachadaBD);
        this.xestionTarifas=new XestionTarifas(fachadaGUI,fachadaBD);
        this.xestionIncidencias=new XestionIncidencias(fachadaGUI, fachadaBD);
        this.xestionInstalacions=new XestionInstalacions(fachadaGUI, fachadaBD);
        this.xestionActividades=new XestionActividades(fachadaGUI, fachadaBD);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/centrodeportivo/gui/vistas/vLogin.fxml"));
        loader.setController(new vLoginController(new FachadaAplicacion()));
        Parent root = loader.load();

        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(true);
        primaryStage.setTitle("Centro Deportivo");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }




    /*
     *   Fachada GUI
     * */

    public void mostrarVentaSocios(Usuario loggedUser) throws IOException {
        fachadaGUI.mostrarVentaSocios(loggedUser);
    }

    public void mostrarVentaPersoal(Usuario loggedUser) throws IOException {
        fachadaGUI.mostrarVentaPersoal(loggedUser);
    }

    public void mostrarAdvertencia(String titulo,String texto) {
        fachadaGUI.mostrarAdvertencia(titulo, texto);
    }

    public void mostrarErro(String titulo,String texto) {
        fachadaGUI.mostrarErro(titulo, texto);
    }

    public void mostrarInformacion(String titulo,String texto){
        fachadaGUI.mostrarInformacion(titulo, texto);
    }

    public ButtonType mostrarConfirmacion(String titulo, String texto){
        return fachadaGUI.mostrarConfirmacion(titulo, texto);
    }

    /*
        Xestion usuarios
     */
    public boolean existeUsuario(String login)  {
        return xestionUsuarios.existeUsuario(login);
    }

    public boolean existeDNI(String dni)  {
        return xestionUsuarios.existeDNI(dni);
    }

    public boolean existeNUSS(String nuss) {
        return xestionUsuarios.existeNUSS(nuss);
    }

    public ContasPersoa contasPersoaFisica(String dni){
        return xestionUsuarios.contasPersoaFisica(dni);
    }

    public boolean validarUsuario(String login,String password) {
        return xestionUsuarios.validarUsuario(login, Criptografia.hashSHA256(password));
    }

    public void insertarUsuario(Usuario usuario) throws ExcepcionBD {
        usuario.setContrasinal(Criptografia.hashSHA256(usuario.getContrasinal()));
        xestionUsuarios.insertarUsuario(usuario);
    }

    public void actualizarUsuario(String loginVello,Usuario usuario, boolean contrasinalCambiado) throws ExcepcionBD {
        if(contrasinalCambiado) usuario.setContrasinal(Criptografia.hashSHA256(usuario.getContrasinal()));
        xestionUsuarios.actualizarUsuario(loginVello,usuario);
    }

    public void darBaixaUsuario(String login) throws ExcepcionBD {
        xestionUsuarios.darBaixaUsuario(login);
    }

    public void darAltaUsuario(String login) throws ExcepcionBD {
        xestionUsuarios.darAltaUsuario(login);
    }

    public TipoUsuario consultarTipo(String login)  {
        return xestionUsuarios.consultarTipo(login);
    }

    public Usuario consultarUsuario(String login) {
        return xestionUsuarios.consultarUsuario(login);
    }

    public ArrayList<Usuario> buscarUsuarios(String login,String nome,TipoUsuario filtroTipo,boolean usuariosDeBaixa){
        return xestionUsuarios.buscarUsuarios(login,nome,filtroTipo,usuariosDeBaixa);
    }

    public ArrayList<Usuario> buscarUsuarios(String login,String nome,TipoUsuario filtroTipo) {
        return xestionUsuarios.buscarUsuarios(login,nome,filtroTipo);
    }

    public ArrayList<Usuario> listarUsuarios(TipoUsuario filtro)  {
        return xestionUsuarios.buscarUsuarios("","",filtro);
    }

    public ArrayList<Usuario> listarUsuarios()  {
        return xestionUsuarios.buscarUsuarios("","",TipoUsuario.Todos);
    }

    public Cuota consultarCuota(String login){
        return xestionUsuarios.consultarCuota(login);
    }

    public ArrayList<RexistroFisioloxico> listarRexistros(String login){
        return xestionUsuarios.listarRexistros(login);
    }

    public void insertarRexistro(RexistroFisioloxico rexistroFisioloxico) throws ExcepcionBD {
        xestionUsuarios.insertarRexistro(rexistroFisioloxico);
    }

    public void eliminarRexistro(RexistroFisioloxico rexistroFisioloxico) throws ExcepcionBD {
        xestionUsuarios.eliminarRexistro(rexistroFisioloxico);
    }

    public PersoaFisica consultarPersoaFisica(String DNI){
        return xestionUsuarios.consultarPersoaFisica(DNI);
    }

    public ArrayList<TipoActividade> listarCapacidades(String login) {
        return xestionUsuarios.listarCapacidades(login);
    }

    public void engadirCapadidade(String login, TipoActividade tipoActividade) throws ExcepcionBD {
        xestionUsuarios.engadirCapadidade(login, tipoActividade);
    }

    public void eliminarCapacidade(String login, TipoActividade tipoActividade) throws ExcepcionBD {
        xestionUsuarios.eliminarCapacidade(login, tipoActividade);
    }

    public boolean tenClasesPendentes(Persoal persoal, TipoActividade tipoActividade){
        return xestionUsuarios.tenClasesPendentes(persoal, tipoActividade);
    }

    public boolean tenClasesPendentes(Persoal persoal){
        return xestionUsuarios.tenClasesPendentes(persoal);
    }

    /*
        Xestion tarifas
     */
    public void insertarTarifa(Tarifa t) throws ExcepcionBD {
        xestionTarifas.insertarTarifa(t);
    }

    public void borrarTarifa(Integer codTarifa) throws ExcepcionBD {
        xestionTarifas.borrarTarifa(codTarifa);
    }

    public void actualizarTarifa(Tarifa t) throws ExcepcionBD {
        xestionTarifas.actualizarTarifa(t);
    }

    public boolean estaEnUsoTarifa(Integer codTarifa) {
        return xestionTarifas.estaEnUsoTarifa(codTarifa);
    }

    public ArrayList<Tarifa> listarTarifas() {
        return xestionTarifas.listarTarifas();
    }

    public Tarifa consultarTarifaSocio(String loginSocio) {
        return xestionTarifas.consultarTarifaSocio(loginSocio);
    }

    public boolean existeTarifa(String nome) {
        return xestionTarifas.existeTarifa(nome);
    }


    /*
        Xestion mensaxes
     */
    public void enviarMensaxe(Mensaxe m) throws ExcepcionBD {
        xestionMensaxes.enviarMensaxe(m);
    }

    public void enviarMensaxe(Usuario emisor, ArrayList<Usuario> receptores,String mensaxe) throws ExcepcionBD {
        xestionMensaxes.enviarMensaxe(emisor, receptores, mensaxe);
    }

    public void marcarMensaxeComoLido(Mensaxe m) throws ExcepcionBD {
        xestionMensaxes.marcarMensaxeComoLido(m);
    }

    public ArrayList<Mensaxe> listarMensaxesRecibidos(String loginReceptor) {
        return xestionMensaxes.listarMensaxesRecibidos(loginReceptor);
    }




    /*
        Xestion incidencias
     */
    public ArrayList<Incidencia> listarIncidencias(){
        return xestionIncidencias.listarIncidencias();
    }
    public ArrayList<Incidencia> listarIncidencias(String descripcion, TipoIncidencia tipoIncidencia) {
        return xestionIncidencias.listarIncidencias(descripcion, tipoIncidencia);
    }

    public void insertarIncidencia(Incidencia incidencia) throws ExcepcionBD {
        xestionIncidencias.insertarIncidencia(incidencia);
    }
    /*
        Xestion instalacións
     */

    public ArrayList<Area> listarAreas(){
        return xestionInstalacions.listarAreas();
    }

    public ArrayList<Material> listarMateriais() {
        return fachadaBD.listarMateriais();
    }

    /*
        Xestión Actividades
     */
    public ArrayList<TipoActividade> listarTipoActividades(){
        return xestionActividades.listarTipoActividades();
    }


}
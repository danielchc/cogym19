package centrodeportivo.aplicacion;

import centrodeportivo.aplicacion.obxectos.Material;
import centrodeportivo.aplicacion.obxectos.RexistroFisioloxico;
import centrodeportivo.aplicacion.obxectos.actividades.TipoActividade;
import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.aplicacion.obxectos.incidencias.Incidencia;
import centrodeportivo.aplicacion.obxectos.tarifas.Cuota;
import centrodeportivo.aplicacion.obxectos.tipos.ContasPersoa;
import centrodeportivo.aplicacion.obxectos.tipos.TipoIncidencia;
import centrodeportivo.aplicacion.obxectos.usuarios.PersoaFisica;
import centrodeportivo.aplicacion.xestion.*;
import centrodeportivo.funcionsAux.Criptografia;
import centrodeportivo.aplicacion.obxectos.Mensaxe;
import centrodeportivo.aplicacion.obxectos.tarifas.Tarifa;
import centrodeportivo.aplicacion.obxectos.tipos.TipoUsuario;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.baseDatos.FachadaBD;
import centrodeportivo.gui.FachadaGUI;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class FachadaAplicacion {
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
        this.fachadaBD=new FachadaBD(this);
        this.xestionUsuarios=new XestionUsuarios(fachadaGUI,fachadaBD);
        this.xestionMensaxes=new XestionMensaxes(fachadaGUI,fachadaBD);
        this.xestionTarifas=new XestionTarifas(fachadaGUI,fachadaBD);
        this.xestionIncidencias=new XestionIncidencias(fachadaGUI, fachadaBD);
        this.xestionInstalacions=new XestionInstalacions(fachadaGUI, fachadaBD);
        this.xestionActividades=new XestionActividades(fachadaGUI, fachadaBD);
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

    public void insertarUsuario(Usuario usuario) {
        usuario.setContrasinal(Criptografia.hashSHA256(usuario.getContrasinal()));
        xestionUsuarios.insertarUsuario(usuario);
    }

    public void actualizarUsuario(String loginVello,Usuario usuario)  {
        xestionUsuarios.actualizarUsuario(loginVello,usuario);
    }

    public void darBaixaUsuario(String login)  {
        xestionUsuarios.darBaixaUsuario(login);
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

    public void insertarRexistro(RexistroFisioloxico rexistroFisioloxico){
        xestionUsuarios.insertarRexistro(rexistroFisioloxico);
    }

    public void eliminarRexistro(RexistroFisioloxico rexistroFisioloxico){
        xestionUsuarios.eliminarRexistro(rexistroFisioloxico);
    }

    public PersoaFisica consultarPersoaFisica(String DNI){
        return xestionUsuarios.consultarPersoaFisica(DNI);
    }

    public ArrayList<TipoActividade> listarCapacidades(String login) {
        return xestionUsuarios.listarCapacidades(login);
    }

    public void engadirCapadidade(String login, TipoActividade tipoActividade){
        xestionUsuarios.engadirCapadidade(login, tipoActividade);
    }

    public void eliminarCapacidade(String login, TipoActividade tipoActividade){
        xestionUsuarios.eliminarCapacidade(login, tipoActividade);
    }

    /*
        Xestion tarifas
     */
    public void insertarTarifa(Tarifa t) {
        xestionTarifas.insertarTarifa(t);
    }

    public void borrarTarifa(Integer codTarifa) {
        xestionTarifas.borrarTarifa(codTarifa);
    }

    public void actualizarTarifa(Tarifa t) {
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
    public void enviarMensaxe(Mensaxe m)  {
        xestionMensaxes.enviarMensaxe(m);
    }

    public void enviarMensaxe(Usuario emisor, ArrayList<Usuario> receptores,String mensaxe) {
        xestionMensaxes.enviarMensaxe(emisor, receptores, mensaxe);
    }

    public void marcarMensaxeComoLido(Mensaxe m) {
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
    /*
        Xestion instalacións
     */

    public HashMap<Area, ArrayList<Material>> listarAreas(){
        return xestionInstalacions.listarAreas();
    }

    public ArrayList<TipoActividade> listarTipoActividades(){
        return xestionActividades.listarTipoActividades();
    }


}
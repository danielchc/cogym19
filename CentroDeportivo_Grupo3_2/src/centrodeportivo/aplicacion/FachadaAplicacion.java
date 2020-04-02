package centrodeportivo.aplicacion;

import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.aplicacion.xestion.XestionInstalacions;
import centrodeportivo.funcionsAux.Criptografia;
import centrodeportivo.aplicacion.obxectos.Mensaxe;
import centrodeportivo.aplicacion.obxectos.tarifas.Tarifa;
import centrodeportivo.aplicacion.obxectos.tipos.TipoUsuario;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.aplicacion.xestion.XestionMensaxes;
import centrodeportivo.aplicacion.xestion.XestionTarifas;
import centrodeportivo.aplicacion.xestion.XestionUsuarios;
import centrodeportivo.baseDatos.FachadaBD;
import centrodeportivo.gui.FachadaGUI;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class FachadaAplicacion {
    private FachadaGUI fachadaGUI;
    private FachadaBD fachadaBD;
    private XestionUsuarios xestionUsuarios;
    private XestionMensaxes xestionMensaxes;
    private XestionTarifas xestionTarifas;
    private XestionInstalacions xestionInstalacions;

    public FachadaAplicacion() throws IOException, SQLException {
        this.fachadaGUI=new FachadaGUI(this);
        this.fachadaBD=new FachadaBD(this);
        this.xestionUsuarios=new XestionUsuarios(fachadaGUI,fachadaBD);
        this.xestionMensaxes=new XestionMensaxes(fachadaGUI,fachadaBD);
        this.xestionTarifas=new XestionTarifas(fachadaGUI,fachadaBD);
        this.xestionInstalacions = new XestionInstalacions(fachadaGUI, fachadaBD);
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

    public ArrayList<Usuario> buscarUsuarios(String login,String nome,TipoUsuario filtroTipo) {
        return xestionUsuarios.buscarUsuarios(login,nome,filtroTipo);
    }

    public ArrayList<Usuario> listarUsuarios(TipoUsuario filtro)  {
        return xestionUsuarios.buscarUsuarios("","",filtro);
    }

    public ArrayList<Usuario> listarUsuarios()  {
        return xestionUsuarios.buscarUsuarios("","",TipoUsuario.Todos);
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

    public void mostrarVentaSocios(Usuario usuario) throws IOException {
        fachadaGUI.mostrarVentaSocios(usuario);
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
        Xestion incidencias
     */

    /*
        Xestion instalacións
     */
    public void darAltaInstalacion(Instalacion instalacion){
        xestionInstalacions.darAltaInstalacion(instalacion);
    }

    public void borrarInstalacion(Instalacion instalacion){
        xestionInstalacions.borrarInstalacion(instalacion);
    }

    public void modificarInstalacion(Instalacion instalacion){
        xestionInstalacions.modificarInstalacion(instalacion);
    }

    public ArrayList<Instalacion> buscarInstalacions(Instalacion instalacion){
        return xestionInstalacions.buscarInstalacions(instalacion);
    }


}

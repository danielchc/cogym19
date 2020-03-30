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
    public boolean existeUsuario(String login) throws SQLException {
        return xestionUsuarios.existeUsuario(login);
    }

    public boolean existeDNI(String dni) throws SQLException {
        return xestionUsuarios.existeDNI(dni);
    }

    public boolean validarUsuario(String login,String password) throws SQLException{
        return xestionUsuarios.validarUsuario(login, Criptografia.hashSHA256(password));
    }

    public void insertarUsuario(Usuario usuario) throws SQLException{
        xestionUsuarios.insertarUsuario(usuario);
    }

    public void actualizarUsuario(String loginVello,Usuario usuario) throws SQLException {
        xestionUsuarios.actualizarUsuario(loginVello,usuario);
    }

    public void darBaixaUsuario(String login) throws SQLException {
        xestionUsuarios.darBaixaUsuario(login);
    }

    public TipoUsuario consultarTipo(String login) throws SQLException {
        return xestionUsuarios.consultarTipo(login);
    }

    public ArrayList<Usuario> buscarUsuarios(String login,String nome,TipoUsuario filtro) throws SQLException {
        return xestionUsuarios.buscarUsuarios(login,nome,filtro);
    }

    public ArrayList<Usuario> listarUsuarios(TipoUsuario filtro) throws SQLException {
        return xestionUsuarios.buscarUsuarios("","",filtro);
    }

    public ArrayList<Usuario> listarUsuarios() throws SQLException {
        return xestionUsuarios.buscarUsuarios("","",TipoUsuario.Todos);
    }

    /*
        Xestion tarifas
     */
    public void insertarTarifa(Tarifa t) throws SQLException{
        xestionTarifas.insertarTarifa(t);
    }

    public void borrarTarifa(Integer codTarifa) throws SQLException{
        xestionTarifas.borrarTarifa(codTarifa);
    }

    public void actualizarTarifa(Tarifa t) throws SQLException{
        xestionTarifas.actualizarTarifa(t);
    }

    public boolean estaEnUsoTarifa(Integer codTarifa) throws SQLException{
        return xestionTarifas.estaEnUsoTarifa(codTarifa);
    }

    public ArrayList<Tarifa> listarTarifas() throws SQLException{
        return xestionTarifas.listarTarifas();
    }

    public Tarifa consultarTarifaSocio(String loginSocio) throws SQLException{
        return xestionTarifas.consultarTarifaSocio(loginSocio);
    }

    /*
        Xestion mensaxes
     */
    public void enviarMensaxe(Mensaxe m) throws SQLException {
        xestionMensaxes.enviarMensaxe(m);
    }

    public void enviarMensaxe(Usuario emisor, ArrayList<Usuario> receptores,String mensaxe) throws SQLException{
        xestionMensaxes.enviarMensaxe(emisor, receptores, mensaxe);
    }

    public void marcarMensaxeComoLido(Mensaxe m) throws SQLException{
        xestionMensaxes.marcarMensaxeComoLido(m);
    }

    public ArrayList<Mensaxe> listarMensaxesRecibidos(String loginReceptor) throws SQLException{
        return xestionMensaxes.listarMensaxesRecibidos(loginReceptor);
    }

    public void mostrarVentaSocios() throws IOException {
        fachadaGUI.mostrarVentaSocios();
    }

    public void mostrarVentaPersoal() throws IOException {
        fachadaGUI.mostrarVentaPersoal();
    }

    public Usuario consultarUsuario(String login) throws SQLException {
        return fachadaBD.consultarUsuario(login);
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

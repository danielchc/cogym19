package centrodeportivo.aplicacion;

import centrodeportivo.aplicacion.funcionsAux.Criptografia;
import centrodeportivo.aplicacion.obxectos.Mensaxe;
import centrodeportivo.aplicacion.obxectos.tarifas.Tarifa;
import centrodeportivo.aplicacion.obxectos.tipos.TipoUsuario;
import centrodeportivo.aplicacion.obxectos.usuarios.Persoal;
import centrodeportivo.aplicacion.obxectos.usuarios.Profesor;
import centrodeportivo.aplicacion.obxectos.usuarios.Socio;
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

    public FachadaAplicacion() throws IOException, SQLException {
        this.fachadaGUI=new FachadaGUI(this);
        this.fachadaBD=new FachadaBD(this);
        this.xestionUsuarios=new XestionUsuarios(fachadaGUI,fachadaBD);
        this.xestionMensaxes=new XestionMensaxes(fachadaGUI,fachadaBD);
        this.xestionTarifas=new XestionTarifas(fachadaGUI,fachadaBD);
    }



    /*
        Xestion usuarios
     */
    public boolean existeUsuario(String login) throws SQLException {
        return xestionUsuarios.existeUsuario(login);
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

    public ArrayList<Socio> listarSocios() throws SQLException {
        return xestionUsuarios.listarSocios();
    }

    public ArrayList<Persoal> listarPersoal() throws SQLException {
        return xestionUsuarios.listarPersoal();
    }

    public ArrayList<Profesor> listarProfesores() throws SQLException {
        return xestionUsuarios.listarProfesores();
    }

    public ArrayList<Usuario> listarUsuarios() throws SQLException {
        return xestionUsuarios.listarUsuarios();
    }

    public ArrayList<Socio> buscarSocios(String login,String nome) throws SQLException {
        return xestionUsuarios.buscarSocios(login,nome);
    }

    public ArrayList<Persoal> buscarPersoal(String login,String nome) throws SQLException {
        return xestionUsuarios.buscarPersoal(login, nome);
    }

    public ArrayList<Profesor> buscarProfesores(String login,String nome) throws SQLException {
        return xestionUsuarios.buscarProfesores(login, nome);
    }

    public ArrayList<Usuario> buscarUsuarios(String login,String nome) throws SQLException{
        return xestionUsuarios.buscarUsuarios(login, nome);
    }

    public TipoUsuario consultarTipo(String login) throws SQLException {
        return xestionUsuarios.consultarTipo(login);
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

    /*
        Xestion incidencias
     */
}

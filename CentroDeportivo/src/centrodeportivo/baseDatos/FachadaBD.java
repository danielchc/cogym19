package centrodeportivo.baseDatos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.Mensaxe;
import centrodeportivo.aplicacion.obxectos.Tarifa;
import centrodeportivo.aplicacion.obxectos.usuarios.Persoal;
import centrodeportivo.aplicacion.obxectos.usuarios.Profesor;
import centrodeportivo.aplicacion.obxectos.usuarios.Socio;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.baseDatos.dao.DAOIncidencias;
import centrodeportivo.baseDatos.dao.DAOMensaxes;
import centrodeportivo.baseDatos.dao.DAOTarifas;
import centrodeportivo.baseDatos.dao.DAOUsuarios;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public final class FachadaBD {
    private FachadaAplicacion fachadaAplicacion;
    private Connection conexion;
    private DAOUsuarios daoUsuarios;
    private DAOTarifas daoTarifas;
    private DAOMensaxes daoMensaxes;
    private DAOIncidencias daoIncidencias;

    public FachadaBD(FachadaAplicacion fachadaAplicacion) throws IOException, SQLException {
        this.fachadaAplicacion=fachadaAplicacion;
        Properties configuracion = new Properties();
        FileInputStream prop;
        prop = new FileInputStream("baseDatos.properties");
        configuracion.load(prop);
        prop.close();
        Properties usuario = new Properties();
        usuario.setProperty("user", configuracion.getProperty("usuario"));
        usuario.setProperty("password", configuracion.getProperty("clave"));
        String con=String.format("jdbc:%s://%s:%s/%s", configuracion.getProperty("gestor"),configuracion.getProperty("servidor"),configuracion.getProperty("puerto"),configuracion.getProperty("baseDatos"));
        this.conexion= DriverManager.getConnection(con,usuario);
        this.conexion.setAutoCommit(false);
        this.daoUsuarios=new DAOUsuarios(this.conexion,this.fachadaAplicacion);
        this.daoTarifas=new DAOTarifas(this.conexion,this.fachadaAplicacion);
        this.daoMensaxes=new DAOMensaxes(this.conexion,this.fachadaAplicacion);
        this.daoIncidencias=new DAOIncidencias(this.conexion,this.fachadaAplicacion);
    }

    /*
        Funcions DAOUsuarios
     */
    public boolean existeUsuario(String login) throws SQLException {
        return daoUsuarios.existeUsuario(login);
    }

    public boolean validarUsuario(String login,String password) throws SQLException{
        return daoUsuarios.validarUsuario(login,password);
    }

    public void insertarUsuario(Usuario usuario) throws SQLException{
        daoUsuarios.insertarUsuario(usuario);
    }

    public void actualizarUsuario(String loginVello,Usuario usuario) throws SQLException {
        daoUsuarios.actualizarUsuario(loginVello,usuario);
    }

    public void darBaixaUsuario(String login) throws SQLException {
        daoUsuarios.darBaixaUsuario(login);
    }

    public ArrayList<Socio> listarSocios() throws SQLException {
        return daoUsuarios.listarSocios();
    }

    public ArrayList<Persoal> listarPersoal() throws SQLException {
        return daoUsuarios.listarPersoal();
    }

    public ArrayList<Profesor> listarProfesores() throws SQLException {
        return daoUsuarios.listarProfesores();
    }

    public ArrayList<Usuario> listarUsuarios() throws SQLException {
        return daoUsuarios.listarUsuarios();
    }

    public ArrayList<Socio> buscarSocios(String login,String nome) throws SQLException {
        return daoUsuarios.buscarSocios(login,nome);
    }

    public ArrayList<Persoal> buscarPersoal(String login,String nome) throws SQLException {
        return daoUsuarios.buscarPersoal(login, nome);
    }

    public ArrayList<Profesor> buscarProfesores(String login,String nome) throws SQLException {
        return daoUsuarios.buscarProfesores(login, nome);
    }

    public ArrayList<Usuario> buscarUsuarios(String login,String nome) throws SQLException{
        return daoUsuarios.buscarUsuarios(login, nome);
    }

    /*
        Funcions DAOTarifas
     */
    public void insertarTarifa(Tarifa t) throws SQLException{
        daoTarifas.insertarTarifa(t);
    }

    public void borrarTarifa(Integer codTarifa) throws SQLException{
        daoTarifas.borrarTarifa(codTarifa);
    }

    public void actualizarTarifa(Tarifa t) throws SQLException{
        daoTarifas.actualizarTarifa(t);
    }

    public boolean estaEnUsoTarifa(Integer codTarifa) throws SQLException{
        return daoTarifas.estaEnUsoTarifa(codTarifa);
    }

    public ArrayList<Tarifa> listarTarifas() throws SQLException{
        return daoTarifas.listarTarifas();
    }

    public Tarifa consultarTarifaSocio(String loginSocio) throws SQLException{
        return daoTarifas.consultarTarifaSocio(loginSocio);
    }

    /*
        Funcions DAOMensaxes
     */
    public void enviarMensaxe(Mensaxe m) throws SQLException {
        daoMensaxes.enviarMensaxe(m);
    }

    public void enviarMensaxe(Usuario emisor, ArrayList<Usuario> receptores,String mensaxe) throws SQLException{
        daoMensaxes.enviarMensaxe(emisor, receptores, mensaxe);
    }

    public void marcarMensaxeComoLido(Mensaxe m) throws SQLException{
        daoMensaxes.marcarMensaxeComoLido(m);
    }

    public ArrayList<Mensaxe> listarMensaxesRecibidos(String loginReceptor) throws SQLException{
        return daoMensaxes.listarMensaxesRecibidos(loginReceptor);
    }

    /*
        Funcions DAOIncidencias
     */







    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public DAOUsuarios getDaoUsuarios() {
        return daoUsuarios;
    }

    public void setDaoUsuarios(DAOUsuarios daoUsuarios) {
        this.daoUsuarios = daoUsuarios;
    }

    public DAOTarifas getDaoTarifas() {
        return daoTarifas;
    }

    public void setDaoTarifas(DAOTarifas daoTarifas) {
        this.daoTarifas = daoTarifas;
    }

    public DAOMensaxes getDaoMensaxes() {
        return daoMensaxes;
    }

    public void setDaoMensaxes(DAOMensaxes daoMensaxes) {
        this.daoMensaxes = daoMensaxes;
    }

    public DAOIncidencias getDaoIncidencias() {
        return daoIncidencias;
    }

    public void setDaoIncidencias(DAOIncidencias daoIncidencias) {
        this.daoIncidencias = daoIncidencias;
    }
}

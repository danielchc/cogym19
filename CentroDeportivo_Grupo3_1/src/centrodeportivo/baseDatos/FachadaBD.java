package centrodeportivo.baseDatos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.RexistroMarca;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.aplicacion.obxectos.tarifas.Cuota;
import centrodeportivo.funcionsAux.Criptografia;
import centrodeportivo.aplicacion.obxectos.Incidencia;
import centrodeportivo.aplicacion.obxectos.Mensaxe;
import centrodeportivo.aplicacion.obxectos.tarifas.Tarifa;
import centrodeportivo.aplicacion.obxectos.tipos.TipoUsuario;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.gui.FachadaGUI;

import java.io.FileInputStream;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public final class FachadaBD {
    private FachadaAplicacion fachadaAplicacion;
    private FachadaGUI fachadaGUI;
    private Connection conexion;
    private DAOUsuarios daoUsuarios;
    private DAOTarifas daoTarifas;
    private DAOMensaxes daoMensaxes;
    private DAOIncidencias daoIncidencias;
    private DAOInstalacions daoInstalacions;

    public FachadaBD(FachadaAplicacion fachadaAplicacion)  {
        this.fachadaAplicacion=fachadaAplicacion;
        Properties configuracion = new Properties();
        FileInputStream prop;

        //prop = new FileInputStream("baseDatos.properties");
        //configuracion.load(prop);
        //prop.close();
        try{
            String conf=new String(Criptografia.desencriptar(Files.readAllBytes(Paths.get("baseDatos.encrypted"))));
            //System.out.println(conf);
            configuracion.load(new StringReader(conf));
        }catch (Exception ex){
            System.out.println("Non se pudo cargar o arquivo cifrado");
            System.exit(1);
        }

        Properties usuario = new Properties();
        usuario.setProperty("user", configuracion.getProperty("usuario"));
        usuario.setProperty("password", configuracion.getProperty("clave"));
        String con=String.format("jdbc:%s://%s:%s/%s", configuracion.getProperty("gestor"),configuracion.getProperty("servidor"),configuracion.getProperty("puerto"),configuracion.getProperty("baseDatos"));
        try{
            this.conexion= DriverManager.getConnection(con,usuario);
            this.conexion.setAutoCommit(false);
        }catch (SQLException e){
            fachadaAplicacion.mostrarErro("Erro","Erro na conexión ca base de datos");
            System.exit(1);
        }
        this.daoUsuarios=new DAOUsuarios(this.conexion,this.fachadaAplicacion);
        this.daoTarifas=new DAOTarifas(this.conexion,this.fachadaAplicacion);
        this.daoMensaxes=new DAOMensaxes(this.conexion,this.fachadaAplicacion);
        this.daoIncidencias=new DAOIncidencias(this.conexion,this.fachadaAplicacion);
        this.daoInstalacions=new DAOInstalacions(this.conexion, this.fachadaAplicacion);
    }

    /*
        Funcions DAOUsuarios
     */
    public boolean existeUsuario(String login) {
        return daoUsuarios.existeUsuario(login);
    }

    public boolean existeDNI(String dni) {
        return daoUsuarios.existeDNI(dni);
    }

    public boolean existeNUSS(String nuss) {
        return daoUsuarios.existeNUSS(nuss);
    }

    public boolean validarUsuario(String login,String password) {
        return daoUsuarios.validarUsuario(login,password);
    }

    public void insertarUsuario(Usuario usuario) {
        daoUsuarios.insertarUsuario(usuario);
    }

    public void actualizarUsuario(String loginVello,Usuario usuario) {
        daoUsuarios.actualizarUsuario(loginVello,usuario);
    }

    public void darBaixaUsuario(String login) {
        daoUsuarios.darBaixaUsuario(login);
    }

    public TipoUsuario consultarTipo(String login) {
        return daoUsuarios.consultarTipo(login);
    }

    public Usuario consultarUsuario(String login) {
        return daoUsuarios.consultarUsuario(login);
    }

    public ArrayList<Usuario> buscarUsuarios(String login,String nome,TipoUsuario filtroTipo) {
        return daoUsuarios.buscarUsuarios(login,nome,filtroTipo);
    }

    public Cuota consultarCuota(String login){
        return daoUsuarios.consultarCuota(login);
    }

    public ArrayList<RexistroMarca> listarRexistros(String login){
        return daoUsuarios.listarRexistros(login);
    }

    /*
        Funcions DAOTarifas
     */
    public void insertarTarifa(Tarifa t) {
        daoTarifas.insertarTarifa(t);
    }

    public void borrarTarifa(Integer codTarifa) {
        daoTarifas.borrarTarifa(codTarifa);
    }

    public void actualizarTarifa(Tarifa t) {
        daoTarifas.actualizarTarifa(t);
    }

    public boolean estaEnUsoTarifa(Integer codTarifa) {
        return daoTarifas.estaEnUsoTarifa(codTarifa);
    }

    public ArrayList<Tarifa> listarTarifas() {
        return daoTarifas.listarTarifas();
    }

    public Tarifa consultarTarifaSocio(String loginSocio) {
        return daoTarifas.consultarTarifaSocio(loginSocio);
    }

    public boolean existeTarifa(String nome){
        return daoTarifas.existeTarifa(nome);
    }

    /*
        Funcions DAOMensaxes
     */
    public void enviarMensaxe(Mensaxe m)  {
        daoMensaxes.enviarMensaxe(m);
    }

    public void enviarMensaxe(Usuario emisor, ArrayList<Usuario> receptores,String mensaxe) {
        daoMensaxes.enviarMensaxe(emisor, receptores, mensaxe);
    }

    public void marcarMensaxeComoLido(Mensaxe m) {
        daoMensaxes.marcarMensaxeComoLido(m);
    }

    public ArrayList<Mensaxe> listarMensaxesRecibidos(String loginReceptor) {
        return daoMensaxes.listarMensaxesRecibidos(loginReceptor);
    }

    /*
        Funcions DAOIncidencias
    */

    public void insertarIncidencia(Incidencia incidencia)  {
        daoIncidencias.insertarIncidencia(incidencia);
    }

    public ArrayList<Incidencia> listarIncidencia()   {
        return daoIncidencias.listarIncidencia();
    }

    public void resolverIncidencia(Incidencia incidencia) {
        daoIncidencias.resolverIncidencia(incidencia);
    }

    /*
        Funcións DAOInstalacions
     */




    //Funcións propias:

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

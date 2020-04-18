package centrodeportivo.baseDatos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.actividades.Actividade;
import centrodeportivo.aplicacion.obxectos.actividades.Curso;
import centrodeportivo.aplicacion.obxectos.actividades.TipoActividade;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.funcionsAux.Criptografia;
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
    private DAOInstalacions daoInstalacions;
    private DAOActividades daoActividades;
    private DAOCursos daoCursos;

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
        this.daoInstalacions=new DAOInstalacions(this.conexion, this.fachadaAplicacion);
        this.daoActividades=new DAOActividades(this.conexion, this.fachadaAplicacion);
    }

    /*
        Funcions DAOUsuarios
     */
    public boolean validarUsuario(String login, String contrasinal) {
        return daoUsuarios.validarUsuario(login, contrasinal);
    }

    public Usuario consultarUsuario(String login) {
        return daoUsuarios.consultarUsuario(login);
    }

    /*
        Funcións DAOInstalacions
     */

    public void darAltaInstalacion(Instalacion instalacion) throws ExcepcionBD {
        daoInstalacions.darAltaInstalacion(instalacion);
    }

    public void borrarInstalacion(Instalacion instalacion) throws ExcepcionBD {
        daoInstalacions.borrarInstalacion(instalacion);
    }

    public void modificarInstalacion(Instalacion instalacion) throws ExcepcionBD {
        daoInstalacions.modificarInstalacion(instalacion);
    }

    public ArrayList<Instalacion> buscarInstalacions(Instalacion instalacion){
        return daoInstalacions.buscarInstalacions(instalacion);
    }

    public ArrayList<Instalacion> listarInstalacions(){
        return daoInstalacions.listarInstalacións();
    }

    public boolean comprobarExistencia(Instalacion instalacion){
        return daoInstalacions.comprobarExistencia(instalacion);
    }

    public boolean tenAreas(Instalacion instalacion){
        return daoInstalacions.tenAreas(instalacion);
    }

    /*
        Funcións DAOActividades
     */

    public void crearTipoActividade(TipoActividade tipoActividade) throws ExcepcionBD {
        this.daoActividades.crearTipoActividade(tipoActividade);
    }

    public void modificarTipoActividade(TipoActividade tipoActividade) throws ExcepcionBD {
        this.daoActividades.modificarTipoActividade(tipoActividade);
    }

    public void eliminarTipoActividade(TipoActividade tipoActividade) throws ExcepcionBD {
        this.daoActividades.eliminarTipoActividade(tipoActividade);
    }

    public ArrayList<TipoActividade> listarTiposActividades(){
        return this.daoActividades.listarTiposActividades();
    }

    public ArrayList<TipoActividade> buscarTiposActividades(TipoActividade tipoActividade){
        return this.daoActividades.buscarTiposActividades(tipoActividade);
    }

    public boolean comprobarExistencia(TipoActividade tipoActividade){
        return this.daoActividades.comprobarExistencia(tipoActividade);
    }

    public boolean tenActividades(TipoActividade tipoActividade){
        return this.daoActividades.tenActividades(tipoActividade);
    }

    /*
        Funcións DAOCursos
     */

    public void rexistrarCurso(Curso curso) throws ExcepcionBD {
        daoCursos.rexistrarCurso(curso);
    }

    public void modificarCurso(Curso curso) throws ExcepcionBD {
        daoCursos.modificarCurso(curso);
    }

    public void engadirActividade(Curso curso, Actividade actividade) throws ExcepcionBD {
        daoCursos.engadirActividade(curso, actividade);
    }

    public void cancelarCurso(Curso curso) throws ExcepcionBD {
        daoCursos.cancelarCurso(curso);
    }

    public ArrayList<Curso> consultarCursos(Curso curso) {
        return daoCursos.consultarCursos(curso);
    }

    public boolean comprobarExistencia(Curso curso){
        return daoCursos.comprobarExistencia(curso);
    }

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
}

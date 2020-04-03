package centrodeportivo.baseDatos;

import centrodeportivo.aplicacion.FachadaAplicacion;
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
    }

    /*
        Funcions DAOUsuarios
     */
    public boolean validarUsuario(String login,String password) {
        return daoUsuarios.validarUsuario(login,password);
    }

    public TipoUsuario consultarTipo(String login) {
        return daoUsuarios.consultarTipo(login);
    }

    public Usuario consultarUsuario(String login) {
        return daoUsuarios.consultarUsuario(login);
    }

    /*
        Funcións DAOInstalacions
     */

    public void darAltaInstalacion(Instalacion instalacion){
        daoInstalacions.darAltaInstalacion(instalacion);
    }

    public void borrarInstalacion(Instalacion instalacion){
        daoInstalacions.borrarInstalacion(instalacion);
    }

    public void modificarInstalacion(Instalacion instalacion){
        daoInstalacions.modificarInstalacion(instalacion);
    }

    public ArrayList<Instalacion> buscarInstalacions(Instalacion instalacion){
        return daoInstalacions.buscarInstalacions(instalacion);
    }

    public ArrayList<Instalacion> listarInstalacions(){
        return daoInstalacions.listarInstalacións();
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

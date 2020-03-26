package centrodeportivo.baseDatos;

import centrodeportivo.aplicacion.FachadaAplicacion;
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
        this.daoIncidencias=new DAOIncidencias(this.conexion);
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
        return buscarProfesores("","");
    }

    public ArrayList<Usuario> listarUsuarios() throws SQLException {
        return buscarUsuarios("","");
    }

    public ArrayList<Socio> buscarSocios(String login,String nome) throws SQLException {
        PreparedStatement stmUsuario = null;
        ArrayList<Socio> socios=new ArrayList<Socio>();
        ResultSet rsUsuarios;
        Connection con=super.getConexion();
        stmUsuario = con.prepareStatement("SELECT * FROM usuarios NATURAL JOIN socios WHERE login LIKE ? OR nome LIKE ?;");
        stmUsuario.setString(1, "%"+login+"%");
        stmUsuario.setString(2, "%"+nome+"%");
        rsUsuarios = stmUsuario.executeQuery();
        while (rsUsuarios.next()) {
            socios.add(new Socio(
                    rsUsuarios.getString("login"),
                    rsUsuarios.getString("contrasinal"),
                    rsUsuarios.getString("nome"),
                    rsUsuarios.getString("numTelefono"),
                    rsUsuarios.getString("DNI"),
                    rsUsuarios.getString("correoElectronico"),
                    rsUsuarios.getString("iban"),
                    rsUsuarios.getDate("dataAlta"),
                    rsUsuarios.getDate("dataNacemento"),
                    rsUsuarios.getString("dificultades")
            ));
        }
        return socios;
    }

    public ArrayList<Persoal> buscarPersoal(String login,String nome) throws SQLException {
        PreparedStatement stmUsuario = null;
        ArrayList<Persoal> persoal=new ArrayList<Persoal>();
        ResultSet rsUsuarios;
        Connection con=super.getConexion();
        stmUsuario = con.prepareStatement("SELECT * FROM usuarios NATURAL JOIN persoal WHERE login NOT IN (SELECT login FROM profesores) AND (login LIKE ? OR nome LIKE ?);");
        stmUsuario.setString(1, "%"+login+"%");
        stmUsuario.setString(2, "%"+nome+"%");
        rsUsuarios = stmUsuario.executeQuery();
        while (rsUsuarios.next()) {
            persoal.add(new Persoal(
                    rsUsuarios.getString("login"),
                    rsUsuarios.getString("contrasinal"),
                    rsUsuarios.getString("nome"),
                    rsUsuarios.getString("numTelefono"),
                    rsUsuarios.getString("DNI"),
                    rsUsuarios.getString("correoElectronico"),
                    rsUsuarios.getString("iban"),
                    rsUsuarios.getString("NUSS")
            ));
        }
        return persoal;
    }

    public ArrayList<Profesor> buscarProfesores(String login,String nome) throws SQLException {
        PreparedStatement stmUsuario = null;
        ArrayList<Profesor> profesores=new ArrayList<>();
        ResultSet rsUsuarios;
        Connection con=super.getConexion();
        stmUsuario = con.prepareStatement("SELECT * FROM usuarios NATURAL JOIN persoal WHERE login IN (SELECT login FROM profesores) AND (login LIKE ? OR nome LIKE ?);");
        stmUsuario.setString(1, "%"+login+"%");
        stmUsuario.setString(2, "%"+nome+"%");
        rsUsuarios = stmUsuario.executeQuery();
        while (rsUsuarios.next()) {
            profesores.add(new Profesor(
                    rsUsuarios.getString("login"),
                    rsUsuarios.getString("contrasinal"),
                    rsUsuarios.getString("nome"),
                    rsUsuarios.getString("numTelefono"),
                    rsUsuarios.getString("DNI"),
                    rsUsuarios.getString("correoElectronico"),
                    rsUsuarios.getString("iban"),
                    rsUsuarios.getString("NUSS")
            ));
        }
        return profesores;
    }

    public ArrayList<Usuario> buscarUsuarios(String login,String nome) throws SQLException{
        PreparedStatement stmUsuario = null;
        ArrayList<Usuario> usuarios=new ArrayList<>();
        ResultSet rsUsuarios;
        Connection con=super.getConexion();
        stmUsuario = con.prepareStatement("SELECT * FROM usuarios WHERE login LIKE ? OR nome LIKE ?;");
        stmUsuario.setString(1, "%"+login+"%");
        stmUsuario.setString(2, "%"+nome+"%");
        rsUsuarios = stmUsuario.executeQuery();
        while (rsUsuarios.next()) {
            usuarios.add(new Usuario(
                    rsUsuarios.getString("login"),
                    rsUsuarios.getString("contrasinal"),
                    rsUsuarios.getString("nome"),
                    rsUsuarios.getString("numTelefono"),
                    rsUsuarios.getString("DNI"),
                    rsUsuarios.getString("correoElectronico"),
                    rsUsuarios.getString("iban"),
                    rsUsuarios.getDate("dataAlta")
            ));
        }
        return usuarios;
    }
    /*
        Funcions DAOTarifas
     */

    /*
        Funcions DAOMensaxes
     */

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

package centrodeportivo.aplicacion.obxectos.xestion;

import centrodeportivo.aplicacion.obxectos.usuarios.Persoal;
import centrodeportivo.aplicacion.obxectos.usuarios.Profesor;
import centrodeportivo.aplicacion.obxectos.usuarios.Socio;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.baseDatos.FachadaBD;
import centrodeportivo.gui.FachadaGUI;

import java.sql.SQLException;
import java.util.ArrayList;

public class XestionUsuarios {
    private FachadaGUI fachadaGUI;
    private FachadaBD fachadaBD;

    public XestionUsuarios(FachadaGUI fachadaGUI,FachadaBD fachadaBD) {
        this.fachadaGUI=fachadaGUI;
        this.fachadaBD=fachadaBD;
    }

    public boolean existeUsuario(String login) throws SQLException {
        return fachadaBD.existeUsuario(login);
    }

    public boolean validarUsuario(String login,String password) throws SQLException{
        return fachadaBD.validarUsuario(login,password);
    }

    public void insertarUsuario(Usuario usuario) throws SQLException{
        fachadaBD.insertarUsuario(usuario);
    }

    public void actualizarUsuario(String loginVello,Usuario usuario) throws SQLException {
        fachadaBD.actualizarUsuario(loginVello,usuario);
    }

    public void darBaixaUsuario(String login) throws SQLException {
        fachadaBD.darBaixaUsuario(login);
    }

    public ArrayList<Socio> listarSocios() throws SQLException {
        return fachadaBD.listarSocios();
    }

    public ArrayList<Persoal> listarPersoal() throws SQLException {
        return fachadaBD.listarPersoal();
    }

    public ArrayList<Profesor> listarProfesores() throws SQLException {
        return fachadaBD.listarProfesores();
    }

    public ArrayList<Usuario> listarUsuarios() throws SQLException {
        return fachadaBD.listarUsuarios();
    }

    public ArrayList<Socio> buscarSocios(String login,String nome) throws SQLException {
        return fachadaBD.buscarSocios(login,nome);
    }

    public ArrayList<Persoal> buscarPersoal(String login,String nome) throws SQLException {
        return fachadaBD.buscarPersoal(login, nome);
    }

    public ArrayList<Profesor> buscarProfesores(String login,String nome) throws SQLException {
        return fachadaBD.buscarProfesores(login, nome);
    }

    public ArrayList<Usuario> buscarUsuarios(String login,String nome) throws SQLException{
        return fachadaBD.buscarUsuarios(login, nome);
    }
}

package centrodeportivo.aplicacion.xestion;

import centrodeportivo.aplicacion.obxectos.tipos.TipoUsuario;
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

    public TipoUsuario consultarTipo(String login) throws SQLException {
        return fachadaBD.consultarTipo(login);
    }

    public Usuario consultarUsuario(String login) throws SQLException {
        return fachadaBD.consultarUsuario(login);
    }

    public ArrayList<Usuario> buscarUsuarios(String login,String nome,TipoUsuario filtro) throws SQLException {
        return fachadaBD.buscarUsuarios(login,nome,filtro);
    }

    public ArrayList<Usuario> listarUsuarios(TipoUsuario filtro) throws SQLException {
        return fachadaBD.buscarUsuarios("","",filtro);
    }

    public ArrayList<Usuario> listarUsuarios() throws SQLException {
        return fachadaBD.buscarUsuarios("","",TipoUsuario.Todos);
    }

}

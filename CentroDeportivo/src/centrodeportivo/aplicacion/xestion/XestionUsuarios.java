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

    public boolean existeUsuario(String login)  {
        return fachadaBD.existeUsuario(login);
    }

    public boolean existeDNI(String dni)  {
        return fachadaBD.existeDNI(dni);
    }

    public boolean validarUsuario(String login,String password) {
        return fachadaBD.validarUsuario(login,password);
    }

    public void insertarUsuario(Usuario usuario) {
        fachadaBD.insertarUsuario(usuario);
    }

    public void actualizarUsuario(String loginVello,Usuario usuario)  {
        fachadaBD.actualizarUsuario(loginVello,usuario);
    }

    public void darBaixaUsuario(String login)  {
        fachadaBD.darBaixaUsuario(login);
    }

    public TipoUsuario consultarTipo(String login)  {
        return fachadaBD.consultarTipo(login);
    }

    public Usuario consultarUsuario(String login)  {
        return fachadaBD.consultarUsuario(login);
    }

    public ArrayList<Usuario> buscarUsuarios(String login,String nome,TipoUsuario filtro)  {
        return fachadaBD.buscarUsuarios(login,nome,filtro);
    }

    public ArrayList<Usuario> listarUsuarios(TipoUsuario filtro)  {
        return fachadaBD.buscarUsuarios("","",filtro);
    }

    public ArrayList<Usuario> listarUsuarios()  {
        return fachadaBD.buscarUsuarios("","",TipoUsuario.Todos);
    }

}

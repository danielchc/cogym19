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

    public boolean validarUsuario(String login,String password) {
        return fachadaBD.validarUsuario(login,password);
    }


    public TipoUsuario consultarTipo(String login)  {
        return fachadaBD.consultarTipo(login);
    }

    public Usuario consultarUsuario(String login)  {
        return fachadaBD.consultarUsuario(login);
    }

}

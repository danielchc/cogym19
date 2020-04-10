package centrodeportivo.aplicacion.xestion;

import centrodeportivo.aplicacion.obxectos.Mensaxe;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.baseDatos.FachadaBD;
import centrodeportivo.gui.FachadaGUI;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public class XestionMensaxes {
    private FachadaGUI fachadaGUI;
    private FachadaBD fachadaBD;

    public XestionMensaxes(FachadaGUI fachadaGUI,FachadaBD fachadaBD) {
        this.fachadaGUI=fachadaGUI;
        this.fachadaBD=fachadaBD;
    }


    public void enviarMensaxe(Mensaxe m) {
        fachadaBD.enviarMensaxe(m);
    }

    public void enviarMensaxe(Usuario emisor, ArrayList<Usuario> receptores, String mensaxe) {
        fachadaBD.enviarMensaxe(emisor, receptores, mensaxe);
    }

    public void marcarMensaxeComoLido(Mensaxe m) {
        fachadaBD.marcarMensaxeComoLido(m);
    }

    public ArrayList<Mensaxe> listarMensaxesRecibidos(String loginReceptor) {
        return fachadaBD.listarMensaxesRecibidos(loginReceptor);
    }
}

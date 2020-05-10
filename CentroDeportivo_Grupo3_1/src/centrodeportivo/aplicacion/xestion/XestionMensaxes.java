package centrodeportivo.aplicacion.xestion;

import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.Mensaxe;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.baseDatos.FachadaBD;
import centrodeportivo.gui.FachadaGUI;

import java.util.ArrayList;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public class XestionMensaxes {

    /**
     * Atributos
     */
    private FachadaGUI fachadaGUI;
    private FachadaBD fachadaBD;

    /**
     * @param fachadaGUI Fachada da gui.
     * @param fachadaBD  Fachada da base de datos.
     */
    public XestionMensaxes(FachadaGUI fachadaGUI, FachadaBD fachadaBD) {
        this.fachadaGUI = fachadaGUI;
        this.fachadaBD = fachadaBD;
    }

    /**
     * Métodos
     */
    public void enviarMensaxe(Mensaxe m) throws ExcepcionBD {
        fachadaBD.enviarMensaxe(m);
    }

    public void enviarMensaxe(Usuario emisor, ArrayList<Usuario> receptores, String mensaxe) throws ExcepcionBD {
        fachadaBD.enviarMensaxe(emisor, receptores, mensaxe);
    }

    public void marcarMensaxeComoLido(Mensaxe m) throws ExcepcionBD {
        fachadaBD.marcarMensaxeComoLido(m);
    }

    public ArrayList<Mensaxe> listarMensaxesRecibidos(String loginReceptor) {
        return fachadaBD.listarMensaxesRecibidos(loginReceptor);
    }
}

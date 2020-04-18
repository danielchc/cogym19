package centrodeportivo.aplicacion.xestion;

import centrodeportivo.aplicacion.obxectos.actividades.TipoActividade;
import centrodeportivo.baseDatos.FachadaBD;
import centrodeportivo.gui.FachadaGUI;

import java.util.ArrayList;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public class XestionActividades {

    /**
     * Atributos
     */
    private FachadaGUI fachadaGUI;
    private FachadaBD fachadaBD;

    /**
     * @param fachadaGUI Fachada da gui.
     * @param fachadaBD Fachada da base de datos.
     */
    public XestionActividades(FachadaGUI fachadaGUI, FachadaBD fachadaBD) {
        this.fachadaGUI = fachadaGUI;
        this.fachadaBD = fachadaBD;
    }

    /**
     * Métodos
     */
    public ArrayList<TipoActividade> listarTipoActividades() {
        return fachadaBD.listarTipoActividades();
    }

}

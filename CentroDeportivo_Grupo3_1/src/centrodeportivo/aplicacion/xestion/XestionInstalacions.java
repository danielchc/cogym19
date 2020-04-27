package centrodeportivo.aplicacion.xestion;

import centrodeportivo.aplicacion.obxectos.Material;
import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.baseDatos.FachadaBD;
import centrodeportivo.gui.FachadaGUI;

import java.util.ArrayList;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public class XestionInstalacions {

    /**
     * Atributos
     */
    private FachadaGUI fachadaGUI;
    private FachadaBD fachadaBD;

    /**
     * @param fachadaGUI Fachada da gui.
     * @param fachadaBD  Fachada da base de datos.
     */
    public XestionInstalacions(FachadaGUI fachadaGUI, FachadaBD fachadaBD) {
        this.fachadaGUI = fachadaGUI;
        this.fachadaBD = fachadaBD;
    }


    /**
     * Métodos
     */
    public ArrayList<Area> listarAreas() {
        return fachadaBD.listarAreas();
    }

    public ArrayList<Material> listarMateriais() {
        return fachadaBD.listarMateriais();
    }
}

package centrodeportivo.aplicacion.xestion;

import centrodeportivo.aplicacion.obxectos.Material;
import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.baseDatos.FachadaBD;
import centrodeportivo.gui.FachadaGUI;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public class XestionInstalacions {

    private FachadaGUI fachadaGUI;
    private FachadaBD fachadaBD;

    public XestionInstalacions(FachadaGUI fachadaGUI, FachadaBD fachadaBD) {
        this.fachadaGUI = fachadaGUI;
        this.fachadaBD = fachadaBD;
    }

    public ArrayList<Area> listarAreas() {
        return fachadaBD.listarAreas();
    }
}

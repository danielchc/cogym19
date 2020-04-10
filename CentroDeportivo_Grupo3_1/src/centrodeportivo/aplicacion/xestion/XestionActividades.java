package centrodeportivo.aplicacion.xestion;

import centrodeportivo.aplicacion.obxectos.actividades.TipoActividade;
import centrodeportivo.baseDatos.FachadaBD;
import centrodeportivo.gui.FachadaGUI;

import java.util.ArrayList;

public class XestionActividades {
    private FachadaGUI fachadaGUI;
    private FachadaBD fachadaBD;

    public XestionActividades(FachadaGUI fachadaGUI,FachadaBD fachadaBD) {
        this.fachadaGUI=fachadaGUI;
        this.fachadaBD=fachadaBD;
    }

    public ArrayList<TipoActividade> listarTipoActividades(){
        return fachadaBD.listarTipoActividades();
    }

}

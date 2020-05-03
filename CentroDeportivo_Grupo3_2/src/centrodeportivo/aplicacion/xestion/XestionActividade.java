package centrodeportivo.aplicacion.xestion;

import centrodeportivo.baseDatos.FachadaBD;
import centrodeportivo.gui.FachadaGUI;

public class XestionActividade {

    private FachadaGUI fachadaGUI;
    private FachadaBD fachadaBD;

    public XestionActividade(FachadaGUI fachadaGUI, FachadaBD fachadaBD){
        this.fachadaGUI = fachadaGUI;
        this.fachadaBD = fachadaBD;
    }

}

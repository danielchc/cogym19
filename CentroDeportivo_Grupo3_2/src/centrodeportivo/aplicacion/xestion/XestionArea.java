package centrodeportivo.aplicacion.xestion;

import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.aplicacion.obxectos.tipos.TipoResultados;
import centrodeportivo.baseDatos.FachadaBD;
import centrodeportivo.gui.FachadaGUI;

public class XestionArea {
    private FachadaGUI fachadaGUI;
    private FachadaBD fachadaBD;

    public XestionArea(FachadaGUI fachadaGUI, FachadaBD fachadaBD){
        this.fachadaGUI = fachadaGUI;
        this.fachadaBD = fachadaBD;
    }

    public void EngadirArea(Area area) throws ExcepcionBD {
        //Se a instalación non existe, dase de alta:
        fachadaBD.EngadirArea(area);
    }
}

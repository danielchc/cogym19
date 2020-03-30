package centrodeportivo.aplicacion.xestion;

import centrodeportivo.baseDatos.FachadaBD;
import centrodeportivo.gui.FachadaGUI;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;

import java.util.ArrayList;

public class XestionInstalacions {
    private FachadaGUI fachadaGUI;
    private FachadaBD fachadaBD;

    public XestionInstalacions(FachadaGUI fachadaGUI, FachadaBD fachadaBD){
        this.fachadaGUI = fachadaGUI;
        this.fachadaBD = fachadaBD;
    }

    public void darAltaInstalacion(Instalacion instalacion){
        fachadaBD.darAltaInstalacion(instalacion);
    }

    public void borrarInstalacion(Instalacion instalacion){
        fachadaBD.borrarInstalacion(instalacion);
    }

    public void modificarInstalacion(Instalacion instalacion){
        fachadaBD.modificarInstalacion(instalacion);
    }

    public ArrayList<Instalacion> listarInstalacions(){
        return fachadaBD.listarInstalacions();
    }
}

package centrodeportivo.aplicacion.xestion;

import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.aplicacion.obxectos.tipos.TipoResultados;
import centrodeportivo.baseDatos.FachadaBD;
import centrodeportivo.gui.FachadaGUI;

import java.util.ArrayList;

public class XestionArea {
    private FachadaGUI fachadaGUI;
    private FachadaBD fachadaBD;

    public XestionArea(FachadaGUI fachadaGUI, FachadaBD fachadaBD){
        this.fachadaGUI = fachadaGUI;
        this.fachadaBD = fachadaBD;
    }

    public TipoResultados EngadirArea(Area area) throws ExcepcionBD {
        //Se a area non existe, dase de alta:
        if(!fachadaBD.ExisteArea(area)) {
            fachadaBD.EngadirArea(area);
            //Se se completa a execución do método sen lanzamento de excepcións, devolvemos que foi ben:
            return TipoResultados.correcto;
        } else {
            return TipoResultados.datoExiste;
        }
    }

    public TipoResultados borrarArea(Area area) throws ExcepcionBD {
        if(fachadaBD.ExisteArea(area)){
            fachadaBD.borrarArea(area);
            //Se se completou o método correctamente, devolvemos o enum que indica corrección:
            return TipoResultados.correcto;
        } else {
            return TipoResultados.referenciaRestrict;
        }
    }

    public TipoResultados modificarArea(Area area) throws ExcepcionBD {
        if(fachadaBD.ExisteArea(area)) {
            fachadaBD.modificarArea(area);
            //Se se completa a execución do método sen lanzamento de excepcións, devolvemos que foi ben:
            return TipoResultados.correcto;
        } else {
            return TipoResultados.datoExiste;
        }
    }

    public TipoResultados darDeBaixaArea(Area area) throws ExcepcionBD {
        if (fachadaBD.ExisteArea(area) && !fachadaBD.EBaixaArea(area)) {
            fachadaBD.darDeBaixaArea(area);
            //Se se completa a execución do método sen lanzamento de excepcións, devolvemos que foi ben:
            return TipoResultados.correcto;
        } else {
            return TipoResultados.datoExiste;
        }
    }

    public TipoResultados darDeAltaAreaa(Area area) throws ExcepcionBD {
        //Se a area non existe, dase de alta:
        if (fachadaBD.ExisteArea(area) && fachadaBD.EBaixaArea(area)) {
            fachadaBD.darDeAltaArea(area);
            //Se se completa a execución do método sen lanzamento de excepcións, devolvemos que foi ben:
            return TipoResultados.correcto;
        } else {
            return TipoResultados.datoExiste;
        }
    }


    public ArrayList<Instalacion> buscarInstalacions(Instalacion instalacion){
        return fachadaBD.buscarInstalacions(instalacion);
    }

    public ArrayList<Area> listarAreas() throws ExcepcionBD {
        return fachadaBD.listarAreas();
    }
}

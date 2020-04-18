package centrodeportivo.aplicacion.xestion;

import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.incidencias.Incidencia;
import centrodeportivo.aplicacion.obxectos.tipos.TipoIncidencia;
import centrodeportivo.baseDatos.FachadaBD;
import centrodeportivo.gui.FachadaGUI;

import java.util.ArrayList;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public class XestionIncidencias {

    /**
     * Atributos
     */
    private FachadaGUI fachadaGUI;
    private FachadaBD fachadaBD;

    /**
     * @param fachadaGUI Fachada da gui.
     * @param fachadaBD Fachada da base de datos.
     */
    public XestionIncidencias(FachadaGUI fachadaGUI, FachadaBD fachadaBD) {
        this.fachadaGUI = fachadaGUI;
        this.fachadaBD = fachadaBD;
    }

    /**
     * Métodos
     */
    public void insertarIncidencia(Incidencia incidencia) throws ExcepcionBD {
        fachadaBD.insertarIncidencia(incidencia);
    }

    public ArrayList<Incidencia> buscarIncidencias(String textoBuscar, TipoIncidencia tipoIncidencia,boolean mostrarResoltas) {
        return fachadaBD.buscarIncidencias(textoBuscar, tipoIncidencia,mostrarResoltas);
    }

    public void resolverIncidencia(Incidencia incidencia) throws ExcepcionBD {
        fachadaBD.resolverIncidencia(incidencia);
    }

    public Incidencia consultarIncidencia(Incidencia incidencia) throws ExcepcionBD {
        return fachadaBD.consultarIncidencia(incidencia);
    }

}

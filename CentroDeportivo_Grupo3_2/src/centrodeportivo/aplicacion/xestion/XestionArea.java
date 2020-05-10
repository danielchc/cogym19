package centrodeportivo.aplicacion.xestion;

import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.aplicacion.obxectos.tipos.TipoResultados;
import centrodeportivo.baseDatos.FachadaBD;
import centrodeportivo.gui.FachadaGUI;

import java.util.ArrayList;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Víctor Barreiro
 * Clase na que levaremos a cabo xestións relativas ás áreas.
 */
public class XestionArea {
    /**
     * Atributos da xestión de Areas: basicamente son referencias ás demáis fachadas.
     */
    private FachadaGUI fachadaGUI;
    private FachadaBD fachadaBD;


    /**
     * Constructor da clase de xestión de instalacións:
     *
     * @param fachadaGUI Referencia á fachada da interface gráfica.
     * @param fachadaBD  Referencia á fachada da parte da Base de datos.
     */
    public XestionArea(FachadaGUI fachadaGUI, FachadaBD fachadaBD) {
        this.fachadaGUI = fachadaGUI;
        this.fachadaBD = fachadaBD;
    }


    /**
     * @param area a engadir
     * @return O resultado da operación levada a cabo.
     * @throws ExcepcionBD Excepción asociada a problemas ao tentar facer a actualización sobre a base de datos.
     */
    public TipoResultados EngadirArea(Area area) throws ExcepcionBD {
        //Se non existe outra área co mesmo nome, dase de alta:
        if (!fachadaBD.ExisteArea(area)) {
            fachadaBD.EngadirArea(area);
            //Se se completa a execución do método sen lanzamento de excepcións, devolvemos que foi ben:
            return TipoResultados.correcto;
        } else {
            //Se existise, devolvemos un erro con este tipo:
            return TipoResultados.datoExiste;
        }
    }

    public TipoResultados borrarArea(Area area) throws ExcepcionBD {
        if (!fachadaBD.tenActividadesArea(area, false) && !fachadaBD.tenMateriaisArea(area)) {
            fachadaBD.borrarArea(area);
            //Se se completou o método correctamente, devolvemos o enum que indica corrección:
            return TipoResultados.correcto;
        } else {
            return TipoResultados.referenciaRestrict;
        }
    }

    public TipoResultados modificarArea(Area area) throws ExcepcionBD {
        //Comprobamos que non haxa outra area na instalación co mesmo nome que a que se quere meter:
        if (!fachadaBD.ExisteArea(area)) {
            fachadaBD.modificarArea(area);
            //Se se completa a execución do método sen lanzamento de excepcións, devolvemos que foi ben:
            return TipoResultados.correcto;
        } else {
            //Se non, devolveremos o enumerado que representa que o dato existe:
            return TipoResultados.datoExiste;
        }
    }

    public TipoResultados darDeBaixaArea(Area area) throws ExcepcionBD {
        //A área non pode estar xa dada de baixa nin ter actividades sen comezar:
        if (!fachadaBD.EBaixaArea(area)) {
            if (!fachadaBD.tenActividadesArea(area, true)) {
                fachadaBD.darDeBaixaArea(area);
                //Se se completa a execución do método sen lanzamento de excepcións, devolvemos que foi ben:
                return TipoResultados.correcto;
            } else {
                //Se ten actividades sen comezar, non se pode levar a cabo a baixa:
                return TipoResultados.referenciaRestrict;
            }
        } else {
            //Se a área xa está dada de baixa, estaremos nunha situacion incorrecta na que non se pode facer o borrado:
            return TipoResultados.sitIncoherente;
        }
    }

    public TipoResultados darDeAltaArea(Area area) throws ExcepcionBD {
        //Se a area está de baixa, dase de alta:
        if (fachadaBD.EBaixaArea(area)) {
            fachadaBD.darDeAltaArea(area);
            //Se se completa a execución do método sen lanzamento de excepcións, devolvemos que foi ben:
            return TipoResultados.correcto;
        } else {
            return TipoResultados.sitIncoherente;
        }
    }

    public boolean EBaixaArea(Area area) throws ExcepcionBD {
        return fachadaBD.EBaixaArea(area);
    }

    public ArrayList<Area> buscarArea(Instalacion instalacion, Area area) {
        return fachadaBD.buscarArea(instalacion, area);
    }

    public ArrayList<Area> listarAreas() throws ExcepcionBD {
        return fachadaBD.listarAreas();
    }

    /**
     * Método que nos permite buscar areas na base de datos en función dunha instalación.
     *
     * @param instalacion Se non é null, a consulta realizase en base o codigo da area.
     * @return Se o parametro non é null, será devolto unha ObservableList con todas as areas que coincidan,
     * noutro caso, listaranse todas as areas.
     */
    public ArrayList<Area> listarAreasActivas(Instalacion instalacion) {
        return fachadaBD.listarAreasActivas(instalacion);
    }
}

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
     * Atributos da xestión de Áreas: basicamente son referencias ás demáis fachadas.
     */
    private FachadaGUI fachadaGUI;
    private FachadaBD fachadaBD;


    /**
     * Constructor da clase de xestión de áreas:
     *
     * @param fachadaGUI Referencia á fachada da interface gráfica.
     * @param fachadaBD  Referencia á fachada da parte da Base de datos.
     */
    public XestionArea(FachadaGUI fachadaGUI, FachadaBD fachadaBD) {
        this.fachadaGUI = fachadaGUI;
        this.fachadaBD = fachadaBD;
    }


    /**
     * Método que permite engadir unha nova área na base de datos.
     *
     * @param area Información da área a engadir na base de datos
     * @return Devolve un enum que tipifica os posibles erroes durante a execución do método.
     * @throws ExcepcionBD Excepción procedente da base de datos para indicar problemas na inserción.
     */
    public TipoResultados EngadirArea(Area area) throws ExcepcionBD {
        // Se non existe outra área co mesmo nome, dase de alta:
        if (!fachadaBD.ExisteArea(area)) {
            fachadaBD.EngadirArea(area);
            // Se se completa a execución do método sen lanzamento de excepcións, devolvemos que foi ben:
            return TipoResultados.correcto;
        } else {
            // Se existise, devolvemos un erro con este tipo:
            return TipoResultados.datoExiste;
        }
    }

    /**
     * Método que permite borrar unha área da base de datos.
     *
     * @param area Área que se desexa eliminar da base de datos.
     * @return Devolve un enum que tipifica os posibles erroes durante a execución do método.
     * @throws ExcepcionBD Excepción procedente da base de datos para indicar problemas no borrado.
     */
    public TipoResultados borrarArea(Area area) throws ExcepcionBD {
        if (!fachadaBD.tenActividadesArea(area, false) && !fachadaBD.tenMateriaisArea(area)) {
            fachadaBD.borrarArea(area);
            // Se se completou o método correctamente, devolvemos o enum que indica corrección:
            return TipoResultados.correcto;
        } else {
            return TipoResultados.referenciaRestrict;
        }
    }

    /**
     * Método que permite modificar os datos dunha área na base de datos.
     *
     * @param area Área cos datos modificados para realizar ditos cambios na base de datos.
     * @return Devolve un enum que tipifica os posibles erroes durante a execución do método.
     * @throws ExcepcionBD Excepción procedente da base de datos para indicar problemas na modificación.
     */
    public TipoResultados modificarArea(Area area) throws ExcepcionBD {
        // Comprobamos que non haxa outra área na instalación co mesmo nome que a que se quere meter:
        if (!fachadaBD.ExisteArea(area)) {
            fachadaBD.modificarArea(area);
            // Se se completa a execución do método sen lanzamento de excepcións, devolvemos que foi ben:
            return TipoResultados.correcto;
        } else {
            // Se non, devolveremos o enumerado que representa que o dato existe:
            return TipoResultados.datoExiste;
        }
    }

    /**
     * Método que permite dar de baixa un área, é dicir, non se elimina da base de datos pero,
     * deixará de estar dispoñible para realizar actividades.
     *
     * @param area Área que se quere dar de baixa.
     * @return Devolve un enum que tipifica os posibles erroes durante a execución do método.
     * @throws ExcepcionBD Excepción procedente da base de datos para indicar problemas na actualización.
     */
    public TipoResultados darDeBaixaArea(Area area) throws ExcepcionBD {
        // A área non pode estar xa dada de baixa nin ter actividades sen comezar:
        if (!fachadaBD.EBaixaArea(area)) {
            if (!fachadaBD.tenActividadesArea(area, true)) {
                fachadaBD.darDeBaixaArea(area);
                // Se se completa a execución do método sen lanzamento de excepcións, devolvemos que foi ben:
                return TipoResultados.correcto;
            } else {
                // Se ten actividades sen comezar, non se pode levar a cabo a baixa:
                return TipoResultados.referenciaRestrict;
            }
        } else {
            // Se a área xa está dada de baixa, estaremos nunha situacion incorrecta na que non se pode facer o borrado:
            return TipoResultados.sitIncoherente;
        }
    }

    /**
     * Método que permite dar de alta unha área que xa exisita na base de datos pero, agora estará
     * dispoñible para a realización de actividades.
     *
     * @param area Área que se procura dar de alta.
     * @return Devolve un enum que tipifica os posibles erroes durante a execución do método.
     * @throws ExcepcionBD Excepción procedente da base de datos para indicar problemas na actualización.
     */
    public TipoResultados darDeAltaArea(Area area) throws ExcepcionBD {
        // Se a área está de baixa, dase de alta:
        if (fachadaBD.EBaixaArea(area)) {
            fachadaBD.darDeAltaArea(area);
            // Se se completa a execución do método sen lanzamento de excepcións, devolvemos que foi ben:
            return TipoResultados.correcto;
        } else {
            return TipoResultados.sitIncoherente;
        }
    }

    /**
     * Método que permite realizar a busca de áreas en función dos datos dunha instalación e seguindo un modelo de área.
     *
     * @param instalacion Instalación que empregaremos para filtrar.
     * @param area        Área modelo que empregaremos para filtrar.
     * @return Retorna un ArrayList de áreas que cumpran ditas condicións de filtrado.
     */
    public ArrayList<Area> buscarArea(Instalacion instalacion, Area area) {
        return fachadaBD.buscarArea(instalacion, area);
    }

    /**
     * Método que permite listar todas as áreas activas dunha instalación.
     *
     * @param instalacion Datos da instalación na que se realiza a procura das áreas activas.
     * @return Retorna un ArrayList cas áreas activas de dita instalación.
     */
    public ArrayList<Area> listarAreasActivas(Instalacion instalacion) {
        return fachadaBD.listarAreasActivas(instalacion);
    }
}

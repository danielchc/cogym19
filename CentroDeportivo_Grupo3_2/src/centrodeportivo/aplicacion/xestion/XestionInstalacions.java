package centrodeportivo.aplicacion.xestion;

import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.tipos.TipoResultados;
import centrodeportivo.baseDatos.FachadaBD;
import centrodeportivo.gui.FachadaGUI;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;

import java.util.ArrayList;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Víctor Barreiro
 * Esta clase é aquela na que se levarán a cabo tarefas de xestión na parte de aplicación relacionadas coas instalacións.
 * Introduciremos aquí as comprobacións que se realizarán para cada caso.
 */
public class XestionInstalacions {
    /**
     * Atributos da xestión de instalacións: basicamente son referencias ás demáis fachadas.
     */
    private FachadaGUI fachadaGUI;
    private FachadaBD fachadaBD;

    /**
     * Constructor da clase de xestión de instalacións:
     * @param fachadaGUI Referencia á fachada da interface gráfica.
     * @param fachadaBD Referencia á fachada da parte da Base de datos.
     */
    public XestionInstalacions(FachadaGUI fachadaGUI, FachadaBD fachadaBD){
        //Asignamos os parámetros pasados aos atributos correspondentes:
        this.fachadaGUI = fachadaGUI;
        this.fachadaBD = fachadaBD;
    }

    /**
     * Método que nos permitirá dar de alta unha nova instalación.
     * @param instalacion A instalación a dar de alta.
     * @return O resultado da operación levada a cabo.
     * @throws ExcepcionBD Excepción asociada a problemas ao tentar facer a actualización sobre a base de datos.
     */
    public TipoResultados darAltaInstalacion(Instalacion instalacion) throws ExcepcionBD {
        //Primeiro comprobaremos se hai unha instalación co mesmo nome ca esta.
        if(!fachadaBD.comprobarExistencia(instalacion)) {
            //Se non existe, entón poderemos insertar esta sen ningún problema:
            fachadaBD.darAltaInstalacion(instalacion);
            //Se se completa a execución do método sen lanzamento de excepcións, devolvemos que rematou ben a inserción:
            return TipoResultados.correcto;
        } else {
            //En caso de que xa existise unha instalación co mesmo nome, devolvemos un resultado incorrecto.
            return TipoResultados.datoExiste;
        }

    }

    public TipoResultados borrarInstalacion(Instalacion instalacion) throws ExcepcionBD {
        if(!fachadaBD.tenAreas(instalacion)){
            fachadaBD.borrarInstalacion(instalacion);
            //Se se completou o método correctamente, devolvemos o enum que indica corrección:
            return TipoResultados.correcto;
        } else {
            //Se houbese áreas asociadas a instalacións, devolvemos o enum de erro por referencias co restrict.
            return TipoResultados.referenciaRestrict;
        }

    }

    public TipoResultados modificarInstalacion(Instalacion instalacion) throws ExcepcionBD {
        if(!fachadaBD.comprobarExistencia(instalacion)) {
            fachadaBD.modificarInstalacion(instalacion);
            //Devolvemos co enum que se fixo a modificación sen problemas chegados a este punto:
            return TipoResultados.correcto;
        } else {
            //Se xa existe outra instalación co mesmo nome, entón devolvemos un enum que indique o propio:
            return TipoResultados.datoExiste;
        }
    }

    /**
     * Método que nos permite buscar instalacións na base de datos, tanto con coma sen filtros.
     * @param instalacion Se non é null, a consulta das instalacións realizarase en base aos campos desta instalación.
     * @return Se instalación non é null, devolveranse as instalacións que coincidan cos campos de consulta, en caso
     * contrario, devolverase un listado de todas as instalacións.
     */
    public ArrayList<Instalacion> buscarInstalacions(Instalacion instalacion){
        return fachadaBD.buscarInstalacions(instalacion);
    }

}

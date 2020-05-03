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

    /**
     * Método que tenta eliminar os datos da instalación pasada como argumento da base de datos.
     * @param instalacion A instalación cuxos datos se queren eliminar.
     * @return O resultado da operación levada a cabo.
     * @throws ExcepcionBD Excepción asociada a problemas que poden xurdir ao actualizar a base de datos.
     */
    public TipoResultados borrarInstalacion(Instalacion instalacion) throws ExcepcionBD {
        //Comprobamos se a instalación ten áreas asociadas:
        if(!fachadaBD.tenAreas(instalacion)){
            //Se non ten áreas asociadas, poderemos borrala:
            fachadaBD.borrarInstalacion(instalacion);
            //Se se completou o método correctamente, devolvemos o enum que indica corrección:
            return TipoResultados.correcto;
        } else {
            //Se houbese áreas asociadas a instalacións, devolvemos o enum de erro por referencias co restrict.
            return TipoResultados.referenciaRestrict;
        }
    }

    /**
     * Método que tenta modificar os datos da instalación pasada como argumento na base de datos.
     * @param instalacion Os datos da instalación para ser modificados.
     * @return O resultado da operación levada a cabo.
     * @throws ExcepcionBD Excepción asociada a posibles problemas dados ao actualizar a base de datos.
     */
    public TipoResultados modificarInstalacion(Instalacion instalacion) throws ExcepcionBD {
        //De xeito análogo ao que facemos cando se inserta, comprobamos que o nome non esté rexistrado xa na base
        //de datos, ollo, NOUTRA instalación que non sexa esta.
        if(!fachadaBD.comprobarExistencia(instalacion)) {
            //Se non o está, entón procedemos a modificar a instalación:
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

    /**
     * Método que nos permite consultar unha instalación concreta:
     * @param instalacion A instalación de referencia para a que se consultará a información
     * @return A instalación con todos os datos, actualizada totalmente.
     */
    public Instalacion consultarInstalacion(Instalacion instalacion){
        return fachadaBD.consultarInstalacion(instalacion);
    }

}

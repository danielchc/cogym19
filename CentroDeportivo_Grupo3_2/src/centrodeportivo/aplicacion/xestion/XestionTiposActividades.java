package centrodeportivo.aplicacion.xestion;

import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.actividades.TipoActividade;
import centrodeportivo.aplicacion.obxectos.tipos.TipoResultados;
import centrodeportivo.baseDatos.FachadaBD;
import centrodeportivo.gui.FachadaGUI;

import java.util.ArrayList;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Víctor Barreiro
 * Esta clase é aquela na que se levarán a cabo tarefas de xestión na parte de aplicación relacionadas co tipo das actividades.
 * Introduciremos aquí as comprobacións que se realizarán para cada caso.
 */
public class XestionTiposActividades {

    /**
     * Atributos da xestión dos tipos de actividades: basicamente son referencias ás demáis fachadas.
     */
    private FachadaBD fachadaBD;
    private FachadaGUI fachadaGUI;


    /**
     * Constructor da clase de xestión dos tipos de actividades:
     *
     * @param fachadaGUI Referencia á fachada da interface gráfica.
     * @param fachadaBD  Referencia á fachada da parte da Base de datos.
     */
    public XestionTiposActividades(FachadaGUI fachadaGUI, FachadaBD fachadaBD) {
        this.fachadaBD = fachadaBD;
        this.fachadaGUI = fachadaGUI;
    }

    /**
     * Método que nos permite introducir na base de datos a información dun novo tipo de actividade, cuxa información
     * se pasa como arugmento.
     *
     * @param tipoActividade Os datos do tipo de actividade a insertar.
     * @return Se instalación non é null, devolveranse as instalacións que coincidan cos campos de consulta, en caso
     * @throws ExcepcionBD Excepción asociada a problemas que ocorran na actualización da base de datos.
     */
    public TipoResultados crearTipoActividade(TipoActividade tipoActividade) throws ExcepcionBD {
        // Comprobamos que non exista outro tipo de actividade co mesmo nome ca o que se quere insertar:
        if (!this.fachadaBD.comprobarExistencia(tipoActividade)) {
            // Chamamos á base de datos para crear un novo tipo de actividade.
            this.fachadaBD.crearTipoActividade(tipoActividade);
            // En caso de que se cree o tipo de actividade, enton devolvemos un indicador do remate correcto:
            return TipoResultados.correcto;
        } else {
            // Se xa existe un tipo de actividade igual almacenado, entón devolvemos que houbo problemas nese sentido:
            return TipoResultados.datoExiste;
        }
    }

    /**
     * Método que nos permite modificar os datos do tipo de actividade pasado como argumento. Suponse que ese tipo de
     * actividade xa está rexistrado e, polo tanto, ten un código asociado.
     *
     * @param tipoActividade O tipo de actividade cos datos a actualizar.
     * @return Se instalación non é null, devolveranse as instalacións que coincidan cos campos de consulta, en caso
     * @throws ExcepcionBD Excepción asociada a problemas que poidan ocorrer durante a modificación na base de datos.
     */
    public TipoResultados modificarTipoActividade(TipoActividade tipoActividade) throws ExcepcionBD {
        // Hai que verificar primeiro que non exista outro tipo de actividade existente co nome pasado
        // (que non sexa este, claro):
        if (!this.fachadaBD.comprobarExistencia(tipoActividade)) {
            // Se non existe, modificamos o tipo:
            this.fachadaBD.modificarTipoActividade(tipoActividade);
            // Se se rematou correctamente, devólvese o enumerado indicador de remate correcto:
            return TipoResultados.correcto;
        } else {
            // Se hai un tipo de actividade DISTINTO DESTE co mesmo nome que se quere modificar, devólvese
            // que xa existe:
            return TipoResultados.datoExiste;
        }
    }

    /**
     * Método que nos permite eliminar da base de datos o tipo de actividade pasado como argumento.
     *
     * @param tipoActividade O tipo de actividade que se quere eliminar.
     * @return Se instalación non é null, devolveranse as instalacións que coincidan cos campos de consulta, en caso
     * @throws ExcepcionBD Excepción asociada a problemas que ocorran durante a actualización da base de datos.
     */
    public TipoResultados eliminarTipoActividade(TipoActividade tipoActividade) throws ExcepcionBD {
        // O curso non se pode borrar se ten actividades, polo que faremos primeiro esa comprobación:
        if (!this.fachadaBD.tenActividades(tipoActividade)) {
            // En caso de que non teña actividades, poderemos facer o borrado:
            this.fachadaBD.eliminarTipoActividade(tipoActividade);
            // Se se chegou a este punto, entón devólvese unha confirmación do remate correcto:
            return TipoResultados.correcto;
        } else {
            // Se o tipo ten actividades asociadas, entón devolvemos que hai un problema cos restrict para o borrado:
            return TipoResultados.referenciaRestrict;
        }

    }

    /**
     * Método que ofrece un conxunto de tipos de actividade contidos na base de datos.
     *
     * @param tipoActividade O tipo de actividade modelo co que se vai a facer a búsqueda.
     * @return Se o tipo de actividade é null, devolveranse todos os tipos de actividades rexistrados, en caso contrario
     * todos os tipos de actividade que teñan coincidencia co nome que ten o tipo pasado como argumento.
     */
    public ArrayList<TipoActividade> buscarTiposActividades(TipoActividade tipoActividade) {
        return this.fachadaBD.buscarTiposActividades(tipoActividade);
    }

    /**
     * Método que nos permite consultar un tipo de actividade a partir do código do tipo pasado como argumento.
     *
     * @param tipoActividade O tipo de actividade do que se collerá o código para a consulta.
     * @return O tipo de actividade co código buscado (se aínda existe na base de datos).
     */
    public TipoActividade consultarTipoActividade(TipoActividade tipoActividade) {
        return this.fachadaBD.consultarTipoActividade(tipoActividade);
    }
}

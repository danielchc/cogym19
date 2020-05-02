package centrodeportivo.aplicacion.xestion;

import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.actividades.TipoActividade;
import centrodeportivo.aplicacion.obxectos.tipos.TipoResultados;
import centrodeportivo.baseDatos.FachadaBD;
import centrodeportivo.gui.FachadaGUI;

import java.util.ArrayList;

//Clase para todos os métodos relacionados coa xestión das actividades (tipos de actividades e actividades).
public class XestionTiposActividades {
    //Atributos: coma sempre nas clases de xestión as fachadas:
    private FachadaBD fachadaBD;
    private FachadaGUI fachadaGUI;

    //Constructor:
    public XestionTiposActividades(FachadaGUI fachadaGUI, FachadaBD fachadaBD){
        this.fachadaBD = fachadaBD;
        this.fachadaGUI = fachadaGUI;
    }

    public TipoResultados crearTipoActividade (TipoActividade tipoActividade) throws ExcepcionBD {
        if(!this.fachadaBD.comprobarExistencia(tipoActividade)){
            this.fachadaBD.crearTipoActividade(tipoActividade);
            //En caso de que se cree o tipo de actividade, enton devolvemos un indicador do remate correcto:
            return TipoResultados.correcto;
        } else {
            //Se xa existe un tipo de actividade igual almacenado, entón devolvemos que houbo problemas nese sentido:
            return TipoResultados.datoExiste;
        }
    }

    public TipoResultados modificarTipoActividade(TipoActividade tipoActividade) throws ExcepcionBD {
        //Hai que verificar primeiro que non exista outro tipo de actividade existente co nome pasado
        //(que non sexa este, claro):
        if(!this.fachadaBD.comprobarExistencia(tipoActividade)) {
            //Se non existe, modificamos o tipo:
            this.fachadaBD.modificarTipoActividade(tipoActividade);
            //Se se rematou correctamente, devólvese o enumerado indicador de remate correcto:
            return TipoResultados.correcto;
        } else {
            //Se hai un tipo de actividade DISTINTO DESTE co mesmo nome que se quere modificar, devólvese
            //que xa existe:
            return TipoResultados.datoExiste;
        }
    }

    public TipoResultados eliminarTipoActividade(TipoActividade tipoActividade) throws ExcepcionBD {
        if(!this.fachadaBD.tenActividades(tipoActividade)){
            this.fachadaBD.eliminarTipoActividade(tipoActividade);
            //Se se chegou a este punto, entón devólvese unha confirmación do remate correcto:
            return TipoResultados.correcto;
        } else {
            //Se o tipo ten actividades asociadas, entón devolvemos que hai un problema cos restrict para o borrado:
            return TipoResultados.referenciaRestrict;
        }

    }

    /**
     * Método que ofrece un conxunto de tipos de actividade contidos na base de datos.
     * @param tipoActividade O tipo de actividade modelo co que se vai a facer a búsqueda.
     * @return Se o tipo de actividade é null, devolveranse todos os tipos de actividades rexistrados, en caso contrario
     * todos os tipos de actividade que teñan coincidencia co nome que ten o tipo pasado como argumento.
     */
    public ArrayList<TipoActividade> buscarTiposActividades(TipoActividade tipoActividade){
        return this.fachadaBD.buscarTiposActividades(tipoActividade);
    }

    /**
     * Método que nos permite consultar un tipo de actividade a partir do código do tipo pasado como argumento.
     * @param tipoActividade O tipo de actividade do que se collerá o código para a consulta.
     * @return O tipo de actividade co código buscado (se todavía existe na base de datos).
     */
    public TipoActividade consultarTipoActividade(TipoActividade tipoActividade){
        return this.fachadaBD.consultarTipoActividade(tipoActividade);
    }
}

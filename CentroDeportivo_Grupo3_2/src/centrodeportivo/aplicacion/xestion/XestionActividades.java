package centrodeportivo.aplicacion.xestion;

import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.actividades.TipoActividade;
import centrodeportivo.aplicacion.obxectos.tipos.TipoResultados;
import centrodeportivo.baseDatos.FachadaBD;
import centrodeportivo.gui.FachadaGUI;

import java.util.ArrayList;

//Clase para todos os métodos relacionados coa xestión das actividades (tipos de actividades e actividades).
public class XestionActividades {
    //Atributos: coma sempre nas clases de xestión as fachadas:
    private FachadaBD fachadaBD;
    private FachadaGUI fachadaGUI;

    //Constructor:
    public XestionActividades (FachadaGUI fachadaGUI, FachadaBD fachadaBD){
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

    public ArrayList<TipoActividade> listarTiposActividades(){
        return this.fachadaBD.listarTiposActividades();
    }

    public ArrayList<TipoActividade> buscarTiposActividades(TipoActividade tipoActividade){
        return this.fachadaBD.buscarTiposActividades(tipoActividade);
    }
}

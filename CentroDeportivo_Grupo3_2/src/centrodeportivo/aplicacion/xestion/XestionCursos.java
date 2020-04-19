package centrodeportivo.aplicacion.xestion;

import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.actividades.Curso;
import centrodeportivo.aplicacion.obxectos.tipos.TipoResultados;
import centrodeportivo.baseDatos.FachadaBD;
import centrodeportivo.gui.FachadaGUI;

import java.sql.Date;
import java.sql.Timestamp;

public class XestionCursos {
    private FachadaGUI fachadaGUI;
    private FachadaBD fachadaBD;

    public XestionCursos(FachadaGUI fachadaGUI, FachadaBD fachadaBD){
        this.fachadaBD = fachadaBD;
        this.fachadaGUI = fachadaGUI;
    }

    public TipoResultados rexistrarCurso(Curso curso) throws ExcepcionBD {
        //Primeiro, verificamos que non exista xa un curso co mesmo nome:
        if(!fachadaBD.comprobarExistencia(curso)){
            //Se non existe, xa poderemos rexistrar o curso:
            fachadaBD.rexistrarCurso(curso);
            //Se chegamos ata este punto, remataríase correctamente:
            return TipoResultados.correcto;
        } else {
            //Se existe, entón haberá que devolver o enum correspondente:
            return TipoResultados.datoExiste;
        }
    }

    /**
     *
     * @param curso
     * @return
     * @throws ExcepcionBD
     */
    public TipoResultados modificarCurso(Curso curso) throws ExcepcionBD {
        //Temos que verificar se o nome pertence a outro curso rexistrado na base de datos:
        if(!fachadaBD.comprobarExistencia(curso)){
            //Se non existe, como antes, poderemos facer a modificación:
            fachadaBD.modificarCurso(curso);
            //Chegados a este punto, remataríase correctamente:
            return TipoResultados.correcto;
        } else {
            //Se existe, non se pode actualizar:
            return TipoResultados.datoExiste;
        }
    }

    public TipoResultados cancelarCurso(Curso curso) throws ExcepcionBD {
        //Para cancelar un curso, hai que comprobar se o curso está xa comezado ou se ten participantes:
        //Non comezado -> Data de inicio maior á data actual
        if(curso.getDataInicio().compareTo(new Date(System.currentTimeMillis())) > 0 || !fachadaBD.tenParticipantes(curso)){
            //Nestes dous casos, poderase proceder ao borrado:
            fachadaBD.cancelarCurso(curso);
            //Se chegamos a este punto, indicamos que se remata correctamente:
            return TipoResultados.correcto;
        } else {
            //Se non, haberá un erro no borrado:
            return TipoResultados.incoherenciaBorrado;
        }
    }
}

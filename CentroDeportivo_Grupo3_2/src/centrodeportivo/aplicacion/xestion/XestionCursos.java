package centrodeportivo.aplicacion.xestion;

import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.actividades.Curso;
import centrodeportivo.aplicacion.obxectos.tipos.TipoResultados;
import centrodeportivo.baseDatos.FachadaBD;
import centrodeportivo.gui.FachadaGUI;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

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
        //Comezamos comprobando se comezou o curso, posto que nese caso xa non deixaremos modificar a información principal:
        if(curso.getDataInicio().compareTo(new Date(System.currentTimeMillis())) > 0) {
            //Temos que verificar se o nome pertence a outro curso rexistrado na base de datos:
            if (!fachadaBD.comprobarExistencia(curso)) {
                //Se non existe, como antes, poderemos facer a modificación:
                fachadaBD.modificarCurso(curso);
                //Chegados a este punto, remataríase correctamente:
                return TipoResultados.correcto;
            } else {
                //Se existe, non se pode actualizar:
                return TipoResultados.datoExiste;
            }
        } else {
            //Se o curso xa comezou, entón non deixamos modificar eses datos.
            return TipoResultados.foraTempo;
        }
    }

    public TipoResultados cancelarCurso(Curso curso) throws ExcepcionBD {
        //Para cancelar un curso, hai que comprobar se o curso está xa comezado ou se ten participantes:
        //Non comezado -> Data de inicio maior á data actual
        if(curso.getDataInicio() == null || curso.getDataInicio().before(new Date(System.currentTimeMillis()))
                || !fachadaBD.tenParticipantes(curso)){
            //Nestes dous casos, poderase proceder ao borrado:
            fachadaBD.cancelarCurso(curso);
            //Se chegamos a este punto, indicamos que se remata correctamente:
            return TipoResultados.correcto;
        } else {
            //Se non, haberá un erro no borrado:
            return TipoResultados.incoherenciaBorrado;
        }
    }

    public TipoResultados activarCurso(Curso curso) throws ExcepcionBD {
        //Primeiro haberá que comprobar que cumple as condicións para poder ser activado:
        if(fachadaBD.listoParaActivar(curso)){
            //Neste caso faremos a activación:
            fachadaBD.abrirCurso(curso);
            //Se se chega a este punto, devolvemos un tipo de resultado correcto:
            return TipoResultados.correcto;
        } else {
            //Noutro caso, haberá un erro:
            return TipoResultados.sitIncoherente;
        }
    }

    public ArrayList<Curso> consultarCursos(Curso curso) {
        return fachadaBD.consultarCursos(curso);
    }

    public Curso recuperarDatosCurso(Curso curso){
        return fachadaBD.recuperarDatosCurso(curso);
    }

    public Curso informeCurso(Curso curso){
        return fachadaBD.informeCurso(curso);
    }
}

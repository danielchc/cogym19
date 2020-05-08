package centrodeportivo.aplicacion.xestion;

import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.Mensaxe;
import centrodeportivo.aplicacion.obxectos.actividades.Curso;
import centrodeportivo.aplicacion.obxectos.tipos.TipoResultados;
import centrodeportivo.baseDatos.FachadaBD;
import centrodeportivo.gui.FachadaGUI;

import java.sql.Date;
import java.util.ArrayList;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Víctor Barreiro
 * Clase na que se levarán a cabo todas as xestións que teñan que ver cos cursos do centro deportivo.
 */
public class XestionCursos {
    /**
     * Coma en todas as clases de xestión, gardamos como atributos referencias ás outras fachadas da aplicación: a da
     * base de datos e á da GUI.
     */
    private FachadaGUI fachadaGUI;
    private FachadaBD fachadaBD;

    /**
     * Constructor da clase de xestión de cursos:
     * @param fachadaGUI A referencia á fachada da interface gráfica.
     * @param fachadaBD A referencia á fachada da parte da base de datos.
     */
    public XestionCursos(FachadaGUI fachadaGUI, FachadaBD fachadaBD){
        this.fachadaBD = fachadaBD;
        this.fachadaGUI = fachadaGUI;
    }

    /**
     * Método que nos permite introducir os datos dun novo curso na base de datos.
     * @param curso O curso a insertar
     * @return O resultado da operación
     * @throws ExcepcionBD Excepción asociada a problemas que puideron ocorrer na base de datos.
     */
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
     * Método que nos permite realizar modificacións na información xeral dun curso determinado.
     * @param curso O curso do que se quere modificar a información, cos datos modificados.
     * @return O resultado da operación realizada.
     * @throws ExcepcionBD Excepción asociada a problemas producidos coa base de datos.
     */
    public TipoResultados modificarCurso(Curso curso) throws ExcepcionBD {
        //Comezamos comprobando se comezou o curso, posto que nese caso xa non deixaremos modificar a información principal:
        if(curso.getDataInicio() == null || curso.getDataInicio().after(new Date(System.currentTimeMillis()))) {
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

    /**
     * Método que nos permite cancelar un curso, e polo tanto borrar a súa información da base de datos.
     * @param curso O curso que se quere borrar.
     * @param mensaxe A mensaxe que se envía aos participantes polo borrado.
     * @return O resultado da operación
     * @throws ExcepcionBD Excepción asociada a problemas que poden ocorrer durante o borrado.
     */
    public TipoResultados cancelarCurso(Curso curso, Mensaxe mensaxe) throws ExcepcionBD {
        //Para cancelar un curso, hai que comprobar se o curso está xa comezado ou se ten participantes:
        //Non comezado -> Data de inicio maior á data actual
        if(curso.getDataInicio() == null || curso.getDataInicio().after(new Date(System.currentTimeMillis()))
                || !fachadaBD.tenParticipantes(curso)){
            //Nestes dous casos, poderase proceder ao borrado:
            fachadaBD.cancelarCurso(curso, mensaxe);
            //Se chegamos a este punto, indicamos que se remata correctamente:
            return TipoResultados.correcto;
        } else {
            //Se non, haberá un erro no borrado:
            return TipoResultados.incoherenciaBorrado;
        }
    }

    /**
     * Método que nos permite levar a cabo a activación dun curso:
     * @param curso Os datos do curso que se quere activar.
     * @return O resultado da operación
     * @throws ExcepcionBD Excepción asociada a problemas producidos na base de datos.
     */
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

    /**
     * Método que nos permite consultar os cursos que hai almacenados na base de datos.
     * @param curso Curso polo que se realiza a busca.
     * @return Se curso vale null, devolveranse todos os cursos, noutro caso, filtraranse polo nome do curso pasado.
     */
    public ArrayList<Curso> consultarCursos(Curso curso) {
        return fachadaBD.consultarCursos(curso);
    }

    /**
     * Método que nos permite recuperar datos máis concretos dun curso. Non só datos contidos na táboa de cursos,
     * máis información todavía.
     * @param curso Información do curso do que se queren recuperar os datos (o atributo importante é o código).
     * @return Datos completos do curso procurado.
     */
    public Curso recuperarDatosCurso(Curso curso){
        return fachadaBD.recuperarDatosCurso(curso);
    }


    /**
     * Método que nos permite recuperar información suficiente do curso como para elaborar o informe que ofrecer ao
     * usuario que o consulta.
     * @param curso Información do curso do que se queren recuperar os datos para o informe.
     * @return Datos completos do curso, incluíndo información adicional necesaria para a elaboración do informe.
     */
    public Curso informeCurso(Curso curso){
        return fachadaBD.informeCurso(curso);
    }

    /**
     * Método que nos permite consultar os cursos abertos que hai almacenados na base de datos.
     *
     * @param curso Curso polo que se realiza a busca.
     * @return Se curso vale null, devolveranse todos os cursos abertos, noutro caso, filtraranse polo nome do curso pasado.
     */
    public ArrayList<Curso> consultarCursosAbertos(Curso curso) {
        return fachadaBD.consultarCursosAbertos(curso);
    }
}

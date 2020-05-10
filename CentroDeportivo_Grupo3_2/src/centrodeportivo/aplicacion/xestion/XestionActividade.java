package centrodeportivo.aplicacion.xestion;

import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.Mensaxe;
import centrodeportivo.aplicacion.obxectos.actividades.Actividade;
import centrodeportivo.aplicacion.obxectos.actividades.TipoActividade;
import centrodeportivo.aplicacion.obxectos.tipos.TipoResultados;
import centrodeportivo.aplicacion.obxectos.usuarios.Persoal;
import centrodeportivo.aplicacion.obxectos.usuarios.Socio;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.baseDatos.FachadaBD;
import centrodeportivo.gui.FachadaGUI;

import javax.swing.*;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Víctor Barreiro
 * Esta clase é aquela na que se levarán a cabo tarefas de xestión na parte de aplicación relacionadas coas actividades.
 * Introduciremos aquí as comprobacións que se realizarán para cada caso.
 */
public class XestionActividade {
    /**
     * Atributos da xestión de actividades: basicamente son referencias ás demáis fachadas.
     */
    private FachadaGUI fachadaGUI;
    private FachadaBD fachadaBD;


    /**
     * Constructor da clase de xestión de actividades:
     *
     * @param fachadaGUI Referencia á fachada da interface gráfica.
     * @param fachadaBD  Referencia á fachada da parte da Base de datos.
     */
    public XestionActividade(FachadaGUI fachadaGUI, FachadaBD fachadaBD) {
        this.fachadaGUI = fachadaGUI;
        this.fachadaBD = fachadaBD;
    }


    /**
     * Método que permite engadir unha actividade na base de datos.
     *
     * @param actividade Actividade que se desexa engadir na base de datos.
     * @return Devolve un enum que tipifica os posibles erroes durante a execución do método.
     * @throws ExcepcionBD Excepción asociada a problemas ao tentar facer a inserción sobre a base de datos.
     */
    public TipoResultados EngadirActividade(Actividade actividade) throws ExcepcionBD {
        // Se a actividade non existe, dase de alta:
        if (fachadaBD.EProfesorActivo(actividade.getProfesor())) {
            if (!fachadaBD.horarioOcupadoActividade(null, actividade)) {
                fachadaBD.EngadirActividade(actividade);
                // Se se completa a execución do método sen lanzamento de excepcións, devolvemos que foi ben:
                return TipoResultados.correcto;
            } else {
                return TipoResultados.foraTempo;
            }
        } else
            return TipoResultados.sitIncoherente;
    }

    /**
     * Método que permite modificar os datos dunha actividade.
     *
     * @param actVella Datos da actividade actualmente.
     * @param actNova  Datos da actividade polos que se desexan cambiar os actuais.
     * @return Devolve un enum que tipifica os posibles erroes durante a execución do método.
     * @throws ExcepcionBD Excepción asociada a problemas ó tentar facer a modificación na base de datos.
     */
    public TipoResultados modificarActividade(Actividade actVella, Actividade actNova) throws ExcepcionBD {
        if(fachadaBD.EProfesorActivo(actNova.getProfesor())) {
            if (!fachadaBD.horarioOcupadoActividade(actVella, actNova)) {
                fachadaBD.modificarActividade(actVella, actNova);
                // Se se completa a execución do método sen lanzamento de excepcións, devolvemos que foi ben:
                return TipoResultados.correcto;
            } else {
                return TipoResultados.datoExiste;
            }
        } else {
            return TipoResultados.sitIncoherente;
        }
    }

    /**
     * Método que permite eliminar os datos dunha actividade da base de datos.
     *
     * @param actividade Actividade que se desexa eliminar da base de datos.
     * @param mensaxe    Mensaxe que se enviara os usuarios que estaban apuntados a dita actividade.
     * @return Devolve un enum que tipifica os posibles erroes durante a execución do método.
     * @throws ExcepcionBD Excepción asociada a problemas ó tentar borrar a actividade da base de datos.
     */
    public TipoResultados borrarActividade(Actividade actividade, Mensaxe mensaxe) throws ExcepcionBD {
        // Só se pode borrar unha actividade se inda non comezou:
        if (actividade.getData().after(new Timestamp(System.currentTimeMillis()))) {
            fachadaBD.borrarActividade(actividade, mensaxe);
            // Se se completou o método correctamente, devolvemos o enum que indica corrección:
            return TipoResultados.correcto;
        } else {
            // Se a actividade se realizou xa, ou se está niso, tense unha situación incoherente:
            return TipoResultados.sitIncoherente;
        }
    }

    /**
     * Método que permite anotar un usuario a unha actividade como participante.
     *
     * @param actividade Actividade a que se desexa apuntar o usuario.
     * @param usuario    Usuario que se quer apuntar a dita actividade.
     * @return Devolve un enum que tipifica os posibles erroes durante a execución do método.
     * @throws ExcepcionBD Excepción asociada a inserción dunha nova tupla na táboa de realización de actividade.
     */
    public TipoResultados apuntarseActividade(Actividade actividade, Usuario usuario) throws ExcepcionBD {
        // Se a actividade non existe, non estas apuntado e non é maxio o aforo podese apuntar, apuntamos o usuario na actividade::
        if (!fachadaBD.estarApuntado(actividade, usuario)) {
            if (fachadaBD.NonEMaximoAforoActividade(actividade)) {
                fachadaBD.apuntarseActividade(actividade, usuario);
                // Se se completa a execución do método sen lanzamento de excepcións, devolvemos que foi ben:
                return TipoResultados.correcto;
            } else
                return TipoResultados.sitIncoherente;
        } else {
            return TipoResultados.datoExiste;
        }
    }

    /**
     * Método que permite desapuntar un usuario dunha actividade.
     *
     * @param actividade Actividade a que se desexa desapuntar dito usuario.
     * @param usuario    Usuario que se desexa desapuntar de dita actividade.
     * @return Devolve un enum que tipifica os posibles erroes durante a execución do método.
     * @throws ExcepcionBD Excepción asociada o borrado dunha tupla na táboa de datos de realización de actividade.
     */
    public TipoResultados borrarseDeActividade(Actividade actividade, Usuario usuario) throws ExcepcionBD {
        // Se a Actividade existe, desapuntamos o usuario:
        if (fachadaBD.estarApuntado(actividade, usuario)) {
            fachadaBD.borrarseDeActividade(actividade, usuario);
            // Se se completa a execución do método sen lanzamento de excepcións, devolvemos que foi ben:
            return TipoResultados.correcto;
        } else {
            return TipoResultados.sitIncoherente;
        }
    }

    /**
     * Método que permite listar as actividades filtrándoas a través dunha actividade modelo.
     *
     * @param actividade Actividade modelo que se empregará apra dito filtrado.
     * @return Retorna un ArrayList das actividades que cumpren dita condición ou, no caso de ser null,
     * un ArrayList con todas as posibles actividades.
     */
    public ArrayList<Actividade> buscarActividade(Actividade actividade) {
        return fachadaBD.buscarActividade(actividade);
    }

    /**
     * Método que permite listar os profesores ca posibilidade de listar en función dun tipo de actividade.
     *
     * @param tipoactividade Tipo de actividade para a que se comprobarán, de non ser nula, os profesores que hai.
     * @return Devolve un ArrayList cos profesores que cumpran ditas condicións de filtrado.
     */
    public ArrayList<Persoal> buscarProfesores(TipoActividade tipoactividade) {
        return fachadaBD.buscarProfesores(tipoactividade);
    }

    /**
     * Método que permite listar as actividades nas que participa certo usuario.
     *
     * @param actividade Actividade modelo que se empregará para realizar un filtrado.
     * @param usuario    Usuario que se desexa asegurar que participa nas actividades.
     * @return Devolve un ArrayList que compre cas condicións de filtrado en función do usuario pasado.
     */
    public ArrayList<Actividade> buscarActividadeParticipa(Actividade actividade, Usuario usuario) {
        if (usuario != null)
            return fachadaBD.buscarActividadeParticipa(actividade, usuario);
        else
            return null;
    }

    /**
     * Método que permite listar as actividades onde NON participa certo usuario.
     *
     * @param actividade Actividade modelo que se empregará para realizar un filtrado.
     * @param usuario    Usuario que se desexa asegurar que NON particida nas actividades
     * @return Devolve un ArrayList que compre cas condicións de filtrado da actividade en función do usuario pasado.
     */
    public ArrayList<Actividade> buscarActividadeNONParticipa(Actividade actividade, Usuario usuario) {
        if (usuario != null)
            return fachadaBD.buscarActividadeNONParticipa(actividade, usuario);
        else
            return null;
    }

    /**
     * Método que permite valorar por certo usuario unha actividade na que participará.
     *
     * @param valoracion Puntuación que lle será asignada a dita actividade.
     * @param actividade Actividade que se desexa valorar.
     * @param usuario    Usuario que esta a valorar dita actividade.
     * @throws ExcepcionBD Excepción asociada a actualización na base de datos.
     */
    public TipoResultados valorarActividade(Integer valoracion, Actividade actividade, Usuario usuario) throws ExcepcionBD {
        if (actividade != null && usuario != null) {
            if ((actividade.getData()).before(new Timestamp(System.currentTimeMillis()))) {
                fachadaBD.valorarActividade(valoracion, actividade, usuario);
                return TipoResultados.correcto;
            }
            return TipoResultados.foraTempo;
        } else {
            return TipoResultados.sitIncoherente;
        }
    }

    /**
     * Método que comproba se unha actividade foi valorada por un usuario.
     *
     * @param actividade Actividade que se comproba se foi valorada.
     * @param usuario    Usuario que se comproba se a valorou.
     * @return Devolve true se a actividade xa foi valorada.
     */
    public boolean isValorada(Actividade actividade, Usuario usuario) {
        return fachadaBD.isValorada(actividade, usuario);
    }

    /**
     * Método que permite listar todos os participantes dunha actividade.
     *
     * @param actividade Actividade da que se queren listar os seus participantes.
     * @return Retorna un ArrayList con todos os participantes que estan apuntados na mesma.
     */
    public ArrayList<Socio> listarParticipantes(Actividade actividade) {
        return fachadaBD.listarParticipantes(actividade);
    }

    /**
     * Método que permite recuperar os datos dunha actividade da base de datos a partir das súas claves primarias.
     *
     * @param actividade Actividade da que se obteñen os datos para realizar a consulta en función dos mesmos.
     * @return Retorna a Actividade cos datos actualizados.
     */
    public Actividade recuperarActividade(Actividade actividade) {
        if (actividade != null) {
            return fachadaBD.recuperarActividade(actividade);
        } else {
            return null;
        }
    }

}

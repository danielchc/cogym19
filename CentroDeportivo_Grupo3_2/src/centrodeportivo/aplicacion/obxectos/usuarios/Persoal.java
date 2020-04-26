package centrodeportivo.aplicacion.obxectos.usuarios;

import centrodeportivo.aplicacion.obxectos.tipos.TipoUsuario;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;

import java.sql.Date;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Victor Barreiro
 * Clase que permite recoller os datos específicos do persoal do centro deportivo.
 */
public final class Persoal extends Usuario {

    /**
     * Atributos da clase.
     */
    private String NUSS; //Número da seguridade social.
    private boolean profesorActivo; //Indicador de que o persoal está identificado como profesor ou non.

    /**
     * Atributos adicionais, que xorden como contedores de datos CALCULADOS na base de datos.
     */
    private Float valoracion; //Valoración do persoal como profesor nalgún curso/actividade.

    /**
     * Construtor únicamente co login do usuario:
     * @param login O login do usuario
     */
    public Persoal (String login){
        super(login);
    }

    /**
     * Constructor que recibe somentes login e valoración: usámolo dende o DAO de cursos cando se recuperan as
     * valoracións dos profesores participantes.
     * @param login O login do usuario
     * @param valoracion Valoración dada a ese persoal nalgunha actividade na que foi profesor.
     */
    public Persoal (String login, Float valoracion){
        //Chamamos ao construtor da clase pai:
        super(login);
        //Asignamos o atributo correspondente a esta clase.
        this.valoracion = valoracion;
    }

    /**
     * Constructor que tolera todos os parámetros que dispón o usuario (salvo o das valoracións propio do persoal, que
     * só se require cando se recupera esa información particular da base de datos).
     * @param login Login de inicio de sesión
     * @param contrasinal Contrasinal que emprega o usuario para iniciar a sesión.
     * @param DNI DNI do usuario
     * @param nome Nome real dese usuario
     * @param dificultades Dificultades que posúa dito usuario
     * @param dataNacemento Data de nacemenro
     * @param numTelefono Número de teléfono
     * @param correoElectronico Correo electrónico do usuario.
     * @param IBANconta IBAN da conta bancaria do usuario
     * @param NUSS Número da seguridade social do usuario
     * @param profesorActivo Indicador de se o persoal é un profesor activo ou non.
     */
    public Persoal(String login, String contrasinal, String DNI, String nome, String dificultades, Date dataNacemento,
                   String numTelefono, String correoElectronico, String IBANconta, String NUSS, boolean profesorActivo) {
        //Chamada ao constructor da clase pai:
        super(login, contrasinal, DNI, nome, dificultades, dataNacemento, numTelefono, correoElectronico, IBANconta);
        //Asignamos os atributos propios desta clase filla.
        this.NUSS = NUSS;
        this.profesorActivo= profesorActivo;
        //Establecemos o valor do enumerado de cara á clase pai.
        if(this.profesorActivo) {
            super.setTipoUsuario(TipoUsuario.ProfesorActivo);
        } else {
            super.setTipoUsuario(TipoUsuario.Persoal);
        }
    }


    /**
     * Getter do NUSS do persoal
     * @return O número da seguridade social que ten ese persoal.
     */
    public String getNUSS() {
        return NUSS;
    }

    /**
     * Setter do NUSS do persoal
     * @param NUSS o NUSS a establecer para ese membro do persoal.
     */
    public void setNUSS(String NUSS) {
        this.NUSS = NUSS;
    }

    /**
     * Getter do atributo que indica se o persoal é un profesor activo neste momento.
     * @return profesorActivo, o indicador de se o persoal é profesor activo ou non.
     */
    public boolean getProfesorActivo(){
        return profesorActivo;
    }

    /**
     * Setter do atributo que indica se o persoal é un profesor activo neste momento.
     * @param profesorActivo Booleano que indica se ese persoal é profesor activo ou non.
     */
    public void setProfesorActivo(boolean profesorActivo){
        this.profesorActivo = profesorActivo;
    }

    /**
     * Getter do atributo da valoración dun persoal.
     * @return A valoración que ten almacenada actualmente dito persoal.
     */
    public Float getValoracion() {
        return valoracion;
    }

    /**
     * Setter do atributo da valoración dun persoal
     * @param valoracion A valoración que se lle quere outorgar a este membro do persoal.
     */
    public void setValoracion(Float valoracion) {
        this.valoracion = valoracion;
    }
}

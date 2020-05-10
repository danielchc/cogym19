package centrodeportivo.aplicacion.obxectos.actividades;

import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.aplicacion.obxectos.usuarios.Persoal;

import java.sql.Timestamp;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Victor Barreiro
 * <p>
 * Clase que almacenará a información recuperada da base de datos sobre as actividades.
 */
public class Actividade {
    /**
     * Atributos dunha actividade
     */
    private Timestamp data;
    private Curso curso;
    private String nome;
    private Float duracion;
    private Area area;
    private TipoActividade tipoActividade;
    private Persoal profesor;

    private Float valMedia;

    /**
     * Constructor recurperar o nome das activiades
     * @param nome
     */
    public Actividade(String nome) {
        this.nome = nome;
    }

    /**
     * Constructor coa clave primaria da activiade
     * @param data
     * @param area
     */
    public Actividade(Timestamp data, Area area) {
        this.data = data;
        this.area = area;
    }

    /**
     * Contructor Co nome e a clave primaria da activiadede
     * @param nome
     * @param area
     */
    public Actividade(String nome, Area area) {
        this.nome = nome;
        this.area = area;
    }

    /**
     *  Consutructor completo sen valMedia, atributo calculado
     * @param data
     * @param nome
     * @param duracion
     * @param area
     * @param tipoActividade
     * @param curso
     * @param profesor
     */
    public Actividade(Timestamp data, String nome, Float duracion, Area area, TipoActividade tipoActividade,
                      Curso curso, Persoal profesor) {
        this.data = data;
        this.nome = nome;
        this.duracion = duracion;
        this.area = area;
        this.tipoActividade = tipoActividade;
        this.profesor = profesor;
        this.curso = curso;
    }

    /**
     * Constructor completo de Actividade
     * @param data
     * @param nome
     * @param duracion
     * @param area
     * @param tipoActividade
     * @param curso
     * @param profesor
     * @param valMedia
     */
    public Actividade(Timestamp data, String nome, Float duracion, Area area, TipoActividade tipoActividade,
                      Curso curso, Persoal profesor, Float valMedia) {
        this(data, nome, duracion, area, tipoActividade, curso, profesor);
        this.valMedia = valMedia;
    }


    /**
     * Getters e setters
     */
    public Timestamp getData() {
        return data;
    }

    public void setData(Timestamp data) {
        this.data = data;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Float getDuracion() {
        return duracion;
    }

    public void setDuracion(Float duracion) {
        this.duracion = duracion;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public TipoActividade getTipoActividade() {
        return tipoActividade;
    }

    // Método necesario para a implementación das taboas en javafx
    public String getTipoActividadenome() {
        return tipoActividade.getNome();
    }

    public void setTipoActividade(TipoActividade tipoActividade) {
        this.tipoActividade = tipoActividade;
    }

    public Persoal getProfesor() {
        return profesor;
    }

    // Método necesario para a implementación das taboas en javafx
    public String getProfesornome() {
        return profesor.getNome();
    }

    public void setProfesor(Persoal profesor) {
        this.profesor = profesor;
    }

    public Curso getCurso() {
        return curso;
    }

    // Método necesario para a implementacion da taboa de actividades en javafx
    public String getCursonome() {
        return curso.getNome();
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Float getValMedia() {
        return valMedia;
    }

    public void setValMedia(Float valMedia) {
        this.valMedia = valMedia;
    }

    @Override
    public String toString() {
        return "Actividade{" +
                "data=" + data +
                ", curso=" + curso +
                ", nome='" + nome + '\'' +
                ", duracion=" + duracion +
                ", area=" + area +
                ", tipoActividade=" + tipoActividade +
                ", profesor=" + profesor +
                ", valMedia=" + valMedia +
                '}';
    }
}

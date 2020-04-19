package centrodeportivo.aplicacion.obxectos.actividades;

import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.aplicacion.obxectos.usuarios.Persoal;

import java.sql.Timestamp;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Victor Barreiro
 *
 * Clase que almacenará a información recuperada da base de datos sobre as actividades.
 */
public class Actividade {
    /**
     * Atributos dunha actividade
     */
    private Timestamp data;
    private Curso curso;
    private String nome;
    private float duracion;
    private Area area;
    private TipoActividade tipoActividade;
    private Persoal profesor;

    //Constructor
    public Actividade(Timestamp data, Area area){
        this.data = data;
        this.area = area;
    }

    public Actividade(Timestamp data, String nome, float duracion, Area area, TipoActividade tipoActividade, Curso curso, Persoal profesor){
        this.data = data;
        this.nome = nome;
        this.duracion = duracion;
        this.area = area;
        this.tipoActividade = tipoActividade;
        this.profesor = profesor;
        this.curso = curso;
    }

    //Getters e setters:
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

    public float getDuracion() {
        return duracion;
    }

    public void setDuracion(float duracion) {
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

    public void setTipoActividade(TipoActividade tipoActividade) {
        this.tipoActividade = tipoActividade;
    }

    public Persoal getProfesor() {
        return profesor;
    }

    public void setProfesor(Persoal profesor) {
        this.profesor = profesor;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
}

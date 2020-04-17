package centrodeportivo.aplicacion.obxectos.actividades;

import java.util.ArrayList;


/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Victor Barreiro
 *
 * Esta clase modela a información sobre os cursos almacenada na base de datos.
 */
public class Curso {
    /**
     * Atributos da clase:
     */
    private int codCurso;
    private String nome;
    private String descricion;
    private float duracion;
    private float prezo;
    private ArrayList<Actividade> actividades;

    //Constructores
    public Curso(String nome, String descricion, float duracion){
        this.nome = nome;
        this.descricion = descricion;
        this.duracion = duracion;
    }

    public Curso(int codCurso, String nome, String descricion, float prezo){
        this(nome, descricion, prezo);
        this.codCurso = codCurso;
    }

    //Getters e setters:
    public void setCodCurso(int codCurso){
        this.codCurso = codCurso;
    }

    public int getCodCurso(){
        return codCurso;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getNome(){
        return nome;
    }

    public void setDescricion(String descricion){
        this.descricion = descricion;
    }

    public String getDescricion(){
        return descricion;
    }

    public void setDuracion(float duracion){
        this.duracion = duracion;
    }

    public float getDuracion(){
        return duracion;
    }

    public void setPrezo(float prezo){
        this.prezo = prezo;
    }

    public float getPrezo(){
        return prezo;
    }

    public void setActividades(ArrayList<Actividade> actividades){
        this.actividades = actividades;
    }

    public ArrayList<Actividade> getActividades(){
        return this.actividades;
    }
}

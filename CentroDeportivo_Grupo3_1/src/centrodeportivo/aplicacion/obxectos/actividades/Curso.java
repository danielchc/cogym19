package centrodeportivo.aplicacion.obxectos.actividades;

import centrodeportivo.aplicacion.obxectos.usuarios.Socio;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public class Curso {

    /**
     * Atributos da clase Curso
     */
    private int codCurso;
    private String nome;
    private String descricion;
    private float prezo;

    /**
     * Contructor cos datos dun curso.
     * @param codCurso Código do curso
     * @param nome Nome do curso
     * @param descricion Descrición do curso.
     * @param prezo Prezo mensual do curso.
     */
    public Curso(int codCurso, String nome, String descricion, float prezo) {
        this.codCurso = codCurso;
        this.nome = nome;
        this.descricion = descricion;
        this.prezo = prezo;
    }

    /**
     * Constructor coa clave primaria.
     * @param codCurso Código do curso.
     */
    public Curso(int codCurso) {
        this.codCurso = codCurso;
    }


    /**
     * Getters e Setters
     */
    public int getCodCurso() {
        return codCurso;
    }

    public void setCodCurso(int codCurso) {
        this.codCurso = codCurso;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricion() {
        return descricion;
    }

    public void setDescricion(String descricion) {
        this.descricion = descricion;
    }

    public float getPrezo() {
        return prezo;
    }

    public void setPrezo(float prezo) {
        this.prezo = prezo;
    }


    /**
     * Equals e toString
     */
    @Override
    public String toString() {
        return "Nome: "+this.nome+
                ", Código: "+this.codCurso;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Curso){
            return ((Curso) o).getCodCurso() == this.codCurso;
        }
        return false;
    }
}

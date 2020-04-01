package centrodeportivo.aplicacion.obxectos.actividades;

import centrodeportivo.aplicacion.obxectos.usuarios.Socio;

import java.util.ArrayList;
import java.util.Objects;

public class Curso {
    private int codCurso;
    private String nome;
    private String descricion;
    private float prezo;
    private ArrayList<Socio> socios;
    private ArrayList<Actividade> actividades;

    public Curso(int codCurso, String nome, String descricion, float prezo) {
        this.codCurso = codCurso;
        this.nome = nome;
        this.descricion = descricion;
        this.prezo = prezo;
    }

    public Curso(int codCurso) {
        this.codCurso = codCurso;
    }

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

    public ArrayList<Socio> getSocios() {
        return socios;
    }

    public void setSocios(ArrayList<Socio> socios) {
        this.socios = socios;
    }

    public ArrayList<Actividade> getActividades() {
        return actividades;
    }

    public void setActividades(ArrayList<Actividade> actividades) {
        this.actividades = actividades;
    }

    @Override
    public String toString() {
        return "Curso{" +
                "codCurso=" + codCurso +
                ", nome='" + nome + '\'' +
                ", descricion='" + descricion + '\'' +
                ", prezo=" + prezo +
                ", socios=" + socios +
                ", actividades=" + actividades +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Curso){
            return ((Curso) o).getCodCurso() == this.codCurso;
        }
        return false;
    }
}

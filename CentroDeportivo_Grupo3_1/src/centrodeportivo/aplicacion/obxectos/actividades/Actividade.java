package centrodeportivo.aplicacion.obxectos.actividades;

import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.aplicacion.obxectos.usuarios.Persoal;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public class Actividade {
    private Timestamp data;
    private Area area;
    private TipoActividade tipoActividade;
    private Curso curso;
    private String nome;
    private float duracion;
    private Persoal profesor;

    public Actividade(Timestamp data, Area area) {
        this.data = data;
        this.area = area;
    }

    public Actividade(Timestamp data, Area area, TipoActividade tipoActividade, Curso curso, String nome, float duracion,Persoal profesor) {
        this.data = data;
        this.area = area;
        this.tipoActividade = tipoActividade;
        this.curso = curso;
        this.nome = nome;
        this.duracion = duracion;
        this.profesor=profesor;
    }

    public Timestamp getData() {
        return data;
    }

    public void setData(Timestamp data) {
        this.data = data;
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

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
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

    public Persoal getProfesor() {
        return profesor;
    }

    public void setProfesor(Persoal profesor) {
        this.profesor = profesor;
    }

    @Override
    public String toString() {
        Date date=new Date(this.data.getTime());
        return "Nome: "+this.nome+
                ", Lugar: "+this.area.getNome()+
                ", Data: "+date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actividade that = (Actividade) o;
        return Objects.equals(data, that.data) &&
                Objects.equals(area, that.area);
    }
}

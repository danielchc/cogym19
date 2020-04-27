package centrodeportivo.aplicacion.obxectos.tarifas;

import centrodeportivo.aplicacion.obxectos.actividades.Actividade;
import centrodeportivo.aplicacion.obxectos.actividades.Curso;
import centrodeportivo.aplicacion.obxectos.usuarios.Socio;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public final class Cuota {

    /**
     * Atributos da clase Cuota.
     */
    private Socio usuario;
    private Tarifa tarifa;
    private ArrayList<Actividade> actividadesMes;
    private ArrayList<Curso> cursosMes;
    private float prezoActividadesExtra;
    private float totalActividades;
    private float totalCursos;
    private float totalPrezo;


    /**
     * Constructor da clase cuota con todos os datos necesarios para empaquetalos dende a base de datos
     * ata a aplicación.
     *
     * @param usuario               Socio á que pertence a Cuota
     * @param tarifa                Tarifa asociada á cuota
     * @param prezoActividadesExtra Prezo a pagar por actividades extras
     * @param totalActividades      Prezo a pagar en total polas actividades realizadas
     * @param totalCursos           Prezo total a pagar polos cursos realizados
     * @param totalPrezo            Prezo total a pagar mensual
     * @param actividades           Actividades realizadas no mes actual
     * @param cursos                Cursos realizados no mes actual
     */
    public Cuota(Socio usuario, Tarifa tarifa, float prezoActividadesExtra, float totalActividades, float totalCursos, float totalPrezo, ArrayList<Actividade> actividades, ArrayList<Curso> cursos) {
        this.usuario = usuario;
        this.tarifa = tarifa;
        this.prezoActividadesExtra = prezoActividadesExtra;
        this.totalActividades = totalActividades;
        this.totalCursos = totalCursos;
        this.totalPrezo = totalPrezo;
        this.actividadesMes = actividades;
        this.cursosMes = cursos;
    }


    /**
     * Getters e Setters
     */
    public Socio getUsuario() {
        return usuario;
    }

    public void setUsuario(Socio usuario) {
        this.usuario = usuario;
    }

    public Tarifa getTarifa() {
        return tarifa;
    }

    public void setTarifa(Tarifa tarifa) {
        this.tarifa = tarifa;
    }

    public ArrayList<Actividade> getActividadesMes() {
        return actividadesMes;
    }

    public void setActividadesMes(ArrayList<Actividade> actividadesMes) {
        this.actividadesMes = actividadesMes;
    }

    public ArrayList<Curso> getCursosMes() {
        return cursosMes;
    }

    public void setCursosMes(ArrayList<Curso> cursosMes) {
        this.cursosMes = cursosMes;
    }

    public float getPrezoActividadesExtra() {
        return prezoActividadesExtra;
    }

    public void setPrezoActividadesExtra(float prezoActividadesExtra) {
        this.prezoActividadesExtra = prezoActividadesExtra;
    }

    public float getTotalActividades() {
        return totalActividades;
    }

    public void setTotalActividades(float totalActividades) {
        this.totalActividades = totalActividades;
    }

    public float getTotalCursos() {
        return totalCursos;
    }

    public void setTotalCursos(float totalCursos) {
        this.totalCursos = totalCursos;
    }

    public float getTotalPrezo() {
        return totalPrezo;
    }

    public void setTotalPrezo(float totalPrezo) {
        this.totalPrezo = totalPrezo;
    }


    /**
     * @return String coa información da Cuota
     */
    @Override
    public String toString() {
        return "Cuota{" +
                "usuario='" + usuario + '\'' +
                ", tarifa=" + tarifa +
                ", prezoActividadesExtra=" + prezoActividadesExtra +
                ", totalActividades=" + totalActividades +
                ", totalCursos=" + totalCursos +
                ", totalPrezo=" + totalPrezo +
                '}';
    }
}

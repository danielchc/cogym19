package centrodeportivo.aplicacion.obxectos.tarifas;

import java.util.ArrayList;
import java.util.Objects;

public final class Cuota {
    private String usuario;
    private Tarifa tarifa;
    //private ArrayList<Actividade> actividadesMes;
    //private ArrayList<Curso> cursosMes;
    private float prezoActividadesExtra;
    private float totalActividades;
    private float totalCursos;
    private float totalPrezo;

    socio-> pocha
    tarifa-> 1 pochisima
    realizarActividades
    realizarcursos


    public Cuota(String usuario, Tarifa tarifa, float prezoActividadesExtra, float totalActividades, float totalCursos, float totalPrezo) {
        this.usuario = usuario;
        this.tarifa = tarifa;
        this.prezoActividadesExtra = prezoActividadesExtra;
        this.totalActividades = totalActividades;
        this.totalCursos = totalCursos;
        this.totalPrezo = totalPrezo;
        //this.actividadesMes=new ArrayList<>();
        //this.cursosMes=new ArrayList<>();
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Tarifa getTarifa() {
        return tarifa;
    }

    public void setTarifa(Tarifa tarifa) {
        this.tarifa = tarifa;
    }

    /*public ArrayList<Actividade> getActividadesMes() {
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
    }*/

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

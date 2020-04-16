package centrodeportivo.aplicacion.obxectos.area;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Objects;



/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Víctor Barreiro
 *
 * Clase xeral para almacenar información de Area.
 * Incorporanse distintos construtres coa finalidade de que poida atender as distintas
 * finalidades de xestión, incluso cando a información recuperada da area non sexa completa.
 */
public class Area {
    private int aforoMaximo;
    private int codArea;
    private Date dataBaixa;
    private String descricion;
    private Instalacion instalacion;
    private ArrayList<Material> materiais;
    private String nome;


    public Area(int codArea, Instalacion instalacion) {
        this.codArea = codArea;
        this.instalacion = instalacion;
    }

    public Area(Instalacion instalacion, String nome, String descricion, int aforoMaximo) {
        this.instalacion = instalacion;
        this.nome = nome;
        this.descricion = descricion;
        this.aforoMaximo = aforoMaximo;
    }

    public Area(int codArea, Instalacion instalacion, String nome, String descricion, int aforoMaximo, Date dataBaixa) {
        this(instalacion, nome, descricion, aforoMaximo);
        this.codArea = codArea;
        this.dataBaixa = dataBaixa;
    }

    public Area(int aforoMaximo, int codArea, Date dataBaixa, String descricion, Instalacion instalacion, ArrayList<Material> materiais, String nome) {
        this.aforoMaximo = aforoMaximo;
        this.codArea = codArea;
        this.dataBaixa = dataBaixa;
        this.descricion = descricion;
        this.instalacion = instalacion;
        this.materiais = materiais;
        this.nome = nome;
    }

    //Getters e setters
    public int getAforoMaximo() {
        return aforoMaximo;
    }

    public void setAforoMaximo(int aforoMaximo) {
        this.aforoMaximo = aforoMaximo;
    }


    public int getCodArea() {
        return codArea;
    }

    public void setCodArea(int codArea) {
        this.codArea = codArea;
    }


    public Date getDataBaixa() {
        return dataBaixa;
    }

    public void setDataBaixa(Date dataBaixa) {
        this.dataBaixa = dataBaixa;
    }


    public String getDescricion() {
        return descricion;
    }

    public void setDescricion(String descricion) {
        this.descricion = descricion;
    }


    public Instalacion getInstalacion() {
        return instalacion;
    }

    public void setInstalacion(Instalacion instalacion) {
        this.instalacion = instalacion;
    }


    public ArrayList<Material> getMatrais() {
        return materiais;
    }

    public void setMateriais(ArrayList<Material> materiais) {
        this.materiais = materiais;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    @Override
    public String toString() {
        return "Area{" +
                "codArea=" + codArea +
                ", instalacion=" + instalacion +
                ", nome='" + nome + '\'' +
                ", descricion='" + descricion + '\'' +
                ", aforoMaximo=" + aforoMaximo +
                ", dataBaixa=" + dataBaixa +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Area area = (Area) o;
        return codArea == area.codArea &&
                Objects.equals(nome, area.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codArea, nome);
    }
}



package centrodeportivo.aplicacion.obxectos.area;

import centrodeportivo.aplicacion.obxectos.Material;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public class Area {

    /**
     * Atributos da clase Área
     */
    private int codArea;
    private Instalacion instalacion;
    private String nome;
    private String descricion;
    private int aforoMaximo;
    private Date dataBaixa;
    private ArrayList<Material> materiais;

    /**
     * Constructor coas claves primarias.
     * @param codArea código da área.
     * @param instalacion instalación na que está.
     */
    public Area(int codArea,Instalacion instalacion) {
        this.codArea = codArea;
        this.instalacion = instalacion;
    }

    /**
     * Constructor da clase área.
     * @param instalacion instalación na que está
     * @param nome nome da área
     * @param descricion descrición
     * @param aforoMaximo afóro máximo da área
     */
    public Area(Instalacion instalacion, String nome, String descricion, int aforoMaximo) {
        this.instalacion = instalacion;
        this.nome = nome;
        this.descricion = descricion;
        this.aforoMaximo = aforoMaximo;
    }

    /**
     * Constructor con todos os datos dunha área
     * @param codArea código da área.
     * @param instalacion instalación na que está
     * @param nome nome da área
     * @param descricion descrición
     * @param aforoMaximo afóro máximo da área
     * @param dataBaixa data de baixa da área.
     */
    public Area(int codArea,Instalacion instalacion, String nome, String descricion, int aforoMaximo,Date dataBaixa) {
        this(instalacion, nome, descricion, aforoMaximo);
        this.codArea = codArea;
        this.dataBaixa = dataBaixa;
    }


    /**
     * Getters e Setters.
     */
    public int getCodArea() {
        return codArea;
    }

    public void setCodArea(int codArea) {
        this.codArea = codArea;
    }

    public Instalacion getInstalacion() {
        return instalacion;
    }

    public void setInstalacion(Instalacion instalacion) {
        this.instalacion = instalacion;
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

    public int getAforoMaximo() {
        return aforoMaximo;
    }

    public void setAforoMaximo(int aforoMaximo) {
        this.aforoMaximo = aforoMaximo;
    }

    public Date getDataBaixa() {
        return dataBaixa;
    }

    public void setDataBaixa(Date dataBaixa) {
        this.dataBaixa = dataBaixa;
    }

    public ArrayList<Material> getMateriais() {
        return materiais;
    }

    public void setMateriais(ArrayList<Material> materiais) {
        this.materiais = materiais;
    }

    /**
     * Equals e toString
     */
    @Override
    public String toString() {
        return nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Area area = (Area) o;
        return ((this.codArea==((Area) o).codArea) && (this.instalacion.getCodInstalacion()==((Area) o).getInstalacion().getCodInstalacion()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(codArea, instalacion, nome);
    }
}

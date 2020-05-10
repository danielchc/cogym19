package centrodeportivo.aplicacion.obxectos.area;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Objects;


/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Víctor Barreiro
 * <p>
 * Clase xeral para almacenar información de Area.
 * Incorporanse distintos construtores coa finalidade de que poida atender as distintas
 * finalidades de xestión, incluso cando a información recuperada da area non sexa completa.
 */
public class Area {
    /**
     * Atributos dunha área
     */
    private int aforoMaximo;
    private int codArea;
    private Date dataBaixa;
    private String descricion;
    private Instalacion instalacion;
    private ArrayList<Material> materiais;
    private String nome;


    /**
     * Constructores
     */
    public Area(int codArea, Instalacion instalacion) {
        this.codArea = codArea;
        this.instalacion = instalacion;
    }

    public Area(String nome, int aforoMaximo) {
        this.nome = nome;
        this.aforoMaximo = aforoMaximo;
    }

    public Area(int codArea, Instalacion instalacion, String nome) {
        this.codArea = codArea;
        this.nome = nome;
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

    public Area(int codArea, Instalacion instalacion, String nome, String descricion) {
        this.codArea = codArea;
        this.instalacion = instalacion;
        this.nome = nome;
        this.descricion = descricion;
    }


    /**
     * Getters e setters
     */
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

    /**
     * Método que permite convertir unha area a cadea de caracteres.
     *
     * @return area representada en forma de caracteres polos seus campos.
     */
    @Override
    public String toString() {
        // Utilidade á hora de mostrar unha área concreta nas táboas.
        return nome + ", inst. " + instalacion.getCodInstalacion();
    }

    /**
     * Método que comproba se dúas áreas son iguais.
     *
     * @param o O obxecto a comparar coa área.
     * @return booleano que indica se a instalación coincide coa pasada como argumento.
     */
    @Override
    public boolean equals(Object o) {
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



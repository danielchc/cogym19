package centrodeportivo.aplicacion.area;

import java.sql.Date;

public class Area {
    private int codArea;
    private Instalacion instalacion;
    private String nome;
    private String descricion;
    private int aforoMaximo;
    private Date dataBaixa;

    public Area(Instalacion instalacion, String nome, String descricion, int aforoMaximo) {
        this.instalacion = instalacion;
        this.nome = nome;
        this.descricion = descricion;
        this.aforoMaximo = aforoMaximo;
    }
    public Area(int codArea,Instalacion instalacion, String nome, String descricion, int aforoMaximo,Date dataBaixa) {
        this(instalacion, nome, descricion, aforoMaximo);
        this.codArea = codArea;
        this.dataBaixa = dataBaixa;
    }

    /*clase de xoguete sei que non a tiña que facer eu pero faciame falta*/

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
}

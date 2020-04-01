package centrodeportivo.aplicacion.obxectos.area;

import java.sql.Date;

public class Material {
    private int codMaterial;
    private Area area;
    private String nome;
    private Date dataCompra;
    private double prezoCompra;

    public Material(int codMaterial) {
        this.codMaterial=codMaterial;
    }

    public Material(String nome, Date dataCompra, double prezoCompra) {
        this.nome = nome;
        this.dataCompra = dataCompra;
        this.prezoCompra = prezoCompra;
    }

    public Material(int codMaterial,Area area,String nome, Date dataCompra, double prezoCompra) {
        this(nome, dataCompra, prezoCompra);
        this.codMaterial=codMaterial;
        this.area=area;
    }

    public int getCodMaterial() {
        return codMaterial;
    }

    public void setCodMaterial(int codMaterial) {
        this.codMaterial = codMaterial;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(Date dataCompra) {
        this.dataCompra = dataCompra;
    }

    public double getPrezoCompra() {
        return prezoCompra;
    }

    public void setPrezoCompra(double prezoCompra) {
        this.prezoCompra = prezoCompra;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return "Material{" +
                "codMaterial=" + codMaterial +
                ", area=" + area +
                ", nome='" + nome + '\'' +
                ", dataCompra=" + dataCompra +
                ", prezoCompra=" + prezoCompra +
                '}';
    }
}

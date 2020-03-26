package centrodeportivo.aplicacion.obxectos.area;

import java.sql.Date;

public class Material {
    private int codMaterial;
    private String nome;
    private Date dataCompra;
    private double prezoCompra;

    public Material(String nome, Date dataCompra, double prezoCompra) {
        this.nome = nome;
        this.dataCompra = dataCompra;
        this.prezoCompra = prezoCompra;
    }
    public Material(int codMaterial,String nome, Date dataCompra, double prezoCompra) {
        this(nome, dataCompra, prezoCompra);
        this.codMaterial=codMaterial;
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

    @Override
    public String toString() {
        return "Material{" +
                "codMaterial=" + codMaterial +
                ", nome='" + nome + '\'' +
                ", dataCompra=" + dataCompra +
                ", prezoCompra=" + prezoCompra +
                '}';
    }
}

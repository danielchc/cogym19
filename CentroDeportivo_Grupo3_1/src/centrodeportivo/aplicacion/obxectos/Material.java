package centrodeportivo.aplicacion.obxectos;

import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;

import java.sql.Date;

public class Material {
    private Integer codMaterial;
    private Area area;
    private String nome;
    private Date dataCompra;
    private float prezoCompra;

    public Material(Integer codMaterial) {
        this.codMaterial = codMaterial;
    }

    public Material(Integer codMaterial, Area area, String nome, Date dataCompra, float prezoCompra) {
        this.codMaterial = codMaterial;
        this.area = area;
        this.nome = nome;
        this.dataCompra = dataCompra;
        this.prezoCompra = prezoCompra;
    }

    public Integer getCodMaterial() {
        return codMaterial;
    }

    public void setCodMaterial(Integer codMaterial) {
        this.codMaterial = codMaterial;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
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

    public float getPrezoCompra() {
        return prezoCompra;
    }

    public void setPrezoCompra(float prezoCompra) {
        this.prezoCompra = prezoCompra;
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

    public boolean equals(Object o){
        if(o instanceof Material){
            return ((Material) o).getCodMaterial().equals(this.codMaterial);
        }
        return false;
    }
}

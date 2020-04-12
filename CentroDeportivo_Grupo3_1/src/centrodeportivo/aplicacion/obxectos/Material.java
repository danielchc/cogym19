package centrodeportivo.aplicacion.obxectos;

import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;

import java.sql.Date;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public class Material {

    /**
     * Atributos da clase material.
     */
    private Integer codTipoMaterial;
    private Integer codMaterial;
    private Area area;
    private String estado;
    private Date dataCompra;
    private float prezoCompra;

    /**
     * Constructor coa clave primaria
     * @param codMaterial código do material.
     * @param codTipoMaterial código do tipo de material
     */
    public Material(Integer codMaterial, Integer codTipoMaterial) {
        this.codMaterial = codMaterial;
        this.codTipoMaterial=codTipoMaterial;
    }

    /**
     * Constructor cos datos do material
     * @param codMaterial código do material.
     * @param codTipoMaterial código do tipo de material
     * @param area Área na que está
     * @param estado Estado no que se encontra
     * @param dataCompra Data de compra
     * @param prezoCompra Prezo de compra
     */
    public Material(Integer codMaterial, Integer codTipoMaterial, Area area, String estado, Date dataCompra, float prezoCompra) {
        this.codMaterial = codMaterial;
        this.codTipoMaterial=codTipoMaterial;
        this.area = area;
        this.estado = estado;
        this.dataCompra = dataCompra;
        this.prezoCompra = prezoCompra;
    }


    /**
     * Getters e Setters.
     */
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


    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    /**
     * Equals e toString
     */
    @Override
    public String toString() {
        return "Material{" +
                "codMaterial=" + codMaterial +
                ", area=" + area +
                ", estado='" + estado + '\'' +
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

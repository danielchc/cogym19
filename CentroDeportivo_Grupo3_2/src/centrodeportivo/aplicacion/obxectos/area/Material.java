package centrodeportivo.aplicacion.obxectos.area;

import java.sql.Date;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Victor Barreiro
 * Clase que almacenará información sobre o material
 */
public class Material {


    // Atributos
    private int codMaterial;
    private TipoMaterial tipoMaterial;
    private Area area;
    private String estado;
    private Date dataCompra;
    private float prezoCompra;


    // Constructores:
    // Atributos not null sen código
    public Material(TipoMaterial tipoMaterial, Area area, String estado) {
        this.tipoMaterial = tipoMaterial;
        this.area = area;
        this.estado = estado;
    }

    // Atributos not null máis o código
    public Material(int codMaterial, TipoMaterial tipoMaterial, Area area, String estado) {
        this.codMaterial = codMaterial;
        this.tipoMaterial = tipoMaterial;
        this.area = area;
        this.estado = estado;
    }

    // Atributos not null + atributos que poden ser null con código
    public Material(int codMaterial, TipoMaterial tipoMaterial, Area area, String estado, Date dataCompra, float prezoCompra) {
        this(codMaterial, tipoMaterial, area, estado);
        this.dataCompra = dataCompra;
        this.prezoCompra = prezoCompra;
    }


    // Getters e setters

    public void setCodMaterial(int codMaterial) {
        this.codMaterial = codMaterial;
    }

    public void setTipoMaterial(TipoMaterial tipoMaterial) {
        this.tipoMaterial = tipoMaterial;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setDataCompra(Date dataCompra) {
        this.dataCompra = dataCompra;
    }

    public void setPrezoCompra(float prezoCompra) {
        this.prezoCompra = prezoCompra;
    }

    public int getCodMaterial() {
        return codMaterial;
    }

    public TipoMaterial getTipoMaterial() {
        return tipoMaterial;
    }

    public Area getArea() {
        return area;
    }

    public String getEstado() {
        return estado;
    }

    public Date getDataCompra() {
        return dataCompra;
    }

    public float getPrezoCompra() {
        return prezoCompra;
    }


    // Outros métodos
    public Instalacion getInstalacion() {
        return area.getInstalacion();
    }

    /*@Override
    public String toString() {
        return "Material{" +
                "codMaterial=" + codMaterial +
                ", area=" + area +
                ", nome='" + nome + '\'' +
                ", dataCompra=" + dataCompra +
                ", prezoCompra=" + prezoCompra +
                '}';
    }*/

}

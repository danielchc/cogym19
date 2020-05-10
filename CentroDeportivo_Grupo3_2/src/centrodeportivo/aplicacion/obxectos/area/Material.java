package centrodeportivo.aplicacion.obxectos.area;

import java.sql.Date;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Victor Barreiro
 * <p>
 * Clase que representará os materias da base de datos
 */
public class Material {


    /**
     * Atributos da clase
     */
    private int codMaterial;  // Codigo do material
    private TipoMaterial tipoMaterial;  // Tipo de material o que pertence
    private Area area;  // Area na que se atopa
    private String estado;  // Estado no que se encontra
    private Date dataCompra;  // Data do día no que se comprou
    Instalacion instalacion;  // Instalación onde se atopa o material, debe ser a mesma que a da área
    private float prezoCompra;  // Prezo polo que se comprou dito material


    /**
     * Constructores
     */
    // Atributos non null sen o código
    public Material(TipoMaterial tipoMaterial, Area area, String estado) {
        this.tipoMaterial = tipoMaterial;
        this.area = area;
        this.estado = estado;
    }

    // Atributos non null máis o código
    public Material(int codMaterial, TipoMaterial tipoMaterial, Area area, String estado) {
        this.codMaterial = codMaterial;
        this.tipoMaterial = tipoMaterial;
        this.area = area;
        this.estado = estado;
    }

    // Atributos not null + atributos que poden ser null con código
    public Material(int codMaterial, TipoMaterial tipoMaterial, Area area, Instalacion instalacion, String estado, Date dataCompra, float prezoCompra) {
        this(codMaterial, tipoMaterial, area, estado);
        this.instalacion = instalacion;
        this.dataCompra = dataCompra;
        this.prezoCompra = prezoCompra;
    }

    // Atributos sen codigo
    public Material(TipoMaterial tipoMaterial, Area area, Instalacion instalacion, String estado, Date dataCompra, float prezoCompra) {
        this(tipoMaterial, area, estado);
        this.instalacion = instalacion;
        this.dataCompra = dataCompra;
        this.prezoCompra = prezoCompra;
    }

    // Atributos sen codigo e sen prezo
    public Material(TipoMaterial tipoMaterial, Area area, Instalacion instalacion, String estado, Date dataCompra) {
        this(tipoMaterial, area, estado);
        this.instalacion = instalacion;
        this.dataCompra = dataCompra;
    }


    // Atributos con código e sen prezo
    public Material(int codMaterial, TipoMaterial tipoMaterial, Area area, Instalacion instalacion, String estado, Date dataCompra) {
        this(codMaterial, tipoMaterial, area, estado);
        this.instalacion = instalacion;
        this.dataCompra = dataCompra;
    }

    // Tipo de material o que pertence e ubicación onde se atopa (area e instalación)
    public Material(TipoMaterial tipoMaterial, Area area, Instalacion instalacion) {
        this.tipoMaterial = tipoMaterial;
        this.area = area;
        this.instalacion = instalacion;
    }


    /**
     * Getters e setters
     */
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

    public void setInstalacion(Instalacion instalacion) {
        this.getArea().setInstalacion(instalacion);
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

    public String getNomeArea() {
        return area.getNome();
    }

    public String getNomeTipoMaterial() {
        return tipoMaterial.getNome();
    }

    public String getNomeInstalacion() {
        return area.getInstalacion().getNome();
    }

    public Instalacion getInstalacion() {
        return instalacion;
    }
}

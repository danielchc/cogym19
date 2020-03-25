package aplicacion;

import java.sql.Date;

public class Incidencia {
    private TipoIncidencia tipoIncidencia;
    private int numero;
    private Usuario usuario;
    private String descripcion;
    private String comentarioResolucion;
    private Date dataFalla;
    private Date dataResolucion;
    private float custoReparacion;
    private int area;
    //private Area area;
    private int material;
    //private Material material;


    public Incidencia(TipoIncidencia tipoIncidencia, int numero, Usuario usuario, String descripcion,int area) {
        this(tipoIncidencia, numero, usuario, descripcion);
        this.area=area;
    }

    /*public Incidencia(TipoIncidencia tipoIncidencia, int numero, Usuario usuario, String descripcion,int material) {
        this(tipoIncidencia, numero, usuario, descripcion);
        this.material=material;
    }*/

    public Incidencia(TipoIncidencia tipoIncidencia, int numero, Usuario usuario, String descripcion) {
        this.tipoIncidencia = tipoIncidencia;
        this.numero = numero;
        this.usuario = usuario;
        this.descripcion = descripcion;
    }

    public TipoIncidencia getTipoIncidencia() {
        return tipoIncidencia;
    }

    public void setTipoIncidencia(TipoIncidencia tipoIncidencia) {
        this.tipoIncidencia = tipoIncidencia;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getComentarioResolucion() {
        return comentarioResolucion;
    }

    public void setComentarioResolucion(String comentarioResolucion) {
        this.comentarioResolucion = comentarioResolucion;
    }

    public Date getDataFalla() {
        return dataFalla;
    }

    public void setDataFalla(Date dataFalla) {
        this.dataFalla = dataFalla;
    }

    public Date getDataResolucion() {
        return dataResolucion;
    }

    public void setDataResolucion(Date dataResolucion) {
        this.dataResolucion = dataResolucion;
    }

    public float getCustoReparacion() {
        return custoReparacion;
    }

    public void setCustoReparacion(float custoReparacion) {
        this.custoReparacion = custoReparacion;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public int getMaterial() {
        return material;
    }

    public void setMaterial(int material) {
        this.material = material;
    }
}

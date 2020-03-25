package aplicacion;

import java.sql.Date;

public class Incidencia {
    private TipoIncidencia tipoIncidencia;
    private int numero;
    private String usuario;
    private String descricion;
    private String comentarioResolucion;
    private Date dataFalla;
    private Date dataResolucion;
    private float custoReparacion;
    private int area;
    private int instalacion;
    //private Area area;
    private int material;
    //private Material material;

    public Incidencia(TipoIncidencia tipoIncidencia, String usuario, String descricion) {
        this.tipoIncidencia = tipoIncidencia;
        this.usuario = usuario;
        this.descricion = descricion;
    }

    public Incidencia(TipoIncidencia tipoIncidencia,String usuario, String descripcion,int area,int instalacion) {
        this(tipoIncidencia, usuario, descripcion);
        this.area=area;
        this.instalacion=instalacion;
    }

    public Incidencia(TipoIncidencia tipoIncidencia, String usuario, String descripcion,int material) {
        this(tipoIncidencia, usuario, descripcion);
        this.material=material;
    }


    public Incidencia(TipoIncidencia tipoIncidencia, int numero, String usuario, String descripcion) {
        this(tipoIncidencia, usuario, descripcion);
        this.numero = numero;
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getDescricion() {
        return descricion;
    }

    public void setDescricion(String descricion) {
        this.descricion = descricion;
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

    public int getInstalacion() {
        return instalacion;
    }

    public void setInstalacion(int instalacion) {
        this.instalacion = instalacion;
    }

    public int getMaterial() {
        return material;
    }

    public void setMaterial(int material) {
        this.material = material;
    }
}

package centrodeportivo.aplicacion.obxectos;

import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.aplicacion.obxectos.tipos.TipoIncidencia;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;

import java.sql.Date;

public final class Incidencia {
    private TipoIncidencia tipoIncidencia;
    private int numero;
    private Usuario usuario;
    private String descricion;
    private String comentarioResolucion;
    private Date dataFalla;
    private Date dataResolucion;
    private float custoReparacion;
    private Area area;
    private Material material;

    public Incidencia(int numero) {
        this.numero = numero;
    }


    public Incidencia(TipoIncidencia tipoIncidencia,int numero, Usuario usuario, String descripcion,Area area) {
        this(tipoIncidencia, numero, usuario, descripcion);
        this.area=area;
    }

    public Incidencia(TipoIncidencia tipoIncidencia,int numero, Usuario usuario, String descripcion,Material material) {
        this(tipoIncidencia, numero, usuario, descripcion);
        this.material=material;
    }


    private Incidencia(TipoIncidencia tipoIncidencia, int numero, Usuario usuario, String descripcion) {
        this.tipoIncidencia=tipoIncidencia;
        this.numero = numero;
        this.usuario=usuario;
        this.descricion=descripcion;
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

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    @Override
    public String toString() {
        return "Incidencia{" +
                "tipoIncidencia=" + tipoIncidencia +
                ", numero=" + numero +
                ", usuario=" + usuario +
                ", descricion='" + descricion + '\'' +
                ", comentarioResolucion='" + comentarioResolucion + '\'' +
                ", dataFalla=" + dataFalla +
                ", dataResolucion=" + dataResolucion +
                ", custoReparacion=" + custoReparacion +
                ", area=" + area +
                ", material=" + material +
                '}';
    }
}

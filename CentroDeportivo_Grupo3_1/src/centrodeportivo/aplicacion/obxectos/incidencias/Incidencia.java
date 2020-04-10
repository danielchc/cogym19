package centrodeportivo.aplicacion.obxectos.incidencias;

import centrodeportivo.aplicacion.obxectos.Material;
import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.aplicacion.obxectos.tipos.TipoIncidencia;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;

import java.sql.Date;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public abstract class Incidencia {
    private TipoIncidencia tipoIncidencia;
    private int numero;
    private Usuario usuario;
    private String descricion;
    private String comentarioResolucion;
    private Date dataFalla;
    private Date dataResolucion;
    private float custoReparacion;

    public Incidencia(int numero) {
        this.numero = numero;
    }

    public Incidencia(int numero, Usuario usuario, String descripcion) {
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
                '}';
    }
}

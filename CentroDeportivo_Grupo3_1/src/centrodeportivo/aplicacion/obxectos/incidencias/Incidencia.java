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

    /**
     * Atributos da clase Incidencia
     */
    private int numero;
    private Usuario usuario;
    private String descricion;
    private String comentarioResolucion;
    private Date dataFalla;
    private Date dataResolucion;
    private float custoReparacion;

    /**
     * Constructor coa clave primaria
     * @param numero número da incidencia
     */
    public Incidencia(int numero) {
        this.numero = numero;
    }

    /**
     * Contructor para engadir unha os datos dunha incidencia
     * @param usuario usuario que a presenta
     * @param descripcion descripción da incidencia
     */
    public Incidencia(Usuario usuario, String descripcion) {
        this.usuario=usuario;
        this.descricion=descripcion;
    }

    /**
     * Contructor con todos os datos dunha incidencia
     * @param numero número
     * @param usuario usuario que a presenta
     * @param descricion descripción da incidencia
     */
    public Incidencia(int numero, Usuario usuario, String descricion, String comentarioResolucion, Date dataFalla, Date dataResolucion, float custoReparacion) {
        this.numero = numero;
        this.usuario = usuario;
        this.descricion = descricion;
        this.comentarioResolucion = comentarioResolucion;
        this.dataFalla = dataFalla;
        this.dataResolucion = dataResolucion;
        this.custoReparacion = custoReparacion;
    }

    public Incidencia(int numero, Usuario usuario, String descricion) {
        this.numero = numero;
        this.usuario = usuario;
        this.descricion = descricion;
    }

    /**
     *  Getters e Setters.
     */

    public boolean estaResolta(){
        return (this.dataResolucion!=null);
    }
    public abstract TipoIncidencia getTipoIncidencia();

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

    /**
     * Equals e toString
     */
    @Override
    public String toString() {
        return "Incidencia{" +
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

package centrodeportivo.aplicacion.obxectos.incidencias;

import centrodeportivo.aplicacion.obxectos.Material;
import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.aplicacion.obxectos.tipos.TipoIncidencia;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public class IncidenciaArea extends Incidencia {

    /**
     * Atributos da clase Incidencia de Áreas
     */
    private Area area;

    /**
     * Constructor coa clave primaria
     * @param numero número da incidencia
     */
    public IncidenciaArea(int numero) {
        super(numero);
        super.setTipoIncidencia(TipoIncidencia.Area);
    }

    /**
     * Contructor con todos os datos dunha incidencia
     * @param numero número
     * @param usuario usuario que a presenta
     * @param descripcion descripción da incidencia
     */
    public IncidenciaArea(int numero, Usuario usuario, String descripcion, Area area) {
        super(numero, usuario, descripcion);
        super.setTipoIncidencia(TipoIncidencia.Area);
        this.area=area;
    }

    /**
     * Contructor para engadir unha os datos dunha incidencia
     *
     * @param usuario     usuario que a presenta
     * @param descripcion descripción da incidencia
     */
    public IncidenciaArea(Usuario usuario, String descripcion, Area area) {
        super(usuario, descripcion);
        this.area = area;
    }

    /**
     * Getters e Setters
     */
    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
}

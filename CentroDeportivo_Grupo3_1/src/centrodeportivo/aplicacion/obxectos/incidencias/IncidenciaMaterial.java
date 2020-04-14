package centrodeportivo.aplicacion.obxectos.incidencias;

import centrodeportivo.aplicacion.obxectos.Material;
import centrodeportivo.aplicacion.obxectos.tipos.TipoIncidencia;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;

import java.sql.Date;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public class IncidenciaMaterial extends Incidencia{

    /**
     * Atributos da clase Incidencia de Material.
     */
    private Material material;

    /**
     * Constructor coa clave primaria
     * @param numero número da incidencia
     */
    public IncidenciaMaterial(int numero) {
        super(numero);
    }

    public IncidenciaMaterial(int numero, Usuario usuario, String descricionIncidencia, Material material) {
        super(numero,usuario,descricionIncidencia);
        this.material=material;
    }

    /**
     * Getters e Setters.
     */
    @Override
    public TipoIncidencia getTipoIncidencia() {
        return TipoIncidencia.Material;
    }

    /**
     * Contructor con todos os datos dunha incidencia
     *
     * @param numero               número
     * @param usuario              usuario que a presenta
     * @param descricion           descripción da incidencia
     * @param comentarioResolucion
     * @param dataFalla
     * @param dataResolucion
     * @param custoReparacion
     */
    public IncidenciaMaterial(int numero, Usuario usuario, String descricion, String comentarioResolucion, Date dataFalla, Date dataResolucion, float custoReparacion, Material material) {
        super(numero, usuario, descricion, comentarioResolucion, dataFalla, dataResolucion, custoReparacion);
        this.material = material;
    }

    /**
     * Contructor para engadir unha os datos dunha incidencia
     *
     * @param usuario     usuario que a presenta
     * @param descripcion descripción da incidencia
     */
    public IncidenciaMaterial(Usuario usuario, String descripcion, Material material) {
        super(usuario, descripcion);
        this.material = material;
    }

    /**
     * Getters e Setters
     */
    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}

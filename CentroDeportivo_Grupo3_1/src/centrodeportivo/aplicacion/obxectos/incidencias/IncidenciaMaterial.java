package centrodeportivo.aplicacion.obxectos.incidencias;

import centrodeportivo.aplicacion.obxectos.Material;
import centrodeportivo.aplicacion.obxectos.tipos.TipoIncidencia;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;

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
        super.setTipoIncidencia(TipoIncidencia.Material);
    }

    /**
     * Contructor con todos os datos dunha incidencia
     * @param numero número
     * @param usuario usuario que a presenta
     * @param descripcion descripción da incidencia
     */
    public IncidenciaMaterial(int numero, Usuario usuario, String descripcion, Material material) {
        super(numero, usuario, descripcion);
        this.material=material;
        super.setTipoIncidencia(TipoIncidencia.Material);
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

package centrodeportivo.aplicacion.obxectos.incidencias;

import centrodeportivo.aplicacion.obxectos.Material;
import centrodeportivo.aplicacion.obxectos.tipos.TipoIncidencia;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;

public class IncidenciaMaterial extends Incidencia{
    private Material material;
    public IncidenciaMaterial(int numero) {
        super(numero);
        super.setTipoIncidencia(TipoIncidencia.Material);
    }

    public IncidenciaMaterial(int numero, Usuario usuario, String descripcion, Material material) {
        super(numero, usuario, descripcion);
        this.material=material;
        super.setTipoIncidencia(TipoIncidencia.Material);
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}

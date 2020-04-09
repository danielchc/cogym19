package centrodeportivo.aplicacion.obxectos.incidencias;

import centrodeportivo.aplicacion.obxectos.Material;
import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.aplicacion.obxectos.tipos.TipoIncidencia;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;

public class IncidenciaArea extends Incidencia {
    private Area area;
    public IncidenciaArea(int numero) {
        super(numero);
        super.setTipoIncidencia(TipoIncidencia.Area);
    }

    public IncidenciaArea(int numero, Usuario usuario, String descripcion, Area area) {
        super(numero, usuario, descripcion);
        super.setTipoIncidencia(TipoIncidencia.Area);
        this.area=area;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
}

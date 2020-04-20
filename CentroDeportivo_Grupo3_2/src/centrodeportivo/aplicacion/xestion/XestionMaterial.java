package centrodeportivo.aplicacion.xestion;

import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.aplicacion.obxectos.area.Material;
import centrodeportivo.aplicacion.obxectos.tipos.TipoResultados;
import centrodeportivo.baseDatos.FachadaBD;
import centrodeportivo.gui.FachadaGUI;

public class XestionMaterial {

    // Atributos
    private FachadaGUI fachadaGUI;
    private FachadaBD fachadaBD;

    // Constructor
    public XestionMaterial(FachadaGUI fachadaGUI, FachadaBD fachadaBD) {
        this.fachadaGUI = fachadaGUI;
        this.fachadaBD = fachadaBD;
    }

    // Outros metodos

    public TipoResultados darAltaMaterial(Material material) throws ExcepcionBD {
        // Se o material non non existe, dase de alta:
        if (!fachadaBD.isMaterial(material)) {
            fachadaBD.darAltaMaterial(material);
            // Se se completa a execución do método sen lanzamento de excepcións, devolvemos que foi ben:
            return TipoResultados.correcto;
        } else {
            return TipoResultados.datoExiste;
        }
    }


}

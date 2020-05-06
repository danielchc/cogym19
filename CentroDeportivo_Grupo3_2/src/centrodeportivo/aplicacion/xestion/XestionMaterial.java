package centrodeportivo.aplicacion.xestion;

import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.area.Material;
import centrodeportivo.aplicacion.obxectos.tipos.TipoResultados;
import centrodeportivo.baseDatos.FachadaBD;
import centrodeportivo.gui.FachadaGUI;

import java.util.ArrayList;

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
        // Se o material non existe, dase de alta:
        if (!fachadaBD.isMaterial(material)) {
            fachadaBD.darAltaMaterial(material);
            // Se se completa a execución do método sen lanzamento de excepcións, devolvemos que foi ben:
            return TipoResultados.correcto;
        } else {
            return TipoResultados.datoExiste;
        }
    }

    public TipoResultados borrarMaterial(Material material) throws ExcepcionBD {
        fachadaBD.borrarMaterial(material);
        // Se se executou o método correctamente, devolvemos o enum que indica corrección;
        return TipoResultados.correcto;
    }

    public TipoResultados modificarMaterial(Material material) throws ExcepcionBD {
        if (!fachadaBD.isMaterial(material)) {
            fachadaBD.modificarMaterial(material);
            // Devolvemos co enum que se fixo a modificación sen problemas chegados a este punto:
            return TipoResultados.correcto;
        } else {
            // Se xa existe outro material do mesmo tipo e co mesmo identificador, devolvemos un enum que indique o propio:
            return TipoResultados.datoExiste;
        }
    }

    public ArrayList<Material> listarMateriais(Material material) {
        return fachadaBD.listarMateriais(material);
    }

    public Material consultarMaterial(Material material) {
        return fachadaBD.consultarMaterial(material);
    }
}

package centrodeportivo.aplicacion.xestion;

import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.area.Material;
import centrodeportivo.aplicacion.obxectos.area.TipoMaterial;
import centrodeportivo.aplicacion.obxectos.tipos.TipoResultados;
import centrodeportivo.baseDatos.FachadaBD;
import centrodeportivo.gui.FachadaGUI;

public class XestionTipoMaterial {


    // Atributos
    private FachadaGUI fachadaGUI;
    private FachadaBD fachadaBD;


    // Constructor
    public XestionTipoMaterial(FachadaGUI fachadaGUI, FachadaBD fachadaBD) {
        this.fachadaGUI = fachadaGUI;
        this.fachadaBD = fachadaBD;
    }


    // Outros metodos

    public TipoResultados darAltaTipoMaterial(TipoMaterial tipoMaterial) throws ExcepcionBD {
        // Se o tipo de material non existe, dase de alta:
        if (!fachadaBD.isTipoMaterial(tipoMaterial)) {
            fachadaBD.darAltaTipoMaterial(tipoMaterial);
            // Se se completa a execución do método sen lanzamento de excepcións, devolvemos que foi ben:
            return TipoResultados.correcto;
        } else {
            return TipoResultados.datoExiste;
        }
    }

    public TipoResultados borrarTipoMaterial(TipoMaterial tipoMaterial) throws ExcepcionBD {
        fachadaBD.borrarTipoMaterial(tipoMaterial);
        // Se se executou o método correctamente, devolvemos o enum que indica corrección;
        return TipoResultados.correcto;
    }

}

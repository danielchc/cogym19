package centrodeportivo.aplicacion.xestion;

import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.area.TipoMaterial;
import centrodeportivo.aplicacion.obxectos.tipos.TipoResultados;
import centrodeportivo.baseDatos.FachadaBD;
import centrodeportivo.gui.FachadaGUI;
import javafx.collections.ObservableList;

import java.util.ArrayList;

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
        // Comprobamos se existen materiais dese tipo:
        if (!fachadaBD.tenMateriais(tipoMaterial)) {
            // Se non ten materiais vinculados, podemos borralo:
            fachadaBD.borrarTipoMaterial(tipoMaterial);
            // Se se completou correctamente a execución do método, devolvemos un enum que indica correción:
            return TipoResultados.correcto;
        } else {
            // No caso de que houbese algún material vinculado, devolvemos un enum especificando o erro por referencia co restrict
            return TipoResultados.referenciaRestrict;
        }
    }

    public ArrayList<TipoMaterial> buscarTipoMaterial(TipoMaterial tipoMaterial) {
        return fachadaBD.buscarTipoMaterial(tipoMaterial);
    }

    public ObservableList<TipoMaterial> listarTiposMateriais(){
        return fachadaBD.lsitarTiposMateriais();
    }

}

package centrodeportivo.aplicacion.xestion;

import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.area.TipoMaterial;
import centrodeportivo.aplicacion.obxectos.tipos.TipoResultados;
import centrodeportivo.baseDatos.FachadaBD;
import centrodeportivo.gui.FachadaGUI;

import java.util.ArrayList;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Víctor Barreiro
 * Esta clase é aquela na que se levarán a cabo tarefas de xestión na parte de aplicación relacionadas cos tipos de materiais.
 * Introduciremos aquí as comprobacións que se realizarán para cada caso.
 */
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

    /**
     * BuscarTipoMaterial -> permite buscar tipos de materiais na base de datos con campos de busqueda, ou sen eles.
     *
     * @param tipoMaterial -> se non é null, a consulta realizase en base o nome do tipo de material.
     * @return -> se o parametro non é null, será devolto un array con todos os tipos de materiais que coincidan,
     * noutro caso, listanse todos os tipos de materiais.
     */
    public ArrayList<TipoMaterial> buscarTipoMaterial(TipoMaterial tipoMaterial) {
        return fachadaBD.buscarTipoMaterial(tipoMaterial);
    }

}

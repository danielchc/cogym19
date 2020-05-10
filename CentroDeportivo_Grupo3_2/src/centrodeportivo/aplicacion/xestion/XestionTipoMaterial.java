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

    /**
     * Atributos da xestión dos tipo de materias: basicamente son referencias ás demáis fachadas.
     */
    private FachadaGUI fachadaGUI;
    private FachadaBD fachadaBD;


    /**
     * Constructor da clase de xestión dos tipo de materiais:
     *
     * @param fachadaGUI Referencia á fachada da interface gráfica.
     * @param fachadaBD  Referencia á fachada da parte da Base de datos.
     */
    public XestionTipoMaterial(FachadaGUI fachadaGUI, FachadaBD fachadaBD) {
        this.fachadaGUI = fachadaGUI;
        this.fachadaBD = fachadaBD;
    }


    /**
     * Método que permite engadir unh nova tupla na base de datos cun novo tipo de material.
     *
     * @param tipoMaterial Datos do tipo de material que se creará (en concreto, o nome).
     * @return Devolve un enum que tipifica os posibles erroes durante a execución do método.
     * @throws ExcepcionBD Excepción procedente do método dao para indicar problemas na inserción.
     */
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

    /**
     * Método que permite eliminar un tipo de material da base de datos.
     *
     * @param tipoMaterial Datos do tipo de material que se eliminará.
     * @return Devolve un enum que tipifica os posibles erroes durante a execución do método.
     * @throws ExcepcionBD Excepción procedente do método dao para indicar problemas no borrado.
     */
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
     * Método que permite buscar tipos de materiais na base de datos con campos de busqueda, ou sen eles.
     *
     * @param tipoMaterial Se non é null, a consulta realizase en base o nome do tipo de material.
     * @return Se o parametro non é null, será devolto un array con todos os tipos de materiais que coincidan,
     * noutro caso, listanse todos os tipos de materiais.
     */
    public ArrayList<TipoMaterial> buscarTipoMaterial(TipoMaterial tipoMaterial) {
        return fachadaBD.buscarTipoMaterial(tipoMaterial);
    }

}

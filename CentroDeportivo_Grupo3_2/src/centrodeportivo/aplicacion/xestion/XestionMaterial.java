package centrodeportivo.aplicacion.xestion;

import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.area.Material;
import centrodeportivo.aplicacion.obxectos.tipos.TipoResultados;
import centrodeportivo.baseDatos.FachadaBD;
import centrodeportivo.gui.FachadaGUI;

import java.util.ArrayList;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Víctor Barreiro
 * Esta clase é aquela na que se levarán a cabo tarefas de xestión na parte de aplicación relacionadas cos materiais.
 * Introduciremos aquí as comprobacións que se realizarán para cada caso.
 */
public class XestionMaterial {
    /**
     * Atributos da xestión de materiais: basicamente son referencias ás demáis fachadas.
     */
    private FachadaGUI fachadaGUI;
    private FachadaBD fachadaBD;


    /**
     * Constructor da clase de xestión de materiais:
     *
     * @param fachadaGUI Referencia á fachada da interface gráfica.
     * @param fachadaBD  Referencia á fachada da parte da Base de datos.
     */
    public XestionMaterial(FachadaGUI fachadaGUI, FachadaBD fachadaBD) {
        this.fachadaGUI = fachadaGUI;
        this.fachadaBD = fachadaBD;
    }


    /**
     * Método que crea unha nova tupla insertando un material na base de datos.
     *
     * @param material Datos do material que se engadirá na base de datos.
     * @return Devolve un enum que tipifica os posibles erroes durante a execución do método.
     * @throws ExcepcionBD Excepción procedente do método dao para indicar problemas na inserción.
     */
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

    /**
     * Método que elimina a tupla dun material na base de datos.
     *
     * @param material Datos do material que se eliminará.
     * @return Devolve un enum que tipifica os posibles errores durante a execución do método.
     * @throws ExcepcionBD Excepción procedente do método dao para indicar problemas no borrado.
     */
    public TipoResultados borrarMaterial(Material material) throws ExcepcionBD {
        fachadaBD.borrarMaterial(material);
        // Se se executou o método correctamente, devolvemos o enum que indica corrección;
        return TipoResultados.correcto;
    }

    /**
     * Método que modifica os datos un material na base de datos
     *
     * @param material Datos do material que se modificará.
     * @return Devolve un enum que tipifica os posibles errores durante a execución do método.
     * @throws ExcepcionBD Excepción procedente do método dao para indicar problemas na modificación.
     */
    public TipoResultados modificarMaterial(Material material) throws ExcepcionBD {
        // Comprobamos que o material a modificar existe na base de datos
        if (fachadaBD.isMaterial(material)) {
            fachadaBD.modificarMaterial(material);
            // Devolvemos un enum que indique que se modificou correctamente:
            return TipoResultados.correcto;
        } else {
            // Se non existe,  devolvemos  un enum que indique o propio:
            return TipoResultados.datoNonExiste;
        }
    }

    /**
     * Método que comproba se certo material existe na base de datos e devolve os datos actualizados.
     *
     * @param material Datos do material que se quere consultar.
     * @return Devolve os datos do material actualizado.
     */
    public Material consultarMaterial(Material material) {
        return fachadaBD.consultarMaterial(material);
    }

    /**
     * Método que obten todos os materiais almacenados na base de datos e permite filtrar en función
     * da área e instalación na que se atope así como, polo tipo de material.
     *
     * @param material Datos do material co que poderemos filtrar en función da instalación e área na que
     *                 se atope así como, polo seu tipo.
     * @return Devolve un ArrayList cos materiais da base de datos que cumpran ditas condicións.
     */
    public ArrayList<Material> listarMateriais(Material material) {
        return fachadaBD.listarMateriais(material);
    }

}

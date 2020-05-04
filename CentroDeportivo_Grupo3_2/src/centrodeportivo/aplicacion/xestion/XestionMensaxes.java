package centrodeportivo.aplicacion.xestion;

import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.Mensaxe;
import centrodeportivo.baseDatos.FachadaBD;
import centrodeportivo.gui.FachadaGUI;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Víctor Barreiro
 * Clase na que se levarán a cabo xestións que estén relacionadas co envío de mensaxes.
 */
public class XestionMensaxes {

    /**
     * Coma sempre, introducimos unha referencia á fachada da parte da base de datos e outra á da parte da interface
     * gráfica.
     */
    private FachadaBD fachadaBD;
    private FachadaGUI fachadaGUI;

    /**
     * Constructor da clase de xestión de mensaxes
     * @param fachadaGUI A referencia á fachada da interface gráfica.
     * @param fachadaBD A referencia á fachada da parte da base de datos.
     */
    public XestionMensaxes(FachadaGUI fachadaGUI, FachadaBD fachadaBD){
        this.fachadaBD = fachadaBD;
        this.fachadaGUI = fachadaGUI;
    }

    /**
     * Método que nos permite enviar unha mensaxe de aviso a todos os socios.
     * @param mensaxe A mensaxe a transmitir
     * @throws ExcepcionBD Excepción que se pode producir por problemas coa base de datos.
     */
    public void enviarAvisoSocios(Mensaxe mensaxe) throws ExcepcionBD{
        fachadaBD.enviarAvisoSocios(mensaxe);
    }

}

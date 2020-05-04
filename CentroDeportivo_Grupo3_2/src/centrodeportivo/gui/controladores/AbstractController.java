package centrodeportivo.gui.controladores;

import centrodeportivo.aplicacion.FachadaAplicacion;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Víctor Barreiro
 * Clase abstracta, que empregaremos para ter unha consideración común de todos os controladores da aplicación. Todos
 * eses controladores que creemos, herdarán deste.
 */
public abstract class AbstractController {
    /**
     * Atributo: a fachada de aplicación. É algo común a todos os controladores que consideremos.
     */
    private FachadaAplicacion fachadaAplicacion;

    /**
     * Constructor do controlador abstracto.
     * @param fachadaAplicacion A referencia á fachada de aplicación.
     */
    public AbstractController(FachadaAplicacion fachadaAplicacion) {
        //Asociamos a fachada de aplicación pasada ao atributo asociado.
        this.fachadaAplicacion = fachadaAplicacion;
    }

    /**
     * Getter da fachada de aplicación.
     * @return A fachada de aplicación que posúe esta clase.
     */
    public FachadaAplicacion getFachadaAplicacion() {
        return fachadaAplicacion;
    }

    /**
     * Setter da fachada de aplicación
     * @param fachadaAplicacion A fachada de aplicación a asignar a esta clase.
     */
    public void setFachadaAplicacion(FachadaAplicacion fachadaAplicacion) {
        this.fachadaAplicacion = fachadaAplicacion;
    }

    /**
     * Método reiniciarForm(): trátase dun método que pode ser útil dende certos controladores para cando iniciemos as
     * ventás correspondentes. Será algo que se invoque cada vez que se chama á ventá correspondente.
     * Os controladores fillos serán os que o poidan sobreescribir e realizar accións útiles con el (non teñen porque).
     */
    public void reiniciarForm(){

    }
}

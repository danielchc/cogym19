package centrodeportivo.gui.controladores;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.gui.controladores.principal.vPrincipalController;


/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public abstract class AbstractController {

    /**
     * Atributos básicos dun controlador
     */
    private FachadaAplicacion fachadaAplicacion;
    private vPrincipalController vPrincipalController;


    /**
     * @param fachadaAplicacion Fachada da aplicación
     * @param vPrincipalController Controlador da vista principal
     */
    public AbstractController(FachadaAplicacion fachadaAplicacion, vPrincipalController vPrincipalController) {
        this.fachadaAplicacion = fachadaAplicacion;
        this.vPrincipalController=vPrincipalController;
    }

    /**
     * @param fachadaAplicacion Fachada da aplicación
     */
    public AbstractController(FachadaAplicacion fachadaAplicacion) {
        this.fachadaAplicacion = fachadaAplicacion;
    }


    /**
     * Getters e Setters
     */
    public FachadaAplicacion getFachadaAplicacion() {
        return fachadaAplicacion;
    }

    public void setFachadaAplicacion(FachadaAplicacion fachadaAplicacion) {
        this.fachadaAplicacion = fachadaAplicacion;
    }

    public vPrincipalController getvPrincipalController() {
        return vPrincipalController;
    }

    public void setvPrincipalController(vPrincipalController vPrincipalController) {
        this.vPrincipalController = vPrincipalController;
    }
}

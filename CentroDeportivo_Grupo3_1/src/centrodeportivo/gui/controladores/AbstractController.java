package centrodeportivo.gui.controladores;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.gui.controladores.principal.vPrincipalController;

public abstract class AbstractController {
    private FachadaAplicacion fachadaAplicacion;
    private vPrincipalController vPrincipalController;


    public AbstractController(FachadaAplicacion fachadaAplicacion, vPrincipalController vPrincipalController) {
        this.fachadaAplicacion = fachadaAplicacion;
        this.vPrincipalController=vPrincipalController;
    }

    public AbstractController(FachadaAplicacion fachadaAplicacion) {
        this.fachadaAplicacion = fachadaAplicacion;
    }

    public FachadaAplicacion getFachadaAplicacion() {
        return fachadaAplicacion;
    }

    public void setFachadaAplicacion(FachadaAplicacion fachadaAplicacion) {
        this.fachadaAplicacion = fachadaAplicacion;
    }

    public centrodeportivo.gui.controladores.principal.vPrincipalController getvPrincipalController() {
        return vPrincipalController;
    }

    public void setvPrincipalController(centrodeportivo.gui.controladores.principal.vPrincipalController vPrincipalController) {
        this.vPrincipalController = vPrincipalController;
    }
}

package centrodeportivo.gui.controladores;

import centrodeportivo.aplicacion.FachadaAplicacion;

public abstract class AbstractController {
    private FachadaAplicacion fachadaAplicacion;


    public AbstractController(FachadaAplicacion fachadaAplicacion) {
        this.fachadaAplicacion = fachadaAplicacion;
    }

    public FachadaAplicacion getFachadaAplicacion() {
        return fachadaAplicacion;
    }

    public void setFachadaAplicacion(FachadaAplicacion fachadaAplicacion) {
        this.fachadaAplicacion = fachadaAplicacion;
    }
}

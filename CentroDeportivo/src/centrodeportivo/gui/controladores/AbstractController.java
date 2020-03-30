package centrodeportivo.gui.controladores;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;

public abstract class AbstractController {
    private FachadaAplicacion fachadaAplicacion;
    private Usuario usuario;


    public AbstractController(FachadaAplicacion fachadaAplicacion) {
        this.fachadaAplicacion = fachadaAplicacion;
    }

    public AbstractController(FachadaAplicacion fachadaAplicacion,Usuario usuario) {
        this.fachadaAplicacion = fachadaAplicacion;
        this.usuario=usuario;
    }


    public FachadaAplicacion getFachadaAplicacion() {
        return fachadaAplicacion;
    }

    public void setFachadaAplicacion(FachadaAplicacion fachadaAplicacion) {
        this.fachadaAplicacion = fachadaAplicacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}

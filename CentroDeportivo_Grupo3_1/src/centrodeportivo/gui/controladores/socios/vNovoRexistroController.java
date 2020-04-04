package centrodeportivo.gui.controladores.socios;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class vNovoRexistroController extends AbstractController implements Initializable {


    private Usuario usuario;
    private vPrincipalController vPrincipalController;

    public vNovoRexistroController(FachadaAplicacion fachadaAplicacion, vPrincipalController vPrincipalController, Usuario usuario) {
        super(fachadaAplicacion);
        this.usuario=usuario;
        this.vPrincipalController=vPrincipalController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

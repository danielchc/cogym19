package centrodeportivo.gui.controladores.socios;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.gui.controladores.AbstractController;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class vPrincipalSociosController extends AbstractController implements Initializable {
    private Usuario loggedUser;

    public vPrincipalSociosController(FachadaAplicacion fachadaAplicacion, Usuario usuario) {
        super(fachadaAplicacion);
        this.loggedUser=usuario;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

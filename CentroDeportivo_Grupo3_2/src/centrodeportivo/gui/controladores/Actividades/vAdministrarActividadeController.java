package centrodeportivo.gui.controladores.Actividades;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class vAdministrarActividadeController extends AbstractController implements Initializable {

    private vPrincipalController controllerPrincipal;

    public vAdministrarActividadeController(FachadaAplicacion fachadaAplicacion, vPrincipalController controllerPrincipal) {
        super(fachadaAplicacion);
        this.controllerPrincipal = controllerPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

}

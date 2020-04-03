package centrodeportivo.gui.controladores.socios;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.usuarios.Socio;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.gui.controladores.AbstractController;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class vResumenRexistrosController extends AbstractController implements Initializable {
    public vResumenRexistrosController(FachadaAplicacion fachadaAplicacion, Usuario socio) {
        super(fachadaAplicacion);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

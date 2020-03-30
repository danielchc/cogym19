package centrodeportivo.gui.controladores.persoal;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.gui.controladores.AbstractController;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class vInicioController extends AbstractController implements Initializable {
    public Label label;

    public vInicioController(FachadaAplicacion fachadaAplicacion, Usuario usuario) {
        super(fachadaAplicacion, usuario);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        label.setText("Benvido "+super.getUsuario().getNome()+"!!!");
    }
}

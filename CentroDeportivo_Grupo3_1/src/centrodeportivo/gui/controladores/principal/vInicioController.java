package centrodeportivo.gui.controladores.principal;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.gui.controladores.AbstractController;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class vInicioController extends AbstractController implements Initializable {
    public Label label;

    private Usuario usuario;

    public vInicioController(FachadaAplicacion fachadaAplicacion, Usuario usuario) {
        super(fachadaAplicacion);
        this.usuario=usuario;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        label.setText("Benvido "+usuario.getNome()+"!!!");
    }
}

package centrodeportivo.gui.controladores.principal;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.usuarios.PersoaFisica;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.gui.controladores.AbstractController;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class vInicioController extends AbstractController implements Initializable {
    public Label label;

    private PersoaFisica persoaFisica;

    public vInicioController(FachadaAplicacion fachadaAplicacion, PersoaFisica persoaFisica) {
        super(fachadaAplicacion);
        this.persoaFisica=persoaFisica;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        label.setText("Benvido "+persoaFisica.getNome()+"!!!");
    }
}

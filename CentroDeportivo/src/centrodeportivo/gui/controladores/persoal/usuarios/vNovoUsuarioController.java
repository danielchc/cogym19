package centrodeportivo.gui.controladores.persoal.usuarios;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.gui.controladores.AbstractController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class vNovoUsuarioController extends AbstractController implements Initializable {
    public RadioButton radioPersoal;
    public RadioButton radioSocio;
    public Button btnContinuar;
    public AnchorPane container;

    private ToggleGroup grupoRadio;

    public vNovoUsuarioController(FachadaAplicacion fachadaAplicacion) {
        super(fachadaAplicacion);
        grupoRadio=new ToggleGroup();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.radioSocio.setToggleGroup(grupoRadio);
        this.radioPersoal.setToggleGroup(grupoRadio);
        radioSocio.setSelected(true);
    }

    public void accionContinuar(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader=new FXMLLoader();
            if(grupoRadio.getSelectedToggle().equals(radioSocio)){
                fxmlLoader.setController(new vNovoSocioController(super.getFachadaAplicacion()));
                fxmlLoader.setLocation(getClass().getResource("../../../vistas/persoal/vNovoSocio.fxml"));
            }
            else{
                fxmlLoader.setController(new vNovoPersoalController(getFachadaAplicacion()));
                fxmlLoader.setLocation(getClass().getResource("../../../vistas/persoal/vNovoPersoal.fxml"));
            }
            this.container.getChildren().removeAll(this.container.getChildren());
            this.container.getChildren().add(fxmlLoader.load());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

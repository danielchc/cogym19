package centrodeportivo.gui.controladores.persoal;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.gui.controladores.AbstractController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class vNovoUsuarioController extends AbstractController implements Initializable {
    public RadioButton radioPersoal;
    public RadioButton radioSocio;
    public Button btnContinuar;
    public AnchorPane container;

    public static int OPCIONSOCIO=1;
    public static int OPCIONPERSOAL=2;
    private int opcion;
    private FXMLLoader fxmlLoader;

    public vNovoUsuarioController(FachadaAplicacion fachadaAplicacion) {
        super(fachadaAplicacion);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.opcion=OPCIONSOCIO;
        radioSocio.setSelected(true);
        this.fxmlLoader=new FXMLLoader();

    }

    public void accionSocio(ActionEvent actionEvent) {
        radioSocio.setSelected(true);
        radioPersoal.setSelected(false);
        this.opcion=OPCIONSOCIO;
    }

    public void accionPersoal(ActionEvent actionEvent) {
        radioSocio.setSelected(false);
        radioPersoal.setSelected(true);
        this.opcion=OPCIONPERSOAL;
    }

    public void accionContinuar(ActionEvent actionEvent) {
        try {

            if(opcion==OPCIONSOCIO){
                fxmlLoader.setController(new vNovoSocioController(super.getFachadaAplicacion()));
                fxmlLoader.setLocation(getClass().getResource("../../vistas/persoal/vNovoSocio.fxml"));
            }
            else{
                fxmlLoader.setController(new vNovoPersoalController(getFachadaAplicacion()));
                fxmlLoader.setLocation(getClass().getResource("../../vistas/persoal/vNovoPersoal.fxml"));
            }

            this.container.getChildren().removeAll(this.container.getChildren());
            this.container.getChildren().add(fxmlLoader.load());
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

    }
}

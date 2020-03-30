package centrodeportivo.gui.controladores.persoal;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class vNovoUsuarioController implements Initializable {
    public RadioButton radioPersoal;
    public RadioButton radioSocio;
    public Button btnContinuar;
    public AnchorPane container;


    public static int OPCIONSOCIO=1;
    public static int OPCIONPERSOAL=2;
    private int opcion;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.opcion=OPCIONSOCIO;
        radioSocio.setSelected(true);
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
        Pane panelConfig;
        try {
            if(opcion==OPCIONSOCIO) panelConfig = FXMLLoader.load(getClass().getResource("../../vistas/persoal/vNovoSocio.fxml"));
            else
                panelConfig = FXMLLoader.load(getClass().getResource("../../vistas/persoal/vNovoPersoal.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        this.container.getChildren().removeAll(this.container.getChildren());
        this.container.getChildren().add(panelConfig);
    }
}

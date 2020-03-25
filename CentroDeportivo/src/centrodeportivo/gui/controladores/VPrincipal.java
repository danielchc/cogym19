package centrodeportivo.gui.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class VPrincipal {

    @FXML
    private Pane panel;
    public void initialize() throws IOException {
    }

    @FXML
    private void tocouButton(ActionEvent event) {
        System.out.println("Dite");

    }
}
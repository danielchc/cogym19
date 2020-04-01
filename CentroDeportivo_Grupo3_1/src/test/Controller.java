package test;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public BorderPane panel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Pane p= FXMLLoader.load(getClass().getResource("menu2.fxml"));
            this.panel.setLeft(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

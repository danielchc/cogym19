package centrodeportivo.gui;

import centrodeportivo.aplicacion.FachadaAplicacion;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FachadaGUI {
    private FachadaAplicacion fachadaAplicacion;
    FXMLLoader fxmlLoader = new FXMLLoader();
    Stage stage = new Stage();
    Scene scene;
    //vprincipal
    //vlogin


    public FachadaGUI(FachadaAplicacion fachadaAplicacion) {
        this.fachadaAplicacion = fachadaAplicacion;
    }

    public void mostrarVentaPersoal() throws IOException {
        fxmlLoader.setLocation(getClass().getResource("./vistas/persoal/vPrincipal.fxml"));
        stage.setTitle("Xestion Centro Deportivo");
        stage.setScene(new Scene(fxmlLoader.load(), 800, 500));
        stage.show();
    }
    public void mostrarVentaSocios() throws IOException {
        fxmlLoader.setLocation(getClass().getResource("./vistas/socios/test.fxml"));
        stage.setTitle("Xestion Centro Deportivo");
        stage.setScene(new Scene(fxmlLoader.load(), 800, 500));
        stage.show();
    }
}

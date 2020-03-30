package centrodeportivo.gui;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.gui.controladores.persoal.vPrincipalPersoalController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class FachadaGUI {
    private FachadaAplicacion fachadaAplicacion;
    FXMLLoader fxmlLoader;
    Stage stage;
    Scene scene;
    //vprincipal
    //vlogin


    public FachadaGUI(FachadaAplicacion fachadaAplicacion) {
        this.fachadaAplicacion = fachadaAplicacion;
        stage = new Stage();
        fxmlLoader= new FXMLLoader();
    }

    public void mostrarVentaPersoal() throws IOException {

        fxmlLoader.setController(new vPrincipalPersoalController(this.fachadaAplicacion));
        fxmlLoader.setLocation(getClass().getResource("./vistas/persoal/vPrincipal.fxml"));
        stage.setTitle("Xestión Centro Deportivo");
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }
    public void mostrarVentaSocios() throws IOException {
        fxmlLoader.setController(new vPrincipalPersoalController(this.fachadaAplicacion));
        fxmlLoader.setLocation(getClass().getResource("./vistas/socios/test.fxml"));
        stage.setTitle("Xestión Centro Deportivo");
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }

}

package centrodeportivo.gui;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.gui.controladores.persoal.vPrincipalPersoalController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;

public class FachadaGUI {
    private FachadaAplicacion fachadaAplicacion;
    FXMLLoader fxmlLoader;
    Stage stage;

    public FachadaGUI(FachadaAplicacion fachadaAplicacion) {
        this.fachadaAplicacion = fachadaAplicacion;
        stage = new Stage();
        fxmlLoader= new FXMLLoader();
    }

    public void mostrarVentaPersoal(Usuario loggedUser) throws IOException {

        fxmlLoader.setController(new vPrincipalPersoalController(this.fachadaAplicacion,loggedUser));
        fxmlLoader.setLocation(getClass().getResource("./vistas/persoal/vPrincipal.fxml"));
        stage.setTitle("Xestión Centro Deportivo");
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.setResizable(false);
        stage.show();
    }
    public void mostrarVentaSocios() throws IOException {
        //fxmlLoader.setController(new vPrincipalPersoalController(this.fachadaAplicacion));
        //fxmlLoader.setLocation(getClass().getResource("./vistas/socios/test.fxml"));
        //stage.setTitle("Xestión Centro Deportivo");
        //stage.setScene(new Scene(fxmlLoader.load()));
        //stage.setResizable(false);
        //stage.show();
    }

    public void mostrarAdvertencia(String titulo,String texto){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);

        alert.setContentText(texto);
        alert.showAndWait();
    }

    public void mostrarErro(String titulo,String texto){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(texto);
        alert.showAndWait();
    }

    public void mostrarInformacion(String titulo,String texto){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(texto);
        alert.showAndWait();
    }

    public ButtonType mostrarConfirmacion(String titulo, String texto){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(texto);
        alert.showAndWait();
        return alert.getResult();
    }

}

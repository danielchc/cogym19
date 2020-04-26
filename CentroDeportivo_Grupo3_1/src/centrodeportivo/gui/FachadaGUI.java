package centrodeportivo.gui;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.auxiliar.IdPantalla;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class FachadaGUI {

    /**
     * Atributos da Fachada GUI.
     */
    private FachadaAplicacion fachadaAplicacion;
    private FXMLLoader fxmlLoader;
    private Stage stage;

    /**
     * Constructor da fachada da GUI.
     * @param fachadaAplicacion fachada da aplicación.
     */
    public FachadaGUI(FachadaAplicacion fachadaAplicacion) {
        this.fachadaAplicacion = fachadaAplicacion;
        stage = new Stage();
        fxmlLoader= new FXMLLoader();
    }

    /**
     * Método para iniciar a vista principal do persoal
     * @param loggedUser usuario loggeado
     * @throws IOException
     */
    public void mostrarVentaPersoal(Usuario loggedUser) throws IOException {

        fxmlLoader.setController(new vPrincipalController(this.fachadaAplicacion,loggedUser, IdPantalla.PANTALLAPERSOAL));
        fxmlLoader.setLocation(getClass().getResource("./vistas/principal/vPrincipal.fxml"));
        stage.getIcons().add(new Image("/centrodeportivo/gui/imaxes/logoPequeno.png"));
        stage.setTitle("COGYM-19");
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.setResizable(true);
        stage.show();
    }

    /**
     * Método para iniciar a vista principal dos socios
     * @param loggedUser usuario loggeado
     * @throws IOException
     */
    public void mostrarVentaSocios(Usuario loggedUser) throws IOException {
        fxmlLoader.setController(new vPrincipalController(this.fachadaAplicacion,loggedUser,IdPantalla.PANTALLASOCIO));
        fxmlLoader.setLocation(getClass().getResource("./vistas/principal/vPrincipal.fxml"));
        stage.getIcons().add(new Image("/centrodeportivo/gui/imaxes/logoPequeno.png"));
        stage.setTitle("COGYM-19");
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.setResizable(true);
        stage.show();
    }

    /**
     * Método para mostrar unha advertencia.
     * @param titulo título
     * @param texto texto a mostrar
     */
    public void mostrarAdvertencia(String titulo,String texto){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);

        alert.setContentText(texto);
        alert.showAndWait();
    }

    /**
     * Método para mostrar un erro.
     * @param titulo título
     * @param texto texto a mostrar
     */
    public void mostrarErro(String titulo,String texto){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(texto);
        alert.showAndWait();
    }

    /**
     * Método para mostrar unha información.
     * @param titulo título
     * @param texto texto a mostrar
     */
    public void mostrarInformacion(String titulo,String texto){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(texto);
        alert.showAndWait();
    }

    /**
     * Método para mostrar unha confirmación.
     * @param titulo título
     * @param texto texto a mostrar
     */
    public ButtonType mostrarConfirmacion(String titulo, String texto){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(texto);
        alert.showAndWait();
        return alert.getResult();
    }

}
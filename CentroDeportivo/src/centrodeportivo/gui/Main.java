package centrodeportivo.gui;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.gui.controladores.vLoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    FachadaAplicacion fachadaAplicacion;
    @Override
    public void start(Stage primaryStage) throws Exception{
        fachadaAplicacion=new FachadaAplicacion();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./vistas/vLogin.fxml"));
        Parent root = loader.load();

        ((vLoginController)loader.getController()).setFa(fachadaAplicacion);

        primaryStage.setScene(new Scene(root));

        primaryStage.setTitle("Centro Deportivo");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

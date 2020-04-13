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
        loader.setController(new vLoginController(this.fachadaAplicacion));
        Parent root = loader.load();
        
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(300);
        primaryStage.setTitle("Centro Deportivo");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

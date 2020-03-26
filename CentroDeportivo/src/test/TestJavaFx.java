package test;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.baseDatos.FachadaBD;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class TestJavaFx extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException, SQLException {

        TableView tableView = new TableView();

        TableColumn<String, Usuario> column1 = new TableColumn<>("Nome");
        column1.setCellValueFactory(new PropertyValueFactory<>("nome"));


        TableColumn<String, Usuario> column2 = new TableColumn<>("Login");
        column2.setCellValueFactory(new PropertyValueFactory<>("login"));

        tableView.getColumns().addAll(column1,column2);

        FachadaBD fb=new FachadaBD(new FachadaAplicacion());
        tableView.getItems().addAll(fb.getDaoUsuarios().listarPersoal());

        VBox vbox = new VBox(tableView);

        Scene scene = new Scene(vbox);

        primaryStage.setScene(scene);

        primaryStage.show();
    }
}

package test;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
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

        TableColumn<Integer, Instalacion> column1 = new TableColumn<>("Codigo");
        column1.setCellValueFactory(new PropertyValueFactory<>("codInstalacion"));

        TableColumn<String, Instalacion> column2 = new TableColumn<>("Nome");
        column2.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<String, Instalacion> column3 = new TableColumn<>("Número de teléfono");
        column3.setCellValueFactory(new PropertyValueFactory<>("numTelefono"));

        TableColumn<String, Instalacion> column4 = new TableColumn<>("Dirección");
        column4.setCellValueFactory(new PropertyValueFactory<>("direccion"));

        tableView.getColumns().addAll(column1,column2, column3, column4);

        FachadaBD fb=new FachadaBD(new FachadaAplicacion());
        tableView.getItems().addAll(fb.buscarInstalacions(new Instalacion("", "", "")));

        VBox vbox = new VBox(tableView);

        Scene scene = new Scene(vbox);

        primaryStage.setScene(scene);

        primaryStage.show();
    }
}

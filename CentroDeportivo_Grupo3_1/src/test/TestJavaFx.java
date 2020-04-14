package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class TestJavaFx extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException, SQLException {

        /*TableView tableView = new TableView();

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

        Scene scene = new Scene(vbox);*/

        /*Pane p=new Pane();
        p.setPrefSize(200,40);
        Label l=new Label("nombre");
        l.setPrefSize(150,40);
        Button b=new Button("10");
        b.setPrefSize(45,40);
        HBox hb=new HBox(l,b);
        p.getChildren().add(hb);*/
        Pane p=FXMLLoader.load(getClass().getResource("../centrodeportivo/gui/vistas/persoal/incidencias/vXestionIncidencia.fxml"));
        primaryStage.setScene(new Scene(p));

        primaryStage.show();
    }
}

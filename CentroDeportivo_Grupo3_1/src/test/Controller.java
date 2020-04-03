package test;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.RexistroMarca;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class Controller implements Initializable {


    public LineChart<String,Float> chart;
    public CategoryAxis dias;
    public NumberAxis peso;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("hola");

        XYChart.Series dataSeries1 = new XYChart.Series();
        dataSeries1.setName("Peso");

        try {
            FachadaAplicacion fa=new FachadaAplicacion();
            for(RexistroMarca r:fa.listarRexistros("david")){
                dataSeries1.getData().add(new XYChart.Data(r.getData().toString(), r.getPeso()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        chart.getData().add(dataSeries1);
    }
}

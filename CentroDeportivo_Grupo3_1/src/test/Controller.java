package test;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.RexistroFisioloxico;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
            for(RexistroFisioloxico r:fa.listarRexistros("david")){
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

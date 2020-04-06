package centrodeportivo.gui.controladores.socios;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.RexistroFisioloxico;
import centrodeportivo.aplicacion.obxectos.usuarios.Socio;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.gui.controladores.AbstractController;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class vResumenRexistrosController extends AbstractController implements Initializable {

    /**
     * Atributos do fxml
     */
    public Label labelSocio;
    public LineChart<String,Float> graficaPeso;
    public StackedAreaChart<String,Integer> graficaTension;
    public TextField campoPeso;
    public TextField campoAltura;
    public TextField campoBFP;
    public TextField campoTension;
    public TextField campoPPM;
    public TreeView<String> treeComentarios;

    /**
     * Atributos do controlador
     */
    private Usuario socio;
    private ArrayList<RexistroFisioloxico> rexistros;

    public vResumenRexistrosController(FachadaAplicacion fachadaAplicacion, Usuario socio) {
        super(fachadaAplicacion);
        this.socio=socio;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.labelSocio.setText(socio.getNome());
        this.rexistros=super.getFachadaAplicacion().listarRexistros(socio.getLogin());
        generarGraficaPeso();
        generarGraficaTension();

        float pesoavg=0;
        float alturaavg=0;
        float bfpavg=0;
        float tensionAltaavg=0;
        float tensionBaixaavg=0;
        float ppmAvg=0;
        for(RexistroFisioloxico rex:this.rexistros){
            pesoavg+=rex.getPeso();
            alturaavg+=rex.getAltura();
            if(rex.getBfp()!=null) bfpavg+=rex.getBfp();
            if(rex.getTensionAlta()!=null) tensionAltaavg+=rex.getTensionAlta();
            if(rex.getTensionBaixa()!=null) tensionBaixaavg+=rex.getTensionBaixa();
            if(rex.getPpm()!=null) ppmAvg+=rex.getPpm();
        }
        if(this.rexistros.size()!=0){
            pesoavg=pesoavg/this.rexistros.size();
            alturaavg=alturaavg/this.rexistros.size();
            bfpavg=bfpavg/this.rexistros.size();
            tensionAltaavg=tensionAltaavg/this.rexistros.size();
            tensionBaixaavg=tensionBaixaavg/this.rexistros.size();
            ppmAvg=ppmAvg/this.rexistros.size();
        }

        this.campoPeso.setText(pesoavg+" Kg");
        this.campoAltura.setText(alturaavg+" cm");
        this.campoBFP.setText(bfpavg+" % de graxa corporal");
        this.campoTension.setText(tensionAltaavg+" / "+tensionBaixaavg);
        this.campoPPM.setText(ppmAvg+" ppm");

        generarTreeComentarios();

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(rexistros.size()==0){
                    getFachadaAplicacion().mostrarAdvertencia("Rexistros Fisiolóxicos","Non dispón de rexistros fisiolóxicos almacenados no sistema.");
                }
            }
        });
    }

    private void generarGraficaPeso(){
        XYChart.Series<String,Float> datos = new XYChart.Series<>();
        datos.setName("Peso");
        for(RexistroFisioloxico rex:this.rexistros){
            LocalDateTime fecha=rex.getData().toLocalDateTime();
            String dataFormateada=String.format("%d-%d-%d %d:%d:%d",
                    fecha.getDayOfMonth(),
                    fecha.getMonthValue(),
                    fecha.getYear(),
                    fecha.getHour(),
                    fecha.getMinute(),
                    fecha.getSecond()
                );
            datos.getData().add(new XYChart.Data(dataFormateada, rex.getPeso()));
        }
        graficaPeso.getData().add(datos);
    }

    private void generarGraficaTension(){
        XYChart.Series<String,Integer> datosAlta = new XYChart.Series<>();
        datosAlta.setName("Tensión Alta");
        for(RexistroFisioloxico rex:this.rexistros){
            if(rex.getTensionAlta()==0) continue;
            LocalDateTime fecha=rex.getData().toLocalDateTime();
            String dataFormateada=String.format("%d-%d-%d %d:%d:%d",
                    fecha.getDayOfMonth(),
                    fecha.getMonthValue(),
                    fecha.getYear(),
                    fecha.getHour(),
                    fecha.getMinute(),
                    fecha.getSecond()
            );
            datosAlta.getData().add(new XYChart.Data(dataFormateada, rex.getTensionAlta()));
        }
        graficaTension.getData().add(datosAlta);
        XYChart.Series<String,Integer> datosBaixa = new XYChart.Series<>();
        datosBaixa.setName("Tensión Baixa");
        for(RexistroFisioloxico rex:this.rexistros){
            if(rex.getTensionBaixa()==0) continue;
            LocalDateTime fecha=rex.getData().toLocalDateTime();
            String dataFormateada=String.format("%d-%d-%d %d:%d:%d",
                    fecha.getDayOfMonth(),
                    fecha.getMonthValue(),
                    fecha.getYear(),
                    fecha.getHour(),
                    fecha.getMinute(),
                    fecha.getSecond()
            );
            datosBaixa.getData().add(new XYChart.Data(dataFormateada, rex.getTensionBaixa()));
        }
        graficaTension.getData().add(datosBaixa);
    }

    private void generarTreeComentarios(){
        TreeItem<String> root=new TreeItem<>("Comentarios das distintas medicións...");

        for(RexistroFisioloxico rex:this.rexistros){
            if(rex.getComentario().isEmpty()) continue;
            TreeItem<String> item=new TreeItem<String>(rex.getData().toLocalDateTime().toLocalDate().toString());
            item.getChildren().add(new TreeItem<>(rex.getComentario()));
            root.getChildren().add(item);
        }

        this.treeComentarios.setRoot(root);
    }
}

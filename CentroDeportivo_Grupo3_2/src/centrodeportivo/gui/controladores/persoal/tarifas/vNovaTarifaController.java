package centrodeportivo.gui.controladores.persoal.tarifas;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.tarifas.Tarifa;
import centrodeportivo.aplicacion.obxectos.usuarios.Socio;
import centrodeportivo.funcionsAux.ValidacionDatos;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.persoal.PantallasPersoal;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class vNovaTarifaController extends AbstractController implements Initializable {
    public TextField campoNome;
    public Slider campoActividades;
    public Spinner campoPrecioBase;
    public Spinner campoPrecioExtras;
    public Label labelNumActividades;
    public Label labelError;

    public vNovaTarifaController(FachadaAplicacion fachadaAplicacion) {
        super(fachadaAplicacion);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.labelNumActividades.setText(String.valueOf((int)this.campoActividades.getValue()));
    }

    public void btnGardarAccion(ActionEvent actionEvent) {
        if(ValidacionDatos.estanCubertosCampos(campoNome)){
            /*
                comprobar que a tarifa non existe xa
             */
            super.getFachadaAplicacion().insertarTarifa(new Tarifa(
                    this.campoNome.getText(),
                    (int)this.campoActividades.getValue(),
                    ((Double)this.campoPrecioBase.getValue()).floatValue(),
                    ((Double)this.campoPrecioExtras.getValue()).floatValue()
            ));
            super.getFachadaAplicacion().mostrarInformacion("Tarifa","Creouse a tarifa "+campoNome.getText()+" correctamente");
            /*
                volver ao menu
             */
        }else{
            labelError.setText("Algún campo sen cubrir.");
        }
    }

    public void listenerSlider(MouseEvent mouseEvent) {
        this.labelNumActividades.setText(String.valueOf((int)this.campoActividades.getValue()));
    }
}

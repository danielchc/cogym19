package centrodeportivo.gui.controladores.persoal.tarifas;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.tarifas.Tarifa;
import centrodeportivo.aplicacion.obxectos.usuarios.Socio;
import centrodeportivo.funcionsAux.ValidacionDatos;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.persoal.PantallasPersoal;
import centrodeportivo.gui.controladores.principal.IdPantalla;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
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
    private Tarifa tarifaModificar;
    private FachadaAplicacion fachadaAplicacion;
    private vPrincipalController vPrincipal;
    public vNovaTarifaController(FachadaAplicacion fachadaAplicacion, vPrincipalController vPrincipalController) {
        super(fachadaAplicacion,vPrincipalController);
        this.fachadaAplicacion=super.getFachadaAplicacion();
        this.vPrincipal=vPrincipalController;
    }

    private void cargarTarifa(){
        campoNome.setDisable(false);
        if(tarifaModificar==null)return;
        campoNome.setDisable(true);
        campoNome.setText(tarifaModificar.getNome());
        campoActividades.setValue(tarifaModificar.getMaxActividades());
        labelNumActividades.setText(tarifaModificar.getMaxActividades().toString());
        campoPrecioBase.getValueFactory().setValue((double)tarifaModificar.getPrezoBase());
        campoPrecioExtras.getValueFactory().setValue((double)tarifaModificar.getPrezoExtras());
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.labelNumActividades.setText(String.valueOf((int)this.campoActividades.getValue()));
        this.tarifaModificar=null;
    }

    public void btnGardarAccion(ActionEvent actionEvent) {
        if(!ValidacionDatos.estanCubertosCampos(campoNome)){
            labelError.setText("Algún campo sen cubrir.");
            return;
        }

        Tarifa tarifa=new Tarifa(
                this.campoNome.getText(),
                (int)this.campoActividades.getValue(),
                ((Double)this.campoPrecioBase.getValue()).floatValue(),
                ((Double)this.campoPrecioExtras.getValue()).floatValue()
        );
        System.out.println(tarifaModificar);
        if(tarifaModificar!=null){

            tarifa.setCodTarifa(tarifaModificar.getCodTarifa());
            fachadaAplicacion.actualizarTarifa(tarifa);
            fachadaAplicacion.mostrarInformacion("Tarifas","Gardaronse os cambios  na tarifa "+campoNome.getText()+" correctamente");
        }else{
            if(this.fachadaAplicacion.existeTarifa(campoNome.getText())){
                fachadaAplicacion.mostrarErro("Error", "Xa existe unha tarifa co nome "+campoNome.getText());
                return;
            }
            fachadaAplicacion.insertarTarifa(tarifa);
            fachadaAplicacion.mostrarInformacion("Tarifas","Creouse a tarifa "+campoNome.getText()+" correctamente");
        }
        this.vPrincipal.volverAtras();

    }

    public void listenerSlider(MouseEvent mouseEvent) {
        this.labelNumActividades.setText(String.valueOf((int)this.campoActividades.getValue()));
    }

    public void setTarifa(Tarifa tarifa) {
        this.tarifaModificar = tarifa;
        cargarTarifa();
    }
}

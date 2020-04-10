package centrodeportivo.gui.controladores.Materiales;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.funcionsAux.ValidacionDatos;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.principal.IdPantalla;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class vNovoMaterialController extends AbstractController implements Initializable {


    // Atributos públicos - trátase dos campos da interface aos que queremos acceder:
    public ComboBox comboTipo;
    public TextField campoEstadoMaterial;
    public ComboBox comboInstalacion;
    public DatePicker campoDataCompraMaterial;
    public ComboBox comboArea;
    public TextField campoPrezoMaterial;
    public Button btnGardarMaterial;
    public Button btnLimparMaterial;


    // Atributos privados: correspóndense con cuestións necesarias para realizar as diferentes xestións.
    private vPrincipalController controllerPrincipal;

    public vNovoMaterialController(FachadaAplicacion fachadaAplicacion, vPrincipalController controllerPrincipal) {
        super(fachadaAplicacion);
        this.controllerPrincipal = controllerPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Neste caso non é necesario facer nada na inicialización.
    }

}

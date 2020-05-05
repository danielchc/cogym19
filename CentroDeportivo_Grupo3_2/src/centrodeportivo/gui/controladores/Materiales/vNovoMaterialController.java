package centrodeportivo.gui.controladores.Materiales;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.aplicacion.obxectos.area.TipoMaterial;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.principal.IdPantalla;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class vNovoMaterialController extends AbstractController implements Initializable {


    // Atributos públicos - trátase dos campos da interface aos que queremos acceder:
    public ComboBox<TipoMaterial> comboTipoMaterial = new ComboBox<>();
    public TextField campoEstadoMaterial;
    public ComboBox<Instalacion> comboInstalacion = new ComboBox<>();
    public DatePicker campoDataCompraMaterial;
    public ComboBox<Area> comboArea = new ComboBox<>();
    public TextField campoPrezoMaterial;
    public Button btnGardarMaterial;
    public Button btnLimparMaterial;


    // Atributos privados: correspóndense con cuestións necesarias para realizar as diferentes xestións.
    private vPrincipalController controllerPrincipal;
    private Stage primaryStage;

    public vNovoMaterialController(FachadaAplicacion fachadaAplicacion, vPrincipalController controllerPrincipal) {
        super(fachadaAplicacion);
        this.controllerPrincipal = controllerPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Inicializamos o comboBox dos tipos de materiais:
        comboTipoMaterial.setItems(getFachadaAplicacion().listarTiposMateriais());
        comboTipoMaterial.setConverter(new StringConverter<TipoMaterial>() {
            @Override
            public String toString(TipoMaterial object) {
                return object.getNome();
            }

            @Override
            public TipoMaterial fromString(String string) {
                return comboTipoMaterial.getItems().stream().filter(ap ->
                        ap.getNome().equals(string)).findFirst().orElse(null);
            }
        });

        // Inicializamos o comboBox das areas dunha instalacion
        comboArea.setConverter(new StringConverter<Area>() {
            @Override
            public String toString(Area object) {
                return object.getNome();
            }

            @Override
            public Area fromString(String string) {
                return comboArea.getItems().stream().filter(ap ->
                        ap.getNome().equals(string)).findFirst().orElse(null);
            }
        });

        // Inicializamos o comboBox das instalacions
        comboInstalacion.setItems(getFachadaAplicacion().listarInstalacions());

        comboInstalacion.setConverter(new StringConverter<Instalacion>() {
            @Override
            public String toString(Instalacion object) {
                return object.getNome();
            }

            @Override
            public Instalacion fromString(String string) {
                return comboInstalacion.getItems().stream().filter(ap ->
                        ap.getNome().equals(string)).findFirst().orElse(null);
            }
        });

        comboInstalacion.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                comboArea.getItems().clear();
                comboArea.setDisable(true);
            } else {
                comboArea.getItems().setAll(getFachadaAplicacion().listarAreasInstalacion(newValue));
                comboArea.setDisable(false);
                comboArea.setValue(null);
            }
        });

    }

    /**
     * Método que se executa cando se preme o botón asociado a xestionar os tipos de materiais
     *
     * @param actionEvent A acción que tivo lugar.
     */
    public void btnXestionarAction(ActionEvent actionEvent) {
        // Facemos que sexa visible a ventá de administrar os tipos de materiais:
        this.controllerPrincipal.mostrarPantalla(IdPantalla.ADMINISTRARTIPOMATERIAL);
    }

}



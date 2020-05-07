package centrodeportivo.gui.controladores.Materiales;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.aplicacion.obxectos.area.Material;
import centrodeportivo.aplicacion.obxectos.area.TipoMaterial;
import centrodeportivo.aplicacion.obxectos.tipos.TipoResultados;
import centrodeportivo.funcionsAux.ValidacionDatos;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.AuxGUI;
import centrodeportivo.gui.controladores.principal.IdPantalla;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    public Label avisoCampos;


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
        comboTipoMaterial.setItems(FXCollections.observableArrayList(getFachadaAplicacion().buscarTipoMaterial(null)));
        // Facemos que se vexa o nome dos tipos no comboBox:
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
        // Facemos que se vexa o nome das areas no comboBox
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
        comboInstalacion.setItems(FXCollections.observableArrayList(getFachadaAplicacion().buscarInstalacions(null)));
        // Facemos que se vexa o nome das instalacions no comboBox:
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
        // Cargamos as areas en funcion da instalacion seleccionada
        comboInstalacion.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {  // No caso de que non haxa ningunha instalaccion seleccionada
                // Eliminamos as areas que poidesen estar cargadas:
                comboArea.getItems().clear();
                // Deshabilitamos o comboBox:
                comboArea.setDisable(true);
            } else {  // En calquer outro caso
                // Cargamos as areas que se atopen na nova instalación seleccionada
                comboArea.getItems().setAll(getFachadaAplicacion().listarAreasActivas(newValue));
                // Habilitamos de novo a seleccion
                comboArea.setDisable(false);
                // Eliminamos o posible valor que poidese estar seleccionado de antes
                comboArea.setValue(null);
            }
        });

        // Inicializamos o datePicker para que so se poidan escoller datas previas a data actual
        campoDataCompraMaterial.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                // Tomamos dereferencia a data actual
                LocalDate today = LocalDate.now();
                // Deshabilitanse as datas dos cadros valeiros ou de data superior a actual
                setDisable(empty || date.compareTo(today) > 0);
            }
        });


        //Engadimos un listener no campo do prezo para controlar os valores introducidos:
        campoPrezoMaterial.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //Se o novo valor que se quere introducir non concorda co formato de tres dixitos na parte enteira
                //e dous na decimal, entón poñemos o valor antigo:
                if (!newValue.matches("\\d{0,3}([\\.]\\d{0,2})?")) {
                    campoPrezoMaterial.setText(oldValue);
                }
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

    /**
     * Método que representa as accións realizadas ao premer o botón de limpado de campos.
     *
     * @param actionEvent A acción que tivo lugar
     */
    public void btnLimparAction(ActionEvent actionEvent) {
        // Vaciamos os campos de texto
        AuxGUI.vaciarCamposTexto(campoEstadoMaterial, campoPrezoMaterial);
        // Ao mesmo tempo, ocultaremos o campo de aviso de incoherencias, por se apareceu:
        AuxGUI.ocultarCampos(avisoCampos);
        // E por ultimo, vaciamos os comboBox e o dataPicker
        comboArea.setValue(null);
        comboInstalacion.setValue(null);
        comboTipoMaterial.setValue(null);
        campoDataCompraMaterial.setValue(null);
    }

    /**
     * Método que representa as accións realizadas ao premer o botón de gardado de material
     *
     * @param actionEvent A acción que tivo lugar.
     */
    public void btnGardarAction(ActionEvent actionEvent) {
        // Primeiro imos comprobar que os campos non están vacíos:
        if (!ValidacionDatos.estanCubertosCampos(campoEstadoMaterial)) {
            // Se algún campo obligatorio non
            AuxGUI.amosarCampos(avisoCampos);
            return;
        }
        Material material;
        Date fechaCompra = null;
        if (campoDataCompraMaterial.getValue() != null) {
            fechaCompra = Date.valueOf(campoDataCompraMaterial.getValue());
        }
        if (!campoPrezoMaterial.getText().isEmpty()) {
            // Creamos un obxecto instalación con todos os datos facilitados
            material = new Material(comboTipoMaterial.getValue(), comboArea.getValue(), comboInstalacion.getValue(), campoEstadoMaterial.getText(), fechaCompra, Float.parseFloat(campoPrezoMaterial.getText()));
        } else {
            material = new Material(comboTipoMaterial.getValue(), comboArea.getValue(), comboInstalacion.getValue(), campoEstadoMaterial.getText(), fechaCompra);
        }

        // Accedemos á base de datos: intentamos que se efectúe sen problemas dito acceso.
        try {
            // A consulta pódenos devolver varios resultados en función da situación
            TipoResultados res = this.getFachadaAplicacion().darAltaMaterial(material);
            //En función do resultado, mostraremos unha mensaxe ou outra:
            if (res == TipoResultados.correcto) {
                // Correcto -> Imprimimos mensaxe de éxito co ID do material insertado:
                this.getFachadaAplicacion().mostrarInformacion("Materiais",
                        "Creado un material do tipo " + material.getTipoMaterial().getNome() + ".");
                // Volvemos á pantalla principal:
                this.controllerPrincipal.mostrarPantalla(IdPantalla.INICIO);
            }

        } catch (ExcepcionBD e) {
            // Se se recibe unha excepción da base de datos, entón imprímese unha mensaxe informando.
            // Esa mensaxe obtense dentro da excepción, co método getMessage():
            this.getFachadaAplicacion().mostrarErro("Administración de materiais", e.getMessage());
        }
        // Se houbo algún erro, seguirase nesta pantalla.
    }
}



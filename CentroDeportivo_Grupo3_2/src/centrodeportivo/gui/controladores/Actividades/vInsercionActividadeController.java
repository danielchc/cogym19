package centrodeportivo.gui.controladores.Actividades;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.actividades.TipoActividade;
import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.aplicacion.obxectos.tipos.TipoResultados;
import centrodeportivo.aplicacion.obxectos.usuarios.Persoal;
import centrodeportivo.funcionsAux.ValidacionDatos;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.AuxGUI;
import centrodeportivo.gui.controladores.principal.IdPantalla;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class vInsercionActividadeController extends AbstractController implements Initializable {
    public Button btnGardar;
    public Button btnLimpar;
    public TextField campoNome;
    public TextField campoDuracion;
    public Label avisoCampos;
    public ComboBox<Instalacion> comboInstalacions = new ComboBox<>();
    public ComboBox<Area> comboArea = new ComboBox<>();
    public DatePicker campoData;
    public ComboBox<TipoActividade> comboTipoactividade = new ComboBox<>();
    public ComboBox<Persoal> comboProfesor = new ComboBox<>();


    private vPrincipalController controllerPrincipal;

    public vInsercionActividadeController(FachadaAplicacion fachadaAplicacion, vPrincipalController controllerPrincipal) {
        super(fachadaAplicacion);
        this.controllerPrincipal = controllerPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Inicializamos o comboBox dos tipos de actividade:
        comboTipoactividade.setItems(FXCollections.observableArrayList(getFachadaAplicacion().buscarTiposActividades(null)));
        // Facemos que se vexa o nome dos tipos no comboBox:
        comboTipoactividade.setConverter(new StringConverter<TipoActividade>() {
            @Override
            public String toString(TipoActividade object) {
                return object.getNome();
            }

            @Override
            public TipoActividade fromString(String string) {
                return comboTipoactividade.getItems().stream().filter(ap ->
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
        comboInstalacions.setItems(FXCollections.observableArrayList(getFachadaAplicacion().buscarInstalacions(null)));
        // Facemos que se vexa o nome das instalacions no comboBox:
        comboInstalacions.setConverter(new StringConverter<Instalacion>() {
            @Override
            public String toString(Instalacion object) {
                return object.getNome();
            }

            @Override
            public Instalacion fromString(String string) {
                return comboInstalacions.getItems().stream().filter(ap ->
                        ap.getNome().equals(string)).findFirst().orElse(null);
            }
        });

        // Cargamos as areas en funcion da instalacion seleccionada
        comboInstalacions.valueProperty().addListener((observable, oldValue, newValue) -> {
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
        campoData.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                // Tomamos dereferencia a data actual
                LocalDate today = LocalDate.now();
                // Deshabilitanse as datas dos cadros valeiros ou de data superior a actual
                setDisable(empty || date.compareTo(today) > 0);
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
        AuxGUI.vaciarCamposTexto(campoNome, campoDuracion);
        //Ao mesmo tempo, ocultaremos o campo de aviso de incoherencias, por se apareceu:
        //AuxGUI.ocultarCampos(avisoCampos);
    }

    /**
     * Método que representa as accións realizadas ao premer o botón de limpado de campos.
     * @param actionEvent A acción que tivo lugar
     */
    public void btngardar(ActionEvent actionEvent) {


    }
}

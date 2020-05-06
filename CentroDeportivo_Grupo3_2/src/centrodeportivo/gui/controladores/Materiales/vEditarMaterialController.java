package centrodeportivo.gui.controladores.Materiales;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.aplicacion.obxectos.area.Material;
import centrodeportivo.aplicacion.obxectos.area.TipoMaterial;
import centrodeportivo.gui.controladores.AbstractController;
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

public class vEditarMaterialController extends AbstractController implements Initializable {

    // Atributos públicos: referentes a ventá correspondente
    public TextField campoCodigo;
    public ComboBox<TipoMaterial> comboTipoMaterial = new ComboBox<>();
    public TextField campoEstadoMaterial;
    public ComboBox<Instalacion> comboInstalacion = new ComboBox<>();
    public DatePicker campoDataCompraMaterial;
    public ComboBox<Area> comboArea = new ComboBox<>();
    public TextField campoPrezoMaterial;
    public Button btnGardarMaterial;
    public Button btnLimparMaterial;

    // Atributos privados: matenmos o controlador da ventá de procedencia e o material a xestionar
    private vPrincipalController controllerPrincipal;
    private Material material;

    // Constructor:
    public vEditarMaterialController(FachadaAplicacion fachadaAplicacion, vPrincipalController controllerPrincipal) {
        super(fachadaAplicacion);
        this.controllerPrincipal = controllerPrincipal;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Para inicializar, comprobaremos se o material non é null:
        if (material != null) {

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

            // Actualizamos os campos ca información do material pasado
            actualizarCamposMaterial();

        } else {

            // En calquer outro caso, amosamos unha mensaxe co erro e volvemos a pantalla anterior:
            this.getFachadaAplicacion().mostrarErro("Administración de materiais",
                    "Non se detectou ningún material... Saíndo.");
            this.controllerPrincipal.mostrarPantalla(IdPantalla.ADMINISTRARMATERIAIS);
        }
    }

    /**
     * Setter do material a editar
     *
     * @param material A instalación que se vai a querer asociar á pantalla.
     */
    public void setMaterial(Material material) {
        this.material = material;
    }


    /**
     * Método que realiza unha consulta sobre o material e actualiza os campos
     */
    private void actualizarCamposMaterial() {
        // Consultamos o material:
        material = getFachadaAplicacion().consultarMaterial(material);
        if (material != null) {
            // Se o material non e null, cubrimos os campos
            comboTipoMaterial.setValue(material.getTipoMaterial());
            comboInstalacion.setValue(material.getInstalacion());
            comboArea.setValue(material.getArea());
            campoCodigo.setText(String.valueOf(material.getCodMaterial()));
            campoEstadoMaterial.setText(material.getEstado());
            campoDataCompraMaterial.setValue(material.getDataCompra().toLocalDate());
            campoPrezoMaterial.setText(String.valueOf(material.getPrezoCompra()));
        } else {
            // Noutro caso amosase un erro e sairíamos tamén:
            this.getFachadaAplicacion().mostrarErro("Administración de Material",
                    "O material que se pediu xestionar non está na base de datos. Saíndo...");
            this.controllerPrincipal.mostrarPantalla(IdPantalla.ADMINISTRARMATERIAIS);
        }
    }


    /**
     * Acción efectuada ao premer o botón volver.
     *
     * @param actionEvent Evento que tivo lugar.
     */
    public void btnVolverAction(ActionEvent actionEvent) {
        // Volvemos a pantalla de administrar materiais:
        this.controllerPrincipal.mostrarPantalla(IdPantalla.ADMINISTRARMATERIAIS);
    }

}

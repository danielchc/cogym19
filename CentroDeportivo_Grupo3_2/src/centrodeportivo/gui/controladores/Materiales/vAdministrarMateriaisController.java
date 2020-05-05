
package centrodeportivo.gui.controladores.Materiales;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.aplicacion.obxectos.area.Material;
import centrodeportivo.aplicacion.obxectos.area.TipoMaterial;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class vAdministrarMateriaisController extends AbstractController implements Initializable {

    // Atributos públicos: correspóndense con cuestións da ventá correspondente
    public TableView taboaMateriais;
    public Button btnBuscar;
    public Button btnLimpar;
    public Button btnXestionar;
    public ComboBox<TipoMaterial> comboTipoMaterial = new ComboBox<>();
    public ComboBox<Instalacion> comboInstalacion = new ComboBox<>();
    public ComboBox<Area> comboArea = new ComboBox<>();


    // Atributos privados: manteremos o controlador da ventá de procedencia:
    private vPrincipalController controllerPrincipal;

    // Constructor:
    public vAdministrarMateriaisController(FachadaAplicacion fachadaAplicacion, vPrincipalController controllerPrincipal) {
        super(fachadaAplicacion);
        this.controllerPrincipal = controllerPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Neste caso temos que colocar todos os campos na táboa:
        // A primeira columna terá o código do Material:
        TableColumn<Material, Integer> colCodigo = new TableColumn<>("Código");
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codMaterial"));
        // A segunda columna terá o seu tipo:
        TableColumn<Material, String> colTipoMaterial = new TableColumn<>("Tipo material");
        colTipoMaterial.setCellValueFactory(new PropertyValueFactory<>("nomeTipoMaterial"));
        // A terceira columna corresponderase ca instalación na que se atopa:
        TableColumn<Material, String> colInstalacion = new TableColumn<>("Instalación");
        colInstalacion.setCellValueFactory(new PropertyValueFactory<>("nomeInstalacion"));
        // A cuarta columna corresponderase coa área dentro da instalación:
        TableColumn<Material, String> colArea = new TableColumn<>("Área");
        colArea.setCellValueFactory(new PropertyValueFactory<>("nomeArea"));


        // Feito isto, engadimos as columnas:
        taboaMateriais.getColumns().addAll(colCodigo, colTipoMaterial, colInstalacion, colArea);
        // Agora engadimos items:
        taboaMateriais.getItems().addAll(super.getFachadaAplicacion().listarMateriais(null));
        // Establecemos unha selección sobre a táboa (se hai resultados):
        taboaMateriais.getSelectionModel().selectFirst();

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

    }

    /**
     * Método para actualizar a taboa de materiais
     * @param materiaisArrayList Lista dos materiais cos que temos que acutalizar a táboa
     */
    private void actualizarTaboaMateriais(ArrayList<Material> materiaisArrayList){
        // Valeiramos a taboa e engadimos os novos valores
        taboaMateriais.getItems().setAll(materiaisArrayList);
        // Establecemos unha selección sobre a táboa (se hai resultados):
        taboaMateriais.getSelectionModel().selectFirst();
    }
}
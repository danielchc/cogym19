
package centrodeportivo.gui.controladores.Materiales;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.actividades.Curso;
import centrodeportivo.aplicacion.obxectos.area.Material;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class vAdministrarMateriaisController extends AbstractController implements Initializable {

    // Atributos públicos: correspóndense con cuestións da ventá correspondente
    public TableView taboaMateriais;
    public Button btnBuscar;
    public Button btnLimpar;
    public Button btnXestionar;
    public ComboBox comboTipoMaterial;
    public ComboBox comboInstalacion;
    public ComboBox comboArea;


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
        TableColumn<Integer, Material> colCodigo = new TableColumn<>("Código");
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codMaterial"));
        // A segunda columna terá o seu tipo:
        TableColumn<String, Material> colTipoMaterial = new TableColumn<>("Tipo material");
        colTipoMaterial.setCellValueFactory(new PropertyValueFactory<>("tipoMaterial"));
        // A terceira columna corresponderase ca instalación na que se atopa:
        TableColumn<String, Material> colInstalacion = new TableColumn<>("Instalación");
        colInstalacion.setCellValueFactory(new PropertyValueFactory<>("instalacion"));
        // A cuarta columna corresponderase coa área dentro da instalación:
        TableColumn<String, Material> colArea = new TableColumn<>("Área");
        colArea.setCellValueFactory(new PropertyValueFactory<>("area"));


        // Feito isto, engadimos as columnas:
        taboaMateriais.getColumns().addAll(colCodigo, colTipoMaterial, colInstalacion, colArea);
        // Agora engadimos items:
        //taboaMateriais.getItems().addAll(super.getFachadaAplicacion().listarMateriais());
        // Establecemos unha selección sobre a táboa (se hai resultados):
        //taboaMateriais.getSelectionModel().selectFirst();

    }
}
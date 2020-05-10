
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Víctor Barreiro
 * <p>
 * Clase que funciona como controlador da ventá de administración de materiais.
 */
public class vAdministrarMateriaisController extends AbstractController implements Initializable {
    /**
     * Atributos públicos: correspóndense con cuestións da ventá correspondente
     */
    public TableView taboaMateriais;
    public Button btnBuscar;
    public Button btnLimpar;
    public Button btnXestionar;
    public ComboBox<TipoMaterial> comboTipoMaterial;
    public ComboBox<Instalacion> comboInstalacion;
    public ComboBox<Area> comboArea;


    /**
     * Atributos privados: manteremos o controlador da ventá de procedencia:
     */

    private vPrincipalController controllerPrincipal;

    /**
     * Constructor da ventá de administración de materiais:
     *
     * @param fachadaAplicacion   A referencia á fachada da parte de aplicación.
     * @param controllerPrincipal A referencia ao controlador da ventá principal.
     */
    public vAdministrarMateriaisController(FachadaAplicacion fachadaAplicacion, vPrincipalController controllerPrincipal) {
        //Chamamos ao constructor da clase pai (controllerPrincipal):
        super(fachadaAplicacion);
        //Asignámoslle o controlador principal ao atributo correspondente.
        this.controllerPrincipal = controllerPrincipal;
    }

    /**
     * Método que se executa ao abrir a ventá, para inicializar compoñentes.
     *
     * @param url
     * @param resourceBundle
     */
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

        // Desactivamos a función de seleccionar unha area ata que se seleccione unha instalacion
        comboArea.setDisable(true);

        // Inicializamos o comboBox das instalacions
        comboInstalacion.setItems(FXCollections.observableArrayList(getFachadaAplicacion().buscarInstalacions(null)));

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
     *
     * @param materiaisArrayList Lista dos materiais cos que temos que acutalizar a táboa
     */
    private void actualizarTaboaMateriais(ArrayList<Material> materiaisArrayList) {
        // Valeiramos a taboa e engadimos os novos valores
        taboaMateriais.getItems().setAll(materiaisArrayList);
        // Establecemos unha selección sobre a táboa (se hai resultados):
        taboaMateriais.getSelectionModel().selectFirst();
    }

    /**
     * Acción efectuada ao premer o botón para limpar unha búsqueda.
     *
     * @param actionEvent A acción que tivo lugar.
     */
    public void btnLimparAction(ActionEvent actionEvent) {
        // Valeiramos as seleccions do comboBox
        comboInstalacion.setValue(null);
        comboArea.setValue(null);
        comboTipoMaterial.setValue(null);
        // Aproveitamos entón para actualizar a táboa:
        actualizarTaboaMateriais(super.getFachadaAplicacion().listarMateriais(null));
    }

    /**
     * Método que se executa cando se preme o botón asociado a xestionar un material
     *
     * @param actionEvent A acción que tivo lugar.
     */

    public void btnXestionarAction(ActionEvent actionEvent) {
        //Recuperamos primeiro a instalación seleccionada:
        Material material = (Material) taboaMateriais.getSelectionModel().getSelectedItem();
        //Comprobamos se o item seleccionado non é nulo: se o é, é que non se seleccionou ningún item da táboa.
        if (material != null) {
            //Se non é null seguimos adiante.
            //Accedemos ao controlador da ventá de edición dunha instalación:
            ((vEditarMaterialController) this.controllerPrincipal.getControlador(IdPantalla.EDITARMATERIAL)).setMaterial(material);
            // Feito iso, facemos que a ventá visíbel sexa a de edición dunha instalación:
            this.controllerPrincipal.mostrarPantalla(IdPantalla.EDITARMATERIAL);
        } else {
            // En caso de que o item si sexa nulo, haberá que mostrar un erro pedindo unha selección:
            this.getFachadaAplicacion().mostrarErro("Administración de materiais", "Non hai ningún material seleccionado!");
        }

    }


    /**
     * Acción efectuada ao premer o botón para realizar a búsqueda.
     *
     * @param actionEvent A acción que tivo lugar.
     */
    public void btnBuscarAction(ActionEvent actionEvent) {
        // Cando se lle dá ao botón de buscar, hai que efectuar unha busca na Base de Datos segundo os campos dispostos.
        // Se non se cubriu ningún campo, o que faremos será listar todos os materiais
        // Inda que poida parecer redundante, é un xeito de actualizar a información:
        if (comboTipoMaterial.getValue() == null && comboInstalacion.getValue() == null && comboArea.getValue() == null) {
            // Listamos todas as instalacións. Valémonos do auxiliar para reemprazar directamente o contido da táboa:
            actualizarTaboaMateriais(super.getFachadaAplicacion().listarMateriais(null));
        } else {
            // Noutro caso, buscaremos segundo a información dos campos.
            // Creamos unha instalación co que se ten:
            Material material = new Material(comboTipoMaterial.getValue(), comboArea.getValue(), comboInstalacion.getValue());
            // Reemprazamos o contido da táboa buscando polo material:
            actualizarTaboaMateriais(super.getFachadaAplicacion().listarMateriais(material));
        }
    }
}
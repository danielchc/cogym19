package centrodeportivo.gui.controladores.Instalacions;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.funcionsAux.ValidacionDatos;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.AuxGUI;
import centrodeportivo.gui.controladores.principal.IdPantalla;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Víctor Barreiro
 * Clase que funciona como controlador da pantalla de administración de instalacións.
 */
public class vAdministrarInstalacionsController extends AbstractController implements Initializable {


    /**
     * Atributos públicos: correspóndense con partes da interface gráfica:
     */
    public TableView taboaInstalacions;
    public Button btnBuscar;
    public Button btnLimpar;
    public TextField campoNome;
    public TextField campoDireccion;
    public Button btnXestionar;
    public TextField campoTelefono;

    /**
     * Atributo privado: a referencia ao controlador da ventá principal, que como sabemos é sobre a que se amosan
     * as diferentes ventás.
     */
    private vPrincipalController controllerPrincipal;

    /**
     * Constructor do controlador da ventá de administración de instalacións:
     *
     * @param fachadaAplicacion   A referencia á fachada da parte de aplicación.
     * @param controllerPrincipal A referencia ao controlador da ventá principal.
     */
    public vAdministrarInstalacionsController(FachadaAplicacion fachadaAplicacion, vPrincipalController controllerPrincipal) {
        //Chamamos ao constructor da clase pai
        super(fachadaAplicacion);
        //Asignamos o controlador principal ao atributo correspondente:
        this.controllerPrincipal = controllerPrincipal;
    }

    /**
     * Método que se executa ao abrir a ventá para realizar a inicialización:
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Neste caso temos que colocar todos os campos na táboa que amosa as instalacións:
        //A primeira columna terá o código da Instalación:
        TableColumn<Instalacion, Integer> colCodigo = new TableColumn<>("Código");
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codInstalacion"));
        //A segunda columna terá o seu nome:
        TableColumn<Instalacion, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        //A terceira columna corresponderase co número de teléfono:
        TableColumn<Instalacion, String> colTelf = new TableColumn<>("Teléfono");
        colTelf.setCellValueFactory(new PropertyValueFactory<>("numTelefono"));
        //A cuarta columna corresponderase coa dirección da Instalación:
        TableColumn<Instalacion, String> colDir = new TableColumn<>("Dirección");
        colDir.setCellValueFactory(new PropertyValueFactory<>("direccion"));

        //Feito isto, engadimos as columnas:
        taboaInstalacions.getColumns().addAll(colCodigo, colNome, colTelf, colDir);
        //Agora engadimos os items:
        taboaInstalacions.getItems().addAll(super.getFachadaAplicacion().buscarInstalacions(null));
        //Establecemos unha selección sobre a táboa (se hai resultados):
        taboaInstalacions.getSelectionModel().selectFirst();
    }

    /**
     * Acción efectuada ao premer o botón para realizar a búsqueda.
     *
     * @param actionEvent A acción que tivo lugar.
     */
    public void btnBuscarAction(ActionEvent actionEvent) {
        //Cando se lle dá ao botón de buscar, hai que efectuar unha busca na Base de Datos segundo os campos dispostos.
        //Se non se cubriu ningún campo, o que faremos será listar todas as instalacións.
        //Inda que poida parecer redundante, é un xeito de actualizar a información:
        if (!ValidacionDatos.estanCubertosCampos(campoNome) && !ValidacionDatos.estanCubertosCampos(campoTelefono)
                && !ValidacionDatos.estanCubertosCampos(campoDireccion)) {
            //Listamos todas as instalacións. Valémonos do auxiliar para reemprazar directamente o contido da táboa:
            actualizarTaboaInstalacions(super.getFachadaAplicacion().buscarInstalacions(null));
        } else {
            //Noutro caso, buscaremos segundo a información dos campos.
            //Creamos unha instalación co que se ten:
            Instalacion instalacion = new Instalacion(campoNome.getText(), campoTelefono.getText(), campoDireccion.getText());
            //Reemprazamos o contido da táboa buscando pola instalación:
            actualizarTaboaInstalacions(super.getFachadaAplicacion().buscarInstalacions(instalacion));
        }
    }

    /**
     * Acción efectuada ao premer o botón para limpar unha búsqueda.
     *
     * @param actionEvent A acción que tivo lugar.
     */
    public void btnLimparAction(ActionEvent actionEvent) {
        //Vaciaranse os campos e, depaso, listaranse todas as instalacións dispoñibeis de novo:
        AuxGUI.vaciarCamposTexto(campoNome, campoDireccion, campoTelefono);
        //Aproveitamos entón para actualizar a táboa:
        actualizarTaboaInstalacions(super.getFachadaAplicacion().buscarInstalacions(null));
    }

    /**
     * Método que se executa cando se preme o botón asociado a xestionar unha instalación seleccionada.
     *
     * @param actionEvent A acción que tivo lugar.
     */
    public void btnXestionarAction(ActionEvent actionEvent) {
        //Recuperamos primeiro a instalación seleccionada:
        Instalacion instalacion = (Instalacion) taboaInstalacions.getSelectionModel().getSelectedItem();
        //Comprobamos se o item seleccionado non é nulo: se o é, é que non se seleccionou ningún item da táboa.
        if (instalacion != null) {
            //Se non é null seguimos adiante.
            //Accedemos ao controlador da ventá de edición dunha instalación:
            ((vEditarInstalacionController) this.controllerPrincipal.getControlador(IdPantalla.EDITARINSTALACION)).setInstalacion((Instalacion) taboaInstalacions.getSelectionModel().getSelectedItem());
            //Feito iso, facemos que a ventá visíbel sexa a de edición dunha instalación:
            this.controllerPrincipal.mostrarPantalla(IdPantalla.EDITARINSTALACION);
        } else {
            //En caso de que o item si sexa nulo, haberá que mostrar un erro pedindo unha selección:
            this.getFachadaAplicacion().mostrarErro("Administración de instalacións", "Non hai celda seleccionada!");
        }

    }

    /**
     * Método que nos permite actualizar a táboa de instalacións.
     *
     * @param instalacionArrayList As instalacións coas que se quere actualizar a táboa.
     */
    private void actualizarTaboaInstalacions(ArrayList<Instalacion> instalacionArrayList) {
        //O primeiro que faremos será vaciar a táboa de instalacións:
        taboaInstalacions.getItems().removeAll(taboaInstalacions.getItems());
        //Agora, insertaremos todos os items que se pasen como argumento:
        taboaInstalacions.getItems().addAll(instalacionArrayList);
        //Establecemos unha selección sobre a táboa (se hai resultados):
        taboaInstalacions.getSelectionModel().selectFirst();
    }
}

package centrodeportivo.gui.controladores.Actividades;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.actividades.Actividade;
import centrodeportivo.aplicacion.obxectos.actividades.TipoActividade;
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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Víctor Barreiro
 * Clase que servirá de controlador da pantalla de administración de tipos de actividades.
 */
public class vAdministrarActividadeController extends AbstractController implements Initializable {

    /**
     * Atributos públicos: correspóndense con elementos que se atopan na ventá de administración de tipos de
     * actividades.
     */
    public TextField campoNome;
    public Button btnBuscar;
    public Button btnLimpar;
    public TableView taboaActividade;
    public Button btnRexistrar;

    /**
     * Atributos privados: somentes temos un que é a referencia ao controlador da ventá principal.
     */
    private vPrincipalController controllerPrincipal;

    /**
     * Constructor do controlador da pantalla de administración de tipos de actividades.
     * @param fachadaAplicacion A referencia á fachada da parte de aplicación.
     * @param controllerPrincipal A referencia ao controlador da ventá principal.
     */
    public vAdministrarActividadeController(FachadaAplicacion fachadaAplicacion, vPrincipalController controllerPrincipal) {
        //Chamamos ao constructor da clase pai coa fachada da aplicación.
        super(fachadaAplicacion);
        //Asignamos o controlador principal ao atributo correspondente.
        this.controllerPrincipal = controllerPrincipal;
    }

    /**
     * Método que nos permite inicializar a pantalla ao abrila.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Haberá que colocar todos os campos na táboa correspondente:
        //A primeira terá a data
        TableColumn<Actividade, Timestamp> coldata = new TableColumn<>("Data");
        coldata.setCellValueFactory(new PropertyValueFactory<>("data"));
        //A segunda terá o nome da actividade
        TableColumn<Actividade, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        //A terceira terá o curso
        TableColumn<Actividade, String> colcurso = new TableColumn<>("Curso");
        colcurso.setCellValueFactory(new PropertyValueFactory<>("curso"));

        TableColumn<Actividade, Timestamp> colduracion = new TableColumn<>("Duracion");
        colduracion.setCellValueFactory(new PropertyValueFactory<>("duracion"));

        TableColumn<Actividade, String> coltipoactividade = new TableColumn<>("Tipo Actividade");
        coltipoactividade.setCellValueFactory(new PropertyValueFactory<>("tipoactividade"));

        TableColumn<Actividade, String> colprofesor = new TableColumn<>("Profesor");
        colprofesor.setCellValueFactory(new PropertyValueFactory<>("profesor"));


        //Engadimos as columnas á táboa
        taboaActividade.getColumns().addAll(coldata, colNome, colcurso, colduracion, coltipoactividade, colprofesor);
        //Engadimos os items dispoñíbeis no momento:
        taboaActividade.getItems().addAll(super.getFachadaAplicacion().buscarTiposActividades(null));
        //Establecemos unha selección sobre a táboa, se hai resultados:
        taboaActividade.getSelectionModel().selectFirst();
    }

    /**
     * Método executado cando se pulsa o botón de realizar unha búsqueda
     * @param actionEvent A acción que tivo lugar.
     */
    public void btnBuscarAction(ActionEvent actionEvent) {
        //A búsqueda pódese realizar polo nome.
        //Pero realmente, se ese campo está baleiro, resulta máis sinxelo que se faga un simple listado.
        if(!ValidacionDatos.estanCubertosCampos(campoNome)){
            //Entón, se non se cubriu filtro de busca, simplemente se fai un listado (pasamos null ao método):
            this.refrescarTaboaTiposAct(super.getFachadaAplicacion().buscarTiposActividades(null));
        } else {
            //Se hai algo no campo, buscamos por el:
            TipoActividade tipoActividade = new TipoActividade(campoNome.getText());
            this.refrescarTaboaTiposAct(super.getFachadaAplicacion().buscarTiposActividades(tipoActividade));
        }
    }

    /**
     * Método executado cando se pulsa o botón de limpar. Basicamente limpanse filtros e deixase a lista de tipos
     * completa.
     * @param actionEvent A acción que tivo lugar.
     */
    public void btnLimparAction(ActionEvent actionEvent) {
        //Simplemente vaciamos o campo do nome:
        AuxGUI.vaciarCamposTexto(campoNome);
        //Listamos de novo os tipos de actividade:
        //this.refrescarTaboaTiposAct(super.getFachadaAplicacion().buscarTiposActividades(null));
    }

}
package centrodeportivo.gui.controladores.Actividades;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.actividades.Actividade;
import centrodeportivo.aplicacion.obxectos.actividades.Curso;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.funcionsAux.ValidacionDatos;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.Instalacions.vEditarInstalacionController;
import centrodeportivo.gui.controladores.principal.IdPantalla;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import javax.swing.*;
import java.net.URL;
import java.sql.Timestamp;
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

    private Actividade actividade;

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

        TableColumn<Actividade, Timestamp> coldata = new TableColumn<>("Data");
        coldata.setCellValueFactory(new PropertyValueFactory<>("Data"));

        TableColumn<Actividade, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Actividade, Timestamp> colduracion = new TableColumn<>("Duracion");
        colduracion.setCellValueFactory(new PropertyValueFactory<>("duracion"));

        TableColumn<Actividade, String> colarea = new TableColumn<>("Area");
        colarea.setCellValueFactory(new PropertyValueFactory<>("areanome"));
        colarea.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Actividade, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Actividade, String> param) {
                return new SimpleObjectProperty<String>(param.getValue().getArea().getNome());
            }
        });

        TableColumn<Actividade, String> colinstalacion = new TableColumn<>("Instalacion");
        colinstalacion.setCellValueFactory(new PropertyValueFactory<>("instalacion"));
        colinstalacion.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Actividade, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Actividade, String> param) {
                return new SimpleObjectProperty<String>(param.getValue().getArea().getInstalacion().getNome());
            }
        });

        TableColumn<Actividade, Timestamp> colprofesor = new TableColumn<>("Profesor");
        colprofesor.setCellValueFactory(new PropertyValueFactory<>("profesor"));

        //Engadimos as columnas á táboa
        taboaActividade.getColumns().addAll(coldata, colNome, colduracion, colarea, colinstalacion, colprofesor);

        actualizarTabla();
    }


    private void actualizarTabla(){
        taboaActividade.getItems().removeAll(taboaActividade.getItems());
        if (ValidacionDatos.estanCubertosCampos(campoNome))
            actividade.setNome(campoNome.getText());
        else
            actividade = null;

        //buscar segundo os parametros anteriores
        taboaActividade.getItems().addAll(super.getFachadaAplicacion().buscarActividade(actividade));
        if(taboaActividade.getItems().size()!=0){
            taboaActividade.getSelectionModel().selectFirst();
        }
    }

    public void btnBuscarAction(ActionEvent actionEvent){
        actualizarTabla();
    }

    public void btnXestionarAction(ActionEvent actionEvent){
        if(!taboaActividade.getSelectionModel().isEmpty()) {
            //Recuperamos primeiro a actividade seleccionada:
            Actividade act = (Actividade) taboaActividade.getSelectionModel().getSelectedItem();
            //Comprobamos se o item seleccionado non é nulo: se o é, é que non se seleccionou ningún item da táboa.
            if(act != null){
                //Se non é null seguimos adiante.
                //Feito iso, facemos que a ventá visíbel sexa a de edición dunha actividade:
                this.controllerPrincipal.mostrarPantalla(IdPantalla.INSERCIONACTIVIDADE);
                //Accedemos ao controlador da ventá de edición dunha actividade:
                ((vInsercionActividadeController)this.controllerPrincipal.getControlador(IdPantalla.INSERCIONACTIVIDADE)).cargarActividade((Actividade) taboaActividade.getSelectionModel().getSelectedItem());

            } else {
                //En caso de que o item si sexa nulo, haberá que mostrar un erro pedindo unha selección:
                this.getFachadaAplicacion().mostrarErro("Administración de actividades", "Non hai celda seleccionada!");
            }
        }
    }

    public void btnBorrarAction(){
        if(!taboaActividade.getSelectionModel().isEmpty()){

        }
    }

}
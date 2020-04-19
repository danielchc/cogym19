package centrodeportivo.gui.controladores.Cursos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.actividades.Actividade;
import centrodeportivo.aplicacion.obxectos.actividades.Curso;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class vXestionCursoController extends AbstractController implements Initializable {

    //Atributos públicos: correspóndense coas compoñentes desta ventá:
    public TextField campoCodigo;
    public TextField campoNome;
    public TextArea campoDescricion;
    public TextField campoPrezo;
    public Label tagObrigatorios;
    public Button btnActivar;
    public Button btnGardar;
    public TableView taboaActividades;
    public Button btnEngadirActividade;
    public Button btnBorrarActividade;
    public Button btnModificarSeleccion;
    public TableView taboaUsuarios;


    //Privados
    private vPrincipalController controllerPrincipal;
    private Curso curso;

    //Constructor:
    public vXestionCursoController(FachadaAplicacion fachadaAplicacion, vPrincipalController controllerPrincipal) {
        super(fachadaAplicacion);
        this.controllerPrincipal = controllerPrincipal;
    }

    //Método para inicializar a ventá:
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Hai que levar a cabo varias tarefas, e pode haber dúas situacións:
        //Situación 1 - Non hai curso asociado á clase (NULL).
        //Situación 2 - Hai curso asociado á clase.

        //Igualmente, en ambos casos hai que inicializar as táboas:
        TableColumn<Date, Actividade> dataActividadeColumn = new TableColumn<>("Data");
        dataActividadeColumn.setCellValueFactory(new PropertyValueFactory<>("data"));

        TableColumn<Date, Actividade> areaColumn = new TableColumn<>("Area");
        areaColumn.setCellValueFactory(new PropertyValueFactory<>("area"));

        TableColumn<Date, Actividade> duracionColumn = new TableColumn<>("Duración");
        duracionColumn.setCellValueFactory(new PropertyValueFactory<>("duracion"));

        TableColumn<Date, Actividade> profesorColumn = new TableColumn<>("Profesor");
        profesorColumn.setCellValueFactory(new PropertyValueFactory<>("profesor"));

        taboaActividades.getColumns().addAll(dataActividadeColumn, areaColumn, duracionColumn, profesorColumn);

        //Situación 1:
        if(curso == null){

        }

    }


    public void btnActivarAction(ActionEvent actionEvent) {
    }

    public void btnGardarAction(ActionEvent actionEvent) {
    }

    public void btnEngadirActividadeAction(ActionEvent actionEvent) {
    }

    public void btnBorrarActividadeAction(ActionEvent actionEvent) {
    }

    public void btnModificarSeleccionAction(ActionEvent actionEvent) {
    }
}

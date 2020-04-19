package centrodeportivo.gui.controladores.Cursos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.actividades.Curso;
import centrodeportivo.gui.controladores.AbstractController;
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
import java.util.ResourceBundle;

public class vAdministrarCursosController extends AbstractController implements Initializable {

    //Atributos públicos:
    public TextField campoNome;
    public Button btnBuscar;
    public Button btnLimpar;
    public TableView taboaCursos;
    public Button btnXestionar;

    //Atributos privados:
    vPrincipalController controllerPrincipal;

    public vAdministrarCursosController(FachadaAplicacion fachadaAplicacion, vPrincipalController controllerPrincipal){
        super(fachadaAplicacion);
        this.controllerPrincipal = controllerPrincipal;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //O primeiro paso será inicializar a táboa na que se amosan os cursos:
        TableColumn<String, Curso> nomeColumn = new TableColumn<>("Nome");
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Timestamp, Curso> dataInicioColumn = new TableColumn<>("Data Inicio");
        dataInicioColumn.setCellValueFactory(new PropertyValueFactory<>("dataInicio"));

        TableColumn<Integer, Curso> numActividadesColumn = new TableColumn<>("Numero de Actividades");
        numActividadesColumn.setCellValueFactory(new PropertyValueFactory<>("numActividades"));

        TableColumn<Float, Curso> duracionColumn = new TableColumn<>("Duración");
        duracionColumn.setCellValueFactory(new PropertyValueFactory<>("duracion"));

        TableColumn<Boolean, Curso> abertoColumn = new TableColumn<>("Aberto");
        abertoColumn.setCellValueFactory(new PropertyValueFactory<>("aberto"));

        //Metemos as columnas creadas na táboa:
        taboaCursos.getColumns().addAll(nomeColumn, dataInicioColumn, numActividadesColumn, duracionColumn, abertoColumn);
        //Buscamos os datos dos cursos e engadímolos.
        taboaCursos.getItems().addAll(getFachadaAplicacion().consultarCursos(new Curso("","",0)));
    }

    public void btnBuscarAction(ActionEvent actionEvent) {
    }

    public void btnLimparAction(ActionEvent actionEvent) {
    }

    public void btnXestionarAction(ActionEvent actionEvent) {
    }
}

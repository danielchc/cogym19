package centrodeportivo.gui.controladores.Cursos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.actividades.Curso;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
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



        //Situación 1:
        if(curso == null){
            //Neste primeiro caso
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

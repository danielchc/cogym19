package centrodeportivo.gui.controladores.Cursos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.actividades.Curso;
import centrodeportivo.funcionsAux.ValidacionDatos;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.principal.IdPantalla;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
<<<<<<< HEAD
import com.sun.org.apache.xpath.internal.operations.Bool;
=======
>>>>>>> f9519a368e9c7e74c10670d287f8b5e70fc83cff
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
<<<<<<< HEAD
import jdk.nashorn.internal.codegen.CompilerConstants;
import sun.java2d.pipe.SpanShapeRenderer;
=======
>>>>>>> f9519a368e9c7e74c10670d287f8b5e70fc83cff

import java.net.URL;
import java.text.SimpleDateFormat;
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

        TableColumn<Curso, String> dataInicioColumn = new TableColumn<>("Data Inicio");
        dataInicioColumn.setCellValueFactory(c -> new SimpleStringProperty(
                new SimpleDateFormat("dd/MM/yyyy").format(c.getValue().getDataInicio())
        ));

        TableColumn<Integer, Curso> numActividadesColumn = new TableColumn<>("Numero de Actividades");
        numActividadesColumn.setCellValueFactory(new PropertyValueFactory<>("numActividades"));

        TableColumn<Float, Curso> duracionColumn = new TableColumn<>("Duración");
        duracionColumn.setCellValueFactory(new PropertyValueFactory<>("duracion"));

        TableColumn<Curso, String> abertoColumn = new TableColumn<>("Aberto");
        abertoColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Curso, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Curso, String> p) {
                if (p.getValue().isAberto()) {
                    return new SimpleStringProperty("Si");
                } else {
                    return new SimpleStringProperty("Non");
                }
            }
        });

        //Metemos as columnas creadas na táboa:
        taboaCursos.getColumns().addAll(nomeColumn, dataInicioColumn, numActividadesColumn, duracionColumn, abertoColumn);
        //Buscamos os datos dos cursos e engadímolos.
        taboaCursos.getItems().addAll(getFachadaAplicacion().consultarCursos(null));
        //Modelo de selección:
        taboaCursos.getSelectionModel().selectFirst();
    }

    public void btnBuscarAction(ActionEvent actionEvent) {
        //Primeiro borramos todos os items que hai amosados na táboa:
        taboaCursos.getItems().removeAll(taboaCursos.getItems());

        //Se o campo de nome non se cubre, o que faremos será listar todos os cursos, se non, faremos búsqueda por nome:
        if(!ValidacionDatos.estanCubertosCampos(campoNome)){
            taboaCursos.getItems().addAll(getFachadaAplicacion().consultarCursos(null));
        } else {
            taboaCursos.getItems().addAll(getFachadaAplicacion().consultarCursos(new Curso(campoNome.getText())));
        }
        taboaCursos.getSelectionModel().selectFirst();
    }

    public void btnLimparAction(ActionEvent actionEvent) {
        //Ao limpar os campos, o que facemos será poñer o campo de búsqueda baleiro e refrescar a táboa sen filtros.
        campoNome.setText("");
        //Borramos os items:
        taboaCursos.getItems().removeAll(taboaCursos.getItems());
        //Refrescamos:
        taboaCursos.getItems().addAll(getFachadaAplicacion().consultarCursos(null));
        taboaCursos.getSelectionModel().selectFirst();
    }

    public void btnXestionarAction(ActionEvent actionEvent) {
        //Neste caso, o que teremos que facer é recopilar os datos completos do curso seleccionado:
        //Para iso, empezamos mirando se hai unha selección feita:
        Curso selected = (Curso) taboaCursos.getSelectionModel().getSelectedItem();
        if(selected != null){
            //Gardamos o resultado noutra variable para refrescar toda a información
            //Iso será o que se lle pase á ventá de xestión dun curso:
            Curso curso = getFachadaAplicacion().recuperarDatosCurso(selected);
            if(curso != null){
                //Se houbo resultado, procedemos a pasar á ventá de xestión de cursos este curso:
                //Chamamos á ventá.
                controllerPrincipal.mostrarMenu(IdPantalla.XESTIONCURSO);
                //Asignamos o curso:
                vXestionCursoController cont = (vXestionCursoController) controllerPrincipal.getControlador(IdPantalla.XESTIONCURSO);
                cont.setCurso(curso);
            } else {
                //Se houbo problemas ao recuperar a información, avisamos do erro:
                this.getFachadaAplicacion().mostrarErro("Administración de Cursos",
                        "Produciuse un erro ao recuperar os datos do curso.");
            }
        } else {
            this.getFachadaAplicacion().mostrarErro("Administración de Cursos",
                    "Selecciona un curso primeiro!");
        }
    }
}

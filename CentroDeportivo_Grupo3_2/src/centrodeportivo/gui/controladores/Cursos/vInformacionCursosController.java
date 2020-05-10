package centrodeportivo.gui.controladores.Cursos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.actividades.Actividade;
import centrodeportivo.aplicacion.obxectos.actividades.Curso;
import centrodeportivo.aplicacion.obxectos.usuarios.Socio;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.principal.IdPantalla;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Víctor Barreiro
 * Clase que servirá de controlador da pantalla que amosa información dun curso ó usuario
 */
public class vInformacionCursosController extends AbstractController implements Initializable {


    public TextField campoNome;
    public TextField campoDuracion;
    public TextField campoDataInicio;
    public TextField campoDataFin;
    public TextField campoDescricion;
    public TableView taboaActividades;

    private vPrincipalController controllerPrincipal;
    private Curso curso;

    /**
     * Constructor do controlador da pantalla de administración de tipos de actividades.
     *
     * @param fachadaAplicacion   A referencia á fachada da parte de aplicación.
     * @param controllerPrincipal A referencia ao controlador da ventá principal.
     */
    public vInformacionCursosController(FachadaAplicacion fachadaAplicacion, vPrincipalController controllerPrincipal) {
        // Chamamos ao constructor da clase pai:
        super(fachadaAplicacion);
        // Asignamos o parámetro pasado de controlador da ventá principal ao atributo correspondente:
        this.controllerPrincipal = controllerPrincipal;
    }

    /**
     * Método que nos permite inicializar a pantalla ao abrila.
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        campoNome.setDisable(true);
        campoNome.setText(curso.getNome());
        campoDuracion.setDisable(true);
        campoDuracion.setText(curso.getDuracion().intValue() + "h, " +
                (int) ((curso.getDuracion().floatValue() - curso.getDuracion().intValue()) * 60) + "m");
        campoDataInicio.setDisable(true);
        campoDataInicio.setText(new SimpleDateFormat("dd/MM/yyyy").format(curso.getDataInicio().getTime()));
        campoDataFin.setDisable(true);
        campoDataFin.setText(new SimpleDateFormat("dd/MM/yyyy").format(curso.getDataFin().getTime()));
        campoDescricion.setDisable(true);
        campoDescricion.setText(curso.getDescricion());


        TableColumn<Actividade, String> nomeColumn = new TableColumn<>("Nome");
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Actividade, String> dataActividadeColumn = new TableColumn<>("Data");
        dataActividadeColumn.setCellValueFactory(c -> new SimpleStringProperty(
                new SimpleDateFormat("dd/MM/yyyy").format(c.getValue().getData().getTime())
        ));

        TableColumn<Actividade, String> duracionColumn = new TableColumn<>("Duración");
        duracionColumn.setCellValueFactory(c -> new SimpleStringProperty(
                //Basicamente collo a parte enteira e logo a decimal multiplicada por 60:
                c.getValue().getDuracion().intValue() + "h, " +
                        (int) ((c.getValue().getDuracion().floatValue() - c.getValue().getDuracion().intValue()) * 60) + "m"
        ));

        TableColumn<Actividade, String> areaInstalacionColumn = new TableColumn<>("Area(Instalación)");
        areaInstalacionColumn.setCellValueFactory(c -> new SimpleStringProperty(
                // Basicamente collo a parte enteira e logo a decimal multiplicada por 60:
                c.getValue().getArea().getNome() + "(" +
                        c.getValue().getArea().getInstalacion().getNome() + ")"
        ));

        // Metemos as columnas creadas na táboa:
        taboaActividades.getColumns().addAll(nomeColumn, dataActividadeColumn, duracionColumn, areaInstalacionColumn);
        // Buscamos os datos dos participantes da actividade:
        // taboaActividades.getItems().addAll(getFachadaAplicacion().);
        //TODO: Listar as actividades dun curso ¿? Hai metodo
        //Controlamos tamaño das columnas:
        taboaActividades.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        // Modelo de selección:
        taboaActividades.getSelectionModel().selectFirst();

    }

    public void btnRefrescarAction(ActionEvent actionEvent) {
        // TODO: Volver a listar a información, recuperar de novo o curso e actualizar a taboa; un novo inicializar
    }

    public void setActividade(Curso curso) {
        this.curso = curso;
    }

    public void btnVolverAction(ActionEvent actionEvent) {
        this.controllerPrincipal.mostrarPantalla(IdPantalla.ELIXIRCURSO);
    }


}

package centrodeportivo.gui.controladores.Actividades;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.actividades.Actividade;
import centrodeportivo.aplicacion.obxectos.usuarios.Socio;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.principal.IdPantalla;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
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
 * Clase que servirá de controlador da pantalla que amosa un informe cas actividades
 */
public class vInformeActividadeController extends AbstractController implements Initializable {


    public TextField campoNomeActividade;
    public TextField campoArea;
    public TextField campoInstalacion;
    public TextField campoDataInicio;
    public TextField campoDuracion;
    public TextField campoNomeProfesor;
    public TextField campoValoracion;
    public TableView taboaPersonal;

    private vPrincipalController controllerPrincipal;
    private Actividade actividade;

    /**
     * Constructor do controlador da pantalla de administración de tipos de actividades.
     *
     * @param fachadaAplicacion   A referencia á fachada da parte de aplicación.
     * @param controllerPrincipal A referencia ao controlador da ventá principal.
     */
    public vInformeActividadeController(FachadaAplicacion fachadaAplicacion, vPrincipalController controllerPrincipal) {
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
        campoNomeActividade.setDisable(true);
        campoNomeActividade.setText(actividade.getNome());
        campoArea.setDisable(true);
        campoArea.setText(actividade.getArea().getNome());
        campoInstalacion.setDisable(true);
        campoInstalacion.setText(actividade.getArea().getInstalacion().getNome());
        campoDataInicio.setDisable(true);
        campoDataInicio.setText(new SimpleDateFormat("dd/MM/yyyy").format(actividade.getData().getTime()));
        campoDuracion.setDisable(true);
        campoDuracion.setText(actividade.getDuracion().intValue() + "h, " +
                (int) ((actividade.getDuracion().floatValue() - actividade.getDuracion().intValue()) * 60) + "m");
        campoNomeProfesor.setDisable(true);
        campoNomeProfesor.setText(actividade.getProfesor().getLogin());
        campoValoracion.setDisable(true);
        if (actividade.getValMedia() != null) {
            campoValoracion.setText(actividade.getValMedia().toString());
        } else {
            campoValoracion.setText("-");
        }


        TableColumn<Socio, String> nomeColumn = new TableColumn<>("Nome");
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Socio, String> loginColumn = new TableColumn<>("Login");
        loginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));

        TableColumn<Socio, Integer> idadeColumn = new TableColumn<>("Idade");
        idadeColumn.setCellValueFactory(new PropertyValueFactory<>("idade"));

        TableColumn<Socio, String> dificultadesColumn = new TableColumn<>("Dificultades");
        dificultadesColumn.setCellValueFactory(new PropertyValueFactory<>("dificultades"));

        // Metemos as columnas creadas na táboa:
        taboaPersonal.getColumns().addAll(nomeColumn, loginColumn, idadeColumn, dificultadesColumn);
        // Buscamos os datos dos participantes da actividade:
        taboaPersonal.getItems().addAll(getFachadaAplicacion().listarParticipantes(actividade));
        //Controlamos tamaño das columnas:
        taboaPersonal.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        // Modelo de selección:
        taboaPersonal.getSelectionModel().selectFirst();

    }

    public void btnRefrescarAction(ActionEvent actionEvent) {
    }

    public void setActividade(Actividade actividade) {
        this.actividade = actividade;
    }

    public void btnVolverAction(ActionEvent actionEvent) {
        this.controllerPrincipal.mostrarPantalla(IdPantalla.ADMINACTIVIDADE);
    }


}

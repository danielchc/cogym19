package centrodeportivo.gui.controladores.Actividades;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.actividades.Actividade;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.controlsfx.control.Rating;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Víctor Barreiro
 * Clase que servirá de controlador da pantalla de administración de tipos de actividades.
 */
public class vValorarPopUpController
        extends AbstractController implements Initializable {


    public TextField campoNome;
    public ComboBox comboInstalacion;
    public ComboBox comboArea;
    public TableView taboaActividade;
    public Button btnVolver;

    private vPrincipalController controllerPrincipal;
    private Usuario usuario;
    private Actividade actividade;
    @FXML
    private Rating rate;
    @FXML
    private Label msg;

    /**
     * Constructor do controlador da pantalla de administración de tipos de actividades.
     *
     * @param fachadaAplicacion   A referencia á fachada da parte de aplicación.
     * @param controllerPrincipal A referencia ao controlador da ventá principal.
     */
    public vValorarPopUpController(FachadaAplicacion fachadaAplicacion, vPrincipalController controllerPrincipal, Usuario usuario) {
        // Chamamos ao constructor da clase pai:
        super(fachadaAplicacion);
        // Asignamos o parámetro pasado de controlador da ventá principal ao atributo correspondente:
        this.controllerPrincipal = controllerPrincipal;
        // Asignamos o usuario que esta loggeado
        this.usuario = usuario;

    }

    /**
     * Método que nos permite inicializar a pantalla ao abrila.
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rate.ratingProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            msg.setText("Rating :- " + newValue);
        });
    }


}

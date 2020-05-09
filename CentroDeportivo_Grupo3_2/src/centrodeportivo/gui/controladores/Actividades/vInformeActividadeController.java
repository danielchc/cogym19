package centrodeportivo.gui.controladores.Actividades;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.actividades.Actividade;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.principal.IdPantalla;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import javax.swing.text.TableView;
import java.awt.*;
import java.net.URL;
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
        //ampoActividadeNome.setText(actividade.getNome());

    }

    public void btnXerarInformeAction(ActionEvent actionEvent) {
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

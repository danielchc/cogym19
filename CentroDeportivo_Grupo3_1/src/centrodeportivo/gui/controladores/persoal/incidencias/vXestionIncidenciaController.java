package centrodeportivo.gui.controladores.persoal.incidencias;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.funcionsAux.ListenerTextFieldNumeros;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class vXestionIncidenciaController /*extends AbstractController*/ implements Initializable {


    public TextField campoNumero;
    public TextField campoUsuario;
    public TextArea campoDescricion;
    public DatePicker campoDataIncidencia;
    public TextField campoCusto;
    public DatePicker campoDataResolucion;
    public TextArea campoComentario;

    /*public vXestionIncidenciaController(FachadaAplicacion fachadaAplicacion, vPrincipalController vPrincipalController) {
        super(fachadaAplicacion, vPrincipalController);
    }*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        campoCusto.setOnKeyTyped(new ListenerTextFieldNumeros(campoCusto));
    }

}

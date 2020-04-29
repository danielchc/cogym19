package centrodeportivo.gui.controladores.principal;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.gui.controladores.AbstractController;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Victor Barreiro
 * Clase que funciona coma controlador da ventá de inicio: esta é unha ventá sinxela, que non ten gran cousa salvo un
 * par de figuras.
 */
public class vInicioController extends AbstractController implements Initializable {
    /**
     * Atributo público: compoñente da interface.
     */
    public Label label;

    /**
     * Atributos privados.
     */
    private Usuario usuario; //Usuario que iniciou a sesión na aplicación.
    private vPrincipalController controllerPrincipal; //Referencia ao controlador da ventá principal.

    /**
     * Constructor do controlador da ventá de Inicio:
     * @param fachadaAplicacion Referencia á fachada da parte de aplicación.
     * @param controllerPrincipal Referencia ao controlador da ventá principal.
     * @param usuario O usuario que iniciou sesión na aplicación.
     */
    public vInicioController(FachadaAplicacion fachadaAplicacion, vPrincipalController controllerPrincipal, Usuario usuario) {
        //Chamamos ao constructor da clase pai:
        super(fachadaAplicacion);
        //Asignamos os atributos da clase:
        this.controllerPrincipal = controllerPrincipal;
        this.usuario = usuario;
    }

    /**
     * Método que se invoca cada vez que se abre esta pantalla.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Simplemente se amosará na pantalla unha mensaxe de benvida.
        label.setText("Benvido "+usuario.getNome()+"!");
    }
}

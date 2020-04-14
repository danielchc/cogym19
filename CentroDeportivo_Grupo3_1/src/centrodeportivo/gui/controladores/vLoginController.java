package centrodeportivo.gui.controladores;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.funcionsAux.ValidacionDatos;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public class vLoginController  extends AbstractController implements Initializable {

    /**
     * Atributos do fxml
     */
    public PasswordField tfContrasinal;
    public TextField tfUsuario;
    public Label labelError;

    /**
     * Atributos privados do controlador
     */
    private FachadaAplicacion fa;

    /**
     * Constructor do controlador da pantalla de login
     * @param fachadaAplicacion fachada da aplicación
     */
    public vLoginController(FachadaAplicacion fachadaAplicacion) {
        super(fachadaAplicacion);
        fa=super.getFachadaAplicacion();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventHandler<KeyEvent> handler=new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode() == KeyCode.ENTER) btnIniciarAction();
            }
        };
        tfUsuario.setOnKeyPressed(handler);
        tfContrasinal.setOnKeyPressed(handler);
    }


    /**
     * Listener para o boton de iniciar sesión.
     * Compróbase se os campos están cubertos e faise a validación dos datos introducidos.
     * En caso correcto, consultase o tipo de usuario e iniciase a ventá correspondente.
     */
    public void btnIniciarAction() {
        Usuario usuarioLogeado;
        if(ValidacionDatos.estanCubertosCampos(tfUsuario,tfContrasinal)){
            try{
                if(fa.validarUsuario(tfUsuario.getText(),tfContrasinal.getText())){
                    usuarioLogeado=fa.consultarUsuario(tfUsuario.getText());
                    switch (usuarioLogeado.getTipoUsuario()){
                        case Socio:
                            fa.mostrarVentaSocios(usuarioLogeado);
                            break;
                        case Persoal:
                        case Profesor:
                            fa.mostrarVentaPersoal(usuarioLogeado);
                            break;
                    }
                    ((Stage) tfUsuario.getScene().getWindow()).close();
                }else{
                    labelError.setText("Algún campo incorrecto");
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }else{
            labelError.setText("Algún campo está incompleto");
        }
    }


    /**
     * Getters e Setters
     */
    public FachadaAplicacion getFa() {
        return fa;
    }

    public void setFa(FachadaAplicacion fa) {
        this.fa = fa;
    }
}

package centrodeportivo.gui.controladores;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.funcionsAux.ValidacionDatos;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

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

    }


    /**
     * Listener para o boton de iniciar sesión.
     * Compróbase se os campos están cubertos e faise a validación dos datos introducidos.
     * Encaso correcto, consultase o tipo de usuario e iniciase a ventá correspondente.
     */
    public void btnIniciarAction() {
        if(ValidacionDatos.estanCubertosCampos(tfUsuario,tfContrasinal)){
            try{
                if(fa.validarUsuario(tfUsuario.getText(),tfContrasinal.getText())){
                    switch (fa.consultarTipo(tfUsuario.getText())){
                        case Socio:
                            fa.mostrarVentaSocios(fa.consultarUsuario(tfUsuario.getText()));
                            break;
                        case Persoal:
                        case Profesor:
                            fa.mostrarVentaPersoal(fa.consultarUsuario(tfUsuario.getText()));
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
     * Listener para iniciar sesión ao pulsar enter.
     * @param keyEvent evento
     */
    public void keyPressedIniciar(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER) btnIniciarAction();
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

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
    public PasswordField tfContrasinal;
    public TextField tfUsuario;
    public Label labelError;

    private FachadaAplicacion fa;

    public vLoginController(FachadaAplicacion fachadaAplicacion) {
        super(fachadaAplicacion);
        fa=super.getFachadaAplicacion();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void btnIniciarAction() {
        if(ValidacionDatos.estanCubertosCampos(tfUsuario,tfContrasinal)){
            try{
                if(fa.validarUsuario(tfUsuario.getText(),tfContrasinal.getText())){
                    switch (fa.consultarTipo(tfUsuario.getText())){
                        case Socio:
                            fa.mostrarVentaSocios(fa.consultarUsuario(tfUsuario.getText()));
                            break;
                        case Persoal:
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

    public void keyPressedIniciar(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER) btnIniciarAction();
    }

    public FachadaAplicacion getFa() {
        return fa;
    }

    public void setFa(FachadaAplicacion fa) {
        this.fa = fa;
    }
}

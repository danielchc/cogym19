package centrodeportivo.gui.controladores;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.tipos.TipoUsuario;
import centrodeportivo.aplicacion.xestion.XestionUsuarios;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class vLoginController  implements Initializable {
    private FachadaAplicacion fa;
    @FXML
    private PasswordField tfContrasinal;
    @FXML
    private TextField tfUsuario;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void btnIniciarAction(ActionEvent actionEvent) {
        if( !tfUsuario.getText().trim().equals("") && !tfContrasinal.getText().trim().equals("")){
            try{
                if(fa.validarUsuario(tfUsuario.getText(),tfContrasinal.getText())){
                    switch (fa.consultarTipo(tfUsuario.getText())){
                        case Socio:
                            fa.mostrarVentaSocios();
                            break;
                        case Persoal:
                        case Profesor:
                        case Desconocido: //ÑAPAAAAAAAAAAAAAAAAAAAA
                            fa.mostrarVentaPersoal();
                            break;
                    }
                    ((Stage) tfUsuario.getScene().getWindow()).close();
                }else{
                    System.out.println("Login incorrecto");
                }
            }catch (Exception ex){
                System.out.println(ex);
            }
        }
    }

    public FachadaAplicacion getFa() {
        return fa;
    }

    public void setFa(FachadaAplicacion fa) {
        this.fa = fa;
    }
}

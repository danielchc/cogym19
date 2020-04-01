package centrodeportivo.gui.controladores.persoal.usuarios;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.tarifas.Tarifa;
import centrodeportivo.aplicacion.obxectos.usuarios.Persoal;
import centrodeportivo.aplicacion.obxectos.usuarios.Profesor;
import centrodeportivo.aplicacion.obxectos.usuarios.Socio;
import centrodeportivo.funcionsAux.ValidacionDatos;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.persoal.PantallasPersoal;
import centrodeportivo.gui.controladores.persoal.vPrincipalPersoalController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class vNovoPersoalController  extends AbstractController{
    public CheckBox checkboxProfesor;
    public TextField campoNome;
    public TextField campoLogin;
    public PasswordField campoPassword;
    public TextField campoTelf;
    public TextField campoDNI;
    public TextField campoCorreo;
    public TextField campoIBAN;
    public TextField campoNUSS;
    public Label labelError;
    private vPrincipalPersoalController controllerPrincipal;

    public vNovoPersoalController(FachadaAplicacion fachadaAplicacion, vPrincipalPersoalController controllerPrincipal) {
        super(fachadaAplicacion);
        this.controllerPrincipal=controllerPrincipal;
    }

    public void btnGardarAccion(ActionEvent actionEvent) {
        if(ValidacionDatos.estanCubertosCampos(campoNome,campoLogin,campoCorreo,campoDNI,campoPassword,campoTelf,campoIBAN,campoNUSS)){
            if(!comprobarFormatos()) return;
            if(!comprobarLogin()) return;
            if(!comprobarDNI()) return;
            if(!comprobarNUSS()) return;


            Profesor profesor=new Profesor(
                    campoLogin.getText(),
                    campoPassword.getText(),
                    campoNome.getText(),
                    campoTelf.getText(),
                    campoDNI.getText(),
                    campoCorreo.getText(),
                    campoIBAN.getText(),
                    campoNUSS.getText()
            );

            if(checkboxProfesor.isSelected()) super.getFachadaAplicacion().insertarUsuario(profesor);
            else super.getFachadaAplicacion().insertarUsuario((Persoal)profesor);
            super.getFachadaAplicacion().mostrarInformacion("Usuario","Creouse o usuario "+profesor.getLogin() +" correctamente");
            controllerPrincipal.mostrarMenu(PantallasPersoal.INICIO);
        }else{
            this.labelError.setText("Algún campo sen cubrir.");
        }
    }

    private boolean comprobarFormatos(){
        if(!ValidacionDatos.isCorrectoTelefono(this.campoTelf.getText())){
            this.labelError.setText("Teléfono incorrecto.");
            return false;
        }
        if(!ValidacionDatos.isCorrectoCorreo(this.campoCorreo.getText())){
            this.labelError.setText("Correo incorrecto.");
            return false;
        }
        if(!ValidacionDatos.isCorrectoDNI(this.campoDNI.getText())){
            this.labelError.setText("DNI incorrecto.");
            return false;
        }
        if(!ValidacionDatos.isCorrectoIBAN(this.campoIBAN.getText())){
            this.labelError.setText("IBAN incorrecto.");
            return false;
        }
        if(!ValidacionDatos.isCorrectoNUSS(this.campoNUSS.getText())){
            this.labelError.setText("NUSS incorrecto.");
            return false;
        }
        return true;
    }

    private boolean comprobarLogin(){
        if(super.getFachadaAplicacion().existeUsuario(campoLogin.getText())){
            super.getFachadaAplicacion().mostrarAdvertencia("Usuario","O login "+campoLogin.getText()+" xa está en uso.");
            return false;
        }
        return true;
    }

    private boolean comprobarDNI(){
        if(super.getFachadaAplicacion().existeDNI(campoDNI.getText())){
            super.getFachadaAplicacion().mostrarAdvertencia("Usuario","O DNI "+campoDNI.getText()+" xa está rexistrado.");
            return false;
        }
        return true;
    }

    private boolean comprobarNUSS(){
        if(super.getFachadaAplicacion().existeNUSS(campoNUSS.getText())){
            super.getFachadaAplicacion().mostrarAdvertencia("Usuario","O NUSS "+campoNUSS.getText()+" xa está rexistrado.");
            return false;
        }
        return true;
    }
}

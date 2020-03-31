package centrodeportivo.gui.controladores.persoal.usuarios;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.tarifas.Tarifa;
import centrodeportivo.aplicacion.obxectos.usuarios.Socio;
import centrodeportivo.funcionsAux.ValidacionDatos;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.persoal.PantallasPersoal;
import centrodeportivo.gui.controladores.persoal.vPrincipalPersoalController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class vNovoSocioController extends AbstractController implements Initializable   {

    public ComboBox comboTarifa;
    public TextField campoNome;
    public TextField campoLogin;
    public PasswordField campoPassword;
    public TextField campoTelf;
    public TextField campoDNI;
    public TextField campoCorreo;
    public TextField campoIBAN;
    public DatePicker campoData;
    public TextArea campoDificultades;
    public Button btnGadar;
    public Label labelError;

    private vPrincipalPersoalController controllerPrincipal;

    public vNovoSocioController(FachadaAplicacion fachadaAplicacion, vPrincipalPersoalController controllerPrincipal) {
        super(fachadaAplicacion);
        this.controllerPrincipal=controllerPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<Tarifa> tarifas= null;
        try {
            tarifas = super.getFachadaAplicacion().listarTarifas();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.comboTarifa.getItems().addAll(tarifas);
        this.comboTarifa.getSelectionModel().selectFirst();
    }

    public void btnGardarAccion(ActionEvent actionEvent) {
        if(ValidacionDatos.estanCubertosCampos(campoNome,campoLogin,campoCorreo,campoDNI,campoPassword,campoTelf,campoIBAN)){
            if(!comprobarFormatos()) return;
            if(!comprobarDataMais16anos()) return;
            String nome=campoNome.getText();
            String login=campoLogin.getText();
            String pass=campoPassword.getText();
            String tlf=campoTelf.getText();
            String dni=campoDNI.getText();
            String correo=campoCorreo.getText();
            String iban=campoIBAN.getText();
            Date data=Date.valueOf(campoData.getValue());
            Tarifa tarifa=(Tarifa) comboTarifa.getSelectionModel().getSelectedItem();
            String dificultades=campoDificultades.getText();

            try {
                super.getFachadaAplicacion().insertarUsuario(new Socio(login,pass,nome,tlf,dni,correo,iban,data,dificultades,tarifa));
            } catch (SQLException e) {
                e.printStackTrace();
            }

            this.controllerPrincipal.mostrarMenu(PantallasPersoal.INICIO);
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
        if(campoData.getValue()==null){
            this.labelError.setText("Data non introducida");
            return false;
        }
        return true;
    }

    private boolean comprobarDataMais16anos(){
        LocalDate data=this.campoData.getValue();
        LocalDate fechaLimite=LocalDate.now().minus(Period.ofYears(16));
        if(data.compareTo(fechaLimite)>0){
            this.labelError.setText("O usuario debe ter polo menos 16 anos.");
            return false;
        }
        return true;
    }
}

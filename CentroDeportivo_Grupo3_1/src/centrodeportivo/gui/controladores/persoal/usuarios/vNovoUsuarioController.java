package centrodeportivo.gui.controladores.persoal.usuarios;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.tarifas.Tarifa;
import centrodeportivo.aplicacion.obxectos.tipos.ContasPersoa;
import centrodeportivo.aplicacion.obxectos.tipos.TipoUsuario;
import centrodeportivo.aplicacion.obxectos.usuarios.PersoaFisica;
import centrodeportivo.aplicacion.obxectos.usuarios.Persoal;
import centrodeportivo.aplicacion.obxectos.usuarios.Socio;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.funcionsAux.ValidacionDatos;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.principal.IdPantalla;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class vNovoUsuarioController extends AbstractController implements Initializable {

    public ComboBox tipoUsuario;
    public TextField campoNome;
    public TextField campoLogin;
    public PasswordField campoPassword;
    public TextField campoTelf;
    public TextField campoDNI;
    public TextField campoCorreo;
    public TextField campoIBAN;
    public TextField campoNUSS;
    public ComboBox comboTarifa;
    public DatePicker campoData;
    public TextArea campoDificultades;
    public Label labelError;
    public CheckBox checkProfesor;
    public Button btnGardar;

    public HBox dataNacementoSocioBox;
    public HBox tarifaSocioBox;
    public HBox dificultadesSocioBox;
    public HBox nussPersoalBox;
    public HBox nomeBox;
    public HBox loginBox;
    public HBox passBox;
    public HBox tlfBox;
    public HBox correoBox;
    public HBox ibanBox;
    public HBox profesorBox;


    enum  RexistroTipo {
        Socio,
        Persoal
    };
    private Usuario usuarioModificar;
    private vPrincipalController controllerPrincipal;
    private FachadaAplicacion fachadaAplicacion;

    public vNovoUsuarioController(FachadaAplicacion fachadaAplicacion, vPrincipalController vPrincipalController) {
        super(fachadaAplicacion,vPrincipalController);
        this.controllerPrincipal=super.getvPrincipalController();
        this.fachadaAplicacion=super.getFachadaAplicacion();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.comboTarifa.getItems().addAll(super.getFachadaAplicacion().listarTarifas());
        this.comboTarifa.getSelectionModel().selectFirst();
        this.tipoUsuario.getItems().addAll(RexistroTipo.values());
        this.tipoUsuario.getSelectionModel().selectFirst();
        this.usuarioModificar=null;
        cambiarTipo();
    }

    private void esconderCampos(){
        dataNacementoSocioBox.setVisible(false);
        tarifaSocioBox.setVisible(false);
        dificultadesSocioBox.setVisible(false);
        nussPersoalBox.setVisible(false);
        nomeBox.setVisible(false);
        loginBox.setVisible(false);
        passBox.setVisible(false);
        tlfBox.setVisible(false);
        correoBox.setVisible(false);
        ibanBox.setVisible(false);
        profesorBox.setVisible(false);
        btnGardar.setDisable(true);
    }

    private void mostrarCamposPersoal(){
        dataNacementoSocioBox.setVisible(true);
        dificultadesSocioBox.setVisible(true);
        nomeBox.setVisible(true);
        loginBox.setVisible(true);
        passBox.setVisible(true);
        tlfBox.setVisible(true);
        correoBox.setVisible(true);
        ibanBox.setVisible(true);
        dataNacementoSocioBox.setManaged(true);
        dificultadesSocioBox.setManaged(true);
        nomeBox.setManaged(true);
        loginBox.setManaged(true);
        passBox.setManaged(true);
        tlfBox.setManaged(true);
        correoBox.setManaged(true);
        ibanBox.setManaged(true);

        nussPersoalBox.setManaged(true);
        nussPersoalBox.setVisible(true);
        profesorBox.setVisible(true);
        profesorBox.setManaged(true);
        tarifaSocioBox.setVisible(false);
        tarifaSocioBox.setManaged(false);
        btnGardar.setDisable(false);
    }

    private void mostrarCamposSocio(){
        dataNacementoSocioBox.setVisible(true);
        dificultadesSocioBox.setVisible(true);
        nomeBox.setVisible(true);
        loginBox.setVisible(true);
        passBox.setVisible(true);
        tlfBox.setVisible(true);
        correoBox.setVisible(true);
        ibanBox.setVisible(true);
        dataNacementoSocioBox.setManaged(true);
        dificultadesSocioBox.setManaged(true);
        nomeBox.setManaged(true);
        loginBox.setManaged(true);
        passBox.setManaged(true);
        tlfBox.setManaged(true);
        correoBox.setManaged(true);
        ibanBox.setManaged(true);

        nussPersoalBox.setManaged(false);
        nussPersoalBox.setVisible(false);
        profesorBox.setVisible(false);
        profesorBox.setManaged(false);
        tarifaSocioBox.setVisible(true);
        tarifaSocioBox.setManaged(true);
        btnGardar.setDisable(false);
    }

    public void btnGardarAccion(ActionEvent actionEvent) {
        if(!ValidacionDatos.estanCubertosCampos(campoNome,campoLogin,campoCorreo,campoDNI,campoPassword,campoTelf,campoIBAN)){
            this.labelError.setText("Algún campo sen cubrir.");
            return;
        }
        if(!comprobarFormatos()) return;
        if(!comprobarDataMais16anos()) return;

        ContasPersoa contasP=super.getFachadaAplicacion().contasPersoaFisica(campoDNI.getText());

        if(contasP==ContasPersoa.Ningunha){
            if(!comprobarDNI()) return;
        }
        if(!comprobarLogin()) return;


        if(this.tipoUsuario.getSelectionModel().getSelectedItem()==RexistroTipo.Socio){
            Tarifa tarifa=(Tarifa) comboTarifa.getSelectionModel().getSelectedItem();
            Socio socio=new Socio(
                    campoLogin.getText(),
                    campoPassword.getText(),
                    campoDNI.getText(),
                    campoNome.getText(),
                    campoDificultades.getText(),
                    Date.valueOf(campoData.getValue()),
                    campoTelf.getText(),
                    campoCorreo.getText(),
                    campoIBAN.getText(),
                    tarifa
            );
            this.fachadaAplicacion.insertarUsuario(socio);
            this.fachadaAplicacion.mostrarInformacion("Usuario","Creouse o usuario "+socio.getLogin() +" correctamente");
        }else{
            Persoal persoal=new Persoal(
                    campoLogin.getText(),
                    campoPassword.getText(),
                    campoDNI.getText(),
                    campoNome.getText(),
                    campoDificultades.getText(),
                    Date.valueOf(campoData.getValue()),
                    campoTelf.getText(),
                    campoCorreo.getText(),
                    campoIBAN.getText(),
                    campoNUSS.getText(),
                    checkProfesor.isSelected()
            );
            this.fachadaAplicacion.insertarUsuario(persoal);
            this.fachadaAplicacion.mostrarInformacion("Usuario","Creouse o usuario "+persoal.getLogin() +" correctamente");
        }
        this.controllerPrincipal.mostrarMenu(IdPantalla.INICIO);
    }

    public void cambiarTipo(){
        if(this.tipoUsuario.getSelectionModel().getSelectedIndex()== RexistroTipo.Socio.ordinal()){
            mostrarCamposSocio();
        }else{
            mostrarCamposPersoal();
        }
    }

    public void dniCambiadoAction(KeyEvent keyEvent){
        this.labelError.setText("");
        ContasPersoa contasP=super.getFachadaAplicacion().contasPersoaFisica(campoDNI.getText());
        switch (contasP){
            case Ningunha:
                this.labelError.setText("");
                this.tipoUsuario.setDisable(false);
                cambiarTipo();
                break;
            case Ambas:
                this.labelError.setText("Esa persoa xa ten contas de Socio e Persoal no sistema.");
                this.tipoUsuario.setDisable(true);
                esconderCampos();
                break;
            case SoloSocio:
                this.labelError.setText("*Esa persoa xa é socio.");
                this.tipoUsuario.getSelectionModel().selectLast();
                this.tipoUsuario.setDisable(true);
                mostrarCamposPersoal();
                break;
            case SoloPersoal:
                this.labelError.setText("*Esa persoa xa é persoal.");
                this.tipoUsuario.getSelectionModel().selectFirst();
                this.tipoUsuario.setDisable(true);
                mostrarCamposSocio();
                break;
        }
        if(contasP!=ContasPersoa.Ningunha){
            PersoaFisica persoaFisica=super.getFachadaAplicacion().consultarPersoaFisica(campoDNI.getText());

            this.campoNome.setText(persoaFisica.getNome());
            this.campoData.setValue(persoaFisica.getDataNacemento().toLocalDate());
            this.campoDificultades.setText(persoaFisica.getDificultades());
            this.campoNome.setEditable(false);
            this.campoData.setEditable(false);
            this.campoDificultades.setEditable(false);
        }else{
            this.campoNome.setText("");
            this.campoDificultades.setText("");
            this.campoNome.setEditable(true);
            this.campoData.setEditable(true);
            this.campoDificultades.setEditable(true);
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


    private void cargarDatosUsuario(){
        /*if(usuarioModificar==null)return;
        this.campoLogin.setText(usuarioModificar.getLogin());
        this.campoNome.setText(usuarioModificar.getNome());
        this.campoCorreo.setText(usuarioModificar.getCorreoElectronico());
        this.campoPassword.setText(usuarioModificar.getContrasinal());
        this.campoDNI.setText(usuarioModificar.getDNI());
        this.campoIBAN.setText(usuarioModificar.getIBANconta());
        this.campoTelf.setText(usuarioModificar.getNumTelefono());
        if(usuarioModificar instanceof Socio){
            Socio socio=(Socio)usuarioModificar;
            this.tipoUsuario.getSelectionModel().select(RexistroTipo.Socio);
            this.comboTarifa.getSelectionModel().select(socio.getTarifa());
            this.campoData.setValue(socio.getDataNacemento().toLocalDate());
            this.campoDificultades.setText(socio.getDificultades());
        }else if(usuarioModificar instanceof Persoal){
            Persoal persoal=(Persoal)usuarioModificar;
            this.tipoUsuario.getSelectionModel().select(RexistroTipo.Persoal);
            this.campoNUSS.setText(persoal.getNUSS());
            if(usuarioModificar instanceof Profesor){
                this.tipoUsuario.getSelectionModel().select(RexistroTipo.Profesor);
            }
        }
        cambiarTipo();*/
    }

    public void setUsuario(Usuario usuario) {
        this.usuarioModificar = usuario;
        cargarDatosUsuario();
    }
}

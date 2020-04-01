package centrodeportivo.gui.controladores.persoal.usuarios;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.tarifas.Tarifa;
import centrodeportivo.aplicacion.obxectos.tipos.TipoUsuario;
import centrodeportivo.aplicacion.obxectos.usuarios.Persoal;
import centrodeportivo.aplicacion.obxectos.usuarios.Profesor;
import centrodeportivo.aplicacion.obxectos.usuarios.Socio;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.funcionsAux.ValidacionDatos;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.principal.IdPantalla;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
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
    public HBox dataNacementoSocioBox;
    public HBox tarifaSocioBox;
    public HBox dificultadesSocioBox;
    public HBox nussPersoalBox;
    enum  RexistroTipo {
        Socio,
        Persoal,
        Profesor
    };
    private Usuario usuario;

    private vPrincipalController controllerPrincipal;

    public vNovoUsuarioController(FachadaAplicacion fachadaAplicacion, vPrincipalController controllerPrincipal) {
        super(fachadaAplicacion);
        this.controllerPrincipal=controllerPrincipal;

    }

    public void cambiarTipo(){
        //Revisar esta pochada
        if(this.tipoUsuario.getSelectionModel().getSelectedIndex()== RexistroTipo.Socio.ordinal()){
            nussPersoalBox.setVisible(false);
            nussPersoalBox.setManaged(false);
            dificultadesSocioBox.setVisible(true);
            dificultadesSocioBox.setManaged(true);
            tarifaSocioBox.setVisible(true);
            tarifaSocioBox.setManaged(true);
            dataNacementoSocioBox.setVisible(true);
            dataNacementoSocioBox.setManaged(true);
        }else{
            nussPersoalBox.setVisible(true);
            nussPersoalBox.setManaged(true);
            dificultadesSocioBox.setVisible(false);
            dificultadesSocioBox.setManaged(false);
            tarifaSocioBox.setVisible(false);
            tarifaSocioBox.setManaged(false);
            dataNacementoSocioBox.setVisible(false);
            dataNacementoSocioBox.setManaged(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.comboTarifa.getItems().addAll(super.getFachadaAplicacion().listarTarifas());
        this.tipoUsuario.getItems().addAll(RexistroTipo.values());
        this.comboTarifa.getSelectionModel().selectFirst();
        this.tipoUsuario.getSelectionModel().selectFirst();
        cambiarTipo();
        cargarDatosUsuario();
    }

    public void btnGardarAccion(ActionEvent actionEvent) {
        if(ValidacionDatos.estanCubertosCampos(campoNome,campoLogin,campoCorreo,campoDNI,campoPassword,campoTelf,campoIBAN)){
            if(!comprobarFormatos()) return;
            if(!comprobarDataMais16anos()) return;
            if(!comprobarLogin()) return;
            if(!comprobarDNI()) return;
            if(this.tipoUsuario.getSelectionModel().getSelectedIndex()==0){
                Tarifa tarifa=(Tarifa) comboTarifa.getSelectionModel().getSelectedItem();
                Socio socio=new Socio(
                        campoLogin.getText(),
                        campoPassword.getText(),
                        campoNome.getText(),
                        campoTelf.getText(),
                        campoDNI.getText(),
                        campoCorreo.getText(),
                        campoIBAN.getText(),
                        Date.valueOf(campoData.getValue()),
                        campoDificultades.getText(),
                        tarifa
                );
                super.getFachadaAplicacion().insertarUsuario(socio);
                super.getFachadaAplicacion().mostrarInformacion("Usuario","Creouse o usuario "+socio.getLogin() +" correctamente");
            }else{
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

                if(this.tipoUsuario.getSelectionModel().getSelectedIndex()==1) super.getFachadaAplicacion().insertarUsuario(profesor);
                else if(this.tipoUsuario.getSelectionModel().getSelectedIndex()==2) super.getFachadaAplicacion().insertarUsuario((Persoal)profesor);
                super.getFachadaAplicacion().mostrarInformacion("Usuario","Creouse o usuario "+profesor.getLogin() +" correctamente");
            }

            this.controllerPrincipal.mostrarMenu(IdPantalla.INICIO);
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
        if(usuario==null)return;
        this.campoLogin.setText(usuario.getLogin());
        this.campoNome.setText(usuario.getNome());
        this.campoCorreo.setText(usuario.getCorreoElectronico());
        this.campoPassword.setText(usuario.getContrasinal());
        this.campoDNI.setText(usuario.getDNI());
        this.campoIBAN.setText(usuario.getIBANconta());
        this.campoTelf.setText(usuario.getNumTelefono());
        if(usuario instanceof Socio){
            Socio socio=(Socio)usuario;
            this.tipoUsuario.getSelectionModel().select(RexistroTipo.Socio);
            //CARGAR TARIFA
            //this.comboTarifa.getSelectionModel().select();
            this.campoData.setValue(socio.getDataNacemento().toLocalDate());
            this.campoDificultades.setText(socio.getDificultades());
        }else if(usuario instanceof Persoal){
            Persoal persoal=(Persoal)usuario;
            this.tipoUsuario.getSelectionModel().select(RexistroTipo.Persoal);
            this.campoNUSS.setText(persoal.getNUSS());
            if(usuario instanceof Profesor){
                this.tipoUsuario.getSelectionModel().select(RexistroTipo.Profesor);
            }
        }
        cambiarTipo();
    }
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}

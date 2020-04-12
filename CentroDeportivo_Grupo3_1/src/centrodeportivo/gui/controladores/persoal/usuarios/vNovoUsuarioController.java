package centrodeportivo.gui.controladores.persoal.usuarios;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.tarifas.Tarifa;
import centrodeportivo.aplicacion.obxectos.tipos.ContasPersoa;
import centrodeportivo.aplicacion.obxectos.tipos.TipoUsuario;
import centrodeportivo.aplicacion.obxectos.usuarios.PersoaFisica;
import centrodeportivo.aplicacion.obxectos.usuarios.Persoal;
import centrodeportivo.aplicacion.obxectos.usuarios.Socio;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.funcionsAux.Criptografia;
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

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public class vNovoUsuarioController extends AbstractController implements Initializable {

    /**
     * Atributos do fxml
     */
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

    /**
     * Caixas para os distintos campos.
     */
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


    /**
     * Atributos privados do controlador.
     */
    enum  RexistroTipo {
        Socio,
        Persoal
    };
    private Usuario usuarioModificar;
    private String loginVello;
    private vPrincipalController controllerPrincipal;
    private FachadaAplicacion fachadaAplicacion;

    /**
     * Constructor do controlador de alta dun novo usuario.
     * @param fachadaAplicacion Fachada da aplicación.
     * @param vPrincipalController Controlador da vista principal.
     */
    public vNovoUsuarioController(FachadaAplicacion fachadaAplicacion, vPrincipalController vPrincipalController) {
        super(fachadaAplicacion,vPrincipalController);
        this.controllerPrincipal=super.getvPrincipalController();
        this.fachadaAplicacion=super.getFachadaAplicacion();
    }

    /**
     * Método para inicicializar a vista.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.comboTarifa.getItems().addAll(super.getFachadaAplicacion().listarTarifas());
        this.comboTarifa.getSelectionModel().selectFirst();
        this.tipoUsuario.getItems().addAll(RexistroTipo.values());
        this.tipoUsuario.getSelectionModel().selectFirst();
        this.usuarioModificar=null;
        cambiarTipo();
    }


    /**
     * Método para realizar a acción de gardar cando se pulsa o botón de gardar.
     * @param actionEvent evento
     */
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
        if(usuarioModificar!=null){
            if(!loginVello.equals(campoLogin.getText()) && !comprobarLogin()) return;
        }else{
            if(!comprobarDNI()) return;
        }

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
            if(usuarioModificar!=null){
                super.getFachadaAplicacion().actualizarUsuario(loginVello,socio,!usuarioModificar.getContrasinal().equals(Criptografia.hashSHA256(campoPassword.getText())));
                this.fachadaAplicacion.mostrarInformacion("Usuario","Modificouse o usuario "+socio.getLogin() +" correctamente");
            }else{
                this.fachadaAplicacion.insertarUsuario(socio);
                this.fachadaAplicacion.mostrarInformacion("Usuario","Creouse o usuario "+socio.getLogin() +" correctamente");
            }
        }else{
            if(!ValidacionDatos.isCorrectoNUSS(campoNUSS.getText())){
                this.labelError.setText("NUSS con formato incorrecto.");
                return;
            }
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
                    campoNUSS.getText().trim(),
                    checkProfesor.isSelected()
            );
            if(usuarioModificar!=null){
                if(fachadaAplicacion.tenClasesPendentes(persoal) && (!checkProfesor.isSelected())){
                    fachadaAplicacion.mostrarErro("Clases pendentes","O persoal "+persoal.getLogin()+ " non pode deixar de ser profesor porque ten clases pendentes.");
                    return;
                }
                super.getFachadaAplicacion().actualizarUsuario(loginVello,persoal,!usuarioModificar.getContrasinal().equals(Criptografia.hashSHA256(campoPassword.getText())));
                this.fachadaAplicacion.mostrarInformacion("Usuario","Modificouse o usuario "+persoal.getLogin() +" correctamente");
            }else{
                this.fachadaAplicacion.insertarUsuario(persoal);
                this.fachadaAplicacion.mostrarInformacion("Usuario","Creouse o usuario "+persoal.getLogin() +" correctamente");
            }
        }
        this.controllerPrincipal.volverAtras();
    }

    /**
     * Método que se executa cando se cambia de tipo de usuario no combobox.
     */
    public void cambiarTipo(){
        if(this.tipoUsuario.getSelectionModel().getSelectedIndex()== RexistroTipo.Socio.ordinal()){
            mostrarCamposSocio();
        }else{
            mostrarCamposPersoal();
        }
    }

    /**
     * Método executado cando se escribe algo no campo do dni.
     * Comproba se existe xa a persoa asociada ao dni escrito e en caso correcto mostra os campos adecuados
     * á situción de dita persoa.
     * @param keyEvent evento
     */
    public void dniCambiadoAction(KeyEvent keyEvent){
        if(usuarioModificar!=null) return;
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

    /**
     * Método para amosar/esconder os campos do formulario.
     * @param mode true para mostrar as caixas, false para non.
     * @param hboxs Caixas a ser tratadas.
     */
    private void visibilidadeHBoxs(boolean mode,HBox...hboxs){
        for(HBox h:hboxs){
            h.setVisible(mode);
        }
    }

    /**
     * Método para activar/desactivar os campos do formulario.
     * @param mode true para mostrar as caixas, false para non.
     * @param hboxs Caixas a ser tratadas.
     */
    private void managedHBoxs(boolean mode,HBox...hboxs){
        for(HBox h:hboxs){
            h.setManaged(mode);
        }
    }

    /**
     * Método para esconder todos os campos do formulario.
     */
    private void esconderCampos(){
        visibilidadeHBoxs(false,
                dataNacementoSocioBox,tarifaSocioBox,dificultadesSocioBox,
                nussPersoalBox,nomeBox,loginBox, passBox,
                tlfBox,correoBox,ibanBox, profesorBox
        );
        btnGardar.setDisable(true);
    }

    /**
     * Método para mostrar so os campos asociados a un novo persoal.
     */
    private void mostrarCamposPersoal(){
        visibilidadeHBoxs(true,
                dataNacementoSocioBox,dificultadesSocioBox,nomeBox,loginBox,passBox,
                tlfBox,correoBox,ibanBox,nussPersoalBox,profesorBox
        );
        managedHBoxs(true,
                dataNacementoSocioBox,dificultadesSocioBox,nomeBox,loginBox,passBox,
                tlfBox,correoBox,ibanBox,nussPersoalBox,profesorBox
        );
        visibilidadeHBoxs(false, tarifaSocioBox);
        managedHBoxs(false, tarifaSocioBox);
        btnGardar.setDisable(false);
    }

    /**
     * Método para mostrar so os campos asociados a un novo socio.
     */
    private void mostrarCamposSocio(){
        visibilidadeHBoxs(true,
                dataNacementoSocioBox,dificultadesSocioBox,nomeBox,loginBox,passBox,
                tlfBox,correoBox,ibanBox,tarifaSocioBox
        );
        managedHBoxs(true,
                dataNacementoSocioBox,dificultadesSocioBox,nomeBox,loginBox,passBox,
                tlfBox,correoBox,ibanBox,tarifaSocioBox
        );
        visibilidadeHBoxs(false, tarifaSocioBox,nussPersoalBox,profesorBox);
        managedHBoxs(false, tarifaSocioBox,nussPersoalBox,profesorBox);
        btnGardar.setDisable(false);
    }


    /**
     * Método para comprobar os formatos dos datos introducidos.
     * @return true se todos os datos están correctos, false noutro caso.
     */
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

    /**
     * Método para comprobar se a data de nacemento introducida é válida.
     * @return true se ten polo menos 16 anos, false noutro caso.
     */
    private boolean comprobarDataMais16anos(){
        LocalDate data=this.campoData.getValue();
        LocalDate fechaLimite=LocalDate.now().minus(Period.ofYears(16));
        if(data.compareTo(fechaLimite)>0){
            this.labelError.setText("O usuario debe ter polo menos 16 anos.");
            return false;
        }
        return true;
    }

    /**
     * Método para comprobar a dispoñibilidade dun login.
     * @return true se é válido, false noutro caso.
     */
    private boolean comprobarLogin(){
        if(super.getFachadaAplicacion().existeUsuario(campoLogin.getText())){
            super.getFachadaAplicacion().mostrarAdvertencia("Usuario","O login "+campoLogin.getText()+" xa está en uso.");
            return false;
        }
        return true;
    }

    /**
     * @return método para comprobar se un dni xa existe.
     */
    private boolean comprobarDNI(){
        if(super.getFachadaAplicacion().existeDNI(campoDNI.getText())){
            super.getFachadaAplicacion().mostrarAdvertencia("Usuario","O DNI "+campoDNI.getText()+" xa está rexistrado.");
            return false;
        }
        return true;
    }


    /**
     * Método para autocompletar os datos dun usuario para modificar.
     */
    private void cargarDatosUsuario(){
        if(usuarioModificar==null)return;
        this.loginVello=usuarioModificar.getLogin();
        campoNome.setText(usuarioModificar.getNome());
        campoLogin.setText(usuarioModificar.getLogin());
        campoPassword.setText(usuarioModificar.getContrasinal());
        campoTelf.setText(usuarioModificar.getNumTelefono());
        campoDNI.setText(usuarioModificar.getDNI());
        campoCorreo.setText(usuarioModificar.getCorreoElectronico());
        campoIBAN.setText(usuarioModificar.getIBANconta());
        campoData.setValue(usuarioModificar.getDataNacemento().toLocalDate());
        campoDificultades.setText(usuarioModificar.getDificultades());

        if(usuarioModificar instanceof Socio){
            Socio socio=(Socio)usuarioModificar;
            this.tipoUsuario.getSelectionModel().select(RexistroTipo.Socio);
            this.comboTarifa.getSelectionModel().select(socio.getTarifa());
        }else if(usuarioModificar instanceof Persoal){
            Persoal persoal=(Persoal)usuarioModificar;
            this.tipoUsuario.getSelectionModel().select(RexistroTipo.Persoal);
            campoNUSS.setText(persoal.getNUSS().trim());
            checkProfesor.setSelected(persoal.getTipoUsuario()==TipoUsuario.Profesor);
        }
        tipoUsuario.setDisable(true);
        campoDNI.setEditable(false);
        campoNUSS.setEditable(false);
        cambiarTipo();
    }

    /**
     * Método para cargar un usuario e podelo modificar.
     * @param usuario usuario a ser modificado
     */
    public void setUsuario(Usuario usuario) {
        this.usuarioModificar = usuario;
        cargarDatosUsuario();
    }
}

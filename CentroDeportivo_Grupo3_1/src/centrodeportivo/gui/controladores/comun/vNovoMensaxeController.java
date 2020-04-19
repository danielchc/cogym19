package centrodeportivo.gui.controladores.comun;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.Mensaxe;
import centrodeportivo.aplicacion.obxectos.tipos.TipoUsuario;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.funcionsAux.ListenerMaxLogitud;
import centrodeportivo.funcionsAux.ValidacionDatos;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.Transicion;
import centrodeportivo.gui.controladores.principal.IdPantalla;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;


/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public class vNovoMensaxeController extends AbstractController implements Initializable {

    /**
     * Atributos do fxml
     */
    public TableView tablaUsuarios;
    public TextField campoEmisor;
    public TextField campoReceptor;
    public TextArea campoMensaxe;
    public ComboBox combo;
    public Label labelError;


    /**
     * Atributos do controlador
     */
    private enum TipoBusqueda {
        UnUsuario,
        Todos,
        Socios,
        Persoal,
        Profesores
    }
    private Usuario emisor;
    private Usuario receptor;
    private TranslateTransition tAbrir;
    private TranslateTransition tCerrar;
    private TipoBusqueda opcionMensaxe;
    private vPrincipalController vPrincipalController;

    /**
     * @param fachadaAplicacion Fachada da aplicación
     * @param vPrincipalController Controlador da vista principal
     */
    public vNovoMensaxeController(FachadaAplicacion fachadaAplicacion, vPrincipalController vPrincipalController) {
        super(fachadaAplicacion,vPrincipalController);
        this.receptor=null;
        this.vPrincipalController=vPrincipalController;
    }

    /**
     * Método para inicializar a vista.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.emisor=super.getvPrincipalController().obterUsuarioLogeado();
        this.campoMensaxe.textProperty().addListener(new ListenerMaxLogitud(campoMensaxe,50));

        this.tAbrir = new TranslateTransition(Duration.millis(100),tablaUsuarios);
        this.tAbrir.setToX(tablaUsuarios.getTranslateX()-tablaUsuarios.getWidth());
        this.tCerrar = new TranslateTransition(Duration.millis(100), tablaUsuarios);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                TranslateTransition t=new TranslateTransition(Duration.millis(.1),tablaUsuarios);
                t.setToX(-(tablaUsuarios.getWidth()));
                t.play();
            }
        });

        this.tablaUsuarios.setPlaceholder(new Label("Non hai usuarios."));
        TableColumn<String, Usuario> column = new TableColumn<>("Usuarios");
        column.setCellValueFactory(new PropertyValueFactory<>("login") );
        this.tablaUsuarios.getColumns().add(column);
        actualizarTabla();

        this.campoEmisor.setEditable(false);
        this.campoEmisor.setText(this.emisor.getLogin());

        this.combo.getItems().addAll(TipoBusqueda.values());
        this.combo.getSelectionModel().selectFirst();
        this.opcionMensaxe=TipoBusqueda.UnUsuario;
        this.combo.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                opcionMensaxe=(TipoBusqueda)observableValue.getValue();
                if(opcionMensaxe==TipoBusqueda.UnUsuario){
                    if(tablaUsuarios.getItems().size()>0){
                        campoReceptor.setText(((Usuario)tablaUsuarios.getSelectionModel().getSelectedItem()).getLogin());
                    }
                }else if(opcionMensaxe==TipoBusqueda.Todos){
                    campoReceptor.setText("Enviar a todos os usuarios");
                }else{
                    campoReceptor.setText("Enviar a todos os "+opcionMensaxe);
                }

                if(opcionMensaxe!=TipoBusqueda.UnUsuario) esconderTabla();
            }
        });
        if(super.getFachadaAplicacion().consultarTipo(this.emisor.getLogin())==TipoUsuario.Socio){
            this.combo.setDisable(true);
        }
    }


    /**
     * Método para abrir a lista dos usuarios.
     */
    private void abrirTabla() {
        if(opcionMensaxe!=TipoBusqueda.UnUsuario) return;
        if ((tablaUsuarios.getTranslateX()) == -(tablaUsuarios.getWidth()) ) {
            tAbrir.play();
        } else {
            esconderTabla();
        }
    }

    /**
     * Método para cerrar a lista dos usuarios.
     */
    private void esconderTabla(){
        tCerrar.setToX(-(tablaUsuarios.getWidth()));
        tCerrar.play();
    }

    /**
     * Método para actualizar a lista dos usuarios.
     */
    private void actualizarTabla(){
        this.tablaUsuarios.getItems().addAll(super.getFachadaAplicacion().listarUsuarios());
        if(this.tablaUsuarios.getItems().size()>0){
            this.tablaUsuarios.getSelectionModel().selectFirst();
            this.tablaUsuarios.getItems().remove(this.emisor);
        }
    }


    /**
     * Este método actualiza o campo do receptor cando se selecciona un usuario da lista.
     * @param mouseEvent evento.
     */
    public void listenerTabla(MouseEvent mouseEvent) {
        if(this.tablaUsuarios.getItems().size()>0){
            Usuario u=(Usuario)tablaUsuarios.getSelectionModel().getSelectedItem();
            this.campoReceptor.setText(u.getLogin());
            this.receptor=u;
        }
        esconderTabla();
    }

    /**
     * Este método abre a lista de usuarios cando se pulsa o campo do receptor.
     * @param mouseEvent evento.
     */
    public void listenerReceptor(MouseEvent mouseEvent){
        abrirTabla();
    }


    /**
     * Este método envía a mensaxe cando se pulsa o botón de enviar.
     * @param actionEvent evento
     */
    public void accionEnviar(ActionEvent actionEvent){
        if(ValidacionDatos.estanCubertosCampos(campoReceptor,campoMensaxe)){
            if(campoMensaxe.getText().length()>500){
                labelError.setText("Os mensaxes deben ter como máx 500 caractéres.");
                return;
            }
            try{
                switch (opcionMensaxe){
                    case UnUsuario:
                        super.getFachadaAplicacion().enviarMensaxe(new Mensaxe(this.emisor,this.receptor,this.campoMensaxe.getText()));
                        break;
                    case Todos:
                        super.getFachadaAplicacion().enviarMensaxe(
                                this.emisor,
                                super.getFachadaAplicacion().listarUsuarios(TipoUsuario.Todos),
                                this.campoMensaxe.getText()
                        );
                        break;
                    case Persoal:
                        super.getFachadaAplicacion().enviarMensaxe(
                                this.emisor,
                                super.getFachadaAplicacion().listarUsuarios(TipoUsuario.Persoal),
                                this.campoMensaxe.getText()
                        );
                        break;
                    case Profesores:
                        super.getFachadaAplicacion().enviarMensaxe(
                                this.emisor,
                                super.getFachadaAplicacion().listarUsuarios(TipoUsuario.Profesor),
                                this.campoMensaxe.getText()
                        );
                        break;
                    case Socios:
                        super.getFachadaAplicacion().enviarMensaxe(
                                this.emisor,
                                super.getFachadaAplicacion().listarUsuarios(TipoUsuario.Socio),
                                this.campoMensaxe.getText()
                        );
                        break;
                }
                super.getFachadaAplicacion().mostrarInformacion("Mensaxe","Mensaxe enviado correctamente");
                this.vPrincipalController.mostrarMenu(IdPantalla.INICIO);
            }catch (ExcepcionBD excepcionBD){
                super.getFachadaAplicacion().mostrarErro("Mensaxes",excepcionBD.getMessage());
            }
        }else{
            labelError.setText("Algún campo sen cubrir.");
        }
    }
}

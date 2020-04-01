package centrodeportivo.gui.controladores.comun;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.Mensaxe;
import centrodeportivo.aplicacion.obxectos.tipos.TipoUsuario;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
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

public class vNovoMensaxeController extends AbstractController implements Initializable {

    public TableView tablaUsuarios;
    public TextField campoEmisor;
    public TextField campoReceptor;
    public TextArea campoMensaxe;
    public ComboBox combo;
    public Label labelError;

    private Usuario emisor;
    private Usuario receptor;
    private TranslateTransition tAbrir;
    private TranslateTransition tCerrar;
    private TipoBusqueda opcionMensaxe;
    private vPrincipalController vPrincipalController;

    public vNovoMensaxeController(FachadaAplicacion fachadaAplicacion, vPrincipalController vPrincipalController, Usuario usuario) {
        super(fachadaAplicacion);
        this.emisor=usuario;
        this.receptor=null;
        this.vPrincipalController=vPrincipalController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
                    campoReceptor.setEditable(true);
                    if(tablaUsuarios.getItems().size()>0){
                        campoReceptor.setText(((Usuario)tablaUsuarios.getSelectionModel().getSelectedItem()).getLogin());
                    }
                }else if(opcionMensaxe==TipoBusqueda.Todos){
                    campoReceptor.setEditable(false);
                    campoReceptor.setText("Enviar a todos os usuarios");
                }else{
                    campoReceptor.setEditable(false);
                    campoReceptor.setText("Enviar a todos os "+opcionMensaxe);
                }
            }
        });
    }


    private void abrirTabla() {
        if(opcionMensaxe!=TipoBusqueda.UnUsuario) return;
        if ((tablaUsuarios.getTranslateX()) == -(tablaUsuarios.getWidth()) ) {
            tAbrir.play();
        } else {
            esconderTabla();
        }
    }

    private void esconderTabla(){
        tCerrar.setToX(-(tablaUsuarios.getWidth()));
        tCerrar.play();
    }


    public void listenerTabla(MouseEvent mouseEvent) {
        if(this.tablaUsuarios.getItems().size()>0){
            Usuario u=(Usuario)tablaUsuarios.getSelectionModel().getSelectedItem();
            this.campoReceptor.setText(u.getLogin());
            this.receptor=u;
        }
        esconderTabla();
    }

    public void listenerReceptor(MouseEvent mouseEvent){
        abrirTabla();
    }

    public void listenerCombo(MouseEvent mouseEvent){
        /*


            MIRAR QUE UN SOCIO NON POIDA CAMBIAR O COMBOBOX


         */
        if(getFachadaAplicacion().consultarTipo(emisor.getLogin())==TipoUsuario.Socio) return;
    }

    private void actualizarTabla(){
        this.tablaUsuarios.getItems().addAll(super.getFachadaAplicacion().listarUsuarios());
        if(this.tablaUsuarios.getItems().size()>0){
            this.tablaUsuarios.getSelectionModel().select(0);
        }
    }

    public void accionEnviar(ActionEvent actionEvent){
        if(ValidacionDatos.estanCubertosCampos(campoReceptor,campoMensaxe)){
            if(campoMensaxe.getText().length()>500){
                labelError.setText("Os mensaxes deben ter como máx 500 caractéres.");
                return;
            }
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
                case Socios:
                    super.getFachadaAplicacion().enviarMensaxe(
                            this.emisor,
                            super.getFachadaAplicacion().listarUsuarios(TipoUsuario.Socio),
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
            }
            super.getFachadaAplicacion().mostrarInformacion("Mensaxe","Mensaxe enviado correctamente");
            this.vPrincipalController.mostrarMenu(IdPantalla.INICIO);
        }else{
            labelError.setText("Algún campo sen cubrir.");
        }
    }
}

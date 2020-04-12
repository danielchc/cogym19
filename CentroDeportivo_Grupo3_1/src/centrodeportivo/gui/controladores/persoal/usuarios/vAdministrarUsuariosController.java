package centrodeportivo.gui.controladores.persoal.usuarios;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.Mensaxe;
import centrodeportivo.aplicacion.obxectos.tipos.TipoUsuario;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.principal.IdPantalla;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public class vAdministrarUsuariosController extends AbstractController implements Initializable {

    /**
     * Atributos do fxml.
     */
    public TableView listaUsuarios;
    public TextField campoNomeBuscar;
    public TextField campoLoginBuscar;
    public ComboBox campoTipoUsuario;
    public CheckBox mostrarUsuariosBaixa;
    public Button btnBorrar;
    public Button btnCapacidades;

    /**
     * Atributos privados do controlador.
     */
    private FachadaAplicacion fachadaAplicacion;
    private vPrincipalController vPrincipal;

    /**
     * Constructor do controlador de administrar usuarios.
     * @param fachadaAplicacion Fachada da aplicación.
     * @param vPrincipal Controlador da vista principal.
     */
    public vAdministrarUsuariosController(FachadaAplicacion fachadaAplicacion, vPrincipalController vPrincipal) {
        super(fachadaAplicacion);
        this.fachadaAplicacion=super.getFachadaAplicacion();
        this.vPrincipal=vPrincipal;
    }


    /**
     * Método para inicializar a vista.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableColumn<Usuario,String> loginColumn = new TableColumn<>("Login");
        loginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
        TableColumn<Usuario,String> nomeColumn = new TableColumn<>("Nome");
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        TableColumn<Usuario,String> dniColumn = new TableColumn<>("DNI");
        dniColumn.setCellValueFactory(new PropertyValueFactory<>("DNI"));
        TableColumn<Usuario,String> correoElectronicoColumn = new TableColumn<>("Correo Electronico");
        correoElectronicoColumn.setCellValueFactory(new PropertyValueFactory<>("correoElectronico"));
        TableColumn<Usuario,String> tipoUsuarioColumn = new TableColumn<>("Tipo Usuario");


        tipoUsuarioColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuario, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuario, String> param) {
                return new SimpleObjectProperty<String>(param.getValue().getTipoUsuario().toString());
            }
        });

        listaUsuarios.setRowFactory(tv -> new TableRow<Usuario>() {
            @Override
            public void updateItem(Usuario item, boolean empty) {
                super.updateItem(item, empty) ;
                if ((item != null)&&(item.estaDeBaixa()))setStyle("-fx-background-color:grey;");
            }
        });

        listaUsuarios.getColumns().addAll(loginColumn,nomeColumn,dniColumn,correoElectronicoColumn,tipoUsuarioColumn);
        listaUsuarios.getItems().addAll(super.getFachadaAplicacion().listarUsuarios());
        if(listaUsuarios.getItems().size()>0){
            listaUsuarios.getSelectionModel().selectFirst();
            listenerTabla();
        }
        campoTipoUsuario.getItems().addAll(TipoUsuario.values());
        campoTipoUsuario.getSelectionModel().selectFirst();
        listaUsuarios.setPlaceholder(new Label("Non se atoparon usuarios"));
    }

    /**
     * Método para buscar os usuarios e engadilos á tabla.
     */
    public void buscarUsuarios(){
        listaUsuarios.getItems().removeAll(listaUsuarios.getItems());
        listaUsuarios.getItems().addAll(fachadaAplicacion.buscarUsuarios(campoLoginBuscar.getText(),campoNomeBuscar.getText(), TipoUsuario.values()[campoTipoUsuario.getSelectionModel().getSelectedIndex()],mostrarUsuariosBaixa.isSelected()));
        if(!listaUsuarios.getItems().isEmpty()){
            listaUsuarios.getSelectionModel().selectFirst();
            listenerTabla();
        }
    }

    /**
     * Método listener do clickear a tabla de datos.
     * Segundo o tipo e situación do usuario seleccionado amosaránse unhos botóns para
     * a xestión ou outros.
     */
    public void listenerTabla(){
        Usuario usuario=((Usuario)listaUsuarios.getSelectionModel().getSelectedItem());
        this.btnCapacidades.setVisible(usuario.getTipoUsuario()!=TipoUsuario.Socio);
        if(usuario.estaDeBaixa()){
            btnBorrar.setText("Reactivar");
        }else{
            btnBorrar.setText("Dar de baixa");
        }
    }


    /**
     * Método para ir á xestión de capacidades dun persoal.
     */
    public void capacidadeUsuario(){
        if(!listaUsuarios.getSelectionModel().isEmpty()) {
            vPrincipal.mostrarMenu(IdPantalla.ADMINISTRARCAPACIDADES);
            ((vAdministrarCapacidadesController) vPrincipal.getControlador(IdPantalla.ADMINISTRARCAPACIDADES)).setUsuario(((Usuario) listaUsuarios.getSelectionModel().getSelectedItem()));
        }
    }

    /**
     * Método para ir á ventá de modificación de datos do usuario seleccionado.
     */
    public void modificarUsuario(){
        if(!listaUsuarios.getSelectionModel().isEmpty()) {
            vPrincipal.mostrarMenu(IdPantalla.NOVOUSUARIO);
            ((vNovoUsuarioController) vPrincipal.getControlador(IdPantalla.NOVOUSUARIO)).setUsuario(((Usuario) listaUsuarios.getSelectionModel().getSelectedItem()));
        }
    }

    /**
     * Método para Desactivar/reactivar o usuario seleccionado segundo este dado de baixa ou non.
     */
    public void activarDesactivarUsuario(){
        if(!listaUsuarios.getSelectionModel().isEmpty()){
            Usuario usuario=(Usuario) listaUsuarios.getSelectionModel().getSelectedItem();
            if(usuario.estaDeBaixa()){
                //se non é nulo entón estaba de baixa e hai que dalo de alta.
                if(fachadaAplicacion.mostrarConfirmacion("Reactivar usuario","Desexa reactivar a conta do usuario "+usuario.getLogin()+ "?")==ButtonType.OK){
                    fachadaAplicacion.darAltaUsuario(usuario.getLogin());
                    fachadaAplicacion.mostrarInformacion("Reactivar usuario","O usuario "+usuario.getLogin()+ " reactivouse correctamente.");
                }
            }else{
                /*

                    COMPROBAR SE É PROFESOR E TEN ALGUNHA ACTIVIDADE PENDENTE
                    NESE CASO MOSTRAR UNHA MENSAXE DE QUE NON SE PODE BORRAR

                 */
                /*if(usuario.equals(super.getvPrincipalController().obterUsuarioLogeado())){
                    if(fachadaAplicacion.mostrarConfirmacion("ATENCIÓN","Estás apunto de darte de baixa a ti mesmo, esta acción fará que saías da aplicación.Queres continuar?")!=ButtonType.OK){
                        return;
                    }
                }*/
                if(fachadaAplicacion.mostrarConfirmacion("Desactivar usuario","Desexa dar de baixa a o usuario "+usuario.getLogin()+ "?")==ButtonType.OK){
                    fachadaAplicacion.darBaixaUsuario(usuario.getLogin());
                    fachadaAplicacion.mostrarInformacion("Desactivar usuario","O usuario "+usuario.getLogin()+ " deuse de baixa correctamente.");
                }
            }
            buscarUsuarios();
        }
    }

}


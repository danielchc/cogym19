package centrodeportivo.gui.controladores.persoal.usuarios;

import centrodeportivo.aplicacion.FachadaAplicacion;
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

public class vAdministrarUsuariosController extends AbstractController implements Initializable {

    public TableView listaUsuarios;
    public TextField campoNomeBuscar;
    public TextField campoLoginBuscar;
    public ComboBox campoTipoUsuario;
    public CheckBox mostrarUsuariosBaixa;
    private FachadaAplicacion fachadaAplicacion;
    private vPrincipalController vPrincipal;

    public vAdministrarUsuariosController(FachadaAplicacion fachadaAplicacion, vPrincipalController vPrincipal) {
        super(fachadaAplicacion);
        this.fachadaAplicacion=super.getFachadaAplicacion();
        this.vPrincipal=vPrincipal;
    }


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

        listaUsuarios.getColumns().addAll(loginColumn,nomeColumn,dniColumn,correoElectronicoColumn,tipoUsuarioColumn);
        listaUsuarios.getItems().addAll(super.getFachadaAplicacion().listarUsuarios());
        campoTipoUsuario.getItems().addAll(TipoUsuario.values());
        campoTipoUsuario.getSelectionModel().selectFirst();
        listaUsuarios.setPlaceholder(new Label("Non se atoparon usuarios"));
    }

    public void buscarUsuarios(){
        listaUsuarios.getItems().removeAll(listaUsuarios.getItems());
        listaUsuarios.getItems().addAll(fachadaAplicacion.buscarUsuarios(campoLoginBuscar.getText(),campoNomeBuscar.getText(), TipoUsuario.values()[campoTipoUsuario.getSelectionModel().getSelectedIndex()],mostrarUsuariosBaixa.isSelected()));
    }

    public void capacidadeUsuario(){
        if(!listaUsuarios.getSelectionModel().isEmpty()) {
            vPrincipal.mostrarMenu(IdPantalla.ADMINISTRARCAPACIDADES);
            ((vAdministrarCapacidadesController) vPrincipal.getControlador(IdPantalla.ADMINISTRARCAPACIDADES)).setUsuario(((Usuario) listaUsuarios.getSelectionModel().getSelectedItem()));
        }
    }

    public void modificarUsuario(){
        if(!listaUsuarios.getSelectionModel().isEmpty()) {
            vPrincipal.mostrarMenu(IdPantalla.NOVOUSUARIO);
            ((vNovoUsuarioController) vPrincipal.getControlador(IdPantalla.NOVOUSUARIO)).setUsuario(((Usuario) listaUsuarios.getSelectionModel().getSelectedItem()));
        }
    }

    public void borrarUsuario(){
        if(!listaUsuarios.getSelectionModel().isEmpty()){
            String login=((Usuario)listaUsuarios.getSelectionModel().getSelectedItem()).getLogin();
            if(fachadaAplicacion.mostrarConfirmacion("Borrar usuario","Desexa dar de baixa a o usuario "+login+ "?")==ButtonType.OK){
                fachadaAplicacion.darBaixaUsuario(login);
                fachadaAplicacion.mostrarInformacion("Borrar usuario","O usuario "+login+ " deuse de baixa correctamente.");
            }
            buscarUsuarios();
        }
    }

}


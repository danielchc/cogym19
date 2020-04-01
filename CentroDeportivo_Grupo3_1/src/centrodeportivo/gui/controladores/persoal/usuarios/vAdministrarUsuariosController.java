package centrodeportivo.gui.controladores.persoal.usuarios;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.tipos.TipoUsuario;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.principal.IdPantalla;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class vAdministrarUsuariosController extends AbstractController implements Initializable {

    public TableView listaUsuarios;
    public TextField campoNomeBuscar;
    public TextField campoLoginBuscar;
    public ComboBox campoTipoUsuario;
    private FachadaAplicacion fachadaAplicacion;
    private vPrincipalController vPrincipal;

    public vAdministrarUsuariosController(FachadaAplicacion fachadaAplicacion, vPrincipalController vPrincipal) {
        super(fachadaAplicacion);
        this.fachadaAplicacion=super.getFachadaAplicacion();
        this.vPrincipal=vPrincipal;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableColumn<Integer, Usuario> loginColumn = new TableColumn<>("Login");
        loginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
        TableColumn<String, Usuario> nomeColumn = new TableColumn<>("Nome");
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        TableColumn<String, Usuario> dniColumn = new TableColumn<>("DNI");
        dniColumn.setCellValueFactory(new PropertyValueFactory<>("DNI"));
        TableColumn<String, Usuario> correoElectronicoColumn = new TableColumn<>("Correo Electronico");
        correoElectronicoColumn.setCellValueFactory(new PropertyValueFactory<>("correoElectronico"));
        TableColumn<String, Usuario> tipoUsuarioColumn = new TableColumn<>("Tipo Usuario");
        tipoUsuarioColumn.setCellValueFactory(new PropertyValueFactory<>("tipoUsuario"));

        listaUsuarios.getColumns().addAll(loginColumn,nomeColumn,dniColumn,correoElectronicoColumn,tipoUsuarioColumn);
        listaUsuarios.getItems().addAll(super.getFachadaAplicacion().listarUsuarios());
        campoTipoUsuario.getItems().addAll(TipoUsuario.values());
        campoTipoUsuario.getSelectionModel().selectFirst();
        listaUsuarios.setPlaceholder(new Label("Non se atoparon usuarios"));
    }

    public void buscarUsuarios(){
        listaUsuarios.getItems().removeAll(listaUsuarios.getItems());
        listaUsuarios.getItems().addAll(fachadaAplicacion.buscarUsuarios(campoLoginBuscar.getText(),campoNomeBuscar.getText(), TipoUsuario.values()[campoTipoUsuario.getSelectionModel().getSelectedIndex()]));
    }

    public void modificarUsuario(){
        if(!listaUsuarios.getSelectionModel().isEmpty()) {
            ((vNovoUsuarioController) vPrincipal.getControlador(IdPantalla.NOVOUSUARIO)).setUsuario(((Usuario) listaUsuarios.getSelectionModel().getSelectedItem()));
            vPrincipal.mostrarMenu(IdPantalla.NOVOUSUARIO);
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


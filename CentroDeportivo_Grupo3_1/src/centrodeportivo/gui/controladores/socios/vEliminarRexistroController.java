package centrodeportivo.gui.controladores.socios;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.RexistroFisioloxico;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class vEliminarRexistroController extends AbstractController implements Initializable {

    /**
     * Atributos do fxml
     */
    public TableView tablaRexistros;
    public Button btnEliminar;


    /**
     * Atributos do controlador
     */
    private Usuario usuario;

    /**
     * Constructor do controlador
     * @param fachadaAplicacion Fachada da aplicación
     */
    public vEliminarRexistroController(FachadaAplicacion fachadaAplicacion, vPrincipalController vPrincipalController) {
        super(fachadaAplicacion,vPrincipalController);
        this.usuario=super.getvPrincipalController().obterUsuarioLogeado();
    }

    /**
     * Método para inicializar o fxml.
     * Aquí cárgase a tabla de rexistros do usuario rexistrado.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.tablaRexistros.setPlaceholder(new Label("Non hai rexistros dispoñibles."));

        TableColumn<Timestamp, RexistroFisioloxico> columna1=new TableColumn<>("Data");
        columna1.setCellValueFactory(new PropertyValueFactory<>("data"));
        TableColumn<Float, RexistroFisioloxico> columna2=new TableColumn<>("Peso");
        columna2.setCellValueFactory(new PropertyValueFactory<>("peso"));
        TableColumn<Float, RexistroFisioloxico> columna3=new TableColumn<>("Altura");
        columna3.setCellValueFactory(new PropertyValueFactory<>("altura"));
        TableColumn<String, RexistroFisioloxico> columna4=new TableColumn<>("Comentario");
        columna4.setCellValueFactory(new PropertyValueFactory<>("comentario"));

        tablaRexistros.getColumns().addAll(columna1,columna2,columna3,columna4);

        cargarRexistrosTabla();
    }

    /**
     * Método para cargar os datos da base na tabla.
     * Se hai datos dispoñibles habilítase o boton para eliminar.
     */
    private void cargarRexistrosTabla(){
        tablaRexistros.getItems().removeAll(tablaRexistros.getItems());
        tablaRexistros.getItems().addAll(super.getFachadaAplicacion().listarRexistros(usuario.getLogin()));

        if(tablaRexistros.getItems().size()>0){
            this.btnEliminar.setDisable(false);
            this.tablaRexistros.getSelectionModel().selectFirst();
        }else{
            this.btnEliminar.setDisable(true);
        }
    }

    /**
     * Método para xestionar a acción do botón de eliminar.
     * Pregúntase pola confirmación do usuario, e en caso afirmativo eliminase o rexistro seleccionado.
     * @param actionEvent evento
     */
    public void btnEliminarAction(ActionEvent actionEvent) {
        if(super.getFachadaAplicacion().mostrarConfirmacion("Rexistro","Estás seguro de que queres eliminar este rexistro?")== ButtonType.CANCEL) return;
        super.getFachadaAplicacion().eliminarRexistro((RexistroFisioloxico) this.tablaRexistros.getSelectionModel().getSelectedItem());
        cargarRexistrosTabla();
    }
}

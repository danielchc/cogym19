package centrodeportivo.gui.controladores.socios;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.RexistroFisioloxico;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.gui.controladores.AbstractController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class vEliminarRexistroController extends AbstractController implements Initializable {

    public TableView tablaRexistros;
    public Button btnEliminar;


    private Usuario usuario;

    public vEliminarRexistroController(FachadaAplicacion fachadaAplicacion, Usuario usuario) {
        super(fachadaAplicacion);
        this.usuario=usuario;
    }

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

    public void btnEliminarAction(ActionEvent actionEvent) {
        super.getFachadaAplicacion().eliminarRexistro((RexistroFisioloxico) this.tablaRexistros.getSelectionModel().getSelectedItem());
        cargarRexistrosTabla();
    }
}

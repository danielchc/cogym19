package centrodeportivo.gui.controladores.persoal.mensaxes;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.Mensaxe;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.gui.controladores.AbstractController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class vMensaxesController extends AbstractController implements Initializable {
    public TableView containerChats;
    public AnchorPane containerMensaxe;
    public Label labelEmisor;
    public Label labelReceptor;
    public Label labelData;
    public Label labelMensaxe;

    private Usuario receptor;

    public vMensaxesController(FachadaAplicacion fachadaAplicacion, Usuario usuario) {
        super(fachadaAplicacion);
        this.receptor=usuario;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.containerMensaxe.setVisible(false);
        this.containerChats.setPlaceholder(new Label("Buzón vacío"));
        TableColumn<Mensaxe, String> column = new TableColumn<>("Chats");
        column.setCellValueFactory(c->new SimpleStringProperty("[Emisor]: "+c.getValue().getEmisor().getLogin()+", [lido]: "+c.getValue().isLido()));
        this.containerChats.getColumns().add(column);
        actualizarTabla();
    }


    public void listenerTabla(MouseEvent mouseEvent) {
        if(this.containerChats.getItems().size()>0){
            Mensaxe mensaxe=(Mensaxe)this.containerChats.getSelectionModel().getSelectedItem();
            this.labelEmisor.setText("Emisor: "+mensaxe.getEmisor().getLogin());
            this.labelReceptor.setText("Receptor: "+mensaxe.getReceptor().getLogin());
            Date data=new Date(mensaxe.getDataEnvio().getTime());
            this.labelData.setText("Data: "+ data);
            this.labelMensaxe.setText(mensaxe.getContido());
            this.containerMensaxe.setVisible(true);
        }
    }

    private void actualizarTabla(){
        this.containerChats.getItems().addAll(super.getFachadaAplicacion().listarMensaxesRecibidos(this.receptor.getLogin()));
        if(this.containerChats.getItems().size()>0){
            this.containerChats.getSelectionModel().select(0);
        }
    }
}

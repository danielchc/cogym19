package centrodeportivo.gui.controladores.comun;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.Mensaxe;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public class vMensaxesController extends AbstractController implements Initializable {

    /**
     * Atributos do fxml.
     */
    public TableView containerChats;
    public AnchorPane containerMensaxe;
    public Label labelEmisor;
    public Label labelReceptor;
    public Label labelData;
    public Label labelMensaxe;

    /**
     * Atributos privados do controlador.
     */
    private Usuario receptor;
    private FachadaAplicacion fachadaAplicacion;

    /**
     * Constructor do controlador do buzón de mensaxes.
     *
     * @param fachadaAplicacion    Fachada da aplicación.
     * @param vPrincipalController Controlador da vista principal.
     */
    public vMensaxesController(FachadaAplicacion fachadaAplicacion, vPrincipalController vPrincipalController) {
        super(fachadaAplicacion, vPrincipalController);
        this.fachadaAplicacion = super.getFachadaAplicacion();
    }

    /**
     * Método para inicializar a vista dos mensaxes.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.receptor = super.getvPrincipalController().obterUsuarioLogeado();

        this.containerMensaxe.setVisible(false);
        this.containerChats.setPlaceholder(new Label("Buzón vacío"));
        TableColumn<Mensaxe, String> column = new TableColumn<>("Chats");
        column.setCellValueFactory(c -> new SimpleStringProperty(
                "[Hora]:" + new Date(c.getValue().getDataEnvio().getTime()).toString() + "[Emisor]: " + c.getValue().getEmisor().getLogin()));
        this.containerChats.setRowFactory(tv -> new TableRow<Mensaxe>() {
            @Override
            public void updateItem(Mensaxe item, boolean empty) {
                super.updateItem(item, empty);
                if ((item != null) && (!item.isLido())) setStyle("-fx-font-weight: bold;");
            }
        });

        this.containerChats.getColumns().add(column);
        actualizarTabla();

    }


    /**
     * Método para amosar a mensaxe seleccionada da lista de mensaxes recibidas.
     *
     * @param mouseEvent evento
     */
    public void listenerTabla(MouseEvent mouseEvent) {
        if (!this.containerChats.getSelectionModel().isEmpty()) {
            try {
                Mensaxe mensaxe = (Mensaxe) this.containerChats.getSelectionModel().getSelectedItem();
                this.labelEmisor.setText("Emisor: " + mensaxe.getEmisor().getLogin());
                this.labelReceptor.setText("Receptor: " + mensaxe.getReceptor().getLogin());
                Date data = new Date(mensaxe.getDataEnvio().getTime());
                this.labelData.setText("Data: " + data);
                this.labelMensaxe.setText(mensaxe.getContido());
                this.containerMensaxe.setVisible(true);
                this.fachadaAplicacion.marcarMensaxeComoLido(mensaxe);
                mensaxe.setLido(true);
                this.containerChats.refresh();
            } catch (ExcepcionBD excepcionBD) {
                super.getFachadaAplicacion().mostrarErro("Mensaxe", excepcionBD.getMessage());
            }
        }
    }

    /**
     * Método para actualizar o buzón de mensaxes recibidas.
     */
    public void actualizarTabla() {
        this.containerChats.getItems().removeAll(this.containerChats.getItems());
        this.containerChats.getItems().addAll(super.getFachadaAplicacion().listarMensaxesRecibidos(this.receptor.getLogin()));
    }
}

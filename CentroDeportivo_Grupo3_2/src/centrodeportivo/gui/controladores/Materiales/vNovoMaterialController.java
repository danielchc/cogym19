package centrodeportivo.gui.controladores.Materiales;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class vNovoMaterialController extends AbstractController implements Initializable {


    // Atributos públicos - trátase dos campos da interface aos que queremos acceder:
    public ComboBox comboTipoMaterial;
    public TextField campoEstadoMaterial;
    public ComboBox comboInstalacion;
    public DatePicker campoDataCompraMaterial;
    public ComboBox comboArea;
    public TextField campoPrezoMaterial;
    public Button btnGardarMaterial;
    public Button btnLimparMaterial;


    // Atributos privados: correspóndense con cuestións necesarias para realizar as diferentes xestións.
    private vPrincipalController controllerPrincipal;
    private Stage primaryStage;

    public vNovoMaterialController(FachadaAplicacion fachadaAplicacion, vPrincipalController controllerPrincipal) {
        super(fachadaAplicacion);
        this.controllerPrincipal = controllerPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Inicializamos as compoñentes da interfaz que sexa preciso
        comboTipoMaterial.getItems().addAll("Un", "Outro", "Outro máis", "");
        comboTipoMaterial.setCellFactory(lv -> {
            ListCell<String> cell = new ListCell<String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        if (item.isEmpty()) {
                            setText("Engadir novo...");
                        } else {
                            setText(item);
                        }
                    }
                }
            };

            cell.addEventFilter(MouseEvent.MOUSE_PRESSED, evt -> {
                if (cell.getItem().isEmpty() && !cell.isEmpty()) {
                    TextInputDialog dialog = new TextInputDialog();
                    dialog.setContentText("Introduce o novo tipo:");
                    dialog.showAndWait().ifPresent(text -> {
                        int index = comboTipoMaterial.getItems().size() - 1;
                        comboTipoMaterial.getItems().add(index, text);
                        comboTipoMaterial.getSelectionModel().select(index);
                    });
                    evt.consume();
                }
            });

            return cell;
        });

    }

}



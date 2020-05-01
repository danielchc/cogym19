package centrodeportivo.gui.controladores.Materiales;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.area.Material;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class vEditarMaterialController extends AbstractController implements Initializable {

    // Atributos públicos: referentes a ventá correspondente

    // Atributos privados: matenmos o controlador da ventá de procedencia
    private vPrincipalController controllerPrincipal;

    // Constructor:
    public vEditarMaterialController(FachadaAplicacion fachadaAplicacion, vPrincipalController controllerPrincipal) {
        super(fachadaAplicacion);
        this.controllerPrincipal = controllerPrincipal;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}

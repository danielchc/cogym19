package centrodeportivo.gui.controladores.Areas;

import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;



public class vNovaAreaController  extends AbstractController implements Initializable {
    //Atributos públicos: correspóndense con cuestións da ventá correspondente
    public TableView taboaInstalacions;


    //Atributos privados: manteremos o controlador da ventá de procedencia:
    private vPrincipalController controllerPrincipal;

    public vNovaAreaController (FachadaAplicacion fachadaAplicacion, vPrincipalController controllerPrincipal) {
        super(fachadaAplicacion);
        this.controllerPrincipal = controllerPrincipal;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Neste caso temos que colocar todos os campos na táboa:
        //A primeira columna terá o código da Instalación:
        TableColumn<Integer, Instalacion> colCodigo = new TableColumn<>("Código");
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codInstalacion"));
        //A segunda columna terá o seu nome:
        TableColumn<String, Instalacion> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        //A terceira columna corresponderase co número de teléfono:
        TableColumn<String, Instalacion> colTelf = new TableColumn<>("Teléfono");
        colTelf.setCellValueFactory(new PropertyValueFactory<>("numTelefono"));
        //A cuarta columna corresponderase coa dirección da Instalación:
        TableColumn<String, Instalacion> colDir = new TableColumn<>("Dirección");
        colDir.setCellValueFactory(new PropertyValueFactory<>("direccion"));

        //Feito isto, engadimos as columnas:
        taboaInstalacions.getColumns().addAll(colCodigo, colNome, colTelf, colDir);
        //Agora engadimos items:
        taboaInstalacions.getItems().addAll(super.getFachadaAplicacion().listarInstalacions());
        //Establecemos unha selección sobre a táboa (se hai resultados):
        taboaInstalacions.getSelectionModel().selectFirst();
    }

}

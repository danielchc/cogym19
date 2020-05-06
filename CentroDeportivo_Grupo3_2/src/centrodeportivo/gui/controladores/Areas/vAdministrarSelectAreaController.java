package centrodeportivo.gui.controladores.Areas;

import centrodeportivo.funcionsAux.ValidacionDatos;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.gui.controladores.principal.IdPantalla;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;



public class vAdministrarSelectAreaController extends AbstractController implements Initializable {
    //Atributos públicos: correspóndense con cuestións da ventá correspondente
    public TableView taboaInstalacions;
    public Button btnBuscar;
    public Button btnLimpar;
    public TextField campoNomeInstalacion;
    public TextField campoDireccion;
    public Button btnXestionar;
    public TextField campoTelefono;



    //Atributos privados: manteremos o controlador da ventá de procedencia:
    private vPrincipalController controllerPrincipal;

    public vAdministrarSelectAreaController(FachadaAplicacion fachadaAplicacion, vPrincipalController controllerPrincipal) {
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
        taboaInstalacions.getItems().addAll(super.getFachadaAplicacion().buscarInstalacions(null));
        //Establecemos unha selección sobre a táboa (se hai resultados):
        taboaInstalacions.getSelectionModel().selectFirst();
    }



    public void btnBuscarAction(ActionEvent actionEvent) {
        //Cando se lle dá ao botón de buscar, hai que efectuar unha busca na Base de Datos segundo os campos dispostos.
        //Vaciamos a táboa:
        taboaInstalacions.getItems().removeAll(taboaInstalacions.getItems());
        //Se non se cubriu ningún campo, o que faremos será listar todas as instalacións.
        //Inda que poida parecer redundante, é un xeito de actualizar a información:
        if(!ValidacionDatos.estanCubertosCampos(campoNomeInstalacion) && ! ValidacionDatos.estanCubertosCampos(campoTelefono)
                && !ValidacionDatos.estanCubertosCampos(campoDireccion)){
            taboaInstalacions.getItems().addAll(super.getFachadaAplicacion().buscarInstalacions(null));
        } else {
            //Noutro caso, buscaremos segundo a información dos campos.
            //Creamos unha instalación co que se ten:
            Instalacion instalacion = new Instalacion(campoNomeInstalacion.getText(), campoTelefono.getText(), campoDireccion.getText());
            taboaInstalacions.getItems().addAll(super.getFachadaAplicacion().buscarInstalacions(instalacion));
        }
        //Establecemos unha selección sobre a táboa (se hai resultados):
        taboaInstalacions.getSelectionModel().selectFirst();
    }

    public void btnLimparAction(ActionEvent actionEvent) {
        //Vaciaranse os campos e, depaso, listaranse todas as instalacións dispoñibeis de novo:
        campoNomeInstalacion.setText("");
        campoTelefono.setText("");
        campoDireccion.setText("");
        //Aproveitamos entón para actualizar a táboa:
        //Eliminamos os items:
        taboaInstalacions.getItems().removeAll(taboaInstalacions.getItems());
        //Engadimos todas as instalacións tras consultalas (así actualizamos):
        taboaInstalacions.getItems().addAll(super.getFachadaAplicacion().buscarInstalacions(null));
        //Establecemos unha selección sobre a táboa (se hai resultados):
        taboaInstalacions.getSelectionModel().selectFirst();
    }

    public void btnModificarAreas(ActionEvent actionEvent) {
        //Recuperamos primeiro a instalación seleccionada:
        Instalacion instalacion = (Instalacion) taboaInstalacions.getSelectionModel().getSelectedItem();
        if(instalacion != null){
            //Se non é null seguimos adiante.
            //Accedemos ao controlador de creación dun area:
            ((vNovaAreaController)this.controllerPrincipal.getControlador(IdPantalla.ADMINSELECTAREA)).setInstalacion((Instalacion)taboaInstalacions.getSelectionModel().getSelectedItem());
            //Feito iso, facemos que a ventá visíbel sexa a de edición dunha instalación:
            this.controllerPrincipal.mostrarPantalla(IdPantalla.ADMINSELECTAREA);
        } else {
            this.getFachadaAplicacion().mostrarErro("Administración de instalacións", "Non hai celda seleccionada!");
        }
    }

    public void metodoDeProba()
    {
        System.out.println("HOLA");
    }

}
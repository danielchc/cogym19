package centrodeportivo.gui.controladores.Areas;

import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.funcionsAux.ValidacionDatos;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.gui.controladores.AuxGUI;
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
import java.util.Date;
import java.util.ResourceBundle;



public class vAdministrarAreaController extends AbstractController implements Initializable {
    //Atributos públicos: correspóndense con cuestións da ventá correspondente:
    public TableView taboaAreas;
    public Button btnBuscar;
    public Button btnLimpar;
    public TextField campoNomeArea;
    public TextField campoAforo;
    public Button btnVolver;
    public Button btnDarBaixa;
    public Button btnDarDeAlta;
    public Button btnEliminarArea;
    public Button btnModificarArea;

    public Instalacion instalacion;

    //Atributos privados: manteremos o controlador da ventá de procedencia:
    private vPrincipalController controllerPrincipal;

    public vAdministrarAreaController(FachadaAplicacion fachadaAplicacion, vPrincipalController controllerPrincipal) {
        super(fachadaAplicacion);
        this.controllerPrincipal = controllerPrincipal;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Neste caso temos que colocar todos os campos na táboa:
        //A primeira columna terá o código da Area:
        TableColumn<Area, Integer> colCodigo = new TableColumn<>("Código");
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codArea"));
        //A segunda columna terá o seu nome:
        TableColumn<Area, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        //A terceira columna corresponderase co aforomaximo:
        TableColumn<Area, Integer> colAforo = new TableColumn<>("Aforo Máximo");
        colAforo.setCellValueFactory(new PropertyValueFactory<>("aforoMaximo"));
        //A cuarta columna corresponderase coa data de baixa:
        TableColumn<Area, Date> coldata = new TableColumn<>("Data de Baixa");
        coldata.setCellValueFactory(new PropertyValueFactory<>("dataBaixa"));
        //A cuarta columna corresponderase coa desciricon da Area:
        TableColumn<Area, String> colDes = new TableColumn<>("Descricion");
        colDes.setCellValueFactory(new PropertyValueFactory<>("descricion"));

        //Feito isto, engadimos as columnas:
        taboaAreas.getColumns().addAll(colCodigo, colNome, colAforo, coldata, colDes);
        //Agora engadimos items:
        taboaAreas.getItems().addAll(super.getFachadaAplicacion().buscarArea(null));
        //Establecemos unha selección sobre a táboa (se hai resultados):
        taboaAreas.getSelectionModel().selectFirst();
    }

    public void actualizarTaboa()
    {
        //Agora engadimos items:
        taboaAreas.getItems().setAll(super.getFachadaAplicacion().buscarArea(null));
        //Establecemos unha selección sobre a táboa (se hai resultados):
        taboaAreas.getSelectionModel().selectFirst();
    }


    public void setInstalacion(Instalacion instalacion) {
        this.instalacion = instalacion;
    }

    public void btnBuscarAction(ActionEvent actionEvent) {
        //Cando se lle dá ao botón de buscar, hai que efectuar unha busca na Base de Datos segundo os campos dispostos.
        //Borramos primeiro todas as áreas da táboa:
        taboaAreas.getItems().removeAll(taboaAreas.getItems());
        //Se non se cubriu ningún campo, o que faremos será listar todas as áreas.
        if(!ValidacionDatos.estanCubertosCampos(campoNomeArea, campoAforo)){
            taboaAreas.getItems().addAll(super.getFachadaAplicacion().buscarArea(null));
        } else {
            //Noutro caso, buscaremos segundo a información dos campos.
            //Creamos unha area cos datos recollidos:
            Area area = new Area(campoNomeArea.getText(), Integer.parseInt(campoAforo.getText()));
            System.out.println(area);
            taboaAreas.getItems().addAll(super.getFachadaAplicacion().buscarArea(area));
        }
        //Selecciónase o primeiro item da táboa:
        taboaAreas.getSelectionModel().selectFirst();
    }

    public void btnLimparAction(ActionEvent actionEvent) {
        //Vaciaranse os campos e, depaso, listaranse todas as áreas dispoñibeis de novo:
        AuxGUI.vaciarCamposTexto(campoAforo,campoNomeArea);

        //Aproveitamos entón para actualizar a táboa:
        //Eliminamos os items:
        taboaAreas.getItems().removeAll(taboaAreas.getItems());
        //Engadimos todas as areas tras consultalas:
        taboaAreas.getItems().addAll(super.getFachadaAplicacion().buscarInstalacions(null));
        //Seleccionamos o primeiro:
        taboaAreas.getSelectionModel().selectFirst();
    }

    public void btnModificarAreaAction(ActionEvent actionEvent) {
        //Recuperamos primeiro a area seleccionada:
        Area area = (Area) taboaAreas.getSelectionModel().getSelectedItem();
        if(area != null){
            //Se non é null seguimos adiante.
            //Feito iso, facemos que a ventá visíbel sexa a de edición dunha instalación:
            this.controllerPrincipal.mostrarPantalla(IdPantalla.XESTIONAREA);
            //Accedemos ao controlador de creación dun area:
            ((vXestionAreaController)this.controllerPrincipal.getControlador(IdPantalla.XESTIONAREA)).setArea((Area) taboaAreas.getSelectionModel().getSelectedItem());

        } else {
            this.getFachadaAplicacion().mostrarErro("Administración de areas", "Non hai celda seleccionada!");
        }
    }

    public void btnDarBaixaAction(ActionEvent actionEvent) throws ExcepcionBD {
        System.out.println("proba");
        Area area = (Area) taboaAreas.getSelectionModel().getSelectedItem();
        if (area != null)
        {
            this.getFachadaAplicacion().darDeBaixaArea(area);
            this.actualizarTaboa();
        }
    }

    public void btnDarAltaAction(ActionEvent actionEvent) throws ExcepcionBD {
        System.out.println("proba");

        Area area = (Area) taboaAreas.getSelectionModel().getSelectedItem();
        if (area != null)
        {
            this.getFachadaAplicacion().darDeAltaArea(area);
            this.actualizarTaboa();
        }
    }

    public void btnEliminarAreaAction(ActionEvent actionEvent) throws ExcepcionBD {
        System.out.println("proba");

        Area area = (Area) taboaAreas.getSelectionModel().getSelectedItem();

        if (area != null)
        {
            this.getFachadaAplicacion().borrarArea(area);
            this.actualizarTaboa();
        }
    }

    public void btnVolverAction(ActionEvent actionEvent){
        //Regresamos á pantalla anterior e amosámola:
        controllerPrincipal.mostrarPantalla(IdPantalla.EDITARINSTALACION);
    }


}
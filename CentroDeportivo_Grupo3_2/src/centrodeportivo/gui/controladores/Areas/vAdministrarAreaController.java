package centrodeportivo.gui.controladores.Areas;

import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.area.Area;
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
import java.util.Date;
import java.util.ResourceBundle;



public class vAdministrarAreaController extends AbstractController implements Initializable {
    //Atributos públicos: correspóndense con cuestións da ventá correspondente
    public TableView taboaAreas;
    public Button btnBuscar;
    public Button btnLimpar;
    public TextField campoNomeArea;
    public TextField campoaforom;
    public Button btnXestionar;


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
        colAforo.setCellValueFactory(new PropertyValueFactory<>("aforomaximo"));
        //A cuarta columna corresponderase coa data de baixa:
        TableColumn<Area, Date> coldata = new TableColumn<>("Data de Baixa");
        coldata.setCellValueFactory(new PropertyValueFactory<>("databaixa"));
        //A cuarta columna corresponderase coa desciricon da Area:
        TableColumn<Area, String> colDes = new TableColumn<>("Descricion");
        colDes.setCellValueFactory(new PropertyValueFactory<>("descricion"));

        //Feito isto, engadimos as columnas:
        taboaAreas.getColumns().addAll(colCodigo, colNome, colAforo, coldata, colDes);
        //Agora engadimos items:
        try {
            taboaAreas.getItems().addAll(super.getFachadaAplicacion().buscarArea(null));
        } catch (ExcepcionBD excepcionBD) {
            excepcionBD.printStackTrace();
        }
        //Establecemos unha selección sobre a táboa (se hai resultados):
        taboaAreas.getSelectionModel().selectFirst();
    }

    public Instalacion getInstalacion() {
        return instalacion;
    }

    public void setInstalacion(Instalacion instalacion) {
        this.instalacion = instalacion;
    }

    public void btnBuscarAction(ActionEvent actionEvent) throws ExcepcionBD {
        //Cando se lle dá ao botón de buscar, hai que efectuar unha busca na Base de Datos segundo os campos dispostos.
        //Vaciamos a táboa:
        taboaAreas.getItems().removeAll(taboaAreas.getItems());
        //Se non se cubriu ningún campo, o que faremos será listar todas as instalacións.
        //Inda que poida parecer redundante, é un xeito de actualizar a información:
        if(!ValidacionDatos.estanCubertosCampos(campoNomeArea) && ! ValidacionDatos.estanCubertosCampos(campoaforom)){
            taboaAreas.getItems().addAll(super.getFachadaAplicacion().buscarInstalacions(null));
        } else {
            //Noutro caso, buscaremos segundo a información dos campos.
            //Creamos unha instalación co que se ten:
            Area area = new Area(campoNomeArea.getText(), Integer.parseInt(campoaforom.getText()));
            taboaAreas.getItems().addAll(super.getFachadaAplicacion().buscarArea(area));
        }
        //Establecemos unha selección sobre a táboa (se hai resultados):
        taboaAreas.getSelectionModel().selectFirst();
    }

    public void btnLimparAction(ActionEvent actionEvent) {
        //Vaciaranse os campos e, depaso, listaranse todas as instalacións dispoñibeis de novo:
        campoNomeArea.setText("");
        campoaforom.setText("");

        //Aproveitamos entón para actualizar a táboa:
        //Eliminamos os items:
        taboaAreas.getItems().removeAll(taboaAreas.getItems());
        //Engadimos todas as instalacións tras consultalas (así actualizamos):
        taboaAreas.getItems().addAll(super.getFachadaAplicacion().buscarInstalacions(null));
        //Establecemos unha selección sobre a táboa (se hai resultados):
        taboaAreas.getSelectionModel().selectFirst();
    }

    public void btnModificarArea(ActionEvent actionEvent) {
        //Recuperamos primeiro a instalación seleccionada:
        Instalacion instalacion = (Instalacion) taboaAreas.getSelectionModel().getSelectedItem();
        if(instalacion != null){
            //Se non é null seguimos adiante.
            //Accedemos ao controlador de creación dun area:
            ((vNovaAreaController)this.controllerPrincipal.getControlador(IdPantalla.NOVAAREA)).setInstalacion((Instalacion)taboaAreas.getSelectionModel().getSelectedItem());
            //Feito iso, facemos que a ventá visíbel sexa a de edición dunha instalación:
            this.controllerPrincipal.mostrarPantalla(IdPantalla.NOVAAREA);
        } else {
            this.getFachadaAplicacion().mostrarErro("Administración de instalacións", "Non hai celda seleccionada!");
        }
    }

    public void metodoDeProba()
    {
        System.out.println("HOLA");
    }

}
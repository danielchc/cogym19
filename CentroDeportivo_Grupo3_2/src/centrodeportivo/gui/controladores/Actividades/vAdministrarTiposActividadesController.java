package centrodeportivo.gui.controladores.Actividades;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.actividades.TipoActividade;
import centrodeportivo.funcionsAux.ValidacionDatos;
import centrodeportivo.gui.controladores.AbstractController;
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

public class vAdministrarTiposActividadesController extends AbstractController implements Initializable {
    //Atributos públicos: son atributos da ventá.
    public TextField campoNome;
    public Button btnBuscar;
    public Button btnLimpar;
    public TableView taboaTiposActividades;
    public Button btnRexistrar;

    //Privados:
    private vPrincipalController controllerPrincipal;

    public vAdministrarTiposActividadesController(FachadaAplicacion fachadaAplicacion, vPrincipalController controllerPrincipal) {
        super(fachadaAplicacion);
        this.controllerPrincipal = controllerPrincipal;
    }

    //Inicialización:
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Haberá que colocar todos os campos na táboa correspondente:
        //Tres columnas
        //A primeira terá o código do tipo de actividade
        TableColumn<Integer, TipoActividade> colCodigo = new TableColumn<>("Código");
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codTipoActividade"));
        //A segunda terá o nome do tipo de actividade
        TableColumn<String, TipoActividade> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        //A terceira terá a descrición
        TableColumn<String, TipoActividade> colDescricion = new TableColumn<>("Descrción");
        colDescricion.setCellValueFactory(new PropertyValueFactory<>("descricion"));

        //Engadimos as columnas á táboa
        taboaTiposActividades.getColumns().addAll(colCodigo, colNome, colDescricion);
        //Engadimos os items dispoñíbeis no momento:
        taboaTiposActividades.getItems().addAll(super.getFachadaAplicacion().listarTiposActividades());
        //Establecemos unha selección sobre a táboa, se hai resultados:
        taboaTiposActividades.getSelectionModel().selectFirst();
    }

    public void btnBuscarAction(ActionEvent actionEvent) {
        //A búsqueda pódese realizar polo nome.
        //Pero realmente, se ese campo está baleiro, resulta máis sinxelo que se faga un simple listado.
        //O primeiro é borrar o contido da táboa:
        taboaTiposActividades.getItems().removeAll(taboaTiposActividades.getItems());
        //Entón, se non se cubriu filtro de busca, simplemente se fai un listado:
        if(!ValidacionDatos.estanCubertosCampos(campoNome)){
            taboaTiposActividades.getItems().addAll(super.getFachadaAplicacion().listarTiposActividades());
        } else {
            //Se hai algo no campo, buscamos por el:
            TipoActividade tipoActividade = new TipoActividade(campoNome.getText());
            taboaTiposActividades.getItems().addAll(super.getFachadaAplicacion().buscarTiposActividades(tipoActividade));
        }
        //Se hai resultados, establecemos unha selección sobre a táboa:
        taboaTiposActividades.getSelectionModel().selectFirst();
    }

    public void btnLimparAction(ActionEvent actionEvent) {
        //Simplemente vaciamos o campo do nome:
        campoNome.setText("");
        //Vaciamos a taboa:
        taboaTiposActividades.getItems().removeAll(taboaTiposActividades.getItems());
        //Listamos de novo as actividades (para refrescar):
        taboaTiposActividades.getItems().addAll(super.getFachadaAplicacion().listarTiposActividades());
        //Se hai resultados, establecemos unha selección sobre a táboa:
        taboaTiposActividades.getSelectionModel().selectFirst();
    }

    public void btnRexistrarAction(ActionEvent actionEvent) {
        //Se se quere rexistrar un novo tipo de actividade, ponse a null o campo do tipo da ventá correspondente:
        vInsercionTipoActividadeController cont = (vInsercionTipoActividadeController) controllerPrincipal.getControlador(IdPantalla.INSERCIONTIPOACTIVIDADE);
        cont.setTipoActividade(null);

        //Agora, amósase esa pantalla:
        controllerPrincipal.mostrarPantalla(IdPantalla.INSERCIONTIPOACTIVIDADE);
    }

    public void btnXestionarAction(ActionEvent actionEvent) {
        //Se se quere xestionar un tipo de actividade existente, hai que comprobar que haxa unha selección:
        TipoActividade tipoActividade = (TipoActividade) taboaTiposActividades.getSelectionModel().getSelectedItem();
        if(tipoActividade != null) {
            vInsercionTipoActividadeController cont = (vInsercionTipoActividadeController) controllerPrincipal.getControlador(IdPantalla.INSERCIONTIPOACTIVIDADE);
            //Introducese o tipo de actividade como atributo no controlador correspondente:
            cont.setTipoActividade(tipoActividade);
            //Agora, amósase esa pantalla:
            controllerPrincipal.mostrarPantalla(IdPantalla.INSERCIONTIPOACTIVIDADE);
        } else {
            super.getFachadaAplicacion().mostrarErro("Administración de Tipos de Actividades", "Non hai ningunha selección para editar!");
        }
    }

}

package centrodeportivo.gui.controladores.Actividades;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.actividades.TipoActividade;
import centrodeportivo.funcionsAux.ValidacionDatos;
import centrodeportivo.gui.controladores.AbstractController;
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
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Víctor Barreiro
 * Clase que servirá de controlador da pantalla de administración de tipos de actividades.
 */
public class vAdministrarTiposActividadesController extends AbstractController implements Initializable {

    /**
     * Atributos públicos: correspóndense con elementos que se atopan na ventá de administración de tipos de
     * actividades.
     */
    public TextField campoNome;
    public Button btnBuscar;
    public Button btnLimpar;
    public TableView taboaTiposActividades;
    public Button btnRexistrar;

    /**
     * Atributos privados: somentes temos un que é a referencia ao controlador da ventá principal.
     */
    private vPrincipalController controllerPrincipal;

    /**
     * Constructor do controlador da pantalla de administración de tipos de actividades.
     *
     * @param fachadaAplicacion   A referencia á fachada da parte de aplicación.
     * @param controllerPrincipal A referencia ao controlador da ventá principal.
     */
    public vAdministrarTiposActividadesController(FachadaAplicacion fachadaAplicacion, vPrincipalController controllerPrincipal) {
        //Chamamos ao constructor da clase pai coa fachada da aplicación.
        super(fachadaAplicacion);
        //Asignamos o controlador principal ao atributo correspondente.
        this.controllerPrincipal = controllerPrincipal;
    }

    /**
     * Método que nos permite inicializar a pantalla ao abrila.
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Haberá que colocar todos os campos na táboa correspondente:
        //Tres columnas
        //A primeira terá o código do tipo de actividade
        TableColumn<TipoActividade, Integer> colCodigo = new TableColumn<>("Código");
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codTipoActividade"));
        //A segunda terá o nome do tipo de actividade
        TableColumn<TipoActividade, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        //A terceira terá a descrición
        TableColumn<TipoActividade, String> colDescricion = new TableColumn<>("Descrción");
        colDescricion.setCellValueFactory(new PropertyValueFactory<>("descricion"));

        //Engadimos as columnas á táboa
        taboaTiposActividades.getColumns().addAll(colCodigo, colNome, colDescricion);
        //Engadimos os items dispoñíbeis no momento:
        taboaTiposActividades.getItems().addAll(super.getFachadaAplicacion().buscarTiposActividades(null));
        //Establecemos unha selección sobre a táboa, se hai resultados:
        taboaTiposActividades.getSelectionModel().selectFirst();
    }

    /**
     * Método executado cando se pulsa o botón de realizar unha búsqueda
     *
     * @param actionEvent A acción que tivo lugar.
     */
    public void btnBuscarAction(ActionEvent actionEvent) {
        //A búsqueda pódese realizar polo nome.
        //Pero realmente, se ese campo está baleiro, resulta máis sinxelo que se faga un simple listado.
        if (!ValidacionDatos.estanCubertosCampos(campoNome)) {
            //Entón, se non se cubriu filtro de busca, simplemente se fai un listado (pasamos null ao método):
            this.refrescarTaboaTiposAct(super.getFachadaAplicacion().buscarTiposActividades(null));
        } else {
            //Se hai algo no campo, buscamos por el:
            TipoActividade tipoActividade = new TipoActividade(campoNome.getText());
            this.refrescarTaboaTiposAct(super.getFachadaAplicacion().buscarTiposActividades(tipoActividade));
        }
    }

    /**
     * Método executado cando se pulsa o botón de limpar. Basicamente limpanse filtros e deixase a lista de tipos
     * completa.
     *
     * @param actionEvent A acción que tivo lugar.
     */
    public void btnLimparAction(ActionEvent actionEvent) {
        //Simplemente vaciamos o campo do nome:
        AuxGUI.vaciarCamposTexto(campoNome);
        //Listamos de novo os tipos de actividade:
        this.refrescarTaboaTiposAct(super.getFachadaAplicacion().buscarTiposActividades(null));
    }

    /**
     * Acción realizada ao pulsar o botón de rexistro dun novo tipo de actividade.
     *
     * @param actionEvent A acción que tivo lugar.
     */
    public void btnRexistrarAction(ActionEvent actionEvent) {
        //Se se quere rexistrar un novo tipo de actividade, ponse a null o campo da ventá á que se vai acceder:
        vInsercionTipoActividadeController cont = (vInsercionTipoActividadeController) controllerPrincipal.getControlador(IdPantalla.INSERCIONTIPOACTIVIDADE);
        cont.setTipoActividade(null);

        //Agora, amósase esa pantalla:
        controllerPrincipal.mostrarPantalla(IdPantalla.INSERCIONTIPOACTIVIDADE);
    }

    /**
     * Acción realizada ao pulsar o botón de xestión dun tipo de actividade.
     *
     * @param actionEvent A acción que tivo lugar.
     */
    public void btnXestionarAction(ActionEvent actionEvent) {
        //Se se quere xestionar un tipo de actividade existente, hai que comprobar que haxa unha selección:
        TipoActividade tipoActividade = (TipoActividade) taboaTiposActividades.getSelectionModel().getSelectedItem();
        if (tipoActividade != null) {
            //Se hai selección, recuperamos o controlador da ventá de inserción do tipo de actividade:
            vInsercionTipoActividadeController cont = (vInsercionTipoActividadeController) controllerPrincipal.getControlador(IdPantalla.INSERCIONTIPOACTIVIDADE);
            //Introducese o tipo de actividade como atributo no controlador correspondente, pero antes consúltase
            //de novo na base de datos, por se pasou moito tempo e xa non está rexistrado:
            tipoActividade = super.getFachadaAplicacion().consultarTipoActividade(tipoActividade);
            if (tipoActividade != null) {
                //En caso de seguir almacenado, establécese o tipo de actividade do controlador da seguinte ventá:
                cont.setTipoActividade(tipoActividade);
                //Agora, amósase esa pantalla:
                controllerPrincipal.mostrarPantalla(IdPantalla.INSERCIONTIPOACTIVIDADE);
            } else {
                //Noutro caso amosamos un erro e mantémonos nesta ventá, actualizando:
                super.getFachadaAplicacion().mostrarErro("Administración de Tipos de Actividades",
                        "O tipo de actividade seleccionado xa non existe na base de datos.");
                //Actualizamos a táboa de tipos de actividades:
                this.refrescarTaboaTiposAct(super.getFachadaAplicacion().buscarTiposActividades(null));
            }
        } else {
            //En caso de non haber selección, avísase:
            super.getFachadaAplicacion().mostrarErro("Administración de Tipos de Actividades",
                    "Non hai ningunha selección para editar!");
        }
    }

    /**
     * Método que nos permite refrescar a táboa (vaciar e encher de novo) cos tipos de actividades pasados como
     * argumento
     *
     * @param tipoActividadeArrayList Os tipos de actividades a introducir na táboa correspondente.
     */
    private void refrescarTaboaTiposAct(ArrayList<TipoActividade> tipoActividadeArrayList) {
        //O primeiro é borrar o contido da táboa:
        taboaTiposActividades.getItems().removeAll(taboaTiposActividades.getItems());
        //Agora introducimos todos os valores pasados no arraylist pasado como argumento:
        taboaTiposActividades.getItems().addAll(tipoActividadeArrayList);
        //Se hai resultados, establecemos unha selección sobre a táboa:
        taboaTiposActividades.getSelectionModel().selectFirst();
    }

}

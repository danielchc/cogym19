package centrodeportivo.gui.controladores.Materiales;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.aplicacion.obxectos.area.TipoMaterial;
import centrodeportivo.aplicacion.obxectos.tipos.TipoResultados;
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
 * Clase que funciona como controlador da pantalla de administración dos tipos de materiais.
 */
public class vAdministrarTipoMaterialController extends AbstractController implements Initializable {


    // Atributos públicos: correspóndense con partes da interface gráfica:
    public TableView taboaTipoMaterial;
    public Button btnBuscar;
    public Button btnLimpar;
    public Button btnEngadir;
    public Button btnEliminar;
    public TextField campoTipoMaterial;


    // Atributo privado: referencia ó controlador da ventá principal sobre o que se amosan as diferentes ventás
    private vPrincipalController controllerPrincipal;

    /**
     * Constructor do controlador da ventá de administración dos tipos de materiais:
     *
     * @param fachadaAplicacion   A referencia á fachada da parte de aplicación.
     * @param controllerPrincipal A referencia ao controlador da ventá principal.
     */
    public vAdministrarTipoMaterialController(FachadaAplicacion fachadaAplicacion, vPrincipalController controllerPrincipal) {
        // Chamamos ó constructor da clase que herda
        super(fachadaAplicacion);
        // Asignamos o controlador principal ó atributo correspondente:
        this.controllerPrincipal = controllerPrincipal;
    }

    /**
     * Método que se executa ao abrir a ventá para realizar a inicialización:
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Iniciamos colocando os campos de tipo material na táboa que os amosará
        // A primeira columna terá o código do tipo:
        TableColumn<TipoMaterial, Integer> colCodigo = new TableColumn<>("Código");
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codTipoMaterial"));
        // A segunda columna terá o nome do tipo:
        TableColumn<TipoMaterial, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        // Engadimos as columnas a táboa:
        taboaTipoMaterial.getColumns().addAll(colCodigo, colNome);
        // Engadimos os items a táboa:
        taboaTipoMaterial.getItems().addAll(super.getFachadaAplicacion().buscarTipoMaterial(null));
        // Establecemos unha selección sobre a táboa no caso de que haxa resultados:
        taboaTipoMaterial.getSelectionModel().selectFirst();
    }

    /**
     * Acción efectuada ao premer o botón para realizar a búsqueda.
     *
     * @param actionEvent A acción que tivo lugar.
     */
    public void btnBuscarAction(ActionEvent actionEvent) {
        // Cando se lle dá ao botón de buscar, hai que efectuar unha busca na Base de Datos segundo os campos dispostos.
        // Se non se cubriu ningún campo, o que faremos será listar todos os tipos almacenados para actualizar a información
        if (!ValidacionDatos.estanCubertosCampos(campoTipoMaterial)) {
            // Listamos todos os tipos que hai:
            actualizarTaboaTipoMaterial(super.getFachadaAplicacion().buscarTipoMaterial(null));
        } else {
            // Se o campo esta cuberto, listanse os tipos de materiais en funcion do mesmo
            TipoMaterial tipoMaterial = new TipoMaterial(campoTipoMaterial.getText());
            // Reemprazamos o contido da táboa buscando polo nome tipo:
            actualizarTaboaTipoMaterial(super.getFachadaAplicacion().buscarTipoMaterial(tipoMaterial));
        }
    }

    /**
     * Acción efectuada ao premer o botón para limpar unha búsqueda.
     *
     * @param actionEvent A acción que tivo lugar.
     */
    public void btnLimparAction(ActionEvent actionEvent) {
        // Vaciaranse os campos e, depaso, listaranse todas os tipos dispoñibeis de novo:
        AuxGUI.vaciarCamposTexto(campoTipoMaterial);
        // Aproveitamos entón para actualizar a táboa:
        actualizarTaboaTipoMaterial(super.getFachadaAplicacion().buscarTipoMaterial(null));
    }


    /**
     * Acción efectuada ao premer o botón para engadir un tipo de material
     *
     * @param actionEvent A acción que tivo lugar.
     */
    public void btnEngadirAction(ActionEvent actionEvent) {


        // Primeiro comprobamos que o nome non este valeiro
        if (!ValidacionDatos.estanCubertosCampos(campoTipoMaterial)) {
            // Se algún campo non esta cuberto, non se fai nada máis
            // TODO: Amosar mensaxe de erro
            return;
        }

        // Creamos un tipo de material co nome facilitado
        TipoMaterial tipoMaterial = new TipoMaterial(campoTipoMaterial.getText());
        // Accedemos á base de datos: intentamos que se efectúe sen problemas dito acceso.
        try {
            // A consulta pódenos devolver varios resultados en función da situación. Avaliámolos:
            TipoResultados resultadoDarAlta = this.getFachadaAplicacion().darAltaTipoMaterial(tipoMaterial);
            // En función do resultado, mostraremos unha mensaxe ou outra:
            switch (resultadoDarAlta) {
                case correcto:
                    // Correcto -> Imprimimos mensaxe de éxito co ID do tipo de material insertado:
                    this.getFachadaAplicacion().mostrarInformacion("Tipos de materiais",
                            "Creada o novo tipo de material " + tipoMaterial.getNome() +
                                    ". O seu id é: " + tipoMaterial.getCodTipoMaterial() + ".");
                    // Continuamos na pantalla e valeiramos o campo do nome:
                    AuxGUI.vaciarCamposTexto(campoTipoMaterial);
                    // Actualizamos a táboa de tipo de material:
                    actualizarTaboaTipoMaterial(super.getFachadaAplicacion().buscarTipoMaterial(null));
                    break;
                case datoExiste:
                    // Se xa existía un tipo de material con ese nome amosase un erro nunha segunda pantalla:
                    this.getFachadaAplicacion().mostrarErro("Administración dos tipos de materiais",
                            "Xa hai un tipo co nome '" + tipoMaterial.getNome().toLowerCase() + "'.");
                    break;
            }
        } catch (ExcepcionBD e) {
            // Se se recibe unha excepción da base de datos, entón imprímese unha mensaxe informando.
            // Esa mensaxe obtense dentro da excepción, co método getMessage():
            this.getFachadaAplicacion().mostrarErro("Administración dos tipos de materiais", e.getMessage());
        }
        // Se houbo algún erro, seguirase nesta pantalla.
    }


    /**
     * Método que nos permite actualizar a táboa dos tipos de material
     *
     * @param tipoMaterialArrayList Os tipos de materiais cos que se quere actualizar a táboa.
     */
    private void actualizarTaboaTipoMaterial(ArrayList<TipoMaterial> tipoMaterialArrayList) {
        // O primeiro que faremos será vaciar a táboa:
        taboaTipoMaterial.getItems().removeAll(taboaTipoMaterial.getItems());
        // Agora, insertaremos todos os items que se pasen como argumento:
        taboaTipoMaterial.getItems().addAll(tipoMaterialArrayList);
        // Establecemos unha selección sobre a táboa no caso de que haxa resultados:
        taboaTipoMaterial.getSelectionModel().selectFirst();
    }
}

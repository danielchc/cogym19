package centrodeportivo.gui.controladores.Cursos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.actividades.Curso;
import centrodeportivo.aplicacion.obxectos.tipos.TipoResultados;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.funcionsAux.ValidacionDatos;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.Actividades.vInformeActividadeController;
import centrodeportivo.gui.controladores.AuxGUI;
import centrodeportivo.gui.controladores.principal.IdPantalla;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;


/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Víctor Barreiro
 * <p>
 * Clase que funciona como controlador da pantalla de cursos para un socio ( ve un listado de todos os cursos abertos )
 */
public class vElixirCursoController extends AbstractController implements Initializable {

    /**
     * Atributos públicos: correspóndense con partes da pantalla correspondente (que se atopan no fxml):
     */
    public TextField campoNome;
    public Button btnBuscar;
    public Button btnLimpar;
    public TableView taboaCursos;
    public Button btnXestionar;
    public CheckBox checkResaltar;

    /**
     * Atributos privados: gardamos a referencia ó controlador da ventá principal é o usuario que esta loggeado
     */
    private vPrincipalController controllerPrincipal;
    private Usuario usuario;

    /**
     * Constructor do controlador da ventá de elixirCurso
     *
     * @param fachadaAplicacion   Referencia á fachada da parte de aplicación.
     * @param controllerPrincipal Referencia ao controlador da ventá principal.
     */
    public vElixirCursoController(FachadaAplicacion fachadaAplicacion, vPrincipalController controllerPrincipal, Usuario usuario) {
        // Chamamos ao constructor da clase pai:
        super(fachadaAplicacion);
        // Asignamos o parámetro pasado de controlador da ventá principal ao atributo correspondente:
        this.controllerPrincipal = controllerPrincipal;
        // Asignamos o usuario que esta loggeado
        this.usuario = usuario;

    }


    /**
     * Método co que se inicializan as compoñentes desta ventá:
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // O primeiro paso será inicializar a táboa na que se amosan os cursos abertos
        TableColumn<Curso, String> nomeColumn = new TableColumn<>("Nome");
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));

        // Ca data de inicio comprobamos cales estan sen comezar:
        TableColumn<Curso, String> iniciadoColum = new TableColumn<>("Iniciado");
        iniciadoColum.setCellValueFactory(p -> {
            if (p.getValue().getDataInicio() != null && p.getValue().getDataInicio().toLocalDate().isBefore(LocalDate.now())) {
                return new SimpleStringProperty("Si");
            } else {
                return new SimpleStringProperty("Non");
            }
        });

        //Número de actividades:
        TableColumn<Curso, Integer> numActividadesColumn = new TableColumn<>("Numero de Actividades");
        numActividadesColumn.setCellValueFactory(new PropertyValueFactory<>("numActividades"));

        //Duración: personaliceina para que aparezan as horas e os minutos separados.
        TableColumn<Curso, String> duracionColumn = new TableColumn<>("Duración");
        duracionColumn.setCellValueFactory(c -> new SimpleStringProperty(
                //Basicamente collo a parte enteira e logo a decimal multiplicada por 60:
                c.getValue().getDuracion().intValue() + "h, " +
                        (int) ((c.getValue().getDuracion().floatValue() - c.getValue().getDuracion().intValue()) * 60) + "m"
        ));


        // Engadimos o seguinte para poder resaltar dunha cor diferente os cursos que xa comezaron
        taboaCursos.setRowFactory(tv -> new TableRow<Curso>() {
            @Override
            public void updateItem(Curso item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && getFachadaAplicacion().estarApuntado(item, usuario)) {
                    getStyleClass().add("resaltar");
                } else {
                    getStyleClass().remove("resaltar");
                }

            }
        });

        // Metemos as columnas creadas na táboa:
        taboaCursos.getColumns().addAll(nomeColumn, numActividadesColumn, duracionColumn, iniciadoColum);
        // Buscamos os datos dos cursos abertos e engadímolos:
        taboaCursos.getItems().addAll(getFachadaAplicacion().consultarCursosAbertos(null));
        // Modelo de selección:
        taboaCursos.getSelectionModel().selectFirst();
    }

    /**
     * Método que representa as accións que se levan a cabo ao premer o botón de busca dun curso.
     *
     * @param actionEvent A acción que tivo lugar.
     */
    public void btnBuscarAction(ActionEvent actionEvent) {
        // Se o campo de nome non se cubre, o que faremos será listar todos os cursos, se non, faremos búsqueda por nome:
        if (!ValidacionDatos.estanCubertosCampos(campoNome)) {
            // Listar todos os cursos -> Pasamos a consultarCursosAbertos parámetro null:
            this.actualizarTaboaCursos(getFachadaAplicacion().consultarCursosAbertos(null));
        } else {
            // Buscar por un curso: pasamos un curso co campo de busca: o nome:
            this.actualizarTaboaCursos(getFachadaAplicacion().consultarCursosAbertos(new Curso(campoNome.getText())));
        }
    }

    /**
     * Método que representa as accións que teñen lugar ao premer o botón de limpar información
     *
     * @param actionEvent A acción que tivo lugar.
     */
    public void btnLimparAction(ActionEvent actionEvent) {
        // Ó limpar os campos, o que facemos será poñer o campo de búsqueda baleiro e refrescar a táboa sen filtros:
        AuxGUI.vaciarCamposTexto(campoNome);
        // Listar todos os cursos -> Pasamos a consultarCursosAbertos parámetro null:
        this.actualizarTaboaCursos(getFachadaAplicacion().consultarCursosAbertos(null));
    }

    /**
     * Método que representa as accións que teñen lugar ao premer o botón de xestionar un curso, neste caso,
     * levarasenos a una pantalla onde poderemos apuntarnos ou desapuntarnos dun curso en función de si xa estamos anotados ou non
     *
     * @param actionEvent A acción que tivo lugar
     */
    public void btnXestionarAction(ActionEvent actionEvent) {

        // Volvemos a pantalla de administrar materiais:
        if (taboaCursos.getSelectionModel().getSelectedItem() != null) {
            if (getFachadaAplicacion().estarApuntado((Curso) taboaCursos.getSelectionModel().getSelectedItem(), usuario)) {
                ((vInformacionCursosController) this.controllerPrincipal.getControlador(IdPantalla.INFORMACIONCURSO)).setEstaApuntado(true);
            } else {
                ((vInformacionCursosController) this.controllerPrincipal.getControlador(IdPantalla.INFORMACIONCURSO)).setEstaApuntado(false);
            }
            ((vInformacionCursosController) this.controllerPrincipal.getControlador(IdPantalla.INFORMACIONCURSO)).setCurso((Curso) taboaCursos.getSelectionModel().getSelectedItem());
            this.controllerPrincipal.mostrarPantalla(IdPantalla.INFORMACIONCURSO);
        }
    }


    /**
     * Método que nos permite actualizar a táboa de cursos (vaciándoa e reenchéndoa):
     *
     * @param cursos Os cursos que se van a introducir na táboa
     */
    private void actualizarTaboaCursos(ArrayList<Curso> cursos) {
        // Retiramos o anterior e engadimos novos items na taboa
        taboaCursos.getItems().setAll(cursos);
        // Por defecto, seleccionarase o primeiro:
        taboaCursos.getSelectionModel().selectFirst();
    }

}

package centrodeportivo.gui.controladores.Cursos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.actividades.Curso;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.funcionsAux.ValidacionDatos;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.AuxGUI;
import centrodeportivo.gui.controladores.principal.IdPantalla;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.text.SimpleDateFormat;
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
    public CheckBox checkAnotado;
    public Label etiquetaRematados;

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
        // Poñemos a etiqueta non visible:
        etiquetaRematados.setVisible(false);

        // O primeiro paso será inicializar a táboa na que se amosan os cursos abertos
        TableColumn<Curso, String> nomeColumn = new TableColumn<>("Nome");
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));

        // Data comezo das actividades
        TableColumn<Curso, String> dataColumn = new TableColumn<>("Data comezo");
        dataColumn.setCellValueFactory(p -> {
            // Haberá que comprobar que a data de inicio non teña nulos ainda que non debería por ter que estar aberto:
            if (p.getValue().getDataInicio() != null) {
                return new SimpleStringProperty(new SimpleDateFormat("dd/MM/yyyy").format(p.getValue().getDataInicio()));
            } else {
                // En caso de que teña nulos, será porque o curso inda non ten fixada a data de inicio:
                return new SimpleStringProperty("Sen inicio fixado");
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

        // Resaltamos aqueles cursos que xa esten rematados, é dicir, que a data de fin sexa menor que a data actual:
        taboaCursos.setRowFactory(tv -> new TableRow<Curso>() {
            @Override
            public void updateItem(Curso item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && item.getDataFin() != null && item.getDataFin().toLocalDate().isBefore(LocalDate.now())) {
                    getStyleClass().add("resaltar");
                } else {
                    getStyleClass().remove("resaltar");
                }
            }
        });

        // Metemos as columnas creadas na táboa:
        taboaCursos.getColumns().addAll(nomeColumn, numActividadesColumn, duracionColumn, dataColumn);
        // Buscamos os datos dos cursos abertos e engadímolos:
        taboaCursos.getItems().addAll(getFachadaAplicacion().consultarCursosAbertosSocios(null));
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
            this.actualizarTaboaCursos(getFachadaAplicacion().consultarCursosAbertosSocios(null));
        } else {
            if (checkAnotado.isSelected()) {
                // Buscar por un curso: pasamos un curso co campo de busca: o nome:
                this.actualizarTaboaCursos(getFachadaAplicacion().consultarCursosUsuario(new Curso(campoNome.getText()), usuario));
            } else {
                // Buscar por un curso: pasamos un curso co campo de busca: o nome:
                this.actualizarTaboaCursos(getFachadaAplicacion().consultarCursosAbertosSocios(new Curso(campoNome.getText())));
            }
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
        if (checkAnotado.isSelected()) {
            // Buscar por un curso: pasamos un curso co campo de busca, o nome do mesmo:
            this.actualizarTaboaCursos(getFachadaAplicacion().consultarCursosUsuario(new Curso(campoNome.getText()), usuario));
        } else {
            // Buscar por un curso: pasamos un curso co campo de busca, o nome do mesmo:
            this.actualizarTaboaCursos(getFachadaAplicacion().consultarCursosAbertosSocios(new Curso(campoNome.getText())));
        }
    }

    /**
     * Método que se executa cando se pulsa o checkbox para amosar so os cursos nos que o usuario esta apuntado:
     *
     * @param actionEvent A acción que tivo lugar.
     */
    public void checkResaltarAction(ActionEvent actionEvent) {
        // Únicamente se refresca a táboa:
        if (checkAnotado.isSelected()) {
            // Buscar por un curso: pasamos un curso co campo de busca, o nome do mesmo:
            this.actualizarTaboaCursos(getFachadaAplicacion().consultarCursosUsuario(null, usuario));
            // Refrescamos a taboa de cursos:
            taboaCursos.refresh();
            // Poñemos a etiqueta visible:
            etiquetaRematados.setVisible(true);
            // Valeiramos os campos de busqueda:
            AuxGUI.vaciarCamposTexto(campoNome);
        } else {
            // Buscar por un curso: pasamos un curso co campo de busca, o nome do mesmo:
            this.actualizarTaboaCursos(getFachadaAplicacion().consultarCursosAbertosSocios(null));
            // Valeiramos os campos de busqueda
            AuxGUI.vaciarCamposTexto(campoNome);
            // Refrescamos a taboa de cursos:
            taboaCursos.refresh();
            // Poñemos a etiqueta non visible:
            etiquetaRematados.setVisible(false);
        }
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

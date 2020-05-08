package centrodeportivo.gui.controladores.Cursos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.actividades.Curso;
import centrodeportivo.aplicacion.obxectos.tipos.TipoResultados;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.funcionsAux.ValidacionDatos;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.AuxGUI;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;


/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Víctor Barreiro
 * Clase que funciona como controlador da pantalla dos cursos nos que un usuario esta apuntado
 */
public class vMeusCursosController extends AbstractController implements Initializable {

    /**
     * Atributos públicos: correspóndense con partes da pantalla correspondente (que se atopan no fxml):
     */
    public TextField campoNome;
    public Button btnBuscar;
    public Button btnLimpar;
    public TableView taboaCursos;
    public Button btnDesapuntarse;
    public CheckBox checkResaltar;

    /**
     * Atributos privados: gardamos a referencia ó controlador da ventá principal é o usuario que esta loggeado
     */
    private vPrincipalController controllerPrincipal;
    private Usuario usuario;

    /**
     * Constructor do controlador da ventá de administración de cursos.
     *
     * @param fachadaAplicacion   Referencia á fachada da parte de aplicación.
     * @param controllerPrincipal Referencia ao controlador da ventá principal.
     */
    public vMeusCursosController(FachadaAplicacion fachadaAplicacion, vPrincipalController controllerPrincipal, Usuario usuario) {
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
        Date date = null;
        // O primeiro paso será inicializar a táboa na que se amosan os cursos:
        TableColumn<Curso, String> nomeColumn = new TableColumn<>("Nome");
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));

        // Número de actividades:
        TableColumn<Curso, Integer> numActividadesColumn = new TableColumn<>("Numero de Actividades");
        numActividadesColumn.setCellValueFactory(new PropertyValueFactory<>("numActividades"));

        // Duración: personaliceina para que aparezan as horas e os minutos separados.
        TableColumn<Curso, String> duracionColumn = new TableColumn<>("Duración");
        duracionColumn.setCellValueFactory(c -> new SimpleStringProperty(
                //Basicamente collo a parte enteira e logo a decimal multiplicada por 60:
                c.getValue().getDuracion().intValue() + "h, " +
                        (int) ((c.getValue().getDuracion() - c.getValue().getDuracion().intValue()) * 60) + "m"
        ));

        // Ca data de inicio comprobamos cales estan sen comezar:
        TableColumn<Curso, String> iniciadoColum = new TableColumn<>("Iniciado");
        iniciadoColum.setCellValueFactory(p -> {
            if (p.getValue().getDataInicio() != null && p.getValue().getDataInicio().toLocalDate().isBefore(LocalDate.now())) {
                return new SimpleStringProperty("Si");
            } else {
                return new SimpleStringProperty("Non");
            }
        });

        // Engadimos o seguinte para poder resaltar dunha cor diferente os cursos que xa comezaron
        taboaCursos.setRowFactory(tv -> new TableRow<Curso>() {
            @Override
            public void updateItem(Curso item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && item.getDataInicio().toLocalDate().isBefore(LocalDate.now())) {
                    getStyleClass().add("resaltar");
                } else {
                    getStyleClass().remove("resaltar");
                }

            }
        });

        // Metemos as columnas creadas na táboa:
        taboaCursos.getColumns().addAll(nomeColumn, numActividadesColumn, duracionColumn, iniciadoColum);
        // Buscamos os datos dos cursos e engadímolos.
        taboaCursos.getItems().addAll(getFachadaAplicacion().consultarCursosUsuario(null, usuario));
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
            // Listar todos os cursos -> Pasamos a consultarCursos parámetro null:
            this.actualizarTaboaCursos(getFachadaAplicacion().consultarCursosUsuario(null, usuario));
        } else {
            // Buscar por un curso: pasamos un curso co campo de busca: o nome:
            this.actualizarTaboaCursos(getFachadaAplicacion().consultarCursosUsuario(new Curso(campoNome.getText()), usuario));
        }
    }

    /**
     * Método que representa as accións que teñen lugar ao premer o botón de limpar información
     *
     * @param actionEvent A acción que tivo lugar.
     */
    public void btnLimparAction(ActionEvent actionEvent) {
        //Ao limpar os campos, o que facemos será poñer o campo de búsqueda baleiro e refrescar a táboa sen filtros.
        AuxGUI.vaciarCamposTexto(campoNome);
        //Listar todos os cursos -> Pasamos a consultarCursos parámetro null:
        this.actualizarTaboaCursos(getFachadaAplicacion().consultarCursosUsuario(null, usuario));
    }

    /**
     * Método que representa as accións que teñen lugar ao premer o botón de desapuntarse a un curso:
     *
     * @param actionEvent A acción que tivo lugar
     */
    public void btnDesapuntarseAction(ActionEvent actionEvent) {
        // Neste caso, o que teremos que facer é recopilar os datos completos do curso seleccionado:
        // Para iso, empezamos mirando se hai unha selección feita:
        Curso selected = (Curso) taboaCursos.getSelectionModel().getSelectedItem();
        if (selected != null) {
            // Gardamos o resultado noutra variable para refrescar toda a información
            // Iso será o que se lle pase ó método de desapuntarse
            Curso res = getFachadaAplicacion().recuperarDatosCurso(selected);
            if (res != null) {
                // Tentamos desanotar o usuario do curso
                try {
                    // TODO: Non te podes desapuntar dun curso que xa comezou
                    TipoResultados resultado = getFachadaAplicacion().desapuntarseCurso(res, usuario);
                    switch (resultado) {
                        case correcto:
                            // Amosamos unha mensaxe por pantalla en como xa non esta anotado no curso
                            getFachadaAplicacion().mostrarInformacion("Cursos", "Boas, " + usuario.getNome()
                                    + ". Agora xa non estás apuntado no curso " + res.getNome() + "!");
                            // Actualizamos a taboa porque desaparecerá o curso:
                            actualizarTaboaCursos(getFachadaAplicacion().consultarCursosUsuario(null, usuario));
                            break;
                        case sitIncoherente:
                            // Amosamos unha mensaxe por pantalla en como esta anotado no curso
                            getFachadaAplicacion().mostrarErro("Cursos", "Boas, " + usuario.getNome()
                                    + ". Non te podes desapuntar do curso " + res.getNome() + "!");
                            break;
                    }
                } catch (ExcepcionBD e) {
                    // Se houbese algun erro, mostrase unha mensaxe por pantalla
                    getFachadaAplicacion().mostrarErro("Cursos", e.getMessage());
                }
            } else {
                // Se houbo problemas ao recuperar a información, avisamos do erro:
                getFachadaAplicacion().mostrarErro("Cursos",
                        "Produciuse un erro ao recuperar os datos do curso.");
                // Para evitar problemas maiores, actualizaremos a táboa de cursos (se vale null é probable que se borrara):
                actualizarTaboaCursos(getFachadaAplicacion().consultarCursosUsuario(null, usuario));
            }
        } else {
            // Se non se ten selección, indícase que hai que facela primeiro (podería ser que a lista estivese vacía):
            this.getFachadaAplicacion().mostrarErro("Cursos",
                    "Selecciona un curso do que desapuntarte!");
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
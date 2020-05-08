package centrodeportivo.gui.controladores.Cursos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.actividades.Curso;
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
import java.text.SimpleDateFormat;
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
    public Button btnApuntarse;
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

        // Á data de inicio dámoslle un formato que sexa cómodo para nós (día/mes/ano):
        TableColumn<Curso, String> dataInicioColumn = new TableColumn<>("Data Inicio");
        dataInicioColumn.setCellValueFactory(p -> {
            // Haberá que comprobar que a data de inicio non teña nulos (podería ser!)
            if (p.getValue().getDataInicio() != null) {
                return new SimpleStringProperty(new SimpleDateFormat("dd/MM/yyyy").format(p.getValue().getDataInicio()));
            } else {
                //En caso de que teña nulos, será porque o curso inda non ten fixada a data de inicio:
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

        // O booleano aberto: facemos que en función do seu valor se imprima un si ou un non (en lugar de ter "true" ou
        //"false").
        TableColumn<Curso, String> abertoColumn = new TableColumn<>("Aberto");
        abertoColumn.setCellValueFactory(p -> {
            if (p.getValue().isAberto()) {
                return new SimpleStringProperty("Si");
            } else {
                return new SimpleStringProperty("Non");
            }
        });


        //Metemos as columnas creadas na táboa:
        taboaCursos.getColumns().addAll(nomeColumn, dataInicioColumn, numActividadesColumn, duracionColumn, abertoColumn);
        //Buscamos os datos dos cursos e engadímolos.
        taboaCursos.getItems().addAll(getFachadaAplicacion().consultarCursosAbertos(null));
        //Modelo de selección:
        taboaCursos.getSelectionModel().selectFirst();
    }

    /**
     * Método que representa as accións que se levan a cabo ao premer o botón de busca dun curso.
     *
     * @param actionEvent A acción que tivo lugar.
     */
    public void btnBuscarAction(ActionEvent actionEvent) {
        //Se o campo de nome non se cubre, o que faremos será listar todos os cursos, se non, faremos búsqueda por nome:
        if (!ValidacionDatos.estanCubertosCampos(campoNome)) {
            //Listar todos os cursos -> Pasamos a consultarCursos parámetro null:
            this.actualizarTaboaCursos(getFachadaAplicacion().consultarCursosAbertos(null));
        } else {
            //Buscar por un curso: pasamos un curso co campo de busca: o nome:
            this.actualizarTaboaCursos(getFachadaAplicacion().consultarCursosAbertos(new Curso(campoNome.getText())));
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
        this.actualizarTaboaCursos(getFachadaAplicacion().consultarCursosAbertos(null));
    }

    /**
     * Método que representa as accións que teñen lugar ao premer o botón de xestión dun curso:
     *
     * @param actionEvent A acción que tivo lugar
     */
    public void btnApuntarseAction(ActionEvent actionEvent) {
        //Neste caso, o que teremos que facer é recopilar os datos completos do curso seleccionado:
        //Para iso, empezamos mirando se hai unha selección feita:
        Curso selected = (Curso) taboaCursos.getSelectionModel().getSelectedItem();
        if (selected != null) {
            //Gardamos o resultado noutra variable para refrescar toda a información
            //Iso será o que se lle pase á ventá de xestión dun curso:
            Curso res = getFachadaAplicacion().recuperarDatosCurso(selected);
            if (res != null) {
                // Amosamos unha mensaxe por pantalla en como esta anotado no curso
                getFachadaAplicacion().mostrarInformacion("Cursos", "Boas, " + usuario.getNome()
                        + " agora estás apuntado no curso " + selected.getNome() + "!");
            } else {
                // Se houbo problemas ao recuperar a información, avisamos do erro:
                getFachadaAplicacion().mostrarErro("Cursos",
                        "Produciuse un erro ao recuperar os datos do curso.");
                // Para evitar problemas maiores, actualizaremos a táboa de cursos (se vale null é probable que se borrara):
                actualizarTaboaCursos(getFachadaAplicacion().consultarCursosAbertos(null));
            }
        } else {
            // Se non se ten selección, indícase que hai que facela primeiro (podería ser que a lista estivese vacía):
            this.getFachadaAplicacion().mostrarErro("Cursos",
                    "Selecciona un curso primeiro!");
        }
    }

    /**
     * Método que se executa cando se pulsa o checkbox para resaltar os cursos que xa remataron.
     *
     * @param actionEvent A acción que tivo lugar.
     */
    public void checkResaltarAction(ActionEvent actionEvent) {
        // Únicamente se refresca a táboa:
        taboaCursos.refresh();
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

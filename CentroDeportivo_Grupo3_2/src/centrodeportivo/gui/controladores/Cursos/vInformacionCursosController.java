package centrodeportivo.gui.controladores.Cursos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.actividades.Actividade;
import centrodeportivo.aplicacion.obxectos.actividades.Curso;
import centrodeportivo.aplicacion.obxectos.tipos.TipoResultados;
import centrodeportivo.aplicacion.obxectos.usuarios.Socio;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.gui.controladores.AbstractController;
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
import java.util.ResourceBundle;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Víctor Barreiro
 * Clase que servirá de controlador da pantalla que amosa información dun curso ó usuario.
 */
public class vInformacionCursosController extends AbstractController implements Initializable {


    public TextField campoNome;
    public TextField campoDuracion;
    public TextField campoDataInicio;
    public TextField campoDataFin;
    public TextArea campoDescricion;
    public TableView taboaActividades;
    public Button btnXestionar;

    private vPrincipalController controllerPrincipal;
    private Curso curso;
    private boolean estaApuntado = false;
    private Usuario usuario;

    /**
     * Constructor do controlador da pantalla de administración de tipos de actividades.
     *
     * @param fachadaAplicacion   A referencia á fachada da parte de aplicación.
     * @param controllerPrincipal A referencia ao controlador da ventá principal.
     */
    public vInformacionCursosController(FachadaAplicacion fachadaAplicacion, vPrincipalController controllerPrincipal, Usuario usuario) {
        // Chamamos ao constructor da clase pai:
        super(fachadaAplicacion);
        // Asignamos o parámetro pasado de controlador da ventá principal ao atributo correspondente:
        this.controllerPrincipal = controllerPrincipal;
        // Asignamos o usuario que esta loggeado:
        this.usuario = usuario;
    }

    /**
     * Método que nos permite inicializar a pantalla ao abrila.
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        campoNome.setDisable(true);
        campoNome.setText(curso.getNome());
        campoDuracion.setDisable(true);
        campoDuracion.setText(curso.getDuracion().intValue() + "h: " +
                (int) ((curso.getDuracion().floatValue() - curso.getDuracion().intValue()) * 60) + "m");
        campoDataInicio.setDisable(true);
        campoDataInicio.setText(new SimpleDateFormat("dd/MM/yyyy").format(curso.getDataInicio().getTime()));
        campoDataFin.setDisable(true);
        campoDataFin.setText(new SimpleDateFormat("dd/MM/yyyy").format(curso.getDataFin().getTime()));
        campoDescricion.setDisable(true);
        campoDescricion.setText(curso.getDescricion());

        // Cambiamos o texto do botón apuntarse/desapuntarse en función de si o usuario esta apuntado ou non:
        if (estaApuntado) {
            btnXestionar.setText("Desapuntarse");
        } else {
            btnXestionar.setText("Apuntarse");
        }

        // Desabilitamos o botón de apuntarse desapuntarse se o curso xa se inicio:
        if (curso.getDataInicio() != null && curso.getDataInicio().toLocalDate().isBefore(LocalDate.now())) {
            btnXestionar.setDisable(true);
        }

        TableColumn<Actividade, String> nomeColumn = new TableColumn<>("Nome");
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Actividade, String> dataActividadeColumn = new TableColumn<>("Data");
        dataActividadeColumn.setCellValueFactory(c -> new SimpleStringProperty(
                new SimpleDateFormat("dd/MM/yyyy").format(c.getValue().getData().getTime())
        ));

        TableColumn<Actividade, String> duracionColumn = new TableColumn<>("Duración");
        duracionColumn.setCellValueFactory(c -> new SimpleStringProperty(
                //Basicamente collo a parte enteira e logo a decimal multiplicada por 60:
                c.getValue().getDuracion().intValue() + "h, " +
                        (int) ((c.getValue().getDuracion().floatValue() - c.getValue().getDuracion().intValue()) * 60) + "m"
        ));

        TableColumn<Actividade, String> areaInstalacionColumn = new TableColumn<>("Area(Instalación)");
        areaInstalacionColumn.setCellValueFactory(c -> new SimpleStringProperty(
                // Basicamente collo a parte enteira e logo a decimal multiplicada por 60:
                c.getValue().getArea().getNome() + " (" +
                        c.getValue().getArea().getInstalacion().getNome() + ")"
        ));

        // Metemos as columnas creadas na táboa:
        taboaActividades.getColumns().addAll(nomeColumn, dataActividadeColumn, duracionColumn, areaInstalacionColumn);

        // Recuperamos a información dun curso
        curso = getFachadaAplicacion().recuperarDatosCurso(curso);
        // De non estar valeira, engadimos a sua descrición no campo:
        if (!curso.getDescricion().isEmpty()) {
            campoDescricion.setText(curso.getDescricion());
        }
        // Recuperamos as actividadaes do curso para engadilas a taboa
        taboaActividades.getItems().addAll(curso.getActividades());
        // Controlamos tamaño das columnas:
        taboaActividades.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //  Modelo de selección:
        taboaActividades.getSelectionModel().selectFirst();

    }


    /**
     * Setters
     */
    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public void setEstaApuntado(boolean estaApuntado) {
        this.estaApuntado = estaApuntado;
    }

    /**
     * Método que permite que te apuntes/desapuntes dun curso en función de se estas actualmente anotado ou non
     *
     * @param actionEvent Accion ou evento que tivo lugar
     */
    public void btnXestionarAction(ActionEvent actionEvent) {
        if (estaApuntado) {
            // No caso de que xa este apuntado, desapuntaste:
            if (curso != null) {
                // Gardamos o resultado noutra variable para refrescar toda a información
                // Iso será o que se lle pase ó método de desapuntarse
                Curso res = getFachadaAplicacion().recuperarDatosCurso(curso);
                if (res != null) {
                    // Tentamos desanotar o usuario do curso
                    try {
                        TipoResultados resultado = getFachadaAplicacion().desapuntarseCurso(res, usuario);
                        switch (resultado) {
                            case correcto:
                                // Amosamos unha mensaxe por pantalla en como xa non esta anotado no curso
                                getFachadaAplicacion().mostrarInformacion("Cursos", "Boas, " + usuario.getNome()
                                        + ". Agora xa non estás apuntado no curso " + res.getNome() + "!");
                                // Volvemos a pantalla anterior:
                                this.controllerPrincipal.mostrarPantalla(IdPantalla.ELIXIRCURSO);
                                break;
                            case sitIncoherente:
                                // Amosamos unha mensaxe por pantalla en como esta anotado no curso
                                getFachadaAplicacion().mostrarErro("Cursos", "Boas, " + usuario.getNome()
                                        + ". Non te podes desapuntar do curso " + res.getNome() + "!");
                                // Volvemos a pantalla anterior:
                                this.controllerPrincipal.mostrarPantalla(IdPantalla.ELIXIRCURSO);
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
                    // Volvemos a pantalla anterior:
                    this.controllerPrincipal.mostrarPantalla(IdPantalla.ELIXIRCURSO);
                }
            } else {
                // Se non se ten selección, indícase que hai que facela primeiro (podería ser que a lista estivese vacía):
                this.getFachadaAplicacion().mostrarErro("Cursos",
                        "Selecciona un curso do que desapuntarte!");
            }
        } else {
            // No caso de que non este apuntado, apuntase:
            if (curso != null) {
                // Gardamos o resultado noutra variable para refrescar toda a información
                // Iso será o que se lle pase ó método de apuntarse
                Curso res = getFachadaAplicacion().recuperarDatosCurso(curso);
                if (res != null) {
                    // Anotamolo no curso
                    try {
                        TipoResultados resultado = getFachadaAplicacion().apuntarseCurso(res, usuario);
                        switch (resultado) {
                            case correcto:
                                // Amosamos unha mensaxe por pantalla en como xa esta anotado no curso
                                getFachadaAplicacion().mostrarInformacion("Cursos", "Boas, " + usuario.getNome()
                                        + ". Agora estas apuntado no curso " + res.getNome() + "!");
                                // Volvemos a pantalla anterior:
                                this.controllerPrincipal.mostrarPantalla(IdPantalla.ELIXIRCURSO);
                                break;
                            case sitIncoherente:
                                // Amosamos unha mensaxe por pantalla en como esta anotado no curso
                                getFachadaAplicacion().mostrarErro("Cursos", "Boas, " + usuario.getNome()
                                        + ". Non te podes apuntar no curso " + res.getNome() + "!");
                                // Voltamos a pantalla anterior
                                this.controllerPrincipal.mostrarPantalla(IdPantalla.ELIXIRCURSO);
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
                }
            } else {
                // Se non se ten selección, indícase que hai que facela primeiro (podería ser que a lista estivese vacía):
                this.getFachadaAplicacion().mostrarErro("Cursos",
                        "Selecciona un curso no que apuntarte!");
            }
            // Voltamos a pantalla anterior
            this.controllerPrincipal.mostrarPantalla(IdPantalla.ELIXIRCURSO);
        }
    }

    /**
     * Método que volve a pantalla anterior cando premes o botón volver
     *
     * @param actionEvent Accion ou evento que ten lugar
     */
    public void btnVolverAction(ActionEvent actionEvent) {
        this.controllerPrincipal.mostrarPantalla(IdPantalla.ELIXIRCURSO);
    }

    public void btnRefrescarAction(ActionEvent actionEvent) {
        // Actualizamos a información do curso
        curso = getFachadaAplicacion().recuperarDatosCurso(curso);
        // Recuperamos as actividadaes do curso para engadilas a taboa
        taboaActividades.getItems().addAll(curso.getActividades());
        // Controlamos tamaño das columnas:
        taboaActividades.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //  Modelo de selección:
        taboaActividades.getSelectionModel().selectFirst();
    }

}

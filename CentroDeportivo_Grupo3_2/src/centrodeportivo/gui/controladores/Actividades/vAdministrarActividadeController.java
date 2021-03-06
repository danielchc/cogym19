package centrodeportivo.gui.controladores.Actividades;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.Mensaxe;
import centrodeportivo.aplicacion.obxectos.actividades.Actividade;
import centrodeportivo.aplicacion.obxectos.tipos.TipoResultados;
import centrodeportivo.funcionsAux.ValidacionDatos;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.principal.IdPantalla;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Víctor Barreiro
 * Clase que servirá de controlador da pantalla de administrar actividades
 */
public class vAdministrarActividadeController extends AbstractController implements Initializable {

    /**
     * Atributos públicos: correspóndense con elementos que se atopan na ventá de administración de actividades
     */
    public TextField campoNome;
    public Button btnBuscar;
    public Button btnLimpar;
    public TableView taboaActividade;
    public Button btnRexistrar;
    public Button btnXerarInforme;
    public Button btnXestionar1;

    /**
     * Atributos privados: somentes temos un que é a referencia ao controlador da ventá principal.
     */
    private vPrincipalController controllerPrincipal;

    /**
     * Constructor don controlador pantlla de administrar actividades
     *
     * @param fachadaAplicacion
     * @param controllerPrincipal
     */
    public vAdministrarActividadeController(FachadaAplicacion fachadaAplicacion, vPrincipalController controllerPrincipal) {
        // Chamamos ao constructor da clase pai coa fachada da aplicación.
        super(fachadaAplicacion);
        // Asignamos o controlador principal ao atributo correspondente.
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
        // Insertamos as columnas da táboa, na data, formateamola:
        TableColumn<Actividade, String> coldata = new TableColumn<>("Data");
        coldata.setCellValueFactory(c -> new SimpleStringProperty(
                new SimpleDateFormat("dd/MM/yyyy kk:mm").format(((Actividade) c.getValue()).getData())
        ));

        // Insertamos a columna do nome:
        TableColumn<Actividade, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        // Insertamos a columna ca duración:
        TableColumn<Actividade, Timestamp> colduracion = new TableColumn<>("Duracion");
        colduracion.setCellValueFactory(new PropertyValueFactory<>("duracion"));

        // Insertamos unha columna para a área:
        TableColumn<Actividade, String> colarea = new TableColumn<>("Area");
        colarea.setCellValueFactory(new PropertyValueFactory<>("areanome"));
        colarea.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Actividade, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Actividade, String> param) {
                return new SimpleObjectProperty<String>(param.getValue().getArea().getNome());
            }
        });

        // Engadimos tamén, unha columna ca instalación:
        TableColumn<Actividade, String> colinstalacion = new TableColumn<>("Instalacion");
        colinstalacion.setCellValueFactory(new PropertyValueFactory<>("instalacion"));
        colinstalacion.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Actividade, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Actividade, String> param) {
                return new SimpleObjectProperty<String>(param.getValue().getArea().getInstalacion().getNome());
            }
        });

        // E por último, engadimos unha columna co profesor:
        TableColumn<Actividade, Timestamp> colprofesor = new TableColumn<>("Profesor");
        colprofesor.setCellValueFactory(new PropertyValueFactory<>("profesor"));

        // Engadimos as columnas á táboa:
        taboaActividade.getColumns().addAll(coldata, colNome, colduracion, colarea, colinstalacion, colprofesor);
        // Adaptamola o tamaño da ventá:
        taboaActividade.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        // Establecemos un listener para a táboa:
        taboaActividade.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) listenerTabla();
        });

        // Chamamos a función actualizar táboa para cargar os datos:
        actualizarTaboa();
    }

    /**
     * Método para cargar a información na táboa e actualizala
     */
    private void actualizarTaboa() {
        taboaActividade.getItems().removeAll(taboaActividade.getItems());
        Actividade actividade = null;
        if (ValidacionDatos.estanCubertosCampos(campoNome)) {
            actividade = new Actividade(campoNome.getText());
        }
        // Realizamos unha busca segundo os parametros anteriores
        taboaActividade.getItems().addAll(super.getFachadaAplicacion().buscarActividade(actividade));
        if (taboaActividade.getItems().size() != 0) {
            taboaActividade.getSelectionModel().selectFirst();
        }
        listenerTabla();
    }

    /**
     * Método que se executa cando se preme o botón buscar
     *
     * @param actionEvent Evento que tivo lugar
     */
    public void btnBuscarAction(ActionEvent actionEvent) {
        actualizarTaboa();
    }

    /**
     * Método que se executa cando se preme o botón de limpar campos.
     *
     * @param actionEvent Evento que tivo lugar
     */
    public void btnLimparAction(ActionEvent actionEvent) {
        campoNome.clear();
        actualizarTaboa();
    }

    /**
     * Listener que se lle aplica a táboa
     */
    public void listenerTabla() {
        if (!taboaActividade.getSelectionModel().isEmpty()) {
            Actividade actividade = (Actividade) taboaActividade.getSelectionModel().getSelectedItem();
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(actividade.getData().getTime());
            cal.add(Calendar.SECOND, (int) (actividade.getDuracion() * 3600));
            boolean estaAcabada = (new Timestamp(System.currentTimeMillis())).after(new Timestamp(cal.getTime().getTime()));
            // Se a actividade seleccionada non esta rematada, desactivamos o botón de xerar informe:
            btnXerarInforme.setDisable(!estaAcabada);
            // Se a actividade seleccionada esta rematada, desactivamos o botonXestionar1
            btnXestionar1.setDisable(estaAcabada);
        }
    }

    /**
     * Método que ten lugar cando se preme o botón de xestionar a actividade seleccionada na táboa.
     *
     * @param actionEvent Evento que tivo lugar
     */
    public void btnXestionarAction(ActionEvent actionEvent) {
        if (!taboaActividade.getSelectionModel().isEmpty()) {
            // Recuperamos primeiro a actividade seleccionada:
            Actividade act = (Actividade) taboaActividade.getSelectionModel().getSelectedItem();
            // Comprobamos se o item seleccionado non é nulo: se o é, é que non se seleccionou ningún item da táboa.
            if (act != null) {
                // Se non é null seguimos adiante.
                // Feito iso, facemos que a ventá visíbel sexa a de edición dunha actividade:
                this.controllerPrincipal.mostrarPantalla(IdPantalla.INSERCIONACTIVIDADE);
                // Accedemos ao controlador da ventá de edición dunha actividade:
                ((vInsercionActividadeController) this.controllerPrincipal.getControlador(IdPantalla.INSERCIONACTIVIDADE)).cargarActividade((Actividade) taboaActividade.getSelectionModel().getSelectedItem());

            } else {
                // En caso de que o item si sexa nulo, haberá que mostrar un erro pedindo unha selección:
                this.getFachadaAplicacion().mostrarErro("Administración de actividades", "Non hai celda seleccionada!");
            }
        }
    }

    /**
     * Método que ten lugar cando se preme o botón de cancelar
     */
    public void btnCancelarAction() {
        if (taboaActividade.getSelectionModel().isEmpty()) {
            // Se non esta seleccionada ningunha actividade da táboa
            this.getFachadaAplicacion().mostrarErro("Administración de Actividades",
                    "Debe selectionar unha actividade para ser borrada.");
        } else {
            // Se hai unha actividade seleccionada:
            if (getFachadaAplicacion().mostrarConfirmacion("Administración de actividades",
                    "Desexa cancelar a actividade seleccionada?") == ButtonType.OK) {
                try {
                    Actividade act = (Actividade) taboaActividade.getSelectionModel().getSelectedItem();
                    Mensaxe mensaxe = new Mensaxe(controllerPrincipal.getUsuario(),
                            "Prezado socio\nA actividade '" + act.getNome() + "' foi cancelada. Desculpe as molestias.");
                    TipoResultados res = this.getFachadaAplicacion().borrarActividade(act, mensaxe);
                    switch (res) {
                        case correcto:
                            // Avísase de que o borrado se fixo correctamente:
                            super.getFachadaAplicacion().mostrarInformacion("Administración de actividades",
                                    "A actividade '" + act.getNome() + "' foi borrada correctamente.");
                            break;
                        case sitIncoherente:
                            // Se non se puidese borrar a actividade, avisaríase do problema:
                            super.getFachadaAplicacion().mostrarErro("Administración de actividades",
                                    "Non se pode cancelar a actividade '" + act.getNome() + "', pois xa foi" +
                                            " realizada!");
                            break;
                    }
                } catch (ExcepcionBD e) {
                    super.getFachadaAplicacion().mostrarErro("Administración de actividades",
                            e.getMessage());
                }
            }
            // En calquera caso refréscase a táboa:
            actualizarTaboa();
        }
    }

    /**
     * Método que xera un informe da actividade seleccionada como resposta a premer no botón de xerar informe
     *
     * @param actionEvent Evento que tivo lugar
     */
    public void btnInformeAction(ActionEvent actionEvent) {
        if (!taboaActividade.getSelectionModel().isEmpty()) {
            // Recuperamos primeiro a actividade seleccionada:
            Actividade act = (Actividade) taboaActividade.getSelectionModel().getSelectedItem();
            // Comprobamos se o item seleccionado non é nulo: se o é, é que non se seleccionou ningún item da táboa.
            if (act != null) {
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(act.getData().getTime());
                cal.add(Calendar.SECOND, (int) (act.getDuracion() * 3600));
                boolean estaAcabada = (new Timestamp(System.currentTimeMillis())).after(new Timestamp(cal.getTime().getTime()));
                // Se non é null seguimos adiante.
                if (estaAcabada) {
                    // Accedemos ao controlador da ventá de edición dunha actividade:
                    ((vInformeActividadeController) this.controllerPrincipal.getControlador(IdPantalla.INFORMEACTIVIDADE)).setActividade(act);
                    // Feito iso, facemos que a ventá visíbel sexa a de edición dunha actividade:
                    this.controllerPrincipal.mostrarPantalla(IdPantalla.INFORMEACTIVIDADE);
                } else {
                    // Amosamos unha mensaxe co erro por pantalla:
                    getFachadaAplicacion().mostrarErro("Informe de Actividades", "A actividade ainda non rematou!");
                }
            } else {
                // Infrmase do erro:
                getFachadaAplicacion().mostrarErro("Informe de Actividades", "Selecciona a actividade da que queiras ver o informe!");
            }
        }
    }

}
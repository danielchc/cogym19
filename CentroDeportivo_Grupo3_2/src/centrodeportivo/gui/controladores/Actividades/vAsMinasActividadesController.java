package centrodeportivo.gui.controladores.Actividades;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.actividades.Actividade;
import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.aplicacion.obxectos.tipos.TipoResultados;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.funcionsAux.ValidacionDatos;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.principal.IdPantalla;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Víctor Barreiro
 * Clase que servirá de controlador da pantalla de administración de tipos de actividades.
 */
public class vAsMinasActividadesController extends AbstractController implements Initializable {


    /**
     * Atributos públicos: son as compoñentes da ventá.
     */
    public TextField campoNome;
    public ComboBox comboInstalacion;
    public ComboBox comboArea;
    public TableView taboaActividade;
    public Button btnDesapuntarse;
    public Button btnApuntarse;
    public Button btnValorar;
    public CheckBox checkApuntado;
    public TextArea campoInfo;

    /**
     * Atributos privados: son a referencia ao controlador da ventá principal e o usuario que se está xestionando:
     */
    private vPrincipalController controllerPrincipal;
    private Usuario usuario;

    /**
     * Constructor do controlador da pantalla de administración de tipos de actividades.
     *
     * @param fachadaAplicacion   A referencia á fachada da parte de aplicación.
     * @param controllerPrincipal A referencia ao controlador da ventá principal.
     */
    public vAsMinasActividadesController(FachadaAplicacion fachadaAplicacion, vPrincipalController controllerPrincipal, Usuario usuario) {
        // Chamamos ao constructor da clase pai:
        super(fachadaAplicacion);
        // Asignamos o parámetro pasado de controlador da ventá principal ao atributo correspondente:
        this.controllerPrincipal = controllerPrincipal;
        // Asignamos o usuario que esta loggeado
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
        //Engadimos os items no combo que recolle as instalacións
        this.comboInstalacion.getItems().addAll(super.getFachadaAplicacion().buscarInstalacions(null));

        //Engadimos un listener para que en función da instalación seleccionada se amosen determinadas áreas no combo de áreas:
        this.comboInstalacion.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                Instalacion instalacion = (Instalacion) observableValue.getValue();
                if (instalacion == null) return;
                comboArea.setItems(FXCollections.observableArrayList(getFachadaAplicacion().buscarArea(instalacion, null)));
            }
        });

        //Completamos a táboa de actividades con estas columnas:
        //Data
        TableColumn<Actividade, Timestamp> coldata = new TableColumn<>("Data");
        coldata.setCellValueFactory(new PropertyValueFactory<>("Data"));
        //Nome
        TableColumn<Actividade, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        //Duración:
        TableColumn<Actividade, Timestamp> colduracion = new TableColumn<>("Duracion");
        colduracion.setCellValueFactory(new PropertyValueFactory<>("duracion"));
        //Tipo de actividade (da que amosaremos o nome):
        TableColumn<Actividade, String> coltipoactividade = new TableColumn<>("Tipo Actividade");
        coltipoactividade.setCellValueFactory(new PropertyValueFactory<>("tipoactividadenome"));
        coltipoactividade.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Actividade, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Actividade, String> param) {
                return new SimpleObjectProperty<String>(param.getValue().getTipoActividadenome());
            }
        });

        //Engadimos as columnas á táboa
        taboaActividade.getColumns().addAll(coldata, colNome, colduracion, coltipoactividade);

        //Actualizaremos a táboa correspondente:
        actualizarTabla();
    }

    /**
     * Método que representa as accións levadas a cabo ao querer actualizar a táboa de actividades:
     */
    private void actualizarTabla() {
        //Baleiramos a táboa de actividades inicialmente:
        taboaActividade.getItems().removeAll(taboaActividade.getItems());

        //Recuperamos o nome introducido no campo de busca:
        String nome = campoNome.getText();
        //Recollemos o usuario:
        Usuario usuario = this.controllerPrincipal.getUsuario();
        Actividade actividade = null;
        Area area = null;
        Instalacion instalacion = null;

        //Se os campos están cubertos ou están seleccionados elementos nos combos, é que hai filtros de busca:
        if (ValidacionDatos.estanCubertosCampos(campoNome) || !comboInstalacion.getSelectionModel().isEmpty() || !comboArea.getSelectionModel().isEmpty()) {
            //Se se seleccionou unha instalación, asígnase.
            if (!comboInstalacion.getSelectionModel().isEmpty()) {
                instalacion = (Instalacion) comboInstalacion.getSelectionModel().getSelectedItem();
                //Créase unha área con código -1, que pode ser cambiada en caso de que haxa unha área seleccionada no seu combo.
                area = new Area(-1, instalacion);
                if (!comboArea.getSelectionModel().isEmpty()) {
                    //Se hai área seleccionada, escóllese.
                    area = (Area) comboArea.getSelectionModel().getSelectedItem();
                }
            }
            //Creamos a actividade co nome correspondente e a área na que ten lugar:
            actividade = new Actividade(nome, area);
        }

        //Se o checkbox no que o usuario pide que se mostren as actividades nas que está apuntado está seleccionado, entón
        //só se poderán amosar as actividades nas que ese socio participa.
        if (checkApuntado.isSelected()) {
            taboaActividade.getItems().addAll(super.getFachadaAplicacion().buscarActividadeParticipa(actividade, usuario));
        } else {
            taboaActividade.getItems().addAll(super.getFachadaAplicacion().buscarActividadeNONParticipa(actividade, usuario));
        }

        //Se a táboa de actividades ten resultados, escollemos unha selección.
        if (taboaActividade.getItems().size() != 0) {
            taboaActividade.getSelectionModel().selectFirst();
        }
        //Executamos o listener correspondente:
        listenerTabla();
    }

    /**
     * Listener da táboa de actividades:
     */
    public void listenerTabla() {
        //Se a táboa de actividades ten unha selección, haberá que facer comprobacións:
        if (!taboaActividade.getSelectionModel().isEmpty()) {
            //Recuperamos a actividade:
            Actividade actividade = (Actividade) taboaActividade.getSelectionModel().getSelectedItem();
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(actividade.getData().getTime());
            cal.add(Calendar.SECOND, (int) (actividade.getDuracion() * 3600));

            //Comprobamos así se a actividade está rematada ou non:
            boolean estaAcabada = (new Timestamp(System.currentTimeMillis())).after(new Timestamp(cal.getTime().getTime()));

            //Comprobamos se a actividade non está acabada e se non está seleccionado o checkbox no que o usuario
            //marca se quere ver as actividades nas que está anotado.
            //Nese caso, deshabilítase o botón de valorar a actividade
            btnValorar.setDisable(!(estaAcabada && checkApuntado.isSelected()));
            //Se a actividade está valorada, tamén se deshabilita ese botón:
            if (getFachadaAplicacion().isValorada(actividade, usuario)) {
                btnValorar.setDisable(true);
            }
            //O botón de desanotarse deshabilitarase se a actividade xa se levou a cabo ou se a actividade pertence a un curso:
            btnDesapuntarse.setDisable(!(!estaAcabada && checkApuntado.isSelected() && actividade.getCurso().getCodCurso() == 0));
            //O botón de apuntarse deshabilitarase cando se amosen as actividades nas que o usuario está anotado. Noutro
            //caso, habilitaranse:
            btnApuntarse.setDisable(checkApuntado.isSelected());

            //Recollemos a información da actividade e elaboramos un string que se amosará no campo de información:
            //Nel estará toda a información da actividade:
            String infoActividade = String.format(
                    "Nome: %s\nData: %s\nHora: %s\nDuración: %s\nInstalación: %s\nÁrea: %s\nTipo: %s\nCurso: %s",
                    actividade.getNome(),
                    new SimpleDateFormat("dd/MM/yyyy").format(new Date(actividade.getData().getTime())),
                    new SimpleDateFormat("HH:mm").format(new Date(actividade.getData().getTime())),
                    (actividade.getDuracion() * 60) + " minutos",
                    actividade.getArea().getInstalacion().getNome(),
                    actividade.getArea().getNome(),
                    actividade.getTipoActividadenome(),
                    actividade.getCurso().getCodCurso() != 0 ? actividade.getCurso().getCodCurso() : "-"
            );

            //Establecemos texto do campo de información:
            campoInfo.setText(infoActividade);
        }
    }

    /**
     * Método que representa o listener do checkbox.
     * Actualizarase a táboa a raíz de marcar/desmarcar o checkbox.
     */
    public void listenerCheckBox() {
        actualizarTabla();
    }

    /**
     * Método que representa as accións realizadas ao premer o botón de buscar:
     *
     * @param actionEvent A acción que tivo lugar.
     */
    public void btnBuscarAction(ActionEvent actionEvent) {
        //Chamaremos ao método no que actualizamos a táboa:
        actualizarTabla();
    }

    /**
     * Método que representa as accións levadas a cabo ao premer o botón de limpeza dos campos.
     */
    public void onActionLimpar() {
        //Limpamos as seleccións dos combobox e os campos de busca:
        this.comboInstalacion.getSelectionModel().clearSelection();
        this.comboArea.getSelectionModel().clearSelection();
        this.campoNome.clear();
        //Actualizamos a táboa:
        actualizarTabla();
    }

    /**
     * Método que representa as accións levadas a cabo ao premer o botón de valorar unha actividade.
     */
    public void onActionValorar() {
        // Pasamoslle a actividade se non e nula
        if ((Actividade) taboaActividade.getSelectionModel().getSelectedItem() != null) {
            //Abrirase a ventá de valoración: asociámoslle a actividade correspondente.
            vValorarPopUpController cont = (vValorarPopUpController) controllerPrincipal.getControlador(IdPantalla.VALORARACTIVIDADEPOPUP);
            cont.setActividade((Actividade) taboaActividade.getSelectionModel().getSelectedItem());
            //Amosaremos a pantalla:
            controllerPrincipal.mostrarPantalla(IdPantalla.VALORARACTIVIDADEPOPUP);
        } else {
            //Se non hai selección feita, entón amosamos unha mensaxe:
            getFachadaAplicacion().mostrarErro("Valorar Actividades",
                    "Selecciona a actividade que queres valorar!");
        }
    }

    /**
     * Método que representa as accións levadas a cabo ao premer no botón de apuntarse nunha actividade.
     */
    public void onActionApuntarse() {
        //Comprobamos que haxa unha selección sobre a táboa:
        if (!taboaActividade.getSelectionModel().isEmpty()) {
            //Recuperamos a actividade correspondente:
            Actividade actividade = (Actividade) taboaActividade.getSelectionModel().getSelectedItem();
            if (super.getFachadaAplicacion().mostrarConfirmacion("Actividade", "Quereste apuntar a " + actividade.getNome() + "?") == ButtonType.OK) {
                //Preguntamos se o usuario se quere apuntar á actividade para confirmar e entón pasaremos a chamar á parte de aplicación:
                try {
                    TipoResultados tipoResultados = super.getFachadaAplicacion().apuntarseActividade(actividade, controllerPrincipal.getUsuario());

                    //resultado; avaliámolo.
                    switch (tipoResultados) {
                        case correcto:
                            //En caso de ser correcto, avisamos de que se apuntou á actividade.
                            super.getFachadaAplicacion().mostrarInformacion("Actividade",
                                    "Apuntacheste á actividade " + actividade.getNome());
                            break;
                        case sitIncoherente:
                            //En caso de que non haxa prazas, entón avísase:
                            super.getFachadaAplicacion().mostrarErro("Actividade",
                                    "Non hai prazas dispoñibles. O aforo é máximo.");
                            break;
                        case datoExiste:
                            //En caso de que houbese algún erro e o usuario xa estivese anotado, avísase:
                            super.getFachadaAplicacion().mostrarErro("Actividade",
                                    "Xa está apuntado nesta actividade, non te podes volver a apuntar.");
                            break;
                    }
                    //Ao rematar, actualizamos a táboa correspondente:
                    actualizarTabla();
                } catch (ExcepcionBD e) {
                    //En caso de ter unha excepción, amosámola.
                    getFachadaAplicacion().mostrarErro("Actividade", e.getMessage());
                }
            }
        }
    }

    /**
     * Método que representa as accións levadas a cabo ao premer no botón de desanotarse da actividade.
     */
    public void onActionDesapuntarse() {
        //Comprobamos que haxa, coma no caso de anotarse, unha selección sobre a táboa:
        if (!taboaActividade.getSelectionModel().isEmpty()) {
            //Seleccionamos a actividade:
            Actividade actividade = (Actividade) taboaActividade.getSelectionModel().getSelectedItem();
            //Preguntamos se realmente se quere desanotar da actividade:
            if (super.getFachadaAplicacion().mostrarConfirmacion("Actividade", "Quereste desapuntar da actividade " + actividade.getNome()) == ButtonType.OK) {
                //Inténtase acceder á base de datos:
                try {
                    TipoResultados tipoResultados = super.getFachadaAplicacion().borrarseDeActividade(actividade, controllerPrincipal.getUsuario());
                    //Avaliamos o resultado:
                    switch (tipoResultados) {
                        //En caso de que rematase correctamente, avísase diso:
                        case correcto:
                            super.getFachadaAplicacion().mostrarInformacion("Actividade",
                                    "Desapuntácheste da actividade " + actividade.getNome());
                            break;
                        //En caso de ter que xa non se esté apuntado á actividade, entón avísase:
                        case sitIncoherente:
                            super.getFachadaAplicacion().mostrarErro("Actividade",
                                    "Non está apuntado a esta actividade.");
                            break;
                    }
                    //Actualizamos a táboa de resultados en calquera caso:
                    actualizarTabla();
                } catch (ExcepcionBD e) {
                    //Amosaremos un erro en caso de que se produza unha excepción da base de datos:
                    getFachadaAplicacion().mostrarErro("Actividade", e.getMessage());
                }
            }
        }
    }
}
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
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.ResourceBundle;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Víctor Barreiro
 * Clase que servirá de controlador da pantalla de administración de tipos de actividades.
 */
public class vAsMinasActividadesController extends AbstractController implements Initializable {


    public TextField campoNome;
    public ComboBox comboInstalacion;
    public ComboBox comboArea;
    public TableView taboaActividade;
    public Button btnDesapuntarse;
    public Button btnValorar;

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
        this.comboInstalacion.getItems().addAll(super.getFachadaAplicacion().buscarInstalacions(null));
        if (!this.comboInstalacion.getItems().isEmpty()) this.comboInstalacion.getSelectionModel().selectFirst();

        this.comboInstalacion.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                Instalacion instalacion = (Instalacion) observableValue.getValue();
                comboArea.setItems(FXCollections.observableArrayList(getFachadaAplicacion().buscarArea(instalacion, null)));
                if (!comboArea.getItems().isEmpty()) comboArea.getSelectionModel().selectFirst();
            }
        });


        TableColumn<Actividade, Timestamp> coldata = new TableColumn<>("Data");
        coldata.setCellValueFactory(new PropertyValueFactory<>("Data"));

        TableColumn<Actividade, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Actividade, Timestamp> colduracion = new TableColumn<>("Duracion");
        colduracion.setCellValueFactory(new PropertyValueFactory<>("duracion"));

        TableColumn<Actividade, String> coltipoactividade = new TableColumn<>("Tipo Actividade");
        coltipoactividade.setCellValueFactory(new PropertyValueFactory<>("tipoactividadenome"));
        coltipoactividade.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Actividade, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Actividade, String> param) {
                return new SimpleObjectProperty<String>(param.getValue().getTipoActividadenome());
            }
        });


        taboaActividade.setRowFactory(tv -> {
            TableRow<Actividade> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Actividade rowData = row.getItem();
                    abrirPopUpInformacion(rowData);
                }
            });
            return row;
        });
        //Engadimos as columnas á táboa
        taboaActividade.getColumns().addAll(coldata, colNome, colduracion, coltipoactividade);

        actualizarTabla();
    }

    private void actualizarTabla() {
        //REVISAR
        taboaActividade.getItems().removeAll(taboaActividade.getItems());
        String nome = campoNome.getText();

        Actividade actividade = null;

        if (ValidacionDatos.estanCubertosCampos(campoNome) || !comboInstalacion.getSelectionModel().isEmpty() || !comboArea.getSelectionModel().isEmpty()) {
            Area area = null;
            if (!comboArea.getSelectionModel().isEmpty()) {
                area = (Area) comboArea.getSelectionModel().getSelectedItem();
                Instalacion instalcion = (Instalacion) comboInstalacion.getSelectionModel().getSelectedItem();
                if (area != null)
                    area.setInstalacion(instalcion);
            }
            actividade = new Actividade(nome, area);
        }

        taboaActividade.getItems().addAll(super.getFachadaAplicacion().buscarActividadeParticipa(actividade, usuario));


        if (taboaActividade.getItems().size() != 0) {
            taboaActividade.getSelectionModel().selectFirst();
        }
    }

    public void btnBuscarAction(ActionEvent actionEvent) {
        actualizarTabla();
    }

    private void abrirPopUpInformacion(Actividade actividade) {

    }

    private void onActionValorar(){
        this.controllerPrincipal.mostrarPantalla(IdPantalla.VALORARACTIVIDADEPOPUP);
    }

    private void onActionDesapuntarse(){
        if(!taboaActividade.getSelectionModel().isEmpty()){
            Actividade actividade=(Actividade) taboaActividade.getSelectionModel().getSelectedItem();
            if(super.getFachadaAplicacion().mostrarConfirmacion("Actividade","Quereste desapuntar da actividade "+actividade.getNome())==ButtonType.OK){
                //desapuntrarse
                try{
                    TipoResultados tipoResultados=super.getFachadaAplicacion().borrarseDeActividade(actividade,controllerPrincipal.getUsuario());
                    super.getFachadaAplicacion().mostrarInformacion("Actividade","Desapuntacheste da actividade "+actividade.getNome());
                    /*

                        COMPROBAR OS RESULTADOS


                     */
                }catch (ExcepcionBD e){
                    getFachadaAplicacion().mostrarErro("Actividade", e.getMessage());
                }
            }
        }
    }
}

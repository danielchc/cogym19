package centrodeportivo.gui.controladores.Actividades;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.actividades.Actividade;
import centrodeportivo.aplicacion.obxectos.actividades.Curso;
import centrodeportivo.aplicacion.obxectos.actividades.TipoActividade;
import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.funcionsAux.ValidacionDatos;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.AuxGUI;
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
import javafx.util.Callback;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Víctor Barreiro
 * Clase que servirá de controlador da pantalla de administración de tipos de actividades.
 */
public class vElixirActividadeController extends AbstractController implements Initializable {


    public TextField campoNome;
    public ComboBox comboInstalacion;
    public ComboBox comboArea;
    public TableView taboaActividade;
    public Button btnXestionar;

    private vPrincipalController controllerPrincipal;
    private boolean isPantallaApuntarse;

    /**
     * Constructor do controlador da pantalla de administración de tipos de actividades.
     * @param fachadaAplicacion A referencia á fachada da parte de aplicación.
     * @param controllerPrincipal A referencia ao controlador da ventá principal.
     */
    public vElixirActividadeController(FachadaAplicacion fachadaAplicacion, vPrincipalController controllerPrincipal) {
        //Chamamos ao constructor da clase pai coa fachada da aplicación.
        super(fachadaAplicacion);
        //Asignamos o controlador principal ao atributo correspondente.
        this.controllerPrincipal = controllerPrincipal;
    }

    /**
     * Método que nos permite inicializar a pantalla ao abrila.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.isPantallaApuntarse=true;

        this.comboInstalacion.getItems().addAll(super.getFachadaAplicacion().buscarInstalacions(null));
        if(!this.comboInstalacion.getItems().isEmpty()) this.comboInstalacion.getSelectionModel().selectFirst();

        this.comboInstalacion.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                Instalacion instalacion=(Instalacion)observableValue.getValue();
                comboArea.setItems(FXCollections.observableArrayList(getFachadaAplicacion().buscarArea(instalacion,null)));
                if(!comboArea.getItems().isEmpty()) comboArea.getSelectionModel().selectFirst();
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
        //Engadimos as columnas á táboa
        taboaActividade.getColumns().addAll(coldata, colNome, colduracion, coltipoactividade);

        btnXestionar.setText("Apuntarse");
        btnXestionar.setDisable(true);

        actualizarTabla();
    }

    private void actualizarTabla(){
        taboaActividade.getItems().removeAll(taboaActividade.getItems());
        String nome=campoNome.getText();
        Instalacion instalacion=(Instalacion) comboInstalacion.getSelectionModel().getSelectedItem();
        Area area=(Area)comboArea.getSelectionModel().getSelectedItem();
        Usuario usuario=this.controllerPrincipal.getUsuario();

        //buscar segundo os parametros anteriores
        taboaActividade.getItems().addAll(super.getFachadaAplicacion().buscarActividade(null));
        if(taboaActividade.getItems().size()!=0){
            taboaActividade.getSelectionModel().selectFirst();
            btnXestionar.setDisable(false);
        }
    }

    public void btnBuscarAction(ActionEvent actionEvent){
        actualizarTabla();
    }

    public void btnXestionarAction(ActionEvent actionEvent){
        if(isPantallaApuntarse){
            //se se esta apuntando
        }else{
            //se esta para borrarse
        }
    }

    public void abrirPantallaDesapuntarse(){
        this.isPantallaApuntarse=false;
        btnXestionar.setText("Desapuntarse");
        btnXestionar.setDisable(true);
    }
}

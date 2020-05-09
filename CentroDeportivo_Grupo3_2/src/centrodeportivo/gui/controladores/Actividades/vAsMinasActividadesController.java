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


    public TextField campoNome;
    public ComboBox comboInstalacion;
    public ComboBox comboArea;
    public TableView taboaActividade;
    public Button btnDesapuntarse;
    public Button btnApuntarse;
    public Button btnValorar;
    public CheckBox checkApuntado;
    public TextArea campoInfo;

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

        this.comboInstalacion.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                Instalacion instalacion = (Instalacion) observableValue.getValue();
                if(instalacion==null) return;
                comboArea.setItems(FXCollections.observableArrayList(getFachadaAplicacion().buscarArea(instalacion, null)));
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

        actualizarTabla();
    }

    private void actualizarTabla() {
        //REVISAR
        taboaActividade.getItems().removeAll(taboaActividade.getItems());

        String nome = campoNome.getText();
        Usuario usuario = this.controllerPrincipal.getUsuario();
        Actividade actividade = null;
        Area area = null;
        Instalacion instalacion=null;

        if(ValidacionDatos.estanCubertosCampos(campoNome) || !comboInstalacion.getSelectionModel().isEmpty() || !comboArea.getSelectionModel().isEmpty()) {
            if(!comboInstalacion.getSelectionModel().isEmpty()){
                instalacion = (Instalacion) comboInstalacion.getSelectionModel().getSelectedItem();

                area=new Area(-1,instalacion);
                if (!comboArea.getSelectionModel().isEmpty()) {
                    area = (Area) comboArea.getSelectionModel().getSelectedItem();
                }
            }
            actividade = new Actividade(nome, area);
        }

        if(checkApuntado.isSelected()){
            taboaActividade.getItems().addAll(super.getFachadaAplicacion().buscarActividadeParticipa(actividade, usuario));
        }else{
            taboaActividade.getItems().addAll(super.getFachadaAplicacion().buscarActividadeNONParticipa(actividade, usuario));
        }

        if (taboaActividade.getItems().size() != 0) {
            taboaActividade.getSelectionModel().selectFirst();
        }
        listenerTabla();
    }

    public void listenerTabla(){
        if(!taboaActividade.getSelectionModel().isEmpty()){
            Actividade actividade=(Actividade)taboaActividade.getSelectionModel().getSelectedItem();
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(actividade.getData().getTime());
            cal.add(Calendar.SECOND, (int)(actividade.getDuracion()*3600));

            boolean estaAcabada=(new Timestamp(System.currentTimeMillis())).after(new Timestamp(cal.getTime().getTime()));

            btnValorar.setDisable(!(estaAcabada && checkApuntado.isSelected()));
            btnDesapuntarse.setDisable(!(!estaAcabada && checkApuntado.isSelected()));
            btnApuntarse.setDisable(checkApuntado.isSelected());

            String infoActividade=String.format(
                    "Nome: %s\nData: %s\nHora: %s\nDuración: %s\nInstalación: %s\nÁrea: %s\nTipo: %s",
                    actividade.getNome(),
                    new SimpleDateFormat("dd/MM/yyyy").format(new Date(actividade.getData().getTime())),
                    new SimpleDateFormat("HH:mm").format(new Date(actividade.getData().getTime())),
                    (actividade.getDuracion()*60)+" minutos",
                    actividade.getArea().getInstalacion().getNome(),
                    actividade.getArea().getNome(),
                    actividade.getTipoActividadenome()
            );
            campoInfo.setText(infoActividade);
        }
    }

    public void listenerCheckBox(){
        actualizarTabla();
    }

    public void btnBuscarAction(ActionEvent actionEvent) {
        actualizarTabla();
    }

    public void onActionLimpar(){
        this.comboInstalacion.getSelectionModel().clearSelection();
        this.comboArea.getSelectionModel().clearSelection();
        this.campoNome.clear();
    }

    public void onActionValorar(){
        this.controllerPrincipal.mostrarPantalla(IdPantalla.VALORARACTIVIDADEPOPUP);
    }

    public void onActionApuntarse(){
        if (!taboaActividade.getSelectionModel().isEmpty()) {
            Actividade actividade = (Actividade) taboaActividade.getSelectionModel().getSelectedItem();
            if (super.getFachadaAplicacion().mostrarConfirmacion("Actividade", "Quereste apuntar a " + actividade.getNome()) == ButtonType.OK) {
                //apuntar
                try {
                    TipoResultados tipoResultados = super.getFachadaAplicacion().apuntarseActividade(actividade, controllerPrincipal.getUsuario());

                    //resultado
                    switch (tipoResultados) {
                        case correcto:
                            super.getFachadaAplicacion().mostrarInformacion("Actividade", "Apuntacheste á actividade " + actividade.getNome());
                            break;
                        case sitIncoherente:
                            super.getFachadaAplicacion().mostrarErro("Actividade", "Non hai prazas dispoñibles. O aforo é máximo.");
                            break;
                        case datoExiste:
                            super.getFachadaAplicacion().mostrarErro("Actividade", "Xa está apuntado nesta actividade, non se pode volver a pauntar.");
                            break;
                    }

                    actualizarTabla();
                } catch (ExcepcionBD e) {
                    getFachadaAplicacion().mostrarErro("Actividade", e.getMessage());
                }
            }
        }
    }

    public void onActionDesapuntarse(){
        if (!taboaActividade.getSelectionModel().isEmpty()) {
            Actividade actividade = (Actividade) taboaActividade.getSelectionModel().getSelectedItem();
            if (super.getFachadaAplicacion().mostrarConfirmacion("Actividade", "Quereste desapuntar da actividade " + actividade.getNome()) == ButtonType.OK) {
                //desapuntrarse
                try {
                    TipoResultados tipoResultados = super.getFachadaAplicacion().borrarseDeActividade(actividade, controllerPrincipal.getUsuario());
                    switch (tipoResultados) {
                        case correcto:
                            super.getFachadaAplicacion().mostrarInformacion("Actividade", "Desapuntacheste da actividade " + actividade.getNome());
                            break;
                        case sitIncoherente:
                            super.getFachadaAplicacion().mostrarErro("Actividade", "Non está apuntado a esta actividade.");
                            break;
                    }

                } catch (ExcepcionBD e) {
                    getFachadaAplicacion().mostrarErro("Actividade", e.getMessage());
                }
            }
        }
    }
}

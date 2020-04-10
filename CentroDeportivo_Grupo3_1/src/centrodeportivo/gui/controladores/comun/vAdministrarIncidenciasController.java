package centrodeportivo.gui.controladores.comun;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.incidencias.Incidencia;
import centrodeportivo.aplicacion.obxectos.incidencias.IncidenciaArea;
import centrodeportivo.aplicacion.obxectos.incidencias.IncidenciaMaterial;
import centrodeportivo.aplicacion.obxectos.tipos.TipoIncidencia;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class vAdministrarIncidenciasController extends AbstractController implements Initializable {

    public TableView listaIncidencias;
    public ComboBox campoTipoIncidencia;
    private vPrincipalController vPrincipal;

    public vAdministrarIncidenciasController(FachadaAplicacion fachadaAplicacion, vPrincipalController vPrincipalController) {
        super(fachadaAplicacion,vPrincipalController);
        this.vPrincipal=super.getvPrincipalController();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        TableColumn<Incidencia,String> numeroColumn = new TableColumn<>("Número");
        numeroColumn.setCellValueFactory(new PropertyValueFactory<>("numero"));
        TableColumn<Incidencia,String> usuarioColumn = new TableColumn<>("Usuario");
        usuarioColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Incidencia, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Incidencia, String> param) {
                return new SimpleObjectProperty<String>(param.getValue().getUsuario().getLogin().toString());
            }
        });

        TableColumn<Incidencia,String> descricionColumn = new TableColumn<>("Descrición");
        descricionColumn.setCellValueFactory(new PropertyValueFactory<>("descricion"));

        TableColumn<Incidencia,String> tipoIncidenciaColumn = new TableColumn<>("Tipo Incidencia");
        tipoIncidenciaColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Incidencia, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Incidencia, String> param) {
                return new SimpleObjectProperty<String>(param.getValue().getTipoIncidencia().toString());
            }
        });

        TableColumn<Incidencia,String> obxecto = new TableColumn<>("WHO");
        obxecto.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Incidencia, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Incidencia, String> param) {
                if(param.getValue().getTipoIncidencia()==TipoIncidencia.Area)return new SimpleObjectProperty<String>(((IncidenciaArea)param.getValue()).getArea().getNome());
                else return new SimpleObjectProperty<String>(((IncidenciaMaterial)param.getValue()).getMaterial().getEstado()); //CAMBIAAAAAAAAAAAAAAAR
            }
        });

        listaIncidencias.getColumns().addAll(tipoIncidenciaColumn,numeroColumn,usuarioColumn,descricionColumn,obxecto);
        listaIncidencias.getItems().addAll(super.getFachadaAplicacion().listarIncidencias());
        campoTipoIncidencia.getItems().addAll(TipoIncidencia.values());
        campoTipoIncidencia.getSelectionModel().selectFirst();
        listaIncidencias.setPlaceholder(new Label("Non se atoparon incidencias"));
    }

    public void buscarIncidencias(){
        listaIncidencias.getItems().removeAll(listaIncidencias.getItems());
        listaIncidencias.getItems().addAll(super.getFachadaAplicacion().listarIncidencias("",TipoIncidencia.values()[campoTipoIncidencia.getSelectionModel().getSelectedIndex()]));
    }



}


package centrodeportivo.gui.controladores.persoal.incidencias;

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

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public class vAdministrarIncidenciasController extends AbstractController implements Initializable {

    public TableView listaIncidencias;
    public ComboBox campoTipoIncidencia;
    public TextField campoBuscar;
    private vPrincipalController vPrincipal;

    public vAdministrarIncidenciasController(FachadaAplicacion fachadaAplicacion, vPrincipalController vPrincipalController) {
        super(fachadaAplicacion,vPrincipalController);
        this.vPrincipal=super.getvPrincipalController();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        TableColumn<Incidencia,String> numeroColumn = new TableColumn<>("Número");
        numeroColumn.setCellValueFactory(new PropertyValueFactory<>("numero"));


        TableColumn<Incidencia,String> descricionColumn = new TableColumn<>("Descrición");
        descricionColumn.setCellValueFactory(new PropertyValueFactory<>("descricion"));

        TableColumn<Incidencia,String> tipoIncidenciaColumn = new TableColumn<>("Tipo Incidencia");
        tipoIncidenciaColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Incidencia, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Incidencia, String> param) {
                return new SimpleObjectProperty<String>(param.getValue().getTipoIncidencia().toString());
            }
        });

        TableColumn<Incidencia,String> obxetoColumn = new TableColumn<>("Material/Area");
        obxetoColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Incidencia, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Incidencia, String> param) {
                if(param.getValue() instanceof IncidenciaArea)
                    return new SimpleObjectProperty<String>(((IncidenciaArea)param.getValue()).getArea().getNome());
                else
                    return new SimpleObjectProperty<String>(String.format("%s %s", ((IncidenciaMaterial)param.getValue()).getMaterial().getTipoNombre(),((IncidenciaMaterial)param.getValue()).getMaterial().getCodMaterial()));
            }
        });

        listaIncidencias.getColumns().addAll(tipoIncidenciaColumn,numeroColumn,obxetoColumn,descricionColumn);
        listaIncidencias.getItems().addAll(super.getFachadaAplicacion().listarIncidencias());
        campoTipoIncidencia.getItems().addAll(TipoIncidencia.values());
        campoTipoIncidencia.getSelectionModel().selectFirst();
        listaIncidencias.setPlaceholder(new Label("Non se atoparon incidencias"));
    }

    public void buscarIncidencias(){
        listaIncidencias.getItems().removeAll(listaIncidencias.getItems());
        listaIncidencias.getItems().addAll(super.getFachadaAplicacion().listarIncidencias(campoBuscar.getText(),TipoIncidencia.values()[campoTipoIncidencia.getSelectionModel().getSelectedIndex()]));
    }



}


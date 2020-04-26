package centrodeportivo.gui.controladores.persoal.tarifas;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.tarifas.Tarifa;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.auxiliar.IdPantalla;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public class vAdministrarTarifasController extends AbstractController implements Initializable {

    /**
     * Atributos do fxml.
     */
    public TableView listaTarifas;

    /**
     * Atributos privados do controlador
     */
    private FachadaAplicacion fachadaAplicacion;
    private vPrincipalController vPrincipal;

    /**
     * @param fachadaAplicacion fachada da aplicación
     * @param vPrincipalController controlador da vista principals
     */
    public vAdministrarTarifasController(FachadaAplicacion fachadaAplicacion, vPrincipalController vPrincipalController) {
        super(fachadaAplicacion,vPrincipalController);
        this.fachadaAplicacion=super.getFachadaAplicacion();
        this.vPrincipal=vPrincipalController;
    }


    /**
     * Método para inicializar a vista.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableColumn<Integer, Tarifa> codTarifaColumn = new TableColumn<>("Codigo");
        codTarifaColumn.setCellValueFactory(new PropertyValueFactory<>("codTarifa"));

        TableColumn<Integer, Tarifa> nomeTarifaColumn = new TableColumn<>("Nome");
        nomeTarifaColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Integer, Tarifa> maxActividadesColumn = new TableColumn<>("Máximo Actividades");
        maxActividadesColumn.setCellValueFactory(new PropertyValueFactory<>("maxActividades"));

        TableColumn<Integer, Tarifa> prezoBaseColumn = new TableColumn<>("Prezo Base");
        prezoBaseColumn.setCellValueFactory(new PropertyValueFactory<>("prezoBase"));

        TableColumn<Integer, Tarifa> prezoExtrasColumn = new TableColumn<>("Prezo Extra");
        prezoExtrasColumn.setCellValueFactory(new PropertyValueFactory<>("prezoExtras"));

        listaTarifas.getColumns().addAll(codTarifaColumn,nomeTarifaColumn,maxActividadesColumn,prezoBaseColumn,prezoExtrasColumn);
        listarTarifas();
        listaTarifas.setPlaceholder(new Label("Non se atoparon tarifas"));
    }

    /**
     * Método para cargar as tarifas.
     */
    private void listarTarifas(){
        listaTarifas.getItems().removeAll(listaTarifas.getItems());
        listaTarifas.getItems().addAll(super.getFachadaAplicacion().listarTarifas());
    }

    /**
     * Método para borrar unha tarifa seleccionada
     */
    public void borrarTarifa(){
        if(!listaTarifas.getSelectionModel().isEmpty()){
            Tarifa tarifa=((Tarifa)listaTarifas.getSelectionModel().getSelectedItem());
            if(fachadaAplicacion.estaEnUsoTarifa(tarifa.getCodTarifa())){
                fachadaAplicacion.mostrarAdvertencia("Tarifa en uso", "Esta tarifa está actualmente en uso, non se pode borrar");
                return;
            }
            if(fachadaAplicacion.mostrarConfirmacion("Borrar tarifa","Desexa borrar a tarifa "+tarifa.getNome() + "?")==ButtonType.OK){
                try {
                    fachadaAplicacion.borrarTarifa(tarifa.getCodTarifa());
                    fachadaAplicacion.mostrarInformacion("Borrar tarifa","A tarifa "+tarifa.getNome()+ " borrouse correctamente.");
                } catch (ExcepcionBD excepcionBD) {
                    super.getFachadaAplicacion().mostrarErro("Tarifas",excepcionBD.getMessage());
                }
            }
            listarTarifas();
        }
    }

    /**
     * Método para modificar unha tarifa seleccionada
     */
    public void modificarTarifa(){
        if(!listaTarifas.getSelectionModel().isEmpty()){
            Tarifa tarifa=((Tarifa)listaTarifas.getSelectionModel().getSelectedItem());
            this.vPrincipal.mostrarMenu(IdPantalla.XESTIONTARIFA);
            ((vXestionTarifaController)this.vPrincipal.getControlador(IdPantalla.XESTIONTARIFA)).setTarifa(tarifa);
        }
    }

}


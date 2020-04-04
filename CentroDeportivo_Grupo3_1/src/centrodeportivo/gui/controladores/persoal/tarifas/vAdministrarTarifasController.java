package centrodeportivo.gui.controladores.persoal.tarifas;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.tarifas.Tarifa;
import centrodeportivo.aplicacion.obxectos.tipos.TipoUsuario;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.principal.IdPantalla;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class vAdministrarTarifasController extends AbstractController implements Initializable {

    public TableView listaTarifas;
    private FachadaAplicacion fachadaAplicacion;
    private vPrincipalController vPrincipal;
    public vAdministrarTarifasController(FachadaAplicacion fachadaAplicacion, vPrincipalController vPrincipal) {
        super(fachadaAplicacion);
        this.fachadaAplicacion=super.getFachadaAplicacion();
        this.vPrincipal=vPrincipal;
    }

    private void listarTarifas(){
        listaTarifas.getItems().removeAll(listaTarifas.getItems());
        listaTarifas.getItems().addAll(super.getFachadaAplicacion().listarTarifas());
    }
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

    public void borrarTarifa(){
        if(!listaTarifas.getSelectionModel().isEmpty()){
            Tarifa tarifa=((Tarifa)listaTarifas.getSelectionModel().getSelectedItem());
            if(fachadaAplicacion.estaEnUsoTarifa(tarifa.getCodTarifa())){
                fachadaAplicacion.mostrarAdvertencia("Tarifa en uso", "Esta tarifa está actualmente en uso, non se pode borrar");
                return;
            }
            if(fachadaAplicacion.mostrarConfirmacion("Borrar tarifa","Desexa borrar a tarifa "+tarifa.getNome() + "?")==ButtonType.OK){
                fachadaAplicacion.borrarTarifa(tarifa.getCodTarifa());
                fachadaAplicacion.mostrarInformacion("Borrar tarifa","A tarifa "+tarifa.getNome()+ " borrouse correctamente.");
            }
            listarTarifas();
        }
    }

    public void modificarTarifa(){
        if(!listaTarifas.getSelectionModel().isEmpty()){
            Tarifa tarifa=((Tarifa)listaTarifas.getSelectionModel().getSelectedItem());
            this.vPrincipal.mostrarMenu(IdPantalla.NOVATARIFA);
            ((vNovaTarifaController)this.vPrincipal.getControlador(IdPantalla.NOVATARIFA)).setTarifa(tarifa);
        }
    }

}


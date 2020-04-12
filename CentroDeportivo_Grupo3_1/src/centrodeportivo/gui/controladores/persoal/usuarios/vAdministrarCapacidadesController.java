package centrodeportivo.gui.controladores.persoal.usuarios;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.actividades.Actividade;
import centrodeportivo.aplicacion.obxectos.actividades.TipoActividade;
import centrodeportivo.aplicacion.obxectos.usuarios.Persoal;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public class vAdministrarCapacidadesController extends AbstractController implements Initializable  {
    public ListView listaDisponibles;
    public ListView listaCapacidades;
    public Label usuarioLabel;
    private ArrayList<TipoActividade> capacidadesDisponibles;
    private ArrayList<TipoActividade> capacidadesActuales;
    private Persoal persoal;

    public vAdministrarCapacidadesController(FachadaAplicacion fachadaAplicacion, centrodeportivo.gui.controladores.principal.vPrincipalController vPrincipalController) {
        super(fachadaAplicacion, vPrincipalController);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listaDisponibles.setCellFactory(new Callback<ListView<TipoActividade>, ListCell<TipoActividade>>() {
            @Override
            public ListCell<TipoActividade> call(ListView<TipoActividade> lv) {
                return new ListCell<TipoActividade>() {
                    @Override
                    public void updateItem(TipoActividade item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null) {
                            setText(null);
                        } else {
                            setText(item.getNome());
                        }
                    }
                };
            }
        });
        listaCapacidades.setCellFactory(new Callback<ListView<TipoActividade>, ListCell<TipoActividade>>() {
            @Override
            public ListCell<TipoActividade> call(ListView<TipoActividade> lv) {
                return new ListCell<TipoActividade>() {
                    @Override
                    public void updateItem(TipoActividade item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null) {
                            setText(null);
                        } else {
                            setText(item.getNome());
                        }
                    }
                };
            }
        });

    }

    public void setUsuario(Usuario selectedItem) {
        usuarioLabel.setText(String.format("Editando capacidades de %s",selectedItem.getLogin()));
        persoal=(Persoal)selectedItem;
        capacidadesActuales=super.getFachadaAplicacion().listarCapacidades(persoal.getLogin());
        capacidadesDisponibles=super.getFachadaAplicacion().listarTipoActividades();
        capacidadesDisponibles.removeAll(capacidadesActuales);

        listaCapacidades.getItems().addAll(capacidadesActuales);
        listaDisponibles.getItems().addAll(capacidadesDisponibles);
    }

    public void engadirCapacidade(){
        TipoActividade actividadeSeleccionada;
        if(!listaDisponibles.getSelectionModel().isEmpty()){
            actividadeSeleccionada= (TipoActividade) listaDisponibles.getSelectionModel().getSelectedItems().get(0);
            listaDisponibles.getItems().remove(actividadeSeleccionada);
            listaCapacidades.getItems().add(actividadeSeleccionada);
            super.getFachadaAplicacion().engadirCapadidade(persoal.getLogin(),actividadeSeleccionada);
        }
    }

    public void eliminarCapacidade(){
        TipoActividade actividadeSeleccionada;
        if(!listaCapacidades.getSelectionModel().isEmpty()){
            actividadeSeleccionada= (TipoActividade) listaCapacidades.getSelectionModel().getSelectedItems().get(0);
            if(super.getFachadaAplicacion().tenClasesPendentes(persoal,actividadeSeleccionada)){
                getFachadaAplicacion().mostrarErro("Non se pode eliminar a capacidade", "Este usuario ainda ten unha clase pendente de esta actividade");
                return;
            }
            listaCapacidades.getItems().remove(actividadeSeleccionada);
            listaDisponibles.getItems().add(actividadeSeleccionada);
            super.getFachadaAplicacion().eliminarCapacidade(persoal.getLogin(),actividadeSeleccionada);
        }
    }

    public void volverMenu(){
        super.getvPrincipalController().volverAtras();
    }

}

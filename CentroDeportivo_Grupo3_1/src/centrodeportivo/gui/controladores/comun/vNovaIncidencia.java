package centrodeportivo.gui.controladores.comun;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.Material;
import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public class vNovaIncidencia extends AbstractController implements Initializable {

    public RadioButton radioArea;
    public RadioButton radioMaterial;
    public TextArea campoDescricion;
    public TableView tablaDatos;
    public ComboBox comboBox;

    private Usuario usuario;
    private ToggleGroup grupoRadios;

    public vNovaIncidencia(FachadaAplicacion fachadaAplicacion, vPrincipalController vPrincipalController) {
        super(fachadaAplicacion, vPrincipalController);
        this.usuario=vPrincipalController.obterUsuarioLogeado();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.grupoRadios=new ToggleGroup();
        this.radioArea.setToggleGroup(grupoRadios);
        this.radioMaterial.setToggleGroup(grupoRadios);
        this.grupoRadios.selectToggle(radioArea);
        this.tablaDatos.setPlaceholder(new Label("Non hai datos dispoñibles"));
        generarTablaAreas();
    }

    public void btnGardarAction(ActionEvent actionEvent) {
    }

    public void listenerRadio(ActionEvent actionEvent){
        if(radioArea.isSelected()){
            generarTablaAreas();
        }else{
            generarTablaMaterial();
        }
    }

    public void listenerCombo(){
        generarTablaMaterial();
    }

    private void generarTablaAreas(){
        this.comboBox.setVisible(false);
        this.tablaDatos.getColumns().removeAll(tablaDatos.getColumns());
        this.tablaDatos.getItems().removeAll(tablaDatos.getItems());
        TableColumn<Area, String> columnaArea = new TableColumn<>("Áreas");
        columnaArea.setCellValueFactory(new PropertyValueFactory<>("nome"));
        this.tablaDatos.getColumns().add(columnaArea);


        HashMap<Area,ArrayList<Material>> areas=super.getFachadaAplicacion().listarAreas();
        this.tablaDatos.getItems().addAll(areas.keySet());
        if(areas.size()>0) tablaDatos.getSelectionModel().selectFirst();
    }

    private void generarTablaMaterial(){
        this.comboBox.setVisible(true);
        this.comboBox.getItems().removeAll(comboBox.getItems());

        this.tablaDatos.getColumns().removeAll(tablaDatos.getColumns());
        this.tablaDatos.getItems().removeAll(tablaDatos.getItems());
        TableColumn<Area, String> columnaArea = new TableColumn<>("Materiais");
        columnaArea.setCellValueFactory(new PropertyValueFactory<>("nome"));
        this.tablaDatos.getColumns().add(columnaArea);

        HashMap<Area,ArrayList<Material>> areas=super.getFachadaAplicacion().listarAreas();
        this.comboBox.getItems().addAll(areas.keySet());

        if(comboBox.getSelectionModel().getSelectedItem()!=null){
            ArrayList<Material> materiales=areas.get((Area)comboBox.getSelectionModel().getSelectedItem());
            if(materiales.size()>0){
                for(Material m:materiales){
                    this.tablaDatos.getItems().add(m);
                }
            }
        }
        if(areas.size()>0) tablaDatos.getSelectionModel().selectFirst();
    }
}

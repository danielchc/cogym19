package centrodeportivo.gui.controladores.comun;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.Material;
import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.aplicacion.obxectos.incidencias.IncidenciaArea;
import centrodeportivo.aplicacion.obxectos.incidencias.IncidenciaMaterial;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.auxiliar.ListenerMaxLogitud;
import centrodeportivo.auxiliar.ValidacionDatos;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.auxiliar.IdPantalla;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public class vNovaIncidencia extends AbstractController implements Initializable {
    public TreeView selectorIncidencia;
    public TextArea infoObxecto;
    public TextArea campoDescricion;
    private Usuario usuario;
    private ArrayList<Area> listaAreas;

    public vNovaIncidencia(FachadaAplicacion fachadaAplicacion, vPrincipalController vPrincipalController) {
        super(fachadaAplicacion, vPrincipalController);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.usuario = super.getvPrincipalController().obterUsuarioLogeado();

        this.listaAreas = super.getFachadaAplicacion().listarAreas();
        this.campoDescricion.textProperty().addListener(new ListenerMaxLogitud(campoDescricion, 500));


        TreeItem rootItem = new TreeItem();
        TreeItem areaActual;
        this.selectorIncidencia.setRoot(rootItem);
        this.selectorIncidencia.setShowRoot(false);
        for (Area area : this.listaAreas) {
            areaActual = new TreeItem(area);
            areaActual.getChildren().add(new TreeItem());
            areaActual.expandedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (!newValue) return;
                    TreeItem t = (TreeItem) ((BooleanProperty) observable).getBean();
                    t.getChildren().removeAll(t.getChildren());
                    area.setMateriais(getFachadaAplicacion().listarMateriais(area));
                    for (Material m : area.getMateriais()) {
                        m.setArea(area);
                        t.getChildren().add(new TreeItem(m));
                    }
                }
            });


            rootItem.getChildren().add(areaActual);
        }


        this.selectorIncidencia.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem> observable, TreeItem oldValue, TreeItem newValue) {

                if (newValue.getValue() instanceof Material) {
                    Material material = (Material) newValue.getValue();
                    infoObxecto.setText(String.format("Tipo de material: %s\nNúmero: %d\nEstado: %s\nÁrea: %s(%s)",
                            material.getTipoNombre(),
                            material.getCodMaterial(),
                            material.getEstado(),
                            material.getArea().getNome(),
                            material.getArea().getInstalacion().getNome()
                    ));
                } else if (newValue.getValue() instanceof Area) {
                    Area area = (Area) newValue.getValue();
                    infoObxecto.setText(String.format("Código: %d\nÁrea: %s\nInstalación: %s\nDirección: %s",
                            area.getCodArea(),
                            area.getNome(),
                            area.getInstalacion().getNome(),
                            area.getInstalacion().getDireccion()
                    ));
                }
            }
        });

        this.selectorIncidencia.setCellFactory(param -> new TreeCell<Object>() {
            @Override
            public void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) setText("");
                else {
                    if (item instanceof Material) {
                        setText(String.format("%s %d", ((Material) item).getTipoNombre(), ((Material) item).getCodMaterial()));
                    } else if (item instanceof Area) {
                        setText(String.format("%s (%s)", ((Area) item).getNome(), ((Area) item).getInstalacion().getNome()));
                    }
                }
                setGraphic(null);
            }

        });
    }

    private void listenerTabla() {

    }

    public void btnGardarAction(ActionEvent actionEvent) {
        if (!this.selectorIncidencia.getSelectionModel().isEmpty() && ValidacionDatos.estanCubertosCampos(campoDescricion)) {
            TreeItem selectedItem = (TreeItem) this.selectorIncidencia.getSelectionModel().getSelectedItem();
            try {
                if (selectedItem.getValue() instanceof Material) {
                    Material material = (Material) selectedItem.getValue();
                    getFachadaAplicacion().insertarIncidencia(new IncidenciaMaterial(
                            super.getvPrincipalController().obterUsuarioLogeado(),
                            this.campoDescricion.getText(),
                            material
                    ));
                } else if (selectedItem.getValue() instanceof Area) {
                    Area area = (Area) selectedItem.getValue();
                    getFachadaAplicacion().insertarIncidencia(new IncidenciaArea(
                            super.getvPrincipalController().obterUsuarioLogeado(),
                            this.campoDescricion.getText(),
                            area
                    ));
                }
            } catch (ExcepcionBD excepcionBD) {
                super.getFachadaAplicacion().mostrarErro("Incidencia", excepcionBD.getMessage());
            }
            getFachadaAplicacion().mostrarInformacion("Incidencia creada", "Creouse a incidencia correctamente.");
            getvPrincipalController().mostrarMenu(IdPantalla.INICIO);
        }
    }
}

package centrodeportivo.gui.controladores.Materiales;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.aplicacion.obxectos.area.Material;
import centrodeportivo.aplicacion.obxectos.area.TipoMaterial;
import centrodeportivo.aplicacion.obxectos.tipos.TipoResultados;
import centrodeportivo.funcionsAux.ValidacionDatos;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.AuxGUI;
import centrodeportivo.gui.controladores.principal.IdPantalla;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class vEditarMaterialController extends AbstractController implements Initializable {

    // Atributos públicos: referentes a ventá correspondente
    public TextField campoCodigo;
    public ComboBox<TipoMaterial> comboTipoMaterial;
    public TextField campoEstadoMaterial;
    public ComboBox<Instalacion> comboInstalacion;
    public DatePicker campoDataCompraMaterial;
    public ComboBox<Area> comboArea;
    public TextField campoPrezoMaterial;
    public Button btnGardarMaterial;
    public Button btnLimparMaterial;
    public Label avisoCampos;

    // Atributos privados: matenmos o controlador da ventá de procedencia e o material a xestionar
    private vPrincipalController controllerPrincipal;
    private Material material;

    // Constructor:
    public vEditarMaterialController(FachadaAplicacion fachadaAplicacion, vPrincipalController controllerPrincipal) {
        super(fachadaAplicacion);
        this.controllerPrincipal = controllerPrincipal;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Para inicializar, comprobaremos se o material non é null:
        if (material != null) {

            // Inicializamos o comboBox dos tipos de materiais:
            comboTipoMaterial.setItems(FXCollections.observableArrayList(getFachadaAplicacion().buscarTipoMaterial(null)));
            comboTipoMaterial.setDisable(true);

            // Inicializamos o comboBox das instalacions
            comboInstalacion.setItems(FXCollections.observableArrayList(getFachadaAplicacion().buscarInstalacions(null)));

            // Cargamos as areas en funcion da instalacion seleccionada
            comboInstalacion.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == null) {  // No caso de que non haxa ningunha instalaccion seleccionada
                    // Eliminamos as areas que poidesen estar cargadas:
                    comboArea.getItems().clear();
                    // Deshabilitamos o comboBox:
                    comboArea.setDisable(true);
                } else {  // En calquer outro caso
                    // Cargamos as areas que se atopen na nova instalación seleccionada
                    comboArea.getItems().setAll(FXCollections.observableArrayList(getFachadaAplicacion().listarAreasActivas(newValue)));
                    // Habilitamos de novo a seleccion
                    comboArea.setDisable(false);
                    // Eliminamos o posible valor que poidese estar seleccionado de antes
                    comboArea.setValue(null);
                }
            });

            // Inicializamos o datePicker para que so se poidan escoller datas previas a data actual
            campoDataCompraMaterial.setDayCellFactory(picker -> new DateCell() {
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    // Tomamos dereferencia a data actual
                    LocalDate today = LocalDate.now();
                    // Deshabilitanse as datas dos cadros valeiros ou de data superior a actual
                    setDisable(empty || date.compareTo(today) > 0);
                }
            });

            //Engadimos un listener no campo do prezo para controlar os valores introducidos:
            campoPrezoMaterial.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    //Se o novo valor que se quere introducir non concorda co formato de tres dixitos na parte enteira
                    //e dous na decimal, entón poñemos o valor antigo:
                    if (!newValue.matches("\\d{0,3}([\\.]\\d{0,2})?")) {
                        campoPrezoMaterial.setText(oldValue);
                    }
                }
            });

            // Actualizamos os campos ca información do material pasado
            actualizarCamposMaterial();

        } else {

            // En calquer outro caso, amosamos unha mensaxe co erro e volvemos a pantalla anterior:
            this.getFachadaAplicacion().mostrarErro("Administración de materiais",
                    "Non se detectou ningún material... Saíndo.");
            this.controllerPrincipal.mostrarPantalla(IdPantalla.ADMINISTRARMATERIAIS);
        }


    }

    /**
     * Setter do material a editar
     *
     * @param material A instalación que se vai a querer asociar á pantalla.
     */
    public void setMaterial(Material material) {
        this.material = material;
    }


    /**
     * Método que realiza unha consulta sobre o material e actualiza os campos
     */
    private void actualizarCamposMaterial() {
        // Consultamos o material:
        material = getFachadaAplicacion().consultarMaterial(material);
        if (material != null) {
            // Se o material non e null, cubrimos os campos
            comboTipoMaterial.setValue(material.getTipoMaterial());
            comboInstalacion.setValue(material.getArea().getInstalacion());
            comboArea.setValue(material.getArea());
            campoCodigo.setText(String.valueOf(material.getCodMaterial()));
            campoEstadoMaterial.setText(material.getEstado());
            if (material.getDataCompra() != null) {
                campoDataCompraMaterial.setValue(material.getDataCompra().toLocalDate());
            }
            if (material.getPrezoCompra() != 0.0f) {
                campoPrezoMaterial.setText(String.valueOf(material.getPrezoCompra()));
            }
        } else {
            // Noutro caso amosase un erro e sairíamos tamén:
            this.getFachadaAplicacion().mostrarErro("Administración de Material",
                    "O material que se pediu xestionar non está na base de datos. Saíndo...");
            this.controllerPrincipal.mostrarPantalla(IdPantalla.ADMINISTRARMATERIAIS);
        }
    }

    /**
     * Acción efectuada o premer o boton de borrar un material.
     *
     * @param actionEvent O evento que tivo lugar.
     */
    public void btnBorrarAction(ActionEvent actionEvent) {
        // Cando se pide borrar, primeiro solicitase a confirmación por parte do usuario:
        if (super.getFachadaAplicacion().mostrarConfirmacion("Administración de Materiais",
                "Desexa eliminar o material seleccionado?") == ButtonType.OK) {
            // Intentamos levar a cabo o borrado de dito material:
            try {
                TipoResultados res = super.getFachadaAplicacion().borrarMaterial(material);
                // En función do resultado, actuamos:
                if (res == TipoResultados.correcto) {
                    // En caso de borrado correcto, confírmase o resultado:
                    super.getFachadaAplicacion().mostrarInformacion("Administración de Materiais",
                            "Material borrado correctamente.");
                    // Unha vez rematado o borrado, voltamos a pantalla anterior:
                    controllerPrincipal.mostrarPantalla(IdPantalla.ADMINISTRARMATERIAIS);
                }
            } catch (ExcepcionBD e) {
                // No caso de ter un erro, amosamos unha mensaxe por pantalla co mesmo:
                super.getFachadaAplicacion().mostrarErro("Administración de Materiais", e.getMessage());
            }
        }
    }

    /**
     * Acción efectuada o premer o boton de borrar un material.
     *
     * @param actionEvent O evento que tivo lugar.
     */
    public void btnRestaurarAction(ActionEvent actionEvent) {
        // Cando se pide borrar, primeiro solicitase a confirmación por parte do usuario:
      actualizarCamposMaterial();
    }


    /**
     * Acción efectuada ao premer o botón de modificar un material
     *
     * @param actionEvent O evento que tivo lugar.
     */
    public void btnModificarAction(ActionEvent actionEvent) {
        // Primeiro imos comprobar que os campos non están vacíos:
        if (!ValidacionDatos.estanCubertosCampos(campoEstadoMaterial) || comboTipoMaterial.getValue() == null ||
                comboInstalacion.getValue() == null || comboArea.getValue() == null) {
            // Se algún campo obligatorio non esta cuberto, amosamos un mensaxe:
            AuxGUI.amosarCampos(avisoCampos);
            return;
        }

        // Creamos o material que daremos de alta:
        Material material;
        // Creamos a fecha que lle pasaremos:
        Date fechaCompra = null;
        // Compobamos que o campo non este vacio:
        if (campoDataCompraMaterial.getValue() != null) {
            // Se non esta valeiro, asignamosllo:
            fechaCompra = Date.valueOf(campoDataCompraMaterial.getValue());
        }
        // Comprobamos que o campo prezo non esta valeiro
        if (!campoPrezoMaterial.getText().isEmpty()) {
            // Se non esta valeiro creamos o material co prezo:
            material = new Material(Integer.parseInt(campoCodigo.getText()), comboTipoMaterial.getValue(), comboArea.getValue(), comboInstalacion.getValue(), campoEstadoMaterial.getText(), fechaCompra, Float.parseFloat(campoPrezoMaterial.getText()));
        } else {
            // Se esta valeiro, creamos o material sen o prezo:
            material = new Material(Integer.parseInt(campoCodigo.getText()), comboTipoMaterial.getValue(), comboArea.getValue(), comboInstalacion.getValue(), campoEstadoMaterial.getText(), fechaCompra);
        }
        // Accedemos á base de datos:
        // Tentaremos realizar o acceso correctamente, xestionando as excepcións:
        try {
            TipoResultados res = super.getFachadaAplicacion().modificarMaterial(material);
            // En función do resultado procedemos:
            switch (res) {
                case datoNonExiste:
                    // Se non existise ningún material con esas condicións porque se borrou mentres o modificabamos
                    super.getFachadaAplicacion().mostrarErro("Administración de Material",
                            "Non existe este material! Comproba que non foi borrado...");
                    // Volvemos a pantalla anterior
                    this.controllerPrincipal.mostrarPantalla(IdPantalla.ADMINISTRARMATERIAIS);
                    break;
                case correcto:
                    // Se rematou correctamente, mostramos unha mensaxe de confirmación:
                    super.getFachadaAplicacion().mostrarInformacion("Administración de Materiais",
                            "Datos do material do tipo " + material.getTipoMaterial().getNome() + " modificados correctamente.");
                    break;
            }
        } catch (ExcepcionBD e) {
            // Se hai un erro na base de datos, amósase a mensaxe, que é creada na nosa excepción con getMessage():
            super.getFachadaAplicacion().mostrarErro("Administración de Instalacións", e.getMessage());
        }
        // En calquera caso, tentamos actualizar os campos amosados:
        actualizarCamposMaterial();
    }

    /**
     * Acción efectuada ao premer o botón volver.
     *
     * @param actionEvent Evento que tivo lugar.
     */
    public void btnVolverAction(ActionEvent actionEvent) {
        // Volvemos a pantalla de administrar materiais:
        this.controllerPrincipal.mostrarPantalla(IdPantalla.ADMINISTRARMATERIAIS);
    }

}

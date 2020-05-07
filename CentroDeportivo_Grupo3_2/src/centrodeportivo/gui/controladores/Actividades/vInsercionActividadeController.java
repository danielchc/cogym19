package centrodeportivo.gui.controladores.Actividades;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.actividades.Actividade;
import centrodeportivo.aplicacion.obxectos.actividades.Curso;
import centrodeportivo.aplicacion.obxectos.actividades.TipoActividade;
import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.aplicacion.obxectos.tipos.TipoResultados;
import centrodeportivo.aplicacion.obxectos.usuarios.Persoal;
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
import java.time.LocalDate;
import java.util.ResourceBundle;

public class vInsercionActividadeController extends AbstractController implements Initializable {


    public ComboBox comboTipoactividade;
    public TextField campoNome;
    public ComboBox comboInstalacions;
    public ComboBox comboArea;
    public ComboBox comboProfesor;
    public DatePicker campoData;
    public TextField campoHoraInicio;
    public TextField campoHoraFin;
    public Label avisoCampos;
    public Button btnVolver;
    public Button btnGardar;

    private Curso curso;
    private Actividade actividadeModificar;
    private vPrincipalController controllerPrincipal;

    public vInsercionActividadeController(FachadaAplicacion fachadaAplicacion, vPrincipalController controllerPrincipal) {
        super(fachadaAplicacion);
        this.controllerPrincipal = controllerPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.curso=null;
        this.actividadeModificar=null;

        //combo tipos
        this.comboTipoactividade.getItems().addAll(super.getFachadaAplicacion().buscarTiposActividades(null));
        if(!this.comboTipoactividade.getItems().isEmpty()) this.comboTipoactividade.getSelectionModel().selectFirst();

        //combo instalacions
        this.comboInstalacions.getItems().addAll(super.getFachadaAplicacion().buscarInstalacions(null));
        if(!this.comboInstalacions.getItems().isEmpty()) this.comboInstalacions.getSelectionModel().selectFirst();


        this.comboInstalacions.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                Instalacion instalacion=(Instalacion)observableValue.getValue();
                comboArea.getItems().addAll(getFachadaAplicacion().buscarArea(instalacion,null));
                if(!comboArea.getItems().isEmpty()) comboArea.getSelectionModel().selectFirst();
            }
        });

        this.comboTipoactividade.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                TipoActividade tipoActividade=(TipoActividade) observableValue.getValue();
                //comboProfesor.getItems().addAll(getFachadaAplicacion());
                //if(!comboProfesor.getItems().isEmpty()) comboProfesor.getSelectionModel().selectFirst();
            }
        });


        this.campoHoraInicio.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String valorAnterior, String valorNovo) {
                if(valorNovo==null || valorNovo.isEmpty()) return;
                if (!valorNovo.matches("\\d{0,2}?:\\d{0,2}")) {
                    campoHoraInicio.setText(valorAnterior);
                }
            }
        });

        this.campoHoraFin.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String valorAnterior, String valorNovo) {
                if(valorNovo==null || valorNovo.isEmpty()) return;
                if (!valorNovo.matches("\\d{0,2}?:\\d{0,2}")) {
                    campoHoraFin.setText(valorAnterior);
                }
            }
        });
    }

    private boolean camposCorrectos(){
        if(campoData.getValue()==null) {
            avisoCampos.setText("Data incorrecta.");
            return false;
        }

        return true;
    }

    public void btnVolverAction(ActionEvent actionEvent) {
    }

    public void btnGardarAction(ActionEvent actionEvent) {
        if(!ValidacionDatos.estanCubertosCampos(campoNome,campoHoraInicio,campoHoraFin)){
            avisoCampos.setText("Algún campo sen cubrir.");
            return;
        }
        if(!camposCorrectos()) return;
    }


    public void cargarCurso(Curso curso){
        this.curso=curso;
    }

    public void cargarActividade(Actividade actividade){
        this.actividadeModificar=actividade;

        this.comboTipoactividade.getSelectionModel().select(actividade.getTipoActividade());
        this.campoNome.setText(actividade.getNome());
        this.comboInstalacions.getSelectionModel().select(actividade.getArea().getInstalacion());
        this.comboArea.getSelectionModel().select(actividade.getArea());
        this.comboProfesor.getSelectionModel().select(actividade.getProfesor());
    }
}

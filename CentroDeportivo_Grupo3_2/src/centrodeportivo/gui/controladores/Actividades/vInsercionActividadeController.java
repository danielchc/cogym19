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
import javafx.util.converter.DateTimeStringConverter;

import java.net.URL;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.ResourceBundle;

public class vInsercionActividadeController extends AbstractController implements Initializable {


    public ComboBox<TipoActividade> comboTipoactividade = new ComboBox<>();
    public TextField campoNome;
    public ComboBox<Instalacion> comboInstalacions = new ComboBox<>();
    public ComboBox<Area> comboArea = new ComboBox<>();
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
                comboProfesor.getItems().addAll(getFachadaAplicacion().buscarProfesores(tipoActividade));
                if(!comboProfesor.getItems().isEmpty()) comboProfesor.getSelectionModel().selectFirst();
            }
        });


        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        try {
            this.campoHoraInicio.setTextFormatter(new TextFormatter<>(new DateTimeStringConverter(format), format.parse("00:00")));
            this.campoHoraFin.setTextFormatter(new TextFormatter<>(new DateTimeStringConverter(format), format.parse("00:00")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
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

        int horasToSegInici=Integer.parseInt(campoHoraInicio.getText().split(":")[0])*3600;
        int minutosToSegInici=Integer.parseInt(campoHoraInicio.getText().split(":")[1])*60;
        int horasToSegFin=Integer.parseInt(campoHoraFin.getText().split(":")[0])*3600;
        int minutosToSegFin=Integer.parseInt(campoHoraFin.getText().split(":")[1])*60;
        int duracion=(horasToSegFin+minutosToSegFin)-(horasToSegInici+minutosToSegInici);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(Timestamp.valueOf(campoData.getValue().atStartOfDay()).getTime());
        cal.add(Calendar.SECOND, horasToSegInici+minutosToSegInici);
        Timestamp data=new Timestamp(cal.getTime().getTime());

        Actividade actividade=new Actividade(
                data,
                campoNome.getText(),
                duracion/3600f,
                comboArea.getSelectionModel().getSelectedItem(),
                comboTipoactividade.getSelectionModel().getSelectedItem(),
                this.curso,
                (Persoal)comboProfesor.getSelectionModel().getSelectedItem()
        );


        if(actividadeModificar==null){
            //crear activida
        }else{
            //modificala
        }
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

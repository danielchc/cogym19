package centrodeportivo.gui.controladores.Actividades;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.Mensaxe;
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
import centrodeportivo.gui.controladores.Cursos.vXestionCursoController;
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
import java.sql.Date;
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

        //Por defecto habilítanse todos os campos:
        AuxGUI.habilitarCampos(comboTipoactividade, campoNome, comboInstalacions,comboArea,
                comboProfesor, campoHoraInicio, campoHoraFin, btnGardar);

        //combo tipos
        this.comboTipoactividade.getItems().addAll(super.getFachadaAplicacion().buscarTiposActividades(null));

        //combo instalacions
        this.comboInstalacions.getItems().addAll(super.getFachadaAplicacion().buscarInstalacions(null));


        this.comboInstalacions.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                Instalacion instalacion=(Instalacion)observableValue.getValue();
                comboArea.setItems(FXCollections.observableArrayList(getFachadaAplicacion().buscarArea(instalacion,null)));
            }
        });

        this.comboTipoactividade.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                TipoActividade tipoActividade=(TipoActividade) observableValue.getValue();
                comboProfesor.setItems(FXCollections.observableArrayList(getFachadaAplicacion().buscarProfesores(tipoActividade)));
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

        if (comboInstalacions.getValue()==null)
        {
            avisoCampos.setText("Instalacion non selecionada.");
            return false;
        }

        if (comboArea.getValue()==null)
        {
            avisoCampos.setText("Area non selecionada.");
            return false;
        }

        if (comboProfesor.getValue()==null)
        {
            avisoCampos.setText("Profesor non selecionado.");
            return false;
        }

        if (comboTipoactividade.getValue()==null)
        {
            avisoCampos.setText("Tipo de Actividade non selecionado.");
            return false;
        }

        if (campoData.getValue().isBefore(LocalDate.now()))
        {
            avisoCampos.setText("Data incorrecta. ");
            return false;
        }
        return true;
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

        if(duracion<=0){
            avisoCampos.setText("Duración invalida.");
            return;
        }

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

        TipoResultados res;

        if(actividadeModificar==null) {
            //crear actividade
            try {
                res = super.getFachadaAplicacion().EngadirActiviade(actividade);
                switch (res) {
                    case correcto:
                        if(curso == null) {
                            if(super.getFachadaAplicacion().mostrarConfirmacion("Actividade gardada",
                                    "Actividade '" + actividade.getNome() + "' gardada correctamente. Queres" +
                                            " avisar aos socios do centro da creación?") == ButtonType.OK){
                                //Entón crearemos unha mensaxe deste usuario para todos os socios:
                                Mensaxe mensaxe = new Mensaxe(controllerPrincipal.getUsuario(),
                                        "Prezado socio\nEstá dispoñible xa a nova actividade '" +
                                        actividade.getNome() + "'. A que esperas para apuntarte?");

                                //Procedemos ao envío da mensaxe:
                                super.getFachadaAplicacion().enviarAvisoSocios(mensaxe);
                                //Se se chega aqui é porque se realizou o envío da mensaxe:
                                super.getFachadaAplicacion().mostrarInformacion("Administración de actividades",
                                        "Mensaxe enviada a todos os socios correctamente");
                            }

                        } else {
                            //Se é a actividade dun curso, simplemente se amosará unha confirmación do resultado:
                            super.getFachadaAplicacion().mostrarInformacion("Actividade gardada",
                                    "Actividade '" + actividade.getNome() + "' gardada correctamente no curso '"
                                        + curso.getNome() + "'.");
                        }
                        //Cando se garda a actividade, pódese volver:
                        accionsVolver();
                        break;
                    case datoExiste:
                        super.getFachadaAplicacion().mostrarErro("Actividade NON gardada",
                                "Actividade " + actividade.getNome() + " non se puido gardar, dado que hai incompatibilidades " +
                                        "cos horarios doutras actividades.");
                        break;
                }
            } catch(ExcepcionBD e) {
                getFachadaAplicacion().mostrarErro("Administración de actividades",
                        e.getMessage());
            }
        }else{
            //modificar actividade
            try{
                res = super.getFachadaAplicacion().modificarActividade(actividadeModificar, actividade);
                switch (res) {
                    case correcto: super.getFachadaAplicacion().mostrarInformacion("Actividade modificada",
                            "Actividade " + actividade.getNome() + " modificada correctamente.");
                        break;
                    case datoExiste:super.getFachadaAplicacion().mostrarErro("Actividade NON modificada",
                            "Actividade " + actividade.getNome() + " non se puido modificar, dado que hai incompatibilidades" +
                                    " cos horarios doutras actividades.");
                        break;
                }
            } catch (ExcepcionBD e){
                getFachadaAplicacion().mostrarErro("Administración de actividades",
                        e.getMessage());
            }
        }

    }

    public void btnVolverAction(ActionEvent actionEvent) {
        accionsVolver();
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

        this.campoHoraInicio.setText(
                new SimpleDateFormat("HH:mm").format(new Date(actividade.getData().getTime()))
        );

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(actividade.getData().getTime());
        cal.add(Calendar.SECOND, (int)(actividade.getDuracion()*3600));
        Timestamp data=new Timestamp(cal.getTime().getTime());

        this.campoHoraFin.setText(
                new SimpleDateFormat("HH:mm").format(new Date(data.getTime()))
        );

        campoData.setValue(actividade.getData().toLocalDateTime().toLocalDate());

        //Se a actividade xa comezou/se levou a cabo, impediremos que se modifiquen os seus campos:
        if(actividade.getData().before(new Date(System.currentTimeMillis()))){
            AuxGUI.inhabilitarCampos(comboTipoactividade, campoNome, comboInstalacions,comboArea,
                    comboProfesor, campoHoraInicio, campoHoraFin, campoData, btnGardar);
        }
    }

    private void accionsVolver(){
        if (curso != null) {
            this.controllerPrincipal.mostrarPantalla(IdPantalla.XESTIONCURSO);
            ((vXestionCursoController) this.controllerPrincipal.getControlador(IdPantalla.XESTIONCURSO)).volverPantallaActividades(curso);
        } else {
            //Se o curso fose null, entón podemos volver á pantalla de administración de actividades (se a actividade se está a modificar) ou
            //á de inicio (noutro caso).
            if(actividadeModificar != null){
                this.controllerPrincipal.mostrarPantalla(IdPantalla.ADMINACTIVIDADE);
            } else {
                this.controllerPrincipal.mostrarPantalla(IdPantalla.INICIO);
            }
        }
    }
}

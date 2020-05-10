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
import javafx.util.converter.DateTimeStringConverter;

import java.net.URL;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.sql.Date;
import java.util.ResourceBundle;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Víctor Barreiro
 * <p>
 * Clase que funciona como controlador da ventá de inserción dunha nova actividade.
 */
public class vInsercionActividadeController extends AbstractController implements Initializable {

    /**
     * Atributos públicos: son as compoñentes da ventá:
     */
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
    public Button btnRestaurar;

    /**
     * Atributos privados: son cuestións que resultan necesarias para as xestións que se van a realizar:
     */
    private Curso curso;
    private Actividade actividadeModificar;
    private vPrincipalController controllerPrincipal;

    /**
     * Constructor do controlador da pantalla de inserción dunha actividade.
     *
     * @param fachadaAplicacion   A referencia á fachada da parte de aplicación.
     * @param controllerPrincipal A referencia ao controlador da ventá principal.
     */
    public vInsercionActividadeController(FachadaAplicacion fachadaAplicacion, vPrincipalController controllerPrincipal) {
        super(fachadaAplicacion);
        this.controllerPrincipal = controllerPrincipal;
    }

    /**
     * Método que nos permite inicializar a ventá de inserción dunha actividaade.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Establecemos curso e actividade a null
        this.curso = null;
        this.actividadeModificar = null;
        //Por defecto habilítanse todos os campos:
        AuxGUI.habilitarCampos(comboTipoactividade, campoNome, comboInstalacions, comboArea,
                comboProfesor, campoHoraInicio, campoHoraFin, btnGardar);

        //combo tipos
        this.comboTipoactividade.getItems().addAll(super.getFachadaAplicacion().buscarTiposActividades(null));

        //combo instalacions
        this.comboInstalacions.getItems().addAll(super.getFachadaAplicacion().buscarInstalacions(null));

        //Engadimos un listener sobre o combo de instalacións, para que se vaian modificando as áreas amosadas no combo
        //de áreas:
        this.comboInstalacions.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                Instalacion instalacion = (Instalacion) observableValue.getValue();
                comboArea.setItems(FXCollections.observableArrayList(getFachadaAplicacion().buscarArea(instalacion, null)));
            }
        });

        //Engadimos un listener sobre o combo de tipos de actividades, para que se vaian modificando os membros do persoal
        //amosados no combo de profesores:
        this.comboTipoactividade.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                TipoActividade tipoActividade = (TipoActividade) observableValue.getValue();
                comboProfesor.setItems(FXCollections.observableArrayList(getFachadaAplicacion().buscarProfesores(tipoActividade)));
            }
        });

        //Inicializamos un listener para as horas, para que teñan o formato axeitado e controlalo:
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        try {
            this.campoHoraInicio.setTextFormatter(new TextFormatter<>(new DateTimeStringConverter(format), format.parse("00:00")));
            this.campoHoraFin.setTextFormatter(new TextFormatter<>(new DateTimeStringConverter(format), format.parse("00:00")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que nos permite validar se os campos introducidos son correctos.
     *
     * @return True se os campos son correctos, False en caso contrario.
     */
    private boolean camposCorrectos() {
        //Campo da data: debe estar cuberto.
        if (campoData.getValue() == null) {
            avisoCampos.setText("Data incorrecta.");
            return false;
        }

        //Combo das instalacións: debe ter unha selección feita:
        if (comboInstalacions.getValue() == null) {
            avisoCampos.setText("Instalacion non selecionada.");
            return false;
        }

        //Combo das áreas: debe ter unha selección feita.
        if (comboArea.getValue() == null) {
            avisoCampos.setText("Area non selecionada.");
            return false;
        }

        //Combo do profesor: debe ter unha selección feita:
        if (comboProfesor.getValue() == null) {
            avisoCampos.setText("Profesor non selecionado.");
            return false;
        }

        //Combo do tipo de actividade: debe ter unha selección feita:
        if (comboTipoactividade.getValue() == null) {
            avisoCampos.setText("Tipo de Actividade non selecionado.");
            return false;
        }

        //Campo da data: debe ser posterior a hoxe (en dous días, para que non se inserte de súpeto):
        if (campoData.getValue().isBefore(LocalDate.now().plusDays(2))) {
            avisoCampos.setText("Data incorrecta. ");
            return false;
        }

        //Campo do nome: debe ter a lonxitude axeitada.
        if (campoNome.getText().length() > 50) {
            avisoCampos.setText("Lonxitude do nome incorrecta!");
            return false;
        }

        //Devolverase true se se pasaron todas as condicións:
        return true;
    }

    /**
     * Método que representa as accións levadas a cabo ao premer o botón de gardado dunh actividade.
     *
     * @param actionEvent A acción que tivo lugar.
     */
    public void btnGardarAction(ActionEvent actionEvent) {

        //Se hai campos de texto sen cubrir, avísase e sáese:
        if (!ValidacionDatos.estanCubertosCampos(campoNome, campoHoraInicio, campoHoraFin)) {
            avisoCampos.setText("Algún campo sen cubrir.");
            return;
        }

        //Se os campos non foron correctos, sairase.
        if (!camposCorrectos()) return;

        //Neste punto calculamos a duración a partir dos campos recollidos (que xa sabemos que van a ter unha hora):
        int horasToSegInici = Integer.parseInt(campoHoraInicio.getText().split(":")[0]) * 3600;
        int minutosToSegInici = Integer.parseInt(campoHoraInicio.getText().split(":")[1]) * 60;
        int horasToSegFin = Integer.parseInt(campoHoraFin.getText().split(":")[0]) * 3600;
        int minutosToSegFin = Integer.parseInt(campoHoraFin.getText().split(":")[1]) * 60;
        int duracion = (horasToSegFin + minutosToSegFin) - (horasToSegInici + minutosToSegInici);

        //Se a duración fose negativa, avisamos:
        if (duracion <= 0) {
            avisoCampos.setText("Duración invalida.");
            return;
        }

        //Controlamos que a hora de inicio non sexa antes das 6 da mañá.
        if (horasToSegInici + minutosToSegInici < 6 * 3600) {
            avisoCampos.setText("Hora de inicio debe ser maior que 06:00.");
            return;
        }

        //Controlamos que a hora de finalización non sexa despois das 11 da noite:
        if (horasToSegFin + minutosToSegFin > 23 * 3600) {
            avisoCampos.setText("Hora de fin debe ser menor que 23:00.");
            return;
        }

        //Creamos o timestamp coa data a partir do calendario:
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(Timestamp.valueOf(campoData.getValue().atStartOfDay()).getTime());
        cal.add(Calendar.SECOND, horasToSegInici + minutosToSegInici);
        Timestamp data = new Timestamp(cal.getTime().getTime());

        //Créase a actividade a insertar na base de datos:
        Actividade actividade = new Actividade(
                data,
                campoNome.getText(),
                duracion / 3600f,
                comboArea.getSelectionModel().getSelectedItem(),
                comboTipoactividade.getSelectionModel().getSelectedItem(),
                this.curso,
                (Persoal) comboProfesor.getSelectionModel().getSelectedItem()
        );

        TipoResultados res;

        //Pode ser que se queira facer unha inserción ou unha modificación, o que dependerá do campo da actividade
        //a modificar.
        if (actividadeModificar == null) {
            //crear unha actividade
            try {
                res = super.getFachadaAplicacion().EngadirActiviade(actividade);
                switch (res) {
                    case correcto:
                        //Comprobamos se a actividade é dun curso ou non: se non o é daremos opción ao envío dunha mensaxe.
                        //(cando se engaden actividades ao curso, este non pode ter participantes).
                        if (curso == null) {
                            //En caso de que fose ben: avisamos do resultado e solicitamos se se quere avisar aos socios.
                            if (super.getFachadaAplicacion().mostrarConfirmacion("Actividade gardada",
                                    "Actividade '" + actividade.getNome() + "' gardada correctamente. Queres" +
                                            " avisar aos socios do centro da creación?") == ButtonType.OK) {

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
                        //Cando se garda a actividade, pódese saír desta ventá:
                        accionsVolver();
                        break;
                    case foraTempo:
                        //Incompatibilidades horarias: avísase para buscar outra hora.
                        super.getFachadaAplicacion().mostrarErro("Actividade NON gardada",
                                "Actividade " + actividade.getNome() + " non se puido gardar, dado que hai incompatibilidades " +
                                        "cos horarios doutras actividades.");
                        break;
                    case sitIncoherente:
                        //En caso de que o profesor seleccionado non sexa persoal activo, avísase:
                        super.getFachadaAplicacion().mostrarErro("Actividade NON gardada",
                                "Actividade " + actividade.getNome() + " non se puido gardar, " +
                                        "o persoal non é un Profesor en activo.");
                        break;
                }
            } catch (ExcepcionBD e) {
                //En caso de excepción, amósase o erro producido:
                getFachadaAplicacion().mostrarErro("Administración de actividades",
                        e.getMessage());
            }
        } else {
            //modificar a actividade:
            try {
                //Avaliamos a modificación dunha activiadde.
                res = super.getFachadaAplicacion().modificarActividade(actividadeModificar, actividade);
                switch (res) {
                    case correcto:
                        //Sexa a actividade ou non dun curso, o que se pedirá e se se quere notificar aos participantes
                        //da actividade:
                        super.getFachadaAplicacion().mostrarInformacion("Actividade modificada",
                                "Actividade " + actividade.getNome() + " modificada correctamente.");

                        Mensaxe mensaxe = new Mensaxe(controllerPrincipal.getUsuario(),
                                "Prezado socio\nA actividade '" + actividadeModificar.getNome() + "' sufriu" +
                                        " certos cambios. Desculpe as molestias.");
                        if (curso != null) {
                            //No caso do curso a mensaxe será algo diferente:
                            mensaxe.setContido("Prezado socio\nA actividade '" + actividadeModificar.getNome() + "'" +
                                    " do curso '" + curso.getNome() + "' sufriu certos cambios. Desculpe as molestias");
                        }
                        //Escollido isto enviarase a mensaxe aos socios da actividade:
                        super.getFachadaAplicacion().enviarAvisoSociosAct(mensaxe, actividadeModificar);

                        //Recargamos a actividade
                        this.cargarActividade(actividade);
                        break;
                    case datoExiste:
                        super.getFachadaAplicacion().mostrarErro("Actividade NON modificada",
                                "Actividade " + actividade.getNome() + " non se puido modificar, dado que hai incompatibilidades" +
                                        " cos horarios doutras actividades.");
                        break;
                    case sitIncoherente:
                        //En caso de que o profesor seleccionado non sexa persoal activo, avísase:
                        super.getFachadaAplicacion().mostrarErro("Actividade NON gardada",
                                "Actividade " + actividade.getNome() + " non se puido gardar, " +
                                        "o persoal non é un Profesor en activo.");
                        break;
                }
            } catch (ExcepcionBD e) {
                //En caso de recibir unha excepción da base de datos, avísase dela:
                getFachadaAplicacion().mostrarErro("Administración de actividades",
                        e.getMessage());
            }
        }

    }

    /**
     * Método que representa as accións levadas a cabo ao premer o botón de retorno.
     *
     * @param actionEvent A acción que tivo lugar.
     */
    public void btnVolverAction(ActionEvent actionEvent) {
        //O comportamento témolo recollido noutro método, porque o precisaremos dende varios lugares:
        accionsVolver();
    }

    /**
     * Método que representa as accións realizadas ao premer o botón de restauración dos campos:
     */
    public void btnRestaurarCampos() {
        //Se a actividade a modificar non é nula (estamos modificando unha actividade) complétanse os campos cos seus datos.
        if (actividadeModificar != null) {
            completarCampos(actividadeModificar);
        } else {
            //Se é nula, vaciaremos todos os campos:
            AuxGUI.vaciarCamposTexto(campoNome);
            campoData.setValue(null);
            campoHoraFin.setText("00:00");
            campoHoraInicio.setText("00:00");
        }
    }

    /**
     * Método que nos permite cargar un curso, cando se xestiona unha actividade asociada a un curso:
     *
     * @param curso O curso a cargar.
     */
    public void cargarCurso(Curso curso) {
        this.curso = curso;
    }

    /**
     * Método que nos permite completar os campos da pantalla dada unha actividade.
     *
     * @param actividade A actividade a expoñer nos campos.
     */
    private void completarCampos(Actividade actividade) {
        //Seleccionamos nos combos e nos campos os valores correspondentes:
        this.comboTipoactividade.getSelectionModel().select(actividade.getTipoActividade());
        this.campoNome.setText(actividade.getNome());
        this.comboInstalacions.getSelectionModel().select(actividade.getArea().getInstalacion());
        this.comboArea.getSelectionModel().select(actividade.getArea());
        this.comboProfesor.getSelectionModel().select(actividade.getProfesor());

        //Poñemos a hora de inicio no formato adecuado:
        this.campoHoraInicio.setText(
                new SimpleDateFormat("HH:mm").format(new Date(actividade.getData().getTime()))
        );

        //Calculamos a data para a hora de fin:
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(actividade.getData().getTime());
        cal.add(Calendar.SECOND, (int) (actividade.getDuracion() * 3600));
        Timestamp data = new Timestamp(cal.getTime().getTime());

        //Poñemos a hora de fin correspondente:
        this.campoHoraFin.setText(
                new SimpleDateFormat("HH:mm").format(new Date(data.getTime()))
        );

        //Poñemos a data:
        campoData.setValue(actividade.getData().toLocalDateTime().toLocalDate());

        //Se a actividade xa comezou/se levou a cabo, impediremos que se modifiquen os seus campos:
        if (actividade.getData().before(new Date(System.currentTimeMillis()))) {
            AuxGUI.inhabilitarCampos(comboTipoactividade, campoNome, comboInstalacions, comboArea,
                    comboProfesor, campoHoraInicio, campoHoraFin, campoData, btnGardar, btnRestaurar);
        }
    }

    /**
     * Método que nos permite cargar a información dunha actividade determinada:
     *
     * @param actividade A actividade a amosar.
     */
    public void cargarActividade(Actividade actividade) {
        //Establecemos o atributo:
        this.actividadeModificar = actividade;
        //Completamos todos os campos da pantalla para que sexan coherentes coa actividade:
        completarCampos(actividade);
    }

    /**
     * Método que representa as accións levadas a cabo cando se quere volver a unha pantalla anterior:
     */
    private void accionsVolver() {
        //Se o curso non é null, amosarase a pantalla de xestión do curso asociando o curso correspondente de novo..
        if (curso != null) {
            this.controllerPrincipal.mostrarPantalla(IdPantalla.XESTIONCURSO);
            //Volveremos, exactamente, á pestana de xestión das actividades do curso.
            ((vXestionCursoController) this.controllerPrincipal.getControlador(IdPantalla.XESTIONCURSO)).volverPantallaActividades(curso);
        } else {
            //Se o curso fose null, entón podemos volver á pantalla de administración de actividades (se a actividade se está a modificar) ou
            //á de inicio (noutro caso).
            if (actividadeModificar != null) {
                this.controllerPrincipal.mostrarPantalla(IdPantalla.ADMINACTIVIDADE);
            } else {
                this.controllerPrincipal.mostrarPantalla(IdPantalla.INICIO);
            }
        }
    }
}

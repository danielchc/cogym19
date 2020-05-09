package centrodeportivo.gui.controladores.Cursos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.Mensaxe;
import centrodeportivo.aplicacion.obxectos.actividades.Actividade;
import centrodeportivo.aplicacion.obxectos.actividades.Curso;
import centrodeportivo.aplicacion.obxectos.tipos.TipoResultados;
import centrodeportivo.aplicacion.obxectos.usuarios.Persoal;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.funcionsAux.ValidacionDatos;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.Actividades.vInsercionActividadeController;
import centrodeportivo.gui.controladores.AuxGUI;
import centrodeportivo.gui.controladores.principal.IdPantalla;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Víctor Barreiro
 * Clase que serve de controlador da pantalla de xestión dun curso determinado, unha das pantallas máis completas que
 * desenvolvemos na nosa parte de aplicación.
 * Dous usos:
 * - Editar datos dun curso e amosar diferente información do mesmo (cando xa está creado).
 * - Introducir os datos dun novo curso.
 */
public class vXestionCursoController extends AbstractController implements Initializable {

    /**
     * Atributos públicos: correspóndense con diferentes compoñentes desta ventá. Hai unha gran cantidade deles neste
     * caso:
     */
    public TextField campoCodigo;
    public TextField campoNome;
    public TextArea campoDescricion;
    public TextField campoPrezo;
    public Label tagObrigatorios;
    public Button btnActivar;
    public Button btnGardar;
    public TableView taboaActividades;
    public Button btnEngadirActividade;
    public Button btnCancelarActividade;
    public Button btnXestionarSeleccion;
    public TableView taboaUsuarios;
    public Button btnCancelar;
    public Button btnRestaurar;
    public Button btnXerarInforme;
    public VBox vBoxBotonInforme;
    public VBox vBoxDetalleInforme;
    public TextField campoDuracion;
    public TextField campoDataInicio;
    public TextField campoNumActividades;
    public TextField campoDataFin;
    public TextField campoNumProfesores;
    public TableView taboaActividadesVal;
    public Label tagVTM;
    public TableView taboaProfesores;
    public Button btnVolver;
    public Button btnRefrescar;
    public Tab tabActividades;
    public TabPane tabPane;


    /**
     * Atributos privados: coma sempre, temos por un lado a referencia ao controlador da ventá principal.
     * Por outro lado, tense unha referencia a un curso (pode ser null ou non, dependerá da ventá usada.
     */
    private vPrincipalController controllerPrincipal;
    private Curso curso;

    /**
     * Constructor da pantalla de xestión dun curso
     *
     * @param fachadaAplicacion   A referencia á fachada da parte de aplicación.
     * @param controllerPrincipal A referencia ao controlador da ventá principal.
     */
    public vXestionCursoController(FachadaAplicacion fachadaAplicacion, vPrincipalController controllerPrincipal) {
        //Chamamos ao constructor da clase pai.
        super(fachadaAplicacion);
        //Asignamos o atributo do controlador da ventá principal:
        this.controllerPrincipal = controllerPrincipal;
    }

    /**
     * Método executado para inicializar a pantalla.
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Hai que levar a cabo varias tarefas, e pode haber dúas situacións:
        //Situación 1 - Non hai curso asociado á clase (NULL).
        //Situación 2 - Hai curso asociado á clase.
        //De todos os xeitos, na inicialización non contemplaremos iso, senón que
        //o cambio farémolo no setter do curso.

        //Inicializamos as táboas
        //Táboa de actividades:
        //En primeiro lugar poremos a data nun formato "amigable" para nós (día/mes/ano):
        TableColumn<Actividade, String> dataActividadeColumn = new TableColumn<>("Data");
        dataActividadeColumn.setCellValueFactory(c -> new SimpleStringProperty(
                new SimpleDateFormat("dd/MM/yyyy").format(c.getValue().getData().getTime())
        ));

        //En segundo lugar, poremos a hora na que ten lugar a actividade. Volvemos a recurrir ao simplestringformat
        //e ao timestamp recollido da base de datos:
        TableColumn<Actividade, String> horaActividadeColumn = new TableColumn<>("Hora");
        horaActividadeColumn.setCellValueFactory(c -> new SimpleStringProperty(
                new Time(c.getValue().getData().getTime()).toString()
        ));

        //Personalizamos tamén a columna na que se representa a área:
        TableColumn<Actividade, String> areaColumn = new TableColumn<>("Area");
        areaColumn.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getArea().getNome() + ", inst. " + c.getValue().getArea().getInstalacion().getCodInstalacion()
        ));

        //A duración representámola partindo as horas e os minutos:
        TableColumn<Actividade, String> duracionColumn = new TableColumn<>("Duración");
        duracionColumn.setCellValueFactory(c -> new SimpleStringProperty(
                //Basicamente collo a parte enteira e logo a decimal multiplicada por 60:
                c.getValue().getDuracion().intValue() + "h, " +
                        (int) ((c.getValue().getDuracion().floatValue() - c.getValue().getDuracion().intValue()) * 60) + "m"
        ));

        TableColumn<Actividade, String> profesorColumn = new TableColumn<>("Profesor");
        profesorColumn.setCellValueFactory(new PropertyValueFactory<>("profesor"));

        //Engadimos todas as columnas á táboa.
        taboaActividades.getColumns().addAll(dataActividadeColumn, horaActividadeColumn, areaColumn, duracionColumn, profesorColumn);
        //Facemos isto para controlar o tamaño das columnas.
        taboaActividades.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        taboaActividades.getSelectionModel().selectFirst();

        //Táboa de socios:
        //Empezamos polo login do usuario:
        TableColumn<Usuario, String> loginColumn = new TableColumn<>("Login");
        loginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));

        //O nome do usuario:
        TableColumn<Usuario, String> nomeColumn = new TableColumn<>("Nome");
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));

        //A idade:
        TableColumn<Usuario, Integer> idadeColumn = new TableColumn<>("Idade");
        idadeColumn.setCellValueFactory(new PropertyValueFactory<>("idade"));

        //As dificultades do usuario:
        TableColumn<Usuario, String> dificultadesColumn = new TableColumn<>("Dificultades");
        dificultadesColumn.setCellValueFactory(new PropertyValueFactory<>("dificultades"));

        //O número de teléfono
        TableColumn<Usuario, String> numTelefonoColumn = new TableColumn<>("Número de Teléfono");
        numTelefonoColumn.setCellValueFactory(new PropertyValueFactory<>("numTelefono"));

        //O correo electrónico:
        TableColumn<Usuario, String> correoColumn = new TableColumn<>("Correo electrónico");
        correoColumn.setCellValueFactory(new PropertyValueFactory<>("correoElectronico"));

        taboaUsuarios.getColumns().addAll(loginColumn, nomeColumn, idadeColumn, dificultadesColumn,
                numTelefonoColumn, correoColumn);
        //Facemos isto para controlar o tamaño das columnas.
        taboaUsuarios.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //Táboa de actividades e valoracións asociadas: será semellante á outra de actividades:
        //Comezamos amosando a data no formato dia/mes/ano
        TableColumn<Actividade, String> dataActColumn = new TableColumn<>("Data");
        dataActColumn.setCellValueFactory(c -> new SimpleStringProperty(
                new SimpleDateFormat("dd/MM/yyyy").format(c.getValue().getData().getTime())
        ));

        //Formateamos ahora a hora na que ten lugar a actividade:
        TableColumn<Actividade, String> horaActColumn = new TableColumn<>("Hora");
        horaActColumn.setCellValueFactory(c -> new SimpleStringProperty(
                new Time(c.getValue().getData().getTime()).toString()
        ));

        //A área na que ten lugar:
        TableColumn<Actividade, String> areaActColumn = new TableColumn<>("Area");
        areaActColumn.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getArea().getNome() + ", inst. " + c.getValue().getArea().getInstalacion().getCodInstalacion()
        ));

        TableColumn<Actividade, String> profActColumn = new TableColumn<>("Profesor");
        profActColumn.setCellValueFactory(new PropertyValueFactory<>("profesor"));

        //Engadimos unha columna diferente, a das valoracións:
        TableColumn<Actividade, Float> valoracionColumn = new TableColumn<>("Valoración");
        valoracionColumn.setCellValueFactory(new PropertyValueFactory<>("valMedia"));

        //Metemos todas as columnas na táboa:
        taboaActividadesVal.getColumns().addAll(dataActColumn, horaActColumn, areaActColumn, profActColumn, valoracionColumn);
        //Con isto controlamos o tamaño das columnas:
        taboaActividadesVal.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //Táboa de valoracións aos profesores:
        //Hai columnas similares coas de usuario:

        TableColumn<String, Usuario> nomeProfColumn = new TableColumn<>("Nome");
        nomeProfColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<String, Usuario> loginProfColumn = new TableColumn<>("Login");
        loginProfColumn.setCellValueFactory(new PropertyValueFactory<>("login"));

        TableColumn<Float, Persoal> valoracionPersoalColumn = new TableColumn<>("Valoración");
        valoracionPersoalColumn.setCellValueFactory(new PropertyValueFactory<>("valoracion"));
        //Engadimos columnas:
        taboaProfesores.getColumns().addAll(nomeProfColumn, loginProfColumn, valoracionPersoalColumn);
        //Controlamos o tamaño das columnas:
        taboaProfesores.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //En primeira instancia, inhabilitamos TODOS os botóns salvo o de gardar curso.
        AuxGUI.inhabilitarCampos(btnActivar, btnCancelarActividade, btnEngadirActividade, btnXestionarSeleccion,
                btnCancelar, btnRefrescar);
        //Deshabilitamos os vbox e o botón de retorno:
        AuxGUI.ocultarCampos(vBoxBotonInforme, vBoxDetalleInforme, btnVolver);

        //Engadimos un listener no campo do prezo para controlar os valores introducidos:
        campoPrezo.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //Se o novo valor que se quere introducir non concorda co formato de tres dixitos na parte enteira
                //e dous na decimal, entón poñemos o valor antigo:
                if (!newValue.matches("\\d{0,3}([\\.]\\d{0,2})?")) {
                    campoPrezo.setText(oldValue);
                }
            }
        });
    }

    /**
     * Método que se executa tras amosar a pantalla, para reiniciar aqueles atributos que sexa preciso.
     */
    @Override
    public void reiniciarForm() {
        //Reestablecemos o campo do curso de cara á apertura de novo da ventá.
        //Deste xeito, para indicar que se vai configurar un curso haberá que usar o setter que temos máis abaixo
        //despois de abrir a ventá.
        this.curso = null;
    }

    /**
     * Método que representa as accións realizadas ao premer o botón de activación dun curso:
     *
     * @param actionEvent A acción que tivo lugar.
     */
    public void btnActivarAction(ActionEvent actionEvent) {
        //Neste caso o que se fará é intentar activar o curso:
        if (curso != null && curso.getCodCurso() != null) {
            //Evidentemente, só poderemos activar o curso se non está aberto:
            if (!curso.isAberto()) {
                //Tentamos facer a activación:
                try {
                    //Gardamos o resultado:
                    TipoResultados res = getFachadaAplicacion().activarCurso(curso);
                    switch (res) {
                        case sitIncoherente:
                            //No caso de devolver este valor, indicará que o curso non estaba preparado para ser activado:
                            getFachadaAplicacion().mostrarErro("Administración de Cursos",
                                    "O curso non está todavía preparado para ser activado. Lembra, debe de ter" +
                                            " como mínimo dúas actividades e non poden quedar menos de dous días para o seu comezo!");
                            //Non faríamos ningunha modificación.
                            break;
                        case correcto:
                            //Unha vez activado, confirmarémolo, quitaremos o botón de activar curso e deixaremos continuar:
                            //O que faremos será pedir confirmación ao usuario de se quere mandarlle unha mensaxe aos
                            //socios:
                            if (getFachadaAplicacion().mostrarConfirmacion("Administración de Cursos",
                                    "O curso activouse. ¿Queres mandar unha mensaxe avisando aos socios do centro?")
                                    == ButtonType.OK) {

                                //Se o quere, elaboraremos unha mensaxe:
                                Mensaxe mensaxe = new Mensaxe(controllerPrincipal.getUsuario(),
                                        "Prezado socio,\nAbertas as inscricións ao curso '" + curso.getNome() + "'. A que esperas " +
                                                "para apuntarte? As prazas voan!!!");

                                //Esa mensaxe mandarémola a todos os socios:
                                getFachadaAplicacion().enviarAvisoSocios(mensaxe);

                                //Se se chega aquí sen lanzar unha excepción, será porque se logrou avisar a todos
                                //os socios. Avísase:
                                getFachadaAplicacion().mostrarInformacion("Administración de Cursos",
                                        "Avisado a TODOS os socios");
                            }
                            //Desaactivamos o botón de activación do curso:
                            AuxGUI.inhabilitarCampos(btnActivar);
                            break;
                    }
                } catch (ExcepcionBD excepcionBD) {
                    //En caso de recoller unha excepción proveniente da base de datos,
                    this.getFachadaAplicacion().mostrarErro("Administración de Cursos", excepcionBD.getMessage());
                }
            } else {
                //Se o curso xa estivese aberto, avisaríase cun erro que non se pode volver a activar.
                getFachadaAplicacion().mostrarErro("Administración de Cursos",
                        "Non podes activar un curso xa aberto!");
            }
        } else {
            //Se non se cumpre esa primeira condición de curso ou código de curso nulo avísase:
            getFachadaAplicacion().mostrarErro("Administración de Cursos",
                    "Non se pode activar o curso se inda non se insertou!");
        }
    }

    /**
     * Método que representa as accións realizadas ao premer o botón de gardado dun curso.
     *
     * @param actionEvent A acción que tivo lugar.
     */
    public void btnGardarAction(ActionEvent actionEvent) {
        //No caso de gardar, hai que verificar que o nome e o ID estén cubertos.
        if (!ValidacionDatos.estanCubertosCampos(campoNome, campoPrezo)) {
            //Se non se cubriron, habilítase a etiqueta que indica campos obrigatorios:
            AuxGUI.amosarCampos(tagObrigatorios);
            return; //Non seguimos adiante.
        } else {
            //Se non, ocultamos esa etiqueta dado que non queremos que apareza
            AuxGUI.ocultarCampos(tagObrigatorios);
        }

        //Chegados aquí, intentamos facer a actualización. Podería ser inserción de datos ou modificación, dependendo
        //da situación.
        if (curso == null) {
            try {
                //Rexistramos un novo curso na base de datos:
                Curso c = new Curso(campoNome.getText(), campoDescricion.getText(), Float.parseFloat(campoPrezo.getText()));
                TipoResultados res = getFachadaAplicacion().rexistrarCurso(c);
                //En función do resultado, amosaremos un erro ou continuaremos:
                switch (res) {
                    case datoExiste:
                        //Se xa existe un curso co mesmo nome, amosamos erro:
                        this.getFachadaAplicacion().mostrarErro("Administración de Cursos",
                                "Xa existe un curso co nome " + c.getNome().toLowerCase());
                        break;
                    case correcto:
                        //En caso de que se teña resultado correcto, habilitaranse outras modificacións da pantalla e avisarase
                        //da correcta inserción:
                        this.getFachadaAplicacion().mostrarInformacion("Administración de Cursos",
                                "Curso " + c.getNome() + " insertado correctamente. " +
                                        "O seu ID é " + c.getCodCurso() + ".");
                        //Asignamos o curso e así poderemos xa facer outro tipo de edicións:
                        this.setCurso(c);
                        break;
                }
                //Poderase capturar unha excepción proveniente da base de datos:
            } catch (ExcepcionBD excepcionBD) {
                //Nese caso, amosamos un mensaxe xenerado na excepción:
                this.getFachadaAplicacion().mostrarErro("Administración de Cursos", excepcionBD.getMessage());
            }
        } else {
            //Se xa existe o curso, entón o que queremos será modificar os seus datos na base de datos:
            try {
                //Creamos un novo curso para poder modificar: inda non sabemos se os cambios se poden efectuar e polo
                //tanto actualizar o curso.
                //Pasamos tamén a data de inicio para poder comprobar que se poidan modificar os datos:
                Curso cursoModif = new Curso(curso.getCodCurso(), campoNome.getText(),
                        campoDescricion.getText(), Float.parseFloat(campoPrezo.getText()),
                        curso.getDataInicio());
                //Chamamos ao método que realiza a modificación:
                TipoResultados res = getFachadaAplicacion().modificarCurso(cursoModif);
                //En función do resultado, diferentes alternativas:
                switch (res) {
                    case datoExiste:
                        //Se xa existía un curso co nome que se intentou cambiar, avísase.
                        this.getFachadaAplicacion().mostrarErro("Administración de Cursos",
                                "Xa existe un curso co nome " + curso.getNome().toLowerCase());
                        break;
                    case foraTempo:
                        //Se non se cumpren os tempos para modificar, é dicir, o curso xa comezou, non deixaremos modificar
                        //a información e restauraremos os campos, dado que non se poden cambiar:
                        this.getFachadaAplicacion().mostrarErro("Administración de Cursos",
                                "Non se poden actualizar os datos, pois o curso xa comezou.");
                        completarCamposPrincipais();
                        break;
                    case correcto:
                        //Se se puideron facer os cambios, entón os campos mantéñense e actualizamos o curso_
                        //Buscamos de novo o curso para así refrescar:
                        Curso actu = getFachadaAplicacion().recuperarDatosCurso(curso);
                        //Actualizamos toda a información:
                        setCurso(actu);

                        //Solicitaremos ao usuario se quere que lle mandemos unha mensaxe aos membros do curso:
                        if (getFachadaAplicacion().mostrarConfirmacion("Administración de Cursos",
                                "Datos do curso " + curso.getCodCurso() + " modificados correctamente. Queres avisar " +
                                        "aos socios participantes no curso de que houbo cambios?") == ButtonType.OK) {
                            //Elaboramos unha mensaxe:
                            Mensaxe mensaxe = new Mensaxe(controllerPrincipal.getUsuario(),
                                    "Estimado socio,\nO curso '" + curso.getNome() + "' sufriu certas modificacións. Vaia a " +
                                            "consultalo para obter maior información.");

                            //Enviámola:
                            getFachadaAplicacion().enviarAvisoSociosCurso(mensaxe, curso);

                            //Se se chega ata aquí, é que se rematou correctamente:
                            getFachadaAplicacion().mostrarInformacion("Adminsitración de cursos",
                                    "Avisados todos os membros do curso correctamente.");
                        }
                        break;
                }
            } catch (ExcepcionBD excepcionBD) {
                //Mostramos o erro asociado:
                this.getFachadaAplicacion().mostrarErro("Administración de Cursos", excepcionBD.getMessage());
            }
        }
    }

    /**
     * Método que representa as accións levadas a cabo ao premer o botón de engadido dunha actividade.
     *
     * @param actionEvent A acción que tivo lugar.
     */
    public void btnEngadirActividadeAction(ActionEvent actionEvent) {
        //Para poder engadir unha actividade, o curso non pode estar activo:
        if (!curso.isAberto()) {
            //Cargamos a pantalla correspondente á inserción dunha actividade:
            this.controllerPrincipal.mostrarPantalla(IdPantalla.INSERCIONACTIVIDADE);
            //Cargamos na pantalla o noso curso:
            ((vInsercionActividadeController) this.controllerPrincipal.getControlador(IdPantalla.INSERCIONACTIVIDADE)).cargarCurso(curso);
        } else {
            getFachadaAplicacion().mostrarErro("Administración de Cursos",
                    "Ollo! Non se poden engadir actividades a un curso XA ABERTO!");
        }
    }

    /**
     * Método que representa as accións levadas a cabo ao premer o botón de cancelación dunha actividade.
     *
     * @param actionEvent A acción que tivo lugar.
     */
    public void btnCancelarActividadeAction(ActionEvent actionEvent) {
        //Somentes se pode borrar unha actividade se o curso inda non rematou:
        if (curso.getDataFin() != null && curso.getDataFin().after(new Date(System.currentTimeMillis()))) {
            //Tomamos a selección da táboa (se é posíbel):
            if (!taboaActividades.getSelectionModel().isEmpty()) {
                //Pedimos primeiramente confirmación
                if(getFachadaAplicacion().mostrarConfirmacion("Administración de cursos",
                        "Está completamente seguro de que desexa borrar a actividade seleccionada?") == ButtonType.OK) {
                    Actividade actividade = ((Actividade) taboaActividades.getSelectionModel().getSelectedItem());
                    //Elaboramos a mensaxe que se enviará automáticamente aos socios:
                    Mensaxe mensaxe = new Mensaxe(controllerPrincipal.getUsuario(),
                            "Prezado socio,\nBorrouse a actividade '" + actividade.getNome() + "', " +
                                    "do curso '" + curso.getNome() + "', planificada orixinalmente para o " +
                                    new SimpleDateFormat("dd/MM/yyyy").format(actividade.getData().getTime()) +
                                    ".\nDesculpe as molestias e un saúdo.");
                    //Entón tentaremos o borrado:
                    try {
                        TipoResultados res = getFachadaAplicacion().borrarActividade(actividade, mensaxe);
                        switch (res) {
                            case correcto:
                                //Imprimimos unha mensaxe de confirmación:
                                String mConf = "Borrada a actividade planificada para o " +
                                        new SimpleDateFormat("dd/MM/yyyy").format(actividade.getData().getTime()) + ".";
                                //Se o curso está aberto, terá sentido que se mostre que se avisa aos socios do borrado.
                                //No caso de non estar aberto o curso, xa é imposible que haxa socios (inda que o envío se
                                //faga no mesmo método non se faría nada nese caso).
                                if (curso.isAberto()) {
                                    mConf += " Avisados a todos os socios apuntados.";
                                }

                                getFachadaAplicacion().mostrarInformacion("Administración de cursos", mConf);
                                break;
                            case sitIncoherente:
                                //Amosamos un erro personalizado:
                                getFachadaAplicacion().mostrarErro("Administración de cursos",
                                        "Non se pode borrar esta actividade, dado que xa foi realizada.");
                                break;
                        }
                        //en calquera caso, recuperaranse os datos do curso:
                        this.setCurso(getFachadaAplicacion().recuperarDatosCurso(curso));
                    } catch (ExcepcionBD e) {
                        //Se se producise unha excepción amosaríase o erro asociado:
                        getFachadaAplicacion().mostrarErro("Administración de cursos",
                                e.getMessage());
                    }
                }
            } else {
                getFachadaAplicacion().mostrarErro("Administración de cursos",
                        "Debes seleccionar antes unha actividade!");
            }
        } else {
            getFachadaAplicacion().mostrarErro("Administración de cursos",
                    "Só se pode borrar un curso se inda non rematou!");
        }
    }

    /**
     * Método que representa as accións levadas a cabo ao premer o botón de xestión dunha selección.
     *
     * @param actionEvent
     */
    public void btnXestionarSeleccionAction(ActionEvent actionEvent) {
        //Se hai unha selección feita na táboa de actividades é a que se escollerá para amosar a súa información na nova ventá:
        if (!taboaActividades.getSelectionModel().isEmpty()) {
            //Creamos a nova actividade a ser xestionada:
            Actividade actividade = ((Actividade) taboaActividades.getSelectionModel().getSelectedItem());
            //Amosamos a pantalla de datos dunha actividade:
            this.controllerPrincipal.mostrarPantalla(IdPantalla.INSERCIONACTIVIDADE);
            //Como imos a modificala, asociamos curso e actividade:
            ((vInsercionActividadeController) this.controllerPrincipal.getControlador(IdPantalla.INSERCIONACTIVIDADE)).cargarCurso(curso);
            ((vInsercionActividadeController) this.controllerPrincipal.getControlador(IdPantalla.INSERCIONACTIVIDADE)).cargarActividade(actividade);
        }
    }

    /**
     * Método que representa as accións levadas a cabo ao premer o botón de cancelar.
     *
     * @param actionEvent A acción que tivo lugar
     */
    public void btnCancelarAction(ActionEvent actionEvent) {
        //Tamén poderemos cancelar un curso, sempre e cando fora rexistrado:
        //(En teoría non debería darse o caso, pero facemos igualmente a comprobación):
        if (curso != null) {
            //Se chegamos aquí, entón o que faremos será:
            //1->Pedir confirmación
            //2->Intentar levar a cabo o borrado.

            if (super.getFachadaAplicacion().mostrarConfirmacion("Administración de Cursos",
                    "Desexa cancelar o curso?") == ButtonType.OK) {
                try {
                    //Neste caso o envío da mensaxe vai ser directo. Queremos que cando se cancele un curso se avise.
                    Mensaxe mensaxe = new Mensaxe(controllerPrincipal.getUsuario(),
                            "Estimado socio,\nO curso '" + curso.getNome() + "' foi cancelado. Desculpe as molestias");
                    //Procédese a intentar a cancelación e o aviso simultáneos:
                    TipoResultados res = super.getFachadaAplicacion().cancelarCurso(curso, mensaxe);
                    //En función do resultado, operamos:
                    switch (res) {
                        case incoherenciaBorrado:
                            super.getFachadaAplicacion().mostrarErro("Administración de Cursos",
                                    "Non se puido cancelar o curso, dado que xa comezou e ten participantes inscritos.");
                            //Só neste caso nos mantemos nesta pantalla. Non temos porque saír.
                            break;
                        case correcto:
                            super.getFachadaAplicacion().mostrarInformacion("Administración de Cursos",
                                    "O curso foi borrado. Avisouse automáticamente a todos os usuarios.");
                            //Directamente neste caso o que se fai é saír desta ventá:
                            this.controllerPrincipal.mostrarPantalla(IdPantalla.INICIO);
                            break;
                    }
                } catch (ExcepcionBD excepcionBD) {
                    //En caso de excepción temos un problema:
                    //Podería ter sucedido que o curso fose borrado polo medio, así que o que cremos que é máis lóxico
                    //é que se volva directamente á ventá de inicio se hai calquera erro na base de datos.
                    super.getFachadaAplicacion().mostrarErro("Administración de Cursos",
                            excepcionBD.getMessage());
                    this.controllerPrincipal.mostrarPantalla(IdPantalla.INICIO);
                }
            }
        } else {
            this.getFachadaAplicacion().mostrarErro("Administración de Cursos",
                    "Este curso non foi rexistrado de momento!");
        }
    }

    /**
     * Método que representa as accións levadas a cabo ao premer o botón de limpado de campos:
     *
     * @param actionEvent A acción que tivo lugar
     */
    public void btnRestaurarAction(ActionEvent actionEvent) {
        //O que faremos será vaciar automáticamente todos os campos:
        //Se temos un curso rexistrado xa, o que faremos será poñer os campos que lle corresponde:
        if (curso != null && curso.getCodCurso() != 0) {
            completarCamposPrincipais();
        } else {
            //Se non, pasamos a vacialos:
            AuxGUI.vaciarCamposTexto(campoNome, campoPrezo, campoDescricion);
        }
        //En ambos casos, ocultamos a etiqueta de campos obrigatorios:
        AuxGUI.ocultarCampos(tagObrigatorios);
    }

    /**
     * Método que representa as accións levadas a cabo ao premer o botón de xeración do informe.
     *
     * @param actionEvent A acción que tivo lugar.
     */
    public void btnXerarInformeAction(ActionEvent actionEvent) {
        //O que hai que facer primeiro é comprobar se se está en condicións de recuperar os datos do informe, para o que
        //haberá que saber se o curso xa rematou.
        if (curso != null && curso.getDataFin() != null &&
                curso.getDataFin().before(new Date(System.currentTimeMillis()))) {
            montarInforme();
        } else {
            //En caso de que non se cumpra a condición, avísase:
            getFachadaAplicacion().mostrarErro("Administración de cursos",
                    "Para poder elaborar un informe do curso, este debe ter rematado!");
        }
    }

    /**
     * Acción que ten lugar ao premer o botón de regreso á pantalla de administración.
     *
     * @param actionEvent A acción que tivo lugar
     */
    public void btnVolverAction(ActionEvent actionEvent) {
        //Saímos á ventá de administración de cursos:
        controllerPrincipal.mostrarPantalla(IdPantalla.ADMINISTRARCURSOS);
    }

    /**
     * Acción que ten lugar ao premer o botón para refrescar a información:
     *
     * @param actionEvent A acción que tivo lugar.
     */
    public void btnRefrescarAction(ActionEvent actionEvent) {
        //Inda que esté o botón inhabilitado nese caso, comprobamos que o curso non sexa null:
        if (curso != null) {
            //Agora pode haber dúas situacións, que o Vbox do informe esté visible ou non:
            if (vBoxDetalleInforme.isVisible()) {
                //Se se pode ver, buscaremos o curso pero co método do informe:
                montarInforme();
            } else {
                //Noutro caso buscamos o curso de novo (normal):
                this.setCurso(getFachadaAplicacion().recuperarDatosCurso(curso));
            }
        }
    }

    /**
     * Setter do curso, co que prepararemos a pantalla para a configuración do curso pasado.
     *
     * @param curso O curso do que se vai amosar a información.
     */
    public void setCurso(Curso curso) {
        //Asignamos o curso, comprobando antes que non veña a null:
        if (curso != null) {
            this.curso = curso;
            //Teremos que encher os campos co que corresponde do curso que está apuntado, e encher as táboas:
            campoCodigo.setText(curso.getCodCurso().toString());
            completarCamposPrincipais();
            //Enchemos a táboa de actividades:
            actualizarTaboaActividades(curso.getActividades());
            //Enchemos a táboa de participantes:
            actualizarTaboaUsuarios(curso.getParticipantes());
            if (curso.isAberto()) {
                //Se o curso xa está aberto non damos opción a abrilo, nin a engadir novas actividades:
                AuxGUI.inhabilitarCampos(btnActivar, btnEngadirActividade);
            } else {
                //Senon si que deixaremos:
                AuxGUI.habilitarCampos(btnActivar, btnEngadirActividade);
            }
            //Activamos botóns actividades, cancelación e xeración de informe
            AuxGUI.habilitarCampos(btnCancelar, btnRefrescar, btnXestionarSeleccion);
            AuxGUI.amosarCampos(btnVolver);
            //Se o curso está rematado, entón daremos opción a amosar o botón do informe:
            if (curso.getDataFin() != null && curso.getDataFin().before(new Date(System.currentTimeMillis()))) {
                AuxGUI.amosarCampos(vBoxBotonInforme);
                //Inhabilitaranse tamén todos os botóns relativos ás actividades (menos o de xestionar):
                AuxGUI.inhabilitarCampos(btnCancelarActividade);
            }
        } else {
            this.getFachadaAplicacion().mostrarErro("Administración de Cursos",
                    "Erro na busca, perdeuse o curso. Saíndo á pantalla principal...");
            //Volveríamos, en caso de que o curso fose null, á pantalla principal:
            controllerPrincipal.mostrarPantalla(IdPantalla.INICIO);
        }
    }

    /**
     * Método que nos permite colocar na ventá correspondente todos os campos do informe do curso:
     */
    public void montarInforme() {
        //Iremos a recuperar a información do curso:
        curso = getFachadaAplicacion().informeCurso(curso);
        //Comprobamos que se devolvese correctamente o curso:
        if (curso != null) {
            //Neste caso actualizaremos seguro aqueles campos que se amosan sempre nesta pantalla:
            //Os campos principais:
            completarCamposPrincipais();
            //Actualizamos as táboas:
            //Enchemos a táboa de actividades:
            actualizarTaboaActividades(curso.getActividades());
            //Enchemos a táboa de participantes:
            actualizarTaboaUsuarios(curso.getParticipantes());
            //Comprobamos tamén que o curso rematara:
            if (curso.getDataFin().before(new Date(System.currentTimeMillis()))) {
                //Nesa situación, enchemos os campos do informe:
                campoDuracion.setText(curso.getDuracion().toString());
                //As datas, coma sempre, amosámolas nun formato amigable:
                campoDataInicio.setText(new SimpleDateFormat("dd/MM/yyyy").format(curso.getDataInicio().getTime()));
                campoDataFin.setText(new SimpleDateFormat("dd/MM/yyyy").format(curso.getDataFin().getTime()));
                //Número de profesores e de actividades:
                campoNumProfesores.setText(curso.getNumProfesores().toString());
                campoNumActividades.setText(curso.getNumActividades().toString());
                //A valoración media:
                tagVTM.setText(curso.getValMedia().toString());
                //En función do valor que teña, pintarémola dunha cor ou da outra:
                if (curso.getValMedia().compareTo((float) 2.5) > 0) {
                    tagVTM.setTextFill(Paint.valueOf("Green"));
                } else {
                    tagVTM.setTextFill(Paint.valueOf("Red"));
                }
                //Enchemos as novas táboas, vaciándoas previamente:
                actualizarTaboaActividadesVal(curso.getActividades());
                actualizarTaboaProfesores(curso.getProfesores());
                //Cambiamos a VBox visible:
                vBoxDetalleInforme.setVisible(true);
                vBoxBotonInforme.setVisible(false);
            } else {
                getFachadaAplicacion().mostrarErro("Administración de Cursos",
                        "Non se pode amosar o informe do curso, dado que inda non rematou.");
            }
        } else {
            //En caso de perderse a información do curso, avísase:
            getFachadaAplicacion().mostrarErro("Administración de Cursos",
                    "Produciuse un erro na busca e perdéronse os datos. Volva a intentalo noutro momento.");
            //Ademais, creo que resulta conveniente neste caso saír desta pantalla e volver ao inicio:
            controllerPrincipal.mostrarPantalla(IdPantalla.ADMINISTRARCURSOS);
        }
    }

    /**
     * Método creado co obxectivo de encher os campos de nome, prezo e descrición cos datos do curso que se ten como
     * atributo directamente. Empregado varias veces ao querer manter os datos ou ao limpar campos e ter curso rexistrado.
     */
    private void completarCamposPrincipais() {
        campoNome.setText(curso.getNome());
        campoPrezo.setText(curso.getPrezo().toString());
        campoDescricion.setText(curso.getDescricion());
    }

    /**
     * Método que nos permite regresar, cando cerramos a pantalla de actividade, á pantalla de actividades dentro
     * desta interface
     *
     * @param curso O curso a establecer.
     */
    public void volverPantallaActividades(Curso curso) {
        //Establecemos o curso que se está xestionando.
        setCurso(getFachadaAplicacion().recuperarDatosCurso(curso));
        //Tomamos do tabpane seleccionando a táboa de actividades:
        this.tabPane.getSelectionModel().select(tabActividades);
    }

    /**
     * Método que nos permite actualizar a táboa de actividades.
     *
     * @param actividadeArrayList A lista de actividades que se quere introducir na táboa.
     */
    private void actualizarTaboaActividades(ArrayList<Actividade> actividadeArrayList) {
        //O primeiro que faremos será vaciar a táboa de actividades:
        taboaActividades.getItems().removeAll(taboaActividades.getItems());
        //Agora, insertaremos todos os items que se pasen como argumento:
        taboaActividades.getItems().addAll(actividadeArrayList);
        //Establecemos unha selección sobre a táboa (se hai resultados), e segundo iso activaremos o botón de xestionar:
        if (!taboaActividades.getItems().isEmpty()) {
            //Se non está baleira seleccionamos o primeiro:
            taboaActividades.getSelectionModel().selectFirst();
            //O que tamén faremos será activar o botón para xestionar unha selección:
            AuxGUI.habilitarCampos(btnXestionarSeleccion, btnCancelarActividade);
        } else {
            //Noutro caso deshabilitamos ese botón e o de eliminar:
            AuxGUI.inhabilitarCampos(btnXestionarSeleccion, btnCancelarActividade);
        }
    }

    /**
     * Método que nos permite actualizar a táboa de usuarios.
     *
     * @param usuarioArrayList A lista de usuarios que se quere introducir na táboa.
     */
    private void actualizarTaboaUsuarios(ArrayList<Usuario> usuarioArrayList) {
        //O primeiro que faremos será vaciar a táboa de usuarios:
        taboaUsuarios.getItems().removeAll(taboaUsuarios.getItems());
        //Agora, insertaremos todos os items que se pasen:
        taboaUsuarios.getItems().addAll(usuarioArrayList);
        //Establecemos unha selección sobre a táboa con resultados:
        taboaUsuarios.getSelectionModel().selectFirst();
    }

    /**
     * Método que nos permite actualizar a táboa de actividades con valoracións.
     *
     * @param actividadeArrayList A lista de actividades que se quere introducir na táboa.
     */
    private void actualizarTaboaActividadesVal(ArrayList<Actividade> actividadeArrayList) {
        //O primeiro que faremos será vaciar a táboa de actividades con valoracións:
        taboaActividadesVal.getItems().removeAll(taboaActividadesVal.getItems());
        //Agora, insertaremos items pasados:
        taboaActividadesVal.getItems().addAll(actividadeArrayList);
        //Establecemos unha selección:
        taboaActividadesVal.getSelectionModel().selectFirst();
    }

    /**
     * Método que nos permite actualizar a táboa de profesores
     *
     * @param persoalArrayList A lista de profesores que se quere introducir na táboa.
     */
    private void actualizarTaboaProfesores(ArrayList<Persoal> persoalArrayList) {
        //O primeiro que faremos será vaciar a táboa de profesores:
        taboaProfesores.getItems().removeAll(taboaProfesores.getItems());
        //Agora, insertaremos items pasados:
        taboaProfesores.getItems().addAll(persoalArrayList);
        //Establecemos unha selección:
        taboaProfesores.getSelectionModel().selectFirst();
    }
}

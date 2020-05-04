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
import java.util.ResourceBundle;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Víctor Barreiro
 * Clase que serve de controlador da pantalla de xestión dun curso determinado, unha das pantallas máis completas que
 * desenvolvemos na nosa parte de aplicación.
 * Dous usos:
 *      - Editar datos dun curso e amosar diferente información do mesmo (cando xa está creado).
 *      - Introducir os datos dun novo curso.
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
    public Button btnBorrarActividade;
    public Button btnModificarSeleccion;
    public TableView taboaUsuarios;
    public Button btnCancelar;
    public Button btnLimpar;
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


    /**
     * Atributos privados: coma sempre, temos por un lado a referencia ao controlador da ventá principal.
     * Por outro lado, tense unha referencia a un curso (pode ser null ou non, dependerá da ventá usada.
     */
    private vPrincipalController controllerPrincipal;
    private Curso curso;

    /**
     * Constructor da pantalla de xestión dun curso
     * @param fachadaAplicacion A referencia á fachada da parte de aplicación.
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
                        (int) ((c.getValue().getDuracion().floatValue()-c.getValue().getDuracion().intValue())*60) + "m"
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

        taboaUsuarios.getColumns().addAll(loginColumn,nomeColumn, idadeColumn,dificultadesColumn,
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
        TableColumn<String, Usuario> loginProfColumn = new TableColumn<>("Login");
        loginProfColumn.setCellValueFactory(new PropertyValueFactory<>("login"));

        TableColumn<Float, Persoal> valoracionPersoalColumn = new TableColumn<>("Valoración");
        valoracionPersoalColumn.setCellValueFactory(new PropertyValueFactory<>("valoracion"));
        //Engadimos columnas:
        taboaProfesores.getColumns().addAll(loginProfColumn, valoracionPersoalColumn);
        //Controlamos o tamaño das columnas:
        taboaProfesores.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //En primeira instancia, inhabilitamos TODOS os botóns salvo o de gardar curso.
        AuxGUI.ocultarCampos(btnActivar,btnBorrarActividade,btnEngadirActividade,btnModificarSeleccion,
                btnCancelar,vBoxBotonInforme,vBoxDetalleInforme);

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
    public void reiniciarForm(){
        //Reestablecemos o campo do curso de cara á apertura de novo da ventá.
        //Deste xeito, para indicar que se vai configurar un curso haberá que usar o setter que temos máis abaixo
        //despois de abrir a ventá.
        this.curso = null;
    }

    /**
     * Método que representa as accións realizadas ao premer o botón de activación dun curso:
     * @param actionEvent A acción que tivo lugar.
     */
    public void btnActivarAction(ActionEvent actionEvent) {
        //Neste caso o que se fará é intentar activar o curso:
        if(curso != null && curso.getCodCurso() != null) {
            //Evidentemente, só poderemos activar o curso se non está aberto:
            if(!curso.isAberto()) {
                //Tentamos facer a activación:
                try {
                    //Gardamos o resultado:
                    TipoResultados res = getFachadaAplicacion().activarCurso(curso);
                    switch(res){
                        case sitIncoherente:
                            //No caso de devolver este valor, indicará que o curso non estaba preparado para ser activado:
                            getFachadaAplicacion().mostrarErro("Administración de Cursos",
                                    "O curso non está todavía preparado para ser activado. Lembra, debe de ter" +
                                            " como mínimo dúas actividades e non pode ter comezado!");
                            //Non faríamos ningunha modificación.
                            break;
                        case correcto:
                            //Unha vez activado, confirmarémolo, quitaremos o botón de activar curso e deixaremos continuar:
                            //O que faremos será pedir confirmación ao usuario de se quere mandarlle unha mensaxe aos
                            //socios:
                            if(getFachadaAplicacion().mostrarConfirmacion("Administración de Cursos",
                                    "O curso activouse. ¿Queres mandar unha mensaxe avisando aos socios do centro?")
                                    == ButtonType.OK) {

                                //Se o quere, elaboraremos unha mensaxe:
                                Mensaxe mensaxe = new Mensaxe(controllerPrincipal.getUsuario(),
                                        "Abertas as inscricións ao curso '" + curso.getNome() + "'. A que esperas " +
                                                "para apuntarte? As prazas voan!!!");

                                //Esa mensaxe mandarémola a todos os socios:
                                getFachadaAplicacion().enviarAvisoSocios(mensaxe);

                                //Se se chega aquí sen lanzar unha excepción, será porque se logrou avisar a todos
                                //os socios. Avísase:
                                getFachadaAplicacion().mostrarConfirmacion("Administración de Cursos",
                                        "Avisado a TODOS os socios");
                            }
                            //Desaactivamos o botón de activación do curso:
                            AuxGUI.ocultarCampos(btnActivar);
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
     * @param actionEvent A acción que tivo lugar.
     */
    public void btnGardarAction(ActionEvent actionEvent) {
        //No caso de gardar, hai que verificar que o nome e o ID estén cubertos.
        if(!ValidacionDatos.estanCubertosCampos(campoNome, campoPrezo)){
            //Se non se cubriron, habilítase a etiqueta que indica campos obrigatorios:
            AuxGUI.amosarCampos(tagObrigatorios);
            return; //Non seguimos adiante.
        } else {
            //Se non, ocultamos esa etiqueta dado que non queremos que apareza
            AuxGUI.ocultarCampos(tagObrigatorios);
        }

        //Chegados aquí, intentamos facer a actualización. Podería ser inserción de datos ou modificación, dependendo
        //da situación.
        if(curso == null) {
            try {
                //Rexistramos un novo curso na base de datos:
                Curso c = new Curso(campoNome.getText(), campoDescricion.getText(), Float.parseFloat(campoPrezo.getText()));
                TipoResultados res = getFachadaAplicacion().rexistrarCurso(c);
                //En función do resultado, amosaremos un erro ou continuaremos:
                switch(res){
                    case datoExiste:
                        //Se xa existe un curso co mesmo nome, amosamos erro:
                        this.getFachadaAplicacion().mostrarErro("Administración de Cursos",
                                "Xa existe un curso co nome " + c.getNome().toLowerCase());
                        break;
                    case correcto:
                        //En caso de que se teña resultado correcto, habilitaranse outras modificacións da pantalla e avisarase
                        //da correcta inserción:
                        this.getFachadaAplicacion().mostrarInformacion("Administración de Cursos",
                                "Curso " + c.getNome() + " insertado correctamente." +
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
            try{
                //Creamos un novo curso para poder modificar: inda non sabemos se os cambios se poden efectuar e polo
                //tanto actualizar o curso.
                Curso cursoModif = new Curso(curso.getCodCurso(), campoNome.getText(),
                        campoDescricion.getText(), Float.parseFloat(campoPrezo.getText()));
                //Chamamos ao método que realiza a modificación:
                TipoResultados res = getFachadaAplicacion().modificarCurso(curso);
                //En función do resultado, diferentes alternativas:
                switch(res){
                    case datoExiste:
                        //Se xa existía un curso co nome que se intentou cambiar, avísase e déixanse os campos orixinais.
                        this.getFachadaAplicacion().mostrarErro("Administración de Cursos",
                                "Xa existe un curso co nome " + curso.getNome().toLowerCase());
                        campoNome.setText(curso.getNome());
                        campoDescricion.setText(curso.getDescricion());
                        campoPrezo.setText(curso.getPrezo().toString());
                        break;
                    case foraTempo:
                        //Se non se cumpren os tempos para modificar, é dicir, o curso xa comezou, non deixaremos modificar
                        //a información e faremos o análogo ao caso anterior cos campos:
                        this.getFachadaAplicacion().mostrarErro("Administración de Cursos",
                                "Non se poden actualizar os datos, pois o curso xa comezou.");
                        campoNome.setText(curso.getNome());
                        campoDescricion.setText(curso.getDescricion());
                        campoPrezo.setText(curso.getPrezo().toString());
                        break;
                    case correcto:
                        //Se se puideron facer os cambios, entón os campos mantéñense e actualizamos o curso_
                        //Buscamos de novo o curso para así refrescar:
                        Curso actu = getFachadaAplicacion().recuperarDatosCurso(curso);
                        //Actualizamos toda a información:
                        setCurso(actu);

                        //Solicitaremos ao usuario se quere que lle mandemos unha mensaxe aos membros do curso:
                        if(getFachadaAplicacion().mostrarConfirmacion("Administración de Cursos",
                                "Datos do curso " + curso.getCodCurso() + " modificados correctamente. Queres avisar " +
                                        "aos socios participantes no curso de que houbo cambios?") == ButtonType.OK) {

                        }
                        break;
                }
            } catch(ExcepcionBD excepcionBD){
                //Mostramos o erro asociado e restauramos os campos da inserción:
                this.getFachadaAplicacion().mostrarErro("Administración de Cursos", excepcionBD.getMessage());
                campoNome.setText(curso.getNome());
                campoDescricion.setText(curso.getDescricion());
                campoPrezo.setText(curso.getPrezo().toString());
            }
        }
    }

    /**
     * Método que representa as accións levadas a cabo ao premer o botón de engadido dunha actividade.
     * @param actionEvent A acción que tivo lugar.
     */
    public void btnEngadirActividadeAction(ActionEvent actionEvent) {
    }

    /**
     * Método que representa as accións levadas a cabo ao premer o botón de borrado dunha actividade.
     * @param actionEvent A acción que tivo lugar.
     */
    public void btnBorrarActividadeAction(ActionEvent actionEvent) {
    }

    /**
     * Método que representa as accións levadas a cabo ao premer o botón de modificado dunha selección.
     * @param actionEvent
     */
    public void btnModificarSeleccionAction(ActionEvent actionEvent) {
    }

    /**
     * Método que representa as accións levadas a cabo ao premer o botón de cancelar.
     * @param actionEvent
     */
    public void btnCancelarAction(ActionEvent actionEvent) {
        //Tamén poderemos cancelar un curso, sempre e cando fora rexistrado:
        //(En teoría non debería darse o caso, pero facemos igualmente a comprobación):
        if(curso != null){
            //Se chegamos aquí, entón o que faremos será:
            //1->Pedir confirmación
            //2->Intentar levar a cabo o borrado.

            if(super.getFachadaAplicacion().mostrarConfirmacion("Administración de Cursos",
                    "Desexa cancelar o curso?") == ButtonType.OK){
                try{
                    TipoResultados res = super.getFachadaAplicacion().cancelarCurso(curso);
                    //En función do resultado, operamos:
                    switch(res){
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
                } catch(ExcepcionBD excepcionBD){
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

    public void btnLimparAction(ActionEvent actionEvent) {
        //O que faremos será vaciar automáticamente todos os campos:
        //Se temos un curso rexistrado xa, o que faremos será poñer os campos que lle corresponde:
        if(curso != null && curso.getCodCurso() != 0){
            campoNome.setText(curso.getNome());
            campoPrezo.setText(curso.getPrezo()+"");
            campoDescricion.setText(curso.getDescricion());
        } else {
            //Se non, pasamos a vacialos:
            campoNome.setText("");
            campoPrezo.setText("");
            campoDescricion.setText("");
        }
        //En ambos casos, ocultamos a etiqueta de campos obrigatorios:
        tagObrigatorios.setVisible(false);
    }


    /**
     * Setter do curso, co que prepararemos a pantalla para a configuración do curso pasado.
     * @param curso O curso do que se vai amosar a información.
     */
    public void setCurso(Curso curso){
        //Asignamos o curso:
        this.curso = curso;
        //Teremos que encher os campos co que corresponde do curso que está apuntado, e encher as táboas:
        campoCodigo.setText(curso.getCodCurso().toString());
        campoNome.setText(curso.getNome());
        campoDescricion.setText(curso.getDescricion());
        campoPrezo.setText(curso.getPrezo().toString());
        //Enchemos a táboa de actividades:
        taboaActividades.getItems().removeAll(taboaActividades.getItems());
        taboaActividades.getItems().addAll(curso.getActividades());
        //Enchemos a táboa de participantes:
        taboaUsuarios.getItems().removeAll(taboaUsuarios.getItems());
        taboaUsuarios.getItems().addAll(curso.getParticipantes());
        if(curso.isAberto()) {
            //Se o curso xa está aberto non damos opción a abrilo:
            AuxGUI.ocultarCampos(btnActivar);
        } else {
            AuxGUI.amosarCampos(btnActivar);
        }
        //Activamos botóns actividades, cancelación e xeración de informe
        AuxGUI.amosarCampos(btnBorrarActividade, btnEngadirActividade, btnModificarSeleccion,
                btnCancelar);
        //Se o curso está rematado, entón daremos opción a amosar o botón do informe:
        if(curso.getDataFin() != null && curso.getDataFin().before(new Date(System.currentTimeMillis()))){
            AuxGUI.amosarCampos(vBoxBotonInforme);
        }
    }


    public void btnXerarInformeAction(ActionEvent actionEvent) {
        //O que hai que facer primeiro é comprobar se se está en condicións de recuperar os datos do informe, para o que
        //haberá que saber se o curso xa rematou.
        //Iremos a recuperar a información do curso:
        curso = getFachadaAplicacion().informeCurso(curso);
        //Comprobamos que se devolvese correctamente o curso:
        if(curso != null) {
            //Neste caso actualizaremos seguro aqueles campos que se amosan sempre nesta pantalla:
            //O nome do curso:
            campoNome.setText(curso.getNome());
            //A descrición
            campoDescricion.setText(curso.getDescricion());
            //O prezo do curso:
            campoPrezo.setText(curso.getPrezo()+"");
            //Actualizamos as táboas, primeiro eliminando os campos actuais:
            taboaActividades.getItems().removeAll(taboaActividades.getItems());
            taboaUsuarios.getItems().removeAll(taboaUsuarios.getItems());
            //Enchemos a táboa de actividades:
            taboaActividades.getItems().addAll(curso.getActividades());
            //Enchemos a táboa de participantes:
            taboaUsuarios.getItems().addAll(curso.getParticipantes());
            //Comprobamos tamén que rematara:
            if(curso.getDataFin().before(new Date(System.currentTimeMillis()))) {
                //Imos agora a encher os campos do informe en sí:
                campoDuracion.setText(curso.getDuracion()+"");
                campoDataInicio.setText(curso.getDataInicio().toString());
                campoDataFin.setText(curso.getDataFin().toString());
                campoNumProfesores.setText(curso.getNumProfesores()+"");
                campoNumActividades.setText(curso.getNumActividades()+"");
                tagVTM.setText(curso.getValMedia().toString());
                if(curso.getValMedia().compareTo((float)2.5) > 0){
                    tagVTM.setTextFill(Paint.valueOf("Green"));
                } else {
                    tagVTM.setTextFill(Paint.valueOf("Red"));
                }
                //Enchemos as novas táboas:
                taboaActividadesVal.getItems().addAll(curso.getActividades());
                taboaProfesores.getItems().addAll(curso.getProfesores());

                //Cambiamos a VBox visible:
                vBoxDetalleInforme.setVisible(true);
                vBoxBotonInforme.setVisible(false);
            } else {
                getFachadaAplicacion().mostrarErro("Administración de Cursos",
                        "Non se pode amosar o informe do curso, dado que inda non rematou.");
            }
        } else {
            getFachadaAplicacion().mostrarErro("Administración de Cursos",
                    "Produciuse un erro na busca e perdéronse os datos. Volva a intentalo noutro momento.");
            //Ademais, creo que resulta conveniente neste caso saír desta pantalla e volver ao inicio:
            controllerPrincipal.mostrarPantalla(IdPantalla.ADMINISTRARCURSOS);
        }
    }
}

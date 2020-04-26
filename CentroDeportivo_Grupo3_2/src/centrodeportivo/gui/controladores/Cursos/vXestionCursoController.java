package centrodeportivo.gui.controladores.Cursos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.actividades.Actividade;
import centrodeportivo.aplicacion.obxectos.actividades.Curso;
import centrodeportivo.aplicacion.obxectos.tipos.TipoResultados;
import centrodeportivo.aplicacion.obxectos.usuarios.Persoal;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.funcionsAux.ValidacionDatos;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.principal.IdPantalla;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import com.sun.prism.paint.Color;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class vXestionCursoController extends AbstractController implements Initializable {

    //Atributos públicos: correspóndense coas compoñentes desta ventá:
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


    //Privados
    private vPrincipalController controllerPrincipal;
    private Curso curso;

    //Constructor:
    public vXestionCursoController(FachadaAplicacion fachadaAplicacion, vPrincipalController controllerPrincipal) {
        super(fachadaAplicacion);
        this.controllerPrincipal = controllerPrincipal;
    }

    //Método para inicializar a ventá:
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Hai que levar a cabo varias tarefas, e pode haber dúas situacións:
        //Situación 1 - Non hai curso asociado á clase (NULL).
        //Situación 2 - Hai curso asociado á clase.

        //Igualmente, en ambos casos hai que inicializar as táboas:
        //Táboa de actividades:
        TableColumn<Date, Actividade> dataActividadeColumn = new TableColumn<>("Data");
        dataActividadeColumn.setCellValueFactory(new PropertyValueFactory<>("data"));

        TableColumn<String, Actividade> areaColumn = new TableColumn<>("Area");
        areaColumn.setCellValueFactory(new PropertyValueFactory<>("area"));

        TableColumn<Float, Actividade> duracionColumn = new TableColumn<>("Duración");
        duracionColumn.setCellValueFactory(new PropertyValueFactory<>("duracion"));

        TableColumn<String, Actividade> profesorColumn = new TableColumn<>("Profesor");
        profesorColumn.setCellValueFactory(new PropertyValueFactory<>("profesor"));

        taboaActividades.getColumns().addAll(dataActividadeColumn, areaColumn, duracionColumn, profesorColumn);
        //Facemos isto para controlar o tamaño das columnas.
        taboaActividades.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //Táboa de socios:
        TableColumn<String, Usuario> loginColumn = new TableColumn<>("Login");
        loginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));

        TableColumn<Date, Usuario> dataNacementoColumn = new TableColumn<>("Data de Nacemento");
        dataNacementoColumn.setCellValueFactory(new PropertyValueFactory<>("dataNacemento"));

        TableColumn<String, Usuario> dificultadesColumn = new TableColumn<>("Dificultades");
        dificultadesColumn.setCellValueFactory(new PropertyValueFactory<>("dificultades"));

        TableColumn<String, Usuario> numTelefonoColumn = new TableColumn<>("Número de Teléfono");
        numTelefonoColumn.setCellValueFactory(new PropertyValueFactory<>("numTelefono"));

        taboaUsuarios.getColumns().addAll(loginColumn,dataNacementoColumn,dificultadesColumn,numTelefonoColumn);
        //Facemos isto para controlar o tamaño das columnas.
        taboaUsuarios.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //Táboa de actividades e valoracións asociadas: será semellante á outra de actividades:
        TableColumn<Date, Actividade> dataActColumn = new TableColumn<>("Data");
        dataActColumn.setCellValueFactory(new PropertyValueFactory<>("data"));

        TableColumn<String, Actividade> areaActColumn = new TableColumn<>("Area");
        areaActColumn.setCellValueFactory(new PropertyValueFactory<>("area"));

        TableColumn<String, Actividade> profActColumn = new TableColumn<>("Profesor");
        profActColumn.setCellValueFactory(new PropertyValueFactory<>("profesor"));

        //Engadimos unha columna diferente, a das valoracións:
        TableColumn<Float, Actividade> valoracionColumn = new TableColumn<>("Valoración");
        valoracionColumn.setCellValueFactory(new PropertyValueFactory<>("valMedia"));

        taboaActividadesVal.getColumns().addAll(dataActColumn, areaActColumn, profActColumn, valoracionColumn);
        //Con isto controlamos o tamaño das columnas:
        taboaActividadesVal.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //Táboa de valoracións aos profesores:
        //De novo, hai columnas similares coas de usuario:
        TableColumn<String, Usuario> loginProfColumn = new TableColumn<>("Login");
        loginProfColumn.setCellValueFactory(new PropertyValueFactory<>("login"));

        TableColumn<Float, Persoal> valoracionPersoalColumn = new TableColumn<>("Valoración");
        valoracionPersoalColumn.setCellValueFactory(new PropertyValueFactory<>("valoracion"));
        //Engadimos columnas:
        taboaProfesores.getColumns().addAll(loginProfColumn, valoracionPersoalColumn);
        //Controlamos o tamaño das columnas:
        taboaProfesores.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //Situación 1:
        if(curso == null){
            //Neste caso teremos que axustarnos para reflexar o rexistro dun curso:
            //En primeira instancia, inhabilitamos TODOS os botóns salvo o de gardar curso.
            btnActivar.setVisible(false);
            btnBorrarActividade.setVisible(false);
            btnEngadirActividade.setVisible(false);
            btnModificarSeleccion.setVisible(false);
            btnCancelar.setVisible(false);

        } else {
            //Entón teremos que encher os campos co que corresponde do curso que está apuntado, e encher as táboas:
            campoCodigo.setText(curso.getCodCurso()+"");
            campoNome.setText(curso.getNome());
            campoDescricion.setText(curso.getDescricion());
            campoPrezo.setText(curso.getPrezo()+"");
            //Enchemos a táboa de actividades:
            taboaActividades.getItems().addAll(curso.getActividades());
            //Enchemos a táboa de participantes:
            taboaUsuarios.getItems().addAll(curso.getParticipantes());
        }

        vBoxBotonInforme.setVisible(true);
        vBoxDetalleInforme.setVisible(false);
    }

    public void btnActivarAction(ActionEvent actionEvent) {
    }

    public void btnGardarAction(ActionEvent actionEvent) {
        //No caso de gardar, hai que verificar que o nome e o ID estén cubertos.
        if(!ValidacionDatos.estanCubertosCampos(campoNome, campoPrezo)){
            tagObrigatorios.setVisible(true);
            return; //Non seguimos adiante.
        }

        //Chegados aquí, intentamos facer a actualización. Podería ser inserción de datos ou modificación, dependendo
        //da situación.
        if(curso == null) {
            try {
                curso = new Curso(campoNome.getText(), campoDescricion.getText(), Float.parseFloat(campoPrezo.getText()));
                TipoResultados res = getFachadaAplicacion().rexistrarCurso(curso);
                //En función do resultado, amosaremos un erro ou continuaremos:
                switch(res){
                    case datoExiste:
                        this.getFachadaAplicacion().mostrarErro("Administración de Cursos",
                                "Xa existe un curso co nome " + curso.getNome().toLowerCase());
                        break;
                    case correcto:
                        //En caso de que se teña resultado correcto, habilitaranse outras modificacións da pantalla e avisarase
                        //da correcta inserción:
                        this.getFachadaAplicacion().mostrarInformacion("Administración de Cursos",
                                "Curso " + curso.getNome() + " insertado correctamente." +
                                "O seu ID é " + curso.getCodCurso() + ".");
                        //Primeiro, gardamos no campo do código do curso o código:
                        campoCodigo.setText(curso.getCodCurso() +"");
                        //Activamos o resto de botóns para que o usuario poida seguir coa xestión do curso:
                        btnActivar.setVisible(true);
                        btnModificarSeleccion.setVisible(true);
                        btnEngadirActividade.setVisible(true);
                        btnBorrarActividade.setVisible(true);
                        break;
                }
            } catch (ExcepcionBD excepcionBD) {
                this.getFachadaAplicacion().mostrarErro("Administración de Cursos", excepcionBD.getMessage());
            }
        } else {
            //Se xa existe o curso, entón o que queremos será modificar os seus datos na base de datos:
            try{
                //Creamos un novo curso para poder modificar: inda non sabemos se os cambios se poden efectuar e polo
                //tanto actualizar o curso.
                Curso cursoModif = new Curso(curso.getCodCurso(), campoNome.getText(),
                        campoDescricion.getText(), Float.parseFloat(campoPrezo.getText()));
                TipoResultados res = getFachadaAplicacion().modificarCurso(curso);
                //En función do resultado, diferentes alternativas:
                switch(res){
                    case datoExiste:
                        //Se xa existía un curso co nome que se intentou cambiar, avísase e déixanse os campos orixinais.
                        this.getFachadaAplicacion().mostrarErro("Administración de Cursos",
                                "Xa existe un curso co nome " + curso.getNome().toLowerCase());
                        campoNome.setText(curso.getNome());
                        campoDescricion.setText(curso.getDescricion());
                        campoPrezo.setText(curso.getPrezo()+"");
                        break;
                    case foraTempo:
                        //Se non se cumpren os tempos para modificar, é dicir, o curso xa comezou, non deixaremos modificar
                        //a información e faremos o análogo ao caso anterior cos campos:
                        this.getFachadaAplicacion().mostrarErro("Administración de Cursos",
                                "Non se poden actualizar os datos, pois o curso xa comezou.");
                        break;
                    case correcto:
                        //Se se puideron facer os cambios, entón os campos mantéñense e actualizamos o curso:
                        this.getFachadaAplicacion().mostrarConfirmacion("Administración de Cursos",
                                "Datos do curso " + curso.getCodCurso() + " modificados correctamente");
                        curso.setNome(cursoModif.getNome());
                        curso.setDescricion(cursoModif.getDescricion());
                        curso.setPrezo(cursoModif.getPrezo());
                        break;
                }
            } catch(ExcepcionBD excepcionBD){
                //Mostramos o erro asociado e restauramos os campos da inserción:
                this.getFachadaAplicacion().mostrarErro("Administración de Cursos", excepcionBD.getMessage());
                campoNome.setText(curso.getNome());
                campoDescricion.setText(curso.getDescricion());
                campoPrezo.setText(curso.getPrezo()+"");
            }
        }
    }

    public void btnEngadirActividadeAction(ActionEvent actionEvent) {
    }

    public void btnBorrarActividadeAction(ActionEvent actionEvent) {
    }

    public void btnModificarSeleccionAction(ActionEvent actionEvent) {
    }

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
                            this.controllerPrincipal.mostrarMenu(IdPantalla.INICIO);
                            break;
                    }
                } catch(ExcepcionBD excepcionBD){
                    //En caso de excepción temos un problema:
                    //Podería ter sucedido que o curso fose borrado polo medio, así que o que cremos que é máis lóxico
                    //é que se volva directamente á ventá de inicio se hai calquera erro na base de datos.
                    super.getFachadaAplicacion().mostrarErro("Administración de Cursos",
                            excepcionBD.getMessage());
                    this.controllerPrincipal.mostrarMenu(IdPantalla.INICIO);
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

    //Getters e setters do curso:
    public void setCurso(Curso curso){
        this.curso = curso;
    }

    public Curso getCurso(){
        return this.curso;
    }

    public void btnXerarInformeAction(ActionEvent actionEvent) {
        //O que hai que facer primeiro é comprobar se se está en condicións de recuperar os datos do informe, para o que
        //haberá que saber se o curso xa rematou.
        //Iremos a recuperar a información do curso:
        curso = getFachadaAplicacion().informeCurso(curso);
        //Actualizamos todos os campos cos novos resultados:
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

        vBoxDetalleInforme.setVisible(true);
        vBoxBotonInforme.setVisible(false);
    }
}

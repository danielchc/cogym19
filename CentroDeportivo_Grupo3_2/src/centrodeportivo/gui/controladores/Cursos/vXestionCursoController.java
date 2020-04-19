package centrodeportivo.gui.controladores.Cursos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.actividades.Actividade;
import centrodeportivo.aplicacion.obxectos.actividades.Curso;
import centrodeportivo.aplicacion.obxectos.tipos.TipoResultados;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.funcionsAux.ValidacionDatos;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
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

        }
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
                }
            } catch (ExcepcionBD e) {
                this.getFachadaAplicacion().mostrarErro("Administración de Cursos", e.getMessage());
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
}

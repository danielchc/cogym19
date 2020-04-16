package centrodeportivo.gui.controladores.Actividades;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.actividades.TipoActividade;
import centrodeportivo.aplicacion.obxectos.tipos.TipoResultados;
import centrodeportivo.funcionsAux.ValidacionDatos;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.principal.IdPantalla;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Victor Barreiro
 */
public class vInsercionTipoActividadeController extends AbstractController implements Initializable {
    //Atributos públicos (compoñentes da ventá):
    public HBox caixaCodigo;
    public TextField campoCodigo;
    public TextField campoNome;
    public TextArea campoDescricion;
    public Button btnVolver;
    public Button btnGardar;
    public Button btnBorrar;

    //Atributos privados:
    private vPrincipalController controllerPrincipal;
    private TipoActividade tipoActividade;

    //Constructor:
    public vInsercionTipoActividadeController(FachadaAplicacion fachadaAplicacion, vPrincipalController controllerPrincipal){
        super(fachadaAplicacion);
        this.controllerPrincipal = controllerPrincipal;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //A inicialización pode darse de dous xeitos: que se teña un tipo de actividade ou que sexa un null.
        if(tipoActividade == null){
            //No primeiro caso: todos os campos baleiros - inserción dun novo tipo.
            caixaCodigo.setVisible(false); //Ocultamos a parte que amosa o código.
            //Facemos editable o nome:
            //Vaciamos os campos:
            campoNome.setText("");
            campoCodigo.setText("");
            //Impídese o borrado:
            btnBorrar.setVisible(false);
        } else {
            //Se hai un tipo de actividade, entón amosarase a súa información.
            caixaCodigo.setVisible(true); //Facemos visible a parte que amosa o código.
            campoCodigo.setText(""+tipoActividade.getCodTipoActividade());
            campoNome.setText(tipoActividade.getNome());
            campoDescricion.setText(tipoActividade.getDescricion());
            //Habilítanse borrados:
            btnBorrar.setVisible(true);
        }
    }

    public void btnVolverAction(ActionEvent actionEvent) {
        //Nese caso, pásase á venta de administración:
        controllerPrincipal.mostrarMenu(IdPantalla.ADMINISTRARTIPOSACTIVIDADES);
    }

    public void btnGardarAction(ActionEvent actionEvent) {
        //Antes de nada, hai que verificar que o nome esté cuberto:
        if (!ValidacionDatos.estanCubertosCampos(campoNome)) {
            super.getFachadaAplicacion().mostrarErro("Administración de Tipos de Actividades", "Hai que insertar un nome!");
            return;
        }
        //Poden ocorrer dúas cousas: que haxa que insertar un novo tipo ou modificalo.
        if(tipoActividade != null) {
            //Se o tipo de actividade non é nulo, é unha modificación. Como a descrición pode ser nula, faise a actualización directamente.
            tipoActividade.setDescricion(campoDescricion.getText());
            //Engadimos tamén o nome actualizado:
            tipoActividade.setNome(campoNome.getText());
            //Intentaremos entón modificar o tipo de actividade:
            try {
                TipoResultados res = this.getFachadaAplicacion().modificarTipoActividade(tipoActividade);
                switch(res){
                    case correcto:
                        //Se rematou o método sen problema, entón amósase confirmación:
                        this.getFachadaAplicacion().mostrarInformacion("Administración de Tipos de Actividades",
                                "Tipo de actividade con id = " + tipoActividade.getCodTipoActividade() + " modificado correctamente.");
                        //Non saímos por se se queren facer outras tarefas:
                        break;
                    case datoExiste:
                        //Se xa existía outro tipo de actividade co nome que se quería poñer, avísase do erro:
                        //Mostramos un erro:
                        this.getFachadaAplicacion().mostrarErro("Administración de Tipos de Actividades",
                                "Non se puido modificar a base de datos: xa existe un tipo de actividade de nome '" +
                                        tipoActividade.getNome().toLowerCase() + "'.");
                        break;

                }
            } catch (ExcepcionBD excepcionBD) {
                excepcionBD.printStackTrace();
            }
        } else {
            //Se fose nulo, é unha inserción:
            TipoActividade tipoActividade = new TipoActividade(campoNome.getText(), campoDescricion.getText());
            //Vaise intentar a actualización da base de datos:
            try {
                TipoResultados res = this.getFachadaAplicacion().crearTipoActividade(tipoActividade);
                //En función do resultado, avaliamos:
                switch(res){
                    case datoExiste:
                        //Mostramos un erro:
                        this.getFachadaAplicacion().mostrarErro("Administración de Tipos de Actividades",
                                "Xa existe un tipo de actividade de nome '" + tipoActividade.getNome().toLowerCase() + "'.");
                        break;
                    case correcto:
                        //Amosamos unha confirmación:
                        this.getFachadaAplicacion().mostrarInformacion("Administración de Tipos de Actividades",
                                "Introducido o tipo de actividade, o seu ID é " + tipoActividade.getCodTipoActividade() + ".");
                        //Finalmente, sáese unha vez rematado:
                        controllerPrincipal.mostrarMenu(IdPantalla.ADMINISTRARTIPOSACTIVIDADES);
                        break;
                }
            } catch (ExcepcionBD excepcionBD) {
                excepcionBD.printStackTrace();
            }
        }

    }

    public void btnBorrarAction(ActionEvent actionEvent) {
        //Se se puido presionar, é porque se quere borrar o tipo de actividade presentado.
        if(super.getFachadaAplicacion().mostrarConfirmacion("Administración de Tipos de Actividades",
                "Desexa eliminar o tipo de actividade seleccionado?") == ButtonType.OK) {
            //Se así o quere facer o usuario, tentaremos o borrado do tipo:
            try {
                TipoResultados res = this.getFachadaAplicacion().eliminarTipoActividade(tipoActividade);
                //En función do resultado, diferentes opcións:
                switch(res){
                    case correcto:
                        //Amosamos unha mensaxe de confirmación:
                        this.getFachadaAplicacion().mostrarConfirmacion("Administración de Tipos de Actividades",
                                "Eliminación correcta.");
                        //Volvese á ventá anterior:
                        controllerPrincipal.mostrarMenu(IdPantalla.ADMINISTRARTIPOSACTIVIDADES);
                        break;
                    case referenciaRestrict:
                        //Amosamos mensaxe de erro:
                        this.getFachadaAplicacion().mostrarErro("Administración de Tipos de Actividades",
                                "O tipo '" + tipoActividade.getNome() + "' ten actividades asociadas! Non se pode borrar.");
                        break;
                }
            } catch (ExcepcionBD excepcionBD) {
                excepcionBD.printStackTrace();
            }
        }
    }

    //Getter e setter para o tipo de actividade:
    public void setTipoActividade(TipoActividade tipoActividade){
        this.tipoActividade = tipoActividade;
    }

    public TipoActividade getTipoActividade(){
        return tipoActividade;
    }

}

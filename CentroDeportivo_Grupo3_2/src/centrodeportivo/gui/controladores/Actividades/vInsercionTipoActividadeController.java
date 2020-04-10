package centrodeportivo.gui.controladores.Actividades;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.actividades.TipoActividade;
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
            campoNome.setEditable(true);
            //Vaciamos os campos:
            campoNome.setText("");
            campoCodigo.setText("");
            //Impídese o borrado:
            btnBorrar.setVisible(false);
        } else {
            //Se hai un tipo de actividade, entón amosarase a súa información.
            caixaCodigo.setVisible(true); //Facemos visible a parte que amosa o código.
            campoCodigo.setText(""+tipoActividade.getCodTipoActividade());
            campoNome.setEditable(false); //Ao editar, non se pode cambiar o nome.
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
        //Poden ocorrer dúas cousas: que haxa que insertar un novo tipo ou modificalo.
        if(tipoActividade != null) {
            //Se o tipo de actividade non é nulo, é unha modificación. Como a descrición pode ser nula, faise a actualización directamente.
            tipoActividade.setDescricion(campoDescricion.getText());
            this.getFachadaAplicacion().modificarTipoActividade(tipoActividade);
        } else {
            //Se fose nulo, é unha inserción:
            //Antes de nada, hai que verificar que o nome esté cuberto:
            if (!ValidacionDatos.estanCubertosCampos(campoNome)) {
                super.getFachadaAplicacion().mostrarErro("Administración de Tipos de Actividades", "Hai que insertar un nome!");
                return;
            }
            TipoActividade tipoActividade = new TipoActividade(campoNome.getText(), campoDescricion.getText());
            this.getFachadaAplicacion().crearTipoActividade(tipoActividade);
            if (tipoActividade.getCodTipoActividade() != 0) {
                this.getFachadaAplicacion().mostrarConfirmacion("Administración de Tipos de Actividades", "Insertado o tipo de actividade. O seu id é " + tipoActividade.getCodTipoActividade() + ".");
            }
        }
        //En calquera dos dous casos, sáese unha vez rematado:
        controllerPrincipal.mostrarMenu(IdPantalla.ADMINISTRARTIPOSACTIVIDADES);
    }

    public void btnBorrarAction(ActionEvent actionEvent) {
        //Se se puido presionar, é porque se quere borrar o tipo de actividade presentado.
        if(super.getFachadaAplicacion().mostrarConfirmacion("Administración de Tipos de Actividades",
                "Desexa eliminar o tipo de actividade seleccionado?") == ButtonType.OK) {
            this.getFachadaAplicacion().eliminarTipoActividade(tipoActividade);
            //Volvese á ventá anterior:
            controllerPrincipal.mostrarMenu(IdPantalla.ADMINISTRARTIPOSACTIVIDADES);
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

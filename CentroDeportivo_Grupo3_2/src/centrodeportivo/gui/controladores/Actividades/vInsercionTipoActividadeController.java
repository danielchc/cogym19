package centrodeportivo.gui.controladores.Actividades;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.actividades.TipoActividade;
import centrodeportivo.aplicacion.obxectos.tipos.TipoResultados;
import centrodeportivo.funcionsAux.ValidacionDatos;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.AuxGUI;
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
 * Clase que servirá de controlador da pantalla de inserción/modificación dun tipo de actividade.
 */
public class vInsercionTipoActividadeController extends AbstractController implements Initializable {
    
    /**
     * Atributos públicos: son compoñentes da pantalla de inserción de tipos de actividades.
     */
    public HBox caixaCodigo;
    public TextField campoCodigo;
    public TextField campoNome;
    public TextArea campoDescricion;
    public Button btnVolver;
    public Button btnGardar;
    public Button btnBorrar;
    public Button btnRestaurar;

    /**
     * Atributos privados: temos dous, por un lado, a referencia ao controlador da ventá principal e, por outro,
     * a referencia ao tipo de actividade que se pode amosar na pantalla.
     * Pode ser que esta pantalla se use para insertar un novo tipo de actividade ou ben para modificar un existente.
     */
    private vPrincipalController controllerPrincipal;
    private TipoActividade tipoActividade;

    /**
     * Constructor do controlador da ventá de inserción/modificación dun tipo de actividade.
     * @param fachadaAplicacion Referencia á fachada da parte de aplicación.
     * @param controllerPrincipal Referencia ao controlador da ventá principal.
     */
    public vInsercionTipoActividadeController(FachadaAplicacion fachadaAplicacion, vPrincipalController controllerPrincipal){
        //Chamamos ao constructor da clase pai.
        super(fachadaAplicacion);
        //Asociamos o controlador principal ao atributo que lle corresponde:
        this.controllerPrincipal = controllerPrincipal;
    }

    /**
     * Método que se executará ao amosar esta pantalla, co que inicializala.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //A inicialización pode darse de dous xeitos: que se teña un tipo de actividade ou que sexa un null.
        if(tipoActividade == null){
            AuxGUI.ocultarCampos(caixaCodigo, btnBorrar); //Ocultamos a parte que amosa o código e o borrado.
            //Vaciamos os campos de nome e código:
            AuxGUI.vaciarCamposTexto(campoNome,campoCodigo);
        } else {
            //Se hai un tipo de actividade, entón amosarase a súa información.
            //Facemos visible a parte do código e do borrado:
            AuxGUI.amosarCampos(caixaCodigo, btnBorrar);
            campoCodigo.setText(tipoActividade.getCodTipoActividade().toString());
            campoNome.setText(tipoActividade.getNome());
            campoDescricion.setText(tipoActividade.getDescricion());
        }
    }

    /**
     * Método que se executará ao premer no botón de retorno.
     * @param actionEvent A acción que tivo lugar.
     */
    public void btnVolverAction(ActionEvent actionEvent) {
        //Nese caso, pásase á venta de administración:
        controllerPrincipal.mostrarPantalla(IdPantalla.ADMINISTRARTIPOSACTIVIDADES);
    }

    /**
     * Método que se executará ao premer no botón de gardado dun tipo de actividade.
     * @param actionEvent A acción que tivo lugar.
     */
    public void btnGardarAction(ActionEvent actionEvent) {
        //Antes de nada, hai que verificar que o nome esté cuberto:
        if (!ValidacionDatos.estanCubertosCampos(campoNome)) {
            //Como só é un campo, o que faremos e avisar de que hai que insertalo, non amosamos unha etiqueta coma
            //no caso das instalacións.
            super.getFachadaAplicacion().mostrarErro("Administración de Tipos de Actividades", "Hai que insertar un nome!");
            return;
        }

        //Poden ocorrer dúas cousas: que haxa que insertar un novo tipo ou modificalo.
        if(tipoActividade != null) {
            //Se o tipo de actividade non é nulo, é unha modificación. Como a descrición pode ser nula, faise a
            //actualización directamente.
            tipoActividade.setDescricion(campoDescricion.getText());
            //Engadimos tamén o nome actualizado:
            tipoActividade.setNome(campoNome.getText());
            //Intentaremos entón modificar o tipo de actividade:
            try {
                //Executamos a operación e avaliamos o resultado obtido:
                TipoResultados res = this.getFachadaAplicacion().modificarTipoActividade(tipoActividade);
                switch(res){
                    case correcto:
                        //Se rematou o método sen problema, entón amósase confirmación:
                        this.getFachadaAplicacion().mostrarInformacion("Administración de Tipos de Actividades",
                                "Tipo de actividade con id = " + tipoActividade.getCodTipoActividade() + " modificado correctamente.");
                        break;
                    case datoExiste:
                        //Se xa existía outro tipo de actividade co nome que se quería poñer, avísase do erro:
                        //Mostramos un erro:
                        this.getFachadaAplicacion().mostrarErro("Administración de Tipos de Actividades",
                                "Non se puido modificar a base de datos: xa existe un tipo de actividade de nome '" +
                                        tipoActividade.getNome().toLowerCase() + "'.");
                        break;
                }
                //Non saímos por se se queren facer outras tarefas, senón que actualizamos:
                actualizarCamposTAct();
            } catch (ExcepcionBD excepcionBD) {
                //En caso de obter unha excepción da base de datos, amosamos o erro producido:
                getFachadaAplicacion().mostrarErro("Administración de Tipos de Actividades", excepcionBD.getMessage());
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
                        //Mostramos un erro en caso de que xa exista un tipo de actividade co mesmo nome:
                        this.getFachadaAplicacion().mostrarErro("Administración de Tipos de Actividades",
                                "Xa existe un tipo de actividade de nome '" + tipoActividade.getNome().toLowerCase() + "'.");
                        break;
                    case correcto:
                        //Amosamos unha confirmación en caso de que se lograra introducir:
                        this.getFachadaAplicacion().mostrarInformacion("Administración de Tipos de Actividades",
                                "Introducido o tipo de actividade, o seu ID é " + tipoActividade.getCodTipoActividade() + ".");
                        //Finalmente, sáese unha vez rematado o proceso de inserción. Iremos á pantalla de administración directamente:
                        controllerPrincipal.mostrarPantalla(IdPantalla.ADMINISTRARTIPOSACTIVIDADES);
                        break;
                }
            } catch (ExcepcionBD excepcionBD) {
                //En caso de recibir unha excepción da base de datos, amosaremos a mensaxe asociada ao erro producido:
                getFachadaAplicacion().mostrarErro("Administración de Tipos de Actividades", excepcionBD.getMessage());
            }
        }

    }

    /**
     * Método que se executará ao premer no botón de borrado dun tipo de actividade.
     * @param actionEvent A acción que tivo lugar.
     */
    public void btnBorrarAction(ActionEvent actionEvent) {
        //Se se puido presionar, é porque se quere borrar o tipo de actividade presentado.
        //Por iso, pediremos unha confirmación adicional do usuario.
        if(super.getFachadaAplicacion().mostrarConfirmacion("Administración de Tipos de Actividades",
                "Desexa eliminar o tipo de actividade seleccionado?") == ButtonType.OK) {
            //Se así o quere facer o usuario, tentaremos o borrado do tipo:
            try {
                TipoResultados res = this.getFachadaAplicacion().eliminarTipoActividade(tipoActividade);
                //En función do resultado, diferentes opcións:
                switch(res){
                    case correcto:
                        //Amosamos unha mensaxe de confirmación:
                        this.getFachadaAplicacion().mostrarInformacion("Administración de Tipos de Actividades",
                                "Eliminación correcta.");
                        //Volvese á ventá anterior:
                        controllerPrincipal.mostrarPantalla(IdPantalla.ADMINISTRARTIPOSACTIVIDADES);
                        break;
                    case referenciaRestrict:
                        //Amosamos mensaxe de erro:
                        this.getFachadaAplicacion().mostrarErro("Administración de Tipos de Actividades",
                                "O tipo '" + tipoActividade.getNome() + "' ten actividades asociadas! Non se pode borrar.");
                        break;
                }
            } catch (ExcepcionBD excepcionBD) {
                getFachadaAplicacion().mostrarErro("Administración de Tipos de Actividades", excepcionBD.getMessage());
            }
        }
    }

    /**
     * Método que se executará ao premer o botón de limpar os campos da pantalla.
     * @param actionEvent A acción que tivo lugar.
     */
    public void btnRestaurarAction(ActionEvent actionEvent) {
        //Dúas opcións, cando estamos insertando un novo tipo de actividade e cando o modificamos:
        if(tipoActividade != null){
            //Se estamos modificando (tipo de actividade non nulo) actualizamos os campos, non os borramos:
            actualizarCamposTAct();
        } else {
            //Noutro caso, estaremos insertando: vaciaremos os campos:
            AuxGUI.vaciarCamposTexto(campoNome,campoDescricion);
        }
    }

    /**
     * Método que nos permite actualizar a información dos campos do tipo de actividade asociado.
     */
    private void actualizarCamposTAct(){
        //Volvemos a consultar o tipo de actividade:
        tipoActividade = super.getFachadaAplicacion().consultarTipoActividade(tipoActividade);
        //Ao rematar, consultamos se houbo resultado ou non:
        if(tipoActividade != null){
            //Se hai resultado, completamos os campos:
            campoCodigo.setText(tipoActividade.getCodTipoActividade().toString());
            campoNome.setText(tipoActividade.getNome());
            campoDescricion.setText(tipoActividade.getDescricion());
        } else {
            //Noutro caso, amosarase un erro e sairemos:
            super.getFachadaAplicacion().mostrarErro("Administración de Tipos de Actividades",
                    "Este tipo de actividade xa non existe. Saíndo da ventá.");
            //Volvemos para a pantalla de administración de tipos de actividades:
            this.controllerPrincipal.mostrarPantalla(IdPantalla.ADMINISTRARTIPOSACTIVIDADES);
        }
    }


    /**
     * Setter para o tipo de actividade asociado á pantalla.
     * @param tipoActividade O tipo de actividade a asignar ao atributo do controlador.
     */
    public void setTipoActividade(TipoActividade tipoActividade){
        this.tipoActividade = tipoActividade;
    }

}

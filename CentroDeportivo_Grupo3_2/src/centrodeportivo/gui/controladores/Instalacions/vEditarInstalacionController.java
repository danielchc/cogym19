package centrodeportivo.gui.controladores.Instalacions;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.aplicacion.obxectos.tipos.TipoResultados;
import centrodeportivo.funcionsAux.ValidacionDatos;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.Areas.vAdministrarAreaController;
import centrodeportivo.gui.controladores.Areas.vNovaAreaController;
import centrodeportivo.gui.controladores.AuxGUI;
import centrodeportivo.gui.controladores.principal.IdPantalla;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Víctor Barreiro
 * Clase que representa o controlador da ventá de edición dunha instalación.
 */
public class vEditarInstalacionController extends AbstractController implements Initializable {
    /**
     * Atributos públicos: elementos da interface gráfica:
     */
    public TextField campoNome;
    public TextField campoDireccion;
    public TextField campoTelefono;
    public TextField campoCodigo;
    public Button btnModificar;
    public Button btnBorrar;
    public Button btnEngadirArea;
    public Button btnAdministrarAreas;
    public Button btnVolver;
    public Label etiquetaAviso;

    /**
     * Atributos privados: neste caso teremos un máis ca o controlador da ventá principal.
     */
    private vPrincipalController controllerPrincipal; //Controlador ventá principal.
    private Instalacion instalacion; //Instalacion de referencia para a ventá, da que presentaremos os datos.

    /**
     * Constructor do controlador da ventá de edición dunha instalación:
     * @param fachadaAplicacion A referencia á fachada da parte de aplicación.
     * @param controllerPrincipal A referencia do controlador principal.
     */
    public vEditarInstalacionController(FachadaAplicacion fachadaAplicacion, vPrincipalController controllerPrincipal){
        //Asignamos os atributos pasados, un ao constructor da clase pai:
        super(fachadaAplicacion);
        //O outro é propio desta clase:
        this.controllerPrincipal = controllerPrincipal;
    }

    /**
     * Sobreescritura do método initialize, por implementar a interface initializable. É o método que se executa cando
     * se abre a ventá.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        //Para inicializar, comprobaremos se hai unha instalación:
        if(instalacion != null){
            //Antes de nada o que faremos será consultar de novo a instalación, por se tivo cambios:
            actualizarCamposInstalacion();
        } else {
            //Noutro caso, informaríamos dun erro e sairíamos:
            this.getFachadaAplicacion().mostrarErro("Administración de Instalacións",
                    "Non se detectou ningunha instalación... Saíndo.");
            this.controllerPrincipal.mostrarPantalla(IdPantalla.ADMINISTRARINSTALACIONS);
        }
    }


    /**
     * Setter da instalación a configurar
     * @param instalacion A instalación que se vai a querer asociar á pantalla.
     */
    public void setInstalacion(Instalacion instalacion){
        this.instalacion = instalacion;
    }

    /**
     * Acción efectuada ao premer o botón de modificar unha instalación.
     * @param actionEvent O evento que tivo lugar.
     */
    public void btnModificarAction(ActionEvent actionEvent) {
        //Cando se modifica unha instalación, hai que comprobar primeiro que os campos sexan correctos.
        //Empezamos comprobando que os campos non estén baleiros.
        if(!ValidacionDatos.estanCubertosCampos(campoNome, campoDireccion, campoTelefono)){
            //Amosaremos unha mensaxe avisando de que non se cubriron todos os campos.
            AuxGUI.amosarCampos(etiquetaAviso);
            return;
        }
        //Comprobamos agora que o número de teléfono sexa correcto:
        if(!ValidacionDatos.isCorrectoTelefono(campoTelefono.getText())){
            //Amosaremos unha mensaxe de erro.
            super.getFachadaAplicacion().mostrarErro("Adiministración de Instalacións",
                    "O número de teléfono é incorrecto!");
            return;
        }

        //Chegados a este punto haberá que ir á fachada de aplicación:
        //Creamos unha nova instalación para acceder con ela á base de datos e tentar facer a modificación:
        Instalacion instalacion = new Instalacion(this.instalacion.getCodInstalacion(), campoNome.getText(),
                campoTelefono.getText(), campoDireccion.getText());
        //Accedemos á base de datos:
        //Tentaremos realizar o acceso correctamente, xestionando as excepcións:
        try {
            TipoResultados res = super.getFachadaAplicacion().modificarInstalacion(instalacion);
            //En función do resultado procedemos:
            switch(res){
                case datoExiste:
                    //Se xa existía outra instalación co mesmo nome, avísase do problema.
                    super.getFachadaAplicacion().mostrarErro("Administración de Instalacións",
                            "Xa hai outra instalación co nome '" + instalacion.getNome() + "'!");
                    break;
                case correcto:
                    //Se rematou correctamente, mostramos unha mensaxe de confirmación:
                    super.getFachadaAplicacion().mostrarInformacion("Administración de Instalacións",
                            "Datos da instalación " + instalacion.getCodInstalacion() + " modificados correctamente." );
                    break;
            }
        } catch (ExcepcionBD e){
            //Se hai un erro na base de datos, amósase a mensaxe,
            //que é creada na nosa excepción con getMessage():
            super.getFachadaAplicacion().mostrarErro("Administración de Instalacións", e.getMessage());
        }
        //En calquera caso, tentamos actualizar os campos amosados:
        actualizarCamposInstalacion();
    }

    /**
     * Acción efectuada ao premer o botón de borrado dunha instalación.
     * @param actionEvent O evento que tivo lugar.
     */
    public void btnBorrarAction(ActionEvent actionEvent) {
        //Cando se pide borrar, primeiro solicitarase a confirmación por parte do usuario.
        if(super.getFachadaAplicacion().mostrarConfirmacion("Administración de Instalacións",
                "Desexa eliminar a instalación seleccionada?") == ButtonType.OK) {
            //Intentamos levar a cabo o borrado da instalación:
            try{
                TipoResultados res = super.getFachadaAplicacion().borrarInstalacion(instalacion);
                //En función do resultado, actuamos:
                switch(res){
                    case referenciaRestrict:
                        //En caso de ter áreas asociadas devólvese este enumerado, polo que imprimimos un erro explicando
                        //o problema.
                        super.getFachadaAplicacion().mostrarErro("Administración de Instalacións",
                                "A instalación non se pode borrar, dado que ten áreas asociadas!");
                        break;
                    case correcto:
                        //En caso de borrado correcto, confírmase o resultado:
                        super.getFachadaAplicacion().mostrarInformacion("Administración de Instalacións",
                                "Instalación borrada correctamente.");
                        //Se se logrou borrar, logo haberá que amosar de novo a pantalla anterior
                        //(a xestión desa instalación deixa de ter sentido).
                        controllerPrincipal.mostrarPantalla(IdPantalla.ADMINISTRARINSTALACIONS);
                        break;
                }
            } catch(ExcepcionBD e){
                //No caso de termos outra excepción da base de datos, haberá que amosala.
                //A mensaxe xestiónase a través do método getMessage:
                super.getFachadaAplicacion().mostrarErro("Administración de Instalacións", e.getMessage());
            }
        }
    }

    /**
     * Acción efectuada ao premer o botón de engadido dunha área.
     * @param actionEvent O evento que tivo lugar.
     */
    public void btnEngadirAreaAction(ActionEvent actionEvent) {
        //Poderemos abrir a pantalla de engadido dunha area:
        ((vNovaAreaController) controllerPrincipal.getControlador(IdPantalla.NOVAAREA)).setInstalacion(this.instalacion);
        //Abrimos a ventá de nova área:
        controllerPrincipal.mostrarPantalla(IdPantalla.NOVAAREA);
    }

    /**
     * Acción efectuada ao premer o botón de administración das áreas desa instalación.
     * @param actionEvent O evento que tivo lugar.
     */
    public void btnAdministrarAreasAction(ActionEvent actionEvent) {
        //Poderemos abrir a pantalla onde amosamos as distintas areas:
        ((vAdministrarAreaController) controllerPrincipal.getControlador(IdPantalla.ADMINAREA)).setInstalacion(this.instalacion);
        //Abrimos a ventá das areas:
        controllerPrincipal.mostrarPantalla(IdPantalla.ADMINAREA);
    }

    /**
     * Acción efectuada ao premer o botón de regreso.
     * @param actionEvent O evento que tivo lugar.
     */
    public void btnVolverAction(ActionEvent actionEvent) {
        //Se se pulsa o botón volver, amosarase de novo a pantalla de administrar instalacións:
        this.controllerPrincipal.mostrarPantalla(IdPantalla.ADMINISTRARINSTALACIONS);
    }

    /**
     * Método que realiza a consulta da instalación que se está a considerar e actualiza os seus campos.
     */
    private void actualizarCamposInstalacion(){
        //Consultamos a instalación:
        instalacion = getFachadaAplicacion().consultarInstalacion(instalacion);
        //Volvemos a validar que está rexistrada a instalación:
        if(instalacion != null){
            //Se a hai, enchemos os campos coa información:
            campoNome.setText(instalacion.getNome());
            campoDireccion.setText(instalacion.getDireccion());
            campoCodigo.setText(instalacion.getCodInstalacion().toString());
            campoTelefono.setText(instalacion.getNumTelefono());
        } else {
            //Noutro caso amosase un erro e sairíamos tamén:
            this.getFachadaAplicacion().mostrarErro("Administración de Instalacións",
                    "A instalación que se pediu xestionar non está na base de datos. Saíndo...");
            this.controllerPrincipal.mostrarPantalla(IdPantalla.ADMINISTRARINSTALACIONS);
        }
    }
}

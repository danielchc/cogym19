package centrodeportivo.gui.controladores.Instalacions;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.aplicacion.obxectos.tipos.TipoResultados;
import centrodeportivo.funcionsAux.ValidacionDatos;
import centrodeportivo.gui.controladores.AbstractController;
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

public class vEditarInstalacionController extends AbstractController implements Initializable {
    //Atributos públicos: elementos da interface.
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


    //Atributos privados:
    private vPrincipalController controllerPrincipal; //Controlador ventá principal.
    private Instalacion instalacion; //Instalacion de referencia que se vai a querer modificar.

    //Constructor:
    public vEditarInstalacionController(FachadaAplicacion fachadaAplicacion, vPrincipalController controllerPrincipal){
        //Asignamos os atributos pasados:
        super(fachadaAplicacion);
        this.controllerPrincipal = controllerPrincipal;
    }

    //Sobreescritura do método initialize, por implementar a interface initializable:
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        //Para inicializar, comprobaremos se hai unha instalación:
        if(instalacion != null){
            //Se a hai, enchemos os campos coa información:
            campoNome.setText(instalacion.getNome());
            campoDireccion.setText(instalacion.getDireccion());
            campoCodigo.setText(""+instalacion.getCodInstalacion());
            campoTelefono.setText(instalacion.getNumTelefono());
        }
    }

    //Definimos getter e setter para a instalación:
    public void setInstalacion(Instalacion instalacion){
        this.instalacion = instalacion;
    }
    
    public Instalacion getInstalacion(Instalacion instalacion){
        return this.instalacion;
    }

    public void btnModificarAction(ActionEvent actionEvent) {
        //Cando se modifica unha instalación, hai que comprobar primeiro que os campos sexan correctos.
        //Empezamos comprobando que os campos non estén baleiros.
        if(!ValidacionDatos.estanCubertosCampos(campoNome, campoDireccion, campoTelefono)){
            //Amosaremos unha mensaxe avisando de que non se cubriron todos os campos.
            etiquetaAviso.setVisible(true);
            return;
        }
        //Comprobamos agora que o número de teléfono sexa correcto:
        if(!ValidacionDatos.isCorrectoTelefono(campoTelefono.getText())){
            //Amosaremos unha mensaxe de erro.
            super.getFachadaAplicacion().mostrarErro("Adiministración de Instalacións", "O número de teléfono é incorrecto!");
            return;
        }

        //Chegados a este punto haberá que ir á fachada de aplicación:
        //Modificamos a instalación existente:
        Instalacion instalacion = new Instalacion(this.instalacion.getCodInstalacion(), campoNome.getText(),
                campoTelefono.getText(), campoDireccion.getText());
        //Accedemos á base de datos:
        //Tentaremos realizar o acceso correctamente, xestionando as excepcións:
        try {
            TipoResultados res = super.getFachadaAplicacion().modificarInstalacion(instalacion);
            switch(res){
                case datoExiste:
                    super.getFachadaAplicacion().mostrarErro("Administración de Instalacións",
                            "Xa hai outra instalación co nome '" + instalacion.getNome() + "'!");
                    break;
                case correcto:
                    //Mostramos unha mensaxe de confirmación:
                    super.getFachadaAplicacion().mostrarInformacion("Administración de Instalacións", "Datos da instalación "
                            + instalacion.getCodInstalacion() + " modificados correctamente." );
                    break;
            }
        } catch (ExcepcionBD e){
            //Se hai un erro na base de datos, amósase a mensaxe,
            //que é creada na nosa excepción con getMessage():
            super.getFachadaAplicacion().mostrarErro("Administración de Instalacións", e.getMessage());
        }
    }

    public void btnBorrarAction(ActionEvent actionEvent) {
        //Cando se pide borrar, primeiro solicitarase a confirmación por parte do usuario.
        if(super.getFachadaAplicacion().mostrarConfirmacion("Administración de Instalacións",
                "Desexa eliminar a instalación seleccionada?") == ButtonType.OK) {
            //Intentamos levar a cabo o borrado da instalación:
            try{
                TipoResultados res = super.getFachadaAplicacion().borrarInstalacion(instalacion);
                switch(res){
                    case referenciaRestrict:
                        super.getFachadaAplicacion().mostrarErro("Administración de Instalacións",
                                "A instalación non se pode borrar, dado que ten áreas asociadas!");
                        break;
                    case correcto:
                        super.getFachadaAplicacion().mostrarInformacion("Administración de Instalacións",
                                "Instalación borrada correctamente.");
                        //Se se logrou borrar, logo haberá que amosar de novo a pantalla anterior (a xestión desa instalación deixa de ter sentido).
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
    
    public void btnEngadirAreaAction(ActionEvent actionEvent) {
    }

    public void btnAdministrarAreasAction(ActionEvent actionEvent) {
    }

    public void btnVolverAction(ActionEvent actionEvent) {
        //Se se pulsa o botón volver, amosarase de novo a pantalla de administrar instalacións:
        this.controllerPrincipal.mostrarPantalla(IdPantalla.ADMINISTRARINSTALACIONS);
    }
}

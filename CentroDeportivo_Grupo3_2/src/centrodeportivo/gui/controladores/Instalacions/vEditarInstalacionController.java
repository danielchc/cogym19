package centrodeportivo.gui.controladores.Instalacions;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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


    //Atributos privados:
    private vPrincipalController controllerPrincipal; //Controlador ventá principal.
    private Instalacion instalacion; //Instalacion de referencia que se vai a querer modificar.

    //Constructor:
    public vEditarInstalacionController(FachadaAplicacion fachadaAplicacion, vPrincipalController controllerPrincipal){
        //Asignamos os atributos pasados:
        super(fachadaAplicacion);
        this.controllerPrincipal = controllerPrincipal;
        this.instalacion = instalacion;
    }

    //Sobreescritura do método initialize, por implementar a interface initializable:
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        //Vamos a inicializar los campos:
    }

    //Definimos getter e setter para a instalación:
    public void setInstalacion(Instalacion instalacion){
        this.instalacion = instalacion;
    }
    
    public Instalacion getInstalacion(Instalacion instalacion){
        return this.instalacion;
    }

    public void btnModificarAction(ActionEvent actionEvent) {
    }

    public void btnBorrarAction(ActionEvent actionEvent) {
    }
    
    public void btnEngadirAreaAction(ActionEvent actionEvent) {
    }

    public void btnAdministrarAreasAction(ActionEvent actionEvent) {
    }
}

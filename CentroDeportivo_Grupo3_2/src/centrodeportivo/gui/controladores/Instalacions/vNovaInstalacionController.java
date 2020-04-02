package centrodeportivo.gui.controladores.Instalacions;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.gui.controladores.AbstractController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import centrodeportivo.gui.controladores.principal.vPrincipalController;

import java.net.URL;
import java.util.ResourceBundle;

public class vNovaInstalacionController extends AbstractController implements Initializable {
    //Atributos públicos - trátase dos campos da interface aos que queremos acceder:
    public Button btnGardar;
    public TextField campoNome;
    public TextField campoNumTlf;
    public TextField campoDireccion;

    //Atributos privados: correspóndense con cuestións necesarias para realizar as diferentes xestións.
    private vPrincipalController controllerPrincipal;

    public vNovaInstalacionController(FachadaAplicacion fachadaAplicacion, vPrincipalController controllerPrincipal){
        super(fachadaAplicacion);
        this.controllerPrincipal = controllerPrincipal;
    }

    public void initialize(URL url, ResourceBundle resourceBundle){
        //Neste caso non é necesario facer nada na inicialización.
    }

    //Acción efectuada ao gardar unha instalación:
    public void btnGardarAction(ActionEvent actionEvent){
        Instalacion instalacion = new Instalacion(campoNome.getText(), campoNumTlf.getText(), campoDireccion.getText());
        //Accedemos á base de datos:
        this.getFachadaAplicacion().darAltaInstalacion(instalacion);
        //Imprimimos mensaxe de éxito:
        this.getFachadaAplicacion().mostrarInformacion("Instalacións", "Creada a instalación " +
                instalacion.getNome() + ". O seu id é: " + instalacion.getCodInstalacion() + ".");
    }


}

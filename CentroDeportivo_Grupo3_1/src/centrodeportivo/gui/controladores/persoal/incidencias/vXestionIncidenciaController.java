package centrodeportivo.gui.controladores.persoal.incidencias;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.incidencias.Incidencia;
import centrodeportivo.aplicacion.obxectos.incidencias.IncidenciaArea;
import centrodeportivo.aplicacion.obxectos.incidencias.IncidenciaMaterial;
import centrodeportivo.funcionsAux.ListenerTextFieldNumeros;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class vXestionIncidenciaController extends AbstractController implements Initializable {


    public TextField campoNumero;
    public TextField campoUsuario;
    public TextArea campoDescricion;
    public DatePicker campoDataIncidencia;
    public TextField campoCusto;
    public DatePicker campoDataResolucion;
    public TextArea campoComentario;
    public TextArea datosIncidencia;
    private Incidencia incidenciaXestionar;

    public vXestionIncidenciaController(FachadaAplicacion fachadaAplicacion, vPrincipalController vPrincipalController) {
        super(fachadaAplicacion, vPrincipalController);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        campoCusto.setOnKeyTyped(new ListenerTextFieldNumeros(campoCusto));
        cargarIncidencia();
    }

    public void cargarIncidencia(){
        System.out.println(incidenciaXestionar);
        String datos="";
        if(incidenciaXestionar instanceof IncidenciaArea){
            datos=String.format(
                    "Data Falla: %s"+
                            "\nTipo Incidencia: Área"+
                            "\nNúmero: %d"+
                            "\nÁrea: %s"+
                            "\nInstalación: TODO"+
                            "\nUsuario incidencia: %s",
                    incidenciaXestionar.getDataFalla(),
                    incidenciaXestionar.getNumero(),
                    ((IncidenciaArea) incidenciaXestionar).getArea(),
                    incidenciaXestionar.getUsuario().getLogin()
            );
        }else if (incidenciaXestionar instanceof IncidenciaMaterial){
            datos=String.format(
                            "Data Falla: %s"+
                            "\nTipo Incidencia: Material"+
                            "\nNúmero: %d"+
                            "\nMaterial: %s %d"+
                            "\nÁrea: %s"+
                            "\nUsuario incidencia: %s",
                    incidenciaXestionar.getDataFalla(),
                    incidenciaXestionar.getNumero(),
                    ((IncidenciaMaterial) incidenciaXestionar).getMaterial().getTipoNombre(),
                    ((IncidenciaMaterial) incidenciaXestionar).getMaterial().getCodMaterial(),
                    ((IncidenciaMaterial) incidenciaXestionar).getMaterial().getArea().getCodArea(),
                    incidenciaXestionar.getUsuario().getLogin()
            );
        }
        this.datosIncidencia.setText(datos);
    }

    public void cargarDatosIncidencia(Incidencia incidencia){
        this.incidenciaXestionar=incidencia;
    }

}

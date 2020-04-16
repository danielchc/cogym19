package centrodeportivo.gui.controladores.persoal.incidencias;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.incidencias.Incidencia;
import centrodeportivo.aplicacion.obxectos.incidencias.IncidenciaArea;
import centrodeportivo.aplicacion.obxectos.incidencias.IncidenciaMaterial;
import centrodeportivo.funcionsAux.ListenerTextFieldNumeros;
import centrodeportivo.funcionsAux.ValidacionDatos;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.principal.IdPantalla;
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


    public TextField campoCusto;
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
        String datos="";
        try {
            incidenciaXestionar=getFachadaAplicacion().consultarIncidencia(incidenciaXestionar);
        } catch (ExcepcionBD excepcionBD) {
            getFachadaAplicacion().mostrarErro("Carga", excepcionBD.getMessage());
        }
        if(incidenciaXestionar instanceof IncidenciaArea){
            datos=String.format(
                    "Data Falla: %s"+
                            "\nTipo Incidencia: Área"+
                            "\nNúmero: %d"+
                            "\nÁrea: %s"+
                            "\nInstalación: %s"+
                            "\nUsuario incidencia: %s",
                    incidenciaXestionar.getDataFalla(),
                    incidenciaXestionar.getNumero(),
                    ((IncidenciaArea) incidenciaXestionar).getArea(),
                    ((IncidenciaArea) incidenciaXestionar).getArea().getNome(),
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
                    ((IncidenciaMaterial) incidenciaXestionar).getMaterial().getArea().getNome(),
                    incidenciaXestionar.getUsuario().getLogin()
            );
        }
        this.datosIncidencia.setText(datos);
        this.campoCusto.setText(String.valueOf(incidenciaXestionar.getCustoReparacion()));
        this.campoComentario.setText(incidenciaXestionar.getComentarioResolucion());
    }

    public void cargarDatosIncidencia(Incidencia incidencia){
        this.incidenciaXestionar=incidencia;
    }

    public void resolverIncidencia(){
        if(!ValidacionDatos.estanCubertosCampos(campoComentario,campoCusto)){
           return;
        }
        incidenciaXestionar.setComentarioResolucion(campoComentario.getText());
        incidenciaXestionar.setCustoReparacion(Float.valueOf(campoCusto.getText()));
        try {
            getFachadaAplicacion().resolverIncidencia(incidenciaXestionar);
            getFachadaAplicacion().mostrarInformacion("Incidencia", "Gardouse a resolución da indicencia, podela editar durante 5 días");
            getvPrincipalController().volverAtras();
        } catch (ExcepcionBD excepcionBD) {
            getFachadaAplicacion().mostrarErro("INCIDENCIA", excepcionBD.getMessage());
        }
    }

    public void volverAdministracion(){
        getvPrincipalController().mostrarMenu(IdPantalla.ADMINISTRARINCIDENCIAS);
    }
}

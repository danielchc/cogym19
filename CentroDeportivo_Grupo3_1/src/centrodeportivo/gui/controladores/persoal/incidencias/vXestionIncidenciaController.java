package centrodeportivo.gui.controladores.persoal.incidencias;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.Mensaxe;
import centrodeportivo.aplicacion.obxectos.incidencias.Incidencia;
import centrodeportivo.aplicacion.obxectos.incidencias.IncidenciaArea;
import centrodeportivo.aplicacion.obxectos.incidencias.IncidenciaMaterial;
import centrodeportivo.funcionsAux.ListenerMaxLogitud;
import centrodeportivo.funcionsAux.ListenerTextFieldNumeros;
import centrodeportivo.funcionsAux.ValidacionDatos;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.principal.IdPantalla;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.sql.Timestamp;
import java.time.chrono.MinguoDate;
import java.util.Date;
import java.util.ResourceBundle;

public class vXestionIncidenciaController extends AbstractController implements Initializable {


    /**
     * Atributos do fxml
     */
    public TextField campoCusto;
    public TextArea campoComentario;
    public TextArea datosIncidencia;
    public Label mensaxeAdvertencia;
    public Button btnResolverIncidencia;

    /**
     * Atributos privados do controlador
     */
    private Incidencia incidenciaXestionar;

    /**
     * Constructor do controlador
     *
     * @param fachadaAplicacion    fachada da apliacación
     * @param vPrincipalController controlador da vista principal
     */
    public vXestionIncidenciaController(FachadaAplicacion fachadaAplicacion, vPrincipalController vPrincipalController) {
        super(fachadaAplicacion, vPrincipalController);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        campoCusto.textProperty().addListener(new ListenerTextFieldNumeros(campoCusto));
        campoComentario.textProperty().addListener(new ListenerMaxLogitud(campoComentario, 500));
        cargarIncidencia();
    }

    public void cargarIncidencia() {
        String datos = "";
        incidenciaXestionar = getFachadaAplicacion().consultarIncidencia(incidenciaXestionar);
        if (incidenciaXestionar instanceof IncidenciaArea) {
            datos = String.format(
                    "Data Falla: %s" +
                            "\nTipo Incidencia: Área" +
                            "\nNúmero: %d" +
                            "\nÁrea: %s" +
                            "\nInstalación: %s" +
                            "\nUsuario incidencia: %s" +
                            "\nDescrición: %s",
                    incidenciaXestionar.getDataFalla(),
                    incidenciaXestionar.getNumero(),
                    ((IncidenciaArea) incidenciaXestionar).getArea(),
                    ((IncidenciaArea) incidenciaXestionar).getArea().getNome(),
                    incidenciaXestionar.getUsuario().getLogin(),
                    incidenciaXestionar.getDescricion()
            );
        } else if (incidenciaXestionar instanceof IncidenciaMaterial) {
            datos = String.format(
                    "Data Falla: %s" +
                            "\nTipo Incidencia: Material" +
                            "\nNúmero: %d" +
                            "\nMaterial: %s %d" +
                            "\nÁrea: %s" +
                            "\nUsuario incidencia: %s" +
                            "\nDescrición: %s",
                    incidenciaXestionar.getDataFalla(),
                    incidenciaXestionar.getNumero(),
                    ((IncidenciaMaterial) incidenciaXestionar).getMaterial().getTipoNombre(),
                    ((IncidenciaMaterial) incidenciaXestionar).getMaterial().getCodMaterial(),
                    ((IncidenciaMaterial) incidenciaXestionar).getMaterial().getArea().getNome(),
                    incidenciaXestionar.getUsuario().getLogin(),
                    incidenciaXestionar.getDescricion()
            );
        }
        if (incidenciaXestionar.estaResolta()) {
            datos += String.format("\nResolta: %s", incidenciaXestionar.getDataResolucion().toString());
            long diff = new Date().getTime() - incidenciaXestionar.getDataResolucion().getTime();
            if (diff > 432000000) {
                System.out.println("Non se pode editar");
                this.campoCusto.setDisable(true);
                this.campoComentario.setDisable(true);
                this.btnResolverIncidencia.setDisable(true);
            } else {
                this.mensaxeAdvertencia.setText("Unha vez resolta a incidencia, so a poderás editar ata 5 días despois");
            }
        } else {
            this.mensaxeAdvertencia.setVisible(false);
        }
        this.datosIncidencia.setText(datos);
        this.campoCusto.setText(String.valueOf(incidenciaXestionar.getCustoReparacion()));
        this.campoComentario.setText(incidenciaXestionar.getComentarioResolucion());
    }

    public void cargarDatosIncidencia(Incidencia incidencia) {
        this.incidenciaXestionar = incidencia;
    }

    public void resolverIncidencia() {
        if (!ValidacionDatos.estanCubertosCampos(campoComentario, campoCusto)) {
            return;
        }
        incidenciaXestionar.setComentarioResolucion(campoComentario.getText());
        incidenciaXestionar.setCustoReparacion(Float.valueOf(campoCusto.getText()));
        try {
            ButtonType resposta = super.getFachadaAplicacion().mostrarConfirmacion("Incidencia", "Desexa notificar ao usuario " + incidenciaXestionar.getUsuario().getLogin() + " da resolución?");
            getFachadaAplicacion().resolverIncidencia(incidenciaXestionar);
            if(resposta==ButtonType.OK) {
                super.getFachadaAplicacion().enviarMensaxe(new Mensaxe(
                        super.getvPrincipalController().obterUsuarioLogeado(),
                        incidenciaXestionar.getUsuario(),
                        "Resolveuse a incidencia "+incidenciaXestionar.getNumero()+" presentada por vostede."
                ));
            }
            getFachadaAplicacion().mostrarInformacion("Incidencia", "Gardouse a resolución da indicencia, podela editar durante 5 días");
            getvPrincipalController().volverAtras();
        } catch (ExcepcionBD excepcionBD) {
            getFachadaAplicacion().mostrarErro("INCIDENCIA", excepcionBD.getMessage());
        }
    }

    public void volverAdministracion() {
        getvPrincipalController().mostrarMenu(IdPantalla.ADMINISTRARINCIDENCIAS);
    }
}

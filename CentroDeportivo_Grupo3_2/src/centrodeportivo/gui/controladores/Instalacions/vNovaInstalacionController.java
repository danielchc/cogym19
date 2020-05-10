package centrodeportivo.gui.controladores.Instalacions;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.aplicacion.obxectos.tipos.TipoResultados;
import centrodeportivo.funcionsAux.ValidacionDatos;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.AuxGUI;
import centrodeportivo.gui.controladores.principal.IdPantalla;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import centrodeportivo.gui.controladores.principal.vPrincipalController;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Víctor Barreiro
 * Esta clase funcionará como controlador da ventá de inserción dos datos dunha nova instalación.
 */
public class vNovaInstalacionController extends AbstractController implements Initializable {
    /**
     * Atributos públicos - trátase dos campos da interface aos que queremos acceder:
     */
    public Button btnGardar;
    public Button btnLimpar;
    public TextField campoNome;
    public TextField campoNumTlf;
    public TextField campoDireccion;
    public Label avisoCampos;

    /**
     * Atributos privados: correspóndense con outras cuestións necesarias para certas xestións.
     */
    private vPrincipalController controllerPrincipal; //Referencia ao controlador da ventá principal.

    /**
     * Constructor do controlador da pantalla de nova instalación:
     *
     * @param fachadaAplicacion   A referencia á fachada da parte de aplicación.
     * @param controllerPrincipal A referencia ao controlador da ventá principal.
     */
    public vNovaInstalacionController(FachadaAplicacion fachadaAplicacion, vPrincipalController controllerPrincipal) {
        super(fachadaAplicacion);
        this.controllerPrincipal = controllerPrincipal;
    }

    /**
     * Método initialize, que se executa cada vez que se abre a ventá-
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Neste caso non é necesario facer nada na inicialización.
        //Polo tanto, deixámolo oculto.
    }

    /**
     * Método que representa as accións realizadas ao premer o botón de gardado da instalación.
     *
     * @param actionEvent A acción que tivo lugar.
     */
    public void btnGardarAction(ActionEvent actionEvent) {
        //Primeiro imos comprobar que os campos non están vacíos:
        if (!ValidacionDatos.estanCubertosCampos(campoNome, campoNumTlf, campoDireccion)) {
            //Se hai campos non cubertos amósase unha mensaxe e non se fai nada máis.
            //Amosamos a mensaxe de erro:
            avisoCampos.setText("Campos Obrigatorios *!!!");
            AuxGUI.amosarCampos(avisoCampos);
            return;
        }

        //Agora imos validar que o teléfono introducido se corresponda con algo correcto:
        if (!ValidacionDatos.isCorrectoTelefono(campoNumTlf.getText())) {
            //O mesmo que no caso dos campos vacíos: avisamos do erro e non se fai nada máis:
            this.getFachadaAplicacion().mostrarErro("Instalacións", "O número de teléfono é incorrecto!");
            return;
        }

        //Comprobamos que o nome da instalación e a dirección teñan a lonxitude necesaria.
        if (campoNome.getText().length() > 50 || campoDireccion.getText().length() > 200) {
            avisoCampos.setText("Lonxitudes incorrectas!");
            AuxGUI.amosarCampos(avisoCampos);
            return;
        }

        //Creamos un obxecto instalación con todos os datos facilitados
        Instalacion instalacion = new Instalacion(campoNome.getText(), campoNumTlf.getText(), campoDireccion.getText());
        //Accedemos á base de datos: intentamos que se efectúe sen problemas dito acceso.
        try {
            //A consulta pódenos devolver varios resultados en función da situación. Avaliámolos:
            TipoResultados res = this.getFachadaAplicacion().darAltaInstalacion(instalacion);
            //En función do resultado, mostraremos unha mensaxe ou outra:
            switch (res) {
                case correcto:
                    //Correcto -> Imprimimos mensaxe de éxito co ID da instalación insertada:
                    this.getFachadaAplicacion().mostrarInformacion("Instalacións",
                            "Creada a instalación " + instalacion.getNome() +
                                    ". O seu id é: " + instalacion.getCodInstalacion() + ".");
                    //Volvemos á pantalla principal:
                    this.controllerPrincipal.mostrarPantalla(IdPantalla.INICIO);
                    break;
                case datoExiste:
                    //Se xa existía unha instalación co nome pasado, entón imprímese un erro e séguese na pantalla.
                    this.getFachadaAplicacion().mostrarErro("Administración de Instalacións",
                            "Xa hai unha instalación co nome '" + instalacion.getNome().toLowerCase() + "'.");
                    break;
            }
        } catch (ExcepcionBD e) {
            //Se se recibe unha excepción da base de datos, entón imprímese unha mensaxe informando.
            //Esa mensaxe obtense dentro da excepción, co método getMessage():
            this.getFachadaAplicacion().mostrarErro("Administración de Instalacións", e.getMessage());
        }
        //Se houbo algún erro, seguirase nesta pantalla.
    }

    /**
     * Método que representa as accións realizadas ao premer o botón de limpado de campos.
     *
     * @param actionEvent A acción que tivo lugar
     */
    public void btnLimparAction(ActionEvent actionEvent) {
        //O que imos a facer e limpar os tres campos, vaciar o que teñan:
        AuxGUI.vaciarCamposTexto(campoNome, campoDireccion, campoNumTlf);
        //Ao mesmo tempo, ocultaremos o campo de aviso de incoherencias, por se apareceu:
        AuxGUI.ocultarCampos(avisoCampos);
    }

}

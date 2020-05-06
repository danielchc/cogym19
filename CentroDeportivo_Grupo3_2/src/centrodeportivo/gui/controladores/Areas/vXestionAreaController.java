package centrodeportivo.gui.controladores.Areas;


import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.aplicacion.obxectos.tipos.TipoResultados;
import centrodeportivo.funcionsAux.ValidacionDatos;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.AuxGUI;
import centrodeportivo.gui.controladores.principal.IdPantalla;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import centrodeportivo.gui.controladores.principal.vPrincipalController;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Víctor Barreiro
 * Esta clase funcionará como controlador da ventá de inserción dos datos dunha nova area.
 */
public class vXestionAreaController extends AbstractController implements Initializable {
    /**
     * Atributos públicos - trátase dos campos da interface aos que queremos acceder:
     */
    public Button btnGardar;
    public Button btnLimpar;
    public Button btnVolver;
    public TextField campoNome;
    public TextArea campoDescricion;
    public Label avisoCampos;
    public TextField campoCodigo;
    public TextField campoAforoMax;

    /**
     * Atributos privados: correspóndense con outras cuestións necesarias para certas xestións.
     */
    private vPrincipalController controllerPrincipal; //Referencia ao controlador da ventá principal.
    private Instalacion instalacion;

    /**
     * Constructor do controlador da pantalla de nova area:
     * @param fachadaAplicacion A referencia á fachada da parte de aplicación.
     * @param controllerPrincipal A referencia ao controlador da ventá principal.
     */
    public vXestionAreaController(FachadaAplicacion fachadaAplicacion, vPrincipalController controllerPrincipal) {
        super(fachadaAplicacion);
        this.controllerPrincipal = controllerPrincipal;
    }

    /**
     * Método initialize, que se executa cada vez que se abre a ventá-
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Neste caso non é necesario facer nada na inicialización.
        //Polo tanto, deixámolo oculto.
        campoAforoMax.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (!t1.matches("\\d{0,4}")) {
                    campoAforoMax.setText(s);
                }
            }
        });
    }

    public void setInstalacion(Instalacion instalacion) {
        this.instalacion = instalacion;
    }

    /**
     * Método que representa as accións realizadas ao premer o botón de gardado da area.
     * @param actionEvent A acción que tivo lugar.
     */
    public void btnGardarAction(ActionEvent actionEvent) {
        //Primeiro imos comprobar que os campos non están vacíos:
        if (!ValidacionDatos.estanCubertosCampos(campoNome, campoDescricion, campoAforoMax)) {
            //Se hai campos non cubertos amósase unha mensaxe e non se fai nada máis.
            //Amosamos a mensaxe de erro:
            AuxGUI.amosarCampos(avisoCampos);
            return;
        }

        //Agora imos validar que o teléfono introducido se corresponda con algo correcto:
        if (Integer.parseInt(campoAforoMax.getText()) < 1) {
            //O mesmo que no caso dos campos vacíos: avisamos do erro e non se fai nada máis:
            this.getFachadaAplicacion().mostrarErro("Area", "O valor de aforomáximo é incorrecto!");
            return;
        }

        //Creamos un obxecto Area con todos os datos facilitados
        Area area = new Area(instalacion, campoNome.getText(), campoDescricion.getText(), Integer.parseInt(campoAforoMax.getText()));

        //Accedemos á base de datos: intentamos que se efectúe sen problemas dito acceso.
        try {
            //A consulta pódenos devolver varios resultados en función da situación. Avaliámolos:
            TipoResultados res = this.getFachadaAplicacion().EngadirArea(area);
            //En función do resultado, mostraremos unha mensaxe ou outra:
            switch (res) {
                case correcto:
                    //Correcto -> Imprimimos mensaxe de éxito co ID da instalación insertada:
                    this.getFachadaAplicacion().mostrarInformacion("Administración de Áreas",
                            "Creada a Area " + area.getNome() +
                                    ". O seu id de Area é: " + area.getCodArea() +
                                    ". O seu id de Instalación é: " + area.getInstalacion().getCodInstalacion() + ".");
                    //Volvemos á pantalla principal:
                    this.controllerPrincipal.mostrarPantalla(IdPantalla.INICIO);
                    break;
                case datoExiste:
                    //Se xa existía unha instalación co nome pasado, entón imprímese un erro e séguese na pantalla.
                    this.getFachadaAplicacion().mostrarErro("Administración de Areas",
                            "Xa hai esta area co nome '" + area.getNome() +" na instalación "+ area.getInstalacion().getCodInstalacion()+ "'.");
                    break;
            }
        } catch (ExcepcionBD e) {
            //Se se recibe unha excepción da base de datos, entón imprímese unha mensaxe informando.
            //Esa mensaxe obtense dentro da excepción, co método getMessage():
            this.getFachadaAplicacion().mostrarErro("Administración de Areas", e.getMessage());
        }
        //Se houbo algún erro, seguirase nesta pantalla.
    }

    /**
     * Método que representa as accións realizadas ao premer o botón de limpado de campos.
     * @param actionEvent A acción que tivo lugar
     */
    public void btnLimparAction(ActionEvent actionEvent) {
        //O que imos a facer e limpar os tres campos, vaciar o que teñan:
        AuxGUI.vaciarCamposTexto(campoNome, campoDescricion, campoAforoMax);
        //Ao mesmo tempo, ocultaremos o campo de aviso de incoherencias, por se apareceu:
        AuxGUI.ocultarCampos(avisoCampos);
    }

    /**
     * Método que representa as accións realizadas ao premer o botón de retorno.
     * @param actionEvent A acción que tivo lugar.
     */
    public void btnVolverAction(ActionEvent actionEvent) {
        //Volvemos á ventá da edición da instalación.
        controllerPrincipal.mostrarPantalla(IdPantalla.EDITARINSTALACION);
    }
}

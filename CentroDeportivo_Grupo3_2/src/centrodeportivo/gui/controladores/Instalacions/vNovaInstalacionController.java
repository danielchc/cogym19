package centrodeportivo.gui.controladores.Instalacions;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.aplicacion.obxectos.tipos.TipoResultados;
import centrodeportivo.funcionsAux.ValidacionDatos;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.principal.IdPantalla;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import centrodeportivo.gui.controladores.principal.vPrincipalController;

import java.net.URL;
import java.util.ResourceBundle;

public class vNovaInstalacionController extends AbstractController implements Initializable {
    //Atributos públicos - trátase dos campos da interface aos que queremos acceder:
    public Button btnGardar;
    public Button btnLimpar;
    public TextField campoNome;
    public TextField campoNumTlf;
    public TextField campoDireccion;
    public Label avisoCampos;

    //Atributos privados: correspóndense con cuestións necesarias para realizar as diferentes xestións.
    private vPrincipalController controllerPrincipal;

    public vNovaInstalacionController(FachadaAplicacion fachadaAplicacion, vPrincipalController controllerPrincipal) {
        super(fachadaAplicacion);
        this.controllerPrincipal = controllerPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Neste caso non é necesario facer nada na inicialización.
    }

    //Acción efectuada ao gardar unha instalación:
    public void btnGardarAction(ActionEvent actionEvent) {
        //Primeiro imos comprobar que os campos non están vacíos:
        if (!ValidacionDatos.estanCubertosCampos(campoNome, campoNumTlf, campoDireccion)) {
            //Se hai campos non cubertos amósase unha mensaxe e non se fai nada máis.
            avisoCampos.setVisible(true); //Amosamos esta mensaxe de erro.
            return;
        }

        //Agora imos validar que o teléfono introducido se corresponda con algo correcto:
        if (!ValidacionDatos.isCorrectoTelefono(campoNumTlf.getText())) {
            //O mesmo que no caso dos campos vacíos: avisamos do erro e non se fai nada máis:
            this.getFachadaAplicacion().mostrarErro("Instalacións", "O número de teléfono é incorrecto!");
            return;
        }

        //Creamos un obxecto instalación con todos os datos facilitados
        Instalacion instalacion = new Instalacion(campoNome.getText(), campoNumTlf.getText(), campoDireccion.getText());
        //Accedemos á base de datos: intentamos que se efectúe sen problemas.
        try {
            //A consulta pódenos devolver varios resultados en función da situación. Avaliámolos:
            TipoResultados res = this.getFachadaAplicacion().darAltaInstalacion(instalacion);
            //En función do resultado, mostraremos unha mensaxe ou outra:
            switch (res) {
                case correcto:
                    //Imprimimos mensaxe de éxito:
                    this.getFachadaAplicacion().mostrarInformacion("Instalacións", "Creada a instalación " +
                            instalacion.getNome() + ". O seu id é: " + instalacion.getCodInstalacion() + ".");
                    //Volvemos á pantalla principal:
                    this.controllerPrincipal.mostrarMenu(IdPantalla.INICIO);
                    break;
                case datoExiste:
                    //Se xa existía unha instalación co nome pasado, entón imprímese un erro e séguese na pantalla.
                    this.getFachadaAplicacion().mostrarErro("Administración de Instalacións", "Xa hai unha instalación co nome '" + instalacion.getNome().toLowerCase() + "'.");
            }
        } catch (ExcepcionBD e) {
            //Se se recibe unha excepción da base de datos, entón imprímese unha mensaxe informando.
            //Esa mensaxe obtense dentro da excepción, co método getMessage():
            this.getFachadaAplicacion().mostrarErro("Administración de Instalacións", e.getMessage());
        }
        //Se houbo algún erro, seguirase nesta pantalla.
    }

    //Acción efectuada ao pulsar o botón "Limpar Campos"
    public void btnLimparAction(ActionEvent actionEvent) {
        //O que imos a facer e limpar os tres campos, vaciar o que teñan:
        campoNome.setText("");
        campoDireccion.setText("");
        campoNumTlf.setText("");
        avisoCampos.setVisible(false);
    }

}

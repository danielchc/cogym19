package centrodeportivo.gui.controladores.Actividades;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.actividades.Actividade;
import centrodeportivo.aplicacion.obxectos.tipos.TipoResultados;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.principal.IdPantalla;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.controlsfx.control.Rating;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Víctor Barreiro
 * Clase que servirá de controlador da pantalla de valorar actividades
 */
public class vValorarPopUpController
        extends AbstractController implements Initializable {

    public Button btnVolver;
    public Label msg;
    public RadioButton radio0;
    public RadioButton radio1;
    public RadioButton radio2;
    public RadioButton radio3;
    public RadioButton radio4;
    public RadioButton radio5;

    private ToggleGroup toggleGroup;
    private ArrayList<RadioButton> radioButtons;


    private vPrincipalController controllerPrincipal;
    private Usuario usuario;
    private Integer valoracion;
    private Actividade actividade;

    /**
     * Constructor do controlador da pantalla de administración de tipos de actividades.
     *
     * @param fachadaAplicacion   A referencia á fachada da parte de aplicación.
     * @param controllerPrincipal A referencia ao controlador da ventá principal.
     */
    public vValorarPopUpController(FachadaAplicacion fachadaAplicacion, vPrincipalController controllerPrincipal, Usuario usuario) {
        // Chamamos ao constructor da clase pai:
        super(fachadaAplicacion);
        // Asignamos o parámetro pasado de controlador da ventá principal ao atributo correspondente:
        this.controllerPrincipal = controllerPrincipal;
        // Asignamos o usuario que esta loggeado
        this.usuario = usuario;
    }

    /**
     * Método que nos permite inicializar a pantalla ao abrila.
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*rate.ratingProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            msg.setText("Puntuación : " + newValue + "/5");
            valoracion = newValue.intValue();
        });*/
        this.toggleGroup=new ToggleGroup();
        this.radioButtons=new ArrayList<>();

        this.radioButtons.add(radio0);
        this.radioButtons.add(radio1);
        this.radioButtons.add(radio2);
        this.radioButtons.add(radio3);
        this.radioButtons.add(radio4);
        this.radioButtons.add(radio5);

        for(RadioButton r:this.radioButtons){
            r.setToggleGroup(this.toggleGroup);
        }
    }

    public void listenerRadioButton(MouseEvent event){
        this.valoracion=this.radioButtons.indexOf((RadioButton) event.getSource());
    }

    public void setActividade(Actividade actividade) {
        this.actividade = actividade;
    }

    /**
     * Acción que ten lugar ao premer o botón de regreso á pantalla de apuntarse actividade.
     *
     * @param actionEvent A acción que tivo lugar
     */
    public void btnVolverAction(ActionEvent actionEvent) {
        // Engadimos a valoracion a actividade:
        try {
            if (valoracion != null) {
                TipoResultados res = super.getFachadaAplicacion().valorarActividade(valoracion, actividade, usuario);
                // En función do resultado, actuamos:
                switch (res) {
                    case correcto:
                        // Se sae correcto saímos á ventá das miñas actividades
                        controllerPrincipal.mostrarPantalla(IdPantalla.VALORARACTIVIDADE);
                        break;
                    case foraTempo:
                        // Amosamos unha mensaxe que clarifique o usuario a situación especificando o erro:
                        super.getFachadaAplicacion().mostrarErro("Valorar Actividades",
                                "Non podes valorar unha actividade que ainda non comezou!");
                        // Saímos á ventá das miñas actividades
                        controllerPrincipal.mostrarPantalla(IdPantalla.VALORARACTIVIDADE);
                        break;
                    case sitIncoherente:
                        // Amosamos unha mensaxe que clarifique o usuario a situación:
                        super.getFachadaAplicacion().mostrarErro("Valorar actividades",
                                "Non se pode valorar esta actividade, comproba a valoración!");
                        // Saímos á ventá das miñas actividades
                        controllerPrincipal.mostrarPantalla(IdPantalla.VALORARACTIVIDADE);
                        break;
                }
            }
        } catch (ExcepcionBD e) {
            // No caso de termos outra excepción da base de datos, amosase:
            super.getFachadaAplicacion().mostrarErro("Valorar actividades", e.getMessage());
        } finally {
            // Saímos á ventá das miñas actividades
            controllerPrincipal.mostrarPantalla(IdPantalla.VALORARACTIVIDADE);
        }

    }


}

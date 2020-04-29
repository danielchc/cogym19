package centrodeportivo.gui.controladores;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.tipos.TipoUsuario;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.funcionsAux.Criptografia;
import centrodeportivo.funcionsAux.ValidacionDatos;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Helena Castro
 * @author Manuel Bendaña
 * @author Victor Barreiro
 * Clase que nos serve de controlador para a ventá de login, de inicio de sesión na aplicación.
 */
public class vLoginController  extends AbstractController implements Initializable {

    /**
     * Atributos da clase públicos: correspóndense con partes da interface correspondente:
     */
    public PasswordField tfContrasinal; /**Campo do controsinal introducido.*/
    public TextField tfUsuario; /**Campo do número de teléfono do usuario.*/
    public Label labelError; /**Etiqueta que se amosa cando se produce un erro de autenticación.*/

    /**
     * Atributos da clase privados: non teñen que ver con partes da interface.
     */
    private FachadaAplicacion fa;

    /**
     * Constructor da ventá de Login:
     * @param fachadaAplicacion a referencia da fachada de aplicación.
     */
    public vLoginController(FachadaAplicacion fachadaAplicacion) {
        //Chamamos ao constructor da clase pai coa fachada de aplicación:
        super(fachadaAplicacion);
        //Para lograr un acceso rápido ao atributo da fachada de aplicación, asociámolo tamén nesta clase:
        fa=super.getFachadaAplicacion();
    }


    /**
     * Método para facer inicialización da ventá asociada ao controlador.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Neste caso, non hai nada que facer neste sentido, polo que deixamos o método baleiro.
        //Trátase dun método que temos que implementar sí ou si, por iso o deixamos así.
    }

    /**
     * Método que representa as accións realizadas ao premer na ventá o botón de inicio de sesión.
     */
    public void btnIniciarAction() {
        //Para comezar, validaremos que os datos estén cubertos:
        if(ValidacionDatos.estanCubertosCampos(tfUsuario,tfContrasinal)){
            try{
                //Se están cubertos os campos, tentamos facer a validación:
                if(fa.validarUsuario(tfUsuario.getText(), tfContrasinal.getText())){
                    //Se se logra realizar a validación, consúltanse máis datos do usuario para manter tras iniciar sesión:
                    Usuario usuario = fa.consultarUsuario(tfUsuario.getText());
                    //Se o tipo do usuario é socio, entón amosamos a ventá de socios.
                    System.out.println(usuario.getTipoUsuario());
                    if(usuario.getTipoUsuario() == TipoUsuario.Socio){
                        fa.mostrarVentaSocios(usuario);
                    } else {
                        //Se é persoal (tanto profesor activo coma non activo), entón amosamos a ventá de persoal.
                        fa.mostrarVentaPersoal(usuario);
                    }
                    //Neste caso, pecharíamos esta escena, porque agora pasaremos á ventá principal dunha das dúas partes
                    //da aplicación.
                    ((Stage) tfUsuario.getScene().getWindow()).close();
                }else{
                    //Se non se puido facer a validación, ponse na etiqueta unha mensaxe indicando o problema:
                    labelError.setText("Algún campo incorrecto");
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }else{
            //Se non están cubertos, establecemos unha mensaxe adecuada na etiqueta:
            labelError.setText("Algún campo está incompleto");
        }
    }

    /**
     * Método invocado cando se presiona unha tecla do teclado.
     * @param keyEvent O evento de pulsado dunha tecla.
     */
    public void keyPressedIniciar(KeyEvent keyEvent) {
        //Se se pulsase a tecla enter, nesta ventá, faríase o análogo a iniciar sesión:
        if(keyEvent.getCode() == KeyCode.ENTER) btnIniciarAction();
    }

}

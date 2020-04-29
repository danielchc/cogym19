package centrodeportivo.gui;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.gui.controladores.principal.IdPantalla;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import centrodeportivo.gui.controladores.vLoginController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Helena Castro
 * @author Manuel Bendaña
 * @author Victor Barreiro
 * Esta clase servirá de fachada para o que é a parte da interface gráfica. No noso caso, ao usar JavaFX, teremos nesta
 * parte do proxecto tanto os fxml coma os controladores das ventás.
 */
public class FachadaGUI {
    /**
     * Atributos:
     */
    private FachadaAplicacion fachadaAplicacion; /**Referencia á fachada de aplicación.*/
    FXMLLoader fxmlLoader; /**Será o que lerá os arquivos fxml (que recollen as nosas pantallas).*/
    Stage stage; /**O escenario.*/

    /**
     * Constructor da fachada da interface gráfica
     * @param fachadaAplicacion A referencia á fachada de aplicación.
     */
    public FachadaGUI(FachadaAplicacion fachadaAplicacion) {
        //Asociamos a fachada de aplicación ao atributo:
        this.fachadaAplicacion = fachadaAplicacion;
        //Creamos un novo escenario e FXMLLoader:
        stage = new Stage();
        fxmlLoader= new FXMLLoader();
    }

    /**
     * Método que nos permite amosar a ventá da aplicación orientada aos socios.
     * @param loggedUser O usuario que iniciou sesión na aplicación, e que desencadeou a apertura desta ventá.
     * @throws IOException Excepción asociada á entrada/saída.
     */
    public void mostrarVentaPersoal(Usuario loggedUser) throws IOException {
        //Empregaremos o loader do fxml: primeiro asociaremos o controlador da ventá de persoal, vPrincipalController.
        fxmlLoader.setController(new vPrincipalController(this.fachadaAplicacion,loggedUser, IdPantalla.PANTALLAPERSOAL));
        //Agora, asociamos a ubicación do fxml da ventá principal ao loader
        fxmlLoader.setLocation(getClass().getResource("./vistas/principal/vPrincipal.fxml"));
        //Tamén lle asociamos unha icona para a ventá.
        stage.getIcons().add(new Image("/centrodeportivo/gui/imaxes/logoBrancoAzul.png"));
        //Establecemmos un título.
        stage.setTitle("CoGYM-19: Persoal");
        //Definimos as dimensións mínimas da ventá correspondente.
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        //Establecemos o escenario cargando o fxml que definimos previamente:
        stage.setScene(new Scene(fxmlLoader.load()));
        //Permitiremos o reescalado de tamaño:
        stage.setResizable(true);
        //Amosamos o escenario:
        stage.show();
    }

    /**
     * Método que nos permite amosar a ventá da aplicación asociada ao persoal do centro.
     * @param loggedUser O usuario que iniciou sesión na aplicación, e que desencadeou a apertura desta ventá.
     * @throws IOException Excepción asociada á entrada/saída.
     */
    public void mostrarVentaSocios(Usuario loggedUser) throws IOException {
        //O funcionamento é similar ao método co que se inicia a ventá de persoal.
        //Empezamos establecendo o controller, o mesmo ca no outro caso:
        fxmlLoader.setController(new vPrincipalController(this.fachadaAplicacion,loggedUser,IdPantalla.PANTALLASOCIO));
        //Asociamos agora a localización do fxml:
        fxmlLoader.setLocation(getClass().getResource("./vistas/principal/vPrincipal.fxml"));
        //Asignamos un icono para a ventá:
        stage.getIcons().add(new Image("/centrodeportivo/gui/imaxes/logoBrancoAzul.png"));
        //Establecemos o título da ventá:
        stage.setTitle("CoGYM-19: Socio");
        //Definimos as dimensións mínimas da ventá:
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        //Establecemos o escenario cargando o fxml definido previamente
        stage.setScene(new Scene(fxmlLoader.load()));
        //Permitimos o reescalado:
        stage.setResizable(true);
        //Amosamos o escenario:
        stage.show();
    }

    /**
     * Método que nos permite amosar a ventá de inicio de sesión -> A primeira que se amosará ao abrir a aplicación.
     * @param primaryStage O escenario principal que se facilita ao iniciar a aplicación.
     * @throws IOException
     */
    public void mostrarVentaLogin(Stage primaryStage) throws IOException {
        //Cárgase o fxml introducindo a súa ubicación:
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/centrodeportivo/gui/vistas/vLogin.fxml"));
        //Establécese tamén o controlador correspondente á ventá de login:
        loader.setController(new vLoginController(fachadaAplicacion));
        //Cárgase a pantalla correspondente cargados o controlador e o fxml.
        Parent root = loader.load();
        //Engádese unha icona:
        primaryStage.getIcons().add(new Image("/centrodeportivo/gui/imaxes/logoPequeno.png"));
        //Asóciase o escenario definido previamente:
        primaryStage.setScene(new Scene(root));
        //Permitimos o reescalado:
        primaryStage.setResizable(true);
        //Introducimos un título para a ventá:
        primaryStage.setTitle("CoGYM-19: Inicio de sesión");
        //Amósase a ventá:
        primaryStage.show();
    }

    /**
     * Método que nos permite amosar advertencias por pantalla.
     * @param titulo O título que se lle quere dar á alerta onde apareza a advertencia.
     * @param texto A mensaxe que se lle quere transmitir ao usuario.
     */
    public void mostrarAdvertencia(String titulo,String texto){
        //Creamos unha instancia do tipo "Alerta" do tipo WARNING (advertencia):
        Alert alert = new Alert(Alert.AlertType.WARNING);
        //Asociámoslle o título e o texto correspondente:
        alert.setTitle(titulo);
        alert.setContentText(texto);
        //Amósase a alerta e agárdase a que se peche:
        alert.showAndWait();
    }

    /**
     * Método que nos permite amosar por pantalla unha mensaxe de erro.
     * @param titulo O título que se lle quere dar á ventá onde se presente o erro.
     * @param texto A mensaxe que se lle quere transmitir ao usuario.
     */
    public void mostrarErro(String titulo,String texto){
        //Creamos unha instancia de Alert do tipo ERROR (erro):
        Alert alert = new Alert(Alert.AlertType.ERROR);
        //Asóciase o título e o texto
        alert.setTitle(titulo);
        //O texto de cabeceira aparece ao lado da icona de erro, pero non queremos que se amose nada aí.
        alert.setHeaderText(null);
        //Sí asociaremos ao texto ao contido da ventá:
        alert.setContentText(texto);
        //Facémola reescalable:
        alert.setResizable(true);
        //Amosamos a alerta:
        alert.showAndWait();
    }

    /**
     * Método que nos peremite amosar información por pantalla (sen ser erro nin advertencia)
     * @param titulo O título que se lle quere dar á ventá onde se presente dita información.
     * @param texto A mensaxe que se lle quere transmitir ao usuario.
     */
    public void mostrarInformacion(String titulo,String texto){
        //Creamos unha instancia de Alert do tipo INFORMATION (información):
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        //De novo, asociamos título e contido, obviando a cabeceira (coma no caso de erro):
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(texto);
        //Habilitamos o reescalado desta ventá:
        alert.setResizable(true);
        //Amosámola:
        alert.showAndWait();
    }

    /**
     * Método co que se lle amosa unha mensaxe ao usuario por pantalla, esperando unha confirmación por parte do mesmo.
     * @param titulo O título que se lle quere dar á ventá na que se amose a información.
     * @param texto A mensaxe que se quere transmitir.
     * @return Un dato tipo ButtonType, co que se poderá saber se o usuario deu a súa autorización (confirmou) ou non a
     * raíz da mensaxe introducida.
     */
    public ButtonType mostrarConfirmacion(String titulo, String texto){
        //Creamos unha instancia de Alert do tipo CONFIRMATION (confirmación):
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        //Poñemos coma sempre título e texto asociado á ventá, sen cabeceira:
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(texto);
        //Amosamos a ventá de confirmación:
        alert.showAndWait();
        //Facémola reescalable:
        alert.setResizable(true);
        //Devolvemos o resultado derivado de amosar a ventá (con isto saberemos se o usuario confirmou ou non).
        return alert.getResult();
    }

}

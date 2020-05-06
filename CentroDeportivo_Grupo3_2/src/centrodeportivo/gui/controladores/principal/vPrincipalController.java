package centrodeportivo.gui.controladores.principal;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.Actividades.vAdministrarTiposActividadesController;
import centrodeportivo.gui.controladores.Actividades.vInsercionTipoActividadeController;
import centrodeportivo.gui.controladores.Areas.vAdministrarAreaController;
import centrodeportivo.gui.controladores.Areas.vXestionAreaController;
import centrodeportivo.gui.controladores.Cursos.vAdministrarCursosController;
import centrodeportivo.gui.controladores.Cursos.vXestionCursoController;
import centrodeportivo.gui.controladores.DatosVista;
import centrodeportivo.gui.controladores.Instalacions.vAdministrarInstalacionsController;
import centrodeportivo.gui.controladores.Instalacions.vEditarInstalacionController;
import centrodeportivo.gui.controladores.Instalacions.vNovaInstalacionController;
import centrodeportivo.gui.controladores.Materiales.vAdministrarMateriaisController;
import centrodeportivo.gui.controladores.Materiales.vAdministrarTipoMaterialController;
import centrodeportivo.gui.controladores.Materiales.vEditarMaterialController;
import centrodeportivo.gui.controladores.Materiales.vNovoMaterialController;
import centrodeportivo.gui.controladores.Transicion;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Víctor Barreiro
 * Clase que funciona de controlador da ventá principal da aplicación: esta parte é a máis importante, dado que dende
 * aquí se colocarán todas as demais pantallas que ten a aplicación (salvo o que é o login).
 */
public class vPrincipalController extends AbstractController implements Initializable {

    /**
     * Atributos públicos: correspóndense coas compoñentes desta parte da interfaz: botóns e sideBars principalmente.
     */
    public VBox menuPersoal;
    public VBox menuSocioPersoal;
    public Button btnMaterialPersoal;
    public Button btnActividadesPersoal;
    public Button btnAreasPersoal;
    public Button btnActividadesSocios;
    public StackPane contedorPrincipal;
    public VBox sideBarAreasPersoal;
    public VBox sideBarActividadesPersoal;
    public VBox sideBarMaterialPersoal;
    public VBox sideBarActividadesSocios;

    /**
     * Atributos privados: outras compoñentes necesarias dende o controlador:
     * Todos eles son final, dado que non os volvemos a modificar en toda a execución:
     */
    private final HashMap<Button, Transicion> transicions; //HashMap de transicións para cada un dos botóns.
    private final ArrayList<Button> btnsMenu; //Lista de botóns do menú.
    private final HashMap<IdPantalla, DatosVista> pantallas; //HashMap de pantallas identificadas polos seus IDs.
    private final Usuario usuario; //Usuario que iniciou sesión.
    private final IdPantalla pantallaAMostrar; //Pantalla que se amosará en cada momento na ventá.

    /**
     * Constructor do controlador da ventá principal.
     *
     * @param fachadaAplicacion Referencia á fachada da parte de aplicación.
     * @param usuario           O usuario que iniciou sesión no sistema.
     * @param pantallaAMostrar  A pantalla que se amosará para comezar.
     */
    public vPrincipalController(FachadaAplicacion fachadaAplicacion, Usuario usuario, IdPantalla pantallaAMostrar) {
        //Chamamos ao constructor da clase pai (AbstractController):
        super(fachadaAplicacion);
        //Asignamos os demais parámetros pasados aos atributos:
        this.usuario = usuario;
        this.pantallaAMostrar = pantallaAMostrar;
        //Creamos os conxuntos de datos:
        this.transicions = new HashMap<>();
        this.btnsMenu = new ArrayList<>();
        this.pantallas = new HashMap<>();
        //Procedemos ao que se chama carga de pantallas, que se pode analizar en detalle máis abaixo:
        cargarPantallas();
    }

    /**
     * Método empregado para inicializar a ventá cando se abre.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Coménzanse as transicións:
        inicioTransicions();
        //Ocúltanse todos aqueles menús que sexan innecesarios. Isto dependerá do usuario que se loggeara.
        ocultarMenusInnecesarios();
        //Amosamos, por defecto, a pantalla de inicio.
        mostrarPantalla(IdPantalla.INICIO);
    }

    /**
     * Método empregado para realizar a carga de todas as pantallas que se poderán colocar sobre a ventá principal.
     * Todas esas pantallas atoparanse noutras clases fxml e terán o seu controlador tamén.
     */
    private void cargarPantallas() {
        //Cargamos as pantallas que sexan precisas:
        //Comezamos pola de inicio.
        this.pantallas.put(IdPantalla.INICIO, new DatosVista("../../vistas/principal/vInicio.fxml", new vInicioController(super.getFachadaAplicacion(), this, this.usuario)));
        //Pantalla de nova instalación: pasámoslle o fxml e o controlador. O controlador pide como argumentos fachada de aplicación e este mesmo controlador.
        this.pantallas.put(IdPantalla.NOVAINSTALACION, new DatosVista("../../vistas/Instalacions/vNovaInstalacion.fxml", new vNovaInstalacionController(super.getFachadaAplicacion(), this)));
        //Pantalla de administración das instalacións: pasámoslle o fxml e o controlador.
        this.pantallas.put(IdPantalla.ADMINISTRARINSTALACIONS, new DatosVista("../../vistas/Instalacions/vAdministrarInstalacions.fxml", new vAdministrarInstalacionsController(super.getFachadaAplicacion(), this)));
        //Pantalla de edición dunha instalación:
        this.pantallas.put(IdPantalla.EDITARINSTALACION, new DatosVista("../../vistas/Instalacions/vEditarInstalacion.fxml", new vEditarInstalacionController(super.getFachadaAplicacion(), this)));
        //Pantalla de administración de tipos de actividades:
        this.pantallas.put(IdPantalla.ADMINISTRARTIPOSACTIVIDADES, new DatosVista("../../vistas/Actividades/vAdministrarTiposActividades.fxml", new vAdministrarTiposActividadesController(super.getFachadaAplicacion(), this)));
        //Pantalla para inserción/modificación de tipos de actividades:
        this.pantallas.put(IdPantalla.INSERCIONTIPOACTIVIDADE, new DatosVista("../../vistas/Actividades/vInsercionTipoActividade.fxml", new vInsercionTipoActividadeController(super.getFachadaAplicacion(), this)));
        //Pantalla de novo curso:
        this.pantallas.put(IdPantalla.XESTIONCURSO, new DatosVista("../../vistas/Cursos/vXestionCurso.fxml", new vXestionCursoController(super.getFachadaAplicacion(), this)));
        //Pantalla de administración de cursos:
        this.pantallas.put(IdPantalla.ADMINISTRARCURSOS, new DatosVista("../../vistas/Cursos/vAdministrarCursos.fxml", new vAdministrarCursosController(super.getFachadaAplicacion(), this)));
        // Pantalla novo material: pasámoslle o fxml e o controlador. O controlador pide como argumentos fachada de aplicación e este mesmo controlador.
        this.pantallas.put(IdPantalla.NOVOMATERIAL, new DatosVista("../../vistas/Materiales/vNovoMaterial.fxml", new vNovoMaterialController(super.getFachadaAplicacion(), this)));
        // Pantalla administrar tipos de materiais: pasámoslle o fxml e o controlador. O controlador pide como argumentos fachada de aplicación e este mesmo controlador.
        this.pantallas.put(IdPantalla.ADMINISTRARTIPOMATERIAL, new DatosVista("../../vistas/Materiales/vAdministrarTipoMaterial.fxml", new vAdministrarTipoMaterialController(super.getFachadaAplicacion(), this)));
        // Pantalla administrar materiais: pasámoslle o fxml e o controlador. O controlador pide como argumentos fachada de aplicación e este mesmo controlador.
        this.pantallas.put(IdPantalla.ADMINISTRARMATERIAIS, new DatosVista("../../vistas/Materiales/vAdministrarMaterial.fxml", new vAdministrarMateriaisController(super.getFachadaAplicacion(), this)));
        // Pantalla editar material: pasámoslle o fxml e o controlador. O controlador pide como argumentos fachada de aplicación e este mesmo controlador.
        this.pantallas.put(IdPantalla.EDITARMATERIAL, new DatosVista("../../vistas/Materiales/vEditarMaterial.fxml", new vEditarMaterialController(super.getFachadaAplicacion(), this)));

        //Pantallas para crear unha nova area
        this.pantallas.put(IdPantalla.XESTIONAREA, new DatosVista("../../vistas/Areas/vXestionArea.fxml", new vXestionAreaController(super.getFachadaAplicacion(), this)));

        //Pantallas para modificar unha area e eliminala
        this.pantallas.put(IdPantalla.ADMINAREA, new DatosVista("../../vistas/Areas/vAdministrarArea.fxml", new vAdministrarAreaController(super.getFachadaAplicacion(), this)));
    }

    /**
     * Método que nos permite empezar a levar a cabo as transicións da pantalla:
     */
    private void inicioTransicions() {
        //Creamos un array de sliders (son vBoxes):
        ArrayList<VBox> sliders = new ArrayList<>();
        //Engadimos as distintas barras da ventá:
        sliders.add(sideBarAreasPersoal);
        sliders.add(sideBarActividadesPersoal);
        sliders.add(sideBarMaterialPersoal);
        sliders.add(sideBarActividadesSocios);

        //Engadimos os botóns de cada un:
        btnsMenu.add(btnAreasPersoal);
        btnsMenu.add(btnActividadesPersoal);
        btnsMenu.add(btnMaterialPersoal);
        btnsMenu.add(btnActividadesSocios);

        //Imos engadindo as transicións para cada un dos botóns:
        for (int i = 0; i < btnsMenu.size(); i++) {
            this.transicions.put(btnsMenu.get(i), new Transicion(sliders.get(i)));
        }

        //Mediante a seguinte chamada poderemos ir facendo actualizacións sobre os sliders:
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for (VBox v : sliders) {
                    TranslateTransition t = new TranslateTransition(Duration.millis(.1), v);
                    t.setToX(-(v.getWidth()));
                    t.play();
                }
            }
        });
    }

    /**
     * Método que nos permitirá ocultar aquelas partes do menú que non sexan necesarias. Isto dependerá da pantalla
     * que se abra (para persoal ou para socio).
     */
    private void ocultarMenusInnecesarios() {
        //Se se vai executar a aplicación para a pantalla do persoal, ocultaremos o menú dos socios:
        if (pantallaAMostrar == IdPantalla.PANTALLAPERSOAL) {
            this.menuSocioPersoal.setVisible(false);
            sideBarActividadesSocios.setVisible(false);

            //Pola contra, se se vai a executar a aplicación para a pantalla dos socios, ocultaremos a parte do persoal:
        } else if (pantallaAMostrar == IdPantalla.PANTALLASOCIO) {
            this.menuPersoal.setVisible(false);
            sideBarAreasPersoal.setVisible(false);
            sideBarActividadesPersoal.setVisible(false);
            sideBarMaterialPersoal.setVisible(false);
        }
    }

    /**
     * Acción levada a cabo cando se pulsa un dos botóns do menú:
     *
     * @param actionEvent
     */
    public void btnMenuAction(ActionEvent actionEvent) {
        //Recuperamos o botón que foi pulsado:
        Button boton = (Button) actionEvent.getSource();
        //Recuperamos a transición asociada a dito botón:
        Transicion t = this.transicions.get(boton);
        //Escondemos todos os sliders:
        esconderTodosSliders();
        //Se o slider está pechado, ábrese:
        if ((t.getSlider().getTranslateX()) == -(t.getSlider().getWidth())) {
            boton.getStyleClass().remove("sidebar-button");
            boton.getStyleClass().add("sidebar-button-active");
            t.getTransicionAbrir().play();
        } else {
            //Se non, procédese a pechar o slider:
            esconderSliderBotonMenu(boton);
        }
    }

    /**
     * Método que permite agochar un slider asociado a un botón:
     *
     * @param boton O botón asociado ao slider a agochar.
     */
    private void esconderSliderBotonMenu(Button boton) {
        boton.getStyleClass().remove("sidebar-button-active");
        boton.getStyleClass().add("sidebar-button");
        //Recupérase a transición do botón:
        Transicion t = this.transicions.get(boton);
        //Emprégase a transición de peche para isto:
        t.getTransicionCerrar().setToX(-(t.getSlider().getWidth()));
        //Execútase:
        t.getTransicionCerrar().play();
    }

    /**
     * Método que permite agochar directamente todos os sliders:
     */
    private void esconderTodosSliders() {
        //Chámase a esconderSliderBotonMenu para cada botón:
        for (Button b : this.btnsMenu) {
            esconderSliderBotonMenu(b);
        }
    }

    /**
     * Método que nos permite amosar unha das pantallas na ventá principal:
     *
     * @param idPantalla O identificador da pantalla a amosar.
     */
    public void mostrarPantalla(IdPantalla idPantalla) {
        //Primeiro, vaciamos o que hai no contedor principal:
        this.contedorPrincipal.getChildren().removeAll(this.contedorPrincipal.getChildren());
        try {
            //recuperamos os datos da vista asociada ao id da pantalla pasado como argumento:
            DatosVista dv = this.pantallas.get(idPantalla);
            //Creamos un fxmlLoader:
            FXMLLoader fxmlLoader = new FXMLLoader();
            //Asociámoslle o controlador da pantalla a abrir:
            fxmlLoader.setController(dv.getControlador());
            //Asociámoslle a ubicación do fxml que contén a pantalla.
            fxmlLoader.setLocation(getClass().getResource(dv.getPathFXML()));
            //Engadimos no contedor principal o resultado da carga do fxml:
            this.contedorPrincipal.getChildren().add(fxmlLoader.load());
            //Chamamos ao método reiniciarForm (que calquera controlador ten) para aplicar aqueles cambios que sexan precisos antes
            //de comezar.
            this.pantallas.get(idPantalla).getControlador().reiniciarForm();
        } catch (Exception e) {
            //En caso de excepción, pintamos o stack trace:
            e.printStackTrace();
        }
    }

    /**
     * Acción levada a cabo cando se preme un botón dun slider que nos leva a abrir unha pantalla:
     *
     * @param actionEvent O evento producido
     */
    public void btnSliderAction(ActionEvent actionEvent) {
        //Agochamos todos os sliders:
        esconderTodosSliders();
        //Amosamos a pantalla que corresponda ao botón que se premeu:
        mostrarPantalla(IdPantalla.valueOf(((Button) actionEvent.getSource()).getId()));
    }

    /**
     * Método que nos permite recuperar o controlador de calquera das pantallas vinculadas á ventá principal:
     *
     * @param idPantalla O identificador da pantalla para a cal se quere recuperar o controlador.
     * @return O controlador asociado á pantalla. Como todos son subclase de AbstractController poderemos devolver
     * calquera deles.
     */
    public AbstractController getControlador(IdPantalla idPantalla) {
        return this.pantallas.get(idPantalla).getControlador();
    }

    /**
     * Método que nos permite recuperar os datos do usuario que iniciou sesión
     * @return O usuario que iniciou sesión
     */
    public Usuario getUsuario(){
        return usuario;
    }
}

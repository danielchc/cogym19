package centrodeportivo.gui.controladores.principal;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.auxiliar.IdPantalla;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.auxiliar.DatosVista;
import centrodeportivo.auxiliar.Transicion;
import centrodeportivo.gui.controladores.comun.vMensaxesController;
import centrodeportivo.gui.controladores.comun.vNovaIncidencia;
import centrodeportivo.gui.controladores.comun.vNovoMensaxeController;
import centrodeportivo.gui.controladores.persoal.incidencias.vAdministrarIncidenciasController;
import centrodeportivo.gui.controladores.persoal.incidencias.vXestionIncidenciaController;
import centrodeportivo.gui.controladores.persoal.tarifas.vAdministrarTarifasController;
import centrodeportivo.gui.controladores.persoal.tarifas.vXestionTarifaController;
import centrodeportivo.gui.controladores.persoal.usuarios.vAdministrarCapacidadesController;
import centrodeportivo.gui.controladores.persoal.usuarios.vAdministrarUsuariosController;
import centrodeportivo.gui.controladores.comun.vUsuarioController;
import centrodeportivo.gui.controladores.socios.vCuotaController;
import centrodeportivo.gui.controladores.socios.vEliminarRexistroController;
import centrodeportivo.gui.controladores.socios.vNovoRexistroController;
import centrodeportivo.gui.controladores.socios.vResumenRexistrosController;
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
 * @author David Carracedo
 * @author Daniel Chenel
 */
public class vPrincipalController extends AbstractController implements Initializable {
    /*
        Menus
     */
    public VBox menuPersoal;
    public VBox menuSocio;
    /*
        Botones menu
     */
    public Button btnIncidenciaP;
    public Button btnTarifasP;
    public Button btnMensaxesP;
    public Button btnUsuariosP;
    public Button btnIncidenciaS;
    public Button btnMensaxesS;
    public Button btnUsuariosS;
    //contenedor para as pantallas
    public StackPane mainContainer;
    /*
        Sliders
     */
    public VBox sideBarUsuariosP;
    public VBox sideBarMensaxesP;
    public VBox sideBarTarifasP;
    public VBox sideBarIncidenciasP;
    public VBox sideBarUsuariosS;
    public VBox sideBarMensaxesS;
    public VBox sideBarIncidenciasS;
    /*
        Atributos
     */
    private HashMap<Button, Transicion> transiciones;
    private ArrayList<Button> botonesMenu;
    private HashMap<IdPantalla, DatosVista> pantallas;
    private String loginUsuarioLoggeado;
    private IdPantalla pantallaAMostrar;
    private IdPantalla ultimaPantalla;


    /**
     * Constructor para o controlador da pantalla principal que contén o menú e o contenedor das distintas pantallas.
     *
     * @param fachadaAplicacion fachada da aplicación
     * @param usuarioLogeado    Usuario que está activo
     * @param pantallaAMostrar  pantalla a mostrar en primeiro lugar
     */
    public vPrincipalController(FachadaAplicacion fachadaAplicacion, Usuario usuarioLogeado, IdPantalla pantallaAMostrar) {
        super(fachadaAplicacion);
        this.loginUsuarioLoggeado = usuarioLogeado.getLogin();
        this.pantallaAMostrar = pantallaAMostrar;
        this.transiciones = new HashMap<>();
        this.botonesMenu = new ArrayList<>();
        this.pantallas = new HashMap<>();
        this.ultimaPantalla = IdPantalla.INICIO;
        cargarPantallas();
    }

    /**
     * Método para inicializar a vista.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inciarTransiciones();
        ocultarMenusInnecesarios();
        mostrarMenu(IdPantalla.INICIO);
    }

    /**
     * Este método cargar todas as pantalals necesarias, asociándolles un
     * identificador e os seus controladores correspondentes.
     */
    private void cargarPantallas() {
        //carganse todas as pantallas necesarias
        if (pantallaAMostrar == IdPantalla.PANTALLAPERSOAL) {
            this.pantallas.put(IdPantalla.ADMINISTRARUSUARIOS, new DatosVista("/centrodeportivo/gui/vistas/persoal/usuarios/vAdministrarUsuarios.fxml", new vAdministrarUsuariosController(super.getFachadaAplicacion(), this)));
            this.pantallas.put(IdPantalla.XESTIONTARIFA, new DatosVista("/centrodeportivo/gui/vistas/persoal/tarifas/vXestionTarifa.fxml", new vXestionTarifaController(super.getFachadaAplicacion(), this)));
            this.pantallas.put(IdPantalla.ADMINISTRARTARIFAS, new DatosVista("/centrodeportivo/gui/vistas/persoal/tarifas/vAdministrarTarifas.fxml", new vAdministrarTarifasController(super.getFachadaAplicacion(), this)));
            this.pantallas.put(IdPantalla.ADMINISTRARINCIDENCIAS, new DatosVista("/centrodeportivo/gui/vistas/persoal/incidencias/vAdministrarIncidencias.fxml", new vAdministrarIncidenciasController(super.getFachadaAplicacion(), this)));
            this.pantallas.put(IdPantalla.ADMINISTRARCAPACIDADES, new DatosVista("/centrodeportivo/gui/vistas/persoal/usuarios/vAdministrarCapacidades.fxml", new vAdministrarCapacidadesController(super.getFachadaAplicacion(), this)));
            this.pantallas.put(IdPantalla.XESTIONINCIDENCIA, new DatosVista("/centrodeportivo/gui/vistas/persoal/incidencias/vXestionIncidencia.fxml", new vXestionIncidenciaController(super.getFachadaAplicacion(), this)));

        } else if (pantallaAMostrar == IdPantalla.PANTALLASOCIO) {
            this.pantallas.put(IdPantalla.CUOTA, new DatosVista("/centrodeportivo/gui/vistas/socios/vCuota.fxml", new vCuotaController(super.getFachadaAplicacion(), this)));
            this.pantallas.put(IdPantalla.RESUMENREXISTROS, new DatosVista("/centrodeportivo/gui/vistas/socios/vResumenRexistros.fxml", new vResumenRexistrosController(super.getFachadaAplicacion(), this)));
            this.pantallas.put(IdPantalla.NOVOREXISTRO, new DatosVista("/centrodeportivo/gui/vistas/socios/vNovoRexistro.fxml", new vNovoRexistroController(super.getFachadaAplicacion(), this)));
            this.pantallas.put(IdPantalla.ELIMINARREXISTRO, new DatosVista("/centrodeportivo/gui/vistas/socios/vEliminarRexistro.fxml", new vEliminarRexistroController(super.getFachadaAplicacion(), this)));

        }
        
        this.pantallas.put(IdPantalla.USUARIO, new DatosVista("/centrodeportivo/gui/vistas/comun/vUsuario.fxml", new vUsuarioController(super.getFachadaAplicacion(), this)));
        this.pantallas.put(IdPantalla.INICIO, new DatosVista("/centrodeportivo/gui/vistas/principal/vInicio.fxml", new vInicioController(super.getFachadaAplicacion(), this)));
        this.pantallas.put(IdPantalla.MENSAXES, new DatosVista("/centrodeportivo/gui/vistas/comun/vMensaxes.fxml", new vMensaxesController(super.getFachadaAplicacion(), this)));
        this.pantallas.put(IdPantalla.NOVOMENSAXE, new DatosVista("/centrodeportivo/gui/vistas/comun/vNovoMensaxe.fxml", new vNovoMensaxeController(super.getFachadaAplicacion(), this)));
        this.pantallas.put(IdPantalla.NOVAINCIDENCIA, new DatosVista("/centrodeportivo/gui/vistas/comun/vNovaIncidencia.fxml", new vNovaIncidencia(super.getFachadaAplicacion(), this)));
    }

    /**
     * Este método inicializa as transicións dos sliders desplegables.
     * Ademáis esconde todos os desplegables ao iniciarse a aplicación.
     */
    private void inciarTransiciones() {
        ArrayList<VBox> sliders = new ArrayList<>();
        sliders.add(sideBarUsuariosP);
        sliders.add(sideBarMensaxesP);
        sliders.add(sideBarTarifasP);
        sliders.add(sideBarIncidenciasP);

        sliders.add(sideBarUsuariosS);
        sliders.add(sideBarMensaxesS);
        sliders.add(sideBarIncidenciasS);

        botonesMenu.add(btnUsuariosP);
        botonesMenu.add(btnMensaxesP);
        botonesMenu.add(btnTarifasP);
        botonesMenu.add(btnIncidenciaP);

        botonesMenu.add(btnUsuariosS);
        botonesMenu.add(btnMensaxesS);
        botonesMenu.add(btnIncidenciaS);

        for (int i = 0; i < botonesMenu.size(); i++) {
            this.transiciones.put(botonesMenu.get(i), new Transicion(sliders.get(i)));
        }

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
     * Este método mostra os menús correspondentes segundo o tipo de usuario que se loggea.
     */
    private void ocultarMenusInnecesarios() {
        if (pantallaAMostrar == IdPantalla.PANTALLAPERSOAL) {
            this.menuSocio.setVisible(false);
            sideBarUsuariosS.setVisible(false);
            sideBarMensaxesS.setVisible(false);
            sideBarIncidenciasS.setVisible(false);

        } else if (pantallaAMostrar == IdPantalla.PANTALLASOCIO) {
            this.menuPersoal.setVisible(false);
            sideBarUsuariosP.setVisible(false);
            sideBarMensaxesP.setVisible(false);
            sideBarTarifasP.setVisible(false);
            sideBarIncidenciasP.setVisible(false);
        }
    }

    /**
     * Este método esconde o desplegable correspondente ao botón pulsado.
     *
     * @param boton Botón do desplegable pulsado.
     */
    private void esconderSliderBotonMenu(Button boton) {
        boton.getStyleClass().remove("sidebar-button-active");
        boton.getStyleClass().add("sidebar-button");
        Transicion t = this.transiciones.get(boton);
        t.getTransicionCerrar().setToX(-(t.getSlider().getWidth()));
        t.getTransicionCerrar().play();
    }

    /**
     * Este método esconde todos os desplegables.
     */
    private void esconderTodosSliders() {
        for (Button b : this.botonesMenu) {
            esconderSliderBotonMenu(b);
        }
    }

    /**
     * Este método abre/cerra o desplegable asociado ao botón do menú pulsado.
     *
     * @param actionEvent evento
     */
    public void btnMenuAction(ActionEvent actionEvent) {
        Button boton = (Button) actionEvent.getSource();
        Transicion t = this.transiciones.get(boton);
        esconderTodosSliders();
        if ((t.getSlider().getTranslateX()) == -(t.getSlider().getWidth())) {
            boton.getStyleClass().remove("sidebar-button");
            boton.getStyleClass().add("sidebar-button-active");
            t.getTransicionAbrir().play();
        } else {
            esconderSliderBotonMenu(boton);
        }
    }

    /**
     * Este método esconde os desplegables e abre a pantalla seleccionada.
     *
     * @param actionEvent evento
     */
    public void btnSliderAction(ActionEvent actionEvent) {
        esconderTodosSliders();
        mostrarMenu(IdPantalla.valueOf(((Button) actionEvent.getSource()).getId()));
    }

    /**
     * Este método mostra a pantalla correspondente ao id que se lle pasa como parámetro.
     * Cárgase o fxml e asóciaselle o seu controlador correspondente.
     *
     * @param idPantalla Pantalla a mostrar.
     */
    public void mostrarMenu(IdPantalla idPantalla) {
        this.ultimaPantalla = this.pantallaAMostrar;
        this.mainContainer.getChildren().removeAll(this.mainContainer.getChildren());
        try {
            DatosVista dv = this.pantallas.get(idPantalla);
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setController(dv.getControlador());
            fxmlLoader.setLocation(getClass().getResource(dv.getPathFXML()));
            this.mainContainer.getChildren().add(fxmlLoader.load());
            this.pantallaAMostrar = idPantalla;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Este método permite volver á última pantalla amosada.
     */
    public void volverAtras() {
        mostrarMenu(ultimaPantalla);
    }

    /**
     * Este método abre o perfil dun socio para poder editalo.
     */
    public void perfilUsuarioAction() {
        esconderTodosSliders();
        mostrarMenu(IdPantalla.USUARIO);
        ((vUsuarioController) getControlador(IdPantalla.USUARIO)).cargarDatosUsuario(super.getFachadaAplicacion().consultarUsuario(this.loginUsuarioLoggeado));
    }

    /**
     * Este método devolve o controlador dunha pantalla.
     *
     * @param idPantalla Identificador da pantalla.
     * @return Controladr asociado á pantalla.
     */
    public AbstractController getControlador(IdPantalla idPantalla) {
        return this.pantallas.get(idPantalla).getControlador();
    }

    /**
     * @return Usuario loggeado no sistema.
     */
    public Usuario obterUsuarioLogeado() {
        return super.getFachadaAplicacion().consultarUsuario(loginUsuarioLoggeado);
    }
}
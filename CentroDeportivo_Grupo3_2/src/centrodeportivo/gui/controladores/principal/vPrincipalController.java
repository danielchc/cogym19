package centrodeportivo.gui.controladores.principal;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.Actividades.vAdministrarTiposActividadesController;
import centrodeportivo.gui.controladores.Actividades.vInsercionTipoActividadeController;
import centrodeportivo.gui.controladores.Areas.vNovaAreaController;
import centrodeportivo.gui.controladores.Cursos.vAdministrarCursosController;
import centrodeportivo.gui.controladores.Cursos.vXestionCursoController;
import centrodeportivo.gui.controladores.DatosVista;
import centrodeportivo.gui.controladores.Instalacions.vAdministrarInstalacionsController;
import centrodeportivo.gui.controladores.Instalacions.vEditarInstalacionController;
import centrodeportivo.gui.controladores.Instalacions.vNovaInstalacionController;
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

public class vPrincipalController extends AbstractController implements Initializable {
    /*
        Menus
     */
    public VBox menuPersoal;
    public VBox menuSocio;
    /*
        Botones menu
     */
    public Button btnMaterialP;
    public Button btnActividadesP;
    public Button btnAreasP;
    public Button btnActividadesS;
    //contenedor para as pantallas
    public StackPane mainContainer;
    /*
        Sliders
     */
    public VBox sideBarAreasP;
    public VBox sideBarActividadesP;
    public VBox sideBarMaterialP;
    public VBox sideBarActividadesS;
    /*
        Atributos
     */
    private HashMap<Button, Transicion> transiciones;
    private ArrayList<Button> botonesMenu;
    private HashMap<IdPantalla, DatosVista> pantallas;
    private Usuario usuario;
    private IdPantalla pantallaAMostrar;

    public vPrincipalController(FachadaAplicacion fachadaAplicacion, Usuario loggedUser,IdPantalla pantallaAMostrar) {
        super(fachadaAplicacion);
        this.usuario=loggedUser;
        this.pantallaAMostrar=pantallaAMostrar;
        this.transiciones=new HashMap<>();
        this.botonesMenu=new ArrayList<>();
        this.pantallas=new HashMap<>();
        cargarPantallas();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inciarTransiciones();
        ocultarMenusInnecesarios();
        mostrarMenu(IdPantalla.INICIO);
    }

    private void cargarPantallas() {
        //Cargamos as pantallas que sexan precisas:
        this.pantallas.put(IdPantalla.INICIO,new DatosVista("../../vistas/principal/vInicio.fxml",new vInicioController(super.getFachadaAplicacion(),this.usuario)));
        //Pantalla de nova instalación: pasámoslle o fxml e o controlador. O controlador pide como argumentos fachada de aplicación e este mesmo controlador.
        this.pantallas.put(IdPantalla.NOVAINSTALACION,new DatosVista("../../vistas/Instalacions/vNovaInstalacion.fxml",new vNovaInstalacionController(super.getFachadaAplicacion(), this)));
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
        this.pantallas.put(IdPantalla.NOVOMATERIAL,new DatosVista("../../vistas/Materiales/vNovoMaterial.fxml",new vNovoMaterialController(super.getFachadaAplicacion(), this)));
        this.pantallas.put(IdPantalla.NOVAAREA, new DatosVista("../../vistas/Areas/vNovaArea.fxml", new vNovaAreaController(super.getFachadaAplicacion(), this)));
        this.pantallas.put(IdPantalla.NOVAAREA1, new DatosVista("../../vistas/Areas/vNovaArea1.fxml", new vNovaAreaController(super.getFachadaAplicacion(), this)));

    }

    private void inciarTransiciones(){
        ArrayList<VBox> sliders=new ArrayList<>();
        sliders.add(sideBarAreasP);
        sliders.add(sideBarActividadesP);
        sliders.add(sideBarMaterialP);
        sliders.add(sideBarActividadesS);

        botonesMenu.add(btnAreasP);
        botonesMenu.add(btnActividadesP);
        botonesMenu.add(btnMaterialP);
        botonesMenu.add(btnActividadesS);

        for(int i=0;i<botonesMenu.size();i++){
            this.transiciones.put(botonesMenu.get(i),new Transicion(sliders.get(i)));
        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for(VBox v:sliders){
                    TranslateTransition t=new TranslateTransition(Duration.millis(.1),v);
                    t.setToX(-(v.getWidth()));
                    t.play();
                }
            }
        });
    }

    private void ocultarMenusInnecesarios(){
        if(pantallaAMostrar==IdPantalla.PANTALLAPERSOAL){
            this.menuSocio.setVisible(false);
            sideBarActividadesS.setVisible(false);

        }else if(pantallaAMostrar==IdPantalla.PANTALLASOCIO){
            this.menuPersoal.setVisible(false);
            sideBarAreasP.setVisible(false);
            sideBarActividadesP.setVisible(false);
            sideBarMaterialP.setVisible(false);
        }
    }

    public void btnMenuAction(ActionEvent actionEvent) {
        Button boton=(Button)actionEvent.getSource();
        Transicion t=this.transiciones.get(boton);
        esconderTodosSliders();
        if ((t.getSlider().getTranslateX()) == -(t.getSlider().getWidth()) ) {
            boton.getStyleClass().remove("sidebar-button");
            boton.getStyleClass().add("sidebar-button-active");
            t.getTransicionAbrir().play();
        } else {
            esconderSliderBotonMenu(boton);
        }
    }

    private void esconderSliderBotonMenu(Button boton){
        boton.getStyleClass().remove("sidebar-button-active");
        boton.getStyleClass().add("sidebar-button");
        Transicion t=this.transiciones.get(boton);
        t.getTransicionCerrar().setToX(-(t.getSlider().getWidth()));
        t.getTransicionCerrar().play();
    }

    private void esconderTodosSliders(){
        for(Button b:this.botonesMenu){
            esconderSliderBotonMenu(b);
        }
    }

    public void mostrarMenu(IdPantalla idPantalla){
        this.mainContainer.getChildren().removeAll(this.mainContainer.getChildren());
        try {
            DatosVista dv=this.pantallas.get(idPantalla);
            FXMLLoader fxmlLoader=new FXMLLoader();
            fxmlLoader.setController(dv.getControlador());
            fxmlLoader.setLocation(getClass().getResource(dv.getPathFXML()));
            this.mainContainer.getChildren().add(fxmlLoader.load());
            this.pantallas.get(idPantalla).getControlador().reiniciarForm();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnSliderAction(ActionEvent actionEvent) {
        esconderTodosSliders();
        mostrarMenu(IdPantalla.valueOf(((Button)actionEvent.getSource()).getId()));
    }

    public AbstractController getControlador(IdPantalla idPantalla){
        return this.pantallas.get(idPantalla).getControlador();
    }
}

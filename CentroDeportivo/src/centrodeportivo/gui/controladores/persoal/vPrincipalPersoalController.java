package centrodeportivo.gui.controladores.persoal;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.DatosVista;
import centrodeportivo.gui.controladores.Transicion;
import centrodeportivo.gui.controladores.persoal.material.vNovoMaterialController;
import centrodeportivo.gui.controladores.persoal.tarifas.vNovaTarifaController;
import centrodeportivo.gui.controladores.persoal.usuarios.vAdministrarUsuariosController;
import centrodeportivo.gui.controladores.persoal.usuarios.vNovoSocioController;
import centrodeportivo.gui.controladores.persoal.usuarios.vNovoUsuarioController;
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

public class vPrincipalPersoalController extends  AbstractController implements Initializable  {
    public Button btnIncidencia;
    public Button btnMaterial;
    public Button btnActividades;
    public Button btnAreas;
    public Button btnTarifas;
    public Button btnMensaxes;
    public Button btnUsuarios;
    public Button btnInicio;
    public StackPane mainContainer;
    public VBox sideBarUsuarios;
    public VBox sideBarMensaxes;
    public VBox sideBarTarifas;
    public VBox sideBarAreas;
    public VBox sideBarActividades;
    public VBox sideBarMaterial;
    public VBox sideBarIncidencias;

    private HashMap<Button, Transicion> transiciones;
    private ArrayList<Button> botonesMenu;
    private HashMap<PantallasPersoal, DatosVista> pantallas;
    private Usuario usuario;

    public vPrincipalPersoalController(FachadaAplicacion fachadaAplicacion, Usuario usuario) {
        super(fachadaAplicacion);
        this.usuario=usuario;
        this.transiciones=new HashMap<>();
        this.botonesMenu=new ArrayList<>();
        this.pantallas=new HashMap<>();
        cargarPantallas();
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inciarTransiciones();
        mostrarMenu(PantallasPersoal.INICIO);
    }

    private void inciarTransiciones(){
        ArrayList<VBox> sliders=new ArrayList<>();
        sliders.add(sideBarUsuarios);       sliders.add(sideBarMensaxes);
        sliders.add(sideBarTarifas);        sliders.add(sideBarAreas);
        sliders.add(sideBarActividades);    sliders.add(sideBarIncidencias);
        sliders.add(sideBarMaterial);

        botonesMenu.add(btnUsuarios);       botonesMenu.add(btnMensaxes);
        botonesMenu.add(btnTarifas);        botonesMenu.add(btnAreas);
        botonesMenu.add(btnActividades);    botonesMenu.add(btnIncidencia);
        botonesMenu.add(btnMaterial);

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

    private void cargarPantallas() {
        //carganse todas as pantallas necesarias
        this.pantallas.put(PantallasPersoal.NOVOUSUARIO,new DatosVista("../../vistas/persoal/usuarios/vNovoUsuario.fxml",new vNovoUsuarioController(super.getFachadaAplicacion(),this)));
        this.pantallas.put(PantallasPersoal.INICIO,new DatosVista("../../vistas/persoal/vInicio.fxml",new vInicioController(super.getFachadaAplicacion(),this.usuario)));
        this.pantallas.put(PantallasPersoal.ADMINISTRARUSUARIOS,new DatosVista("../../vistas/persoal/usuarios/vAdministrarUsuarios.fxml",new vAdministrarUsuariosController(super.getFachadaAplicacion())));
        this.pantallas.put(PantallasPersoal.NOVATARIFA,new DatosVista("../../vistas/persoal/tarifas/vNovaTarifa.fxml",new vNovaTarifaController(super.getFachadaAplicacion())));
        this.pantallas.put(PantallasPersoal.NOVOMATERIAL,new DatosVista("../../vistas/persoal/material/vNovoMaterial.fxml",new vNovoMaterialController(super.getFachadaAplicacion())));

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

    public void mostrarMenu(PantallasPersoal idPantalla){
        this.mainContainer.getChildren().removeAll(this.mainContainer.getChildren());
        try {
            DatosVista dv=this.pantallas.get(idPantalla);
            FXMLLoader fxmlLoader=new FXMLLoader();
            fxmlLoader.setController(dv.getControlador());
            fxmlLoader.setLocation(getClass().getResource(dv.getPathFXML()));
            this.mainContainer.getChildren().add(fxmlLoader.load());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnSliderAction(ActionEvent actionEvent) {
        esconderTodosSliders();
        mostrarMenu(PantallasPersoal.valueOf(((Button)actionEvent.getSource()).getId()));
    }
}

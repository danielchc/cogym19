package centrodeportivo.gui.controladores.principal;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.DatosVista;
import centrodeportivo.gui.controladores.Transicion;
import centrodeportivo.gui.controladores.persoal.PantallasPersoal;
import centrodeportivo.gui.controladores.persoal.mensaxes.vMensaxesController;
import centrodeportivo.gui.controladores.persoal.tarifas.vAdministrarTarifasController;
import centrodeportivo.gui.controladores.persoal.tarifas.vNovaTarifaController;
import centrodeportivo.gui.controladores.persoal.usuarios.vAdministrarUsuariosController;
import centrodeportivo.gui.controladores.persoal.usuarios.vNovoUsuarioController;
import centrodeportivo.gui.controladores.persoal.vInicioController;
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
    public Button btnIncidenciaP;
    public Button btnMaterialP;
    public Button btnActividadesP;
    public Button btnAreasP;
    public Button btnTarifasP;
    public Button btnMensaxesP;
    public Button btnUsuariosP;
    public Button btnIncidenciaS;
    public Button btnActividadesS;
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
    public VBox sideBarAreasP;
    public VBox sideBarActividadesP;
    public VBox sideBarMaterialP;
    public VBox sideBarIncidenciasP;
    public VBox sideBarUsuariosS;
    public VBox sideBarMensaxesS;
    public VBox sideBarActividadesS;
    public VBox sideBarIncidenciasS;
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
        //carganse todas as pantallas necesarias
        this.pantallas.put(IdPantalla.NOVOUSUARIO,new DatosVista("../../vistas/persoal/usuarios/vNovoUsuario.fxml",new vNovoUsuarioController(super.getFachadaAplicacion(),null)));
        this.pantallas.put(IdPantalla.INICIO,new DatosVista("../../vistas/persoal/vInicio.fxml",new vInicioController(super.getFachadaAplicacion(),this.usuario)));
        this.pantallas.put(IdPantalla.ADMINISTRARUSUARIOS,new DatosVista("../../vistas/persoal/usuarios/vAdministrarUsuarios.fxml",new vAdministrarUsuariosController(super.getFachadaAplicacion())));
        this.pantallas.put(IdPantalla.NOVATARIFA,new DatosVista("../../vistas/persoal/tarifas/vNovaTarifa.fxml",new vNovaTarifaController(super.getFachadaAplicacion())));
        this.pantallas.put(IdPantalla.ADMINISTRARTARIFAS,new DatosVista("../../vistas/persoal/tarifas/vAdministrarTarifas.fxml",new vAdministrarTarifasController(super.getFachadaAplicacion())));
        this.pantallas.put(IdPantalla.MENSAXES,new DatosVista("../../vistas/persoal/mensaxes/vMensaxes.fxml",new vMensaxesController(super.getFachadaAplicacion(),this.usuario)));

    }

    private void inciarTransiciones(){
        ArrayList<VBox> sliders=new ArrayList<>();
        sliders.add(sideBarUsuariosP);       sliders.add(sideBarMensaxesP);
        sliders.add(sideBarTarifasP);        sliders.add(sideBarAreasP);
        sliders.add(sideBarActividadesP);    sliders.add(sideBarIncidenciasP);
        sliders.add(sideBarMaterialP);
        sliders.add(sideBarUsuariosS);       sliders.add(sideBarMensaxesS);
        sliders.add(sideBarActividadesS);    sliders.add(sideBarIncidenciasS);

        botonesMenu.add(btnUsuariosP);       botonesMenu.add(btnMensaxesP);
        botonesMenu.add(btnTarifasP);        botonesMenu.add(btnAreasP);
        botonesMenu.add(btnActividadesP);    botonesMenu.add(btnIncidenciaP);
        botonesMenu.add(btnMaterialP);
        botonesMenu.add(btnUsuariosS);       botonesMenu.add(btnMensaxesS);
        botonesMenu.add(btnActividadesS);    botonesMenu.add(btnIncidenciaS);

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
            sideBarUsuariosS.setVisible(false);
            sideBarMensaxesS.setVisible(false);
            sideBarActividadesS.setVisible(false);
            sideBarIncidenciasS.setVisible(false);

        }else if(pantallaAMostrar==IdPantalla.PANTALLASOCIO){
            this.menuPersoal.setVisible(false);
            sideBarUsuariosP.setVisible(false);
            sideBarMensaxesP.setVisible(false);
            sideBarTarifasP.setVisible(false);
            sideBarAreasP.setVisible(false);
            sideBarActividadesP.setVisible(false);
            sideBarMaterialP.setVisible(false);
            sideBarIncidenciasP.setVisible(false);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnSliderAction(ActionEvent actionEvent) {
        esconderTodosSliders();
        mostrarMenu(IdPantalla.valueOf(((Button)actionEvent.getSource()).getId()));
    }
}

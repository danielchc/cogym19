package centrodeportivo.gui.controladores.Areas;

import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.aplicacion.obxectos.tipos.TipoResultados;
import centrodeportivo.funcionsAux.ValidacionDatos;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.gui.controladores.AuxGUI;
import centrodeportivo.gui.controladores.principal.IdPantalla;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;


/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Víctor Barreiro
 *
 * Clase que funciona como controlador da ventá de administración de áreas.
 */
public class vAdministrarAreaController extends AbstractController implements Initializable {

    /**
     * Atributos públicos: correspóndense con cuestións da ventá correspondente.
     */
    public TableView taboaAreas;
    public Button btnBuscar;
    public Button btnLimpar;
    public TextField campoNome;
    public TextField campoAforo;
    public Button btnVolver;
    public Button btnDarBaixa;
    public Button btnDarDeAlta;
    public Button btnEliminarArea;
    public Button btnModificarArea;


    /**
     * Atributos privados: manteremos o controlador da ventá de procedencia e a instalación de referencia:
     */
    private vPrincipalController controllerPrincipal;
    private Instalacion instalacion;

    /**
     * Constructor do controlador da ventá de administración de áreas
     * @param fachadaAplicacion A referencia á fachada da parte de aplicación.
     * @param controllerPrincipal A referencia ao controlador da ventá principal.
     */
    public vAdministrarAreaController(FachadaAplicacion fachadaAplicacion, vPrincipalController controllerPrincipal) {
        //Chamamos ao constructor da clase pai:
        super(fachadaAplicacion);
        //Asignamos o controlador:
        this.controllerPrincipal = controllerPrincipal;
    }

    /**
     * Método que se executa ao abrir a ventá, para inicializar as compoñentes.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Neste caso temos que colocar todos os campos na táboa:
        //A primeira columna terá o código da Area:
        TableColumn<Area, Integer> colCodigo = new TableColumn<>("Código");
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codArea"));
        //A segunda columna terá o seu nome:
        TableColumn<Area, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        //A terceira columna corresponderase co aforo máximo:
        TableColumn<Area, Integer> colAforo = new TableColumn<>("Aforo Máximo");
        colAforo.setCellValueFactory(new PropertyValueFactory<>("aforoMaximo"));
        //A cuarta columna corresponderase coa data de baixa:
        TableColumn<Area, Date> coldata = new TableColumn<>("Data de Baixa");
        coldata.setCellValueFactory(new PropertyValueFactory<>("dataBaixa"));
        //A quinta columna corresponderase coa desciricon da área:
        TableColumn<Area, String> colDes = new TableColumn<>("Descricion");
        colDes.setCellValueFactory(new PropertyValueFactory<>("descricion"));

        //Feito isto, engadimos as columnas:
        taboaAreas.getColumns().addAll(colCodigo, colNome, colAforo, coldata, colDes);
        //Agora engadimos items:
        taboaAreas.getItems().addAll(super.getFachadaAplicacion().buscarArea(instalacion, null));
        //Establecemos unha selección sobre a táboa (se hai resultados):
        taboaAreas.getSelectionModel().selectFirst();
        //Controlamos tamaño das columnas:
        taboaAreas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //Engadimos un listener para cando se seleccione unha actividade:
        taboaAreas.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) enSeleccion();
        });
    }

    /**
     * Método executado ao seleccionar unha actividade determinada, o que executa o listener de cando se seleccione
     * unha actividade.
     */
    public void enSeleccion() {
        //Escollemos o item seleccionado da táboa de áreas:
        Area area = ((Area) taboaAreas.getSelectionModel().getSelectedItem());
        if(area != null){
            //Se a data de baixa está rexistrada a área estará de baixa, polo que habilitaremos a opción de dala de alta.
            if(area.getDataBaixa() != null){
                AuxGUI.habilitarCampos(btnDarDeAlta);
                AuxGUI.inhabilitarCampos(btnDarBaixa);
            } else {
                //Noutro caso, se a data de baixa é null, daremos só a opción de dar a área de baixa (estará de alta):
                AuxGUI.inhabilitarCampos(btnDarDeAlta);
                AuxGUI.habilitarCampos(btnDarBaixa);
            }
        }
    }

    /**
     * Método que permite actualizar a táboa de áreas presente nesta ventá con todas as áreas.
     */
    public void actualizarTaboa() {
        //Engadimos items:
        taboaAreas.getItems().setAll(super.getFachadaAplicacion().buscarArea(instalacion, null));
        //Establecemos unha selección sobre a táboa (se hai resultados):
        taboaAreas.getSelectionModel().selectFirst();
    }

    /**
     * Setter da instalación
     * @param instalacion A instalación a asignar.
     */
    public void setInstalacion(Instalacion instalacion) {
        this.instalacion = instalacion;
    }

    /**
     * Método que representa as accións levadas a cabo ao premer o botón de busca:
     *
     * @param actionEvent A acción que tivo lugar.
     */
    public void btnBuscarAction(ActionEvent actionEvent) {
        //Cando se lle dá ao botón de buscar, hai que efectuar unha busca na Base de Datos segundo os campos dispostos.
        //Borramos primeiro todas as áreas da táboa:
        taboaAreas.getItems().removeAll(taboaAreas.getItems());
        //Se non se cubriu ningún campo, o que faremos será listar todas as áreas.
        if(!ValidacionDatos.estanCubertosCampos(campoNome) && !ValidacionDatos.estanCubertosCampos(campoAforo)){
            taboaAreas.getItems().addAll(super.getFachadaAplicacion().buscarArea(instalacion, null));
        } else {
            //Noutro caso, buscaremos segundo a información dos campos.
            //Creamos unha area cos datos recollidos:
            Area area = new Area(campoNome.getText(), AuxGUI.pasarEnteiro(campoAforo));
            taboaAreas.getItems().addAll(super.getFachadaAplicacion().buscarArea(instalacion, area));
        }
        //Selecciónase o primeiro item da táboa:
        taboaAreas.getSelectionModel().selectFirst();
    }

    /**
     * Método que representa as accións levadas a cabo ao premer o botón de limpado da táboa
     *
     * @param actionEvent A acción que tivo lugar.
     */
    public void btnLimparAction(ActionEvent actionEvent) {
        //Vaciaranse os campos e, depaso, listaranse todas as áreas dispoñibeis de novo:
        AuxGUI.vaciarCamposTexto(campoAforo, campoNome);
        //Aproveitamos entón para actualizar a táboa:
        //Eliminamos os items:
        taboaAreas.getItems().removeAll(taboaAreas.getItems());
        //Engadimos todas as areas tras consultalas:
        taboaAreas.getItems().addAll(super.getFachadaAplicacion().buscarArea(instalacion, null));
        //Seleccionamos o primeiro:
        taboaAreas.getSelectionModel().selectFirst();
    }

    /**
     * Método que representa as accións levadas a cabo ao premer o botón de modificación dunha área.
     *
     * @param actionEvent A acción que tivo lugar.
     */
    public void btnModificarAreaAction(ActionEvent actionEvent) {
        //Recuperamos primeiro a area seleccionada:
        Area area = (Area) taboaAreas.getSelectionModel().getSelectedItem();
        if(area != null){
            //Se non é null seguimos adiante.
            //Feito iso, facemos que a ventá visíbel sexa a de edición dunha instalación:
            this.controllerPrincipal.mostrarPantalla(IdPantalla.XESTIONAREA);
            //Accedemos ao controlador de creación dun area:
            vXestionAreaController cont = ((vXestionAreaController)this.controllerPrincipal.getControlador(IdPantalla.XESTIONAREA));
            //Recuperamos a área seleccionada e establecémola:
            cont.setArea((Area) taboaAreas.getSelectionModel().getSelectedItem());
            //Establecemos tamén a instalación de referencia:
            cont.setInstalacion(instalacion);

        } else {
            //Se non se tivese ningunha selección amósase un erro.
            this.getFachadaAplicacion().mostrarErro("Administración de areas", "Non hai celda seleccionada!");
        }
    }

    /**
     * Método que representa as accións levadas a cabo ao premer o botón de dar de baixa unha área.
     *
     * @param actionEvent A acción que tivo lugar.
     */
    public void btnDarBaixaAction(ActionEvent actionEvent)  {
        //Tomamos a área seleccionada da táboa:
        Area area = (Area) taboaAreas.getSelectionModel().getSelectedItem();
        if (area != null) {
            try {
                //Avaliamos o resultado da operación:
                TipoResultados res = getFachadaAplicacion().darDeBaixaArea(area);
                switch(res) {
                    case correcto:
                        //En caso de que se conseguira a baixa, avísase de que se fixo correctamente:
                        getFachadaAplicacion().mostrarInformacion("Administración de áreas",
                                "Baixa da área '" + area.getNome() + "' lograda correctamente. Agora podes modificar o aforo.");
                        break;
                    case referenciaRestrict:
                        //Nese caso, avisaremos que ten actividades sen rematar nesa área:
                        getFachadaAplicacion().mostrarInformacion("Administración de áreas",
                                "Non se pode dar de baixa a área '" + area.getNome() + "' , hai actividades " +
                                        "sen comezar planificadas nela.");
                        break;
                    case sitIncoherente:
                        //Se se ten unha situación incoherente, avisaremos que xa está dada de baixa:
                        getFachadaAplicacion().mostrarInformacion("Administración de áreas",
                                "Non se pode dar de baixa a área '" + area.getNome() + "' , pois xa está dada de baixa.");
                        break;
                }
                //Actualizamos para rematar a táboa de áreas.
                this.actualizarTaboa();
            } catch (ExcepcionBD excepcionBD) {
                excepcionBD.printStackTrace();
            }
        } else {
            getFachadaAplicacion().mostrarErro("Administración de Áreas",
                    "Debes seleccionar unha das áreas!");
        }
    }

    /**
     * Método que representa as accións levadas a cabo ao premer o botón de alta dunha área.
     *
     * @param actionEvent A acción que tivo lugar.
     */
    public void btnDarAltaAction(ActionEvent actionEvent) {
        Area area = (Area) taboaAreas.getSelectionModel().getSelectedItem();
        if (area != null) {
            try {
                //Intentamos a alta, avaliamos o resultado.
                TipoResultados res = this.getFachadaAplicacion().darDeAltaArea(area);
                switch (res){
                    case correcto:
                        //Se rematou ben, avisamos desa situación:
                        getFachadaAplicacion().mostrarInformacion("Administración de áreas",
                                "Alta da área '" + area.getNome() + "' levada a cabo satisfactoriamente. Pódese usar de novo a área para novas" +
                                        " actividades.");
                        break;
                    case sitIncoherente:
                        //Se houbo problemas porque a área xa está dada de alta, avisamos tamén:
                        getFachadaAplicacion().mostrarInformacion("Administración de áreas",
                                "Non se pode realizar a alta da área '" + area.getNome() + "' de novo, dado que a xa está dada de alta.");
                }
            } catch (ExcepcionBD excepcionBD) {
                excepcionBD.printStackTrace();
            }
            //Actualizamos a táboa correspondente en calquera caso.
            this.actualizarTaboa();
        }else {
            //En caso de non ter selección sobre a táboa, avisamos.
            getFachadaAplicacion().mostrarErro("Administración de Áreas",
                    "Debes seleccionar unha das áreas!");
        }
    }

    /**
     * Método que representa as accións levadas a cabo ao premer o botón de eliminación dunha área.
     *
     * @param actionEvent A acción que tivo lugar.
     */
    public void btnEliminarAreaAction(ActionEvent actionEvent) {
        //Recuperamos a área seleccionada sobre a táboa:
        Area area = (Area) taboaAreas.getSelectionModel().getSelectedItem();
        if (area != null) {
            //Pedimos primeiro unha confirmación por parte do usuario:
            if(getFachadaAplicacion().mostrarConfirmacion("Administración de Áreas",
                    "Desexas borrar a área '" + area.getNome() + "'?")  == ButtonType.OK) {
                try {
                    //De tela, intentamos o borrado:
                    TipoResultados res = getFachadaAplicacion().borrarArea(area);
                    //En función do resultado, avaliamos:
                    switch (res) {
                        case correcto:
                            //Se se puido facer avísase do suceso:
                            getFachadaAplicacion().mostrarInformacion("Administración de Áreas",
                                    "A área '" + area.getNome() + "' da instalación '" + instalacion.getNome() + "' " +
                                            "foi borrada satisfactoriamente.");
                            break;
                        case referenciaRestrict:
                            //Se a área ten actividades, avísase do propio.
                            getFachadaAplicacion().mostrarErro("Administración de Áreas",
                                    "Non se pode borrar a área '" + area.getNome() + "'. " +
                                            "Unha vez que ten material ou actividades rexistradas," +
                                            " a área non se pode eliminar!");
                            break;
                    }
                    //En calquera caso, actualizamos a táboa:
                    this.actualizarTaboa();
                } catch(ExcepcionBD e) {
                    //Amosamos un erro, en caso de capturar a excepción da parte da base de datos:
                    getFachadaAplicacion().mostrarErro("Administración de Áreas",
                            e.getMessage());
                }
            }
        } else {
            //Amosamos un erro tamén en caso de non ter selección sobre a táboa:
            getFachadaAplicacion().mostrarErro("Administración de Áreas",
                    "Debes seleccionar unha das áreas!");
        }
    }

    /**
     * Método que se executa cando se pulsa o botón de retorno.
     *
     * @param actionEvent A acción que tivo lugar.
     */
    public void btnVolverAction(ActionEvent actionEvent){
        //Regresamos á pantalla anterior e amosámola:
        controllerPrincipal.mostrarPantalla(IdPantalla.EDITARINSTALACION);
    }
}
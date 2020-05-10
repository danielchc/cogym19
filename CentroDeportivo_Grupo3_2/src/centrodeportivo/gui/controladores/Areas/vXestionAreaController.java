package centrodeportivo.gui.controladores.Areas;


import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.aplicacion.obxectos.tipos.TipoResultados;
import centrodeportivo.funcionsAux.ValidacionDatos;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.AuxGUI;
import centrodeportivo.gui.controladores.principal.IdPantalla;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import centrodeportivo.gui.controladores.principal.vPrincipalController;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Víctor Barreiro
 * Esta clase funcionará como controlador da ventá de inserción e modificación dos datos dunha área.
 */
public class vXestionAreaController extends AbstractController implements Initializable {
    /**
     * Atributos públicos - trátase dos campos da interface aos que queremos acceder:
     */
    public Button btnGardar;
    public Button btnRestaurar;
    public Button btnVolver;
    public TextField campoNome;
    public TextArea campoDescricion;
    public Label avisoCampos;
    public TextField campoCodigo;
    public TextField campoAforoMax;
    public TextField campoInstalacion;

    /**
     * Atributos privados: correspóndense con outras cuestións necesarias para certas xestións.
     */
    private vPrincipalController controllerPrincipal; //Referencia ao controlador da ventá principal.
    private Instalacion instalacion; //referencia á instalación na que se situará a área
    private Area area; //A área a editar (en caso de editar información).

    /**
     * Constructor do controlador da pantalla de nova area:
     * @param fachadaAplicacion A referencia á fachada da parte de aplicación.
     * @param controllerPrincipal A referencia ao controlador da ventá principal.
     */
    public vXestionAreaController(FachadaAplicacion fachadaAplicacion, vPrincipalController controllerPrincipal) {
        super(fachadaAplicacion);
        this.controllerPrincipal = controllerPrincipal;
    }

    /**
     * Método initialize, que se executa cada vez que se abre a ventá-
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Neste caso facemos un listener para controlar o valor que se introduce no aforo máximo.
        campoAforoMax.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (!t1.matches("\\d{0,4}")) {
                    campoAforoMax.setText(s);
                }
            }
        });
        //Por defecto, deixanse editables todos os campos e abertos:
        AuxGUI.habilitarCampos(campoAforoMax,campoDescricion,campoNome);
    }

    /**
     * Método sobreescrito que se executa tras inicializar a ventá, para aquelas restauracións que se queiran facer.
     */
    @Override
    public void reiniciarForm(){
        //Reestablecemos a área a null:
        this.area = null;
    }

    /**
     * Setter da instalación á que pertence a área a insertar/modificar:
     * @param instalacion A instalación a asignar.
     */
    public void setInstalacion(Instalacion instalacion) {
        this.instalacion = instalacion;
        this.campoInstalacion.setText(instalacion.getNome());
    }

    /**
     * Setter da área a editar. A súa execución implica que se acaba de insertar unha nova área
     * @param area A área a insertar.
     */
    public void setArea(Area area){
        //Asignamos a área.
        this.area = area;
        //En función da data de baixa, o que faremos será amosar o campo do aforo editable ou non:
        if(this.area.getDataBaixa() != null){
            AuxGUI.habilitarCampos(campoAforoMax);
        } else {
            AuxGUI.inhabilitarCampos(campoAforoMax);
        }
        //Asignamos os campos:
        this.campoCodigo.setText(Integer.toString(area.getCodArea()));
        this.campoNome.setText(area.getNome());
        this.campoAforoMax.setText(Integer.toString(area.getAforoMaximo()));
        this.campoDescricion.setText(area.getDescricion());
    }

    /**
     * Método que representa as accións realizadas ao premer o botón de gardado da area.
     * @param actionEvent A acción que tivo lugar.
     */
    public void btnGardarAction(ActionEvent actionEvent) {
        //Primeiro imos comprobar que os campos non están vacíos:
        if (!ValidacionDatos.estanCubertosCampos(campoNome, campoAforoMax)) {
            //Se hai campos non cubertos amósase unha mensaxe e non se fai nada máis.
            //Amosamos a mensaxe de erro:
            avisoCampos.setText("Campos Obrigatorios*!!!");
            AuxGUI.amosarCampos(avisoCampos);
            return;
        }

        if (!ValidacionDatos.isNatural(campoAforoMax.getText())) {
            //O mesmo que no caso dos campos vacíos: avisamos do erro e non se fai nada máis:
            avisoCampos.setText("Aforo máximo debe ser positivo!");
            AuxGUI.amosarCampos(avisoCampos);
            return;
        }

        //Comprobamos as lonxitudes:
        if(campoNome.getText().length() > 50 || campoDescricion.getText().length() > 200) {
            avisoCampos.setText("Lonxitudes incorrectas!!");
            AuxGUI.amosarCampos(avisoCampos);
            return;
        }

        //Creamos un obxecto Area con todos os datos facilitados
        Area area1 = new Area(instalacion, campoNome.getText(), campoDescricion.getText(), Integer.parseInt(campoAforoMax.getText()));

        //Accedemos á base de datos: intentamos que se efectúe sen problemas dito acceso.
        try {
            //A consulta pódenos devolver varios resultados en función da situación. Avaliámolos:
            TipoResultados res;
            if (area == null) {
                res = this.getFachadaAplicacion().EngadirArea(area1);
                //En función do resultado, mostraremos unha mensaxe ou outra:
                switch (res) {
                    case correcto:
                        //Correcto -> Imprimimos mensaxe de éxito co ID da instalación insertada:
                        this.getFachadaAplicacion().mostrarInformacion("Administración de Áreas",
                                "Creada a Area " + area1.getNome() +
                                        ". O seu id de Area é " + area1.getCodArea() +
                                        ", e o seu id de Instalación é " + area1.getInstalacion().getCodInstalacion() + ".");
                        //Volvemos á pantalla de xestión dunha instalación
                        this.controllerPrincipal.mostrarPantalla(IdPantalla.EDITARINSTALACION);
                        break;
                    case datoExiste:
                        //Se xa existía unha instalación co nome pasado, entón imprímese un erro e séguese na pantalla.
                        this.getFachadaAplicacion().mostrarErro("Administración de Areas",
                                "Xa hai unha area co nome '" + area1.getNome() +"' na instalación "+ area1.getInstalacion().getCodInstalacion()+ "'.");
                        break;
                        //Neste outro caso, mantémonos nesta pantalla.
                }
            } else {
                area1.setCodArea(area.getCodArea());
                area1.setDataBaixa(area.getDataBaixa());
                res = this.getFachadaAplicacion().modificarArea(area1);
                switch (res) {
                    case correcto:
                        //Facemos a asignación entre áreas:
                        area = area1;
                        //Correcto -> Imprimimos mensaxe de éxito co ID da instalación insertada:
                        this.getFachadaAplicacion().mostrarInformacion("Administración de Áreas",
                                "Modificada a Area " + area.getNome() +
                                        " con id " + area.getCodArea() +
                                        " e id de Instalación " + area.getInstalacion().getCodInstalacion() + ".");
                        //Neste caso, mantémonos nesta pantalla para poder seguir editando se se quere.
                        break;
                    case datoExiste:
                        //Se xa existía unha instalación co nome pasado, entón imprímese un erro e séguese na pantalla.
                        this.getFachadaAplicacion().mostrarErro("Administración de Areas",
                                "Xa hai unha area co nome '" + area1.getNome() + "' na instalación "+ area1.getInstalacion().getCodInstalacion()+ "'.");
                        break;
                }
            }
        } catch (ExcepcionBD e) {
            //Se se recibe unha excepción da base de datos, entón imprímese unha mensaxe informando.
            //Esa mensaxe obtense dentro da excepción, co método getMessage():
            this.getFachadaAplicacion().mostrarErro("Administración de Areas", e.getMessage());
        }
    }

    /**
     * Método que representa as accións realizadas ao premer o botón de limpado de campos.
     * @param actionEvent A acción que tivo lugar
     */
    public void btnRestaurarAction(ActionEvent actionEvent) {
        if(area == null){
            //O que imos a facer e limpar os tres campos, vaciar o que teñan:
            AuxGUI.vaciarCamposTexto(campoNome, campoDescricion, campoAforoMax);
        } else {
            //Reestablecemos os valores dos campos
            campoNome.setText(area.getNome());
            campoDescricion.setText(area.getDescricion());
            campoAforoMax.setText(Integer.toString(area.getAforoMaximo()));
        }
        //Ao mesmo tempo, ocultaremos o campo de aviso de incoherencias, por se apareceu:
        AuxGUI.ocultarCampos(avisoCampos);
    }

    /**
     * Método que representa as accións realizadas ao premer o botón de retorno.
     * @param actionEvent A acción que tivo lugar.
     */
    public void btnVolverAction(ActionEvent actionEvent) {
        //Se se está editando unha área vólvese á pantalla de administración:
        if(area != null){
            controllerPrincipal.mostrarPantalla(IdPantalla.ADMINAREA);
        } else {
            //Se non, volvemos á de xestión da instalación:
            controllerPrincipal.mostrarPantalla(IdPantalla.EDITARINSTALACION);
        }
    }
}

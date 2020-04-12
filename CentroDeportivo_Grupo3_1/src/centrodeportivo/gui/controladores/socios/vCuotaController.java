package centrodeportivo.gui.controladores.socios;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.actividades.Actividade;
import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.aplicacion.obxectos.tarifas.Cuota;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public class vCuotaController extends AbstractController implements Initializable {

    /**
     * Atributos do fxml
     */
    public TextField campoSocio;
    public TextField campoTarifa;
    public TextField campoPrezoBase;
    public TextField campoPrezoExtras;
    public TextField campoMaxActividades;
    public ComboBox campoActividades;
    public ComboBox campoCursos;
    public Label campoPrezoTotal;
    public TreeView campoPrezos;

    /**
     * Atributos do controlador
     */
    private Usuario usuario;

    /**
     * Constructor do controlador.
     * @param fachadaAplicacion Fachada da aplicación.
     * @param vPrincipalController Controlador da vista principal
     */
    public vCuotaController(FachadaAplicacion fachadaAplicacion, vPrincipalController vPrincipalController) {
        super(fachadaAplicacion,vPrincipalController);
        this.usuario=super.getvPrincipalController().obterUsuarioLogeado();
    }

    /**
     * Método para inicializar a vista.
     * Consultanse os datos da cuota do mes actual do usuario loggeado.
     * Despois cárgase un resumo dos datos para mostralos.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Cuota cuota=super.getFachadaAplicacion().consultarCuota(this.usuario.getLogin());
        this.campoSocio.setText(cuota.getUsuario().getNome());
        this.campoTarifa.setText(cuota.getTarifa().getNome());
        this.campoPrezoBase.setText(cuota.getTarifa().getPrezoBase() +" €");
        this.campoPrezoExtras.setText(cuota.getTarifa().getPrezoExtras() +" €");
        this.campoMaxActividades.setText(String.valueOf(cuota.getTarifa().getMaxActividades()));
        this.campoPrezoTotal.setText(cuota.getTotalPrezo()+" €");

        //actividades
        this.campoActividades.getItems().addAll(cuota.getActividadesMes());
        this.campoActividades.setPlaceholder(new Label("Sen actividades..."));
        this.campoActividades.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                return;
            }
        });

        //cursos
        this.campoCursos.getItems().addAll(cuota.getCursosMes());
        this.campoCursos.setPlaceholder(new Label("Sen cursos..."));
        this.campoCursos.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                return;
            }
        });

        //tabla precios
        TreeItem<String> actItem=new TreeItem<String>("Prezos Actividades");
        actItem.getChildren().add(new TreeItem<String>("Total actividades realizadas: "+cuota.getActividadesMes().size()));
        actItem.getChildren().add(new TreeItem<String>("Base actividades: "+cuota.getTarifa().getPrezoBase()));
        actItem.getChildren().add(new TreeItem<String>("Extra actividades: "+cuota.getPrezoActividadesExtra()));
        actItem.getChildren().add(new TreeItem<String>("Total actividades: "+cuota.getTotalActividades()));

        TreeItem<String> curItem=new TreeItem<String>("Prezos Cursos");
        curItem.getChildren().add(new TreeItem<String>("Total cursos realizados: "+cuota.getCursosMes().size()));
        curItem.getChildren().add(new TreeItem<String>("Total cursos: "+cuota.getTotalCursos()));

        TreeItem<String> root=new TreeItem<>("Desglose");
        root.getChildren().add(actItem);
        root.getChildren().add(curItem);
        this.campoPrezos.setRoot(root);
    }
}

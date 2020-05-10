package centrodeportivo.gui.controladores.socios;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.Mensaxe;
import centrodeportivo.aplicacion.obxectos.RexistroFisioloxico;
import centrodeportivo.aplicacion.obxectos.actividades.Actividade;
import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.aplicacion.obxectos.incidencias.Incidencia;
import centrodeportivo.aplicacion.obxectos.tarifas.Cuota;
import centrodeportivo.aplicacion.obxectos.usuarios.Socio;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
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
    public TableView tablaActividades;
    public TableView tablaCursos;
    public Label campoPrezoTotal;
    public TreeView campoPrezos;
    public PieChart graficaActividades;

    /**
     * Atributos do controlador
     */
    private Socio socio;
    private Cuota cuota;

    /**
     * Constructor do controlador.
     *
     * @param fachadaAplicacion    Fachada da aplicación.
     * @param vPrincipalController Controlador da vista principal
     */
    public vCuotaController(FachadaAplicacion fachadaAplicacion, vPrincipalController vPrincipalController) {
        super(fachadaAplicacion, vPrincipalController);
    }

    /**
     * Método para inicializar a vista.
     * Consultanse os datos da cuota do mes actual do usuario loggeado.
     * Despois cárgase un resumo dos datos para mostralos.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Usuario usuario = super.getvPrincipalController().obterUsuarioLogeado();
        if (usuario instanceof Socio) this.socio = (Socio) usuario;

        socio.setTarifa(super.getFachadaAplicacion().consultarTarifaSocio(socio.getLogin()));
        this.cuota = super.getFachadaAplicacion().consultarCuota(socio);

        this.campoSocio.setText(cuota.getUsuario().getNome());
        this.campoTarifa.setText(cuota.getTarifa().getNome());
        this.campoPrezoBase.setText(cuota.getTarifa().getPrezoBase() + " €");
        this.campoPrezoExtras.setText(cuota.getTarifa().getPrezoExtras() + " €");
        this.campoMaxActividades.setText(String.valueOf(cuota.getTarifa().getMaxActividades()));
        this.campoPrezoTotal.setText(cuota.getTotalPrezo() + " €");

        cargarActividadesTabla();
        generarGraficaActividades();
        cargarCursosTabla();
        cargarDesgloseTabla();

    }

    /**
     * Método para cargar as actividades realizadas no mes.
     */
    private void cargarActividadesTabla() {
        this.tablaActividades.setPlaceholder(new Label("Non se realizou ningunha actividade."));

        TableColumn<Actividade, String> dataColum = new TableColumn<>("Data");
        dataColum.setCellValueFactory(
                c -> new SimpleStringProperty(
                        new SimpleDateFormat("dd/MM/yyyy").format(((Actividade) c.getValue()).getData())
                )
        );

        TableColumn<Actividade, String> lugarColum = new TableColumn<>("Lugar");
        lugarColum.setCellValueFactory(
                c -> new SimpleStringProperty(
                        c.getValue().getArea().getNome()
                )
        );

        TableColumn<Actividade, String> tipoActividadeColum = new TableColumn<>("Tipo de Actividade");
        tipoActividadeColum.setCellValueFactory(
                c -> new SimpleStringProperty(
                        c.getValue().getTipoActividade().getNome()
                )
        );

        TableColumn<Actividade, String> nomeColum = new TableColumn<>("Nome da Actividade");
        nomeColum.setCellValueFactory(
                c -> new SimpleStringProperty(
                        c.getValue().getNome()
                )
        );

        this.tablaActividades.getColumns().addAll(dataColum, lugarColum, tipoActividadeColum, nomeColum);
        this.tablaActividades.getItems().addAll(this.cuota.getActividadesMes());
    }

    /**
     * Método para cargar os cursos realizados no mes.
     */
    private void cargarCursosTabla() {
        this.tablaCursos.setPlaceholder(new Label("Non se realizou ningún curso."));

        TableColumn<Actividade, Integer> codColum = new TableColumn<>("Código");
        codColum.setCellValueFactory(new PropertyValueFactory<>("codCurso"));
        TableColumn<Actividade, String> nomeColum = new TableColumn<>("Nome do Curso");
        nomeColum.setCellValueFactory(new PropertyValueFactory<>("nome"));
        TableColumn<Actividade, String> descrColum = new TableColumn<>("Descrición");
        descrColum.setCellValueFactory(new PropertyValueFactory<>("descricion"));


        this.tablaCursos.getColumns().addAll(codColum, nomeColum, descrColum);
        this.tablaCursos.getItems().addAll(this.cuota.getCursosMes());
    }

    /**
     * Método para cargar a tabla de desglose.
     */
    private void cargarDesgloseTabla() {
        //tabla precios
        TreeItem<String> actItem = new TreeItem<String>("Prezos Actividades");
        actItem.getChildren().add(new TreeItem<String>("Total actividades realizadas: " + cuota.getActividadesMes().size()));
        actItem.getChildren().add(new TreeItem<String>("Base actividades: " + cuota.getTarifa().getPrezoBase()));
        actItem.getChildren().add(new TreeItem<String>("Extra actividades: " + cuota.getPrezoActividadesExtra()));
        actItem.getChildren().add(new TreeItem<String>("Total actividades: " + cuota.getTotalActividades()));

        TreeItem<String> curItem = new TreeItem<String>("Prezos Cursos");
        curItem.getChildren().add(new TreeItem<String>("Total cursos realizados: " + cuota.getCursosMes().size()));
        curItem.getChildren().add(new TreeItem<String>("Total cursos: " + cuota.getTotalCursos()));

        TreeItem<String> root = new TreeItem<>("Desglose");
        root.getChildren().add(actItem);
        root.getChildren().add(curItem);
        this.campoPrezos.setRoot(root);
    }

    private void generarGraficaActividades() {
        HashMap<String, Integer> numTipos = new HashMap<>();
        for (Actividade a : this.cuota.getActividadesMes()) {
            String nTipo = a.getTipoActividade().getNome();
            if (numTipos.get(nTipo) != null) {
                Integer old = numTipos.get(nTipo);
                old = old + 1;
                numTipos.replace(nTipo, old);
            } else {
                numTipos.put(nTipo, 1);
            }
        }

        for (String s : numTipos.keySet()) {
            PieChart.Data parte = new PieChart.Data(s, numTipos.get(s));
            this.graficaActividades.getData().add(parte);
        }
    }
}

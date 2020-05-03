package centrodeportivo.gui.controladores.Cursos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.actividades.Curso;
import centrodeportivo.funcionsAux.ValidacionDatos;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.AuxGUI;
import centrodeportivo.gui.controladores.principal.IdPantalla;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;


/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Víctor Barreiro
 * Clase que funciona como controlador da pantalla de administración dos cursos (onde se amosa unha lista dos cursos
 * rexistrados).
 */
public class vAdministrarCursosController extends AbstractController implements Initializable {

    /**
     * Atributos públicos: correspóndense con partes da pantalla correspondente (que se atopan no fxml):
     */
    public TextField campoNome;
    public Button btnBuscar;
    public Button btnLimpar;
    public TableView taboaCursos;
    public Button btnXestionar;
    public CheckBox checkResaltar;

    /**
     * Atributos privados: neste caso só gardamos a referencia ao controlador da ventá principal:
     */
    private vPrincipalController controllerPrincipal;

    /**
     * Constructor do controlador da ventá de administración de cursos.
     * @param fachadaAplicacion Referencia á fachada da parte de aplicación.
     * @param controllerPrincipal Referencia ao controlador da ventá principal.
     */
    public vAdministrarCursosController(FachadaAplicacion fachadaAplicacion, vPrincipalController controllerPrincipal){
        //Chamamos ao constructor da clase pai.
        super(fachadaAplicacion);
        //Asignamos o parámetro pasado de controlador da ventá principal ao atributo correspondente.
        this.controllerPrincipal = controllerPrincipal;
    }

    /**
     * Método co que se inicializan as compoñentes desta ventá:
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //O primeiro paso será inicializar a táboa na que se amosan os cursos:
        TableColumn<Curso, String> nomeColumn = new TableColumn<>("Nome");
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));

        //Á data de inicio dámoslle tamén un formato, e que sexa cómodo para nós (día/mes/ano):
        TableColumn<Curso, String> dataInicioColumn = new TableColumn<>("Data Inicio");
        dataInicioColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Curso, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Curso, String> p) {
                //Haberá que comprobar que a data de inicio non teña nulos (podería ser!)
                if (p.getValue().getDataInicio() != null) {
                    return new SimpleStringProperty(new SimpleDateFormat("dd/MM/yyyy").format(p.getValue().getDataInicio()));
                } else {
                    //En caso de que teña nulos, será porque o curso inda non ten fixada a data de inicio:
                    return new SimpleStringProperty("Sen inicio fixado");
                }
            }
        });
        //Número de actividades:
        TableColumn<Curso, Integer> numActividadesColumn = new TableColumn<>("Numero de Actividades");
        numActividadesColumn.setCellValueFactory(new PropertyValueFactory<>("numActividades"));

        //Duración: personaliceina para que aparezan as horas e os minutos separados.
        TableColumn<Curso, String> duracionColumn = new TableColumn<>("Duración");
        duracionColumn.setCellValueFactory(c -> new SimpleStringProperty(
                //Basicamente collo a parte enteira e logo a decimal multiplicada por 60:
                c.getValue().getDuracion().intValue() + "h, " +
                        (int) ((c.getValue().getDuracion().floatValue()-c.getValue().getDuracion().intValue())*60) + "m"
        ));

        //O booleano aberto: facemos que en función do seu valor se imprima un si ou un non (en lugar de ter "true" ou
        //"false").
        TableColumn<Curso, String> abertoColumn = new TableColumn<>("Aberto");
        abertoColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Curso, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Curso, String> p) {
                if (p.getValue().isAberto()) {
                    return new SimpleStringProperty("Si");
                } else {
                    return new SimpleStringProperty("Non");
                }
            }
        });

        //Engadimos o seguinte para poder resaltar dunha cor diferente os cursos finalizados:
        taboaCursos.setRowFactory(tv -> new TableRow<Curso>() {
            @Override
            public void updateItem(Curso item, boolean empty) {
                super.updateItem(item, empty);
                if ((item != null) &&  (item.getDataFin()!=null) &&
                        (item.getDataFin().before(new Date(System.currentTimeMillis()))) &&
                        checkResaltar.isSelected()){
                    //Temos asociada unha folla de estilo, de xeito que se engadirá a opción "resaltar" para que se
                    //resalten as filas que cumpran a condición de ter un curso rematado tendo o checkbox de resaltar
                    //activado:
                    getStyleClass().add("resaltar");
                } else {
                    //Se non se cumpren as condicións anteriores, eliminamos esa condición da fila.
                    getStyleClass().remove("resaltar");
                }
            }
        });
        //Metemos as columnas creadas na táboa:
        taboaCursos.getColumns().addAll(nomeColumn, dataInicioColumn, numActividadesColumn, duracionColumn, abertoColumn);
        //Buscamos os datos dos cursos e engadímolos.
        taboaCursos.getItems().addAll(getFachadaAplicacion().consultarCursos(null));
        //Modelo de selección:
        taboaCursos.getSelectionModel().selectFirst();
    }

    /**
     * Método que representa as accións que se levan a cabo ao premer o botón de busca dun curso.
     * @param actionEvent A acción que tivo lugar.
     */
    public void btnBuscarAction(ActionEvent actionEvent) {
        //Se o campo de nome non se cubre, o que faremos será listar todos os cursos, se non, faremos búsqueda por nome:
        if(!ValidacionDatos.estanCubertosCampos(campoNome)){
            //Listar todos os cursos -> Pasamos a consultarCursos parámetro null:
            this.actualizarTaboaCursos(getFachadaAplicacion().consultarCursos(null));
        } else {
            //Buscar por un curso: pasamos un curso co campo de busca: o nome:
            this.actualizarTaboaCursos(getFachadaAplicacion().consultarCursos(new Curso(campoNome.getText())));
        }
    }

    /**
     * Método que representa as accións que teñen lugar ao premer o botón de limpar información
     * @param actionEvent A acción que tivo lugar.
     */
    public void btnLimparAction(ActionEvent actionEvent) {
        //Ao limpar os campos, o que facemos será poñer o campo de búsqueda baleiro e refrescar a táboa sen filtros.
        AuxGUI.vaciarCamposTexto(campoNome);
        //Listar todos os cursos -> Pasamos a consultarCursos parámetro null:
        this.actualizarTaboaCursos(getFachadaAplicacion().consultarCursos(null));
    }

    /**
     * Método que representa as accións que teñen lugar ao premer o botón de xestión dun curso:
     * @param actionEvent A acción que tivo lugar
     */
    public void btnXestionarAction(ActionEvent actionEvent) {
        //Neste caso, o que teremos que facer é recopilar os datos completos do curso seleccionado:
        //Para iso, empezamos mirando se hai unha selección feita:
        Curso selected = (Curso) taboaCursos.getSelectionModel().getSelectedItem();
        if(selected != null){
            //Gardamos o resultado noutra variable para refrescar toda a información
            //Iso será o que se lle pase á ventá de xestión dun curso:
            Curso res = getFachadaAplicacion().recuperarDatosCurso(selected);
            if(res != null){
                //Se houbo resultado, procedemos a pasar á ventá de xestión de cursos este curso:
                //Chamamos á ventá.
                controllerPrincipal.mostrarPantalla(IdPantalla.XESTIONCURSO);
                //E agora facemos a asignación do curso (para que se amose toda a súa información):
                //Recuperamos o controlador
                vXestionCursoController cont = (vXestionCursoController) controllerPrincipal.getControlador(IdPantalla.XESTIONCURSO);
                //Establecemos o curso:
                cont.setCurso(res);
            } else {
                //Se houbo problemas ao recuperar a información, avisamos do erro:
                this.getFachadaAplicacion().mostrarErro("Administración de Cursos",
                        "Produciuse un erro ao recuperar os datos do curso.");
                //Para evitar problemas maiores, actualizaremos a táboa de cursos (se vale null é probable que se borrara):
                actualizarTaboaCursos(getFachadaAplicacion().consultarCursos(null));
            }
        } else {
            //Se non se ten selección, indícase que hai que facela primeiro (podería ser que a lista estivese vacía):
            this.getFachadaAplicacion().mostrarErro("Administración de Cursos",
                    "Selecciona un curso primeiro!");
        }
    }

    /**
     *
     * @param actionEvent
     */
    public void checkResaltarAction(ActionEvent actionEvent){
        taboaCursos.refresh();
    }

    private void actualizarTaboaCursos(ArrayList<Curso> cursos){
        //Primeiro borramos todos os items que hai amosados na táboa:
        taboaCursos.getItems().removeAll(taboaCursos.getItems());
        //Engadimos os cursos pasados:
        taboaCursos.getItems().addAll(cursos);
        //Por defecto, seleccionarase o primeiro:
        taboaCursos.getSelectionModel().selectFirst();
    }

}

package centrodeportivo.aplicacion;

import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.actividades.Curso;
import centrodeportivo.aplicacion.obxectos.actividades.TipoActividade;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.aplicacion.obxectos.area.Material;
import centrodeportivo.aplicacion.obxectos.area.TipoMaterial;
import centrodeportivo.aplicacion.obxectos.tipos.TipoResultados;
import centrodeportivo.aplicacion.xestion.*;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.baseDatos.FachadaBD;
import centrodeportivo.gui.FachadaGUI;
import centrodeportivo.gui.controladores.vLoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class FachadaAplicacion extends Application {
    private FachadaGUI fachadaGUI;
    private FachadaBD fachadaBD;
    private XestionUsuarios xestionUsuarios;
    private XestionInstalacions xestionInstalacions;
    private XestionTiposActividades xestionActividades;
    private XestionCursos xestionCursos;
    private XestionTipoMaterial xestionTipoMaterial;
    private XestionMaterial xestionMaterial;

    public FachadaAplicacion() throws IOException, SQLException {
        this.fachadaGUI = new FachadaGUI(this);
        this.fachadaBD = new FachadaBD(this);
        this.xestionUsuarios = new XestionUsuarios(fachadaGUI, fachadaBD);
        this.xestionInstalacions = new XestionInstalacions(fachadaGUI, fachadaBD);
        this.xestionActividades = new XestionTiposActividades(fachadaGUI, fachadaBD);
        this.xestionCursos = new XestionCursos(fachadaGUI, fachadaBD);
        this.xestionTipoMaterial = new XestionTipoMaterial(fachadaGUI, fachadaBD);
        this.xestionMaterial = new XestionMaterial(fachadaGUI, fachadaBD);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/centrodeportivo/gui/vistas/vLogin.fxml"));
        loader.setController(new vLoginController(this));
        Parent root = loader.load();

        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.setTitle("Centro Deportivo");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public boolean validarUsuario(String login, String contrasinal) {
        return xestionUsuarios.validarUsuario(login, contrasinal);
    }

    public Usuario consultarUsuario(String login) {
        return xestionUsuarios.consultarUsuario(login);
    }

    public void mostrarVentaSocios(Usuario usuario) throws IOException {
        fachadaGUI.mostrarVentaSocios(usuario);
    }

    public void mostrarVentaPersoal(Usuario loggedUser) throws IOException {
        fachadaGUI.mostrarVentaPersoal(loggedUser);
    }

    public void mostrarAdvertencia(String titulo, String texto) {
        fachadaGUI.mostrarAdvertencia(titulo, texto);
    }

    public void mostrarErro(String titulo, String texto) {
        fachadaGUI.mostrarErro(titulo, texto);
    }

    public void mostrarInformacion(String titulo, String texto) {
        fachadaGUI.mostrarInformacion(titulo, texto);
    }

    public ButtonType mostrarConfirmacion(String titulo, String texto) {
        return fachadaGUI.mostrarConfirmacion(titulo, texto);
    }

    /*
        Xestion instalacións
     */
    public TipoResultados darAltaInstalacion(Instalacion instalacion) throws ExcepcionBD {
        return xestionInstalacions.darAltaInstalacion(instalacion);
    }

    public TipoResultados borrarInstalacion(Instalacion instalacion) throws ExcepcionBD {
        return xestionInstalacions.borrarInstalacion(instalacion);
    }

    public TipoResultados modificarInstalacion(Instalacion instalacion) throws ExcepcionBD {
        return xestionInstalacions.modificarInstalacion(instalacion);
    }

    public ArrayList<Instalacion> buscarInstalacions(Instalacion instalacion) {
        return xestionInstalacions.buscarInstalacions(instalacion);
    }

    public ArrayList<Instalacion> listarInstalacions() {
        return xestionInstalacions.listarInstalacions();
    }

    /*
        Xestión TIPOS de actividade
     */

    public TipoResultados crearTipoActividade(TipoActividade tipoActividade) throws ExcepcionBD {
        return xestionActividades.crearTipoActividade(tipoActividade);
    }

    public TipoResultados modificarTipoActividade(TipoActividade tipoActividade) throws ExcepcionBD {
        return xestionActividades.modificarTipoActividade(tipoActividade);
    }

    public TipoResultados eliminarTipoActividade(TipoActividade tipoActividade) throws ExcepcionBD {
        return xestionActividades.eliminarTipoActividade(tipoActividade);
    }

    public ArrayList<TipoActividade> listarTiposActividades() {
        return xestionActividades.listarTiposActividades();
    }

    public ArrayList<TipoActividade> buscarTiposActividades(TipoActividade tipoActividade) {
        return xestionActividades.buscarTiposActividades(tipoActividade);
    }

    /*
        Xestión cursos
     */

    public TipoResultados rexistrarCurso(Curso curso) throws ExcepcionBD {
        return xestionCursos.rexistrarCurso(curso);
    }

    public TipoResultados modificarCurso(Curso curso) throws ExcepcionBD {
        return xestionCursos.modificarCurso(curso);
    }

    public TipoResultados cancelarCurso(Curso curso) throws ExcepcionBD {
        return xestionCursos.cancelarCurso(curso);
    }

    public ArrayList<Curso> consultarCursos(Curso curso) {
        return xestionCursos.consultarCursos(curso);
    }

    public Curso recuperarDatosCurso(Curso curso) {
        return xestionCursos.recuperarDatosCurso(curso);
    }

    /*
        Xestión TIPOS de material
     */
    public TipoResultados darAltaTipoMaterial(TipoMaterial tipoMaterial) throws ExcepcionBD {
        return xestionTipoMaterial.darAltaTipoMaterial(tipoMaterial);
    }

    public TipoResultados borrarTipoMaterial(TipoMaterial tipoMaterial) throws ExcepcionBD {
        return xestionTipoMaterial.borrarTipoMaterial(tipoMaterial);
    }

    public ArrayList<TipoMaterial> listarTiposMateriais() {
        return xestionTipoMaterial.listarTiposMateriais();
    }

    /*
        Xestión materiais
     */

    public TipoResultados darAltaMaterial(Material material) throws ExcepcionBD {
        return xestionMaterial.darAltaMaterial(material);
    }

    public TipoResultados borrarMaterial(Material material) throws ExcepcionBD {
        return xestionMaterial.borrarMaterial(material);
    }

    public TipoResultados modificarMaterial(Material material) throws ExcepcionBD {
        return xestionMaterial.modificarMaterial(material);
    }

    public ArrayList<Material> listarMateriais() {
        return xestionMaterial.listarMateriais();
    }

}

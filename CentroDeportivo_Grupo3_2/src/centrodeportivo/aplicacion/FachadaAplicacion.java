package centrodeportivo.aplicacion;

import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.aplicacion.xestion.XestionInstalacions;
import centrodeportivo.funcionsAux.Criptografia;
import centrodeportivo.aplicacion.obxectos.Mensaxe;
import centrodeportivo.aplicacion.obxectos.tipos.TipoUsuario;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.aplicacion.xestion.XestionUsuarios;
import centrodeportivo.baseDatos.FachadaBD;
import centrodeportivo.gui.FachadaGUI;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class FachadaAplicacion {
    private FachadaGUI fachadaGUI;
    private FachadaBD fachadaBD;
    private XestionUsuarios xestionUsuarios;
    private XestionInstalacions xestionInstalacions;

    public FachadaAplicacion() throws IOException, SQLException {
        this.fachadaGUI=new FachadaGUI(this);
        this.fachadaBD=new FachadaBD(this);
        this.xestionUsuarios=new XestionUsuarios(fachadaGUI,fachadaBD);
        this.xestionInstalacions = new XestionInstalacions(fachadaGUI, fachadaBD);
    }
    public boolean validarUsuario(String login,String password) {
        return xestionUsuarios.validarUsuario(login, Criptografia.hashSHA256(password));
    }

    public TipoUsuario consultarTipo(String login)  {
        return xestionUsuarios.consultarTipo(login);
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

    public void mostrarAdvertencia(String titulo,String texto) {
        fachadaGUI.mostrarAdvertencia(titulo, texto);
    }

    public void mostrarErro(String titulo,String texto) {
        fachadaGUI.mostrarErro(titulo, texto);
    }

    public void mostrarInformacion(String titulo,String texto){
        fachadaGUI.mostrarInformacion(titulo, texto);
    }

    public ButtonType mostrarConfirmacion(String titulo, String texto){
        return fachadaGUI.mostrarConfirmacion(titulo, texto);
    }


    /*
        Xestion incidencias
     */

    /*
        Xestion instalacións
     */
    public void darAltaInstalacion(Instalacion instalacion){
        xestionInstalacions.darAltaInstalacion(instalacion);
    }

    public void borrarInstalacion(Instalacion instalacion){
        xestionInstalacions.borrarInstalacion(instalacion);
    }

    public void modificarInstalacion(Instalacion instalacion){
        xestionInstalacions.modificarInstalacion(instalacion);
    }

    public ArrayList<Instalacion> buscarInstalacions(Instalacion instalacion){
        return xestionInstalacions.buscarInstalacions(instalacion);
    }

    public ArrayList<Instalacion> listarInstalacions(){
        return xestionInstalacions.listarInstalacions();
    }


}

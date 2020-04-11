package centrodeportivo.aplicacion.xestion;

import centrodeportivo.aplicacion.obxectos.RexistroFisioloxico;
import centrodeportivo.aplicacion.obxectos.actividades.TipoActividade;
import centrodeportivo.aplicacion.obxectos.tarifas.Cuota;
import centrodeportivo.aplicacion.obxectos.tipos.ContasPersoa;
import centrodeportivo.aplicacion.obxectos.tipos.TipoUsuario;
import centrodeportivo.aplicacion.obxectos.usuarios.PersoaFisica;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.baseDatos.FachadaBD;
import centrodeportivo.gui.FachadaGUI;

import java.util.ArrayList;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public class XestionUsuarios {
    private FachadaGUI fachadaGUI;
    private FachadaBD fachadaBD;

    public XestionUsuarios(FachadaGUI fachadaGUI,FachadaBD fachadaBD) {
        this.fachadaGUI=fachadaGUI;
        this.fachadaBD=fachadaBD;
    }

    public boolean existeUsuario(String login)  {
        return fachadaBD.existeUsuario(login);
    }

    public boolean existeDNI(String dni)  {
        return fachadaBD.existeDNI(dni);
    }

    public boolean existeNUSS(String nuss) {
        return fachadaBD.existeNUSS(nuss);
    }

    public ContasPersoa contasPersoaFisica(String dni){
        return fachadaBD.contasPersoaFisica(dni);
    }

    public boolean validarUsuario(String login,String password) {
        return fachadaBD.validarUsuario(login,password);
    }

    public void insertarUsuario(Usuario usuario) {
        fachadaBD.insertarUsuario(usuario);
    }

    public void actualizarUsuario(String loginVello,Usuario usuario)  {
        fachadaBD.actualizarUsuario(loginVello,usuario);
    }

    public void darBaixaUsuario(String login)  {
        fachadaBD.darBaixaUsuario(login);
    }

    public void darAltaUsuario(String login) {
        fachadaBD.darAltaUsuario(login);
    }

    public TipoUsuario consultarTipo(String login)  {
        return fachadaBD.consultarTipo(login);
    }

    public Usuario consultarUsuario(String login, boolean estaBaixa) {
        return fachadaBD.consultarUsuario(login,estaBaixa);
    }

    public ArrayList<Usuario> buscarUsuarios(String login,String nome,TipoUsuario filtroTipo,boolean usuariosDeBaixa)  {
        return fachadaBD.buscarUsuarios(login,nome,filtroTipo,usuariosDeBaixa);
    }

    public ArrayList<Usuario> buscarUsuarios(String login,String nome,TipoUsuario filtroTipo)  {
        return fachadaBD.buscarUsuarios(login,nome,filtroTipo,false);
    }

    public ArrayList<Usuario> listarUsuarios(TipoUsuario filtro)  {
        return fachadaBD.buscarUsuarios("","",filtro,false);
    }

    public ArrayList<Usuario> listarUsuarios()  {
        return fachadaBD.buscarUsuarios("","",TipoUsuario.Todos,false);
    }

    public Cuota consultarCuota(String login){
        return fachadaBD.consultarCuota(login);
    }

    public ArrayList<RexistroFisioloxico> listarRexistros(String login){
        return fachadaBD.listarRexistros(login);
    }

    public void insertarRexistro(RexistroFisioloxico rexistroFisioloxico){
        fachadaBD.insertarRexistro(rexistroFisioloxico);
    }

    public void eliminarRexistro(RexistroFisioloxico rexistroFisioloxico){
        fachadaBD.eliminarRexistro(rexistroFisioloxico);
    }

    public PersoaFisica consultarPersoaFisica(String DNI){
        return fachadaBD.consultarPersoaFisica(DNI);
    }
    public ArrayList<TipoActividade> listarCapacidades(String login){
        return fachadaBD.listarCapacidades(login);
    }

    public void engadirCapadidade(String login, TipoActividade tipoActividade){
        fachadaBD.engadirCapadidade(login, tipoActividade);
    }

    public void eliminarCapacidade(String login, TipoActividade tipoActividade){
        fachadaBD.eliminarCapacidade(login, tipoActividade);
    }

}

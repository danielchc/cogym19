package centrodeportivo.baseDatos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.Material;
import centrodeportivo.aplicacion.obxectos.RexistroFisioloxico;
import centrodeportivo.aplicacion.obxectos.actividades.TipoActividade;
import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.aplicacion.obxectos.tarifas.Cuota;
import centrodeportivo.aplicacion.obxectos.tipos.ContasPersoa;
import centrodeportivo.aplicacion.obxectos.tipos.TipoIncidencia;
import centrodeportivo.aplicacion.obxectos.usuarios.PersoaFisica;
import centrodeportivo.aplicacion.obxectos.usuarios.Persoal;
import centrodeportivo.funcionsAux.Criptografia;
import centrodeportivo.aplicacion.obxectos.incidencias.Incidencia;
import centrodeportivo.aplicacion.obxectos.Mensaxe;
import centrodeportivo.aplicacion.obxectos.tarifas.Tarifa;
import centrodeportivo.aplicacion.obxectos.tipos.TipoUsuario;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.gui.FachadaGUI;

import java.io.FileInputStream;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public final class FachadaBD {

    /**
     * Atributos
     */
    private FachadaAplicacion fachadaAplicacion;
    private FachadaGUI fachadaGUI;
    private Connection conexion;
    private DAOUsuarios daoUsuarios;
    private DAOTarifas daoTarifas;
    private DAOMensaxes daoMensaxes;
    private DAOIncidencias daoIncidencias;
    private DAOInstalacions daoInstalacions;
    private DAOActividades daoActividades;

    /**
     * Este é o constructor da fachada de base de datos.
     * Establecese a conexión coa base de datos mediante o arquivo properties.
     * (Hai dúas versións do arquivo, unha cifrada e outra que non, ambas son compatibles).
     * @param fachadaAplicacion fachada da aplicación
     * @throws ExcepcionBD
     */
    public FachadaBD(FachadaAplicacion fachadaAplicacion) throws ExcepcionBD {
        this.fachadaAplicacion = fachadaAplicacion;
        Properties configuracion = new Properties();
        FileInputStream prop;

        //prop = new FileInputStream("baseDatos.properties");
        //configuracion.load(prop);
        //prop.close();
        try {
            String conf = new String(Criptografia.desencriptar(Files.readAllBytes(Paths.get("baseDatos.encrypted"))));
            //System.out.println(conf);
            configuracion.load(new StringReader(conf));
        } catch (Exception ex) {
            System.out.println("Non se pudo cargar o arquivo cifrado");
            System.exit(1);
        }

        Properties usuario = new Properties();
        usuario.setProperty("user", configuracion.getProperty("usuario"));
        usuario.setProperty("password", configuracion.getProperty("clave"));
        String con = String.format("jdbc:%s://%s:%s/%s", configuracion.getProperty("gestor"), configuracion.getProperty("servidor"), configuracion.getProperty("puerto"), configuracion.getProperty("baseDatos"));
        try {
            this.conexion = DriverManager.getConnection(con, usuario);
            this.conexion.setAutoCommit(false);
        } catch (SQLException e) {
            //fachadaAplicacion.mostrarErro("Erro","Erro na conexión ca base de datos");
            throw new ExcepcionBD(this.conexion, e);
            //System.exit(1);
        }
        this.daoUsuarios = new DAOUsuarios(this.conexion, this.fachadaAplicacion);
        this.daoTarifas = new DAOTarifas(this.conexion, this.fachadaAplicacion);
        this.daoMensaxes = new DAOMensaxes(this.conexion, this.fachadaAplicacion);
        this.daoIncidencias = new DAOIncidencias(this.conexion, this.fachadaAplicacion);
        this.daoInstalacions = new DAOInstalacions(this.conexion, this.fachadaAplicacion);
        this.daoActividades = new DAOActividades(this.conexion, this.fachadaAplicacion);
    }

    /*
        Funcions DAOUsuarios
     */
    public boolean existeUsuario(String login) {
        return daoUsuarios.existeUsuario(login);
    }

    public boolean existeDNI(String dni) {
        return daoUsuarios.existeDNI(dni);
    }

    public boolean existeNUSS(String nuss) {
        return daoUsuarios.existeNUSS(nuss);
    }

    public ContasPersoa contasPersoaFisica(String dni) {
        return daoUsuarios.contasPersoaFisica(dni);
    }

    public boolean validarUsuario(String login, String password) {
        return daoUsuarios.validarUsuario(login, password);
    }

    public void insertarUsuario(Usuario usuario) throws ExcepcionBD {
        daoUsuarios.insertarUsuario(usuario);
    }

    public void actualizarUsuario(Usuario usuario) throws ExcepcionBD {
        daoUsuarios.actualizarUsuario(usuario);
    }

    public void darBaixaUsuario(Usuario usuario) throws ExcepcionBD {
        daoUsuarios.darBaixaUsuario(usuario);
    }

    public TipoUsuario consultarTipo(String login) {
        return daoUsuarios.consultarTipo(login);
    }

    public Usuario consultarUsuario(String login) {
        return daoUsuarios.consultarUsuario(login);
    }

    public PersoaFisica consultarPersoaFisica(String DNI) {
        return daoUsuarios.consultarPersoaFisica(DNI);
    }

    public ArrayList<Usuario> buscarUsuarios(String login, String nome, TipoUsuario filtroTipo, boolean usuariosDeBaixa) {
        return daoUsuarios.buscarUsuarios(login, nome, filtroTipo, usuariosDeBaixa);
    }

    public Cuota consultarCuota(String login) {
        return daoUsuarios.consultarCuota(login);
    }

    public ArrayList<RexistroFisioloxico> listarRexistros(String login) {
        return daoUsuarios.listarRexistros(login);
    }

    public void insertarRexistro(RexistroFisioloxico rexistroFisioloxico) throws ExcepcionBD {
        daoUsuarios.insertarRexistro(rexistroFisioloxico);
    }

    public void eliminarRexistro(RexistroFisioloxico rexistroFisioloxico) throws ExcepcionBD {
        daoUsuarios.eliminarRexistro(rexistroFisioloxico);
    }

    public ArrayList<TipoActividade> listarCapacidades(String login) {
        return daoUsuarios.listarCapacidades(login);
    }

    public void engadirCapadidade(String login, TipoActividade tipoActividade) throws ExcepcionBD {
        daoUsuarios.engadirCapadidade(login, tipoActividade);
    }

    public void eliminarCapacidade(String login, TipoActividade tipoActividade) throws ExcepcionBD {
        daoUsuarios.eliminarCapacidade(login, tipoActividade);
    }

    public boolean tenClasesPendentes(Persoal persoal, TipoActividade tipoActividade) {
        return daoUsuarios.tenClasesPendentes(persoal, tipoActividade);
    }

    /*
        Funcions DAOTarifas
     */
    public void insertarTarifa(Tarifa t) throws ExcepcionBD {
        daoTarifas.insertarTarifa(t);
    }

    public void borrarTarifa(Integer codTarifa) throws ExcepcionBD {
        daoTarifas.borrarTarifa(codTarifa);
    }

    public void actualizarTarifa(Tarifa t) throws ExcepcionBD {
        daoTarifas.actualizarTarifa(t);
    }

    public boolean estaEnUsoTarifa(Integer codTarifa) {
        return daoTarifas.estaEnUsoTarifa(codTarifa);
    }

    public ArrayList<Tarifa> listarTarifas() {
        return daoTarifas.listarTarifas();
    }

    public Tarifa consultarTarifaSocio(String loginSocio) {
        return daoTarifas.consultarTarifaSocio(loginSocio);
    }

    public boolean existeTarifa(String nome) {
        return daoTarifas.existeTarifa(nome);
    }

    /*
        Funcions DAOMensaxes
     */
    public void enviarMensaxe(Mensaxe m) throws ExcepcionBD {
        daoMensaxes.enviarMensaxe(m);
    }

    public void enviarMensaxe(Usuario emisor, ArrayList<Usuario> receptores, String mensaxe) throws ExcepcionBD {
        daoMensaxes.enviarMensaxe(emisor, receptores, mensaxe);
    }

    public void marcarMensaxeComoLido(Mensaxe m) throws ExcepcionBD {
        daoMensaxes.marcarMensaxeComoLido(m);
    }

    public ArrayList<Mensaxe> listarMensaxesRecibidos(String loginReceptor) {
        return daoMensaxes.listarMensaxesRecibidos(loginReceptor);
    }

    /*
        Funcions DAOIncidencias
    */

    public void insertarIncidencia(Incidencia incidencia) throws ExcepcionBD {
        daoIncidencias.insertarIncidencia(incidencia);
    }

    public ArrayList<Incidencia> listarIncidencias(String textoBuscar, TipoIncidencia tipoIncidencia) {
        return daoIncidencias.listarIncidencias(textoBuscar, tipoIncidencia);
    }

    public void resolverIncidencia(Incidencia incidencia) throws ExcepcionBD {
        daoIncidencias.resolverIncidencia(incidencia);
    }

    public Incidencia consultarIncidencia(Incidencia incidencia) throws ExcepcionBD {
       return daoIncidencias.consultarIncidencia(incidencia);
    }

    /*
        Funcións DAOInstalacions
     */

    public ArrayList<Area> listarAreas() {
        return daoInstalacions.listarAreas();
    }

    public ArrayList<Material> listarMateriais() {
        return daoInstalacions.listarMateriais();
    }

    public ArrayList<TipoActividade> listarTipoActividades() {
        return daoActividades.listarTipoActividades();
    }



    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public DAOUsuarios getDaoUsuarios() {
        return daoUsuarios;
    }

    public void setDaoUsuarios(DAOUsuarios daoUsuarios) {
        this.daoUsuarios = daoUsuarios;
    }

    public DAOTarifas getDaoTarifas() {
        return daoTarifas;
    }

    public void setDaoTarifas(DAOTarifas daoTarifas) {
        this.daoTarifas = daoTarifas;
    }

    public DAOMensaxes getDaoMensaxes() {
        return daoMensaxes;
    }

    public void setDaoMensaxes(DAOMensaxes daoMensaxes) {
        this.daoMensaxes = daoMensaxes;
    }

    public DAOIncidencias getDaoIncidencias() {
        return daoIncidencias;
    }

    public void setDaoIncidencias(DAOIncidencias daoIncidencias) {
        this.daoIncidencias = daoIncidencias;
    }
}

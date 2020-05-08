package centrodeportivo.baseDatos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.Mensaxe;
import centrodeportivo.aplicacion.obxectos.actividades.Actividade;
import centrodeportivo.aplicacion.obxectos.actividades.Curso;
import centrodeportivo.aplicacion.obxectos.actividades.TipoActividade;
import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.aplicacion.obxectos.area.Material;
import centrodeportivo.aplicacion.obxectos.area.TipoMaterial;
import centrodeportivo.aplicacion.obxectos.usuarios.Persoal;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import javafx.beans.binding.ObjectExpression;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Víctor Barreiro
 * Esta clase é a fachada que ten a parte da base de datos do noso proxecto. Con ela, o resto de partes da aplicación
 * simplemente terán que ter coñecemento desta fachada, evitando coñecer todos os DAOs.
 */
public final class FachadaBD {
    /**
     * Atributos da clase
     */
    private FachadaAplicacion fachadaAplicacion; //Fachada de aplicación
    private Connection conexion; //Conexión coa base de datos.
    /**
     * Os diferentes DAO (Data Access Object) dende os que faremos os métodos que realmente acceden á base de datos.
     */
    private DAOUsuarios daoUsuarios;
    private DAOInstalacions daoInstalacions;
    private DAOTipoMaterial daoTipoMaterial;
    private DAOMaterial daoMaterial;
    private DAOTiposActividades daoActividades;
    private DAOTiposActividades daoTiposActividades;
    private DAOCursos daoCursos;
    private DAOAreas daoareas;
    private DAOActividade daoActividade;
    private DAOMensaxes daoMensaxes;

    /**
     * Constructor da fachada da base de datos:
     *
     * @param fachadaAplicacion A referencia á fachada da parte de aplicación.
     * @throws ExcepcionBD excepción asociada a problemas relativos á base de datos.
     */
    public FachadaBD(FachadaAplicacion fachadaAplicacion) throws ExcepcionBD {
        // Para empezar, asociaremos a fachada de aplicación ao atributo correspondente:
        this.fachadaAplicacion = fachadaAplicacion;
        // Creamos o atributo de tipo properties para gardar toda a información para a configuración.
        Properties configuracion = new Properties();
        FileInputStream prop;

        try {
            // Cargamos os datos incluidos no .properties.
            prop = new FileInputStream("baseDatos.properties");
            configuracion.load(prop);
            prop.close();
        } catch (Exception ex) {
            // En caso de problemas ao desencriptar o cifrado, avisamos e saímos:
            // O aviso farase tanto por ventá:
            fachadaAplicacion.mostrarErro("Carga do arquivo de configuración",
                    "Non se puido cargar o arquivo de configuración da base de datos");
            // Coma por texto:
            System.out.println("Non se puido cargar o arquivo properties");
            System.exit(1);
        }

        // A partir da información lida imos ir asociando as propiedades ao usuario para o acceso á BD:
        Properties usuario = new Properties();
        // Nome de usuario da base de datos:
        usuario.setProperty("user", configuracion.getProperty("usuario"));
        // Clave do usuario
        usuario.setProperty("password", configuracion.getProperty("clave"));
        // Creamos o string para poder solicitar a conexión coa base de datos:
        String con = String.format("jdbc:%s://%s:%s/%s", configuracion.getProperty("gestor"), configuracion.getProperty("servidor"), configuracion.getProperty("puerto"), configuracion.getProperty("baseDatos"));
        try {
            //Tentamos establecer a conexión:
            this.conexion = DriverManager.getConnection(con, usuario);
            //Por convenio global estableceremos o autoCommit a false. POLO TANTO, teremos que facer commit nos métodos
            //de DAO.
            this.conexion.setAutoCommit(false);
        } catch (SQLException e) {
            //Se non podemos, lanzaremos a nosa propia excepción asociada á base de datos:
            throw new ExcepcionBD(this.conexion, e);
        }

        //Creamos todos os DAOs:
        this.daoUsuarios = new DAOUsuarios(this.conexion, this.fachadaAplicacion);
        this.daoInstalacions = new DAOInstalacions(this.conexion, this.fachadaAplicacion);
        this.daoActividades = new DAOTiposActividades(this.conexion, this.fachadaAplicacion);
        this.daoCursos = new DAOCursos(this.conexion, this.fachadaAplicacion);
        this.daoUsuarios = new DAOUsuarios(this.conexion, this.fachadaAplicacion);
        this.daoInstalacions = new DAOInstalacions(this.conexion, this.fachadaAplicacion);
        this.daoTiposActividades = new DAOTiposActividades(this.conexion, this.fachadaAplicacion);
        this.daoTipoMaterial = new DAOTipoMaterial(this.conexion, this.fachadaAplicacion);
        this.daoMaterial = new DAOMaterial(this.conexion, this.fachadaAplicacion);
        this.daoActividade = new DAOActividade(this.conexion, this.fachadaAplicacion);
        this.daoMensaxes = new DAOMensaxes(this.conexion, this.fachadaAplicacion);
        this.daoareas = new DAOAreas(this.conexion, this.fachadaAplicacion);
    }

    /*
        Funcions DAOUsuarios
     */

    /**
     * Método que nos permitirá levar a cabo a validación dun usuario:
     *
     * @param login       O login introducido polo usuario.
     * @param contrasinal O contrasinal introducido polo usuario.
     * @return booleano que nos indica se a validación foi correcta ou non.
     */
    public boolean validarUsuario(String login, String contrasinal) {
        return daoUsuarios.validarUsuario(login, contrasinal);
    }

    /**
     * Método que nos permitirá consultar os datos esenciais dun usuario:
     *
     * @param login O login do usuario que se quere consultar.
     * @return Usuario con algúns dos datos asociados na base de datos ao login pasado como argumento.
     */
    public Usuario consultarUsuario(String login) {
        //Chamamos ao método do dao correspondente.
        return daoUsuarios.consultarUsuario(login);
    }

    /*
        Funcións DAOMensaxes:
     */

    /**
     * Método que nos permite enviar unha mensaxe de aviso a todos os socios.
     *
     * @param mensaxe A mensaxe a transmitir
     * @throws ExcepcionBD Excepción que se pode producir por problemas coa base de datos.
     */
    public void enviarAvisoSocios(Mensaxe mensaxe) throws ExcepcionBD {
        daoMensaxes.enviarAvisoSocios(mensaxe);
    }

    /**
     * Método que nos permite enviar un aviso aos socios dun curso determinado.
     *
     * @param mensaxe A mensaxe que se vai a enviar aos socios.
     * @param curso   O curso ao que pertencen os usuarios aos que se lle vai enviar a mensaxe.
     * @throws ExcepcionBD Excepción que se pode producir por problemas coa base de datos.
     */
    public void enviarAvisoSociosCurso(Mensaxe mensaxe, Curso curso) throws ExcepcionBD {
        daoMensaxes.enviarAvisoSociosCurso(mensaxe, curso);
    }

    /*
        Funcións DAOInstalacions
     */

    /**
     * Método que nos permitirá dar de alta unha nova instalación.
     *
     * @param instalacion A instalación a dar de alta.
     * @throws ExcepcionBD Excepción asociada a problemas ao tentar facer a actualización sobre a base de datos.
     */
    public void darAltaInstalacion(Instalacion instalacion) throws ExcepcionBD {
        //Chamamos ao método correspondente do dao de instalacións:
        daoInstalacions.darAltaInstalacion(instalacion);
    }

    /**
     * Método que tenta eliminar os datos da instalación pasada como argumento da base de datos.
     *
     * @param instalacion A instalación cuxos datos se queren eliminar.
     * @throws ExcepcionBD Excepción asociada a problemas que poden xurdir ao actualizar a base de datos.
     */
    public void borrarInstalacion(Instalacion instalacion) throws ExcepcionBD {
        daoInstalacions.borrarInstalacion(instalacion);
    }

    /**
     * Método que tenta modificar os datos da instalación pasada como argumento na base de datos.
     *
     * @param instalacion Os datos da instalación para ser modificados.
     * @throws ExcepcionBD Excepción asociada a posibles problemas dados ao actualizar a base de datos.
     */
    public void modificarInstalacion(Instalacion instalacion) throws ExcepcionBD {
        daoInstalacions.modificarInstalacion(instalacion);
    }

    /**
     * Método que nos permite buscar instalacións na base de datos, tanto con coma sen filtros.
     *
     * @param instalacion Se non é null, a consulta das instalacións realizarase en base aos campos desta instalación.
     * @return Se instalación non é null, devolveranse as instalacións que coincidan cos campos de consulta, en caso
     * contrario, devolverase un listado de todas as instalacións.
     */
    public ArrayList<Instalacion> buscarInstalacions(Instalacion instalacion) {
        return daoInstalacions.buscarInstalacions(instalacion);
    }

    /**
     * Método que nos permite consultar unha instalación concreta:
     *
     * @param instalacion A instalación de referencia para a que se consultará a información
     * @return A instalación con todos os datos, actualizada totalmente.
     */
    public Instalacion consultarInstalacion(Instalacion instalacion) {
        return daoInstalacions.consultarInstalacion(instalacion);
    }


    /**
     * Método que nos permite comprobar se a instalación pasada existe na base de datos, é dicir, se ten o mesmo
     * nome.
     *
     * @param instalacion A instalación cuxo nome queremos validar
     * @return True se hai unha instalación xa co mesmo nome, False en caso contrario.
     */
    public boolean comprobarExistencia(Instalacion instalacion) {
        return daoInstalacions.comprobarExistencia(instalacion);
    }

    /**
     * Método que nos permite comprobar se unha instalación ten asociada algunha área.
     *
     * @param instalacion A instalación para a cal queremos comprobar se ten áreas.
     * @return True se a instalación ten áreas, False en caso contrario.
     */
    public boolean tenAreas(Instalacion instalacion) {
        return daoInstalacions.tenAreas(instalacion);
    }


    /*
        Funcións DAOTiposActividades
     */

    /**
     * Método que nos permite introducir na base de datos a información dun novo tipo de actividade, cuxa información
     * se pasa como arugmento.
     *
     * @param tipoActividade Os datos do tipo de actividade a insertar.
     * @throws ExcepcionBD Excepción asociada a problemas que ocorran na actualización da base de datos.
     */
    public void crearTipoActividade(TipoActividade tipoActividade) throws ExcepcionBD {
        this.daoTiposActividades.crearTipoActividade(tipoActividade);
    }

    /**
     * Método que nos permite modificar os datos do tipo de actividade pasado como argumento. Suponse que ese tipo de
     * actividade xa está rexistrado e, polo tanto, ten un código asociado.
     *
     * @param tipoActividade O tipo de actividade cos datos a actualizar.
     * @throws ExcepcionBD Excepción asociada a problemas que poidan ocorrer durante a inserción na base de datos.
     */
    public void modificarTipoActividade(TipoActividade tipoActividade) throws ExcepcionBD {
        //Chamamos ao método do dao de tipos de actividades:
        this.daoTiposActividades.modificarTipoActividade(tipoActividade);
    }

    /**
     * Método que nos permite eliminar da base de datos o tipo de actividade pasado como argumento.
     *
     * @param tipoActividade O tipo de actividade que se quere eliminar.
     * @throws ExcepcionBD Excepción asociada a problemas que ocorran durante a actualización da base de datos.
     */
    public void eliminarTipoActividade(TipoActividade tipoActividade) throws ExcepcionBD {
        //Chamamos ao método do dao correspondente:
        this.daoTiposActividades.eliminarTipoActividade(tipoActividade);
    }

    /**
     * Método que ofrece un conxunto de tipos de actividade contidos na base de datos.
     *
     * @param tipoActividade O tipo de actividade modelo co que se vai a facer a búsqueda.
     * @return Se o tipo de actividade é null, devolveranse todos os tipos de actividades rexistrados, en caso contrario
     * todos os tipos de actividade que teñan coincidencia co nome que ten o tipo pasado como argumento.
     */
    public ArrayList<TipoActividade> buscarTiposActividades(TipoActividade tipoActividade) {
        return this.daoTiposActividades.buscarTiposActividades(tipoActividade);
    }

    /**
     * Método que nos permite consultar un tipo de actividade a partir do código do tipo pasado como argumento.
     *
     * @param tipoActividade O tipo de actividade do que se collerá o código para a consulta.
     * @return O tipo de actividade co código buscado (se todavía existe na base de datos).
     */
    public TipoActividade consultarTipoActividade(TipoActividade tipoActividade) {
        return this.daoTiposActividades.consultarTipoActividade(tipoActividade);
    }

    /**
     * Método que nos permite comprobar que non existe un tipo de actividade diferente co mesmo nome ca o tipo pasado
     * como argumento.
     *
     * @param tipoActividade O tipo para o que se quere validar a existencia.
     * @return True se existe un tipo de actividade diferente na base de datos que ten o mesmo nome, False en caso
     * contrario.
     */
    public boolean comprobarExistencia(TipoActividade tipoActividade) {
        //Chamamos ao dao de tipos de actividades:
        return this.daoTiposActividades.comprobarExistencia(tipoActividade);
    }

    /**
     * Método que nos permite comprobar se un tipo de actividade ten actividades asociadas.
     *
     * @param tipoActividade O tipo para o que se quere validar se ten actividades.
     * @return True se este tipo de actividade ten actividades asociadas, False noutro caso.
     */
    public boolean tenActividades(TipoActividade tipoActividade) {
        //Accedemos ao método correspondente do dao de tipos de actividades
        return this.daoTiposActividades.tenActividades(tipoActividade);
    }

    /*
        Funcións DAOCursos
     */

    /**
     * Método que nos permite introducir os datos dun novo curso na base de datos.
     *
     * @param curso O curso a insertar
     * @throws ExcepcionBD Excepción asociada a problemas que puideron ocorrer na base de datos.
     */
    public void rexistrarCurso(Curso curso) throws ExcepcionBD {
        daoCursos.rexistrarCurso(curso);
    }

    /**
     * Método que nos permite realizar modificacións na información xeral dun curso determinado.
     *
     * @param curso O curso do que se quere modificar a información, cos datos modificados.
     * @throws ExcepcionBD Excepción asociada a problemas producidos coa base de datos.
     */
    public void modificarCurso(Curso curso) throws ExcepcionBD {
        daoCursos.modificarCurso(curso);
    }

    public void engadirActividade(Curso curso, Actividade actividade) throws ExcepcionBD {
        daoCursos.engadirActividade(curso, actividade);
    }

    /**
     * Método que nos permite levar a cabo a activación dun curso:
     *
     * @param curso Os datos do curso que se quere activar.
     * @throws ExcepcionBD Excepción asociada a problemas producidos na base de datos.
     */
    public void abrirCurso(Curso curso) throws ExcepcionBD {
        daoCursos.abrirCurso(curso);
    }

    /**
     * Método que nos permite cancelar un curso, e polo tanto borrar a súa información da base de datos.
     *
     * @param curso   O curso que se quere borrar.
     * @param mensaxe A mensaxe que se envía aos participantes polo borrado.
     * @throws ExcepcionBD Excepción asociada a problemas que poden ocorrer durante o borrado.
     */
    public void cancelarCurso(Curso curso, Mensaxe mensaxe) throws ExcepcionBD {
        daoCursos.cancelarCurso(curso, mensaxe);
    }

    /**
     * Método que nos permite consultar os cursos que hai almacenados na base de datos.
     *
     * @param curso Curso polo que se realiza a busca.
     * @return Se curso vale null, devolveranse todos os cursos, noutro caso, filtraranse polo nome do curso pasado.
     */
    public ArrayList<Curso> consultarCursos(Curso curso) {
        return daoCursos.consultarCursos(curso);
    }

    /**
     * Método que nos permite consultar os cursos abertos que hai almacenados na base de datos.
     *
     * @param curso Curso polo que se realiza a busca.
     * @return Se curso vale null, devolveranse todos os cursos abertos, noutro caso, filtraranse polo nome do curso pasado.
     */
    public ArrayList<Curso> consultarCursosAbertos(Curso curso) {
        return daoCursos.consultarCursosAbertos(curso);
    }

    /**
     * Método que nos permite recuperar datos máis concretos dun curso. Non só datos contidos na táboa de cursos,
     * máis información todavía.
     *
     * @param curso Información do curso do que se queren recuperar os datos (o atributo importante é o código).
     * @return Datos completos do curso procurado.
     */
    public Curso recuperarDatosCurso(Curso curso) {
        return daoCursos.recuperarDatosCurso(curso);
    }


    /**
     * Método que nos permite recuperar información suficiente do curso como para elaborar o informe que ofrecer ao
     * usuario que o consulta.
     *
     * @param curso Información do curso do que se queren recuperar os datos para o informe.
     * @return Datos completos do curso, incluíndo información adicional necesaria para a elaboración do informe.
     */
    public Curso informeCurso(Curso curso) {
        return daoCursos.informeCurso(curso);
    }

    /**
     * Método que nos permite comprobar que non existe un curso rexistrado co mesmo nome
     *
     * @param curso O curso que se quere validar
     * @return True se non existe outro curso diferente que teña o mesmo nome ca este, False noutro caso.
     */
    public boolean comprobarExistencia(Curso curso) {
        return daoCursos.comprobarExistencia(curso);
    }

    /**
     * Método que permite comprobar se un curso ten participantes.
     *
     * @param curso O curso para o que se quere validar dita información.
     * @return True se o curso ten participantes, False se non os ten.
     */
    public boolean tenParticipantes(Curso curso) {
        return daoCursos.tenParticipantes(curso);
    }

    /**
     * Método que leva a cabo as comprobacións de se un curso está preparado para ser activado:
     *
     * @param curso O curso a activar.
     * @return True se o curso se pode activar, False en caso contrario.
     */
    public boolean listoParaActivar(Curso curso) {
        return daoCursos.listoParaActivar(curso);
    }

    /**
     * Metodo que permite que un usuario se anote nun curso
     *
     * @param curso   Curso o que se vai a apuntar o usuario
     * @param usuario Usuario que se vai a apuntar o curso
     * @throws ExcepcionBD Excepción asociada a problemas que poden ocorrer durante a consulta
     */
    public void apuntarseCurso(Curso curso, Usuario usuario) throws ExcepcionBD {
        daoCursos.apuntarseCurso(curso, usuario);
    }

    /**
     * Metodo que permite que un usuario se desapunte dun curso
     *
     * @param curso   Curso o que se vai a desapuntar o usuario
     * @param usuario Usuario que se vai a desapuntar o curso
     * @throws ExcepcionBD Excepción asociada a problemas que poden ocorrer durante a consulta
     */
    public void desapuntarseCurso(Curso curso, Usuario usuario) throws ExcepcionBD {
        daoCursos.desapuntarseCurso(curso, usuario);
    }

    /**
     * Metodo para comprobar que un usuario este apuntado nun curso
     *
     * @param curso   Curso no que se quer comprobar se esta apuntado
     * @param usuario Usuario que se quer comprobar se esta apuntado
     * @throws ExcepcionBD Excepción asociada a problemas que poden ocorrer durante a consulta
     */
    public boolean estarApuntado(Curso curso, Usuario usuario) {
        return daoCursos.estarApuntado(curso, usuario);
    }

    /**
     * Metodo que comproba que se o aforo do curso é o máximo
     *
     * @param curso Curso no que se quer comprobar se o aforo e máximo
     * @return Retorna true no caso de que o aforo non sexa maximo, e false en caso contrario
     */
    public boolean NonEMaximoAforo(Curso curso) {
        return daoCursos.NonEMaximoAforo(curso);
    }

    /**
     * Método que nos permite consultar os que esta apuntado un usuario
     *
     * @param usuario Usuario co que se realiza a busqueda
     * @return Devolverase un ArrayList con todos os cursos nos que esta apuntado o usuario
     */
    public ArrayList<Curso> consultarCursosUsuario(Curso curso, Usuario usuario) {
        return daoCursos.consultarCursosUsuario(curso, usuario);
    }


    /*
        Funcións DAOTipoMaterial
     */

    public void darAltaTipoMaterial(TipoMaterial tipoMaterial) throws ExcepcionBD {
        this.daoTipoMaterial.darAltaTipoMaterial(tipoMaterial);
    }

    public void borrarTipoMaterial(TipoMaterial tipoMaterial) throws ExcepcionBD {
        this.daoTipoMaterial.borrarTipoMaterial(tipoMaterial);
    }


    public boolean isTipoMaterial(TipoMaterial tipoMaterial) {
        return this.daoTipoMaterial.isTipoMaterial(tipoMaterial);
    }

    public ArrayList<TipoMaterial> buscarTipoMaterial(TipoMaterial tipoMaterial) {
        return this.daoTipoMaterial.buscarTipoMaterial(tipoMaterial);
    }

    public boolean tenMateriais(TipoMaterial tipoMaterial) {
        return this.daoTipoMaterial.tenMateriais(tipoMaterial);
    }

    /*
        Funcións DAOMaterial
     */

    public void darAltaMaterial(Material material) throws ExcepcionBD {
        this.daoMaterial.darAltaMaterial(material);
    }

    public void borrarMaterial(Material material) throws ExcepcionBD {
        this.daoMaterial.borrarMaterial(material);
    }

    public void modificarMaterial(Material material) throws ExcepcionBD {
        this.daoMaterial.modificarMaterial(material);
    }

    public boolean isMaterial(Material material) {
        return this.daoMaterial.isMaterial(material);
    }

    public ArrayList<Material> listarMateriais(Material material) {
        return this.daoMaterial.listarMateriais(material);
    }

    public Material consultarMaterial(Material material) {
        return daoMaterial.consultarMaterial(material);
    }


    //Funcións propias:

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

    public DAOAreas getDaoareas() {
        return daoareas;
    }

    public void setDaoareas(DAOAreas daoareas) {
        this.daoareas = daoareas;
    }

    //Areas
    public boolean ExisteArea(Area area) {
        return daoareas.ExisteArea(area);
    }

    public int EngadirArea(Area area) throws ExcepcionBD {
        return daoareas.EngadirArea(area);
    }

    public void borrarArea(Area area) throws ExcepcionBD {
        daoareas.borrarArea(area);
    }

    public boolean tenActividadesArea(Area area, boolean senComezar) {
        return daoareas.tenActividadeArea(area, senComezar);
    }

    public boolean tenMateriaisArea(Area area) {
        return daoareas.tenMateriaisArea(area);
    }

    public void modificarArea(Area area) throws ExcepcionBD {
        daoareas.modificarArea(area);
    }

    public void darDeBaixaArea(Area area) throws ExcepcionBD {
        daoareas.darDeBaixaArea(area);
    }

    public boolean EBaixaArea(Area area) {
        return daoareas.EBaixaArea(area);
    }

    public void darDeAltaArea(Area area) throws ExcepcionBD {
        daoareas.darDeAltaArea(area);
    }

    public ArrayList<Area> buscarArea(Instalacion instalacion, Area area) {
        return daoareas.buscarArea(instalacion, area);
    }

    public ArrayList<Area> listarAreas() {
        return daoareas.listarAreas();
    }

    /**
     * Método que nos permite buscar areas na base de datos en función dunha instalación.
     *
     * @param instalacion Se non é null, a consulta realizase en base o codigo da area.
     * @return Se o parametro non é null, será devolto unha ObservableList con todas as areas que coincidan,
     * noutro caso, listaranse todas as areas.
     */
    public ArrayList<Area> listarAreasActivas(Instalacion instalacion) {
        return daoareas.listarAreasActivas(instalacion);
    }

    // Actividades
    public void EngadirActividade(Actividade actividade) throws ExcepcionBD {
        daoActividade.EngadirActividade(actividade);
    }

    public boolean horarioOcupadoActividade(Actividade actVella, Actividade actNova) {
        return daoActividade.horarioOcupadoActividade(actVella, actNova);
    }

    public void modificarActividade(Actividade actVella, Actividade actNova) throws ExcepcionBD {
        daoActividade.modificarActividade(actVella, actNova);
    }

    public void borrarActividade(Actividade actividade) throws ExcepcionBD {
        daoActividade.borrarActividade(actividade);
    }

    public void apuntarseActividade(Actividade actividade, Usuario usuario) throws ExcepcionBD {
        daoActividade.apuntarseActividade(actividade, usuario);
    }

    public ArrayList<Actividade> buscarActividade(Actividade actividade) {
        return daoActividade.buscarActividade(actividade);
    }

    // Alguen apuntado

    public void borrarseDeActividade(Actividade actividade, Usuario usuario) throws ExcepcionBD {
        daoActividade.borrarseDeActividade(actividade, usuario);
    }

    public boolean estarApuntado(Actividade actividade, Usuario usuario) {
        return daoActividade.estarApuntado(actividade, usuario);
    }

    public boolean NonEMaximoAforoActividade(Actividade actividade) {
        return daoActividade.NonEMaximoAforoActividade(actividade);
    }

    public ArrayList<Persoal> buscarProfesores(TipoActividade tipoactividade) {
        return daoActividade.buscarProfesores(tipoactividade);
    }

    public ArrayList<Actividade> buscarActividadeparticipa(Actividade actividade, Usuario usuario)
    {
        return daoActividade.buscarActividadeParticipa(actividade,usuario);
    }

}

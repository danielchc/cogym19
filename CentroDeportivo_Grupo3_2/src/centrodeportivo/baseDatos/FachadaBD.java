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
import centrodeportivo.aplicacion.obxectos.usuarios.Socio;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;

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
    private FachadaAplicacion fachadaAplicacion;  // Fachada de aplicación
    private Connection conexion;  // Conexión coa base de datos.
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
            // Tentamos establecer a conexión:
            this.conexion = DriverManager.getConnection(con, usuario);
            // Por convenio global estableceremos o autoCommit a false. POLO TANTO, teremos que facer commit nos métodos
            // de DAO.
            this.conexion.setAutoCommit(false);
        } catch (SQLException e) {
            // Se non podemos, lanzaremos a nosa propia excepción asociada á base de datos:
            throw new ExcepcionBD(this.conexion, e);
        }

        // Creamos todos os DAOs:
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

    /**
     * Funcións propias
     */

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
        // Chamamos ao método do dao correspondente.
        return daoUsuarios.validarUsuario(login, contrasinal);
    }

    /**
     * Método que nos permitirá consultar os datos esenciais dun usuario:
     *
     * @param login O login do usuario que se quere consultar e co que conseguiremos todos os demais datos.
     * @return Usuario con algúns dos datos asociados na base de datos ao login pasado como argumento.
     */
    public Usuario consultarUsuario(String login) {
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
        // Chamamos ó método correspondente do dao mensaxes:
        daoMensaxes.enviarAvisoSocios(mensaxe);
    }

    /**
     * Método que nos permite enviar un aviso aos socios dun curso determinado.
     *
     * @param mensaxe A mensaxe que se vai a enviar aos socios.
     * @param curso   O curso ao que pertencen os socios aos que se lle vai enviar a mensaxe.
     * @throws ExcepcionBD Excepción que se pode producir por problemas coa base de datos.
     */
    public void enviarAvisoSociosCurso(Mensaxe mensaxe, Curso curso) throws ExcepcionBD {
        daoMensaxes.enviarAvisoSociosCurso(mensaxe, curso);
    }

    /**
     * Método que nos permite realizar o envío dun aviso aos socios dunha actividade.
     *
     * @param mensaxe    A mensaxe a enviar a eses socios.
     * @param actividade A actividade de referencia da que se collerán os participantes aos que enviar as mensaxes.
     * @throws ExcepcionBD Excepción que se pode producir por problemas coa base de datos.
     */
    public void enviarAvisoSociosAct(Mensaxe mensaxe, Actividade actividade) throws ExcepcionBD {
        daoMensaxes.enviarAvisoSociosAct(mensaxe, actividade);
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
        // Chamamos ao método correspondente do dao de instalacións:
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
        // Chamamos ao método do dao de tipos de actividades:
        this.daoTiposActividades.crearTipoActividade(tipoActividade);
    }

    /**
     * Método que nos permite modificar os datos do tipo de actividade pasado como argumento. Suponse que ese tipo de
     * actividade xa está rexistrado e, polo tanto, ten un código asociado.
     *
     * @param tipoActividade O tipo de actividade cos datos a actualizar.
     * @throws ExcepcionBD Excepción asociada a problemas que poidan ocorrer durante a modificación na base de datos.
     */
    public void modificarTipoActividade(TipoActividade tipoActividade) throws ExcepcionBD {
        this.daoTiposActividades.modificarTipoActividade(tipoActividade);
    }

    /**
     * Método que nos permite eliminar da base de datos o tipo de actividade pasado como argumento.
     *
     * @param tipoActividade O tipo de actividade que se quere eliminar.
     * @throws ExcepcionBD Excepción asociada a problemas que ocorran durante a actualización da base de datos.
     */
    public void eliminarTipoActividade(TipoActividade tipoActividade) throws ExcepcionBD {
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
     * @return O tipo de actividade co código buscado (se aínda existe na base de datos).
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
        return this.daoTiposActividades.comprobarExistencia(tipoActividade);
    }

    /**
     * Método que nos permite comprobar se un tipo de actividade ten actividades asociadas.
     *
     * @param tipoActividade O tipo para o que se quere validar se ten actividades.
     * @return True se este tipo de actividade ten actividades asociadas, False noutro caso.
     */
    public boolean tenActividades(TipoActividade tipoActividade) {
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
        // Chamamos ao método do dao de cursos:
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

    /**
     * Método que nos permite levar a cabo a activación dun curso.
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
     * @throws ExcepcionBD Excepción asociada a problemas que poden ocorrer durante o borrado na base de datos.
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
     * Método que nos permite recuperar datos máis concretos dun curso. Non só datos contidos na táboa de cursos,
     * máis información aínda.
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
     * Método que nos permite comprobar que non existe un curso rexistrado co mesmo nome.
     *
     * @param curso O curso que se quere validar.
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
     * Método que leva a cabo as comprobacións de se un curso está preparado para ser activado.
     *
     * @param curso O curso a activar.
     * @return True se o curso se pode activar, False en caso contrario.
     */
    public boolean listoParaActivar(Curso curso) {
        return daoCursos.listoParaActivar(curso);
    }

    /**
     * Método que nos permite consultar os cursos ós que un usuario se pode apuntar (contemplaranse todos aqueles cursos
     * que esten abertos, non esté apuntado e a maoires, que ainda non derán comezo). Contémplase a posibilidade de filtrar
     * os cursos polo nome dos mesmos:
     *
     * @param curso   Cursos que se empregará para o filtrado.
     * @param usuario Usuario polo que se realiza a busca.
     * @return Devolverase un ArrayList con todos os cursos nos que esta apuntado o usuario
     */
    public ArrayList<Curso> consultarCursosDisponhibles(Curso curso, Usuario usuario) {
        return daoCursos.consultarCursosDisponhibles(curso, usuario);
    }

    /**
     * Método que nos permite consultar os cursos ós que está apuntado un usuario. Permitese unha busca filtrando polo
     * nome do curso.
     *
     * @param curso   Curso co que se filtra, no caso de que non sexa null, mediante o nome.
     * @param usuario Usuario co que se realiza a busca.
     * @return Devolverase un ArrayList con todos os cursos nos que esta apuntado o usuario e, se non é null,
     * coincidan co nome do curso pasado.
     */
    public ArrayList<Curso> consultarCursosUsuario(Curso curso, Usuario usuario) {
        return daoCursos.consultarCursosUsuario(curso, usuario);
    }

    /**
     * Método que permite que un usuario se anote nun curso.
     *
     * @param curso   Curso ó que se vai a apuntar o usuario (o importante é o código).
     * @param usuario Usuario que se vai a apuntar ó curso (o importante é o login).
     * @throws ExcepcionBD Excepción asociada a problemas que poden ocorrer durante a consulta.
     */
    public void apuntarseCurso(Curso curso, Usuario usuario) throws ExcepcionBD {
        daoCursos.apuntarseCurso(curso, usuario);
    }

    /**
     * Método que permite que un usuario se desapunte dun curso.
     *
     * @param curso   Curso ó que se vai a desapuntar o usuario (o importante é o código).
     * @param usuario Usuario que se vai a desapuntar ó curso (o importante é o login).
     * @throws ExcepcionBD Excepción asociada a problemas que poden ocorrer durante a consulta.
     */
    public void desapuntarseCurso(Curso curso, Usuario usuario) throws ExcepcionBD {
        daoCursos.desapuntarseCurso(curso, usuario);
    }

    /**
     * Método para comprobar que se un usuario esta apuntado nun curso.
     *
     * @param curso   Curso no que se quer comprobar se esta apuntado (o importante é o código).
     * @param usuario Usuario que se quer comprobar se esta apuntado (o importante é o login).
     */
    public boolean estarApuntado(Curso curso, Usuario usuario) {
        return daoCursos.estarApuntado(curso, usuario);
    }

    /**
     * Método que comproba que se o aforo do curso é o máximo.
     *
     * @param curso Curso no que se quer comprobar se o aforo e máximo.
     * @return Retorna true no caso de que o aforo non sexa o máximo, e false en caso contrario.
     */
    public boolean NonEMaximoAforo(Curso curso) {
        return daoCursos.NonEMaximoAforo(curso);
    }

    /**
     * Método para comprobar se un curso esta almaceado na base de datos.
     *
     * @param curso Curso que do que se quere comprobar a existencia.
     * @return Retorna true se o curso se atopa almaceado na base de datos e false en caso contrario.
     */
    public boolean isCurso(Curso curso) {
        return daoCursos.isCurso(curso);
    }


    /*
        Funcións DAOTipoMaterial
     */

    /**
     * Método que permite engadir unh nova tupla na base de datos cun novo tipo de material.
     *
     * @param tipoMaterial Datos do tipo de material que se creará (en concreto, o nome).
     * @throws ExcepcionBD Excepción procedente do método dao para indicar problemas na inserción.
     */
    public void darAltaTipoMaterial(TipoMaterial tipoMaterial) throws ExcepcionBD {
        this.daoTipoMaterial.darAltaTipoMaterial(tipoMaterial);
    }

    /**
     * Método que permite eliminar un tipo de material da base de datos.
     *
     * @param tipoMaterial Datos do tipo de material que se eliminará.
     * @throws ExcepcionBD Excepción procedente do método dao para indicar problemas no borrado.
     */
    public void borrarTipoMaterial(TipoMaterial tipoMaterial) throws ExcepcionBD {
        this.daoTipoMaterial.borrarTipoMaterial(tipoMaterial);
    }

    // Non se contempla a modificación do tipo de material por motivos de deseño

    /**
     * Método que comproba se certo tipo de material existe na base de datos.
     *
     * @param tipoMaterial Datos do tipo de material que se quer validar.
     * @return Devolve true se o tipo de material se encontra na base de datos.
     */
    public boolean isTipoMaterial(TipoMaterial tipoMaterial) {
        return this.daoTipoMaterial.isTipoMaterial(tipoMaterial);
    }

    /**
     * Método que permite buscar tipos de materiais na base de datos con campos de busqueda, ou sen eles.
     *
     * @param tipoMaterial Se non é null, a consulta realizase en base o nome do tipo de material.
     * @return Se o parametro non é null, será devolto un array con todos os tipos de materiais que coincidan,
     * noutro caso, listanse todos os tipos de materiais.
     */
    public ArrayList<TipoMaterial> buscarTipoMaterial(TipoMaterial tipoMaterial) {
        return this.daoTipoMaterial.buscarTipoMaterial(tipoMaterial);
    }

    /**
     * Método que permite comprobar existen materiais vínculados o tipo.
     *
     * @param tipoMaterial O tipo de material do cal queremos comprobar se existen materiais vinculados
     * @return Retorna true se o tipo ten materiais vinculados, False en caso contrario.
     */
    public boolean tenMateriais(TipoMaterial tipoMaterial) {
        return this.daoTipoMaterial.tenMateriais(tipoMaterial);
    }


    /*
        Funcións DAOMaterial
     */

    /**
     * Método que crea unha nova tupla insertando un material na base de datos.
     *
     * @param material Datos do material que se engadirá a base de datos.
     * @throws ExcepcionBD Excepción procedente do método dao para indicar problemas na inserción.
     */
    public void darAltaMaterial(Material material) throws ExcepcionBD {
        this.daoMaterial.darAltaMaterial(material);
    }

    /**
     * Método que elimina a tupla dun material na base de datos.
     *
     * @param material Datos do material que se eliminará.
     * @throws ExcepcionBD Excepción procedente do método dao para indicar problemas no borrado.
     */
    public void borrarMaterial(Material material) throws ExcepcionBD {
        this.daoMaterial.borrarMaterial(material);
    }

    /**
     * Método que modifica os datos un material na base de datos
     *
     * @param material Datos do material que se modificará.
     * @throws ExcepcionBD Excepción procedente do método dao para indicar problemas na modificación.
     */
    public void modificarMaterial(Material material) throws ExcepcionBD {
        this.daoMaterial.modificarMaterial(material);
    }

    /**
     * Método que comproba se certo material existe na base de datos.
     *
     * @param material Datos do material que se quer validar.
     * @return Devolve true se o material se encontra na base de datos e false en caso contrario.
     */
    public boolean isMaterial(Material material) {
        return this.daoMaterial.isMaterial(material);
    }

    /**
     * Método que comproba se certo material existe na base de datos e devolve os datos actualizados.
     *
     * @param material Datos do material que se quere consultar.
     * @return Devolve os datos do material actualizado.
     */
    public Material consultarMaterial(Material material) {
        return daoMaterial.consultarMaterial(material);
    }

    /**
     * Método que obten todos os materiais almacenados na base de datos e permite filtrar en función
     * da área e instalación na que se atope así como, polo tipo de material.
     *
     * @param material Datos do material co que poderemos filtrar en función da instalación e área na que
     *                 se atope así como, polo seu tipo.
     * @return Devolve un ArrayList cos materiais da base de datos que cumpran ditas condicións.
     */
    public ArrayList<Material> listarMateriais(Material material) {
        return this.daoMaterial.listarMateriais(material);
    }


    /*
        Funcións DAOArea
     */

    /**
     * Método que comproba se existe algunha área co mesmo nome nunha instalación.
     *
     * @param area Área que se busca comprobar se é única ou non.
     * @return Retorna true se existe outra area na mesma instalación con ese nome e false en caso contrario.
     */
    public boolean ExisteArea(Area area) {
        return daoareas.ExisteArea(area);
    }

    /**
     * Método que permite engadir unha nova área na base de datos.
     *
     * @param area Información da área a engadir na base de datos
     * @return Retorna un enteiro, neste caso, o código da área.
     * @throws ExcepcionBD Excepción procedente da base de datos para indicar problemas na inserción.
     */
    public int EngadirArea(Area area) throws ExcepcionBD {
        return daoareas.EngadirArea(area);
    }

    /**
     * Método que permite consultar se a área ten actividades ou non. A maiores, posibilitamos realizar
     * a consulta sobre actividades, ou actividades sen comezar.
     *
     * @param area       Datos da área onde se desexa facer a busca.
     * @param senComezar Booleano empregado para filtrar a consulta.
     * @return Retorna true se a area ten actividades e false en calquer outro caso.
     */
    public boolean tenActividadesArea(Area area, boolean senComezar) {
        return daoareas.tenActividadeArea(area, senComezar);
    }

    /**
     * Método que permite comprobar se a área ten materiais asociados.
     *
     * @param area Información da área sobre a que se quere realizar a busca.
     * @return Devolve true se a área ten materiais asociados e false en calquer outro caso.
     */
    public boolean tenMateriaisArea(Area area) {
        return daoareas.tenMateriaisArea(area);
    }

    /**
     * Método que permite borrar unha área da base de datos.
     *
     * @param area Área que se desexa eliminar da base de datos.
     * @throws ExcepcionBD Excepción procedente da base de datos para indicar problemas no borrado.
     */
    public void borrarArea(Area area) throws ExcepcionBD {
        daoareas.borrarArea(area);
    }

    /**
     * Método que permite modificar os datos dunha área na base de datos.
     *
     * @param area Área cos datos modificados para realizar ditos cambios na base de datos.
     * @throws ExcepcionBD Excepción procedente da base de datos para indicar problemas na modificación.
     */
    public void modificarArea(Area area) throws ExcepcionBD {
        daoareas.modificarArea(area);
    }

    /**
     * Método que permite dar de baixa un área, é dicir, non se elimina da base de datos pero,
     * deixará de estar dispoñible para realizar actividades.
     *
     * @param area Área que se quere dar de baixa.
     * @throws ExcepcionBD Excepción procedente da base de datos para indicar problemas na actualización.
     */
    public void darDeBaixaArea(Area area) throws ExcepcionBD {
        daoareas.darDeBaixaArea(area);
    }

    /**
     * Método que permite dar de alta unha área que xa exisita na base de datos pero, agora estará
     * dispoñible para a realización de actividades.
     *
     * @param area Área que se procura dar de alta.
     * @throws ExcepcionBD Excepción procedente da base de datos para indicar problemas na actualización.
     */
    public void darDeAltaArea(Area area) throws ExcepcionBD {
        daoareas.darDeAltaArea(area);
    }

    /**
     * Método que permite consultar se un área esta dada de baixa ou non.
     *
     * @param area Área que se quere comprobar se esta dada de baixa ou non.
     * @return Retorna true se a área esta dada de baixa e false en caso contrario.
     */
    public boolean EBaixaArea(Area area) {
        return daoareas.EBaixaArea(area);
    }

    /**
     * Método que permite realizar a busca de áreas en función dos datos dunha instalación e seguindo un modelo de área.
     *
     * @param instalacion Instalación que empregaremos para filtrar.
     * @param area        Área modelo que empregaremos para filtrar.
     * @return Retorna un ArrayList de áreas que cumpran ditas condicións de filtrado.
     */
    public ArrayList<Area> buscarArea(Instalacion instalacion, Area area) {
        return daoareas.buscarArea(instalacion, area);
    }

    /**
     * Método que permite listar todas as áreas activas dunha instalación.
     *
     * @param instalacion Datos da instalación na que se realiza a procura das áreas activas.
     * @return Retorna un ArrayList cas áreas activas de dita instalación.
     */
    public ArrayList<Area> listarAreasActivas(Instalacion instalacion) {
        return daoareas.listarAreasActivas(instalacion);
    }


    /*
        Funcións DAOActividade
     */

    /**
     * Método que nos permite insertar unha actividade na base de datos
     *
     * @param actividade A actividade que se quer insertar na base de datos
     * @throws ExcepcionBD Excepción asociada a posibles problemas dados ao actualizar a base de datos.
     */
    public void EngadirActividade(Actividade actividade) throws ExcepcionBD {
        daoActividade.EngadirActividade(actividade);
    }

    public boolean horarioOcupadoActividade(Actividade actVella, Actividade actNova) {
        return daoActividade.horarioOcupadoActividade(actVella, actNova);
    }

    public void modificarActividade(Actividade actVella, Actividade actNova) throws ExcepcionBD {
        daoActividade.modificarActividade(actVella, actNova);
    }

    public void borrarActividade(Actividade actividade, Mensaxe mensaxe) throws ExcepcionBD {
        daoActividade.borrarActividade(actividade, mensaxe);
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

    public ArrayList<Actividade> buscarActividadeparticipa(Actividade actividade, Usuario usuario) {
        return daoActividade.buscarActividadeParticipa(actividade, usuario);
    }

    public ArrayList<Actividade> buscarActividadeNONParticipa(Actividade actividade, Usuario usuario) {
        return daoActividade.buscarActividadeNONParticipa(actividade, usuario);
    }

    public void valorarActividade(Integer valoracion, Actividade actividade, Usuario usuario) throws ExcepcionBD {
        daoActividade.valorarActividade(valoracion, actividade, usuario);
    }

    public boolean EProfesorActivo(Persoal profesor) {
        return daoActividade.EProfesorActivo(profesor);
    }

    public boolean isValorada(Actividade actividade, Usuario usuario) {
        return daoActividade.isValorada(actividade, usuario);
    }

    public ArrayList<Socio> listarParticipantes(Actividade actividade) {
        return daoActividade.listarParticipantes(actividade);
    }

    public Actividade recuperarActividade(Actividade actividade) {
        return daoActividade.recuperarActividade(actividade);
    }
}

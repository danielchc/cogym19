package centrodeportivo.aplicacion;

import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.Mensaxe;
import centrodeportivo.aplicacion.obxectos.actividades.Actividade;
import centrodeportivo.aplicacion.obxectos.actividades.Curso;
import centrodeportivo.aplicacion.obxectos.actividades.TipoActividade;
import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.aplicacion.obxectos.area.Material;
import centrodeportivo.aplicacion.obxectos.area.TipoMaterial;
import centrodeportivo.aplicacion.obxectos.tipos.TipoResultados;
import centrodeportivo.aplicacion.obxectos.usuarios.Persoal;
import centrodeportivo.aplicacion.obxectos.usuarios.Socio;
import centrodeportivo.aplicacion.xestion.*;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.baseDatos.FachadaBD;
import centrodeportivo.gui.FachadaGUI;
import javafx.application.Application;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Victor Barreiro
 * Esta clase serve de fachada para a parte de aplicación da nosa implementación. Nela, teremos ademais o método main dende
 * onde arrancará a execución.
 */
public class FachadaAplicacion extends Application {

    /**
     * Atributos: comezamos polo que son as referencias ás outras fachadas.
     */
    private FachadaGUI fachadaGUI;
    private FachadaBD fachadaBD;
    /**
     * O resto de atributos fan referencia ás clases de xestión que forman parte tamén da aplicación.
     */
    private XestionUsuarios xestionUsuarios;
    private XestionInstalacions xestionInstalacions;
    private XestionTiposActividades xestionActividades;
    private XestionCursos xestionCursos;
    private XestionTipoMaterial xestionTipoMaterial;
    private XestionMaterial xestionMaterial;
    private XestionArea xestionArea;
    private XestionMensaxes xestionMensaxes;
    private XestionActividade xestionActividade;


    /**
     * Constructor da clase, onde se inicializan todos os atributos comentados antes (fachadas e ventás ded xestión).
     *
     * @throws IOException  Excepción de entrada/saída.
     * @throws SQLException Excepción de SQL ao abrir a fachada da base de datos.
     */
    public FachadaAplicacion() throws IOException, SQLException {
        // Primeiro, creamos as fachadas:
        this.fachadaGUI = new FachadaGUI(this);
        try {
            this.fachadaBD = new FachadaBD(this);
        } catch (ExcepcionBD excepcionBD) {
            //Amosaremos a mensaxe de erro proporcionada pola excepción:
            this.mostrarErro("Conexión coa base de datos", excepcionBD.getMessage());
            System.exit(1);
        }
        // Tendo as fachadas creadas, imos creando as clases de xestión, ás que lle pasaremos ditas fachadas.
        this.xestionUsuarios = new XestionUsuarios(fachadaGUI, fachadaBD);
        this.xestionInstalacions = new XestionInstalacions(fachadaGUI, fachadaBD);
        this.xestionActividades = new XestionTiposActividades(fachadaGUI, fachadaBD);
        this.xestionCursos = new XestionCursos(fachadaGUI, fachadaBD);
        this.xestionTipoMaterial = new XestionTipoMaterial(fachadaGUI, fachadaBD);
        this.xestionMaterial = new XestionMaterial(fachadaGUI, fachadaBD);
        this.xestionMensaxes = new XestionMensaxes(fachadaGUI, fachadaBD);
        this.xestionArea = new XestionArea(fachadaGUI, fachadaBD);
        this.xestionActividade = new XestionActividade(fachadaGUI, fachadaBD);
    }


    /**
     * Con este outro método sobreescrito, start, iniciarase o que é a ventá de login, a partir do que o usuario
     * poderá comezar a meter os datos.
     *
     * @param primaryStage O escenario principal.
     * @throws Exception As excepcións que se poden lanzar por culpa da inicialización.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Chamamos ao método da GUI co que se abre devandita ventá.
        fachadaGUI.mostrarVentaLogin(primaryStage);
    }

    /**
     * Este é o main, onde comeza a executarse a aplicación.
     *
     * @param args Argumentos pasados por liña de comandos (non os empregaremos)
     */
    public static void main(String[] args) {
        launch(args);
    }

    //A partir de aquí, comezan os métodos que irán a distintas partes da aplicación, pero que todos eles serán chamados
    //dende outras partes:

    /*
        Chamadas a ventás:
     */

    /**
     * Método que nos permite amosar a ventá da aplicación orientada aos socios.
     *
     * @param loggedUser O usuario que iniciou sesión na aplicación, e que desencadeou a apertura desta ventá.
     * @throws IOException Excepción asociada á entrada/saída.
     */
    public void mostrarVentaSocios(Usuario loggedUser) throws IOException {
        //Imos ao método correspondente da GUI:
        fachadaGUI.mostrarVentaSocios(loggedUser);
    }

    /**
     * Método que nos permite amosar a ventá da aplicación asociada ao persoal do centro.
     *
     * @param loggedUser O usuario que iniciou sesión na aplicación, e que desencadeou a apertura desta ventá.
     * @throws IOException Excepción asociada á entrada/saída.
     */
    public void mostrarVentaPersoal(Usuario loggedUser) throws IOException {
        //Accedemos ao método correspondente da GUI:
        fachadaGUI.mostrarVentaPersoal(loggedUser);
    }

    /**
     * Método que nos permite amosar advertencias por pantalla.
     *
     * @param titulo O título que se lle quere dar á alerta onde apareza a advertencia.
     * @param texto  A mensaxe que se lle quere transmitir ao usuario.
     */
    public void mostrarAdvertencia(String titulo, String texto) {
        //Accedemos ao método correspondente da GUI:
        fachadaGUI.mostrarAdvertencia(titulo, texto);
    }

    /**
     * Método que nos permite amosar por pantalla unha mensaxe de erro.
     *
     * @param titulo O título que se lle quere dar á ventá onde se presente o erro.
     * @param texto  A mensaxe que se lle quere transmitir ao usuario.
     */
    public void mostrarErro(String titulo, String texto) {
        //Accedemos ao método correspondente da GUI, pasando os mesmos argumentos:
        fachadaGUI.mostrarErro(titulo, texto);
    }

    /**
     * Método que nos peremite amosar información por pantalla (sen ser erro nin advertencia)
     *
     * @param titulo O título que se lle quere dar á ventá onde se presente dita información.
     * @param texto  A mensaxe que se lle quere transmitir ao usuario.
     */
    public void mostrarInformacion(String titulo, String texto) {
        //Accederemos ao método correspondente da GUI, pasando os mesmos argumentos.
        fachadaGUI.mostrarInformacion(titulo, texto);
    }

    /**
     * Método co que se lle amosa unha mensaxe ao usuario por pantalla, esperando unha confirmación por parte do mesmo.
     *
     * @param titulo O título que se lle quere dar á ventá na que se amose a información.
     * @param texto  A mensaxe que se quere transmitir.
     * @return Un dato tipo ButtonType, co que se poderá saber se o usuario deu a súa autorización (confirmou) ou non a
     * raíz da mensaxe introducida.
     */
    public ButtonType mostrarConfirmacion(String titulo, String texto) {
        return fachadaGUI.mostrarConfirmacion(titulo, texto);
    }


    /*
        Métodos de usuarios.
     */

    /**
     * Método que nos permitirá levar a cabo a validación dun usuario:
     *
     * @param login       O login introducido polo usuario.
     * @param contrasinal O contrasinal introducido polo usuario.
     * @return booleano que nos indica se a validación foi correcta ou non.
     */
    public boolean validarUsuario(String login, String contrasinal) {
        //Imos á xestión de usuarios, pasando os argumentos.
        return xestionUsuarios.validarUsuario(login, contrasinal);
    }

    /**
     * Método que nos permitirá consultar os datos esenciais dun usuario:
     *
     * @param login O login do usuario que se quere consultar.
     * @return Usuario con algúns dos datos asociados na base de datos ao login pasado como argumento.
     */
    public Usuario consultarUsuario(String login) {
        //Imos á xestión de usuarios
        return xestionUsuarios.consultarUsuario(login);
    }


    /*
        Xestión mensaxes
     */

    /**
     * Método que nos permite enviar unha mensaxe de aviso a todos os socios.
     *
     * @param mensaxe A mensaxe a transmitir
     * @throws ExcepcionBD Excepción que se pode producir por problemas coa base de datos.
     */
    public void enviarAvisoSocios(Mensaxe mensaxe) throws ExcepcionBD {
        xestionMensaxes.enviarAvisoSocios(mensaxe);
    }

    /**
     * Método que nos permite enviar un aviso aos socios dun curso determinado.
     *
     * @param mensaxe A mensaxe que se vai a enviar aos socios.
     * @param curso   O curso ao que pertencen os usuarios aos que se lle vai enviar a mensaxe.
     * @throws ExcepcionBD Excepción que se pode producir por problemas coa base de datos.
     */
    public void enviarAvisoSociosCurso(Mensaxe mensaxe, Curso curso) throws ExcepcionBD {
        xestionMensaxes.enviarAvisoSociosCurso(mensaxe, curso);
    }

    /**
     * Método que nos permite realizar o envío dun aviso aos socios dunha actividade.
     *
     * @param mensaxe    A mensaxe a enviar a eses socios.
     * @param actividade A actividade de referencia da que se collerán os participantes aos que enviar as mensaxes.
     * @throws ExcepcionBD Excepción que se pode producir por problemas coa base de datos.
     */
    public void enviarAvisoSociosAct(Mensaxe mensaxe, Actividade actividade) throws ExcepcionBD {
        xestionMensaxes.enviarAvisoSociosAct(mensaxe, actividade);
    }


    /*
        Xestion instalacións
     */

    /**
     * Método que nos permitirá dar de alta unha nova instalación.
     *
     * @param instalacion A instalación a dar de alta.
     * @return O resultado da operación levada a cabo.
     * @throws ExcepcionBD Excepción asociada a problemas ao tentar facer a actualización sobre a base de datos.
     */
    public TipoResultados darAltaInstalacion(Instalacion instalacion) throws ExcepcionBD {
        //Chamamos ao método correspondente da clase de xestión de instalacións:
        return xestionInstalacions.darAltaInstalacion(instalacion);
    }

    /**
     * Método que tenta eliminar os datos da instalación pasada como argumento da base de datos.
     *
     * @param instalacion A instalación cuxos datos se queren eliminar.
     * @return O resultado da operación levada a cabo.
     * @throws ExcepcionBD Excepción asociada a problemas que poden xurdir ao actualizar a base de datos.
     */
    public TipoResultados borrarInstalacion(Instalacion instalacion) throws ExcepcionBD {
        return xestionInstalacions.borrarInstalacion(instalacion);
    }

    /**
     * Método que tenta modificar os datos da instalación pasada como argumento na base de datos.
     *
     * @param instalacion Os datos da instalación para ser modificados.
     * @return O resultado da operación levada a cabo.
     * @throws ExcepcionBD Excepción asociada a posibles problemas dados ao actualizar a base de datos.
     */
    public TipoResultados modificarInstalacion(Instalacion instalacion) throws ExcepcionBD {
        return xestionInstalacions.modificarInstalacion(instalacion);
    }

    /**
     * Método que nos permite buscar instalacións na base de datos, tanto con coma sen filtros.
     *
     * @param instalacion Se non é null, a consulta das instalacións realizarase en base aos campos desta instalación.
     * @return Se instalación non é null, devolveranse as instalacións que coincidan cos campos de consulta, en caso
     * contrario, devolverase un listado de todas as instalacións.
     */
    public ArrayList<Instalacion> buscarInstalacions(Instalacion instalacion) {
        return xestionInstalacions.buscarInstalacions(instalacion);
    }

    /**
     * Método que nos permite consultar unha instalación concreta:
     *
     * @param instalacion A instalación de referencia para a que se consultará a información
     * @return A instalación con todos os datos, actualizada totalmente.
     */
    public Instalacion consultarInstalacion(Instalacion instalacion) {
        return xestionInstalacions.consultarInstalacion(instalacion);
    }


    /*
        Xestión TIPOS de actividade
     */

    /**
     * Método que nos permite introducir na base de datos a información dun novo tipo de actividade, cuxa información
     * se pasa como arugmento.
     *
     * @param tipoActividade Os datos do tipo de actividade a insertar.
     * @return Se instalación non é null, devolveranse as instalacións que coincidan cos campos de consulta, en caso
     * @throws ExcepcionBD Excepción asociada a problemas que ocorran na actualización da base de datos.
     */
    public TipoResultados crearTipoActividade(TipoActividade tipoActividade) throws ExcepcionBD {
        return xestionActividades.crearTipoActividade(tipoActividade);
    }

    /**
     * Método que nos permite modificar os datos do tipo de actividade pasado como argumento. Suponse que ese tipo de
     * actividade xa está rexistrado e, polo tanto, ten un código asociado.
     *
     * @param tipoActividade O tipo de actividade cos datos a actualizar.
     * @return Se instalación non é null, devolveranse as instalacións que coincidan cos campos de consulta, en caso
     * @throws ExcepcionBD Excepción asociada a problemas que poidan ocorrer durante a modificación na base de datos.
     */
    public TipoResultados modificarTipoActividade(TipoActividade tipoActividade) throws ExcepcionBD {
        return xestionActividades.modificarTipoActividade(tipoActividade);
    }

    /**
     * Método que nos permite eliminar da base de datos o tipo de actividade pasado como argumento.
     *
     * @param tipoActividade O tipo de actividade que se quere eliminar.
     * @return Se instalación non é null, devolveranse as instalacións que coincidan cos campos de consulta, en caso
     * @throws ExcepcionBD Excepción asociada a problemas que ocorran durante a actualización da base de datos.
     */
    public TipoResultados eliminarTipoActividade(TipoActividade tipoActividade) throws ExcepcionBD {
        return xestionActividades.eliminarTipoActividade(tipoActividade);
    }

    /**
     * Método que ofrece un conxunto de tipos de actividade contidos na base de datos.
     *
     * @param tipoActividade O tipo de actividade modelo co que se vai a facer a búsqueda.
     * @return Se o tipo de actividade é null, devolveranse todos os tipos de actividades rexistrados, en caso contrario
     * todos os tipos de actividade que teñan coincidencia co nome que ten o tipo pasado como argumento.
     */
    public ArrayList<TipoActividade> buscarTiposActividades(TipoActividade tipoActividade) {
        return xestionActividades.buscarTiposActividades(tipoActividade);
    }

    /**
     * Método que nos permite consultar un tipo de actividade a partir do código do tipo pasado como argumento.
     *
     * @param tipoActividade O tipo de actividade do que se collerá o código para a consulta.
     * @return O tipo de actividade co código buscado (se aínda existe na base de datos).
     */
    public TipoActividade consultarTipoActividade(TipoActividade tipoActividade) {
        return xestionActividades.consultarTipoActividade(tipoActividade);
    }


    /*
        Xestión cursos
     */

    /**
     * Método que nos permite introducir os datos dun novo curso na base de datos.
     *
     * @param curso O curso a insertar
     * @return O resultado da operación
     * @throws ExcepcionBD Excepción asociada a problemas que puideron ocorrer na base de datos.
     */
    public TipoResultados rexistrarCurso(Curso curso) throws ExcepcionBD {
        return xestionCursos.rexistrarCurso(curso);
    }

    /**
     * Método que nos permite realizar modificacións na información xeral dun curso determinado.
     *
     * @param curso O curso do que se quere modificar a información, cos datos modificados.
     * @return O resultado da operación realizada.
     * @throws ExcepcionBD Excepción asociada a problemas producidos coa base de datos.
     */
    public TipoResultados modificarCurso(Curso curso) throws ExcepcionBD {
        return xestionCursos.modificarCurso(curso);
    }

    /**
     * Método que nos permite cancelar un curso, e polo tanto borrar a súa información da base de datos.
     *
     * @param curso   O curso que se quere borrar.
     * @param mensaxe A mensaxe que se envía aos participantes polo borrado.
     * @return O resultado da operación
     * @throws ExcepcionBD Excepción asociada a problemas que poden ocorrer durante o borrado.
     */
    public TipoResultados cancelarCurso(Curso curso, Mensaxe mensaxe) throws ExcepcionBD {
        return xestionCursos.cancelarCurso(curso, mensaxe);
    }

    /**
     * Método que nos permite consultar os cursos que hai almacenados na base de datos.
     *
     * @param curso Curso polo que se realiza a busca.
     * @return Se curso vale null, devolveranse todos os cursos, noutro caso, filtraranse polo nome do curso pasado.
     */
    public ArrayList<Curso> consultarCursos(Curso curso) {
        return xestionCursos.consultarCursos(curso);
    }

    /**
     * Método que nos permite recuperar datos máis concretos dun curso. Non só datos contidos na táboa de cursos,
     * máis información todavía.
     *
     * @param curso Información do curso do que se queren recuperar os datos (o atributo importante é o código).
     * @return Datos completos do curso procurado.
     */
    public Curso recuperarDatosCurso(Curso curso) {
        return xestionCursos.recuperarDatosCurso(curso);
    }

    /**
     * Método que nos permite recuperar información suficiente do curso como para elaborar o informe que ofrecer ao
     * usuario que o consulta.
     *
     * @param curso Información do curso do que se queren recuperar os datos para o informe.
     * @return Datos completos do curso, incluíndo información adicional necesaria para a elaboración do informe.
     */
    public Curso informeCurso(Curso curso) {
        return xestionCursos.informeCurso(curso);
    }

    /**
     * Método que nos permite levar a cabo a activación dun curso:
     *
     * @param curso Os datos do curso que se quere activar.
     * @return O resultado da operación
     * @throws ExcepcionBD Excepción asociada a problemas producidos na base de datos.
     */
    public TipoResultados activarCurso(Curso curso) throws ExcepcionBD {
        return xestionCursos.activarCurso(curso);
    }

    /**
     * Método que nos permite consultar os que esta non esta apuntado un usuario pero estan dispoñibles para apuntarse
     *
     * @param usuario Usuario co que se realiza a busqueda
     * @return Devolverase un ArrayList con todos os cursos nos que esta apuntado o usuario
     */
    public ArrayList<Curso> consultarCursosDisponhibles(Curso curso, Usuario usuario) {
        return xestionCursos.consultarCursosDisponhibles(curso, usuario);
    }


    /**
     * Metodo que nos permite apuntar a un usuario nun curso
     *
     * @param curso   Curso ó que se quere apuntar o usuario
     * @param usuario Usuario que se quer apuntar o curso
     * @return Un enum que especifica como foi a execución do método
     * @throws ExcepcionBD Excepción asociada a problemas que poden ocorrer durante as consultas
     */
    public TipoResultados apuntarseCurso(Curso curso, Usuario usuario) throws ExcepcionBD {
        return xestionCursos.apuntarseCurso(curso, usuario);
    }

    /**
     * Metodo que nos permite desapuntar a un usuario a un curso
     *
     * @param curso   Curso do que se vai desapuntar
     * @param usuario Usuario que se vai desapuntar
     * @return Un enum que especifica como foi a execución do método
     * @throws ExcepcionBD Excepción asociada a problemas que poden ocorrer durante as consultas
     */
    public TipoResultados desapuntarseCurso(Curso curso, Usuario usuario) throws ExcepcionBD {
        return xestionCursos.desapuntarseCurso(curso, usuario);
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
    public ArrayList<Curso> consultarCursosUsuario(Curso curso, Usuario usuario) {
        return xestionCursos.consultarCursosUsuario(curso, usuario);
    }


    /**
     * Método que nos permite saber se un usuario esta apuntado nun curso
     *
     * @param curso   Curso no que queremos saber se esta apuntado
     * @param usuario Usuario que queremos saber se esta apuntado
     * @return Retorna true cando o usuario xa se atope apuntado no curso e false cando non.
     */
    public boolean estarApuntado(Curso curso, Usuario usuario) {
        return xestionCursos.estarApuntado(curso, usuario);
    }


    /*
        Xestión TIPOS de material
     */

    /**
     * Método que permite engadir unh nova tupla na base de datos cun novo tipo de material.
     *
     * @param tipoMaterial Datos do tipo de material que se creará (en concreto, o nome).
     * @return Devolve un enum que tipifica os posibles erroes durante a execución do método.
     * @throws ExcepcionBD Excepción procedente do método dao para indicar problemas na inserción.
     */
    public TipoResultados darAltaTipoMaterial(TipoMaterial tipoMaterial) throws ExcepcionBD {
        return xestionTipoMaterial.darAltaTipoMaterial(tipoMaterial);
    }

    /**
     * Método que permite eliminar un tipo de material da base de datos.
     *
     * @param tipoMaterial Datos do tipo de material que se eliminará.
     * @return Devolve un enum que tipifica os posibles erroes durante a execución do método.
     * @throws ExcepcionBD Excepción procedente do método dao para indicar problemas no borrado.
     */
    public TipoResultados borrarTipoMaterial(TipoMaterial tipoMaterial) throws ExcepcionBD {
        return xestionTipoMaterial.borrarTipoMaterial(tipoMaterial);
    }

    /**
     * Método que permite buscar tipos de materiais na base de datos con campos de busqueda, ou sen eles.
     *
     * @param tipoMaterial Se non é null, a consulta realizase en base o nome do tipo de material.
     * @return Se o parametro non é null, será devolto un array con todos os tipos de materiais que coincidan,
     * noutro caso, listanse todos os tipos de materiais.
     */
    public ArrayList<TipoMaterial> buscarTipoMaterial(TipoMaterial tipoMaterial) {
        return xestionTipoMaterial.buscarTipoMaterial(tipoMaterial);
    }


    /*
        Xestión materiais
     */

    /**
     * Método que crea unha nova tupla insertando un material na base de datos.
     *
     * @param material Datos do material que se engadirá na base de datos.
     * @return Devolve un enum que tipifica os posibles erroes durante a execución do método.
     * @throws ExcepcionBD Excepción procedente do método dao para indicar problemas na inserción.
     */
    public TipoResultados darAltaMaterial(Material material) throws ExcepcionBD {
        return xestionMaterial.darAltaMaterial(material);
    }

    /**
     * Método que elimina a tupla dun material na base de datos.
     *
     * @param material Datos do material que se eliminará.
     * @return Devolve un enum que tipifica os posibles errores durante a execución do método.
     * @throws ExcepcionBD Excepción procedente do método dao para indicar problemas no borrado.
     */
    public TipoResultados borrarMaterial(Material material) throws ExcepcionBD {
        return xestionMaterial.borrarMaterial(material);
    }

    /**
     * Método que modifica os datos un material na base de datos
     *
     * @param material Datos do material que se modificará.
     * @return Devolve un enum que tipifica os posibles errores durante a execución do método.
     * @throws ExcepcionBD Excepción procedente do método dao para indicar problemas na modificación.
     */
    public TipoResultados modificarMaterial(Material material) throws ExcepcionBD {
        return xestionMaterial.modificarMaterial(material);
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
        return xestionMaterial.listarMateriais(material);
    }

    /**
     * Método que comproba se certo material existe na base de datos e devolve os datos actualizados.
     *
     * @param material Datos do material que se quere consultar.
     * @return Devolve os datos do material actualizado.
     */
    public Material consultarMaterial(Material material) {
        return xestionMaterial.consultarMaterial(material);
    }


      /*
        Xestión areas
     */

    /**
     * Método que permite engadir unha nova área na base de datos.
     *
     * @param area Información da área a engadir na base de datos
     * @return Devolve un enum que tipifica os posibles erroes durante a execución do método.
     * @throws ExcepcionBD Excepción procedente da base de datos para indicar problemas na inserción.
     */
    public TipoResultados EngadirArea(Area area) throws ExcepcionBD {
        return xestionArea.EngadirArea(area);
    }

    /**
     * Método que permite borrar unha área da base de datos.
     *
     * @param area Área que se desexa eliminar da base de datos.
     * @return Devolve un enum que tipifica os posibles erroes durante a execución do método.
     * @throws ExcepcionBD Excepción procedente da base de datos para indicar problemas no borrado.
     */
    public TipoResultados borrarArea(Area area) throws ExcepcionBD {
        return xestionArea.borrarArea(area);
    }

    /**
     * Método que permite modificar os datos dunha área na base de datos.
     *
     * @param area Área cos datos modificados para realizar ditos cambios na base de datos.
     * @return Devolve un enum que tipifica os posibles erroes durante a execución do método.
     * @throws ExcepcionBD Excepción procedente da base de datos para indicar problemas na modificación.
     */
    public TipoResultados modificarArea(Area area) throws ExcepcionBD {
        return xestionArea.modificarArea(area);
    }

    /**
     * Método que permite dar de alta unha área que xa exisita na base de datos pero, agora estará
     * dispoñible para a realización de actividades.
     *
     * @param area Área que se procura dar de alta.
     * @return Devolve un enum que tipifica os posibles erroes durante a execución do método.
     * @throws ExcepcionBD Excepción procedente da base de datos para indicar problemas na actualización.
     */
    public TipoResultados darDeAltaArea(Area area) throws ExcepcionBD {
        return xestionArea.darDeAltaArea(area);
    }

    /**
     * Método que permite dar de baixa un área, é dicir, non se elimina da base de datos pero,
     * deixará de estar dispoñible para realizar actividades.
     *
     * @param area Área que se quere dar de baixa.
     * @return Devolve un enum que tipifica os posibles erroes durante a execución do método.
     * @throws ExcepcionBD Excepción procedente da base de datos para indicar problemas na actualización.
     */
    public TipoResultados darDeBaixaArea(Area area) throws ExcepcionBD {
        return xestionArea.darDeBaixaArea(area);
    }

    /**
     * Método que permite realizar a busca de áreas en función dos datos dunha instalación e seguindo un modelo de área.
     *
     * @param instalacion Instalación que empregaremos para filtrar.
     * @param area        Área modelo que empregaremos para filtrar.
     * @return Retorna un ArrayList de áreas que cumpran ditas condicións de filtrado.
     */
    public ArrayList<Area> buscarArea(Instalacion instalacion, Area area) {
        return xestionArea.buscarArea(instalacion, area);
    }

    /**
     * Método que nos permite buscar areas na base de datos en función dunha instalación.
     *
     * @param instalacion Se non é null, a consulta realizase en base o codigo da area.
     * @return Se o parametro non é null, será devolto unha ObservableList con todas as areas que coincidan,
     * noutro caso, listaranse todas as areas.
     */
    public ArrayList<Area> listarAreasActivas(Instalacion instalacion) {
        return xestionArea.listarAreasActivas(instalacion);
    }

    /*
    Xestion Actividades
     */

    /**
     * Método que permite engadir unha actividade na base de datos.
     *
     * @param actividade Actividade que se desexa engadir na base de datos.
     * @return Devolve un enum que tipifica os posibles erroes durante a execución do método.
     * @throws ExcepcionBD Excepción asociada a problemas ao tentar facer a inserción sobre a base de datos.
     */
    public TipoResultados EngadirActiviade(Actividade actividade) throws ExcepcionBD {
        return xestionActividade.EngadirActividade(actividade);
    }

    /**
     * Método que permite modificar os datos dunha actividade.
     *
     * @param actVella Datos da actividade actualmente.
     * @param actNova  Datos da actividade polos que se desexan cambiar os actuais.
     * @return Devolve un enum que tipifica os posibles erroes durante a execución do método.
     * @throws ExcepcionBD Excepción asociada a problemas ó tentar facer a modificación na base de datos.
     */
    public TipoResultados modificarActividade(Actividade actVella, Actividade actNova) throws ExcepcionBD {
        return xestionActividade.modificarActividade(actVella, actNova);
    }

    /**
     * Método que permite eliminar os datos dunha actividade da base de datos.
     *
     * @param actividade Actividade que se desexa eliminar da base de datos.
     * @param mensaxe    Mensaxe que se enviara os usuarios que estaban apuntados a dita actividade.
     * @return Devolve un enum que tipifica os posibles erroes durante a execución do método.
     * @throws ExcepcionBD Excepción asociada a problemas ó tentar borrar a actividade da base de datos.
     */
    public TipoResultados borrarActividade(Actividade actividade, Mensaxe mensaxe) throws ExcepcionBD {
        return xestionActividade.borrarActividade(actividade, mensaxe);
    }

    /**
     * Método que permite anotar un usuario a unha actividade como participante.
     *
     * @param actividade Actividade a que se desexa apuntar o usuario.
     * @param usuario    Usuario que se quer apuntar a dita actividade.
     * @return Devolve un enum que tipifica os posibles erroes durante a execución do método.
     * @throws ExcepcionBD Excepción asociada a inserción dunha nova tupla na táboa de realización de actividade.
     */
    public TipoResultados apuntarseActividade(Actividade actividade, Usuario usuario) throws ExcepcionBD {
        return xestionActividade.apuntarseActividade(actividade, usuario);
    }

    /**
     * Método que permite desapuntar un usuario dunha actividade.
     *
     * @param actividade Actividade a que se desexa desapuntar dito usuario.
     * @param usuario    Usuario que se desexa desapuntar de dita actividade.
     * @return Devolve un enum que tipifica os posibles erroes durante a execución do método.
     * @throws ExcepcionBD Excepción asociada o borrado dunha tupla na táboa de datos de realización de actividade.
     */
    public TipoResultados borrarseDeActividade(Actividade actividade, Usuario usuario) throws ExcepcionBD {
        return xestionActividade.borrarseDeActividade(actividade, usuario);
    }

    /**
     * Método que permite listar as actividades filtrándoas a través dunha actividade modelo.
     *
     * @param actividade Actividade modelo que se empregará apra dito filtrado.
     * @return Retorna un ArrayList das actividades que cumpren dita condición ou, no caso de ser null,
     * un ArrayList con todas as posibles actividades.
     */
    public ArrayList<Actividade> buscarActividade(Actividade actividade) {
        return xestionActividade.buscarActividade(actividade);
    }

    /**
     * Método que permite listar os profesores ca posibilidade de listar en función dun tipo de actividade.
     *
     * @param tipoactividade Tipo de actividade para a que se comprobarán, de non ser nula, os profesores que hai.
     * @return Devolve un ArrayList cos profesores que cumpran ditas condicións de filtrado.
     */
    public ArrayList<Persoal> buscarProfesores(TipoActividade tipoActividade) {
        return xestionActividade.buscarProfesores(tipoActividade);
    }

    /**
     * Método que permite listar as actividades nas que participa certo usuario.
     *
     * @param actividade Actividade modelo que se empregará para realizar un filtrado.
     * @param usuario    Usuario que se desexa asegurar que participa nas actividades.
     * @return Devolve un ArrayList que compre cas condicións de filtrado en función do usuario pasado.
     */
    public ArrayList<Actividade> buscarActividadeParticipa(Actividade actividade, Usuario usuario) {
        return xestionActividade.buscarActividadeParticipa(actividade, usuario);
    }

    /**
     * Método que permite listar as actividades onde NON participa certo usuario.
     *
     * @param actividade Actividade modelo que se empregará para realizar un filtrado.
     * @param usuario    Usuario que se desexa asegurar que NON particida nas actividades
     * @return Devolve un ArrayList que compre cas condicións de filtrado da actividade en función do usuario pasado.
     */
    public ArrayList<Actividade> buscarActividadeNONParticipa(Actividade actividade, Usuario usuario) {
        return xestionActividade.buscarActividadeNONParticipa(actividade, usuario);
    }

    /**
     * Método que permite valorar por certo usuario unha actividade na que participará.
     *
     * @param valoracion Puntuación que lle será asignada a dita actividade.
     * @param actividade Actividade que se desexa valorar.
     * @param usuario    Usuario que esta a valorar dita actividade.
     * @throws ExcepcionBD Excepción asociada a actualización na base de datos.
     */
    public TipoResultados valorarActividade(Integer valoracion, Actividade actividade, Usuario usuario) throws ExcepcionBD {
        return xestionActividade.valorarActividade(valoracion, actividade, usuario);
    }

    /**
     * Método que comproba se unha actividade foi valorada por un usuario.
     *
     * @param actividade Actividade que se comproba se foi valorada.
     * @param usuario    Usuario que se comproba se a valorou.
     * @return Devolve true se a actividade xa foi valorada.
     */
    public boolean isValorada(Actividade actividade, Usuario usuario) {
        return xestionActividade.isValorada(actividade, usuario);
    }

    /**
     * Método que permite listar todos os participantes dunha actividade.
     *
     * @param actividade Actividade da que se queren listar os seus participantes.
     * @return Retorna un ArrayList con todos os participantes que estan apuntados na mesma.
     */
    public ArrayList<Socio> listarParticipantes(Actividade actividade) {
        return xestionActividade.listarParticipantes(actividade);
    }

    /**
     * Método que permite recuperar os datos dunha actividade da base de datos a partir das súas claves primarias.
     *
     * @param actividade Actividade da que se obteñen os datos para realizar a consulta en función dos mesmos.
     * @return Retorna a Actividade cos datos actualizados.
     */
    public Actividade recuperarActividade(Actividade actividade) {
        return xestionActividade.recuperarActividade(actividade);
    }
}

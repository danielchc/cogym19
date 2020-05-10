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
        //Chamamos ao método da GUI co que se abre devandita ventá.
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
     * @return O resultado da operación levada a cabo.
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
     * @return O resultado da operación levada a cabo.
     * @throws ExcepcionBD Excepción asociada a problemas que poidan ocorrer durante a actualización da base de datos.
     */
    public TipoResultados modificarTipoActividade(TipoActividade tipoActividade) throws ExcepcionBD {
        return xestionActividades.modificarTipoActividade(tipoActividade);
    }

    /**
     * Método que nos permite eliminar da base de datos o tipo de actividade pasado como argumento.
     *
     * @param tipoActividade O tipo de actividade que se quere eliminar.
     * @return O resultado da operación levada a cabo.
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
     * @return O tipo de actividade co código buscado (se todavía existe na base de datos).
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
    public TipoResultados darAltaTipoMaterial(TipoMaterial tipoMaterial) throws ExcepcionBD {
        return xestionTipoMaterial.darAltaTipoMaterial(tipoMaterial);
    }

    public TipoResultados borrarTipoMaterial(TipoMaterial tipoMaterial) throws ExcepcionBD {
        return xestionTipoMaterial.borrarTipoMaterial(tipoMaterial);
    }

    /**
     * BuscarTipoMaterial -> permite buscar tipos de materiais na base de datos con campos de busqueda, ou sen eles.
     *
     * @param tipoMaterial -> se non é null, a consulta realizase en base o nome do tipo de material.
     * @return -> se o parametro non é null, será devolto un array con todos os tipos de materiais que coincidan,
     * noutro caso, listanse todos os tipos de materiais.
     */
    public ArrayList<TipoMaterial> buscarTipoMaterial(TipoMaterial tipoMaterial) {
        return xestionTipoMaterial.buscarTipoMaterial(tipoMaterial);
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

    public ArrayList<Material> listarMateriais(Material material) {
        return xestionMaterial.listarMateriais(material);
    }

    public Material consultarMaterial(Material material) {
        return xestionMaterial.consultarMaterial(material);
    }

      /*
        Xestión areas
     */

    public TipoResultados EngadirArea(Area area) throws ExcepcionBD {
        return xestionArea.EngadirArea(area);
    }

    public TipoResultados borrarArea(Area area) throws ExcepcionBD {
        return xestionArea.borrarArea(area);
    }

    public TipoResultados modificarArea(Area area) throws ExcepcionBD {
        return xestionArea.modificarArea(area);
    }

    public TipoResultados darDeAltaArea(Area area) throws ExcepcionBD {
        return xestionArea.darDeAltaArea(area);
    }

    public TipoResultados darDeBaixaArea(Area area) throws ExcepcionBD {
        return xestionArea.darDeBaixaArea(area);
    }

    public ArrayList<Area> buscarArea(Instalacion instalacion, Area area) {
        return xestionArea.buscarArea(instalacion, area);
    }

    public Boolean EBaixaArea(Area area) throws ExcepcionBD {
        return xestionArea.EBaixaArea(area);
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

    public TipoResultados EngadirActiviade(Actividade actividade) throws ExcepcionBD {
        return xestionActividade.EngadirActividade(actividade);
    }

    public TipoResultados modificarActividade(Actividade actVella, Actividade actNova) throws ExcepcionBD {
        return xestionActividade.modificarActividade(actVella, actNova);
    }

    public TipoResultados borrarActividade(Actividade actividade, Mensaxe mensaxe) throws ExcepcionBD {
        return xestionActividade.borrarActividade(actividade, mensaxe);
    }

    public TipoResultados apuntarseActividade(Actividade actividade, Usuario usuario) throws ExcepcionBD {
        return xestionActividade.apuntarseActividade(actividade, usuario);
    }

    public TipoResultados borrarseDeActividade(Actividade actividade, Usuario usuario) throws ExcepcionBD {
        return xestionActividade.borrarseDeActividade(actividade, usuario);
    }

    public ArrayList<Actividade> buscarActividade(Actividade actividade) {
        return xestionActividade.buscarActividade(actividade);
    }

    public ArrayList<Persoal> buscarProfesores(TipoActividade tipoActividade) {
        return xestionActividade.buscarProfesores(tipoActividade);
    }

    public ArrayList<Actividade> buscarActividadeParticipa(Actividade actividade, Usuario usuario) {
        return xestionActividade.buscarActividadeParticipa(actividade, usuario);
    }

    public ArrayList<Actividade> buscarActividadeNONParticipa(Actividade actividade, Usuario usuario) {
        return xestionActividade.buscarActividadeNONParticipa(actividade, usuario);
    }

    public TipoResultados valorarActividade(Integer valoracion, Actividade actividade, Usuario usuario) throws ExcepcionBD {
        return xestionActividade.valorarActividade(valoracion, actividade, usuario);
    }

    public boolean isValorada(Actividade actividade, Usuario usuario) {
        return xestionActividade.isValorada(actividade, usuario);
    }

    public ArrayList<Socio> listarParticipantes(Actividade actividade) {
        return xestionActividade.listarParticipantes(actividade);
    }

    public Actividade recuperarActividade(Actividade actividade){
        return xestionActividade.recuperarActividade(actividade);
    }
}

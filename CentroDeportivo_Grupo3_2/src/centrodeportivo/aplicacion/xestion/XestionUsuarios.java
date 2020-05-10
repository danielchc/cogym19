package centrodeportivo.aplicacion.xestion;

import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.baseDatos.FachadaBD;
import centrodeportivo.funcionsAux.Criptografia;
import centrodeportivo.gui.FachadaGUI;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Víctor Barreiro
 * Clase na que se abordarán todas as xestións a nivel de aplicación asociadas aos Usuarios. No noso caso, moi pouco deles.
 */
public class XestionUsuarios {

    /**
     * Atributos da clase de xestion de usuarios: son as fachadas da GUI e da parte da base de datos.
     */
    private FachadaGUI fachadaGUI;
    private FachadaBD fachadaBD;

    /**
     * Constructor da clase de xestión de usuarios.
     *
     * @param fachadaGUI A fachada da interface gráfica.
     * @param fachadaBD  A fachada da base de datos.
     */
    public XestionUsuarios(FachadaGUI fachadaGUI, FachadaBD fachadaBD) {
        // Asignamos as fachadas pasadas como argumento aos atributos correspondentes da clase.
        this.fachadaGUI = fachadaGUI;
        this.fachadaBD = fachadaBD;
    }

    /**
     * Método que nos permitirá levar a cabo a validación dun usuario:
     *
     * @param login       O login introducido polo usuario.
     * @param contrasinal O contrasinal introducido polo usuario.
     * @return booleano que nos indica se a validación foi correcta ou non.
     */
    public boolean validarUsuario(String login, String contrasinal) {
        // Chamaremos á fachada da base de datos para resolver a consulta.
        // Ciframos neste paso a clave para ser almacenada na base de datos mediante sha256:
        return fachadaBD.validarUsuario(login, Criptografia.hashSHA256(contrasinal));
    }

    /**
     * Método que nos permitirá consultar os datos esenciais dun usuario:
     *
     * @param login O login do usuario que se quere consultar.
     * @return Usuario con algúns datos asociados na base de datos ao login pasado como argumento.
     */
    public Usuario consultarUsuario(String login) {
        // Chamamos ao método correspondente da fachada da base de datos.
        return fachadaBD.consultarUsuario(login);
    }

}

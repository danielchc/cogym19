package centrodeportivo.baseDatos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.tipos.TipoUsuario;
import centrodeportivo.aplicacion.obxectos.usuarios.Persoal;
import centrodeportivo.aplicacion.obxectos.usuarios.Socio;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;

import java.sql.*;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Víctor Barreiro
 * Este é o dao relacionado con aqueles accesos á base de datos que involucren principalmente aos usuarios.
 */
public final class DAOUsuarios extends AbstractDAO {

    /**
     * Constructor deste DAO.
     * @param conexion A conexión coa base de datos.
     * @param fachadaAplicacion Referencia coa fachada da parte de aplicación.
     */
    protected DAOUsuarios(Connection conexion, FachadaAplicacion fachadaAplicacion) {
        //Chamamos á clase pai co constructor que pide os mesmos dous argumentos.
        super(conexion,fachadaAplicacion);
    }

    /**
     * Método que nos permitirá levar a cabo a validación dun usuario:
     * @param login O login introducido polo usuario.
     * @param contrasinal O contrasinal introducido polo usuario.
     * @return booleano que nos indica se a validación foi correcta ou non.
     */
    protected boolean validarUsuario(String login, String contrasinal) {
        //O que faremos será comprobar se hai un usuario rexistrado estrictamente con ese login e ese contrasinal.
        //Á parte, regularemos que non se dera de baixa para que non poida iniciar sesión.
        PreparedStatement stmUsuarios = null;
        ResultSet resultValidacion;
        Connection con;
        //Devolveremos un booleano como resultado.
        boolean resultado = false;

        //Recuperamos a conexión:
        con = super.getConexion();

        //Intentamos facer devandita consulta:
        try {
            //Preparamos a consulta:
            stmUsuarios = con.prepareStatement("SELECT * FROM usuario WHERE login=? AND contrasinal=? AND dataBaixa IS NULL");
            //Completámolas cos campos con ?
            stmUsuarios.setString(1, login);
            stmUsuarios.setString(2, contrasinal);
            //Executaremos a consulta:
            resultValidacion = stmUsuarios.executeQuery();
            //Comprobamos se hai resultado chamando a next. Con iso saberemos se o usuario existe e está dado de alta.
            resultado = resultValidacion.next();

            //Como se rematou, faise o commit:
            con.commit();
        } catch (SQLException e) {
            //En caso de recibir unha excepción, tratamos de facer o rollback:
            try{
                con.rollback();
            } catch (SQLException ex){
                ex.printStackTrace();
            }
            //Logo, imprimimos o stack trace:
            e.printStackTrace();
        }finally {
            //Para rematar, en calquera caso, tentamos pechar o statement:
            try {
                stmUsuarios.close();
            } catch (SQLException e){
                System.out.println("Imposible pechar os cursores");
            }
        }
        //Saímos devolvendo o booleano que indica o resultado da validación:
        return resultado;
    }

    protected Usuario consultarUsuario(String login) {
        //Nesta consulta comprobaremos primeiro o tipo de usuario e recupesaremos algúns datos comúns do usuario.
        PreparedStatement stmUsuario = null;
        ResultSet rsUsuarios;
        //Empregaremos un atributo para o tipo de usuario e o usuario:
        TipoUsuario tipoUsuario = null;
        Usuario resultado = null;

        try {
            //Intentamos consultar en primeiro lugar o tipo de usuario:
            stmUsuario = super.getConexion().prepareStatement("SELECT CASE" +
                    " WHEN EXISTS(SELECT * FROM socio as s WHERE s.login = ?) THEN 'socio'" +
                    " WHEN EXISTS(SELECT * FROM persoal as pe WHERE pe.login = ? and pe.profesorActivo = false) THEN 'persoal'" +
                    " WHEN EXISTS(SELECT * FROM persoal as pe WHERE pe.login = ? and pe.profesorActivo = true) THEN 'profesor' " +
                    " ELSE 'erro'" +
                    " END as tipoUsuario"
            );
            //Completamos a consulta. Os tres campos interrogantes son o mesmo: o login do usuario.
            stmUsuario.setString(1, login);
            stmUsuario.setString(2, login);
            stmUsuario.setString(3, login);

            //Realizamos a consulta:
            rsUsuarios = stmUsuario.executeQuery();
            //Seguro que ten resultado:
            rsUsuarios.next();
            //En función do string devolto (se chegamos a este punto, sabendo que non se pode borrar un usuario, vaisenos
            //devolver ou socio ou persoal ou profesor), escollemos o tipo de usuario.
            //De todos os xeitos, por se isto tivese outras aplicacións posibles, mantense o caso do erro por se acaso.
            switch(rsUsuarios.getString("tipoUsuario")) {
                case "socio": tipoUsuario = TipoUsuario.Socio; break; //O usuario é socio.
                case "persoal": tipoUsuario = TipoUsuario.Persoal; break; //O usuario é persoal
                case "profesor": tipoUsuario = TipoUsuario.ProfesorActivo; break; //O usuario é un profesor activo, ademais de persoal.
            }

            //Agora consultaremos os datos do usuario. Na nosa parte da aplicación somentes tomaremos datos realmente esenciais.
            if(tipoUsuario==TipoUsuario.Socio) {
                //Neste caso consultaremos un socio:
                stmUsuario = super.getConexion().prepareStatement("SELECT nome, dni, login " +
                        " FROM vistasocio " +
                        " WHERE login = ? " +
                        "   AND dataBaixa IS NULL");
                stmUsuario.setString(1, login);
                if (rsUsuarios.next()) {
                    resultado = new Socio(rsUsuarios.getString("nome"),
                            rsUsuarios.getString("dni"),
                            rsUsuarios.getString("login"));
                }
            }else{
                //Noutro caso (profesor/persoal): consultamos un membro do persoal.
                stmUsuario = super.getConexion().prepareStatement("SELECT nome, dni, login, profesorActivo " +
                        " FROM vistapersoal " +
                        " WHERE login = ? " +
                        "   AND dataBaixa IS NULL");
                stmUsuario.setString(1, login);
                rsUsuarios = stmUsuario.executeQuery();
                if (rsUsuarios.next()) {
                    resultado = new Persoal(rsUsuarios.getString("nome"),
                            rsUsuarios.getString("dni"),
                            rsUsuarios.getString("login"),
                            rsUsuarios.getBoolean("profesorActivo"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                stmUsuario.close();
            } catch (SQLException e){
                System.out.println("Imposible pechar os cursores");
            }
        }
        return resultado;
    }

}

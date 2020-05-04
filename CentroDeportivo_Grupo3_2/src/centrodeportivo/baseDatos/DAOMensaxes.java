package centrodeportivo.baseDatos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.Mensaxe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Víctor Barreiro
 * Clase na que se inclúen métodos relativos ao envío de mensaxes a membros do centro deportivo. No noso caso,
 * centrarémonos en notificacións aos socios en forma de avisos de parte dun dos membros do persoal.
 * Facemos tamén a clase dado que nun futuro poderíamos seguir engadindo funcionalidade.
 */

public final class DAOMensaxes extends AbstractDAO{

    /**
     * Constructor do DAO de mensaxes.
     * @param conexion Referencia á conexión coa base de datos
     * @param fachadaAplicacion Referencia á fachada da parte de aplicación
     */
    public DAOMensaxes(Connection conexion, FachadaAplicacion fachadaAplicacion){
        //Asignamos os atributos ao constructor da clase pai:
        super(conexion, fachadaAplicacion);
    }

    /**
     * Método que nos permite enviar unha mensaxe de aviso a todos os socios.
     * @param mensaxe A mensaxe a transmitir
     * @throws ExcepcionBD Excepción que se pode producir por problemas coa base de datos.
     */
    public void enviarAvisoSocios(Mensaxe mensaxe) throws ExcepcionBD {
        //Consultaremos todos os socios que hai rexistrados e enviaremoslles mensaxes:
        PreparedStatement stmMensaxes = null;
        PreparedStatement stmUsuarios = null;
        ResultSet rsUsuarios = null;

        Connection con;

        //Recuperamos a conexion:
        con = super.getConexion();

        //Comezaremos a intentar o envío da mensaxe:
        try {
            //Primeiro consultamos os login de todos os socios:
            stmUsuarios = con.prepareStatement("SELECT login FROM socio");
            //Non precisamos pasarlle nada, posto que simplemente queremos recuperar os socios todos:
            rsUsuarios = stmUsuarios.executeQuery();

            //Imos preparando xa a inserción na táboa de mensaxes:
            stmMensaxes = con.prepareStatement("INSERT INTO enviarmensaxe (emisor, receptor, contido, lido)" +
                    " VALUES (?, ?, ?, ?)");

            //Establecemos o emisor, o contido e o lido porque non varían.
            stmMensaxes.setString(1, mensaxe.getEmisor().getLogin());
            stmMensaxes.setString(3, mensaxe.getContido());
            stmMensaxes.setBoolean(4, false);

            //Imos recuperando todos os resultados e, ao mesmo tempo, enviando as mensaxes:
            while (rsUsuarios.next()) {
                //Poñemos agora como parámetro da consulta 'receptor' cada un dos logins recibidos:
                stmMensaxes.setString(2, rsUsuarios.getString("login"));
                //Executamos entón a actualización:
                stmMensaxes.executeUpdate();
            }

        } catch(SQLException e){
            //Lanzamos a nosa propia excepción:
            throw new ExcepcionBD(con,e);
        } finally {
            //Intentamos pechar os statement:
            try {
                stmMensaxes.close();
                stmUsuarios.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
    }


}

package centrodeportivo.baseDatos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.Mensaxe;
import centrodeportivo.aplicacion.obxectos.actividades.Actividade;
import centrodeportivo.aplicacion.obxectos.actividades.Curso;

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

public final class DAOMensaxes extends AbstractDAO {

    /**
     * Constructor do DAO de mensaxes
     *
     * @param conexion          Referencia á conexión coa base de datos
     * @param fachadaAplicacion Referencia á fachada da parte de aplicación
     */
    public DAOMensaxes(Connection conexion, FachadaAplicacion fachadaAplicacion) {
        // Asignaremos estes atributos no constructor da clase pai ao que chamamos:
        super(conexion, fachadaAplicacion);
    }

    /**
     * Método que nos permite enviar unha mensaxe de aviso a todos os socios.
     *
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
            //Primeiro consultamos os login de todos os socios (que non estivesen dados de baixa!):
            stmUsuarios = con.prepareStatement("SELECT login FROM vistasocio WHERE dataBaixa IS NULL");
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

            //Facemos o commit para rematar:
            con.commit();

        } catch (SQLException e) {
            //Lanzamos a nosa propia excepción:
            throw new ExcepcionBD(con, e);
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

    /**
     * Método que nos permite enviar un aviso aos socios dun curso determinado.
     *
     * @param mensaxe A mensaxe que se vai a enviar aos socios.
     * @param curso   O curso ao que pertencen os usuarios aos que se lle vai enviar a mensaxe.
     * @throws ExcepcionBD Excepción que se pode producir por problemas coa base de datos.
     */
    public void enviarAvisoSociosCurso(Mensaxe mensaxe, Curso curso) throws ExcepcionBD {
        //Primeiro recuperaremos os datos dos socios que están realiando o curso e logo mandaremos a mensaxe:
        PreparedStatement stmUsuarios = null;
        PreparedStatement stmMensaxes = null;
        ResultSet rsUsuarios;
        Connection con;

        //Recuperamos a conexión coa base de datos:
        con = super.getConexion();

        //Tentamos levar a cabo a consulta:
        try {
            //Comezaremos por buscar os participantes no curso:
            stmUsuarios = con.prepareStatement("SELECT usuario FROM realizarcurso WHERE curso = ?");
            //Completamos a consulta co código do curso:
            stmUsuarios.setInt(1, curso.getCodCurso());
            //Agora executamos a consulta:
            rsUsuarios = stmUsuarios.executeQuery();

            //Agora procedemos ao envío das mensaxes:
            //Preparamos o envío do mensaxe, o que cambia é o destinatario:
            stmMensaxes = con.prepareStatement("INSERT INTO enviarmensaxe (emisor, receptor, contido, lido) " +
                    "VALUES (?, ?, ?, ?)");

            //Asignamos dende xa os campos invariantes:
            stmMensaxes.setString(1, mensaxe.getEmisor().getLogin());
            stmMensaxes.setString(3, mensaxe.getContido());
            stmMensaxes.setBoolean(4, false);


            //A medida que lemos o result set imos procesando os envíos de mensaxes:
            while (rsUsuarios.next()) {
                //Establecemos o parámetro variante en cada inserción:
                stmMensaxes.setString(2, rsUsuarios.getString("usuario"));
                //Executamos entón actualizacións sobre a base de datos:
                stmMensaxes.executeUpdate();
            }

            //Facemos agora xa o commit, dado que rematamos a transacción:
            con.commit();

        } catch (SQLException e) {
            //Lanzamos a nosa propia excepción cara arriba:
            throw new ExcepcionBD(con, e);
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

    /**
     * Método que nos permite realizar o envío dun aviso aos socios dunha actividade.
     *
     * @param mensaxe    A mensaxe a enviar a eses socios.
     * @param actividade A actividade de referencia da que se collerán os participantes aos que enviar as mensaxes.
     * @throws ExcepcionBD Excepción que se pode producir por problemas coa base de datos.
     */
    public void enviarAvisoSociosAct(Mensaxe mensaxe, Actividade actividade) throws ExcepcionBD {
        //O que faremos será recoller primeiro os participantes da actividade e logo mandarlles a mensaxe:
        PreparedStatement stmUsuarios = null;
        PreparedStatement stmMensaxes = null;
        ResultSet rsUsuarios;

        Connection con;
        //Recuperamos a conexión:
        con = super.getConexion();

        //Intentamos levar a cabo todas as accións:
        try {
            stmUsuarios = con.prepareStatement("SELECT * FROM realizaractividade " +
                    " WHERE dataactividade = ? " +
                    "   AND area = ? " +
                    "   AND instalacion = ?");

            //Completamos a consulta:
            stmUsuarios.setTimestamp(1, actividade.getData());
            stmUsuarios.setInt(2, actividade.getArea().getCodArea());
            stmUsuarios.setInt(3, actividade.getArea().getInstalacion().getCodInstalacion());

            //Realizamos a consulta sobre a base de datos:
            rsUsuarios = stmUsuarios.executeQuery();

            //Imos preparando xa a inserción para o envío dos avisos:
            stmMensaxes = con.prepareStatement("INSERT INTO enviarmensaxe (emisor, receptor, contido, lido) " +
                    " VALUES(?, ?, ?, ?) ");

            //Completamos os campos invariantes da consulta:
            stmMensaxes.setString(1, mensaxe.getEmisor().getLogin());
            stmMensaxes.setString(3, mensaxe.getContido());
            stmMensaxes.setBoolean(4, false);

            //Imos procesando o resultado e facendo os envíos que sexan precisos:
            while (rsUsuarios.next()) {
                //Completamos co que falta a inserción:
                stmMensaxes.setString(2, rsUsuarios.getString("usuario"));
                //Facemos a actualización:
                stmMensaxes.executeUpdate();
            }

            //Feito isto, levamos a cabo o commit:
            con.commit();

        } catch (SQLException e) {
            //O que facemos é lanzar para arriba a nosa propia excepción:
            throw new ExcepcionBD(con, e);
        } finally {
            //Tratamos de pechar os statements usados nesta consulta:
            try {
                stmMensaxes.close();
                stmUsuarios.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
    }
}

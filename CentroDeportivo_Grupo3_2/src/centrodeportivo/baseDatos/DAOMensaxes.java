package centrodeportivo.baseDatos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.Mensaxe;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public final class DAOMensaxes extends AbstractDAO{
    //Constructor:
    public DAOMensaxes(Connection conexion, FachadaAplicacion fachadaAplicacion){
        //Chamamos ao constructor da clase pai:
        super(conexion, fachadaAplicacion);
    }

    public void enviarAvisoSocios(Mensaxe mensaxe) throws ExcepcionBD {
        //Recuperaranse todos os socios almacenados na base de datos e enviaráselle a mensaxe pasada.
        PreparedStatement stmMensaxes = null;
        PreparedStatement stmUsuarios = null;
        ResultSet rsUsuarios = null;
        Connection con;

        //Recuperamos a conexión:
        con = super.getConexion();

        //Intentamos realizar todas as tarefas:
        try{
            //Primeiro consultamos os login de todos os socios:
            stmUsuarios = con.prepareStatement("SELECT login FROM socio");
            //Non precisamos pasarlle nada, posto que simplemente queremos recuperar os socios todos:
            rsUsuarios = stmUsuarios.executeQuery();

            //Imos preparando xa a inserción na táboa de mensaxes:
            stmMensaxes = con.prepareStatement("INSERT INTO mensaxe (emisor, receptor, contido, lido)" +
                    " VALUES (?, ?, ?, ?)");

            //Establecemos o emisor, o contido e o lido porque non varían.
            stmMensaxes.setString(1, mensaxe.getEmisor().getLogin());
            stmMensaxes.setString(3, mensaxe.getContido());
            stmMensaxes.setBoolean(4, false);

            //Imos recuperando todos os resultados e, ao mesmo tempo, enviando as mensaxes:
            while(rsUsuarios.next()){
                //Poñemos agora como parámetro da consulta un dos logins recibidos:
                stmMensaxes.setString(2,rsUsuarios.getString("login"));
                //Executamos entón a actualización:
                stmMensaxes.executeUpdate();
            }

            //Rematado isto, podemos poñer o commit: ou se mandan as mensaxes a todos ou a ningún:
            con.commit();
        } catch(SQLException e){
            //En caso de excepción, lanzaremos a nosa propia:
            throw new ExcepcionBD(con, e);
        } finally {
            //Tentamos pechar o statement.
            try{
                stmMensaxes.close();
                stmUsuarios.close();
            } catch(SQLException e){
                System.out.printf("Imposible pechar os cursores");
            }
        }
    }

}

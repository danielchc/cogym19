package centrodeportivo.baseDatos.dao;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.Mensaxe;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOMensaxes extends AbstractDAO {

    public DAOMensaxes(Connection conexion, FachadaAplicacion fachadaAplicacion) {
        super(conexion,fachadaAplicacion);
    }

    public void enviarMensaxe(Mensaxe m) throws SQLException {
        Connection conexion=super.getConexion();
        PreparedStatement stmMensaxe=null;

        stmMensaxe=conexion.prepareStatement("INSERT INTO enviarMensaxes (emisor,receptor,dataEnvio,contido,lido) VALUES (?,?,NOW(),?,?);");
        stmMensaxe.setString(1,m.getEmisor());
        stmMensaxe.setString(2,m.getReceptor());
        stmMensaxe.setString(3,m.getContido());
        stmMensaxe.setBoolean(4,m.isLido());
        stmMensaxe.executeUpdate();
        conexion.commit();
    }

    public void enviarMensaxe(Usuario emisor, ArrayList<Usuario> receptores,String mensaxe) throws SQLException{
        Connection conexion=super.getConexion();
        PreparedStatement stmMensaxe=null;

        for(Usuario receptor:receptores){
            stmMensaxe=conexion.prepareStatement("INSERT INTO enviarMensaxes (emisor,receptor,dataEnvio,contido,lido) VALUES (?,?,NOW(),?,?);");
            stmMensaxe.setString(1,emisor.getLogin());
            stmMensaxe.setString(2,receptor.getLogin());
            stmMensaxe.setString(3,mensaxe);
            stmMensaxe.setBoolean(4,false);
            stmMensaxe.executeUpdate();
        }
        conexion.commit();
    }

    public void marcarMensaxeComoLido(Mensaxe m) throws SQLException{
        Connection conexion=super.getConexion();
        PreparedStatement stmMensaxe=null;

        stmMensaxe=conexion.prepareStatement("UPDATE enviarMensaxes SET lido=TRUE WHERE emisor=? AND receptor=? AND dataEnvio=?;");
        stmMensaxe.setString(1,m.getEmisor());
        stmMensaxe.setString(2,m.getReceptor());
        stmMensaxe.setTimestamp(3,m.getDataEnvio());
        stmMensaxe.executeUpdate();
        conexion.commit();
    }

    public ArrayList<Mensaxe> listarMensaxesRecibidos(String loginReceptor) throws SQLException{
        ArrayList<Mensaxe> mensaxes=new ArrayList<>();
        Connection conexion=super.getConexion();
        PreparedStatement stmMensaxe = null;
        ResultSet resultMensaxes;

        stmMensaxe=conexion.prepareStatement("SELECT * FROM enviarMensaxes WHERE receptor=? ORDER BY dataEnvio DESC;");
        stmMensaxe.setString(1,loginReceptor);
        resultMensaxes=stmMensaxe.executeQuery();
        while (resultMensaxes.next()){
            mensaxes.add(new Mensaxe(
                    resultMensaxes.getString("emisor"),
                    resultMensaxes.getString("receptor"),
                    resultMensaxes.getTimestamp("dataEnvio"),
                    resultMensaxes.getString("contido"),
                    resultMensaxes.getBoolean("lido")
            ));
        }
        return mensaxes;
    }
}

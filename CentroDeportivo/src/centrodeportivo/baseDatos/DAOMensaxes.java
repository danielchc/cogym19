package centrodeportivo.baseDatos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.Mensaxe;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.baseDatos.AbstractDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public final class DAOMensaxes extends AbstractDAO {
    protected DAOMensaxes(Connection conexion, FachadaAplicacion fachadaAplicacion) {
        super(conexion,fachadaAplicacion);
    }

    protected void enviarMensaxe(Mensaxe m) throws SQLException {

        PreparedStatement stmMensaxe=null;

        stmMensaxe=super.getConexion().prepareStatement("INSERT INTO enviarMensaxes (emisor,receptor,dataEnvio,contido,lido) VALUES (?,?,NOW(),?,?);");
        stmMensaxe.setString(1,m.getEmisor().getLogin());
        stmMensaxe.setString(2,m.getReceptor().getLogin());
        stmMensaxe.setString(3,m.getContido());
        stmMensaxe.setBoolean(4,m.isLido());
        stmMensaxe.executeUpdate();
        super.getConexion().commit();
    }

    protected void enviarMensaxe(Usuario emisor, ArrayList<Usuario> receptores,String mensaxe) throws SQLException{
        PreparedStatement stmMensaxe=null;

        for(Usuario receptor:receptores){
            stmMensaxe=super.getConexion().prepareStatement("INSERT INTO enviarMensaxes (emisor,receptor,dataEnvio,contido,lido) VALUES (?,?,NOW(),?,?);");
            stmMensaxe.setString(1,emisor.getLogin());
            stmMensaxe.setString(2,receptor.getLogin());
            stmMensaxe.setString(3,mensaxe);
            stmMensaxe.setBoolean(4,false);
            stmMensaxe.executeUpdate();
        }
        super.getConexion().commit();
    }

    protected void marcarMensaxeComoLido(Mensaxe m) throws SQLException{
        PreparedStatement stmMensaxe=null;

        stmMensaxe=super.getConexion().prepareStatement("UPDATE enviarMensaxes SET lido=TRUE WHERE emisor=? AND receptor=? AND dataEnvio=?;");
        stmMensaxe.setString(1,m.getEmisor().getLogin());
        stmMensaxe.setString(2,m.getReceptor().getLogin());
        stmMensaxe.setTimestamp(3,m.getDataEnvio());
        stmMensaxe.executeUpdate();
        super.getConexion().commit();
    }

    protected ArrayList<Mensaxe> listarMensaxesRecibidos(String loginReceptor) throws SQLException{
        ArrayList<Mensaxe> mensaxes=new ArrayList<>();
        PreparedStatement stmMensaxe = null;
        ResultSet resultMensaxes;

        stmMensaxe=super.getConexion().prepareStatement("SELECT * FROM enviarMensaxes WHERE receptor=? ORDER BY dataEnvio DESC;");
        stmMensaxe.setString(1,loginReceptor);
        resultMensaxes=stmMensaxe.executeQuery();
        while (resultMensaxes.next()){
            mensaxes.add(new Mensaxe(
                    new Usuario(resultMensaxes.getString("emisor")),
                    new Usuario(resultMensaxes.getString("receptor")),
                    resultMensaxes.getTimestamp("dataEnvio"),
                    resultMensaxes.getString("contido"),
                    resultMensaxes.getBoolean("lido")
            ));
        }
        return mensaxes;
    }
}

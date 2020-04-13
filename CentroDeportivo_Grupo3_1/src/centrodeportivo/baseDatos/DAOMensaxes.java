package centrodeportivo.baseDatos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.Mensaxe;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.baseDatos.AbstractDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public final class DAOMensaxes extends AbstractDAO {
    protected DAOMensaxes(Connection conexion, FachadaAplicacion fachadaAplicacion) {
        super(conexion, fachadaAplicacion);
    }

    protected void enviarMensaxe(Mensaxe m) throws ExcepcionBD {

        PreparedStatement stmMensaxe = null;
        try {
            stmMensaxe = super.getConexion().prepareStatement("INSERT INTO enviarMensaxe (emisor,receptor,dataEnvio,contido,lido) VALUES (?,?,NOW(),?,?);");
            stmMensaxe.setString(1, m.getEmisor().getLogin());
            stmMensaxe.setString(2, m.getReceptor().getLogin());
            stmMensaxe.setString(3, m.getContido());
            stmMensaxe.setBoolean(4, m.isLido());
            stmMensaxe.executeUpdate();
            super.getConexion().commit();
        } catch (SQLException e) {
            throw new ExcepcionBD(super.getConexion(), e);
        } finally {
            try {
                stmMensaxe.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }

    protected void enviarMensaxe(Usuario emisor, ArrayList<Usuario> receptores, String mensaxe) throws ExcepcionBD {
        PreparedStatement stmMensaxe = null;
        try {
            for (Usuario receptor : receptores) {
                stmMensaxe = super.getConexion().prepareStatement("INSERT INTO enviarMensaxe (emisor,receptor,dataEnvio,contido,lido) VALUES (?,?,NOW(),?,?);");
                stmMensaxe.setString(1, emisor.getLogin());
                stmMensaxe.setString(2, receptor.getLogin());
                stmMensaxe.setString(3, mensaxe);
                stmMensaxe.setBoolean(4, false);
                stmMensaxe.executeUpdate();
            }
            super.getConexion().commit();
        } catch (SQLException e) {
            throw new ExcepcionBD(super.getConexion(), e);
        } finally {
            try {
                stmMensaxe.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }

    protected void marcarMensaxeComoLido(Mensaxe m) throws ExcepcionBD {
        PreparedStatement stmMensaxe = null;

        try {
            stmMensaxe = super.getConexion().prepareStatement("UPDATE enviarMensaxe SET lido=TRUE WHERE emisor=? AND receptor=? AND dataEnvio=?;");
            stmMensaxe.setString(1, m.getEmisor().getLogin());
            stmMensaxe.setString(2, m.getReceptor().getLogin());
            stmMensaxe.setTimestamp(3, m.getDataEnvio());
            stmMensaxe.executeUpdate();
            super.getConexion().commit();
        } catch (SQLException e) {
            throw new ExcepcionBD(super.getConexion(), e);
        } finally {
            try {
                stmMensaxe.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }

    protected ArrayList<Mensaxe> listarMensaxesRecibidos(String loginReceptor) {
        ArrayList<Mensaxe> mensaxes = new ArrayList<>();
        PreparedStatement stmMensaxe = null;
        ResultSet resultMensaxes;
        try {
            stmMensaxe = super.getConexion().prepareStatement("SELECT * FROM enviarMensaxe WHERE receptor=? ORDER BY dataEnvio DESC;");
            stmMensaxe.setString(1, loginReceptor);
            resultMensaxes = stmMensaxe.executeQuery();
            while (resultMensaxes.next()) {
                mensaxes.add(new Mensaxe(
                        new Usuario(resultMensaxes.getString("emisor")),
                        new Usuario(resultMensaxes.getString("receptor")),
                        resultMensaxes.getTimestamp("dataEnvio"),
                        resultMensaxes.getString("contido"),
                        resultMensaxes.getBoolean("lido")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmMensaxe.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
        return mensaxes;
    }
}

package centrodeportivo.baseDatos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public final class DAOInstalacions extends AbstractDAO {

    public DAOInstalacions(Connection conexion, FachadaAplicacion fachadaAplicacion){
        super(conexion, fachadaAplicacion);
    }

    public void darAltaInstalacion(Instalacion instalacion){
        PreparedStatement stmInstalacions = null;
        Connection con;

        //Recuperamos a conexión coa base de datos.
        con = super.getConexion();

        //Preparamos a inserción:
        try {
            stmInstalacions = con.prepareStatement("INSERT INTO Instalacions (nome, numtelefono, direccion) " +
                    " VALUES (?, ?, ?)");
            //Establecemos os valores:
            stmInstalacions.setString(1, instalacion.getNome());
            stmInstalacions.setString(2, instalacion.getNumTelefono());
            stmInstalacions.setString(3, instalacion.getDireccion());

            //Realizamos a actualización:
            stmInstalacions.executeUpdate();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            try {
                stmInstalacions.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }

    public void borrarInstalacion(Instalacion instalacion){
        PreparedStatement stmInstalacions = null;
        Connection con;

        //Recuperamos a conexión:
        con = super.getConexion();

        //Preparamos o borrado:
        try{
            stmInstalacions = con.prepareStatement("DELETE FROM Instalacions " +
                    " WHERE codInstalacion = ?");
            stmInstalacions.setInt(1, instalacion.getCodInstalacion());

            //Realizamos a actualización:
            stmInstalacions.executeUpdate();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            //Pechamos o statement:
            try{
                stmInstalacions.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }

    public void modificarInstalacion(Instalacion instalacion){
        PreparedStatement stmInstalacions = null;
        Connection con;

        //Recuperamos a conexión:
        con = super.getConexion();

        //Preparamos a modificación:
        try{
            stmInstalacions = con.prepareStatement("UPDATE Instalacions " +
                    " SET nome = ?, " +
                    "     numtelefono = ?, " +
                    "     direccion = ? " +
                    " WHERE codInstalacion = ? ");

            //Asignamos os valores que corresponden:
            stmInstalacions.setString(1, instalacion.getNome());
            stmInstalacions.setString(2, instalacion.getNumTelefono());
            stmInstalacions.setString(3, instalacion.getDireccion());
            stmInstalacions.setInt(4, instalacion.getCodInstalacion());

            //Executamos a actualización:
            stmInstalacions.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                //Tentamos pechar o statement usado nesta actualización:
                stmInstalacions.close();
            } catch (SQLException e){
                System.out.println("Imposible pechar os cursores");
            }
        }
    }

    public ArrayList<Instalacion> buscarInstalacions(Instalacion instalacion){
        //Usaremos un ArrayList para almacenar un usuario:
        ArrayList<Instalacion> instalacions = new ArrayList<>();

        PreparedStatement stmInstalacions = null;
        Connection con;

        //Recuperamos a conexión:
        con = super.getConexion();

        //Preparamos a consulta:
        try{
            stmInstalacions = con.prepareStatement("");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            //Peche do statement:
            try {
                stmInstalacions.close();
            } catch (SQLException e){
                System.out.println("Imposible pechar os cursores");
            }
        }


        return instalacions;
    }
}

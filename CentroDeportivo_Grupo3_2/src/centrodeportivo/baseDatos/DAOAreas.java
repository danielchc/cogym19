package centrodeportivo.baseDatos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOAreas extends AbstractDAO {

    public DAOAreas(Connection conexion, FachadaAplicacion fachadaAplicacion){
        super(conexion, fachadaAplicacion);
    }

    public void darAltsaArea(Instalacion instalacion) throws ExcepcionBD {
        PreparedStatement stmInstalacions = null;
        ResultSet rsInstalacions;
        Connection con;
        //Recuperamos a conexión coa base de datos.
        con = super.getConexion();

        //Preparamos a inserción:
        try {
            stmInstalacions = con.prepareStatement("INSERT INTO Instalacion (nome, numtelefono, direccion) " +
                    " VALUES (?, ?, ?)");
            //Establecemos os valores:
            stmInstalacions.setString(1, instalacion.getNome());
            stmInstalacions.setString(2, instalacion.getNumTelefono());
            stmInstalacions.setString(3, instalacion.getDireccion());

            //Realizamos a actualización:
            stmInstalacions.executeUpdate();

            //Imos facer unha segunda consulta para recuperar o ID da instalación:
            //Fago a consulta polo nome porque tamén é único:
            stmInstalacions = con.prepareStatement("SELECT codInstalacion FROM instalacion " +
                    " WHERE nome = ? ");

            //Establecemos o valor que se necesita:
            stmInstalacions.setString(1, instalacion.getNome());

            //Facemos a consulta:
            rsInstalacions = stmInstalacions.executeQuery();

            //Feita a consulta, recuperamos o valor:
            if(rsInstalacions.next()) { //Só debería devolverse un ID.
                //Así metemos o ID na instalación, e podemos amosalo para rematar a operación.
                instalacion.setCodInstalacion(rsInstalacions.getInt(1));
            }

            //Facemos commit:
            con.commit();
        } catch (SQLException e){
            //Lanzamos neste caso unha excepción cara a aplicación:
            throw new ExcepcionBD(con, e);
        } finally {
            //En calquera caso, téntase pechar os cursores.
            try {
                stmInstalacions.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }


}

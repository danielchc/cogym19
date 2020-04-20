

package centrodeportivo.baseDatos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.area.TipoMaterial;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class DAOTipoMaterial extends AbstractDAO {

    // Constructor

    public DAOTipoMaterial(Connection conexion, FachadaAplicacion fachadaAplicacion) {
        super(conexion, fachadaAplicacion);
    }


    // Outros metodos

    /**
     * EngadirTipoMaterial -> engadese unha nova tupla cun novo tipo de material na base de datos
     *
     * @param tipoMaterial -> datos do material que creará
     * @throws ExcepcionBD -> excepción procedente do método dao para indicar problemas na inserción
     */
    public void engadirTipoMaterial(TipoMaterial tipoMaterial) throws ExcepcionBD {
        PreparedStatement stmTipoMaterial = null;
        ResultSet rsTipoMaterial;
        Connection con;

        // Recuperamos a conexión coa base de datos
        con = super.getConexion();

        // Preparamos a inserción:
        try {
            stmTipoMaterial = con.prepareStatement("INSERT INTO tipoMaterial (nome) VALUES (?)");
            // Establecemos os valores:
            stmTipoMaterial.setString(1, tipoMaterial.getNome());
            // Realizamos a acutalización:
            stmTipoMaterial.executeUpdate();

            // Realizamos unha segunda consulta co fin de recuperar o identificador do tipoMaterial:
            stmTipoMaterial = con.prepareStatement("SELECT codTipoMaterial FROM tipoMaterial WHERE nome = ?");
            // Establecemos o valor:
            stmTipoMaterial.setString(1, tipoMaterial.getNome());
            // Executamos a consulta:
            rsTipoMaterial = stmTipoMaterial.executeQuery();

            // Unha vez realizada a consulta, recuperamos o valor:
            if (rsTipoMaterial.next()) {
                // Asignamoslle o identificador o tipoMaterial
                tipoMaterial.setCodTipoMaterial(rsTipoMaterial.getInt(1));
            }

            // Facemos o commit:
            con.commit();
        } catch (SQLException e) {
            // Lanzamos a excepcion cara á aplicación
            throw new ExcepcionBD(con, e);
        } finally {
            // En calquera dos casos, téntase pechar os cursores
            try {
                assert stmTipoMaterial != null;
                stmTipoMaterial.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }

    /**
     * BorrarTipoMaterial -> eliminase un tipo de material da base de datos
     *
     * @param tipoMaterial -> datos do tipo de material que se eliminará
     * @throws ExcepcionBD -> excepción procedente do método dao para indicar problemas no borrado
     */
    public void borrarTipoMaterial(TipoMaterial tipoMaterial) throws ExcepcionBD {
        PreparedStatement stmTipoMaterial = null;
        Connection con;


        // Recuperamos a conexión coa base de datos
        con = super.getConexion();

        // Preparamos o borrado:
        try {
            stmTipoMaterial = con.prepareStatement("DELETE FROM tipoMaterial WHERE codTipoMaterial = ?");
            // Establecemos os valores:
            stmTipoMaterial.setInt(1, tipoMaterial.getCodTipoMaterial());
            // Realizamos a acutalización:
            stmTipoMaterial.executeUpdate();

            // Facemos o commit:
            con.commit();
        } catch (SQLException e) {
            // Lanzamos a excepcion cara á aplicación
            throw new ExcepcionBD(con, e);
        } finally {
            // En calquera dos casos, téntase pechar os cursores
            try {
                assert stmTipoMaterial != null;
                stmTipoMaterial.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }

}
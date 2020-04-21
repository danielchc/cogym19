

package centrodeportivo.baseDatos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.area.Material;
import centrodeportivo.aplicacion.obxectos.area.TipoMaterial;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
    public void darAltaTipoMaterial(TipoMaterial tipoMaterial) throws ExcepcionBD {
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

    /**
     * IsTipoMaterial -> comproba se certo tipo de material existe na base de datos
     *
     * @param tipoMaterial -> datos do tipo de material que se quer validar
     * @return -> devolve true se o tipo de material se encontra na base de datos
     */
    public boolean isTipoMaterial(TipoMaterial tipoMaterial) {

        boolean resultado = false;
        PreparedStatement stmTipoMaterial = null;
        ResultSet rsTipoMaterial = null;
        Connection con;

        // Recuperamos a conexión coa base de datos
        con = super.getConexion();

        // Preparamos a consulta
        try {
            stmTipoMaterial = con.prepareStatement("SELECT * " +
                    "FROM tipoMaterial " +
                    "WHERE lower(nome) =  lower (?) AND codTipoMaterial != ?");

            // Asignamos os valores que corresponden:
            stmTipoMaterial.setString(1, tipoMaterial.getNome());
            stmTipoMaterial.setInt(2, tipoMaterial.getCodTipoMaterial());

            // Executamos a consulta
            rsTipoMaterial = stmTipoMaterial.executeQuery();

            // Comprobamos o resultSet, se obtemos un resultado, o tipoMaterial existe
            if (rsTipoMaterial.next()) {
                resultado = true;
            }

            // Facemos o commit
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                assert stmTipoMaterial != null;
                stmTipoMaterial.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
        return resultado;
    }

    /**
     * ListarTiposMateriais -> obten todos os tipos de materiais almacenados na base de datos
     *
     * @return -> devolve un ArrayList cos tipos de materiais da base de datos
     */
    public ArrayList<TipoMaterial> listarTiposMateriais() {
        ArrayList<TipoMaterial> tiposMateriais = new ArrayList<>();
        PreparedStatement stmTipoMaterial = null;
        ResultSet rsTipoMaterial;
        Connection con;

        // Recuperamos a conexión coa base de datos
        con = super.getConexion();

        // Preparamos a consulta
        try {
            stmTipoMaterial = con.prepareStatement("SELECT * FROM tipoMaterial");
            // Executamos a consulta
            rsTipoMaterial = stmTipoMaterial.executeQuery();

            // Procesamos o ResultSet
            while (rsTipoMaterial.next()) {
                // Engadimos o ArrayList os tipos de materiais
                tiposMateriais.add(new TipoMaterial(rsTipoMaterial.getInt(1), rsTipoMaterial.getString(2)));
            }
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            // Intentamos pechar o statement:
            try {
                assert stmTipoMaterial != null;
                stmTipoMaterial.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
        return tiposMateriais;
    }

}
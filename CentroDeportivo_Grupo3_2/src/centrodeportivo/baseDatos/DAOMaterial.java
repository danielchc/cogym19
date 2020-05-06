package centrodeportivo.baseDatos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.actividades.Curso;
import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.aplicacion.obxectos.area.Material;
import centrodeportivo.aplicacion.obxectos.area.TipoMaterial;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public final class DAOMaterial extends AbstractDAO {

    // Constructor
    public DAOMaterial(Connection conexion, FachadaAplicacion fachadaAplicacion) {
        super(conexion, fachadaAplicacion);
    }


    // Outros métodos

    /**
     * DarAltaMaterial -> crea unha nova tupla insertando un material na base de datos
     *
     * @param material -> datos do material que se engadirá a base de datos
     * @throws ExcepcionBD -> excepción procedente do método dao para indicar problemas na inserción
     */
    public void darAltaMaterial(Material material) throws ExcepcionBD {

        PreparedStatement stmMaterial = null;
        Connection con;

        // Recuperamos a conexión coa base de datos
        con = super.getConexion();

        // Preparamos a inserción:
        try {
            stmMaterial = con.prepareStatement("INSERT INTO material (tipoMaterial, area, instalacion, estado) " +
                    "VALUES (?, ?, ?, ?)");

            // Establecemos os valores:
            stmMaterial.setInt(1, material.getTipoMaterial().getCodTipoMaterial());
            stmMaterial.setInt(2, material.getArea().getCodArea());
            stmMaterial.setInt(3, material.getInstalacion().getCodInstalacion());
            stmMaterial.setString(4, material.getEstado());

            // Realizamos a inserción:
            stmMaterial.executeUpdate();

            // Facemos o commit:
            con.commit();
        } catch (SQLException e) {
            // Lanzamos a excepcion cara á aplicación
            throw new ExcepcionBD(con, e);
        } finally {
            // En calquera dos casos, tentamos pechar os cursores
            try {
                assert stmMaterial != null;
                stmMaterial.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }


    /**
     * BorrarMaterial -> elimina a tupla dun material na base de datos
     *
     * @param material -> datos do material que se eliminará
     * @throws ExcepcionBD-> excepción procedente do método dao para indicar problemas no borrado
     */
    public void borrarMaterial(Material material) throws ExcepcionBD {

        PreparedStatement stmMaterial = null;
        Connection con;

        // Recuperamos a conexión coa base de datos
        con = super.getConexion();

        // Preparamos o borrado:
        try {
            stmMaterial = con.prepareStatement("DELETE FROM material " +
                    " WHERE codMaterial = ?");
            stmMaterial.setInt(1, material.getCodMaterial());

            // Realizamos a actualización:
            stmMaterial.executeUpdate();

            // Facemos o commit:
            con.commit();
        } catch (SQLException e) {
            // Lanzamos a excepción cara á aplicación
            throw new ExcepcionBD(con, e);
        } finally {
            // En calquera dos casos, tentamos pechar os cursores
            try {
                assert stmMaterial != null;
                stmMaterial.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }


    /**
     * ModificarMaterial -> modifica os datos un material na base de datos
     *
     * @param material -> datos do material que se modificará
     * @throws ExcepcionBD -> excepción procedente do método dao para indicar problemas na modificación
     */
    public void modificarMaterial(Material material) throws ExcepcionBD {

        PreparedStatement stmMaterial = null;
        Connection con;

        // Recuperamos a conexión:
        con = super.getConexion();

        // Preparamos a modificación:
        try {
            stmMaterial = con.prepareStatement("UPDATE material " +
                    " SET area = ?, " +
                    "     instalacion = ?, " +
                    "     estado = ?, " +
                    "     dataCompra = ?, " +
                    "     prezoCompra = ? " +
                    " WHERE codMaterial = ? AND tipoMaterial = ? ");

            // Asignamos os valores que corresponden:
            stmMaterial.setInt(1, material.getArea().getCodArea());
            stmMaterial.setInt(2, material.getInstalacion().getCodInstalacion());
            stmMaterial.setString(3, material.getEstado());
            stmMaterial.setDate(4, material.getDataCompra());
            stmMaterial.setFloat(5, material.getPrezoCompra());
            stmMaterial.setInt(6, material.getCodMaterial());
            stmMaterial.setInt(7, material.getTipoMaterial().getCodTipoMaterial());


            // Executamos a actualización
            stmMaterial.executeUpdate();

            // Facemos o commit
            con.commit();
        } catch (SQLException e) {
            // Lanzamos a excepción cara á aplicación
            throw new ExcepcionBD(con, e);
        } finally {
            try {
                // En calquera dos casos, tentamos pechar os cursores
                assert stmMaterial != null;
                stmMaterial.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
    }


    /**
     * IsMaterial -> comproba se certo material existe na base de datos
     *
     * @param material -> datos do material que se quer validar
     * @return -> devolve true se o material se encontra na base de datos
     */
    public boolean isMaterial(Material material) {

        boolean resultado = false;
        PreparedStatement stmMaterial = null;
        ResultSet rsMaterial;
        Connection con;

        // Recuperamos a conexión coa base de datos
        con = super.getConexion();

        // Preparamos a consulta
        try {
            stmMaterial = con.prepareStatement("SELECT * " +
                    "FROM material " +
                    "WHERE codmaterial = ? AND tipoMaterial = ? ");

            // Asignamos os valores que corresponden:
            stmMaterial.setInt(1, material.getCodMaterial());
            stmMaterial.setInt(2, material.getTipoMaterial().getCodTipoMaterial());

            //Executamos a consulta
            rsMaterial = stmMaterial.executeQuery();

            // Comprobamos o resultSet, se obtemos un resultado, o material existe
            if (rsMaterial.next()) {
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
                assert stmMaterial != null;
                stmMaterial.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
        return resultado;
    }


    /**
     * ListarMateriais -> obten todos os materiais almacenados na base de datos
     *
     * @return -> devolve un ArrayList cos materiais da base de datos
     */
    public ArrayList<Material> listarMateriais(Material material) {
        ArrayList<Material> materiais = new ArrayList<>();
        PreparedStatement stmMaterial = null;
        ResultSet rsMaterial;
        Connection con;
        TipoMaterial tipoMaterial;
        Area area;
        Instalacion instalacion;

        // Recuperamos a conexión coa base de datos
        con = super.getConexion();

        // Preparamos a consulta
        try {

            stmMaterial = con.prepareStatement("SELECT m.codmaterial, m.tipomaterial, m.area, m.instalacion, m.estado, " +
                    "tm.nome as nometipo, " +
                    "a.nome as nomearea, a.descricion, " +
                    "i.nome as nomeinstalacion, i.numtelefono, i.direccion " +
                    "FROM material as m " +
                    "INNER JOIN tipomaterial as tm " +
                    "ON m.tipomaterial = tm.codtipomaterial " +
                    "INNER JOIN area as a " +
                    "ON m.area = a.codarea " +
                    "INNER JOIN instalacion as i " +
                    "ON a.instalacion = i.codinstalacion AND m.instalacion = i.codinstalacion " +
                    "ORDER BY m.tipomaterial, m.codmaterial");

            // Executamos a consulta
            rsMaterial = stmMaterial.executeQuery();
            // Procesamos o ResultSet
            while (rsMaterial.next()) {
                tipoMaterial = new TipoMaterial(rsMaterial.getInt("tipomaterial"), rsMaterial.getString("nometipo"));
                instalacion = new Instalacion(rsMaterial.getInt("instalacion"), rsMaterial.getString("nomeinstalacion"),
                        rsMaterial.getString("numtelefono"), rsMaterial.getString("direccion"));
                area = new Area(rsMaterial.getInt("area"), instalacion, rsMaterial.getString("nomearea"),
                        rsMaterial.getString("descricion"));
                // Engadimos o material o ArrayList
                materiais.add(new Material(rsMaterial.getInt("codmaterial"), tipoMaterial, area,
                        rsMaterial.getString("estado")));
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
                assert stmMaterial != null;
                stmMaterial.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
        return materiais;
    }

}

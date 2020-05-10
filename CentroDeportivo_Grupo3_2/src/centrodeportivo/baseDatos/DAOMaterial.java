package centrodeportivo.baseDatos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.aplicacion.obxectos.area.Material;
import centrodeportivo.aplicacion.obxectos.area.TipoMaterial;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Víctor Barreiro
 * Clase que conterá todos os métodos DAO relacionados na súa maioría con xestións dos materiais.
 */
public final class DAOMaterial extends AbstractDAO {

    /**
     * Constructor do DAO de materiais
     *
     * @param conexion          Referencia á conexión coa base de datos.
     * @param fachadaAplicacion Referencia á fachada da parte da aplicación.
     */
    public DAOMaterial(Connection conexion, FachadaAplicacion fachadaAplicacion) {
        // Asignaremos estes atributos no constructor da clase pai ao que chamamos:
        super(conexion, fachadaAplicacion);
    }


    /**
     * Método que crea unha nova tupla insertando un material na base de datos.
     *
     * @param material Datos do material que se engadirá a base de datos.
     * @throws ExcepcionBD Excepción procedente da base de datos para indicar problemas na inserción.
     */
    public void darAltaMaterial(Material material) throws ExcepcionBD {

        PreparedStatement stmMaterial = null;
        Connection con;

        // Recuperamos a conexión coa base de datos:
        con = super.getConexion();

        // Preparamos a inserción:
        try {
            stmMaterial = con.prepareStatement("INSERT INTO material (tipoMaterial, area, instalacion, estado, datacompra, prezocompra) " +
                    "VALUES (?, ?, ?, ?, ?, ?)");

            // Establecemos os valores:
            stmMaterial.setInt(1, material.getTipoMaterial().getCodTipoMaterial());
            stmMaterial.setInt(2, material.getArea().getCodArea());
            stmMaterial.setInt(3, material.getInstalacion().getCodInstalacion());
            stmMaterial.setString(4, material.getEstado());
            stmMaterial.setDate(5, material.getDataCompra());
            stmMaterial.setFloat(6, material.getPrezoCompra());

            // Realizamos a inserción:
            stmMaterial.executeUpdate();

            // Facemos o commit:
            con.commit();
        } catch (SQLException e) {
            // Lanzamos a excepcion cara á aplicación:
            throw new ExcepcionBD(con, e);
        } finally {
            // En calquera dos casos, tentamos pechar os cursores:
            try {
                stmMaterial.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }

    /**
     * Método que elimina a tupla dun material na base de datos.
     *
     * @param material Datos do material que se eliminará.
     * @throws ExcepcionBD Excepción procedente da base de datos para indicar problemas no borrado.
     */
    public void borrarMaterial(Material material) throws ExcepcionBD {
        PreparedStatement stmMaterial = null;
        Connection con;

        // Recuperamos a conexión coa base de datos:
        con = super.getConexion();

        // Preparamos o borrado:
        try {
            stmMaterial = con.prepareStatement("DELETE FROM material " +
                    " WHERE codMaterial = ? and tipomaterial =  ? ");
            stmMaterial.setInt(1, material.getCodMaterial());
            stmMaterial.setInt(2, material.getTipoMaterial().getCodTipoMaterial());

            // Realizamos a actualización:
            stmMaterial.executeUpdate();

            // Facemos o commit:
            con.commit();
        } catch (SQLException e) {
            // Lanzamos a excepción cara á aplicación:
            throw new ExcepcionBD(con, e);
        } finally {
            // En calquera dos casos, tentamos pechar os cursores:
            try {
                stmMaterial.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }

    /**
     * Método que modifica os datos un material na base de datos
     *
     * @param material Datos do material que se modificará.
     * @throws ExcepcionBD Excepción procedente da base de datos para indicar problemas na modificación.
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

            // Executamos a actualización:
            stmMaterial.executeUpdate();

            // Facemos o commit:
            con.commit();
        } catch (SQLException e) {
            // Lanzamos a excepción cara á aplicación:
            throw new ExcepcionBD(con, e);
        } finally {
            try {
                // En calquera dos casos, tentamos pechar os cursores:
                stmMaterial.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
    }

    /**
     * Método que comproba se certo material existe na base de datos.
     *
     * @param material Datos do material que se quer validar.
     * @return Devolve true se o material se encontra na base de datos e false en caso contrario.
     */
    public boolean isMaterial(Material material) {
        PreparedStatement stmMaterial = null;
        ResultSet rsMaterial;
        Connection con;
        // Devolvemos un booleano como resultado:
        boolean resultado = false;

        // Recuperamos a conexión coa base de datos:
        con = super.getConexion();

        // Preparamos a consulta:
        try {
            stmMaterial = con.prepareStatement("SELECT * " +
                    "FROM material " +
                    "WHERE codmaterial = ? AND tipoMaterial = ? ");

            // Asignamos os valores que corresponden:
            stmMaterial.setInt(1, material.getCodMaterial());
            stmMaterial.setInt(2, material.getTipoMaterial().getCodTipoMaterial());

            //Executamos a consulta:
            rsMaterial = stmMaterial.executeQuery();

            // Comprobamos o resultSet, se obtemos un resultado, o material existe:
            if (rsMaterial.next()) {
                resultado = true;
            }

            // Facemos o commit:
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
                stmMaterial.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
        // Devolvemos o resultado:
        return resultado;
    }

    /**
     * Método que comproba se certo material existe na base de datos e devolve os datos actualizados.
     *
     * @param material Datos do material que se quere consultar.
     * @return Devolve os datos do material actualizado.
     */
    public Material consultarMaterial(Material material) {
        PreparedStatement stmMaterial = null;
        ResultSet rsMaterial;
        Material resultado = null;
        TipoMaterial tipoMaterial;
        Instalacion instalacion;
        Area area;
        Connection con;

        // Recuperamos a conexión coa base de datos:
        con = super.getConexion();

        // Preparamos a consulta:
        try {
            stmMaterial = con.prepareStatement("SELECT m.*, " +
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
                    "WHERE m.codmaterial = ? and m.tipomaterial = ? " +
                    "ORDER BY m.tipomaterial, m.codmaterial ");

            // Asignamos os valores que corresponden:
            stmMaterial.setInt(1, material.getCodMaterial());
            stmMaterial.setInt(2, material.getTipoMaterial().getCodTipoMaterial());

            // Executamos a consulta:
            rsMaterial = stmMaterial.executeQuery();
            // Procesamos o ResultSet:
            if (rsMaterial.next()) {
                // Creamos o tipo de material:
                tipoMaterial = new TipoMaterial(rsMaterial.getInt("tipomaterial"), rsMaterial.getString("nometipo"));

                // Creamos a instalacion:
                instalacion = new Instalacion(rsMaterial.getInt("instalacion"), rsMaterial.getString("nomeinstalacion"),
                        rsMaterial.getString("numtelefono"), rsMaterial.getString("direccion"));

                // Creamos a area:
                area = new Area(rsMaterial.getInt("area"), instalacion, rsMaterial.getString("nomearea"),
                        rsMaterial.getString("descricion"));

                // Creamos o material:
                resultado = new Material(rsMaterial.getInt("codmaterial"), tipoMaterial, area,
                        rsMaterial.getString("estado"));

                // Se a data da compra non é nula, engadimoslla:
                if (rsMaterial.getObject("datacompra") != null) {
                    resultado.setDataCompra((Date) rsMaterial.getObject("datacompra"));
                }

                // Se o prezo da compra non é nulo, engadimosllo:
                if (rsMaterial.getObject("prezocompra") != null) {
                    Float prezocompra = ((BigDecimal) rsMaterial.getObject("prezocompra")).floatValue();
                    resultado.setPrezoCompra(prezocompra);
                }
            }
            con.commit();

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
                stmMaterial.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
        // Devolvemos o resultado:
        return resultado;
    }

    /**
     * Método que obten todos os materiais almacenados na base de datos e permite filtrar en función
     * da área e instalación na que se atope así como, polo tipo de material.
     *
     * @param material Datos do material co que poderemos filtrar en función da instalación e área na que
     *                 se atope así como, polo seu tipo.
     * @return Devolve un ArrayList cos materiais da base de datos que cumpran ditas condicións.
     */
    public ArrayList<Material> listarMateriais(Material material) {
        ArrayList<Material> materiais = new ArrayList<>();
        PreparedStatement stmMaterial = null;
        ResultSet rsMaterial;
        Connection con;
        TipoMaterial tipoMaterial;
        Area area;
        Instalacion instalacion;
        Material resultado;

        // Recuperamos a conexión coa base de datos:
        con = super.getConexion();

        // Preparamos a consulta:
        try {
            String consultaMaterial = "SELECT m.*, " +
                    "tm.nome as nometipo, " +
                    "a.nome as nomearea, a.descricion, " +
                    "i.nome as nomeinstalacion, i.numtelefono, i.direccion " +
                    "FROM material as m " +
                    "INNER JOIN tipomaterial as tm " +
                    "ON m.tipomaterial = tm.codtipomaterial " +
                    "INNER JOIN area as a " +
                    "ON m.area = a.codarea " +
                    "INNER JOIN instalacion as i " +
                    "ON a.instalacion = i.codinstalacion AND m.instalacion = i.codinstalacion ";

            if (material != null) {
                consultaMaterial += "WHERE CAST(m.tipomaterial AS TEXT) like ? AND CAST(m.area AS TEXT) LIKE ? AND CAST(m.instalacion AS TEXT) LIKE ? ";
            }

            consultaMaterial += "ORDER BY m.tipomaterial, m.codmaterial ";

            stmMaterial = con.prepareStatement(consultaMaterial);

            // Completamos onde corresponda:
            if (material != null) {
                if (material.getTipoMaterial() != null) {
                    stmMaterial.setString(1, "" + material.getTipoMaterial().getCodTipoMaterial());
                } else {
                    stmMaterial.setString(1, "%%");
                }
                if (material.getArea() != null) {
                    stmMaterial.setString(2, "" + material.getArea().getCodArea());

                } else {
                    stmMaterial.setString(2, "%%");
                }
                if (material.getInstalacion() != null) {
                    stmMaterial.setString(3, "" + material.getInstalacion().getCodInstalacion());
                } else {
                    stmMaterial.setString(3, "%%");
                }
            }

            // Executamos a consulta:
            rsMaterial = stmMaterial.executeQuery();

            // Procesamos o ResultSet:
            while (rsMaterial.next()) {
                // Creamos o tipo de material que corresponda:
                tipoMaterial = new TipoMaterial(rsMaterial.getInt("tipomaterial"), rsMaterial.getString("nometipo"));

                // Creamos a instalación onde se atope:
                instalacion = new Instalacion(rsMaterial.getInt("instalacion"), rsMaterial.getString("nomeinstalacion"),
                        rsMaterial.getString("numtelefono"), rsMaterial.getString("direccion"));

                // Creamos a área da instalación onde se atope o material:
                area = new Area(rsMaterial.getInt("area"), instalacion, rsMaterial.getString("nomearea"),
                        rsMaterial.getString("descricion"));

                // Creamos e engadimos o material o ArrayList que se retornará como resultado:
                resultado = new Material(rsMaterial.getInt("codmaterial"), tipoMaterial, area,
                        rsMaterial.getString("estado"));

                // Se a data da compra non é nula, engadímoslla:
                if (rsMaterial.getObject("datacompra") != null) {
                    resultado.setDataCompra((Date) rsMaterial.getObject("datacompra"));
                }

                // Se o prezo da compra non é nulo, engadímosllo:
                if (rsMaterial.getObject("prezocompra") != null) {
                    Float prezocompra = ((BigDecimal) rsMaterial.getObject("prezocompra")).floatValue();
                    resultado.setPrezoCompra(prezocompra);
                }
                // Engadimos o material o ArrayList final:
                materiais.add(resultado);
            }
            // Facemos commit:
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
                stmMaterial.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
        // Devolvemos, finalmente, un ArrayList de materiais:
        return materiais;
    }

}

package centrodeportivo.baseDatos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.area.TipoMaterial;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Víctor Barreiro
 * Clase que conterá todos os métodos DAO relacionados na súa maioría con xestións do tipo dos materiais
 */
public final class DAOTipoMaterial extends AbstractDAO {

    /**
     * Constructor do DAO dos tipos de materiais
     *
     * @param conexion          Referencia á conexión coa base de datos.
     * @param fachadaAplicacion Referencia á fachada da parte da aplicación.
     */
    public DAOTipoMaterial(Connection conexion, FachadaAplicacion fachadaAplicacion) {
        // Asignaremos estes atributos no constructor da clase pai ao que chamamos:
        super(conexion, fachadaAplicacion);
    }


    /**
     * Método que permite engadir unh nova tupla na base de datos cun novo tipo de material.
     *
     * @param tipoMaterial Datos do tipo de material que se creará (en concreto, o nome).
     * @throws ExcepcionBD Excepción procedente do método dao para indicar problemas na inserción.
     */
    public void darAltaTipoMaterial(TipoMaterial tipoMaterial) throws ExcepcionBD {
        PreparedStatement stmTipoMaterial = null;
        ResultSet rsTipoMaterial;
        Connection con;

        // Recuperamos a conexión coa base de datos:
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
                // Asignámoslle o identificador o tipo de material:
                tipoMaterial.setCodTipoMaterial(rsTipoMaterial.getInt(1));
            }

            // Facemos o commit:
            con.commit();
        } catch (SQLException e) {
            // Lanzamos a excepcion cara á aplicación:
            throw new ExcepcionBD(con, e);
        } finally {
            // En calquera dos casos, téntase pechar os cursores:
            try {
                stmTipoMaterial.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }

    /**
     * Método que permite eliminar un tipo de material da base de datos.
     *
     * @param tipoMaterial Datos do tipo de material que se eliminará.
     * @throws ExcepcionBD Excepción procedente do método dao para indicar problemas no borrado.
     */
    public void borrarTipoMaterial(TipoMaterial tipoMaterial) throws ExcepcionBD {
        PreparedStatement stmTipoMaterial = null;
        Connection con;

        // Recuperamos a conexión coa base de datos:
        con = super.getConexion();

        // Preparamos o borrado:
        try {
            stmTipoMaterial = con.prepareStatement("DELETE FROM tipoMaterial WHERE codTipoMaterial = ?");

            // Establecemos os valores:
            stmTipoMaterial.setInt(1, tipoMaterial.getCodTipoMaterial());

            // Realizamos a acutalización da base de datos:
            stmTipoMaterial.executeUpdate();

            // Facemos o commit:
            con.commit();
        } catch (SQLException e) {
            // Lanzamos a excepcion cara á aplicación:
            throw new ExcepcionBD(con, e);
        } finally {
            // En calquera dos casos, téntase pechar os cursores:
            try {
                stmTipoMaterial.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }

     // Non se contempla a modificación do tipo de material por motivos de deseño

    /**
     * Método que comproba se certo tipo de material existe na base de datos.
     *
     * @param tipoMaterial Datos do tipo de material que se quer validar.
     * @return Devolve true se o tipo de material se encontra na base de datos.
     */
    public boolean isTipoMaterial(TipoMaterial tipoMaterial) {
        PreparedStatement stmTipoMaterial = null;
        ResultSet rsTipoMaterial = null;
        Connection con;
        // Devolvemos un boolean como resultado:
        boolean resultado = false;

        // Recuperamos a conexión coa base de datos:
        con = super.getConexion();

        // Preparamos a consulta:
        try {
            stmTipoMaterial = con.prepareStatement("SELECT * " +
                    "FROM tipoMaterial " +
                    "WHERE lower(nome) =  lower (?) AND codTipoMaterial != ?");

            // Asignamos os valores que corresponden:
            stmTipoMaterial.setString(1, tipoMaterial.getNome());
            stmTipoMaterial.setInt(2, tipoMaterial.getCodTipoMaterial());

            // Executamos a consulta:
            rsTipoMaterial = stmTipoMaterial.executeQuery();

            // Comprobamos o resultSet, se obtemos un resultado, o tipoMaterial existe.
            if (rsTipoMaterial.next()) {
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
                stmTipoMaterial.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
        return resultado;
    }

    /**
     * Método que permite buscar tipos de materiais na base de datos con campos de busqueda, ou sen eles.
     *
     * @param tipoMaterial Se non é null, a consulta realizase en base o nome do tipo de material.
     * @return Se o parametro non é null, será devolto un array con todos os tipos de materiais que coincidan,
     * noutro caso, listanse todos os tipos de materiais.
     */
    public ArrayList<TipoMaterial> buscarTipoMaterial(TipoMaterial tipoMaterial) {
        ArrayList<TipoMaterial> tiposMateriais = new ArrayList<>();
        PreparedStatement stmTipoMaterial = null;
        ResultSet rsTipoMaterial;
        Connection con;

        // Recuperamos a conexión coa base de datos
        con = super.getConexion();

        // Preparamos a consulta:
        try {
            String consultaTipoMaterial = "SELECT * FROM tipoMaterial ";

            // Comprobaremos se estamos a pasar un tipo nulo e xestionarémolo en función do caso:
            if (tipoMaterial != null) {
                consultaTipoMaterial += " WHERE lower(nome) like lower(?) ";
            }

            // Ordenaremos o resultado en función do código:
            consultaTipoMaterial += " ORDER BY codtipomaterial ";

            stmTipoMaterial = con.prepareStatement(consultaTipoMaterial);

            // Completamos a consulta no caso de que o tipo de material non sexa nulo:
            if (tipoMaterial != null) {
                // Establecese o nome do tipo como parámetro:
                stmTipoMaterial.setString(1, "%" + tipoMaterial.getNome() + "%");
            }

            // Executamos a consulta:
            rsTipoMaterial = stmTipoMaterial.executeQuery();

            // Procesamos os resultados obtidos da consulta:
            while (rsTipoMaterial.next()) {
                // Imos engadindo cada tipo de material o ArrayList que devolveremos:
                tiposMateriais.add(new TipoMaterial(rsTipoMaterial.getInt(1), rsTipoMaterial.getString(2)));
            }

            // Facemos un commit para rematar:
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            // Tentamos pechar o statement:
            try {
                stmTipoMaterial.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
        return tiposMateriais;
    }

    /**
     * Método que permite comprobar existen materiais vínculados o tipo.
     *
     * @param tipoMaterial O tipo de material do cal queremos comprobar se existen materiais vinculados
     * @return Retorna true se o tipo ten materiais vinculados, False en caso contrario.
     */
    public boolean tenMateriais(TipoMaterial tipoMaterial) {
        PreparedStatement stmMaterial = null;
        ResultSet rsMaterial = null;
        Connection con;
        // Devolvemos un booleano como resultado:
        boolean resultado = false;

        // Recuperamos a conexión:
        con = super.getConexion();

        // Preparamos a consulta: hai que comprobar se hai materiais asociados o tipo:
        try {
            stmMaterial = con.prepareStatement("SELECT * FROM material WHERE tipomaterial = ? ");
            // Completamos a consulta:
            stmMaterial.setInt(1, tipoMaterial.getCodTipoMaterial());
            // Realizamos a consulta:
            rsMaterial = stmMaterial.executeQuery();
            // Comprobamos se hai resultado: se os hai, existen materiais do tipo.
            if (rsMaterial.next()) {
                resultado = true;
            }
            // Para rematar, facemos commit:
            con.commit();
        } catch (SQLException e) {
            // En caso de capturar unha excepción, imprimimos o stack trace:
            e.printStackTrace();
            // Tentamos facer rollback:
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            // Para rematar, pechamos o statement:
            try {
                stmMaterial.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
        // Devolvemos o resultado:
        return resultado;
    }
}

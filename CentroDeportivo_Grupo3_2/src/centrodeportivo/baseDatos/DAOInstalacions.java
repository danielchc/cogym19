package centrodeportivo.baseDatos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;

import java.sql.*;
import java.util.ArrayList;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Víctor Barreiro
 * DAO relacionado con aqueles accesos á base de datos que involucren ás instalacións.
 */
public final class DAOInstalacions extends AbstractDAO {

    /**
     * Constructor do DAO de instalacións
     *
     * @param conexion          Referencia á conexión coa base de datos.
     * @param fachadaAplicacion Referencia á fachada da parte da aplicación.
     */
    public DAOInstalacions(Connection conexion, FachadaAplicacion fachadaAplicacion) {
        // Chamamos ao constructor da clase pai:
        super(conexion, fachadaAplicacion);
    }


    /**
     * Método que nos permitirá dar de alta unha nova instalación.
     *
     * @param instalacion A instalación a dar de alta.
     * @throws ExcepcionBD Excepción asociada a problemas ao tentar facer a actualización sobre a base de datos.
     */
    public void darAltaInstalacion(Instalacion instalacion) throws ExcepcionBD {
        PreparedStatement stmInstalacions = null;
        ResultSet rsInstalacions;
        Connection con;
        // Recuperamos a conexión coa base de datos.
        con = super.getConexion();

        // Preparamos a inserción:
        try {
            // Colocaremos nome, número de teléfono e dirección:
            stmInstalacions = con.prepareStatement("INSERT INTO Instalacion (nome, numtelefono, direccion) " +
                    " VALUES (?, ?, ?)");
            // Establecemos os valores para a inserción:
            stmInstalacions.setString(1, instalacion.getNome());
            stmInstalacions.setString(2, instalacion.getNumTelefono());
            stmInstalacions.setString(3, instalacion.getDireccion());

            // Realizamos a actualización sobre a base de datos:
            stmInstalacions.executeUpdate();

            // Imos facer unha segunda consulta para recuperar o ID da instalación:
            // Fago a consulta polo nome porque tamén é único:
            stmInstalacions = con.prepareStatement("SELECT codInstalacion FROM instalacion " +
                    " WHERE nome = ? ");

            // Establecemos o valor que se necesita:
            stmInstalacions.setString(1, instalacion.getNome());

            // Facemos a consulta:
            rsInstalacions = stmInstalacions.executeQuery();

            // Feita a consulta, recuperamos o valor:
            if (rsInstalacions.next()) {  // Só debería devolverse un ID.
                // Así metemos o ID na instalación, e poderemos amosalo dende a interface para rematar a operación.
                instalacion.setCodInstalacion(rsInstalacions.getInt(1));
            }

            // Facemos commit:
            con.commit();
        } catch (SQLException e) {
            // Lanzamos neste caso unha excepción cara a aplicación:
            throw new ExcepcionBD(con, e);
        } finally {
            // En calquera caso, téntanse pechar os cursores.
            try {
                stmInstalacions.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }

    /**
     * Método que tenta eliminar os datos da instalación pasada como argumento da base de datos.
     *
     * @param instalacion A instalación cuxos datos se queren eliminar.
     * @throws ExcepcionBD Excepción asociada a problemas que poden xurdir ao actualizar a base de datos.
     */
    public void borrarInstalacion(Instalacion instalacion) throws ExcepcionBD {
        PreparedStatement stmInstalacions = null;
        Connection con;

        // Recuperamos a conexión:
        con = super.getConexion();

        // Preparamos o borrado:
        try {
            // Usamos como referencia o código da instalación:
            stmInstalacions = con.prepareStatement("DELETE FROM Instalacion " +
                    " WHERE codInstalacion = ?");
            // Completamos a sentencia anterior:
            stmInstalacions.setInt(1, instalacion.getCodInstalacion());

            // Realizamos a actualización:
            stmInstalacions.executeUpdate();
            // Facemos o commit:
            con.commit();
        } catch (SQLException e) {
            // Lanzamos unha das nosas excepcións propias en caso de que se capture excepción SQL:
            throw new ExcepcionBD(con, e);
        } finally {
            // Pechamos o statement para rematar:
            try {
                stmInstalacions.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }

    /**
     * Método que tenta modificar os datos da instalación pasada como argumento na base de datos.
     *
     * @param instalacion Os datos da instalación para ser modificados.
     * @throws ExcepcionBD Excepción asociada a posibles problemas dados ao actualizar a base de datos.
     */
    public void modificarInstalacion(Instalacion instalacion) throws ExcepcionBD {
        PreparedStatement stmInstalacions = null;
        Connection con;

        // Recuperamos a conexión:
        con = super.getConexion();

        // Preparamos a modificación:
        try {
            // Como temos o código da instalación, podemos ir directamente á tupla que nos interesa:
            stmInstalacions = con.prepareStatement("UPDATE Instalacion " +
                    " SET nome = ?, " +
                    "     numtelefono = ?, " +
                    "     direccion = ? " +
                    " WHERE codInstalacion = ? ");

            // Asignamos os valores que corresponden:
            stmInstalacions.setString(1, instalacion.getNome());
            stmInstalacions.setString(2, instalacion.getNumTelefono());
            stmInstalacions.setString(3, instalacion.getDireccion());
            stmInstalacions.setInt(4, instalacion.getCodInstalacion());

            // Executamos a actualización:
            stmInstalacions.executeUpdate();

            // Facemos un commit, dado que se rematou a actualización:
            con.commit();
        } catch (SQLException e) {
            // Lanzamos a nosa excepción de base de datos en caso de que se capture unha excepción SQL.
            throw new ExcepcionBD(con, e);
        } finally {
            try {
                // Tentamos pechar o statement usado nesta actualización:
                stmInstalacions.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
    }

    /**
     * Método que nos permite buscar instalacións na base de datos, tanto con, coma sen filtros.
     *
     * @param instalacion Se non é null, a consulta das instalacións realizarase en base aos campos desta instalación.
     * @return Se instalación non é null, devolveranse as instalacións que coincidan cos campos de consulta, en caso
     * contrario, devolverase un listado de todas as instalacións.
     */
    public ArrayList<Instalacion> buscarInstalacions(Instalacion instalacion) {
        // Usaremos un ArrayList para almacenar unha nova instalación:
        ArrayList<Instalacion> instalacions = new ArrayList<>();

        PreparedStatement stmInstalacions = null;
        ResultSet rsInstalacions = null;
        Connection con;

        // Recuperamos a conexión:
        con = super.getConexion();

        // Preparamos a consulta:
        try {
            String consulta = "SELECT codinstalacion, nome, numtelefono, direccion " +
                    " FROM instalacion ";

            // A esta consulta, ademais do anterior, engadiremos os filtros se se pasa unha instalación non nula como
            // argumento:
            if (instalacion != null) {
                consulta += " WHERE lower(nome) like lower(?) " +
                        "   and numtelefono like ? " +
                        "   and lower(direccion) like lower(?) ";
            }

            // Ordenaremos o resultado polo código da instalación (para que saian así ordenadas)
            consulta += " ORDER BY codinstalacion";

            stmInstalacions = con.prepareStatement(consulta);

            // Tamén se se pasa argumento haberá que completar a consulta:
            if (instalacion != null) {
                // Establecemos os valores da consulta segundo a instancia de instalación pasada:
                stmInstalacions.setString(1, "%" + instalacion.getNome() + "%");
                stmInstalacions.setString(2, "%" + instalacion.getNumTelefono() + "%");
                stmInstalacions.setString(3, "%" + instalacion.getDireccion() + "%");
            }

            // Realizamos a consulta:
            rsInstalacions = stmInstalacions.executeQuery();

            // Recibida a consulta, procesámola:
            while (rsInstalacions.next()) {
                // Imos engadindo ao ArrayList do resultado cada Instalación consultada:
                instalacions.add(new Instalacion(rsInstalacions.getInt(1), rsInstalacions.getString(2),
                        rsInstalacions.getString(3), rsInstalacions.getString(4)));
            }

            // Facemos o commit para rematar:
            con.commit();
        } catch (SQLException e) {
            // Se se recibe unha excepción, imprimimos o stack trace e facemos o rollback:
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            // Peche do statement:
            try {
                stmInstalacions.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
        // Devolvemos as instalacións como resultado:
        return instalacions;
    }

    /**
     * Método que nos permite consultar unha instalación concreta.
     *
     * @param instalacion A instalación de referencia para a que se consultará a información
     * @return A instalación con todos os datos, actualizada totalmente.
     */
    public Instalacion consultarInstalacion(Instalacion instalacion) {
        PreparedStatement stmInstalacions = null;
        ResultSet rsInstalacions;
        // Devolverase unha instalación como resultado:
        Instalacion resultado = null;
        Connection con;

        // Recuperamos a conexión:
        con = super.getConexion();

        // Tentamos levar a cabo a consulta. Farémola directamente pola clave primaria: isto é unha consulta dunha
        // instalación concreta.
        try {
            stmInstalacions = con.prepareStatement("SELECT codInstalacion, nome, numtelefono, direccion " +
                    " FROM instalacion" +
                    " WHERE codInstalacion = ?");

            // Completamos a preparación da consulta:
            stmInstalacions.setInt(1, instalacion.getCodInstalacion());

            // A continuación, realizamos a consulta:
            rsInstalacions = stmInstalacions.executeQuery();

            // Procesámola
            if (rsInstalacions.next()) {
                // Debería haber un só elemento de resultado:
                resultado = new Instalacion(rsInstalacions.getInt("codInstalacion"),
                        rsInstalacions.getString("nome"),
                        rsInstalacions.getString("numtelefono"),
                        rsInstalacions.getString("direccion"));
            }

            // Feito isto, facemos o commit:
            con.commit();
        } catch (SQLException e) {
            // Imprimimos en caso de excepción o stack trace e facemos o rollback:
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            // Finalmente, pechamos o statement:
            try {
                stmInstalacions.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
        // Devolvemos o resultado da consulta:
        return resultado;
    }

    /**
     * Método que nos permite comprobar se a instalación pasada existe na base de datos, é dicir, se ten o mesmo
     * nome.
     *
     * @param instalacion A instalación cuxo nome queremos validar
     * @return True se hai unha instalación xa co mesmo nome, False en caso contrario.
     */
    public boolean comprobarExistencia(Instalacion instalacion) {
        // Devolveremos un booleano:
        boolean resultado = false;

        PreparedStatement stmInstalacions = null;
        ResultSet rsInstalacions = null;
        Connection con;

        // Recuperamos a conexión:
        con = super.getConexion();

        // Preparamos a consulta: buscamos se hai unha instalación co id pasado:
        try {
            // Ademais, de cara a comprobar se se cambia o nome dunha instalación e esa instalación xa está na base de datos,
            // buscaremos que o código da instalación sexa distinto.
            String consulta = "SELECT * " +
                    "FROM instalacion " +
                    "WHERE lower(nome) = lower(?) ";

            // Preparamos antes a consulta precisamente por poder variar esta:
            if (instalacion.getCodInstalacion() != null) {
                consulta += "  and codInstalacion != ? ";
            }

            stmInstalacions = con.prepareStatement(consulta);

            // Introducimos o nome como filtro:
            stmInstalacions.setString(1, instalacion.getNome());

            if (instalacion.getCodInstalacion() != null) {
                // Asociamos o enteiro en caso do que non sexa null:
                stmInstalacions.setInt(2, instalacion.getCodInstalacion());
            }
            // Executamos a consulta:
            rsInstalacions = stmInstalacions.executeQuery();
            // Comprobamos: se hai resultado, o nome non é valido, xa existe unha instalación así.
            if (rsInstalacions.next()) {
                resultado = true;
            }
            // Ao rematar, facemos commit:
            con.commit();
        } catch (SQLException e) {
            // En caso de erro, imprimimos o stack trace e facemos rollback:
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            // Para rematar pechamos os cursores:
            try {
                stmInstalacions.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
        // Devolvemos, para rematar, o resultado:
        return resultado;
    }

    /**
     * Método que nos permite comprobar se unha instalación ten asociada algunha área.
     *
     * @param instalacion A instalación para a cal queremos comprobar se ten áreas.
     * @return True se a instalación ten áreas, False en caso contrario.
     */
    public boolean tenAreas(Instalacion instalacion) {
        boolean resultado = false;

        PreparedStatement stmAreas = null;
        ResultSet rsAreas = null;
        Connection con;

        // Recuperamos a conexión
        con = super.getConexion();

        // Preparamos a consulta: hai que comprobar se hai áreas que estén asociadas á instalación pasada:
        try {
            stmAreas = con.prepareStatement("SELECT * FROM area WHERE instalacion = ? ");
            // Completamos a consulta:
            stmAreas.setInt(1, instalacion.getCodInstalacion());
            // Realizamos a consulta:
            rsAreas = stmAreas.executeQuery();
            // Comprobamos se hai resultado, se o hai, a instalación ten áreas:
            if (rsAreas.next()) {
                resultado = true;
            }
            // Para rematar facemos o commit:
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
                stmAreas.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
        // Devolvemos o booleano como resultado:
        return resultado;
    }

}

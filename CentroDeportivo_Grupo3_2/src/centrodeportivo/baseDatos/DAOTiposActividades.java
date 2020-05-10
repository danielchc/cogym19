package centrodeportivo.baseDatos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.actividades.TipoActividade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Víctor Barreiro
 * Clase que incluirá os métodos DAO asociado na súa maioría aos tipos de actividades do centro deportivo.
 */
public final class DAOTiposActividades extends AbstractDAO {

    /**
     * Construtor do DAO de tipos de actividades
     *
     * @param conexion          A referencia da conexión coa base de datos.
     * @param fachadaAplicacion A referencia á fachada da parte de aplicación.
     */
    public DAOTiposActividades(Connection conexion, FachadaAplicacion fachadaAplicacion) {
        // Asignaremos estes atributos no constructor da clase pai ao que chamamos:
        super(conexion, fachadaAplicacion);
    }


    /**
     * Método que nos permite introducir na base de datos un novo tipo de actividade, cuxa información se pasa como
     * arugmento.
     *
     * @param tipoActividade Os datos do tipo de actividade a insertar.
     * @throws ExcepcionBD Excepción asociada a problemas que ocorran na actualización da base de datos.
     */
    public void crearTipoActividade(TipoActividade tipoActividade) throws ExcepcionBD {
        // Faremos unha actualización na Base de Datos, usaremos PreparedStatement:
        PreparedStatement stmTiposActividades = null;
        ResultSet rsTiposActividades = null;
        Connection con;

        // Recuperamos a conexión:
        con = super.getConexion();

        // Preparamos a inserción:
        try {
            stmTiposActividades = con.prepareStatement("INSERT INTO tipoActividade (nome, descricion) " +
                    " VALUES (?, ?)");
            // Establecemos os campos co '?'
            stmTiposActividades.setString(1, tipoActividade.getNome());
            stmTiposActividades.setString(2, tipoActividade.getDescricion());

            // Executamos a actualización sobre a Base de Datos:
            stmTiposActividades.executeUpdate();

            // Imos preparar agora unha consulta para coñecer o id do tipo de actividade insertado.
            // Faremos a consulta polo nome deste tipo de actividade:
            stmTiposActividades = con.prepareStatement("SELECT codTipoActividade " +
                    "FROM tipoActividade " +
                    "WHERE nome = ? ");

            // Completamos a consulta cos campos descoñecidos:
            stmTiposActividades.setString(1, tipoActividade.getNome());

            // Realizamos a consulta:
            rsTiposActividades = stmTiposActividades.executeQuery();

            // Debería haber un resultado: o ID da instalación insertada:
            if (rsTiposActividades.next()) {
                tipoActividade.setCodTipoActividade(rsTiposActividades.getInt(1));
            }

            // Facemos o commit:
            con.commit();
        } catch (SQLException e) {
            // En caso de excepción do SQL, lanzaremos a nosa propia excepción:
            throw new ExcepcionBD(con, e);
        } finally {
            // Intentamos pechar o statement:
            try {
                stmTiposActividades.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar os cursores");
            }
        }
    }

    /**
     * Método que nos permite modificar os datos do tipo de actividade pasado como argumento. Suponse que ese tipo de
     * actividade xa está rexistrado e, polo tanto, ten un código asociado.
     *
     * @param tipoActividade O tipo de actividade cos datos a actualizar.
     * @throws ExcepcionBD Excepción asociada a problemas que poidan ocorrer durante a actualización da base de datos.
     */
    public void modificarTipoActividade(TipoActividade tipoActividade) throws ExcepcionBD {
        // Faremos unha actualización sobre a base de datos, usaremos PreparedStatement:
        PreparedStatement stmTiposActividades = null;
        Connection con;

        // Recuperamos a conexión:
        con = super.getConexion();

        // Preparamos a inserción:
        try {
            // Actualizarase a táboa de tipos de actividade onde esté a fila co código pasado:
            stmTiposActividades = con.prepareStatement("UPDATE tipoActividade " +
                    "SET descricion = ?, " +
                    "    nome = ? " +
                    "WHERE codTipoActividade = ? ");

            // Completamos a consulta:
            stmTiposActividades.setString(1, tipoActividade.getDescricion());
            stmTiposActividades.setString(2, tipoActividade.getNome());
            stmTiposActividades.setInt(3, tipoActividade.getCodTipoActividade());

            // Executamos a actualización:
            stmTiposActividades.executeUpdate();

            // Por último, facemos o commit:
            con.commit();
        } catch (SQLException e) {
            // Lanzamos a nosa excepción:
            throw new ExcepcionBD(con, e);
        } finally {
            // Tratamos de pechar o statement
            try {
                stmTiposActividades.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
    }

    /**
     * Método que nos permite eliminar da base de datos o tipo de actividade pasado como argumento.
     *
     * @param tipoActividade O tipo de actividade que se quere eliminar.
     * @throws ExcepcionBD Excepción asociada a problemas que ocorran durante a actualización da base de datos.
     */
    public void eliminarTipoActividade(TipoActividade tipoActividade) throws ExcepcionBD {
        // Tamén faremos unha actualización sobre a base de datos.
        PreparedStatement stmTiposActividades = null;
        Connection con;

        // Recuperamos a conexión:
        con = super.getConexion();

        // Intentamos realizar o borrado:
        try {
            stmTiposActividades = con.prepareStatement("DELETE FROM tipoActividade " +
                    "WHERE codTipoActividade = ?");

            // Completamos a sentenza incluíndo o código do tipo de actividade:
            stmTiposActividades.setInt(1, tipoActividade.getCodTipoActividade());

            // Executamos a actualización:
            stmTiposActividades.executeUpdate();

            // Facemos o commit:
            con.commit();
        } catch (SQLException e) {
            // Lanzamos a nosa excepción de BD para indicar que se produciu un problema no borrado:
            throw new ExcepcionBD(con, e);
        } finally {
            // Tratamos de pechar o statement:
            try {
                stmTiposActividades.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
    }

    /**
     * Método que ofrece un conxunto de tipos de actividade contidos na base de datos.
     *
     * @param tipoActividade O tipo de actividade modelo co que se vai a facer a búsqueda.
     * @return Se o tipo de actividade é null, devolveranse todos os tipos de actividades rexistrados, en caso contrario
     * todos os tipos de actividade que teñan coincidencia co nome que ten o tipo pasado como argumento.
     */
    public ArrayList<TipoActividade> buscarTiposActividades(TipoActividade tipoActividade) {
        // É unha consulta sobre a base de datos.
        ArrayList<TipoActividade> tiposActividades = new ArrayList<>();
        PreparedStatement stmTiposActividades = null;
        ResultSet rsTiposActividades = null;
        Connection con;

        // Recuperamos a conexión:
        con = super.getConexion();

        // Intentamos realizar a consulta:
        try {
            // Elaboramos un string previo porque a consulta pode variar en función de se o tipo de actividade pasado
            // é null ou non
            String consulta = "SELECT codtipoactividade, nome, descricion " +
                    "FROM tipoActividade ";

            // Se o tipo de actividade non é null, entón filtramos polo nome:
            if (tipoActividade != null) {
                consulta += "WHERE lower(nome) like lower(?) ";
            }

            // Ordenamos polo código do tipo de actividade para que nos aparezan así en orde:
            consulta += " ORDER BY codtipoactividade";

            // Recuperamos todos os tipos de actividades posíbeis a partir do nome dentro do tipo de actividade
            // pasado como argumento (se non é null):
            stmTiposActividades = con.prepareStatement(consulta);

            // Completamos a consulta a realizar (en caso de que haxa que completala):
            if (tipoActividade != null) {
                stmTiposActividades.setString(1, "%" + tipoActividade.getNome() + "%");
            }

            // Realizamos a consulta:
            rsTiposActividades = stmTiposActividades.executeQuery();

            // Procesámola:
            while (rsTiposActividades.next()) {
                // Imos creando instancias de tipos de actividades e engadíndoas ao ArrayList:
                tiposActividades.add(new TipoActividade(rsTiposActividades.getInt(1),
                        rsTiposActividades.getString(2), rsTiposActividades.getString(3)));
            }

            // Realizamos o commit:
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
                stmTiposActividades.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }

        // Ofrecemos como resultado os tipos de actividades obtidos da consulta (no peor caso, un arraylist valeiro).
        return tiposActividades;
    }

    /**
     * Método que nos permite consultar un tipo de actividade a partir do código do tipo pasado como argumento.
     *
     * @param tipoActividade O tipo de actividade do que se collerá o código para a consulta.
     * @return O tipo de actividade co código buscado (se aínda existe na base de datos).
     */
    public TipoActividade consultarTipoActividade(TipoActividade tipoActividade) {
        // Neste método usaremos o código do tipo pasado como argumento para consultar se segue estando aí ese tipo de
        // actividade.
        PreparedStatement stmTiposActividades = null;
        ResultSet rsTiposActividades;
        // O resultado meterémolo no seguinte obxecto:
        TipoActividade resultado = null;
        Connection con;

        // Recuperamos a conexión:
        con = super.getConexion();

        // Intentamos facer a consulta:
        try {
            // Consultamos por código:
            stmTiposActividades = con.prepareStatement("SELECT codtipoactividade, nome, descricion " +
                    " FROM tipoactividade" +
                    " WHERE codtipoactividade = ?");

            // Completamos a consulta:
            stmTiposActividades.setInt(1, tipoActividade.getCodTipoActividade());

            // Executamos a consulta:
            rsTiposActividades = stmTiposActividades.executeQuery();

            // Procesamos o resultado, en caso de habelo, só habería 1 tupla (O código é PK!)
            if (rsTiposActividades.next()) {
                resultado = new TipoActividade(rsTiposActividades.getInt("codtipoactividade"),
                        rsTiposActividades.getString("nome"),
                        rsTiposActividades.getString("descricion"));
            }

            // Chegados a este punto, é o momento de facer o commit:
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
                stmTiposActividades.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }

        return resultado;
    }

    /**
     * Método que nos permite comprobar que non existe un tipo de actividade diferente co mesmo nome ca o tipo pasado
     * como argumento.
     *
     * @param tipoActividade O tipo para o que se quere validar a existencia.
     * @return True se existe un tipo de actividade diferente na base de datos que ten o mesmo nome, False en caso
     * contrario.
     */
    public boolean comprobarExistencia(TipoActividade tipoActividade) {
        PreparedStatement stmTiposActividades = null;
        ResultSet rsTiposActividades = null;
        Connection con;
        // Devolveremos como resultado un booleano:
        boolean resultado = false;

        // Recuperamos a conexión:
        con = super.getConexion();

        // Preparamos a consulta: miraremos se hai tipos de actividades co mesmo nome que o pasado.
        // Se a instalación está na base de datos, temos en conta o código pasado (porque o nome pasado pode ser
        // o que xa ten ese tipo de actividade):
        try {
            String consulta = "SELECT * FROM tipoActividade " +
                    "WHERE lower(nome) = lower(?) ";

            if (tipoActividade.getCodTipoActividade() != null) {
                consulta += "  and codTipoActividade != ? ";
            }

            stmTiposActividades = con.prepareStatement(consulta);
            // Completamos os campos:
            stmTiposActividades.setString(1, tipoActividade.getNome());
            if (tipoActividade.getCodTipoActividade() != null) {
                //O código só o engadimos se é realmente necesario:
                stmTiposActividades.setInt(2, tipoActividade.getCodTipoActividade());
            }
            // Realizamos a consulta:
            rsTiposActividades = stmTiposActividades.executeQuery();
            // Comprobamos se hai resultados, pois se é así existirá xa un tipo de actividade con ese nome:
            if (rsTiposActividades.next()) {
                resultado = true;
            }
            // Facemos o commit:
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
                stmTiposActividades.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
        return resultado;
    }

    /**
     * Método que nos permite comprobar se un tipo de actividade ten actividades asociadas.
     *
     * @param tipoActividade O tipo para o que se quere validar se ten actividades.
     * @return True se este tipo de actividade ten actividades asociadas, False noutro caso.
     */
    public boolean tenActividades(TipoActividade tipoActividade) {
        PreparedStatement stmActividades = null;
        ResultSet rsActividades = null;
        Connection con;
        // Devolveremos un booleano:
        boolean resultado = false;


        // Recuperamos a conexión:
        con = super.getConexion();

        // Preparamos a consulta - comprobamos se hai actividades co tipo de actividade pasado como argumento:
        try {
            stmActividades = con.prepareStatement("SELECT * FROM actividade WHERE tipoActividade = ? ");
            // Completamos a consulta:
            stmActividades.setInt(1, tipoActividade.getCodTipoActividade());
            // Realizamos a consulta:
            rsActividades = stmActividades.executeQuery();
            // Comprobamos se hai resultado, se é así, entón hai actividades do tipo pasado:
            if (rsActividades.next()) {
                resultado = true;
            }

            // Facemos commit:
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
                stmActividades.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
        // Devolvemos o resultado, o booleano:
        return resultado;
    }
}

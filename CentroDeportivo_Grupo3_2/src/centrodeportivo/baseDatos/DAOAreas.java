package centrodeportivo.baseDatos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;

import java.sql.*;
import java.util.ArrayList;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Víctor Barreiro
 * Clase que conterá todos os métodos DAO relacionados na súa maioría con xestións das areas.
 */
public final class DAOAreas extends AbstractDAO {

    /**
     * Constructor do DAO de Areas
     *
     * @param conexion          Referencia á conexión coa base de datos.
     * @param fachadaAplicacion Referencia á fachada da parte da aplicación.
     */
    public DAOAreas(Connection conexion, FachadaAplicacion fachadaAplicacion) {
        // Asignaremos estes atributos no constructor da clase pai ao que chamamos:
        super(conexion, fachadaAplicacion);
    }


    /**
     * Método que comproba se existe algunha área co mesmo nome nunha instalación.
     *
     * @param area Área que se busca comprobar se é única ou non.
     * @return Retorna true se existe outra area na mesma instalación con ese nome e false en caso contrario.
     */
    public boolean ExisteArea(Area area) {
        PreparedStatement stmAreas = null;
        ResultSet rsAreas;
        Connection con;

        // Recuperamos a conexión coa base de datos:
        con = super.getConexion();
        boolean existe = false;

        // Preparamos a busca:
        try {
            String consulta = "SELECT  * " +
                    " FROM area" +
                    " WHERE lower(nome) = lower(?) and instalacion = ?";

            // Se a área xa está rexistrada, verificaremos que o código de área sexa distinto.
            // Buscamos áreas DISTINTAS co mesmo nome que o que se lle quere dar á pasada como argumento.
            if (area.getCodArea() != 0) {
                consulta += " and codArea != ?";
            }

            stmAreas = con.prepareStatement(consulta);

            // Completamos ca información da área:
            stmAreas.setString(1, area.getNome());
            stmAreas.setInt(2, area.getInstalacion().getCodInstalacion());

            if (area.getCodArea() != 0) {
                stmAreas.setInt(3, area.getCodArea());
            }

            // Facemos a consulta:
            rsAreas = stmAreas.executeQuery();

            if (rsAreas.next()) {
                // Se hai resultado, poñeremos o booleano a true: existe outra área co mesmo nome na instalación.
                existe = true;
            }

        } catch (SQLException e) {
            // Imprimimos en caso de excepción o stack trace e facemos o rollback:
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            // En calquera caso, téntase pechar os cursores.
            try {
                stmAreas.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
        return existe;
    }

    /**
     * Método que permite engadir unha nova área na base de datos.
     *
     * @param area Información da área a engadir na base de datos
     * @return Retorna un enteiro, neste caso, o código da área.
     * @throws ExcepcionBD Excepción procedente da base de datos para indicar problemas na inserción.
     */
    public int EngadirArea(Area area) throws ExcepcionBD {
        PreparedStatement stmAreas = null;
        ResultSet rsAreas;
        Connection con;

        // Recuperamos a conexión coa base de datos.
        con = super.getConexion();

        // Preparamos a inserción:
        try {
            stmAreas = con.prepareStatement("INSERT INTO Area (instalacion, nome, descricion, aforomaximo) " +
                    " VALUES (?, ?, ?, ?)");

            // Establecemos os valores:
            stmAreas.setInt(1, area.getInstalacion().getCodInstalacion());
            stmAreas.setString(2, area.getNome());
            stmAreas.setString(3, area.getDescricion());
            stmAreas.setInt(4, area.getAforoMaximo());

            // Realizamos a actualización:
            stmAreas.executeUpdate();

            // Fago a consulta polo nome porque tamén é único:
            stmAreas = con.prepareStatement("SELECT codarea FROM area " +
                    " WHERE nome = ? and instalacion = ? ");

            // Establecemos o valor que se necesita:
            stmAreas.setString(1, area.getNome());
            stmAreas.setInt(2, area.getInstalacion().getCodInstalacion());

            // Facemos a consulta:
            rsAreas = stmAreas.executeQuery();

            // Feita a consulta, recuperamos o valor:
            if (rsAreas.next()) {
                area.setCodArea(rsAreas.getInt(1));
            }

            // Facemos commit:
            con.commit();
            return 0;
        } catch (SQLException e) {
            //Lanzamos neste caso unha excepción cara a aplicación:
            throw new ExcepcionBD(con, e);
        } finally {
            //En calquera caso, téntase pechar os cursores.
            try {
                stmAreas.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }

    /**
     * Método que permite consultar se a área ten actividades ou non. A maiores, posibilitamos realizar
     * a consulta sobre actividades, ou actividades sen comezar.
     *
     * @param area       Datos da área onde se desexa facer a busca.
     * @param senComezar Booleano empregado para filtrar a consulta.
     * @return Retorna true se a area ten actividades e false en calquer outro caso.
     */
    public boolean tenActividadeArea(Area area, boolean senComezar) {
        PreparedStatement stmAreas = null;
        Connection con;
        ResultSet rsAux = null;

        // Recuperamos a conexión:
        con = super.getConexion();

        // Preparamos a consulta:
        try {
            // Como temos un booleano que nos permitirá variar un pouco a consulta, faremos un string previo:
            String busca = "SELECT * " +
                    " FROM actividade " +
                    " WHERE area = ? and instalacion = ? ";

            // Se se pideu buscar as actividades desa área non comezadas, entón engadimos outro filtro:
            if (senComezar) {
                busca += " and dataactividade > NOW()";
            }

            stmAreas = con.prepareStatement(busca);

            // Neste caso, non hai parámetros distintos que engadir aos statements en función da consulta feita.
            stmAreas.setInt(1, area.getCodArea());
            stmAreas.setInt(2, area.getInstalacion().getCodInstalacion());

            // Facemos a consulta:
            rsAux = stmAreas.executeQuery();

            if (rsAux.next()) {
                return true;
            }
            return false;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            // Pechamos o statement para rematar.
            try {
                stmAreas.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
        return false;
    }

    /**
     * Método que permite comprobar se a área ten materiais asociados.
     *
     * @param area Información da área sobre a que se quere realizar a busca.
     * @return Devolve true se a área ten materiais asociados e false en calquer outro caso.
     */
    public boolean tenMateriaisArea(Area area) {
        PreparedStatement stmAreas = null;
        Connection con;
        ResultSet rsAux = null;

        // Recuperamos a conexión:
        con = super.getConexion();

        // Preparamos o borrado:
        try {
            stmAreas = con.prepareStatement("SELECT area " +
                    " FROM material " +
                    " WHERE area = ? and instalacion = ? ");

            stmAreas.setInt(1, area.getCodArea());
            stmAreas.setInt(2, area.getInstalacion().getCodInstalacion());

            // Facemos a consulta:
            rsAux = stmAreas.executeQuery();

            if (rsAux.next()) {
                return true;
            }
            return false;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            // Pechamos o statement para rematar:
            try {
                stmAreas.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
        return false;
    }

    /**
     * Método que permite borrar unha área da base de datos.
     *
     * @param area Área que se desexa eliminar da base de datos.
     * @throws ExcepcionBD Excepción procedente da base de datos para indicar problemas no borrado.
     */
    public void borrarArea(Area area) throws ExcepcionBD {
        PreparedStatement stmAreas = null;
        Connection con;

        // Recuperamos a conexión:
        con = super.getConexion();

        // Preparamos o borrado:
        try {
            stmAreas = con.prepareStatement("DELETE FROM area " +
                    " WHERE codarea = ? and instalacion = ?");
            stmAreas.setInt(1, area.getCodArea());
            stmAreas.setInt(2, area.getInstalacion().getCodInstalacion());

            // Realizamos a actualización:
            stmAreas.executeUpdate();

            // Facemos o commit:
            con.commit();
        } catch (SQLException e) {
            // Lanzamos unha das nosas excepcións propias:
            throw new ExcepcionBD(con, e);
        } finally {
            // Pechamos o statement para rematar.
            try {
                stmAreas.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }

    /**
     * Método que permite modificar os datos dunha área na base de datos.
     *
     * @param area Área cos datos modificados para realizar ditos cambios na base de datos.
     * @throws ExcepcionBD Excepción procedente da base de datos para indicar problemas na modificación.
     */
    public void modificarArea(Area area) throws ExcepcionBD {
        PreparedStatement stmAreas = null;
        Connection con;
        ResultSet rsAux = null;

        // Recuperamos a conexión:
        con = super.getConexion();

        // Preparamos a modificación:
        try {
            stmAreas = con.prepareStatement("UPDATE area " +
                    " SET nome = ?, " +
                    "     descricion = ?, " +
                    "     aforomaximo = ?," +
                    "     databaixa = ? " +
                    " WHERE codarea = ? and instalacion = ? ");

            // Asignamos os valores que corresponden:
            stmAreas.setString(1, area.getNome());
            stmAreas.setString(2, area.getDescricion());
            stmAreas.setInt(3, area.getAforoMaximo());
            stmAreas.setDate(4, area.getDataBaixa());
            stmAreas.setInt(5, area.getCodArea());
            stmAreas.setInt(6, area.getInstalacion().getCodInstalacion());

            // Executamos a actualización:
            stmAreas.executeUpdate();

            // Facemos un commit, dado que se rematou a actualización:
            con.commit();
        } catch (SQLException e) {
            // Lanzamos a nosa excepción de base de datos.
            throw new ExcepcionBD(con, e);
        } finally {
            try {
                // Tentamos pechar o statement usado nesta actualización:
                stmAreas.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
    }

    /**
     * Método que permite dar de baixa un área, é dicir, non se elimina da base de datos pero,
     * deixará de estar dispoñible para realizar actividades.
     *
     * @param area Área que se quere dar de baixa.
     * @throws ExcepcionBD Excepción procedente da base de datos para indicar problemas na actualización.
     */
    public void darDeBaixaArea(Area area) throws ExcepcionBD {
        PreparedStatement stmAreas = null;
        Connection con;
        ResultSet rsAux = null;

        // Recuperamos a conexión:
        con = super.getConexion();

        // Preparamos a modificación:
        try {
            stmAreas = con.prepareStatement("UPDATE area " +
                    " SET databaixa = ? " +
                    " WHERE codarea = ? and instalacion = ? ");

            // Asignamos os valores que corresponden:
            stmAreas.setDate(1, new Date(System.currentTimeMillis()));
            stmAreas.setInt(2, area.getCodArea());
            stmAreas.setInt(3, area.getInstalacion().getCodInstalacion());

            // Executamos a actualización:
            stmAreas.executeUpdate();

            // Facemos un commit, dado que se rematou a actualización:
            con.commit();
        } catch (SQLException e) {
            // Lanzamos a nosa excepción de base de datos:
            throw new ExcepcionBD(con, e);
        } finally {
            try {
                // Tentamos pechar o statement usado nesta actualización:
                stmAreas.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
    }

    /**
     * Método que permite dar de alta unha área que xa exisita na base de datos pero, agora estará
     * dispoñible para a realización de actividades.
     *
     * @param area Área que se procura dar de alta.
     * @throws ExcepcionBD Excepción procedente da base de datos para indicar problemas na actualización.
     */
    public void darDeAltaArea(Area area) throws ExcepcionBD {
        PreparedStatement stmAreas = null;
        Connection con;
        ResultSet rsAux = null;

        // Recuperamos a conexión:
        con = super.getConexion();

        // Preparamos a modificación:
        try {
            stmAreas = con.prepareStatement("UPDATE area " +
                    " SET databaixa = null " +
                    " WHERE codarea = ? and instalacion = ? ");

            // Asignamos os valores que corresponden:
            stmAreas.setInt(1, area.getCodArea());
            stmAreas.setInt(2, area.getInstalacion().getCodInstalacion());

            // Executamos a actualización:
            stmAreas.executeUpdate();

            // Facemos un commit, dado que se rematou a actualización:
            con.commit();
        } catch (SQLException e) {
            // Lanzamos a nosa excepción de base de datos:
            throw new ExcepcionBD(con, e);
        } finally {
            try {
                // Tentamos pechar o statement usado nesta actualización:
                stmAreas.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
    }

    /**
     * Método que permite consultar se un área esta dada de baixa ou non.
     *
     * @param area Área que se quere comprobar se esta dada de baixa ou non.
     * @return Retorna true se a área esta dada de baixa e false en caso contrario.
     */
    public boolean EBaixaArea(Area area) {
        PreparedStatement stmAreas = null;
        Connection con;
        ResultSet rsAux = null;

        int result;

        // Recuperamos a conexión:
        con = super.getConexion();

        // Preparamos a modificación:
        try {
            stmAreas = con.prepareStatement("SELECT instalacion, codarea " +
                    " FROM area " +
                    " WHERE codarea = ? and instalacion = ? and not databaixa is null");

            stmAreas.setInt(1, area.getCodArea());
            stmAreas.setInt(2, area.getInstalacion().getCodInstalacion());

            // Facemos a consulta:
            rsAux = stmAreas.executeQuery();

            if (rsAux.next()) {
                return true;
            }
            return false;  // Non existe a area na base de datos.

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                // Tentamos pechar o statement usado nesta actualización:
                stmAreas.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
        return false;
    }

    /**
     * Método que permite realizar a busca de áreas en función dos datos dunha instalación e seguindo un modelo de área.
     *
     * @param instalacion Instalación que empregaremos para filtrar.
     * @param area        Área modelo que empregaremos para filtrar.
     * @return Retorna un ArrayList de áreas que cumpran ditas condicións de filtrado.
     */
    public ArrayList<Area> buscarArea(Instalacion instalacion, Area area) {
        PreparedStatement stmAreas = null;
        ResultSet rsAreas;
        Connection con;
        // Usaremos un ArrayList para almacenar unha nova área:
        ArrayList<Area> areas = new ArrayList<>();

        // Recuperamos a conexión:
        con = super.getConexion();

        // Preparamos a consulta:
        try {
            String consulta = "SELECT codArea, instalacion, nome, descricion, aforomaximo, databaixa" +
                    " FROM area " +
                    " WHERE instalacion = ?";

            // A esta consulta, ademais do anterior, engadiremos os filtros se se pasa unha área non nula como
            // argumento:

            if (area != null) {
                consulta += " and lower(nome) like lower(?) ";
                if (area.getAforoMaximo() != 0) {
                    consulta += " and aforomaximo = ? ";
                }
            }

            // Ordenaremos o resultado polo código da área para ordenalas:
            consulta += " ORDER BY codarea";

            stmAreas = con.prepareStatement(consulta);

            stmAreas.setInt(1, instalacion.getCodInstalacion());

            // Pasando área non nula completase a consulta:
            if (area != null) {
                // Establecemos os valores da consulta segundo a instancia de instalación pasada:
                stmAreas.setString(2, "%" + area.getNome() + "%");
                if (area.getAforoMaximo() != 0) {
                    stmAreas.setInt(3, area.getAforoMaximo());
                }
            }

            // Realizamos a consulta:
            rsAreas = stmAreas.executeQuery();

            // Recibida a consulta, procesámola:
            while (rsAreas.next()) {
                // Imos engadindo ao ArrayList do resultado cada área consultada:
                areas.add(new Area(rsAreas.getInt("codarea"), new Instalacion(rsAreas.getInt("instalacion")),
                        rsAreas.getString("nome"), rsAreas.getString("descricion"), rsAreas.getInt("aforomaximo"), rsAreas.getDate("databaixa")));
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
            // Peche do statement:
            try {
                stmAreas.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
        return areas;
    }

    /**
     * Método que permite listar todas as áreas activas dunha instalación.
     *
     * @param instalacion Datos da instalación na que se realiza a procura das áreas activas.
     * @return Retorna un ArrayList cas áreas activas de dita instalación.
     */
    public ArrayList<Area> listarAreasActivas(Instalacion instalacion) {
        PreparedStatement stmArea = null;
        ResultSet rsArea;
        Connection con;
        // Resultado que será devolto:
        ArrayList<Area> areas = new ArrayList<>();

        // Recuperamos a conexión:
        con = super.getConexion();

        // Preparamos a consulta:
        try {
            String consultaArea = "SELECT codarea, instalacion, nome, descricion, aforomaximo, databaixa " +
                    "FROM area " +
                    "WHERE databaixa is null ";


            // Comprobaremos se estamos a pasar un tipo nulo e xestionaremolo en función do caso:
            if (instalacion != null) {
                consultaArea += " AND instalacion = ? ";
            }

            // Ordenaremos o resultado en función do codigo
            consultaArea += " ORDER BY codArea ";

            stmArea = con.prepareStatement(consultaArea);

            // Completamos a consulta no caso de que o tipo de material non sexa nulo:
            if (instalacion != null) {
                // Establecese o nome do tipo como parametro:
                stmArea.setInt(1, instalacion.getCodInstalacion());
            }

            // Executamos a consulta:
            rsArea = stmArea.executeQuery();

            // Procesamos os datos obtidos da consulta:
            while (rsArea.next()) {
                // Imos engadindo ao ObservableList do resultado cada área consultada:
                areas.add(new Area(rsArea.getInt("codarea"), new Instalacion(rsArea.getInt("instalacion")),
                        rsArea.getString(3), rsArea.getString(4), rsArea.getInt(5), rsArea.getDate(6)));
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
            // Intentamos pechar o statement:
            try {
                assert stmArea != null;
                stmArea.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
        return areas;
    }
}



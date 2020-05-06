package centrodeportivo.baseDatos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.aplicacion.obxectos.area.TipoMaterial;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

public final class DAOAreas extends AbstractDAO {

    public DAOAreas(Connection conexion, FachadaAplicacion fachadaAplicacion) {
        super(conexion, fachadaAplicacion);
    }

    public boolean ExisteArea(Area area) throws ExcepcionBD {
        PreparedStatement stmAreas = null;
        ResultSet rsAreas;
        Connection con;
        //Recuperamos a conexión coa base de datos.
        con = super.getConexion();

        //Preparamos a inserción:
        try {
            stmAreas = con.prepareStatement("SELECT  * " +
                    " FROM area " +
                    " WHERE instalacion = ? and codarea = ?");

            stmAreas.setInt(1, area.getInstalacion().getCodInstalacion());
            stmAreas.setInt(2, area.getCodArea());

            //Facemos a consulta:
            rsAreas = stmAreas.executeQuery();

            if (rsAreas.next()) {
                return true;
            }
            return false; //Dado que non existe dita area na base de datos

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

    public int EngadirArea(Area area) throws ExcepcionBD {
        PreparedStatement stmAreas = null;
        ResultSet rsAreas;
        Connection con;
        //Recuperamos a conexión coa base de datos.
        con = super.getConexion();

        //Preparamos a inserción:
        try {
            stmAreas = con.prepareStatement("INSERT INTO Area (instalacion, nome, descricion, aforomaximo) " +
                    " VALUES (?, ?, ?, ?)");
            //Establecemos os valores:
            stmAreas.setInt(1, area.getInstalacion().getCodInstalacion());
            stmAreas.setString(2, area.getNome());
            stmAreas.setString(3, area.getDescricion());
            stmAreas.setInt(4, area.getAforoMaximo());

            //Realizamos a actualización:
            stmAreas.executeUpdate();

            //Fago a consulta polo nome porque tamén é único:
            stmAreas = con.prepareStatement("SELECT codarea FROM area " +
                    " WHERE nome = ? and instalacion = ? ");

            //Establecemos o valor que se necesita:
            stmAreas.setString(1, area.getNome());
            stmAreas.setInt(2, area.getInstalacion().getCodInstalacion());

            //Facemos a consulta:
            rsAreas = stmAreas.executeQuery();

            //Feita a consulta, recuperamos o valor:
            if (rsAreas.next()) {
                area.setCodArea(rsAreas.getInt(1));
            }

            //Facemos commit:
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

    public boolean tenActividadeArea(Area area) throws ExcepcionBD {
        PreparedStatement stmAreas = null;
        Connection con;
        ResultSet rsAux = null;

        //Recuperamos a conexión:
        con = super.getConexion();

        //Preparamos o borrado:
        try {
            stmAreas = con.prepareStatement("SELECT area " +
                    " FROM actividade " +
                    " WHERE area = ? and instalacion = ? ");

            stmAreas.setInt(1, area.getCodArea());
            stmAreas.setInt(2, area.getInstalacion().getCodInstalacion());

            //Facemos a consulta:
            rsAux = stmAreas.executeQuery();

            if (rsAux.next()) {
                return true;
            }
            return false;

        } catch (SQLException e) {
            //Lanzamos unha das nosas excepcións propias:
            throw new ExcepcionBD(con, e);
        } finally {
            //Pechamos o statement para rematar.
            try {
                stmAreas.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }

    public boolean tenMateriaisArea(Area area) throws ExcepcionBD {
        PreparedStatement stmAreas = null;
        Connection con;
        ResultSet rsAux = null;

        //Recuperamos a conexión:
        con = super.getConexion();

        //Preparamos o borrado:
        try {
            stmAreas = con.prepareStatement("SELECT area " +
                    " FROM material " +
                    " WHERE area = ? and instalacion = ? ");

            stmAreas.setInt(1, area.getCodArea());
            stmAreas.setInt(2, area.getInstalacion().getCodInstalacion());

            //Facemos a consulta:
            rsAux = stmAreas.executeQuery();

            if (rsAux.next()) {
                return true;
            }
            return false;

        } catch (SQLException e) {
            //Lanzamos unha das nosas excepcións propias:
            throw new ExcepcionBD(con, e);
        } finally {
            //Pechamos o statement para rematar.
            try {
                stmAreas.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }

    public void borrarArea(Area area) throws ExcepcionBD {
        PreparedStatement stmAreas = null;
        Connection con;

        //Recuperamos a conexión:
        con = super.getConexion();

        System.out.println(area.getCodArea());

        //Preparamos o borrado:
        try {
            stmAreas = con.prepareStatement("DELETE FROM area " +
                    " WHERE codarea = ? and instalacion = ?");
            stmAreas.setInt(1, area.getCodArea());
            stmAreas.setInt(2, area.getInstalacion().getCodInstalacion());

            //Realizamos a actualización:
            stmAreas.executeUpdate();
            //Facemos o commit:
            con.commit();

        } catch (SQLException e) {
            //Lanzamos unha das nosas excepcións propias:
            throw new ExcepcionBD(con, e);
        } finally {
            //Pechamos o statement para rematar.
            try {
                stmAreas.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }


    public void modificarArea(Area area) throws ExcepcionBD {
        PreparedStatement stmAreas = null;
        Connection con;
        ResultSet rsAux = null;

        //Recuperamos a conexión:
        con = super.getConexion();

        //Preparamos a modificación:
        try {
                    stmAreas = con.prepareStatement("UPDATE area " +
                            " SET nome = ?, " +
                            "     descricion = ?, " +
                            "     aforomaximo = ?," +
                            "     databaixa = ? " +
                            " WHERE codarea = ? and instalacion = ? ");

                    //Asignamos os valores que corresponden:
                    stmAreas.setString(1, area.getNome());
                    stmAreas.setString(2, area.getDescricion());
                    stmAreas.setInt(3, area.getAforoMaximo());
                    stmAreas.setDate(4, area.getDataBaixa());
                    stmAreas.setInt(5, area.getCodArea());
                    stmAreas.setInt(6, area.getInstalacion().getCodInstalacion());

                    //Executamos a actualización:
                    stmAreas.executeUpdate();
                    //Facemos un commit, dado que se rematou a actualización:
                    con.commit();

        } catch (SQLException e) {
            //Lanzamos a nosa excepción de base de datos.
            throw new ExcepcionBD(con, e);
        } finally {
            try {
                //Tentamos pechar o statement usado nesta actualización:
                stmAreas.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
    }

    public void darDeBaixaArea(Area area) throws ExcepcionBD {
        PreparedStatement stmAreas = null;
        Connection con;
        ResultSet rsAux = null;

        //Recuperamos a conexión:
        con = super.getConexion();

        //Preparamos a modificación:
        try {
            stmAreas = con.prepareStatement("UPDATE area " +
                    " SET databaixa = ? " +
                    " WHERE codarea = ? and instalacion = ? ");

            //Asignamos os valores que corresponden:
            stmAreas.setDate(1, new Date(System.currentTimeMillis()));
            stmAreas.setInt(2, area.getCodArea());
            stmAreas.setInt(3, area.getInstalacion().getCodInstalacion());

            //Executamos a actualización:
            stmAreas.executeUpdate();
            //Facemos un commit, dado que se rematou a actualización:
            con.commit();

        } catch (SQLException e) {
            //Lanzamos a nosa excepción de base de datos.
            throw new ExcepcionBD(con, e);
        } finally {
            try {
                //Tentamos pechar o statement usado nesta actualización:
                stmAreas.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
    }

    public void darDeAltaArea(Area area) throws ExcepcionBD {
        PreparedStatement stmAreas = null;
        Connection con;
        ResultSet rsAux = null;

        //Recuperamos a conexión:
        con = super.getConexion();

        //Preparamos a modificación:
        try {
            stmAreas = con.prepareStatement("UPDATE area " +
                    " SET databaixa = null " +
                    " WHERE codarea = ? and instalacion = ? ");

            //Asignamos os valores que corresponden:
            stmAreas.setInt(1, area.getCodArea());
            stmAreas.setInt(2, area.getInstalacion().getCodInstalacion());

            //Executamos a actualización:
            stmAreas.executeUpdate();
            //Facemos un commit, dado que se rematou a actualización:
            con.commit();

        } catch (SQLException e) {
            //Lanzamos a nosa excepción de base de datos.
            throw new ExcepcionBD(con, e);
        } finally {
            try {
                //Tentamos pechar o statement usado nesta actualización:
                stmAreas.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
    }

    public boolean EBaixaArea(Area area) throws ExcepcionBD {
        PreparedStatement stmAreas = null;
        Connection con;
        ResultSet rsAux = null;

        int result;

        //Recuperamos a conexión:
        con = super.getConexion();

        //Preparamos a modificación:
        try {
            stmAreas = con.prepareStatement("SELECT instalacion, codarea " +
                    " FROM area " +
                    " WHERE codarea = ? and instalacion = ? and not databaixa is null");

            stmAreas.setInt(1, area.getCodArea());
            stmAreas.setInt(2, area.getInstalacion().getCodInstalacion());

            //Facemos a consulta:
            rsAux = stmAreas.executeQuery();

            if (rsAux.next()) {
                return true;
            }
            return false; //Non existe a area na base de datos

        } catch (SQLException e) {
            //Lanzamos a nosa excepción de base de datos.
            throw new ExcepcionBD(con, e);
        } finally {
            try {
                //Tentamos pechar o statement usado nesta actualización:
                stmAreas.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
    }


    public ArrayList<Area> listarAreas() {
        ArrayList<Area> areas = new ArrayList<>();

        PreparedStatement stmAreas = null;
        ResultSet rsAreas = null;
        Connection con;

        //Recuperamos a conexión:
        con = super.getConexion();

        //Preparamos a consulta:
        try {
            stmAreas = con.prepareStatement("SELECT codarea, instalacion, nome, descricion, aforomaximo, databaixa " +
                    "FROM area");

            //Non hai nada que insertar na consulta, polo que directamente a realizamos:
            rsAreas = stmAreas.executeQuery();

            //Recibida a consulta, procesámola:
            while (rsAreas.next()) {
                //Imos engadindo ao ArrayList do resultado cada area consultada:
                areas.add(new Area(rsAreas.getInt(2), new Instalacion(rsAreas.getInt(1)),
                        rsAreas.getString(3), rsAreas.getString(4), rsAreas.getInt(5), rsAreas.getDate(6)));
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
            //Intentamos pechar o statement:
            try {
                stmAreas.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
        return areas;
    }

    public ArrayList<Area> buscarArea(Instalacion instalacion, Area area) {
        //Usaremos un ArrayList para almacenar unha nova area:
        ArrayList<Area> areas = new ArrayList<>();

        PreparedStatement stmAreas = null;
        ResultSet rsAreas;
        Connection con;

        //Recuperamos a conexión:
        con = super.getConexion();

        //Preparamos a consulta:
        try {
            String consulta = "SELECT codArea, instalacion, nome, descricion, aforomaximo, databaixa" +
                    " FROM area " +
                    " WHERE instalacion = ?";

            //A esta consulta, ademais do anterior, engadiremos os filtros se se pasa unha area non nula como
            //argumento:

            if (area != null) {
                consulta += " WHERE nome like ? " +
                        "   and aforomaximo = ? ";
            }

            //Ordenaremos o resultado polo código da área para ordenalas
            consulta += " ORDER BY codarea";

            stmAreas = con.prepareStatement(consulta);

            stmAreas.setInt(1, instalacion.getCodInstalacion());

            //Pasando area non nula completase a consulta.
            if (area != null) {
                //Establecemos os valores da consulta segundo a instancia de instalación pasada:
                stmAreas.setString(2, "%" + area.getNome() + "%");
                stmAreas.setInt(3, area.getAforoMaximo());
            }

            //Realizamos a consulta:
            rsAreas = stmAreas.executeQuery();

            //Recibida a consulta, procesámola:
            while (rsAreas.next()) {
                //Imos engadindo ao ArrayList do resultado cada area consultada:
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
            //Peche do statement:
            try {
                stmAreas.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
        return areas;
    }


    /**
     * Método que nos permite buscar areas na base de datos en función dunha instalación.
     *
     * @param instalacion Se non é null, a consulta realizase en base o codigo da area.
     * @return Se o parametro non é null, será devolto unha ObservableList con todas as areas que coincidan,
     * noutro caso, listaranse todas as areas.
     */
    public ObservableList<Area> listarAreasActivas(Instalacion instalacion) {
        ObservableList<Area> areas = FXCollections.observableArrayList();
        PreparedStatement stmArea = null;
        ResultSet rsArea;
        Connection con;

        // Recuperamos a conexión:
        con = super.getConexion();

        // Preparamos a consulta:
        try {
            String consultaArea = "SELECT codarea, instalacion, nome, descricion, aforomaximo, databaixa " +
                    "FROM area " +
                    "WHERE databaixa is null";


            // Comprobaremos se estamos a pasar un tipo nulo e xestionaremolo en función do caso:
            if (instalacion != null) {
                consultaArea += " AND instalacion = ? ";
            }

            // Ordenaremos o resultado en función do codigo
            consultaArea += " ORDER BY codArea ";

            stmArea = con.prepareStatement(consultaArea);

            // Completamos a consulta no caso de que o tipo de material non sexa nulo:
            if (instalacion != null) {
                // Establecese o nome do tipo como parametro
                stmArea.setInt(1, instalacion.getCodInstalacion());
            }

            // Executamos a consulta
            rsArea = stmArea.executeQuery();

            // Procesamos os datos obtidos da consulta:
            while (rsArea.next()) {
                // Imos engadindo ao ObservableList do resultado cada area consultada:
                areas.add(new Area(rsArea.getInt(2), new Instalacion(rsArea.getInt(1)),
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



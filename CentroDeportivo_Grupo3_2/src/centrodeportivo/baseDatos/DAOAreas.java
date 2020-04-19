package centrodeportivo.baseDatos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public final class DAOAreas extends AbstractDAO {

    public DAOAreas(Connection conexion, FachadaAplicacion fachadaAplicacion){
        super(conexion, fachadaAplicacion);
    }

    public int darAltaArea(Area area) throws ExcepcionBD {
        PreparedStatement stmAreas = null;
        ResultSet rsAreas;
        Connection con;
        //Recuperamos a conexión coa base de datos.
        con = super.getConexion();

        //Preparamos a inserción:
        try {
            stmAreas = con.prepareStatement("SELECT codinstalacion " +
                    " FROM instalacion " +
                    " WHERE codinstalacion = ? ");

            stmAreas.setInt(1, area.getInstalacion().getCodInstalacion());

            //Facemos a consulta:
            rsAreas = stmAreas.executeQuery();

            if(rsAreas.next()) {
                if (rsAreas.getInt(1) == area.getInstalacion().getCodInstalacion())
                {
                    stmAreas = con.prepareStatement("INSERT INTO Area (instalacion, nome, descricion, aforomaximo) " +
                            " VALUES (?, ?, ?, ?)");
                    //Establecemos os valores:
                    stmAreas.setInt(1, area.getInstalacion().getCodInstalacion());
                    stmAreas.setString(2, area.getNome());
                    stmAreas.setString(3, area.getDescricion());
                    stmAreas.setInt(4, area.getAforoMaximo());

                    //Realizamos a actualización:
                    stmAreas.executeUpdate();

                    //Imos facer unha segunda consulta para recuperar o ID da instalación:
                    //Fago a consulta polo nome porque tamén é único:
                    stmAreas = con.prepareStatement("SELECT codarea FROM area " +
                            " WHERE nome = ? and instalacion = ? ");

                    //Establecemos o valor que se necesita:
                    stmAreas.setString(1, area.getNome());
                    stmAreas.setInt(2, area.getInstalacion().getCodInstalacion());

                    //Facemos a consulta:
                    rsAreas = stmAreas.executeQuery();

                    //Feita a consulta, recuperamos o valor:
                    if(rsAreas.next()) { //Só debería devolverse un ID.
                        //Así metemos o ID na instalación, e podemos amosalo para rematar a operación.
                        area.setCodArea(rsAreas.getInt(1));
                    }

                    //Facemos commit:
                    con.commit();
                    return 0;
                }
            }
            return 1; //Dado que non existe dita instalación na base de datos

        } catch (SQLException e){
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

    public int borrarArea (Area area) throws ExcepcionBD {
        PreparedStatement stmAreas = null;
        Connection con;
        ResultSet rsAux = null;

        //Recuperamos a conexión:
        con = super.getConexion();

        //Preparamos o borrado:
        try{
            stmAreas = con.prepareStatement("SELECT area " +
                    " FROM actividade " +
                    " WHERE area = ? and instalacion = ? ");

            stmAreas.setInt(1, area.getCodArea());
            stmAreas.setInt(2, area.getInstalacion().getCodInstalacion());

            if(!rsAux.next()) {
                stmAreas = con.prepareStatement("SELECT area " +
                        " FROM material " +
                        " WHERE area = ? and instalacion = ? ");

                stmAreas.setInt(1, area.getCodArea());
                stmAreas.setInt(2, area.getInstalacion().getCodInstalacion());

                if(!rsAux.next()) {
                    stmAreas = con.prepareStatement("DELETE FROM area " +
                            " WHERE codarea = ? and codinstalacion = ?");
                    stmAreas.setInt(1, area.getCodArea());
                    stmAreas.setInt(2, area.getInstalacion().getCodInstalacion());

                    //Realizamos a actualización:
                    stmAreas.executeUpdate();
                    //Facemos o commit:
                    con.commit();
                    return 0;
                }
                return 2; //Devolvemos un 2 dado que existen materiais asociados a este area
            }
            return 1; //Devolvemos un 1 dado que hai algunha actividade neste area

        } catch (SQLException e){
            //Lanzamos unha das nosas excepcións propias:
            throw new ExcepcionBD(con, e);
        } finally {
            //Pechamos o statement para rematar.
            try{
                stmAreas.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }

    public int modificarArea (Area area) throws ExcepcionBD {
        PreparedStatement stmAreas = null;
        Connection con;
        ResultSet rsAux = null;

        //Recuperamos a conexión:
        con = super.getConexion();

        //Preparamos a modificación:
        try {
            stmAreas = con.prepareStatement("SELECT codinstalacion " +
                    " FROM instalacion " +
                    " WHERE codinstalacion = ? ");

            stmAreas.setInt(1, area.getInstalacion().getCodInstalacion());

            //Facemos a consulta:
            rsAux = stmAreas.executeQuery();

            if (rsAux.next()) {
                if (rsAux.getInt(1) == area.getInstalacion().getCodInstalacion()) {
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
                    return 0;
                }
            }
            return 1; //Non existe a instalacion na base de datos

        } catch (SQLException e) {
            //Lanzamos a nosa excepción de base de datos.
            throw new ExcepcionBD(con, e);
        } finally {
            try {
                //Tentamos pechar o statement usado nesta actualización:
                stmAreas.close();
            } catch (SQLException e){
                System.out.println("Imposible pechar os cursores");
            }
        }
    }

    public int darDeBaixaArea (Area area) throws ExcepcionBD {
        PreparedStatement stmAreas = null;
        Connection con;
        ResultSet rsAux = null;

        int result;

        //Recuperamos a conexión:
        con = super.getConexion();

        //Preparamos a modificación:
        try{
            stmAreas = con.prepareStatement("SELECT area " +
                    " FROM actividade " +
                    " WHERE area = ? and instalacion = ? ");

            stmAreas.setInt(1, area.getCodArea());
            stmAreas.setInt(2, area.getInstalacion().getCodInstalacion());

            if(!rsAux.next()) {
                stmAreas = con.prepareStatement("SELECT area " +
                        " FROM material " +
                        " WHERE area = ? and instalacion = ? ");

                stmAreas.setInt(1, area.getCodArea());
                stmAreas.setInt(2, area.getInstalacion().getCodInstalacion());

                if(!rsAux.next()) {
                    stmAreas = con.prepareStatement("UPDATE area " +
                            " SET databaixa = ? " +
                            " WHERE codarea = ? and instalacion = ? ");

                    //Asignamos os valores que corresponden:
                    stmAreas.setDate(1, area.getDataBaixa());
                    stmAreas.setInt(2, area.getCodArea());
                    stmAreas.setInt(3, area.getInstalacion().getCodInstalacion());

                    //Executamos a actualización:
                    stmAreas.executeUpdate();
                    //Facemos un commit, dado que se rematou a actualización:
                    con.commit();
                    return 0;
                }
                return 2; //Devolvemos un 2 dado que existen materiais asociados a este area
            }
            return 1; //Devolvemos un 1 dado que hai algunha actividade neste area

        } catch (SQLException e) {
            //Lanzamos a nosa excepción de base de datos.
            throw new ExcepcionBD(con, e);
        } finally {
            try {
                //Tentamos pechar o statement usado nesta actualización:
                stmAreas.close();
            } catch (SQLException e){
                System.out.println("Imposible pechar os cursores");
            }
        }
    }

    public ArrayList<Area> listarAreas(){
        //Este método serviranos para amosar todas as instalacións, e evitar usar o where en buscas sen filtros:
        ArrayList<Area> areas = new ArrayList<>();

        PreparedStatement stmAreas = null;
        ResultSet rsAreas = null;
        Connection con;

        //Recuperamos a conexión:
        con = super.getConexion();

        //Preparamos a consulta:
        try{
            stmAreas = con.prepareStatement("SELECT codarea, instalacion, nome, descricion, aforomaximo, databaixa " +
                    "FROM area");

            //Non hai nada que insertar na consulta, polo que directamente a realizamos:
            rsAreas = stmAreas.executeQuery();

            //Recibida a consulta, procesámola:
            while(rsAreas.next()){
                //Imos engadindo ao ArrayList do resultado cada Instalación consultada:
                areas.add(new Area(rsAreas.getInt(2),new Instalacion(rsAreas.getInt(1)),
                        rsAreas.getString(3), rsAreas.getString(4), rsAreas.getInt(5), rsAreas.getDate(6)));

            }
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try{
                con.rollback();
            } catch (SQLException ex){
                ex.printStackTrace();
            }
        } finally {
            //Intentamos pechar o statement:
            try{
                stmAreas.close();
            } catch(SQLException e){
                System.out.println("Imposible pechar os cursores.");
            }
        }
        return areas;
    }

}

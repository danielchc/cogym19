package centrodeportivo.baseDatos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;

import java.sql.*;
import java.util.ArrayList;

public final class DAOInstalacions extends AbstractDAO {

    public DAOInstalacions(Connection conexion, FachadaAplicacion fachadaAplicacion){
        super(conexion, fachadaAplicacion);
    }

    /**
     * Método para dar de alta unha nova instalación:
     * @param instalacion a instalación a insertar
     */
    public void darAltaInstalacion(Instalacion instalacion){
        PreparedStatement stmInstalacions = null;
        ResultSet rsInstalacions = null;
        Connection con;

        //Recuperamos a conexión coa base de datos.
        con = super.getConexion();

        //Preparamos a inserción:
        try {
            stmInstalacions = con.prepareStatement("INSERT INTO Instalacion (nome, numtelefono, direccion) " +
                    " VALUES (?, ?, ?)");
            //Establecemos os valores:
            stmInstalacions.setString(1, instalacion.getNome());
            stmInstalacions.setString(2, instalacion.getNumTelefono());
            stmInstalacions.setString(3, instalacion.getDireccion());

            //Realizamos a actualización:
            stmInstalacions.executeUpdate();

            //Imos facer unha segunda consulta para recuperar o ID da instalación:
            //Fago a consulta polo nome porque tamén é único:
            stmInstalacions = con.prepareStatement("SELECT codInstalacion FROM instalacion " +
                    " WHERE nome = ? ");

            //Establecemos o valor que se necesita:
            stmInstalacions.setString(1, instalacion.getNome());

            //Facemos a consulta:
            rsInstalacions = stmInstalacions.executeQuery();

            //Feita a consulta, recuperamos o valor:
            if(rsInstalacions.next()) { //Só debería devolverse un ID.
                //Así metemos o ID na instalación, e podemos amosalo para rematar a operación.
                instalacion.setCodInstalacion(rsInstalacions.getInt(1));
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            try {
                con.commit();
                stmInstalacions.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }

    public void borrarInstalacion(Instalacion instalacion){
        PreparedStatement stmInstalacions = null;
        Connection con;

        //Recuperamos a conexión:
        con = super.getConexion();

        //Preparamos o borrado:
        try{
            stmInstalacions = con.prepareStatement("DELETE FROM Instalacion " +
                    " WHERE codInstalacion = ?");
            stmInstalacions.setInt(1, instalacion.getCodInstalacion());

            //Realizamos a actualización:
            stmInstalacions.executeUpdate();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            //Pechamos o statement:
            try{
                con.commit();
                stmInstalacions.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }

    public void modificarInstalacion(Instalacion instalacion){
        PreparedStatement stmInstalacions = null;
        Connection con;

        //Recuperamos a conexión:
        con = super.getConexion();

        //Preparamos a modificación:
        try{
            stmInstalacions = con.prepareStatement("UPDATE Instalacion " +
                    " SET nome = ?, " +
                    "     numtelefono = ?, " +
                    "     direccion = ? " +
                    " WHERE codInstalacion = ? ");

            //Asignamos os valores que corresponden:
            stmInstalacions.setString(1, instalacion.getNome());
            stmInstalacions.setString(2, instalacion.getNumTelefono());
            stmInstalacions.setString(3, instalacion.getDireccion());
            stmInstalacions.setInt(4, instalacion.getCodInstalacion());

            //Executamos a actualización:
            stmInstalacions.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                con.commit();
                //Tentamos pechar o statement usado nesta actualización:
                stmInstalacions.close();
            } catch (SQLException e){
                System.out.println("Imposible pechar os cursores");
            }
        }
    }

    public ArrayList<Instalacion> buscarInstalacions(Instalacion instalacion){
        //Usaremos un ArrayList para almacenar un usuario:
        ArrayList<Instalacion> instalacions = new ArrayList<>();

        PreparedStatement stmInstalacions = null;
        ResultSet rsInstalacions = null;
        Connection con;

        //Recuperamos a conexión:
        con = super.getConexion();

        //Preparamos a consulta:
        try{
            stmInstalacions = con.prepareStatement("SELECT codinstalacion, nome, numtelefono, direccion " +
                    " FROM instalacion " +
                    " WHERE nome like ? " +
                    "   and numtelefono like ? " +
                    "   and direccion like ? ");

            //Establecemos os valores da consulta segundo a instancia de instalación pasada:
            stmInstalacions.setString(1, "%" + instalacion.getNome() + "%");
            stmInstalacions.setString(2, "%" + instalacion.getNumTelefono() + "%");
            stmInstalacions.setString(3, "%" + instalacion.getDireccion() + "%");

            //Realizamos a consulta:
            rsInstalacions = stmInstalacions.executeQuery();

            //Recibida a consulta, procesámola:
            while(rsInstalacions.next()){
                //Imos engadindo ao ArrayList do resultado cada Instalación consultada:
                instalacions.add(new Instalacion(rsInstalacions.getInt(1), rsInstalacions.getString(2),
                        rsInstalacions.getString(3), rsInstalacions.getString(4)));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            //Peche do statement:
            try {
                stmInstalacions.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
        return instalacions;
    }

    public ArrayList<Instalacion> listarInstalacións(){
        //Este método serviranos para amosar todas as instalacións, e evitar usar o where en buscas sen filtros:
        ArrayList<Instalacion> instalacions = new ArrayList<>();

        PreparedStatement stmInstalacions = null;
        ResultSet rsInstalacions = null;
        Connection con;

        //Recuperamos a conexión:
        con = super.getConexion();

        //Preparamos a consulta:
        try{
            stmInstalacions = con.prepareStatement("SELECT codInstalacion, nome, numTelefono, direccion " +
                    "FROM Instalacion");

            //Non hai nada que insertar na consulta, polo que directamente a realizamos:
            rsInstalacions = stmInstalacions.executeQuery();

            //Recibida a consulta, procesámola:
            while(rsInstalacions.next()){
                //Imos engadindo ao ArrayList do resultado cada Instalación consultada:
                instalacions.add(new Instalacion(rsInstalacions.getInt(1), rsInstalacions.getString(2),
                        rsInstalacions.getString(3), rsInstalacions.getString(4)));
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            //Intentamos pechar o statement:
            try{
                stmInstalacions.close();
            } catch(SQLException e){
                System.out.println("Imposible pechar os cursores.");
            }
        }
        return instalacions;
    }

    public boolean comprobarExistencia(Instalacion instalacion){
        boolean resultado = false;

        PreparedStatement stmInstalacions = null;
        ResultSet rsInstalacions = null;
        Connection con;

        //Recuperamos a conexión:
        con = super.getConexion();

        //Preparamos a consulta: buscamos se hai unha instalación co id pasado:
        try{
            //Ademais, de cara a comprobar se se cambia o nome dunha instalación, buscaremos
            //que o código da instalación sexa distinto (se se pasa unha instalación nova, o código será 0).
            stmInstalacions = con.prepareStatement("SELECT * " +
                    "FROM instalacion " +
                    "WHERE lower(nome) = lower(?) " +
                    "  and codInstalacion != ? ");
            //Introducimos o nome como filtro:
            stmInstalacions.setString(1, instalacion.getNome());
            stmInstalacions.setInt(2, instalacion.getCodInstalacion());
            //Executamos a consulta:
            rsInstalacions = stmInstalacions.executeQuery();
            //Comprobamos: se hai resultado, o nome non é valido, xa existe unha instalación así.
            if(rsInstalacions.next()){
                resultado = true;
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            try{
                stmInstalacions.close();
            } catch(SQLException e){
                System.out.println("Imposible pechar os cursores");
            }
        }
        return resultado;
    }

    public boolean tenAreas(Instalacion instalacion){
        boolean resultado = false;

        PreparedStatement stmAreas = null;
        ResultSet rsAreas = null;
        Connection con;

        //Recuperamos a conexión
        con = super.getConexion();

        //Preparamos a consulta: hai que comprobar se hai áreas que estén asociadas á instalación pasada:
        try{
            stmAreas = con.prepareStatement("SELECT * FROM area WHERE instalacion = ? ");
            //Completamos a consulta:
            stmAreas.setInt(1, instalacion.getCodInstalacion());
            //Realizamos a consulta:
            rsAreas = stmAreas.executeQuery();
            //Comprobamos se hai resultado: se o hai, a instalación ten áreas:
            if(rsAreas.next()){
                resultado = true;
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            try{
                stmAreas.close();
            } catch (SQLException e){
                System.out.println("Imposible pechar os cursores");
            }
        }
        return resultado;
    }

}

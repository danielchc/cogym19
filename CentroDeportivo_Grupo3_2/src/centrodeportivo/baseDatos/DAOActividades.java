package centrodeportivo.baseDatos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.actividades.TipoActividade;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOActividades extends AbstractDAO {
    //DAO para o relativo á xestión de actividades e tipos de actividades:

    //Constructor:
    public DAOActividades(Connection conexion, FachadaAplicacion fachadaAplicacion){
        //Chamamos ao constructor da clase pai:
        super(conexion, fachadaAplicacion);
    }

    public void crearTipoActividade(TipoActividade tipoActividade){
        //Faremos unha actualización na Base de Datos, usaremos PreparedStatement:
        PreparedStatement stmTiposActividades = null;
        ResultSet rsTiposActividades = null;
        Connection con;

        //Recuperamos a conexión:
        con = super.getConexion();

        //Preparamos a inserción:
        try{
            stmTiposActividades = con.prepareStatement("INSERT INTO tipoActividade (nome, descricion) " +
                    " VALUES (?, ?)");
            //Establecemos os campos co ?
            stmTiposActividades.setString(1, tipoActividade.getNome());
            stmTiposActividades.setString(2, tipoActividade.getDescricion());

            //Executamos a actualización sobre a Base de Datos:
            stmTiposActividades.executeUpdate();

            //Imos preparar agora unha consulta para coñecer o id do tipo de actividade insertado.
            //Faremos a consulta polo nome deste tipo de actividade:

            stmTiposActividades = con.prepareStatement("SELECT codTipoActividade " +
                    "FROM tipoActividade " +
                    "WHERE nome = ? ");

            //Completamos a consulta cos campos descoñecidos:
            stmTiposActividades.setString(1,tipoActividade.getNome());

            //Realizamos a consulta:
            rsTiposActividades = stmTiposActividades.executeQuery();

            //Debería haber un resultado: o ID da instalación insertada:
            if(rsTiposActividades.next()){
                tipoActividade.setCodTipoActividade(rsTiposActividades.getInt(1));
            }

        } catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            //Intentamos pechar o statement:
            try{
                //Facemos o commit:
                con.commit();
                stmTiposActividades.close();
            } catch(SQLException e ){
                System.out.println("Imposible cerrar os cursores");
            }
        }
    }

    public void modificarTipoActividade(TipoActividade tipoActividade){
        //Faremos unha actualización sobre a base de datos, usaremos PreparedStatement:
        PreparedStatement stmTiposActividades = null;
        Connection con;

        //Recuperamos a conexión:
        con = super.getConexion();

        //Preparamos a inserción:
        try{
            //Actualizarase a taboa de tipos de actividade onde esté a fila co código pasado:
            stmTiposActividades = con.prepareStatement("UPDATE tipoActividade " +
                    "SET descricion = ? " +
                    "WHERE codTipoActividade = ? ");

            //Completamos a consulta:
            stmTiposActividades.setString(1, tipoActividade.getDescricion());
            stmTiposActividades.setInt(2, tipoActividade.getCodTipoActividade());

            //Executamos a actualización:
            stmTiposActividades.executeUpdate();

            //Por último, facemos o commit:
            con.commit();
        } catch(SQLException e){
            System.out.println(e.getMessage());
        } finally {
            //Tratamos de pechar o statement
            try{
                con.commit();
                stmTiposActividades.close();
            } catch (SQLException e){
                System.out.printf("Imposible pechar os cursores");
            }
        }
    }

    public void eliminarTipoActividade(TipoActividade tipoActividade){
        //Tamén faremos unha actualización sobre a base de datos.
        PreparedStatement stmTiposActividades = null;
        Connection con;

        //Recuperamos a conexión:
        con = super.getConexion();

        //Intentamos realizar o borrado:
        try{
            stmTiposActividades = con.prepareStatement("DELETE FROM tipoActividade " +
                    "WHERE codTipoActividade = ?");
            //Completamos a sentenza:
            stmTiposActividades.setInt(1, tipoActividade.getCodTipoActividade());

            //Executamos a actualización:
            stmTiposActividades.executeUpdate();

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            //Tratamos de pechar o statement:
            try{
                con.commit();
                stmTiposActividades.close();
            } catch (SQLException e){
                System.out.println("Imposible pechar os cursores");
            }
        }
    }

    public ArrayList<TipoActividade> listarTiposActividades(){
        //Trátase dunha consulta sobre a base de datos.
        ArrayList<TipoActividade> tiposActividades = new ArrayList<>();
        PreparedStatement stmTiposActividades = null;
        ResultSet rsTiposActividades = null;
        Connection con;

        //Recuperamos a conexión
        con = super.getConexion();

        //Intentamos realizar a consulta:
        try{
            stmTiposActividades = con.prepareStatement("SELECT codTipoActividade, nome, descricion " +
                    "FROM tipoActividade");
            //Executámola:
            rsTiposActividades = stmTiposActividades.executeQuery();

            //Procesámola:
            while(rsTiposActividades.next()){
                //Imos creando instancias de tipos de actividades e engadíndoas ao ArrayList:
                tiposActividades.add(new TipoActividade(rsTiposActividades.getInt(1),
                        rsTiposActividades.getString(2), rsTiposActividades.getString(3)));
            }

            //Facemos o commit:
            con.commit();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            //Intentamos pechar o statement:
            try {
                stmTiposActividades.close();
            } catch (SQLException e){
                System.out.println("Imposible pechar os cursores");
            }
        }
        //Ofrecemos como resultado os tipos de actividades recuperados:
        return tiposActividades;
    }

    public ArrayList<TipoActividade> buscarTiposActividades(TipoActividade tipoActividade){
        //É unha consulta sobre a base de datos.
        ArrayList<TipoActividade> tiposActividades = new ArrayList<>();
        PreparedStatement stmTiposActividades = null;
        ResultSet rsTiposActividades = null;
        Connection con;

        //Recuperamos a conexión:
        con = super.getConexion();

        //Intentamos realizar a consulta:
        try{
            //Recuperamos todos os tipos de actividades posíbeis a partir do nome dentro do tipo de actividade
            //pasado como argumento:
            stmTiposActividades = con.prepareStatement("SELECT codTipoActividade, nome, descricion " +
                    "FROM tipoActividade " +
                    "WHERE nome like ? ");
            //Completamos a consulta a realizar:
            stmTiposActividades.setString(1, "%" + tipoActividade.getNome() + "%");

            //Realizamos a consulta:
            rsTiposActividades =  stmTiposActividades.executeQuery();

            //Procesámola:
            while(rsTiposActividades.next()){
                //Imos creando instancias de tipos de actividades e engadíndoas ao ArrayList:
                tiposActividades.add(new TipoActividade(rsTiposActividades.getInt(1),
                        rsTiposActividades.getString(2), rsTiposActividades.getString(3)));
            }

            //Realizamos o commit:
            con.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            //Intentamos pechar os cursores:
            try{
                stmTiposActividades.close();
            } catch (SQLException e){
                System.out.println("Imposible pechar os cursores");
            }
        }

        //Ofrecemos como resultado os tipos de actividades obtidos da consulta (no peor caso, un arraylist vacío).
        return tiposActividades;
    }

    public boolean comprobarExistencia(TipoActividade tipoActividade){
        boolean resultado = false;

        PreparedStatement stmTiposActividades = null;
        ResultSet rsTiposActividades = null;
        Connection con;

        //Recuperamos a conexión:
        con = super.getConexion();

        //Preparamos a consulta: miraremos se hai tipos de actividades co mesmo nome que o pasado:
        try{
            stmTiposActividades = con.prepareStatement("SELECT * FROM tipoActividade " +
                    "WHERE lower(nome) = lower(?) ");
            //Completamos os campos:
            stmTiposActividades.setString(1, tipoActividade.getNome());
            //Realizamos a consulta:
            rsTiposActividades = stmTiposActividades.executeQuery();
            //Comprobamos se hai resultados, pois se é así existirá xa un tipo de actividade con ese nome:
            if(rsTiposActividades.next()){
                resultado = true;
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            try{
                stmTiposActividades.close();
            } catch(SQLException e){
                System.out.println("Imposible pechar os cursores");
            }
        }
        return resultado;
    }

    public boolean tenActividades(TipoActividade tipoActividade){
        boolean resultado = false;

        PreparedStatement stmActividades = null;
        ResultSet rsActividades = null;
        Connection con;

        //Recuperamos a conexión:
        con = super.getConexion();

        //Preparamos a consulta - comprobamos se hai actividades con ese tipo:
        try{
            stmActividades = con.prepareStatement("SELECT * FROM actividade WHERE tipoActividade = ? ");
            //Completamos a consulta:
            stmActividades.setInt(1, tipoActividade.getCodTipoActividade());
            //Realizamos a consulta:
            rsActividades = stmActividades.executeQuery();
            //Comprobamos se hai resultado, se é así, entón hai actividades do tipo pasado:
            if(rsActividades.next()){
                resultado = true;
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            try{
                stmActividades.close();
            } catch(SQLException e){
                System.out.println("Imposible pechar os cursores.");
            }
        }
        return resultado;
    }
}

package centrodeportivo.baseDatos;


import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.tarifas.Tarifa;
import centrodeportivo.baseDatos.AbstractDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public final class DAOTarifas extends AbstractDAO {
    public DAOTarifas(Connection conexion, FachadaAplicacion fachadaAplicacion) {
        super(conexion,fachadaAplicacion);
    }

    protected void insertarTarifa(Tarifa t){
        PreparedStatement stmTarifa=null;
        try{
            stmTarifa=super.getConexion().prepareStatement("INSERT INTO tarifas (nome,maxActividades,precioBase,precioExtra) VALUES (?,?,?,?);");
            stmTarifa.setString(1,t.getNome());
            stmTarifa.setInt(2,t.getMaxActividades());
            stmTarifa.setFloat(3,t.getPrezoBase());
            stmTarifa.setFloat(4,t.getPrezoExtras());
            stmTarifa.executeUpdate();
            super.getConexion().commit();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                stmTarifa.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }

    protected void borrarTarifa(Integer codTarifa) {
        PreparedStatement stmTarifa=null;
        try{
            stmTarifa=super.getConexion().prepareStatement("DELETE FROM tarifas WHERE codTarifa=?");
            stmTarifa.setInt(1, codTarifa);
            stmTarifa.executeUpdate();
            super.getConexion().commit();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                stmTarifa.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }

    protected void actualizarTarifa(Tarifa t){
        Connection conexion=super.getConexion();
        PreparedStatement stmTarifa=null;
        try{
            stmTarifa=conexion.prepareStatement("UPDATE tarifas SET maxActividades=?, precioBase=?, precioExtra=? WHERE codTarifa=?;");
            stmTarifa.setInt(1,t.getMaxActividades());
            stmTarifa.setFloat(2,t.getPrezoBase());
            stmTarifa.setFloat(3,t.getPrezoExtras());
            stmTarifa.setInt(4,t.getCodTarifa());
            stmTarifa.executeUpdate();
            System.out.println(stmTarifa);
            conexion.commit();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                stmTarifa.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }

    protected boolean estaEnUsoTarifa(Integer codTarifa){
        Connection conexion=super.getConexion();
        PreparedStatement stmTarifa = null;
        ResultSet resultTarifas;
        try{
            stmTarifa=conexion.prepareStatement("SELECT * FROM tarifas WHERE codTarifa=? AND codTarifa IN (SELECT tarifa FROM socios);");
            stmTarifa.setInt(1,codTarifa);
            resultTarifas=stmTarifa.executeQuery();
            return resultTarifas.next();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                stmTarifa.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
        return false;
    }

    protected ArrayList<Tarifa> listarTarifas(){
        ArrayList<Tarifa> tarifas=new ArrayList<>();
        Connection conexion=super.getConexion();
        PreparedStatement stmTarifa = null;
        ResultSet resultTarifas;
        try{
            stmTarifa=conexion.prepareStatement("SELECT * FROM tarifas");
            resultTarifas=stmTarifa.executeQuery();
            while (resultTarifas.next()){
                tarifas.add(new Tarifa(
                        resultTarifas.getInt("codTarifa"),
                        resultTarifas.getString("nome"),
                        resultTarifas.getInt("maxActividades"),
                        resultTarifas.getFloat("precioBase"),
                        resultTarifas.getFloat("precioExtra")
                ));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                stmTarifa.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
        return tarifas;
    }

    protected Tarifa consultarTarifaSocio(String loginSocio){
        PreparedStatement stmTarifa = null;
        ResultSet resultTarifas;
        try{
            stmTarifa=super.getConexion().prepareStatement("SELECT * FROM tarifas WHERE codTarifa IN (SELECT tarifa FROM socios WHERE login=?)");
            stmTarifa.setString(1,loginSocio);
            resultTarifas=stmTarifa.executeQuery();
            if(resultTarifas.next()){
                return new Tarifa(
                        resultTarifas.getInt("codTarifa"),
                        resultTarifas.getString("nome"),
                        resultTarifas.getInt("maxActividades"),
                        resultTarifas.getFloat("precioBase"),
                        resultTarifas.getFloat("precioExtra")
                );
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                stmTarifa.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
        return null;
    }

    protected boolean existeTarifa(String nome){
        PreparedStatement stmTarifa = null;
        ResultSet resultValidacion;
        try {
            stmTarifa=super.getConexion().prepareStatement("SELECT * FROM tarifas WHERE LOWER(nome)=LOWER(?)");
            stmTarifa.setString(1,nome);
            resultValidacion=stmTarifa.executeQuery();
            return resultValidacion.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                stmTarifa.close();
            } catch (SQLException e){
                System.out.println("Imposible cerrar cursores");
            }
        }
        return false;
    }
}

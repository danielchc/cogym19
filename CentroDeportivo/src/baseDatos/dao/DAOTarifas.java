package baseDatos.dao;


import aplicacion.Tarifa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public final class DAOTarifas extends AbstractDAO {

    public DAOTarifas(Connection conexion) {
        super(conexion);
    }

    public void insertarTarifa(Tarifa t) throws SQLException{
        Connection conexion=super.getConexion();
        PreparedStatement stmTarifa=null;

        stmTarifa=conexion.prepareStatement("INSERT INTO tarifas (nome,maxActividades,precioBase,precioExtra) VALUES (?,?,?,?);");
        stmTarifa.setString(1,t.getNome());
        stmTarifa.setInt(2,t.getMaxActividades());
        stmTarifa.setFloat(3,t.getPrezoBase());
        stmTarifa.setFloat(4,t.getPrezoExtras());
        stmTarifa.executeUpdate();
        conexion.commit();
    }

    public void borrarTarifa(Integer codTarifa) throws SQLException{
        Connection conexion=super.getConexion();
        PreparedStatement stmTarifa=null;

        stmTarifa=conexion.prepareStatement("DELETE FROM tarifas WHERE codTarifa=?");
        stmTarifa.setInt(1, codTarifa);
        stmTarifa.executeUpdate();
        conexion.commit();
    }

    public void actualizarTarifa(Tarifa t) throws SQLException{
        Connection conexion=super.getConexion();
        PreparedStatement stmTarifa=null;

        stmTarifa=conexion.prepareStatement("UPDATE tarifas SET maxActividades=?, precioBase=?, precioExtra=? WHERE codTarifa=?;");
        stmTarifa.setInt(1,t.getMaxActividades());
        stmTarifa.setFloat(2,t.getPrezoBase());
        stmTarifa.setFloat(3,t.getPrezoExtras());
        stmTarifa.setInt(4,t.getCodTarifa());
        stmTarifa.executeUpdate();
        conexion.commit();
    }

    public boolean estaEnUsoTarifa(Integer codTarifa) throws SQLException{
        Connection conexion=super.getConexion();
        PreparedStatement stmTarifa = null;
        ResultSet resultTarifas;

        stmTarifa=conexion.prepareStatement("SELECT * FROM tarifas WHERE codTarifa=? AND codTarifa IN (SELECT tarifa FROM socios);");
        stmTarifa.setInt(1,codTarifa);
        resultTarifas=stmTarifa.executeQuery();
        return resultTarifas.next();
    }

    public ArrayList<Tarifa> listarTarifas() throws SQLException{
        ArrayList<Tarifa> tarifas=new ArrayList<>();
        Connection conexion=super.getConexion();
        PreparedStatement stmTarifa = null;
        ResultSet resultTarifas;

        stmTarifa=conexion.prepareStatement("SELECT * FROM tarifas");
        resultTarifas=stmTarifa.executeQuery();
        while (resultTarifas.next()){
            tarifas.add(new Tarifa(resultTarifas.getInt("codTarifa"),resultTarifas.getString("nome"),resultTarifas.getInt("maxActividades"),resultTarifas.getFloat("precioBase"),resultTarifas.getFloat("precioExtra")));
        }
        return tarifas;
    }
}

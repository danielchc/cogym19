package baseDatos.dao;


import aplicacion.Tarifa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class DAOTarifas extends AbstractDAO {

    public DAOTarifas(Connection conexion) {
        super(conexion);
    }

    public void insertarTarifa(Tarifa t) throws SQLException{
        Connection conexion=super.getConexion();
        PreparedStatement stmTarfia=null;

        conexion.setAutoCommit(false);
        stmTarfia=conexion.prepareStatement("INSERT INTO tarifas (nome,maxActividades,precioBase,precioExtra) VALUES (?,?,?,?)");
        stmTarfia.setString(1,t.getNome());
        stmTarfia.setInt(2,t.getMaxActividades());
        stmTarfia.setFloat(3,t.getPrezoBase());
        stmTarfia.setFloat(4,t.getPrezoExtras());
        stmTarfia.executeUpdate();
        conexion.commit();
    }

    public void borrarTarifa(Tarifa t){

    }

    public void actualizarTarifa(Tarifa t) throws SQLException{
        Connection conexion=super.getConexion();
        PreparedStatement stmTarfia=null;

        conexion.setAutoCommit(false);
        stmTarfia=conexion.prepareStatement("UPDATE tarifas (nome,maxActividades,precioBase,precioExtra) VALUES (?,?,?,?)");
        stmTarfia.setString(1,t.getNome());
        stmTarfia.setInt(2,t.getMaxActividades());
        stmTarfia.setFloat(3,t.getPrezoBase());
        stmTarfia.setFloat(4,t.getPrezoExtras());
        stmTarfia.executeUpdate();
        conexion.commit();
    }
}

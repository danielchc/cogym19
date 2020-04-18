package centrodeportivo.baseDatos;


import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.tarifas.Tarifa;
import centrodeportivo.baseDatos.AbstractDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public final class DAOTarifas extends AbstractDAO {

    /**
     * @param conexion Conexión coa base de datos
     * @param fachadaAplicacion fachada da aplicación
     */
    protected DAOTarifas(Connection conexion, FachadaAplicacion fachadaAplicacion) {
        super(conexion, fachadaAplicacion);
    }

    /**
     * Método para gardar unha tarifa na base de datos.
     * @param t Tarifa a ser insertada
     * @throws ExcepcionBD
     */
    protected void insertarTarifa(Tarifa t) throws ExcepcionBD {
        PreparedStatement stmTarifa = null;
        try {
            stmTarifa = super.getConexion().prepareStatement("INSERT INTO tarifa (nome,maxActividades,precioBase,precioExtra) VALUES (?,?,?,?);");
            stmTarifa.setString(1, t.getNome());
            stmTarifa.setInt(2, t.getMaxActividades());
            stmTarifa.setFloat(3, t.getPrezoBase());
            stmTarifa.setFloat(4, t.getPrezoExtras());
            stmTarifa.executeUpdate();
            super.getConexion().commit();
        } catch (SQLException e) {
            throw new ExcepcionBD(super.getConexion(), e);
        } finally {
            try {
                stmTarifa.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }

    /**
     * Método para borrar unha tarifa da base de datos.
     * @param codTarifa Código da tarifa a borrar
     * @throws ExcepcionBD
     */
    protected void borrarTarifa(Integer codTarifa) throws ExcepcionBD {
        PreparedStatement stmTarifa = null;
        try {
            stmTarifa = super.getConexion().prepareStatement("DELETE FROM tarifa WHERE codTarifa=?");
            stmTarifa.setInt(1, codTarifa);
            stmTarifa.executeUpdate();
            super.getConexion().commit();
        } catch (SQLException e) {
            throw new ExcepcionBD(super.getConexion(), e);
        } finally {
            try {
                stmTarifa.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }

    /**
     * Método para actualizar os datos dunha tarifa.
     * @param t tarifa a ser actualizada
     * @throws ExcepcionBD
     */
    protected void actualizarTarifa(Tarifa t) throws ExcepcionBD {
        Connection conexion = super.getConexion();
        PreparedStatement stmTarifa = null;
        try {
            stmTarifa = conexion.prepareStatement("UPDATE tarifa SET maxActividades=?, precioBase=?, precioExtra=? WHERE codTarifa=?;");
            stmTarifa.setInt(1, t.getMaxActividades());
            stmTarifa.setFloat(2, t.getPrezoBase());
            stmTarifa.setFloat(3, t.getPrezoExtras());
            stmTarifa.setInt(4, t.getCodTarifa());
            stmTarifa.executeUpdate();
            System.out.println(stmTarifa);
            conexion.commit();
        } catch (SQLException e) {
            throw new ExcepcionBD(super.getConexion(), e);
        } finally {
            try {
                stmTarifa.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }

    /**
     * Método para comprobar se unha tarifa está sendo usada por algún socio.
     * @param codTarifa código da tarifa
     * @return
     */
    protected boolean estaEnUsoTarifa(Integer codTarifa) {
        Connection conexion = super.getConexion();
        PreparedStatement stmTarifa = null;
        ResultSet resultTarifas;
        try {
            stmTarifa = conexion.prepareStatement("SELECT * FROM tarifa WHERE codTarifa=? AND codTarifa IN (SELECT tarifa FROM socio);");
            stmTarifa.setInt(1, codTarifa);
            resultTarifas = stmTarifa.executeQuery();
            return resultTarifas.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmTarifa.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
        return false;
    }

    /**
     * Método para listar todas as tarifas dispoñibles.
     * @return lista de tarifas.
     */
    protected ArrayList<Tarifa> listarTarifas() {
        ArrayList<Tarifa> tarifas = new ArrayList<>();
        Connection conexion = super.getConexion();
        PreparedStatement stmTarifa = null;
        ResultSet resultTarifas;
        try {
            stmTarifa = conexion.prepareStatement("SELECT * FROM tarifa");
            resultTarifas = stmTarifa.executeQuery();
            while (resultTarifas.next()) {
                tarifas.add(new Tarifa(
                        resultTarifas.getInt("codTarifa"),
                        resultTarifas.getString("nome"),
                        resultTarifas.getInt("maxActividades"),
                        resultTarifas.getFloat("precioBase"),
                        resultTarifas.getFloat("precioExtra")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmTarifa.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
        return tarifas;
    }

    /**
     * Método para comprobar qué tarifa ten un socio.
     * @param loginSocio login do socio
     * @return tarifa que ten o socio
     */
    protected Tarifa consultarTarifaSocio(String loginSocio) {
        PreparedStatement stmTarifa = null;
        ResultSet resultTarifas;
        try {
            stmTarifa = super.getConexion().prepareStatement("SELECT * FROM tarifa WHERE codTarifa IN (SELECT tarifa FROM socio WHERE login=?)");
            stmTarifa.setString(1, loginSocio);
            resultTarifas = stmTarifa.executeQuery();
            if (resultTarifas.next()) {
                return new Tarifa(
                        resultTarifas.getInt("codTarifa"),
                        resultTarifas.getString("nome"),
                        resultTarifas.getInt("maxActividades"),
                        resultTarifas.getFloat("precioBase"),
                        resultTarifas.getFloat("precioExtra")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmTarifa.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
        return null;
    }

    /**
     * Método para comprobar se unha tarifa con nome dado existe.
     * @param nome nome da tarifa
     * @return true se existe, false se non
     */
    protected boolean existeTarifa(String nome) {
        PreparedStatement stmTarifa = null;
        ResultSet resultValidacion;
        try {
            stmTarifa = super.getConexion().prepareStatement("SELECT * FROM tarifa WHERE LOWER(nome)=LOWER(?)");
            stmTarifa.setString(1, nome);
            resultValidacion = stmTarifa.executeQuery();
            return resultValidacion.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmTarifa.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return false;
    }
}

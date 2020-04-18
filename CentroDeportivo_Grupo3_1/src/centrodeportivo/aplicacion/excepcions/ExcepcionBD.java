package centrodeportivo.aplicacion.excepcions;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public class ExcepcionBD extends Exception {

    /**
     * Atributos da clase Excepción SQL.
     */
    private Connection conexion;
    private int codigoError;
    private SQLException exceptionSQL;

    /**
     * Constructor da excepción para as transaccións sql.
     * Garda os datos da excepción e realiza o rollback da transacción que fallou.
     * @param conexion conexion coa base.
     * @param excepcion excepcion SQL producida.
     */
    public ExcepcionBD(Connection conexion, SQLException excepcion){
        super();
        this.conexion=conexion;
        this.codigoError=Integer.parseInt(excepcion.getSQLState());
        this.exceptionSQL=excepcion;
        try {
            conexion.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método para obter a causa da excepción.
     * @return mensaxe co erro
     */
    @Override
    public String getMessage(){
        String msg="Erro na base de datos: ";
        switch (codigoError){
            //claves primarias repetidas/uniques
            case 23505:
                msg+="Intentouse introducir un dato xa en uso";
                break;
            //checks
            case 23514:
                msg+="Non se validou algunha das condicións necesarias para a actualización";
                break;
            //claves foráneas
            case 23503:
                msg+="Problema coas dependencias dos datos.";
                break;
            //conexion coa base
            case 8004:
                msg+="Problema ao conectar coa base de datos";
                break;
        }
        return msg+=" ("+codigoError+": "+exceptionSQL.getMessage()+").";
    }


    /**
     * Getters e Setters
     */
    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public int getCodigoError() {
        return codigoError;
    }

    public void setCodigoError(int codigoError) {
        this.codigoError = codigoError;
    }

    public SQLException getExceptionSQL() {
        return exceptionSQL;
    }

    public void setExceptionSQL(SQLException exceptionSQL) {
        this.exceptionSQL = exceptionSQL;
    }

    @Override
    public String toString() {
        return "ExcepcionBD{" +
                "conexion=" + conexion +
                ", codigoError=" + codigoError +
                ", exceptionSQL=" + exceptionSQL +
                '}';
    }
}

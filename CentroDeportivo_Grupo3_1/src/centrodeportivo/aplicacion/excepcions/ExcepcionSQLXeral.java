package centrodeportivo.aplicacion.excepcions;

import java.sql.Connection;

public class ExcepcionSQLXeral extends Exception {

    /**
     * Atributos da clase Excepción SQL Xeral.
     */
    private Connection conexion;
    private int codigoError;
    private String mensaxe;

    public ExcepcionSQLXeral(Connection conexion,int codigoError){
        this.conexion=conexion;
        this.codigoError=codigoError;
        this.mensaxe="Erro relacionado coa base de datos.";
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

    public String getMensaxe() {
        return mensaxe;
    }

    public void setMensaxe(String mensaxe) {
        this.mensaxe = mensaxe;
    }
}

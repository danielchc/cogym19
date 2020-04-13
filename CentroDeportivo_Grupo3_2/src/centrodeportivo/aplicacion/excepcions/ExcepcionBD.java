package centrodeportivo.aplicacion.excepcions;

import java.sql.Connection;
import java.sql.SQLException;

public class ExcepcionBD extends Exception {

    private Connection conexion;
    private SQLException excepcionSQL;

    public ExcepcionBD(Connection con,SQLException excepcionSQL) {
        super();
        this.conexion = conexion;
        this.excepcionSQL = excepcionSQL;
    }

    @Override
    public String getMessage(){
        String msg = "Erro na base de datos: ";
        switch(excepcionSQL.getErrorCode()){
            case 23505: //Unique duplicado - claves priamrias e alternativas
                msg += "intentouse introducir un dato xa en uso";
                break;
            case 23514: //Checks inclumplidos.
                msg += "non se validou algunha das condicións necesarias para a actualización";
                break;
            case 23503: //Claves foráneas
                msg += "";
                break;
            case 8000: //Erro coa conexión
                msg += "problema ao conectar coa base de datos";
                break;
        }
        return msg + " (" + excepcionSQL.getMessage() + ").";
    }
}

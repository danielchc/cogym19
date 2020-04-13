package centrodeportivo.aplicacion.excepcions;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Hai moitas restriccións que xa comprobamos a nivel de aplicación, sen embargo, sempre pode aparecer algo na base de
 * datos (pensemos en se se executan varias transaccións de xeito concorrente). Deste xeito, creamos unha excepción
 * particular para que, en caso de erros na base de datos, se controlen mellor ca dende o DAO.
 * Con este motivo nace a clase ExcepcionBD.
 *
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Victor Barreiro
 */
public class ExcepcionBD extends Exception {

    private Connection conexion;
    private SQLException excepcionSQL;

    public ExcepcionBD(Connection con, SQLException excepcionSQL) {
        super();
        this.conexion = conexion;
        this.excepcionSQL = excepcionSQL;
        try{
            con.rollback();
        } catch(SQLException e){
            e.printStackTrace(); //Imprimiríamos por consola se houbese algún problema.
        }
    }

    @Override
    public String getMessage(){
        String msg = "Erro na base de datos: ";
        switch(Integer.parseInt(excepcionSQL.getSQLState())){
            case 23505: //Unique duplicado - claves priamrias e alternativas
                msg += "intentouse introducir un dato xa en uso";
                break;
            case 23514: //Checks inclumplidos.
                msg += "non se validou algunha das condicións necesarias para a actualización";
                break;
            case 23503: //Claves foráneas
                msg += "";
                break;
            case 8004: //Erro coa conexión
                msg += "problema ao conectar coa base de datos";
                break;
        }
        return msg + " ("+ excepcionSQL.getSQLState() + ": " + excepcionSQL.getMessage() + ").";
    }
}

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

    /**
     * Atributos da clase:
     */
    private Connection conexion; //Conexión coa base de datos dende onde se produciu a excepción.
    private SQLException excepcionSQL; //Excepción de SQL asociada ao motivo do lanzamento desta outra excepción.

    /**
     * Constructor da excepción asociada a problemas nos métodos DAO:
     * @param con conexión coa base de datos.
     * @param excepcionSQL Excepción de SQL producida.
     */
    public ExcepcionBD(Connection con, SQLException excepcionSQL) {
        //Chamamos ao constructor da clase pai:
        super();
        //Asignamos os parámetros aos atributos da excepción:
        this.conexion = conexion;
        this.excepcionSQL = excepcionSQL;
        //Tentamos realizar, coa conexión pasada, o rollback() para que non se aplique ningún cambio sobre a BD.
        try{
            con.rollback();
        } catch(SQLException e){
            e.printStackTrace(); //Imprimiríamos por consola se houbese algún problema no rollback.
        }
    }

    /**
     * Método que nos permite elaborar a mensaxe de erro asociada a esta excepción.
     * @return A mensaxe a amosar.
     */
    @Override
    public String getMessage(){
        //As mensaxes que se imprimirán por culpa desta excepción estarán asociadas a problemas coa base de datos, de
        //aí que un dos atributos sexa unha excepción SQL.
        //Todas as mensaxes empezarán por esta frase:
        String msg = "Erro na base de datos: ";
        //Agora, en función do código de estado SQL co que se devolvera a excepción, personalizamos algo a mensaxe:
        switch(excepcionSQL.getSQLState()){
            case "23505": //Unique duplicado - claves priamrias e alternativas
                msg += "intentouse introducir un dato xa en uso";
                break;
            case "23514": //Checks inclumplidos.
                msg += "non se validou algunha das condicións necesarias para a actualización";
                break;
            case "23503": //Claves foráneas
                msg += "produciuse algún problema de dependencia cos datos";
                break;
            case "8004": //Erro coa conexión
                msg += "problema ao conectar coa base de datos";
                break;
        }
        //Para rematar a mensaxe devolta, asociaremos o estado da excepción SQL e a propia mensaxe que nos proporciona
        //a excepción de SQL:
        return msg + " ("+ excepcionSQL.getSQLState() + ": " + excepcionSQL.getMessage() + ").";
    }
}

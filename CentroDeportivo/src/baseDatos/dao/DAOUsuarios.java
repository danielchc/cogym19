package baseDatos.dao;

import aplicacion.Persoal;
import aplicacion.Profesor;
import aplicacion.Socio;
import aplicacion.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;

public final class DAOUsuarios extends AbstractDAO {

    public DAOUsuarios(Connection conexion) {
        super(conexion);
    }

    public void insertarUsuario(Usuario usuario){
        PreparedStatement stmUsuario;
        if(usuario instanceof Socio) {

        }else if (usuario instanceof Persoal){
            if(usuario instanceof Profesor){

            }
        }
    }
}

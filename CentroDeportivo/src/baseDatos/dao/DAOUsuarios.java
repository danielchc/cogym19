package baseDatos.dao;

import aplicacion.Persoal;
import aplicacion.Profesor;
import aplicacion.Socio;
import aplicacion.Usuario;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class DAOUsuarios extends AbstractDAO {

    public DAOUsuarios(Connection conexion) {
        super(conexion);
    }

    public void insertarUsuario(Usuario usuario) throws SQLException {
        Connection con= this.getConexion();
        PreparedStatement stmUsuario,stmSocio,stmPersoal,stmProfesor;
        con.setAutoCommit(false);

        stmUsuario= con.prepareStatement(
                "INSERT INTO usuarios (login,contrasinal,nome,numTelefono,DNI,correoElectronico,IBAN) \n" +
                        "VALUES (?,?,?,?,?,?,?);"
        );
        stmUsuario.setString(1,usuario.getLogin());
        stmUsuario.setString(2,usuario.getContrasinal());
        stmUsuario.setString(3,usuario.getNome());
        stmUsuario.setString(4,usuario.getNumTelefono());
        stmUsuario.setString(5,usuario.getDNI());
        stmUsuario.setString(6,usuario.getCorreoElectronico());
        stmUsuario.setString(7,usuario.getIBANconta());
        stmUsuario.executeUpdate();
        if(usuario instanceof Socio) {
            Socio socio=(Socio)usuario;
            stmSocio=con.prepareStatement(
                    "INSERT INTO socios (login,dataNacemento,dificultades,tarifa) values (?,?,?,?);"
            );
            stmSocio.setString(1,socio.getLogin());
            stmSocio.setDate(2, socio.getDataNacemento());
            stmSocio.setString(3,socio.getDificultades());
            stmSocio.setInt(4,socio.getTarifa().getCodTarifa());
            stmSocio.executeUpdate();
        }else if (usuario instanceof Persoal){
            Persoal persoal=(Persoal)usuario;
            stmPersoal=con.prepareStatement(
                    "INSERT INTO persoal (login,dataIncorporacion,NUSS) VALUES (?,?,?);"
            );
            stmPersoal.setString(1,persoal.getLogin());
            stmPersoal.setDate(2, persoal.getDataIncorporacion());
            stmPersoal.setString(3,persoal.getNUSS());
            stmPersoal.executeUpdate();
            if(usuario instanceof Profesor){
                Profesor profesor=(Profesor)usuario;
                stmProfesor=con.prepareStatement(
                        "INSERT INTO profesor (login) VALUES (?);"
                );
                stmProfesor.setString(1,profesor.getLogin());
                stmProfesor.executeUpdate();
            }
        }
        con.commit();
    }
}

package baseDatos.dao;

import aplicacion.Persoal;
import aplicacion.Profesor;
import aplicacion.Socio;
import aplicacion.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class DAOUsuarios extends AbstractDAO {

    public DAOUsuarios(Connection conexion) {
        super(conexion);
    }

    public boolean existeUsuario(String login) throws SQLException {
        Connection conexion=super.getConexion();
        PreparedStatement stmUsuario = null;
        ResultSet resultValidacion;
        stmUsuario=conexion.prepareStatement("SELECT * FROM usuarios WHERE login=?");
        stmUsuario.setString(1,login);
        resultValidacion=stmUsuario.executeQuery();
        return resultValidacion.next();
    }

    public boolean validarUsuario(String login,String password) throws SQLException {
        Connection conexion=super.getConexion();
        PreparedStatement stmUsuario = null;
        ResultSet resultValidacion;
        stmUsuario=conexion.prepareStatement("SELECT * FROM usuarios WHERE login=? AND contrasinal=?");
        stmUsuario.setString(1,login);
        stmUsuario.setString(2,password);
        resultValidacion=stmUsuario.executeQuery();
        return resultValidacion.next();
    }

    public void insertarUsuario(Usuario usuario) throws SQLException {
        Connection con= this.getConexion();
        PreparedStatement stmUsuario=null,stmSocio=null,stmPersoal=null,stmProfesor=null;
        con.setAutoCommit(false);

        stmUsuario= con.prepareStatement("INSERT INTO usuarios (login,contrasinal,nome,numTelefono,DNI,correoElectronico,IBAN)  VALUES (?,?,?,?,?,?,?);");
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
            stmSocio=con.prepareStatement("INSERT INTO socios (login,dataNacemento,dificultades,tarifa) values (?,?,?,?);");
            stmSocio.setString(1,socio.getLogin());
            stmSocio.setDate(2, socio.getDataNacemento());
            stmSocio.setString(3,socio.getDificultades());
            stmSocio.setInt(4,socio.getTarifa().getCodTarifa());
            stmSocio.executeUpdate();
        }else if (usuario instanceof Persoal){
            Persoal persoal=(Persoal)usuario;
            stmPersoal=con.prepareStatement("INSERT INTO persoal (login,dataIncorporacion,NUSS) VALUES (?,?,?);");
            stmPersoal.setString(1,persoal.getLogin());
            stmPersoal.setDate(2, persoal.getDataIncorporacion());
            stmPersoal.setString(3,persoal.getNUSS());
            stmPersoal.executeUpdate();
            if(usuario instanceof Profesor){
                Profesor profesor=(Profesor)usuario;
                stmProfesor=con.prepareStatement("INSERT INTO profesor (login) VALUES (?);");
                stmProfesor.setString(1,profesor.getLogin());
                stmProfesor.executeUpdate();
            }
        }
        con.commit();
        stmUsuario.close();
        stmPersoal.close();
        stmProfesor.close();
        stmSocio.close();
    }

    public void actualizarUsuario(String loginVello,Usuario usuario) throws SQLException {
        Connection con= this.getConexion();
        PreparedStatement stmUsuario=null,stmSocio=null,stmPersoal=null;
        con.setAutoCommit(false);
        stmUsuario= con.prepareStatement("UPDATE usuarios SET login=?,contrasinal=?,nome=?,numTelefono=?,DNI=?,correoElectronico=?,IBAN=? WHERE login=?;");
        stmUsuario.setString(1,usuario.getLogin());
        stmUsuario.setString(2,usuario.getContrasinal());
        stmUsuario.setString(3,usuario.getNome());
        stmUsuario.setString(4,usuario.getNumTelefono());
        stmUsuario.setString(5,usuario.getDNI());
        stmUsuario.setString(6,usuario.getCorreoElectronico());
        stmUsuario.setString(7,usuario.getIBANconta());
        stmUsuario.setString(8,loginVello);
        System.out.println(stmUsuario);
        stmUsuario.executeUpdate();
        if(usuario instanceof Socio) {
            Socio socio=(Socio)usuario;
            stmSocio=con.prepareStatement("UPDATE socios SET dataNacemento=?,dificultades=?,tarifa=? WHERE login=?;");
            stmSocio.setDate(1, socio.getDataNacemento());
            stmSocio.setString(2,socio.getDificultades());
            stmSocio.setInt(3,socio.getTarifa().getCodTarifa());
            stmSocio.setString(4,socio.getLogin());
            System.out.println(stmSocio);

            stmSocio.executeUpdate();
        }else if (usuario instanceof Persoal){
            Persoal persoal=(Persoal)usuario;
            stmPersoal=con.prepareStatement("UPDATE persoal SET dataIncorporacion=?,NUSS=? WHERE login=?;");
            stmPersoal.setDate(1, persoal.getDataIncorporacion());
            stmPersoal.setString(2,persoal.getNUSS());
            stmPersoal.setString(3,persoal.getLogin());
            stmPersoal.executeUpdate();
        }
        con.commit();
        stmUsuario.close();
        stmPersoal.close();
        stmSocio.close();
    }

    public void darBaixaUsuario(String login) throws SQLException {
        Connection con= this.getConexion();
        PreparedStatement stmUsuario;
        stmUsuario= con.prepareStatement("UPDATE usuarios SET dataBaixa=NOW() WHERE login=?");
        stmUsuario.setString(1,login);
        stmUsuario.executeUpdate();
        con.commit();
        stmUsuario.close();
    }



}

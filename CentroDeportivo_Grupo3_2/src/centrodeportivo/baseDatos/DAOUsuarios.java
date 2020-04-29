package centrodeportivo.baseDatos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.tipos.TipoUsuario;
import centrodeportivo.aplicacion.obxectos.usuarios.Persoal;
import centrodeportivo.aplicacion.obxectos.usuarios.Socio;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;

import java.sql.*;

/**
 *
 */
public final class DAOUsuarios extends AbstractDAO {

    protected DAOUsuarios(Connection conexion, FachadaAplicacion fachadaAplicacion) {
        super(conexion,fachadaAplicacion);
    }

    /**
     * Método que nos permitirá levar a cabo a validación dun usuario:
     * @param login O login introducido polo usuario.
     * @param contrasinal O contrasinal introducido polo usuario.
     * @return booleano que nos indica se a validación foi correcta ou non.
     */
    protected boolean validarUsuario(String login, String contrasinal) {
        PreparedStatement stmUsuario = null;
        ResultSet resultValidacion;
        boolean resultado = false;

        try {
            stmUsuario=super.getConexion().prepareStatement("SELECT * FROM usuario WHERE login=? AND contrasinal=? AND dataBaixa IS NULL");
            stmUsuario.setString(1, login);
            stmUsuario.setString(2, contrasinal);
            resultValidacion=stmUsuario.executeQuery();
            resultado = resultValidacion.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                stmUsuario.close();
            } catch (SQLException e){
                System.out.println("Imposible pechar os cursores");
            }
        }
        return resultado;
    }

    protected Usuario consultarUsuario(String login) {
        PreparedStatement stmUsuario = null;
        ResultSet rsUsuarios;
        TipoUsuario tipoUsuario = null;
        Usuario resultado = null;

        try {
            stmUsuario = super.getConexion().prepareStatement("SELECT " +
                    "login," +
                    "(SELECT 1 FROM socio AS s WHERE s.login=u.login) AS eSocio," +
                    "(SELECT 1 FROM persoal AS pe WHERE pe.login=u.login AND profesorActivo=FALSE) AS ePersoal," +
                    "(SELECT 1 FROM persoal AS pe WHERE pe.login=u.login AND profesorActivo=TRUE) AS eProfesor " +
                    "FROM usuario as u WHERE u.login=?"
            );
            stmUsuario.setString(1, login);

            rsUsuarios = stmUsuario.executeQuery();
            rsUsuarios.next();
            if(rsUsuarios.getBoolean("eSocio")) tipoUsuario = TipoUsuario.Socio;
            if(rsUsuarios.getBoolean("eProfesor")) tipoUsuario = TipoUsuario.ProfesorActivo;
            if(rsUsuarios.getBoolean("ePersoal")) tipoUsuario =  TipoUsuario.Persoal;

            if(tipoUsuario==TipoUsuario.Socio) {
                stmUsuario = super.getConexion().prepareStatement("SELECT *,vs.nome AS nomeUsuario FROM vistasocio as vs WHERE u.login=? AND (dataBaixa IS NULL);");
                stmUsuario.setString(1, login);
                if (rsUsuarios.next()) {
                    resultado = new Socio(
                            rsUsuarios.getString("login"),
                            rsUsuarios.getString("contrasinal"),
                            rsUsuarios.getString("DNI"),
                            rsUsuarios.getString("nome"),
                            rsUsuarios.getString("dificultades"),
                            rsUsuarios.getDate("dataNacemento"),
                            rsUsuarios.getString("numTelefono"),
                            rsUsuarios.getString("correoElectronico"),
                            rsUsuarios.getString("iban")
                    );
                }
            }else{
                stmUsuario = super.getConexion().prepareStatement("SELECT * FROM vistapersoal AS vi WHERE vi.login=? AND (dataBaixa IS NULL);");
                stmUsuario.setString(1, login);
                rsUsuarios = stmUsuario.executeQuery();
                if(rsUsuarios.next()){
                    resultado = new Persoal(
                            rsUsuarios.getString("login"),
                            rsUsuarios.getString("contrasinal"),
                            rsUsuarios.getString("DNI"),
                            rsUsuarios.getString("nome"),
                            rsUsuarios.getString("dificultades"),
                            rsUsuarios.getDate("dataNacemento"),
                            rsUsuarios.getString("numTelefono"),
                            rsUsuarios.getString("correoElectronico"),
                            rsUsuarios.getString("iban"),
                            rsUsuarios.getString("nuss"),
                            rsUsuarios.getBoolean("profesoractivo")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                stmUsuario.close();
            } catch (SQLException e){
                System.out.println("Imposible pechar os cursores");
            }
        }
        return resultado;
    }

}

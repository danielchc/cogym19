package centrodeportivo.baseDatos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.tipos.TipoUsuario;
import centrodeportivo.aplicacion.obxectos.usuarios.Persoal;
import centrodeportivo.aplicacion.obxectos.usuarios.Socio;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;

import java.sql.*;

public final class DAOUsuarios extends AbstractDAO {

    protected DAOUsuarios(Connection conexion, FachadaAplicacion fachadaAplicacion) {
        super(conexion,fachadaAplicacion);
    }

    protected boolean validarUsuario(String login,String password) {
        PreparedStatement stmUsuario = null;
        ResultSet resultValidacion;
        boolean resultado = false;

        try {
            stmUsuario=super.getConexion().prepareStatement("SELECT * FROM usuarios WHERE login=? AND contrasinal=? AND dataBaixa IS NULL");
            stmUsuario.setString(1,login);
            stmUsuario.setString(2,password);
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

    protected TipoUsuario consultarTipo(String login) {
        PreparedStatement stmUsuario = null;
        ResultSet rsUsuarios;
        TipoUsuario resultado = null;

        try {
            stmUsuario = super.getConexion().prepareStatement("SELECT " +
                    "login," +
                    "(SELECT 1 FROM socios AS s WHERE s.login=u.login) AS eSocio," +
                    "(SELECT 1 FROM persoal AS pe WHERE pe.login=u.login AND profesorActivo=FALSE) AS ePersoal," +
                    "(SELECT 1 FROM persoal AS pe WHERE pe.login=u.login AND profesorActivo=TRUE) AS eProfesor " +
                    "FROM usuarios as u WHERE u.login=?"
            );
            stmUsuario.setString(1,login);

            rsUsuarios = stmUsuario.executeQuery();
            rsUsuarios.next();
            if(rsUsuarios.getBoolean("eSocio")) resultado = TipoUsuario.Socio;
            if(rsUsuarios.getBoolean("eProfesor")) resultado = TipoUsuario.Profesor;
            if(rsUsuarios.getBoolean("ePersoal")) resultado =  TipoUsuario.Persoal;
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
        TipoUsuario tipoUsuario=consultarTipo(login);
        Usuario resultado = null;

        try {
            if(tipoUsuario==TipoUsuario.Socio) {
                stmUsuario = super.getConexion().prepareStatement("SELECT *,vs.nome AS nomeUsuario FROM vistasocios as vs WHERE u.login=? AND (dataBaixa IS NULL);");
                stmUsuario.setString(1, login);
                rsUsuarios = stmUsuario.executeQuery();
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

package centrodeportivo.baseDatos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.actividades.Actividade;
import centrodeportivo.aplicacion.obxectos.actividades.Curso;
import centrodeportivo.aplicacion.obxectos.actividades.TipoActividade;
import centrodeportivo.aplicacion.obxectos.tipos.TipoUsuario;
import centrodeportivo.aplicacion.obxectos.usuarios.Persoal;
import centrodeportivo.aplicacion.obxectos.usuarios.Profesor;
import centrodeportivo.aplicacion.obxectos.usuarios.Socio;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;

import java.sql.*;
import java.util.ArrayList;

public final class DAOUsuarios extends AbstractDAO {

    protected DAOUsuarios(Connection conexion, FachadaAplicacion fachadaAplicacion) {
        super(conexion,fachadaAplicacion);
    }

    protected boolean validarUsuario(String login,String password) {
        PreparedStatement stmUsuario = null;
        ResultSet resultValidacion;
        try {
            stmUsuario=super.getConexion().prepareStatement("SELECT * FROM usuarios WHERE login=? AND contrasinal=? AND dataBaixa IS NULL");
            stmUsuario.setString(1,login);
            stmUsuario.setString(2,password);
            resultValidacion=stmUsuario.executeQuery();
            return resultValidacion.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                stmUsuario.close();
            } catch (SQLException e){
                System.out.println("Imposible cerrar cursores");
            }
        }
        return false;
    }

    protected TipoUsuario consultarTipo(String login) {
        PreparedStatement stmUsuario = null;
        ResultSet rsUsuarios;

        try {
            stmUsuario = super.getConexion().prepareStatement("SELECT " +
                    "login," +
                    "(SELECT 1 FROM socios AS s WHERE s.login=u.login) AS eSocio," +
                    "(SELECT 1 FROM persoal AS pe WHERE pe.login=u.login AND u.login NOT IN (SELECT login FROM profesores)) AS ePersoal," +
                    "(SELECT 1 FROM profesores AS pf WHERE pf.login=u.login AND u.login IN (SELECT login FROM profesores)) AS eProfesor " +
                    "FROM usuarios as u WHERE u.login=?"
            );
            stmUsuario.setString(1,login);

            rsUsuarios = stmUsuario.executeQuery();
            rsUsuarios.next();
            if(rsUsuarios.getBoolean("eSocio"))return TipoUsuario.Socio;
            if(rsUsuarios.getBoolean("eProfesor"))return TipoUsuario.Profesor;
            if(rsUsuarios.getBoolean("ePersoal"))return TipoUsuario.Persoal;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                stmUsuario.close();
            } catch (SQLException e){
                System.out.println("Imposible cerrar cursores");
            }
        }

        return null;
    }

    protected Usuario consultarUsuario(String login) {
        PreparedStatement stmUsuario = null;
        ResultSet rsUsuarios;
        TipoUsuario tipoUsuario=consultarTipo(login);

        try {
            if(tipoUsuario==TipoUsuario.Socio) {
                stmUsuario = super.getConexion().prepareStatement("SELECT *,u.nome AS nomeUsuario,t.nome AS nomeTarifa FROM usuarios AS u NATURAL JOIN socios AS s JOIN tarifas AS t ON s.tarifa=t.codTarifa WHERE u.login=? AND (dataBaixa IS NULL);");
                stmUsuario.setString(1, login);
                rsUsuarios = stmUsuario.executeQuery();
                if (rsUsuarios.next()) {
                    return new Socio(
                            rsUsuarios.getString("login"),
                            rsUsuarios.getString("contrasinal"),
                            rsUsuarios.getString("nomeUsuario"),
                            rsUsuarios.getString("numTelefono"),
                            rsUsuarios.getString("DNI"),
                            rsUsuarios.getString("correoElectronico"),
                            rsUsuarios.getString("iban"),
                            rsUsuarios.getDate("dataAlta"),
                            rsUsuarios.getDate("dataNacemento"),
                            rsUsuarios.getString("dificultades")
                    );
                }
            }else{
                stmUsuario = super.getConexion().prepareStatement("SELECT * FROM usuarios AS u JOIN persoal AS pe ON pe.login=u.login WHERE u.login=? AND (dataBaixa IS NULL);");
                stmUsuario.setString(1, login);
                rsUsuarios = stmUsuario.executeQuery();
                if(tipoUsuario==TipoUsuario.Profesor){
                    if (rsUsuarios.next()) {
                        return new Profesor(
                                rsUsuarios.getString("login"),
                                rsUsuarios.getString("contrasinal"),
                                rsUsuarios.getString("nome"),
                                rsUsuarios.getString("numTelefono"),
                                rsUsuarios.getString("DNI"),
                                rsUsuarios.getString("correoElectronico"),
                                rsUsuarios.getString("iban"),
                                rsUsuarios.getString("NUSS")
                        ) ;
                    }
                }else if(tipoUsuario==TipoUsuario.Persoal){
                    if (rsUsuarios.next()) {
                        return new Persoal(
                                rsUsuarios.getString("login"),
                                rsUsuarios.getString("contrasinal"),
                                rsUsuarios.getString("nome"),
                                rsUsuarios.getString("numTelefono"),
                                rsUsuarios.getString("DNI"),
                                rsUsuarios.getString("correoElectronico"),
                                rsUsuarios.getString("iban"),
                                rsUsuarios.getString("NUSS")
                        ) ;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                stmUsuario.close();
            } catch (SQLException e){
                System.out.println("Imposible cerrar cursores");
            }
        }
        return null;
    }


    /*
    protected Cuota consultarCuota(String login){
        PreparedStatement stm = null;
        ResultSet resultSet;
        Socio socio;
        Tarifa tarifa;
        ArrayList<Actividade> actividadesMes=new ArrayList<>();
        ArrayList<Curso> cursosMes=new ArrayList<>();
        float prezoActividadesExtra=0.0f;
        float totalActividades=0.0f;
        float totalCursos=0.0f;
        float totalPrezo=0.0f;

        socio=(Socio)this.consultarUsuario(login);
        tarifa=socio.getTarifa();

        try {
            stm = super.getConexion().prepareStatement(
                    "SELECT * " +
                            "FROM realizarActividades NATURAL JOIN actividades " +
                            "WHERE dataActividade BETWEEN to_date(format('%s-%s-%s',EXTRACT(YEAR from NOW()),EXTRACT(MONTH from NOW()),'01'),'YYYY-MM-DD') AND NOW() " +
                            "AND usuario=? AND curso IS NULL;"
            );
            stm.setString(1, login);
            resultSet = stm.executeQuery();
            while(resultSet.next()){
                actividadesMes.add(new Actividade(
                        resultSet.getTimestamp("dataActividade"),
                        resultSet.getInt("area"),
                        resultSet.getInt("instalacion"),
                        resultSet.getInt("tipoActividade"),
                        resultSet.getInt("curso"),
                        resultSet.getString("profesor"),
                        resultSet.getString("nome"),
                        resultSet.getFloat("duracion")
                ));
            }

            stm = super.getConexion().prepareStatement(
                    "SELECT * " +
                            "FROM cursos " +
                            "WHERE codCurso IN (SELECT curso FROM realizarCursos WHERE usuario=?) " +
                            "AND codCurso IN (SELECT curso FROM actividades GROUP BY curso HAVING MAX(dataActividade)>=NOW());"
            );
            stm.setString(1, login);
            resultSet = stm.executeQuery();
            while(resultSet.next()){
                cursosMes.add(new Curso(
                        resultSet.getInt("codCurso"),
                        resultSet.getString("nome"),
                        resultSet.getString("descricion"),
                        resultSet.getFloat("prezo")
                ));
            }

            if(actividadesMes.size()>tarifa.getMaxActividades()){
                prezoActividadesExtra=tarifa.getPrezoExtras()*(actividadesMes.size()-tarifa.getMaxActividades());
            }
            totalActividades=tarifa.getPrezoBase()+prezoActividadesExtra;
            for(Curso c:cursosMes){
                totalCursos+=c.getPrezo();
            }
            totalPrezo=totalActividades+totalCursos;

            return new Cuota(socio,tarifa,prezoActividadesExtra,totalActividades,totalCursos,totalPrezo,actividadesMes,cursosMes);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                stm.close();
            } catch (SQLException e){
                System.out.println("Imposible cerrar cursores");
            }
        }
        return null;
    }*/


}

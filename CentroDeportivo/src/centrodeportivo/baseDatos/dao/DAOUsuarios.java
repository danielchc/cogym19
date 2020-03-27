package centrodeportivo.baseDatos.dao;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.actividades.TipoActividade;
import centrodeportivo.aplicacion.obxectos.tarifas.Cuota;
import centrodeportivo.aplicacion.obxectos.tarifas.Tarifa;
import centrodeportivo.aplicacion.obxectos.usuarios.Persoal;
import centrodeportivo.aplicacion.obxectos.usuarios.Profesor;
import centrodeportivo.aplicacion.obxectos.usuarios.Socio;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;

import java.sql.*;
import java.util.ArrayList;

public final class DAOUsuarios extends AbstractDAO {
    private Connection con;

    public DAOUsuarios(Connection conexion, FachadaAplicacion fachadaAplicacion) {
        super(conexion,fachadaAplicacion);
        this.con=conexion;
    }

    public boolean existeUsuario(String login) throws SQLException {
        PreparedStatement stmUsuario = null;
        ResultSet resultValidacion;
        stmUsuario=con.prepareStatement("SELECT * FROM usuarios WHERE login=?");
        stmUsuario.setString(1,login);
        resultValidacion=stmUsuario.executeQuery();
        return resultValidacion.next();
    }

    public boolean validarUsuario(String login,String password) throws SQLException {
        PreparedStatement stmUsuario = null;
        ResultSet resultValidacion;
        stmUsuario=con.prepareStatement("SELECT * FROM usuarios WHERE login=? AND contrasinal=?");
        stmUsuario.setString(1,login);
        stmUsuario.setString(2,password);
        resultValidacion=stmUsuario.executeQuery();
        return resultValidacion.next();
    }

    public void insertarUsuario(Usuario usuario) throws SQLException {
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
            stmPersoal=con.prepareStatement("INSERT INTO persoal (login,NUSS) VALUES (?,?);");
            stmPersoal.setString(1,persoal.getLogin());
            stmPersoal.setString(2,persoal.getNUSS());
            stmPersoal.executeUpdate();
            if(usuario instanceof Profesor){
                Profesor profesor=(Profesor)usuario;
                stmProfesor=con.prepareStatement("INSERT INTO profesores (login) VALUES (?);");
                stmProfesor.setString(1,profesor.getLogin());
                stmProfesor.executeUpdate();
            }
        }
        con.commit();
    }

    public void actualizarUsuario(String loginVello,Usuario usuario) throws SQLException {
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
        stmUsuario.executeUpdate();
        if(usuario instanceof Socio) {
            Socio socio=(Socio)usuario;
            stmSocio=con.prepareStatement("UPDATE socios SET dataNacemento=?,dificultades=?,tarifa=? WHERE login=?;");
            stmSocio.setDate(1, socio.getDataNacemento());
            stmSocio.setString(2,socio.getDificultades());
            stmSocio.setInt(3,socio.getTarifa().getCodTarifa());
            stmSocio.setString(4,socio.getLogin());
            stmSocio.executeUpdate();
        }else if (usuario instanceof Persoal){
            Persoal persoal=(Persoal)usuario;
            stmPersoal=con.prepareStatement("UPDATE persoal SET NUSS=? WHERE login=?;");
            stmPersoal.setString(1,persoal.getNUSS());
            stmPersoal.setString(2,persoal.getLogin());
            stmPersoal.executeUpdate();
        }
        con.commit();
        stmUsuario.close();
        stmPersoal.close();
        stmSocio.close();
    }

    public void darBaixaUsuario(String login) throws SQLException {
        PreparedStatement stmUsuario;
        stmUsuario= con.prepareStatement("UPDATE usuarios SET dataBaixa=NOW() WHERE login=?");
        stmUsuario.setString(1,login);
        stmUsuario.executeUpdate();
        con.commit();
        stmUsuario.close();
    }

    public ArrayList<Socio> listarSocios() throws SQLException {
        return buscarSocios("","");
    }

    public ArrayList<Persoal> listarPersoal() throws SQLException {
        return buscarPersoal("","");
    }

    public ArrayList<Profesor> listarProfesores() throws SQLException {
        return buscarProfesores("","");
    }

    public ArrayList<Usuario> listarUsuarios() throws SQLException {
        return buscarUsuarios("","");
    }

    public ArrayList<Socio> buscarSocios(String login,String nome) throws SQLException {
        PreparedStatement stmUsuario = null;
        ArrayList<Socio> socios=new ArrayList<Socio>();
        ResultSet rsUsuarios;
        stmUsuario = con.prepareStatement("SELECT * FROM usuarios NATURAL JOIN socios WHERE login LIKE ? OR nome LIKE ?;");
        stmUsuario.setString(1, "%"+login+"%");
        stmUsuario.setString(2, "%"+nome+"%");
        rsUsuarios = stmUsuario.executeQuery();
        while (rsUsuarios.next()) {
            socios.add(new Socio(
               rsUsuarios.getString("login"),
               rsUsuarios.getString("contrasinal"),
               rsUsuarios.getString("nome"),
               rsUsuarios.getString("numTelefono"),
               rsUsuarios.getString("DNI"),
               rsUsuarios.getString("correoElectronico"),
               rsUsuarios.getString("iban"),
               rsUsuarios.getDate("dataAlta"),
               rsUsuarios.getDate("dataNacemento"),
               rsUsuarios.getString("dificultades")
            ));
        }
        return socios;
    }

    public ArrayList<Persoal> buscarPersoal(String login,String nome) throws SQLException {
        PreparedStatement stmUsuario = null;
        ArrayList<Persoal> persoal=new ArrayList<Persoal>();
        ResultSet rsUsuarios;
        stmUsuario = con.prepareStatement("SELECT * FROM usuarios NATURAL JOIN persoal WHERE login NOT IN (SELECT login FROM profesores) AND (login LIKE ? OR nome LIKE ?);");
        stmUsuario.setString(1, "%"+login+"%");
        stmUsuario.setString(2, "%"+nome+"%");
        rsUsuarios = stmUsuario.executeQuery();
        while (rsUsuarios.next()) {
            persoal.add(new Persoal(
               rsUsuarios.getString("login"),
               rsUsuarios.getString("contrasinal"),
               rsUsuarios.getString("nome"),
               rsUsuarios.getString("numTelefono"),
               rsUsuarios.getString("DNI"),
               rsUsuarios.getString("correoElectronico"),
               rsUsuarios.getString("iban"),
               rsUsuarios.getString("NUSS")
            ));
        }
        return persoal;
    }

    public ArrayList<Profesor> buscarProfesores(String login,String nome) throws SQLException {
        PreparedStatement stmUsuario = null;
        ArrayList<Profesor> profesores=new ArrayList<>();
        ResultSet rsUsuarios;
        stmUsuario = con.prepareStatement("SELECT * FROM usuarios NATURAL JOIN persoal WHERE login IN (SELECT login FROM profesores) AND (login LIKE ? OR nome LIKE ?);");
        stmUsuario.setString(1, "%"+login+"%");
        stmUsuario.setString(2, "%"+nome+"%");
        rsUsuarios = stmUsuario.executeQuery();
        while (rsUsuarios.next()) {
            profesores.add(new Profesor(
                    rsUsuarios.getString("login"),
                    rsUsuarios.getString("contrasinal"),
                    rsUsuarios.getString("nome"),
                    rsUsuarios.getString("numTelefono"),
                    rsUsuarios.getString("DNI"),
                    rsUsuarios.getString("correoElectronico"),
                    rsUsuarios.getString("iban"),
                    rsUsuarios.getString("NUSS")
            ));
        }
        return profesores;
    }

    public ArrayList<Usuario> buscarUsuarios(String login,String nome) throws SQLException{
        PreparedStatement stmUsuario = null;
        ArrayList<Usuario> usuarios=new ArrayList<>();
        ResultSet rsUsuarios;
        stmUsuario = con.prepareStatement("SELECT * FROM usuarios WHERE login LIKE ? OR nome LIKE ?;");
        stmUsuario.setString(1, "%"+login+"%");
        stmUsuario.setString(2, "%"+nome+"%");
        rsUsuarios = stmUsuario.executeQuery();
        while (rsUsuarios.next()) {
            usuarios.add(new Usuario(
                    rsUsuarios.getString("login"),
                    rsUsuarios.getString("contrasinal"),
                    rsUsuarios.getString("nome"),
                    rsUsuarios.getString("numTelefono"),
                    rsUsuarios.getString("DNI"),
                    rsUsuarios.getString("correoElectronico"),
                    rsUsuarios.getString("iban"),
                    rsUsuarios.getDate("dataAlta")
            ));
        }
        return usuarios;
    }

    public Socio buscarSocio(String login) throws SQLException{
        PreparedStatement stmUsuario = null;
        ResultSet rsUsuarios;
        stmUsuario = con.prepareStatement("SELECT *,u.nome AS nomeUsuario,t.nome AS nomeTarifa FROM usuarios AS u NATURAL JOIN socios AS s JOIN tarifas AS t ON s.tarifa=t.codTarifa WHERE u.login=? ;");
        stmUsuario.setString(1, login);
        rsUsuarios = stmUsuario.executeQuery();
        if(rsUsuarios.next()) {
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
                    rsUsuarios.getString("dificultades"),
                    new Tarifa(
                            rsUsuarios.getInt("tarifa"),
                            rsUsuarios.getString("nomeTarifa"),
                            rsUsuarios.getInt("maxActividades"),
                            rsUsuarios.getFloat("precioBase"),
                            rsUsuarios.getFloat("precioExtra")
                    )
            );
        }
        return null;
    }

    public void engadirCapadidade(String login, TipoActividade tipoActividade) throws SQLException{
        PreparedStatement stmAct=null;
        stmAct= con.prepareStatement("INSERT INTO estarCapacitado (tipoActividade,profesor)  VALUES (?,?);");
        stmAct.setInt(1,tipoActividade.getCodTipoActividade());
        stmAct.setString(2,login);
        stmAct.executeUpdate();
        con.commit();
    }

    public void eliminarCapacidade(String login, TipoActividade tipoActividade) throws SQLException{
        PreparedStatement stmAct=null;
        stmAct= con.prepareStatement("DELETE FROM estarCapacitado WHERE tipoActividade=? AND profesor=?;");
        stmAct.setInt(1,tipoActividade.getCodTipoActividade());
        stmAct.setString(2,login);
        stmAct.executeUpdate();
        con.commit();
    }

    /*
    public Cuota consultarCuota(String login) throws SQLException{
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

        socio=this.buscarSocio(login);
        tarifa=socio.getTarifa();

        stm = con.prepareStatement(
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

        stm = con.prepareStatement(
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
    }*/


}

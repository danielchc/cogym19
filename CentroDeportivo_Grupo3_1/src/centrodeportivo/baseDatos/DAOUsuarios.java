package centrodeportivo.baseDatos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.RexistroFisioloxico;
import centrodeportivo.aplicacion.obxectos.actividades.Actividade;
import centrodeportivo.aplicacion.obxectos.actividades.Curso;
import centrodeportivo.aplicacion.obxectos.actividades.TipoActividade;
import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.aplicacion.obxectos.tarifas.Cuota;
import centrodeportivo.aplicacion.obxectos.tarifas.Tarifa;
import centrodeportivo.aplicacion.obxectos.tipos.ContasPersoa;
import centrodeportivo.aplicacion.obxectos.tipos.TipoUsuario;
import centrodeportivo.aplicacion.obxectos.usuarios.PersoaFisica;
import centrodeportivo.aplicacion.obxectos.usuarios.Persoal;
import centrodeportivo.aplicacion.obxectos.usuarios.Socio;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;

import java.sql.*;
import java.util.ArrayList;

public final class DAOUsuarios extends AbstractDAO {

    protected DAOUsuarios(Connection conexion, FachadaAplicacion fachadaAplicacion) {
        super(conexion,fachadaAplicacion);
    }

    protected boolean existeUsuario(String login){
        PreparedStatement stmUsuario = null;
        ResultSet resultValidacion;
        try {
            stmUsuario=super.getConexion().prepareStatement("SELECT * FROM usuario WHERE login=?");
            stmUsuario.setString(1,login);
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

    protected boolean existeDNI(String dni) {
        PreparedStatement stmUsuario = null;
        ResultSet resultValidacion;
        try {
            stmUsuario=super.getConexion().prepareStatement("SELECT * FROM persoaFisica WHERE dni=?");
            stmUsuario.setString(1,dni);
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

    protected boolean existeNUSS(String nuss) {
        PreparedStatement stmUsuario = null;
        ResultSet resultValidacion;
        try {
            stmUsuario=super.getConexion().prepareStatement("SELECT * FROM usuario NATURAL JOIN persoal WHERE nuss=?");
            stmUsuario.setString(1,nuss);
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

    protected ContasPersoa contasPersoaFisica(String dni){
        PreparedStatement stmUsuario = null;
        ResultSet resultValidacion;
        try {
            stmUsuario=super.getConexion().prepareStatement("SELECT * FROM persoaFisica WHERE dni=UPPER(?)");
            stmUsuario.setString(1,dni);
            resultValidacion=stmUsuario.executeQuery();
            if(resultValidacion.next()){
                if(resultValidacion.getString("usuarioSocio")!=null && resultValidacion.getString("usuarioPersoal")!=null) return ContasPersoa.Ambas;
                else if(resultValidacion.getString("usuarioSocio")==null && resultValidacion.getString("usuarioPersoal")!=null) return ContasPersoa.SoloPersoal;
                else if(resultValidacion.getString("usuarioSocio")!=null && resultValidacion.getString("usuarioPersoal")==null) return ContasPersoa.SoloSocio;
            }else{
                return ContasPersoa.Ningunha;
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
        return ContasPersoa.Ningunha;
    }

    protected boolean validarUsuario(String login,String password) {
        PreparedStatement stmUsuario = null;
        ResultSet resultValidacion;
        try {
            stmUsuario=super.getConexion().prepareStatement("SELECT * FROM usuario WHERE login=? AND contrasinal=? AND dataBaixa IS NULL");
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


    protected PersoaFisica consultarPersoaFisica(String DNI){
        PreparedStatement stmPersoa = null;
        ResultSet resultPersoa;
        try{
            stmPersoa=super.getConexion().prepareStatement("SELECT * FROM persoafisica WHERE DNI=?");
            stmPersoa.setString(1,DNI);
            resultPersoa=stmPersoa.executeQuery();
            if(resultPersoa.next()){
                return new PersoaFisica(
                        resultPersoa.getString("DNI"),
                        resultPersoa.getString("dificultades"),
                        resultPersoa.getString("dificultades"),
                        resultPersoa.getDate("datanacemento")
                );
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                stmPersoa.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
        return null;

    }
    protected void insertarUsuario(Usuario usuario) {
        /*PreparedStatement stmUsuario=null,stmSocio=null,stmPersoal=null,stmProfesor=null;
        try {
            stmUsuario= super.getConexion().prepareStatement("INSERT INTO usuarios (login,contrasinal,nome,numTelefono,DNI,correoElectronico,IBAN)  VALUES (?,?,?,?,?,?,?);");
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
                stmSocio=super.getConexion().prepareStatement("INSERT INTO socios (login,dataNacemento,dificultades,tarifa) values (?,?,?,?);");
                stmSocio.setString(1,socio.getLogin());
                stmSocio.setDate(2, socio.getDataNacemento());
                stmSocio.setString(3,socio.getDificultades());
                stmSocio.setInt(4,socio.getTarifa().getCodTarifa());
                stmSocio.executeUpdate();
            }else if (usuario instanceof Persoal){
                Persoal persoal=(Persoal)usuario;
                stmPersoal=super.getConexion().prepareStatement("INSERT INTO persoal (login,NUSS) VALUES (?,?);");
                stmPersoal.setString(1,persoal.getLogin());
                stmPersoal.setString(2,persoal.getNUSS());
                stmPersoal.executeUpdate();
                if(usuario instanceof Profesor){
                    Profesor profesor=(Profesor)usuario;
                    stmProfesor=super.getConexion().prepareStatement("INSERT INTO profesores (login) VALUES (?);");
                    stmProfesor.setString(1,profesor.getLogin());
                    stmProfesor.executeUpdate();
                }
            }
            super.getConexion().commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if(stmUsuario!=null)stmUsuario.close();
                if(stmPersoal!=null)stmPersoal.close();
                if(stmProfesor!=null)stmProfesor.close();
                if(stmSocio!=null)stmSocio.close();
            } catch (SQLException e){
                System.out.println("Imposible cerrar cursores");
            }
        }*/
    }

    protected void actualizarUsuario(String loginVello,Usuario usuario) {
        /*PreparedStatement stmUsuario=null,stmSocio=null,stmPersoal=null;

        try {
            super.getConexion().setAutoCommit(false);
            stmUsuario= super.getConexion().prepareStatement("UPDATE usuarios SET login=?,contrasinal=?,nome=?,numTelefono=?,DNI=?,correoElectronico=?,IBAN=? WHERE login=?;");
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
                stmSocio=super.getConexion().prepareStatement("UPDATE socios SET dataNacemento=?,dificultades=?,tarifa=? WHERE login=?;");
                stmSocio.setDate(1, socio.getDataNacemento());
                stmSocio.setString(2,socio.getDificultades());
                stmSocio.setInt(3,socio.getTarifa().getCodTarifa());
                stmSocio.setString(4,socio.getLogin());
                stmSocio.executeUpdate();
            }else if (usuario instanceof Persoal){
                Persoal persoal=(Persoal)usuario;
                stmPersoal=super.getConexion().prepareStatement("UPDATE persoal SET NUSS=? WHERE login=?;");
                stmPersoal.setString(1,persoal.getNUSS());
                stmPersoal.setString(2,persoal.getLogin());
                stmPersoal.executeUpdate();
            }
            super.getConexion().commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                assert stmUsuario != null;
                stmUsuario.close();
                assert stmPersoal != null;
                stmPersoal.close();
                assert stmSocio != null;
                stmSocio.close();
            } catch (SQLException e){
                System.out.println("Imposible cerrar cursores");
            }
        }*/
    }

    protected void darBaixaUsuario(String login) {
        PreparedStatement stmUsuario=null;
        try {
            stmUsuario= super.getConexion().prepareStatement("UPDATE usuario SET dataBaixa=NOW() WHERE login=?");
            stmUsuario.setString(1,login);
            stmUsuario.executeUpdate();
            super.getConexion().commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                stmUsuario.close();
            } catch (SQLException e){
                System.out.println("Imposible cerrar cursores");
            }
        }
    }

    protected void engadirCapadidade(String login, TipoActividade tipoActividade) {
        PreparedStatement stmAct=null;

        try {
            stmAct= super.getConexion().prepareStatement("INSERT INTO estarCapacitado (tipoActividade,profesor)  VALUES (?,?);");
            stmAct.setInt(1,tipoActividade.getCodTipoActividade());
            stmAct.setString(2,login);
            stmAct.executeUpdate();
            super.getConexion().commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                stmAct.close();
            } catch (SQLException e){
                System.out.println("Imposible cerrar cursores");
            }
        }
    }

    protected void eliminarCapacidade(String login, TipoActividade tipoActividade) {
        PreparedStatement stmAct=null;

        try {
            stmAct= super.getConexion().prepareStatement("DELETE FROM estarCapacitado WHERE tipoActividade=? AND profesor=?;");
            stmAct.setInt(1,tipoActividade.getCodTipoActividade());
            stmAct.setString(2,login);
            stmAct.executeUpdate();
            super.getConexion().commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                stmAct.close();
            } catch (SQLException e){
                System.out.println("Imposible cerrar cursores");
            }
        }
    }

    protected TipoUsuario consultarTipo(String login) {
        PreparedStatement stmUsuario = null;
        ResultSet rsUsuarios;

        try {
            stmUsuario = super.getConexion().prepareStatement("SELECT " +
                    "login," +
                    "(SELECT 1 FROM socio AS s WHERE s.login=u.login) AS eSocio," +
                    "(SELECT 1 FROM persoal AS pe WHERE pe.login=u.login AND profesoractivo=FALSE) AS ePersoal, " +
                    "(SELECT 1 FROM persoal AS pe WHERE pe.login=u.login AND profesoractivo=TRUE) AS eProfesor  " +
                    "FROM usuario as u WHERE u.login=?"
            );
            stmUsuario.setString(1,login);

            rsUsuarios = stmUsuario.executeQuery();
            rsUsuarios.next();
            if(rsUsuarios.getBoolean("eSocio")) return TipoUsuario.Socio;
            if(rsUsuarios.getBoolean("ePersoal")) return TipoUsuario.Persoal;
            if(rsUsuarios.getBoolean("eProfesor")) return TipoUsuario.Profesor;
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

    protected ArrayList<Usuario> buscarUsuarios(String login,String nome,TipoUsuario filtroTipo,boolean usuariosDeBaixa) {
        String usBaixa="";
        if(!usuariosDeBaixa)usBaixa=" AND (dataBaixa IS NULL) ";
        PreparedStatement stmUsuario = null;
        ArrayList<Usuario> usuarios=new ArrayList<Usuario>();
        ResultSet rsUsuarios;

        try {
            if(filtroTipo==TipoUsuario.Socio || filtroTipo==TipoUsuario.Todos) {
                stmUsuario = super.getConexion().prepareStatement("SELECT *,vs.nome AS nomeUsuario,t.nome AS nomeTarifa FROM vistasocio AS vs JOIN tarifa AS t ON vs.tarifa=t.codTarifa  " +
                        "WHERE (LOWER(vs.login) LIKE LOWER(?) AND LOWER(vs.nome) LIKE LOWER(?))  "+ usBaixa +
                        "ORDER BY login ASC;"
                );
                stmUsuario.setString(1, "%"+login+"%");
                stmUsuario.setString(2, "%"+nome+"%");
                rsUsuarios = stmUsuario.executeQuery();
                while (rsUsuarios.next()) {
                    usuarios.add(new Socio(
                            rsUsuarios.getString("login"),
                            rsUsuarios.getString("contrasinal"),
                            rsUsuarios.getString("DNI"),
                            rsUsuarios.getString("nome"),
                            rsUsuarios.getString("dificultades"),
                            rsUsuarios.getDate("dataNacemento"),
                            rsUsuarios.getString("numTelefono"),
                            rsUsuarios.getString("correoElectronico"),
                            rsUsuarios.getString("iban"),
                            new Tarifa(
                                    rsUsuarios.getInt("tarifa"),
                                    rsUsuarios.getString("nomeTarifa"),
                                    rsUsuarios.getInt("maxActividades"),
                                    rsUsuarios.getFloat("precioBase"),
                                    rsUsuarios.getFloat("precioExtra")
                            )
                    ));
                }
            }
            if (filtroTipo==TipoUsuario.Profesor || filtroTipo== TipoUsuario.Persoal || filtroTipo==TipoUsuario.Todos){
                String conds="";
                if(filtroTipo==TipoUsuario.Profesor)conds="AND profesoractivo=TRUE ";
                if(filtroTipo==TipoUsuario.Persoal)conds="AND profesoractivo=FALSE ";

                stmUsuario = super.getConexion().prepareStatement("SELECT * FROM vistapersoal AS vp  " +
                                "WHERE (LOWER(vp.login) LIKE LOWER(?) AND LOWER(vp.nome) LIKE LOWER(?)) "+ conds + usBaixa +
                                "ORDER BY login ASC;"
                );
                stmUsuario.setString(1, "%"+login+"%");
                stmUsuario.setString(2, "%"+nome+"%");
                rsUsuarios = stmUsuario.executeQuery();
                while (rsUsuarios.next()) {
                    usuarios.add(new Persoal(
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
                    ));
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
        return usuarios;
    }

    protected Usuario consultarUsuario(String login) {
        PreparedStatement stmUsuario = null;
        ResultSet rsUsuarios;
        TipoUsuario tipoUsuario=consultarTipo(login);
        try {
            if(tipoUsuario==TipoUsuario.Socio) {
                stmUsuario = super.getConexion().prepareStatement(
                        "SELECT *, vs.nome AS nomeUsuario,t.nome AS nomeTarifa FROM vistasocio AS vs JOIN tarifa AS t ON vs.tarifa=t.codTarifa WHERE vs.login=? AND (vs.dataBaixa IS NULL);"
                );
                stmUsuario.setString(1, login);
                rsUsuarios = stmUsuario.executeQuery();
                if (rsUsuarios.next()) {
                    return new Socio(
                            rsUsuarios.getString("login"),
                            rsUsuarios.getString("contrasinal"),
                            rsUsuarios.getString("DNI"),
                            rsUsuarios.getString("nome"),
                            rsUsuarios.getString("dificultades"),
                            rsUsuarios.getDate("dataNacemento"),
                            rsUsuarios.getString("numTelefono"),
                            rsUsuarios.getString("correoElectronico"),
                            rsUsuarios.getString("iban"),
                            new Tarifa(
                                    rsUsuarios.getInt("tarifa"),
                                    rsUsuarios.getString("nomeTarifa"),
                                    rsUsuarios.getInt("maxActividades"),
                                    rsUsuarios.getFloat("precioBase"),
                                    rsUsuarios.getFloat("precioExtra")
                            )
                    );
                }
            }else{
                stmUsuario = super.getConexion().prepareStatement("SELECT * FROM vistapersoal AS vp WHERE vp.login=? AND (dataBaixa IS NULL);");
                stmUsuario.setString(1, login);
                rsUsuarios = stmUsuario.executeQuery();
                if (rsUsuarios.next()) {
                    return new Persoal(
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
                System.out.println("Imposible cerrar cursores");
            }
        }
        return null;
}

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
/*
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
                        new Area(resultSet.getInt("area"),new Instalacion(resultSet.getInt("instalacion"))),
                        new TipoActividade(resultSet.getInt("tipoActividade")),
                        new Curso(resultSet.getInt("curso")),
                        resultSet.getString("nome"),
                        resultSet.getFloat("duracion"),
                        new Profesor(resultSet.getString("profesor"))
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
        }*/
        return null;
    }

    protected void insertarRexistro(RexistroFisioloxico rexistroFisioloxico){
        PreparedStatement stm=null;

        try {
            stm= super.getConexion().prepareStatement(
                    "INSERT INTO rexistroFisioloxico (usuario, dataMarca, peso, altura, bfp, tensionAlta, tensionBaixa, ppm, comentario)  "+
                        "VALUES (?,NOW(),?,?,?,?,?,?,?);"
            );
            stm.setString(1,rexistroFisioloxico.getSocio().getLogin());
            stm.setFloat(2,rexistroFisioloxico.getPeso());
            stm.setFloat(3,rexistroFisioloxico.getAltura());
            stm.setObject(4,rexistroFisioloxico.getBfp());
            stm.setObject(5,rexistroFisioloxico.getTensionAlta());
            stm.setObject(6,rexistroFisioloxico.getTensionBaixa());
            stm.setObject(7,rexistroFisioloxico.getPpm());
            stm.setString(8,rexistroFisioloxico.getComentario());
            stm.executeUpdate();
            super.getConexion().commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                stm.close();
            } catch (SQLException e){
                System.out.println("Imposible cerrar cursores");
            }
        }
    }

    protected void eliminarRexistro(RexistroFisioloxico rexistroFisioloxico){
        PreparedStatement stm=null;

        try {
            stm= super.getConexion().prepareStatement(
                    "DELETE FROM rexistroFisioloxico WHERE usuario=? AND dataMarca=?;"
            );
            stm.setString(1,rexistroFisioloxico.getSocio().getLogin());
            stm.setTimestamp(2,rexistroFisioloxico.getData());
            stm.executeUpdate();
            super.getConexion().commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                stm.close();
            } catch (SQLException e){
                System.out.println("Imposible cerrar cursores");
            }
        }
    }

    protected ArrayList<RexistroFisioloxico> listarRexistros(String login){
        PreparedStatement stm = null;
        ResultSet resultSet;
        ArrayList<RexistroFisioloxico> rexistros=new ArrayList<>();

        try {
            stm = super.getConexion().prepareStatement(
                    "SELECT * " +
                            "FROM rexistroFisioloxico " +
                            "WHERE socio=? "+
                            "ORDER BY dataMarca DESC;"
            );
            stm.setString(1, login);
            resultSet = stm.executeQuery();
            while(resultSet.next()){
                rexistros.add(new RexistroFisioloxico(
                    resultSet.getTimestamp("dataMarca"),
                    new Socio(login),
                    resultSet.getFloat("peso"),
                    resultSet.getFloat("altura"),
                    resultSet.getFloat("bfp"),
                    resultSet.getInt("tensionAlta"),
                    resultSet.getInt("tensionBaixa"),
                    resultSet.getInt("ppm"),
                    resultSet.getString("comentario")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                stm.close();
            } catch (SQLException e){
                System.out.println("Imposible cerrar cursores");
            }
        }
        return rexistros;
    }


}

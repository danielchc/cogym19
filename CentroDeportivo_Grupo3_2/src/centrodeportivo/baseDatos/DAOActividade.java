package centrodeportivo.baseDatos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.Mensaxe;
import centrodeportivo.aplicacion.obxectos.actividades.Actividade;
import centrodeportivo.aplicacion.obxectos.actividades.Curso;
import centrodeportivo.aplicacion.obxectos.actividades.TipoActividade;
import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.aplicacion.obxectos.usuarios.Persoal;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class DAOActividade extends AbstractDAO {

    public DAOActividade(Connection conexion, FachadaAplicacion fachadaAplicacion) {
        super(conexion, fachadaAplicacion);
    }

    public void EngadirActividade(Actividade actividade) throws ExcepcionBD {
        PreparedStatement stmActividade = null;
        ResultSet rsActividade;
        Connection con;
        //Recuperamos a conexión coa base de datos.
        con = super.getConexion();


        //Preparamos a inserción:
        try {
            stmActividade = con.prepareStatement("INSERT INTO Actividade (dataactividade, area, instalacion, tipoactividade, curso, profesor, nome, duracion) " +
                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            //Establecemos os valores:
            stmActividade.setTimestamp(1, actividade.getData());
            stmActividade.setInt(2, actividade.getArea().getCodArea());
            stmActividade.setInt(3, actividade.getArea().getInstalacion().getCodInstalacion());
            stmActividade.setInt(4, actividade.getTipoActividade().getCodTipoActividade());

            if (actividade.getCurso()!=null)
                stmActividade.setInt(5, actividade.getCurso().getCodCurso());
            else
                stmActividade.setNull(5, Types.INTEGER);

            stmActividade.setString(6, actividade.getProfesor().getLogin());
            stmActividade.setString(7, actividade.getNome());
            stmActividade.setFloat(8, actividade.getDuracion());

            //Realizamos a actualización:
            stmActividade.executeUpdate();

            //Facemos commit:
            con.commit();

        } catch (SQLException e) {
            //Lanzamos neste caso unha excepción cara a aplicación:
            throw new ExcepcionBD(con, e);
        } finally {
            //En calquera caso, téntase pechar os cursores.
            try {
                stmActividade.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }

    public boolean horarioOcupadoActividade(Actividade actVella, Actividade actNova) {
        /*
         * Esta función permite avaliar se a actividade pasada se superporia con algunha das existentes
         * na base de datos, sempre e cando sexa unha actividade con datos cambiados.
         * */
        PreparedStatement stmActividade = null;
        ResultSet rsActividade;
        Connection con;
        //Recuperamos a conexión coa base de datos.
        con = super.getConexion();

        //Preparamos a consulta:
        try {
            String consulta = "SELECT dataactividade, area, instalacion, nome  " +
                    " FROM actividade " +
                    " WHERE ((area = ? and instalacion = ? and (" +
                    "       ((dataactividade + (duracion * interval '1 hour')) > ? and dataactividade <= ?) or" +
                    "       ((dataactividade + (duracion * interval '1 hour')) >= ? " +
                    "           and dataactividade < ?)))" +
                    "   or (profesor = ? and (" +
                    "       ((dataactividade + (duracion * interval '1 hour')) > ? and dataactividade <= ?) or" +
                    "       ((dataactividade + (duracion * interval '1 hour')) >= ? " +
                    "           and dataactividade < ?))))";

            if(actVella != null){
                consulta +=     "       and (dataactividade != ? " +
                                "       or area != ? " +
                                "       or instalacion != ?)";
            }

            stmActividade = con.prepareStatement(consulta);

            Timestamp dataFin =  new Timestamp(actNova.getData().getTime() + TimeUnit.HOURS.toMillis((long) actNova.getDuracion().floatValue()));

            //Establecemos os valores:
            stmActividade.setInt(1, actNova.getArea().getCodArea());
            stmActividade.setInt(2, actNova.getArea().getInstalacion().getCodInstalacion());
            stmActividade.setTimestamp(3, actNova.getData());
            stmActividade.setTimestamp(4, actNova.getData());
            stmActividade.setTimestamp(5, dataFin);
            stmActividade.setTimestamp(6, dataFin);
            stmActividade.setString(7, actNova.getProfesor().getLogin());
            stmActividade.setTimestamp(8, actNova.getData());
            stmActividade.setTimestamp(9, actNova.getData());
            stmActividade.setTimestamp(10, dataFin);
            stmActividade.setTimestamp(11, dataFin);

            if(actVella != null){
                stmActividade.setTimestamp(12, actVella.getData());
                stmActividade.setInt(13, actVella.getArea().getCodArea());
                stmActividade.setInt(14, actVella.getArea().getInstalacion().getCodInstalacion());
            }

            //Facemos a consulta:
            rsActividade = stmActividade.executeQuery();

            if (rsActividade.next()) {
                return true;
            }

            return false;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            //En calquera caso, téntase pechar os cursores.
            try {
                stmActividade.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
        return false;
    }

    public void modificarActividade(Actividade actVella, Actividade actNova) throws ExcepcionBD {
        PreparedStatement stmActividade = null;
        ResultSet rsActividade;
        Connection con;
        //Recuperamos a conexión coa base de datos.
        con = super.getConexion();

        //Preparamos a inserción:
        try {
            String consulta =" UPDATE actividade " +
            " SET tipoactividade = ?, " +
                    "     curso = ?, " +
                    "     profesor = ?," +
                    "     nome = ?, " +
                    "     duracion = ?, " +
                    "     dataactividade = ?, " +
                    "     area = ?, " +
                    "     instalacion = ? " +
                    " WHERE dataactividade = ? and area = ? and instalacion = ? ";

            stmActividade = con.prepareStatement(consulta);

            //Establecemos os valores:
            stmActividade.setInt(1, actNova.getTipoActividade().getCodTipoActividade());
            if (actNova.getCurso()!=null) {
                stmActividade.setInt(2, actNova.getCurso().getCodCurso());
            } else {
                stmActividade.setNull(2, Types.INTEGER);
            }
            stmActividade.setString(3, actNova.getProfesor().getLogin());
            stmActividade.setString(4, actNova.getNome());
            stmActividade.setFloat(5, actNova.getDuracion());
            stmActividade.setTimestamp(6, actNova.getData());
            stmActividade.setInt(7, actNova.getArea().getCodArea());
            stmActividade.setInt(8, actNova.getArea().getInstalacion().getCodInstalacion());
            stmActividade.setTimestamp(9, actVella.getData());
            stmActividade.setInt(10, actVella.getArea().getCodArea());
            stmActividade.setInt(11, actVella.getArea().getInstalacion().getCodInstalacion());


            //Realizamos a actualización:
            stmActividade.executeUpdate();

            //Facemos commit:
            con.commit();

        } catch (SQLException e) {
            //Lanzamos neste caso unha excepción cara a aplicación:
            throw new ExcepcionBD(con, e);
        } finally {
            //En calquera caso, téntase pechar os cursores.
            try {
                stmActividade.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }

    public void borrarActividade(Actividade actividade, Mensaxe mensaxe) throws ExcepcionBD {
        PreparedStatement stmActividade = null;
        //Ao cancelar a actividade, envíase unha mensaxe si ou si a todos os socios da actividade:
        PreparedStatement stmUsuarios = null;
        PreparedStatement stmMensaxes = null;
        ResultSet rsUsuarios;
        Connection con;
        //Recuperamos a conexión coa base de datos.
        con = super.getConexion();

        //Preparamos a inserción:
        try {
            //Comezaremos buscando os participantes da actividade borrada:
            stmUsuarios = con.prepareStatement("SELECT * FROM realizaractividade " +
                    " WHERE dataactividade = ? " +
                    "   AND area = ? " +
                    "   AND instalacion = ?");

            //Completamos esta primeira consulta:
            stmUsuarios.setTimestamp(1, actividade.getData());
            stmUsuarios.setInt(2, actividade.getArea().getCodArea());
            stmUsuarios.setInt(3, actividade.getArea().getInstalacion().getCodInstalacion());

            //Agora realizamos a consulta: é necesario facela aquí porque despois estará borrada a actividade e perderemos aos participantes:
            rsUsuarios = stmUsuarios.executeQuery();

            //O seguinte que faremos será o borrado da actividade:

            stmActividade = con.prepareStatement("DELETE FROM actividade " +
                    " WHERE dataactividade = ? and instalacion = ? and area = ?");

            stmActividade.setTimestamp(1, actividade.getData());
            stmActividade.setInt(2, actividade.getArea().getInstalacion().getCodInstalacion());
            stmActividade.setInt(3, actividade.getArea().getCodArea());

            //Realizamos a actualización:
            stmActividade.executeUpdate();

            //Para rematar, garantiremos que todos os usuarios anotados se enteren do borrado:
            stmMensaxes = con.prepareStatement("INSERT INTO enviarmensaxe (emisor, receptor, contido, lido) " +
                    " VALUES (?, ?, ?, ?)");
            stmMensaxes.setString(1, mensaxe.getEmisor().getLogin());
            stmMensaxes.setString(3, mensaxe.getContido());
            stmMensaxes.setBoolean(4, false);

            //Procesamos os usuarios participantes da actividade obtidos antes e imos engadíndoos:
            while(rsUsuarios.next()){
                stmMensaxes.setString(2, rsUsuarios.getString("usuario"));
                //Facemos a actualización:
                stmMensaxes.executeUpdate();
            }

            //Facemos commit:
            con.commit();

        } catch (SQLException e) {
            //Lanzamos neste caso unha excepción cara a aplicación:
            throw new ExcepcionBD(con, e);
        } finally {
            //En calquera caso, téntase pechar os cursores.
            try {
                stmActividade.close();
                stmUsuarios.close();
                stmMensaxes.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }

    public void apuntarseActividade(Actividade actividade, Usuario usuario) throws ExcepcionBD {
        PreparedStatement stmActividade = null;
        ResultSet rsActividade;
        Connection con;
        //Recuperamos a conexión coa base de datos.
        con = super.getConexion();

        //Preparamos a inserción:
        try {
            stmActividade = con.prepareStatement("INSERT INTO realizaractividade (dataactividade, area, instalacion, usuario) " +
                    " VALUES (?, ?, ?, ?)");
            //Establecemos os valores:
            stmActividade.setTimestamp(1, actividade.getData());
            stmActividade.setInt(2, actividade.getArea().getCodArea());
            stmActividade.setInt(3, actividade.getArea().getInstalacion().getCodInstalacion());
            stmActividade.setString(4, usuario.getLogin());

            //Realizamos a actualización:
            stmActividade.executeUpdate();

            //Facemos commit:
            con.commit();

        } catch (SQLException e) {
            //Lanzamos neste caso unha excepción cara a aplicación:
            throw new ExcepcionBD(con, e);
        } finally {
            //En calquera caso, téntase pechar os cursores.
            try {
                stmActividade.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }

    public void borrarseDeActividade(Actividade actividade, Usuario usuario) throws ExcepcionBD {
        PreparedStatement stmActividade = null;
        ResultSet rsActividade;
        Connection con;
        //Recuperamos a conexión coa base de datos.
        con = super.getConexion();

        //Preparamos a inserción:
        try {
            stmActividade = con.prepareStatement("DELETE FROM realizaractividade " +
                    " WHERE dataactividade = ? and instalacion = ? and area = ? and usuario = ?");
            //Establecemos os valores
            stmActividade.setTimestamp(1, actividade.getData());
            stmActividade.setInt(2, actividade.getArea().getInstalacion().getCodInstalacion());
            stmActividade.setInt(3, actividade.getArea().getCodArea());
            stmActividade.setString(4, usuario.getLogin());

            //Realizamos a actualización:
            stmActividade.executeUpdate();

            //Facemos commit:
            con.commit();

        } catch (SQLException e) {
            //Lanzamos neste caso unha excepción cara a aplicación:
            throw new ExcepcionBD(con, e);
        } finally {
            //En calquera caso, téntase pechar os cursores.
            try {
                stmActividade.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }

    public boolean estarApuntado(Actividade actividade, Usuario usuario) {
        PreparedStatement stmActividade = null;
        ResultSet rsActividade;
        Connection con;
        //Recuperamos a conexión coa base de datos.
        con = super.getConexion();

        //Preparamos a consulta:
        try {
            stmActividade = con.prepareStatement("SELECT dataactividade, area, instalacion, usuario " +
                    " FROM realizaractividade " +
                    " WHERE dataactividade = ? and area = ? and instalacion = ? and usuario = ?");

            //Establecemos os valores:
            stmActividade.setTimestamp(1, actividade.getData());
            stmActividade.setInt(2, actividade.getArea().getCodArea());
            stmActividade.setInt(3, actividade.getArea().getInstalacion().getCodInstalacion());
            stmActividade.setString(4, usuario.getLogin());

            //Facemos a consulta:
            rsActividade = stmActividade.executeQuery();

            if (rsActividade.next())
                    return true;
            return false;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            //En calquera caso, téntase pechar os cursores.
            try {
                stmActividade.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
        return false;
    }

    public ArrayList<Persoal> buscarProfesores(TipoActividade tipoactividade) {
        //Usaremos un ArrayList para almacenar todos os profesores que correspondan:
        ArrayList<Persoal> profesores = new ArrayList<>();

        PreparedStatement stmAreas = null;
        ResultSet rsProfes;
        Connection con;

        //Recuperamos a conexión:
        con = super.getConexion();

        //Preparamos a consulta:
        try {
            String consulta = "SELECT persoal " +
                    " FROM estarcapacitado ";

            //A esta consulta, ademais do anterior, engadiremos os filtros se se pasa unha area non nula como
            //argumento:

            if (tipoactividade != null) {
                consulta += " WHERE tipoactividade = ?";
            }

            //Ordenaremos o resultado polo código da área para ordenalas
            consulta += " ORDER BY persoal asc";

            stmAreas = con.prepareStatement(consulta);

            //Pasando area non nula completase a consulta.
            if (tipoactividade != null) {
                stmAreas.setInt(1, tipoactividade.getCodTipoActividade());
            }

            //Realizamos a consulta:
            rsProfes = stmAreas.executeQuery();

            //Recibida a consulta, procesámola:
            while (rsProfes.next()) {
                //Imos engadindo ao ArrayList do resultado cada area consultada:
                profesores.add(new Persoal(rsProfes.getString("persoal")));
            }
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            //Peche do statement:
            try {
                stmAreas.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
        return profesores;
    }

    public boolean NonEMaximoAforoActividade(Actividade actividade) {
        PreparedStatement stmActividade = null;
        ResultSet rsActividade;
        Connection con;

        //Recuperamos a conexión coa base de datos.
        con = super.getConexion();

        //Preparamos a consulta:
        try {
            stmActividade = con.prepareStatement(
                    " SELECT * " +
                            " FROM area" +
                            " WHERE codarea=? AND instalacion=? " +
                                " AND aforomaximo>(select count(*) " +
                                    " FROM realizaractividade " +
                                    " WHERE dataactividade=? " +
                                    " AND area=?" +
                                    " AND instalacion=?)"
            );

            //Establecemos os valores:
            stmActividade.setInt(1, actividade.getArea().getCodArea());
            stmActividade.setInt(2, actividade.getArea().getInstalacion().getCodInstalacion());
            stmActividade.setTimestamp(3, actividade.getData());
            stmActividade.setInt(4, actividade.getArea().getCodArea());
            stmActividade.setInt(5, actividade.getArea().getInstalacion().getCodInstalacion());

            //Facemos a consulta:
            rsActividade = stmActividade.executeQuery();


            if (rsActividade.next())
                    return true;
            return false;

        } catch (SQLException e) {
            //Imprimimos en caso de excepción o stack trace e facemos o rollback:
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            //En calquera caso, téntase pechar os cursores.
            try {
                stmActividade.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
        return false;
    }

    public ArrayList<Actividade> buscarActividade(Actividade actividade) {
        //Usaremos un ArrayList para almacenar unha nova actividade:
        ArrayList<Actividade> actividades = new ArrayList<>();

        PreparedStatement stmActividades = null;
        ResultSet rsActividades;
        Connection con;

        //Recuperamos a conexión:
        con = super.getConexion();

        //Preparamos a consulta:
        try {
            String consulta = "SELECT dataactividade, area, area.instalacion, tipoactividade, curso, profesor, " +
                    " actividade.nome, duracion, area.nome as areanome, instalacion.nome as instalacionnome " +
                    " FROM actividade JOIN area ON actividade.area=area.codarea  AND actividade.instalacion=area.instalacion " +
                    " JOIN instalacion ON area.codarea=instalacion.codinstalacion " +
                    " WHERE curso is NULL";

            //A esta consulta, ademais do anterior, engadiremos os filtros se se pasa unha area non nula como
            //argumento:


            if (actividade != null) {
                consulta += " AND LOWER(actividade.nome) LIKE LOWER(?)  ";
            }

            //Ordenaremos o resultado polo código da área para ordenalas
            consulta += " ORDER BY dataactividade asc";

            stmActividades = con.prepareStatement(consulta);

            //Pasando area non nula completase a consulta.
            if (actividade != null) {
                //Establecemos os valores da consulta segundo a instancia de instalación pasada:
                stmActividades.setString(1, "%" + actividade.getNome() + "%");
            }

            //Realizamos a consulta:
            rsActividades = stmActividades.executeQuery();

            //Recibida a consulta, procesámola:
            while (rsActividades.next()) {
                //Imos engadindo ao ArrayList do resultado cada area consultada:
                actividades.add(new Actividade(rsActividades.getTimestamp(1), rsActividades.getString("nome"),
                        rsActividades.getFloat("duracion"), new Area(rsActividades.getInt("area"), new Instalacion(rsActividades.getInt("instalacion"), rsActividades.getString("instalacionnome")), rsActividades.getString("areanome")) ,new TipoActividade(rsActividades.getInt("tipoactividade")), new Curso(rsActividades.getInt("curso")),new Persoal(rsActividades.getString("profesor")) ));
            }
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            //Peche do statement:
            try {
                stmActividades.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
        return actividades;
    }

    public ArrayList<Actividade> buscarActividadeNONParticipa(Actividade actividade, Usuario usuario) {
        //Usaremos un ArrayList para almacenar unha nova actividade:
        ArrayList<Actividade> actividades = new ArrayList<>();

        PreparedStatement stmActividades = null;
        ResultSet rsActividades;
        Connection con;

        //Recuperamos a conexión:
        con = super.getConexion();


        //Preparamos a consulta:
        try {
            String consulta = "SELECT actividade.dataactividade, actividade.area, actividade.instalacion, actividade.tipoactividade as tipoactividade, curso, profesor, actividade.nome as nome, duracion, tipoactividade.nome as nomeactividade, area.nome as nomearea, instalacion.nome as nomeinstalacion  " +
                    " FROM actividade, tipoactividade, area, instalacion " +
                    " WHERE actividade.tipoactividade=tipoactividade.codtipoactividade " +
                    "   AND area.instalacion=instalacion.codinstalacion " +
                    "   AND area.codarea=actividade.area " +
                    "   AND area.instalacion=actividade.instalacion " +
                    "   AND curso is null" +
                    "   AND (actividade.dataactividade, actividade.area, actividade.instalacion) NOT IN " +
                    "                            (SELECT actividade.dataactividade, actividade.area, actividade.instalacion " +
                    "                            FROM actividade, realizaractividade " +
                    "                            WHERE realizaractividade.dataactividade=actividade.dataactividade " +
                    "                            AND realizaractividade.area=actividade.area " +
                    "                            AND realizaractividade.instalacion=actividade.instalacion " +
                    "                            AND usuario=? )" +
                    "   AND actividade.dataactividade>NOW()";


            //A esta consulta, ademais do anterior, engadiremos os filtros se se pasa unha area non nula como
            //argumento:
            if (actividade!= null){
                System.out.println(actividade.getNome());
                consulta += " AND lower(actividade.nome) like lower(?)  ";

                if (actividade.getArea()!=null)
                    consulta += " AND actividade.area=? AND actividade.instalacion=? ";

            }

            consulta+=" ORDER BY actividade.nome ";

            stmActividades = con.prepareStatement(consulta);

            stmActividades.setString(1, usuario.getLogin());
            if (actividade!= null) {
                stmActividades.setString(2, "%" + actividade.getNome() + "%");
                //Pasando area non nula completase a consulta.
                if (actividade.getArea() != null) {
                    stmActividades.setInt(3, actividade.getArea().getCodArea());
                    stmActividades.setInt(4, actividade.getArea().getInstalacion().getCodInstalacion());
                }
            }

            //Realizamos a consulta:
            rsActividades = stmActividades.executeQuery();

            //Recibida a consulta, procesámola:
            while (rsActividades.next()) {
                //Imos engadindo ao ArrayList do resultado cada area consultada:
                actividades.add(new Actividade(rsActividades.getTimestamp(1), rsActividades.getString("nome"),
                        rsActividades.getFloat("duracion"), new Area(rsActividades.getInt("area"), new Instalacion(rsActividades.getInt("instalacion"), rsActividades.getString("nomeinstalacion")), rsActividades.getString("nomearea")) ,new TipoActividade(rsActividades.getInt("tipoactividade"), rsActividades.getString("nomeactividade")), new Curso(rsActividades.getInt("curso")),new Persoal(rsActividades.getString("profesor")) ));
            }
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            //Peche do statement:
            try {
                if(stmActividades!=null)stmActividades.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
        return actividades;
    }

    public ArrayList<Actividade> buscarActividadeParticipa(Actividade actividade, Usuario usuario) {
        //Usaremos un ArrayList para almacenar unha nova actividade:
        ArrayList<Actividade> actividades = new ArrayList<>();

        PreparedStatement stmActividades = null;
        ResultSet rsActividades;
        Connection con;

        //Recuperamos a conexión:
        con = super.getConexion();

        //Preparamos a consulta:
        try {
            String consulta = "SELECT actividade.dataactividade, actividade.area, actividade.instalacion, actividade.tipoactividade as tipoactividade, curso, profesor, actividade.nome as nome, duracion, tipoactividade.nome as nomeactividade, area.nome as nomearea, instalacion.nome as nomeinstalacion " +
                    " FROM actividade, tipoactividade, realizaractividade, instalacion, area " +
                    " WHERE actividade.tipoactividade=tipoactividade.codtipoactividade " +
                    "   AND area.instalacion=instalacion.codinstalacion 1" +
                    "   AND area.codarea=actividade.area " +
                    "   AND area.instalacion=actividade.instalacion"+
                    "   AND curso is null " +
                    "   AND realizaractividade.dataactividade=actividade.dataactividade " +
                    "   AND realizaractividade.area=actividade.area " +
                    "   AND realizaractividade.instalacion=actividade.instalacion " +
                    "   AND usuario=? ";

            //A esta consulta, ademais do anterior, engadiremos os filtros se se pasa unha area non nula como
            //argumento:
            if (actividade!= null){
                consulta += " AND lower(actividade.nome) like lower(?)  ";

                if (actividade.getArea()!=null)
                    consulta += " AND actividade.area=? AND actividade.instalacion=? ";

            }


            //Ordenaremos o resultado polo código da área para ordenalas
            consulta += " ORDER BY actividade.nome asc";

            stmActividades = con.prepareStatement(consulta);

            stmActividades.setString(1, usuario.getLogin());
            if (actividade!= null) {
                stmActividades.setString(2, "%" + actividade.getNome() + "%");
                //Pasando area non nula completase a consulta.
                if (actividade.getArea() != null) {
                    stmActividades.setInt(3, actividade.getArea().getCodArea());
                    stmActividades.setInt(4, actividade.getArea().getInstalacion().getCodInstalacion());
                }
            }

            //Realizamos a consulta:
            rsActividades = stmActividades.executeQuery();

            //Recibida a consulta, procesámola:
            while (rsActividades.next()) {
                //Imos engadindo ao ArrayList do resultado cada area consultada:
                actividades.add(new Actividade(rsActividades.getTimestamp(1), rsActividades.getString("nome"),
                        rsActividades.getFloat("duracion"), new Area(rsActividades.getInt("area"), new Instalacion(rsActividades.getInt("instalacion"), rsActividades.getString("nomeinstalacion")), rsActividades.getString("nomearea")) ,new TipoActividade(rsActividades.getInt("tipoactividade"), rsActividades.getString("nomeactividade")), new Curso(rsActividades.getInt("curso")),new Persoal(rsActividades.getString("profesor")) ));
            }
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            //Peche do statement:
            try {
                if(stmActividades!=null)stmActividades.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
        return actividades;
    }
}


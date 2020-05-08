package centrodeportivo.baseDatos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.actividades.Actividade;
import centrodeportivo.aplicacion.obxectos.actividades.Curso;
import centrodeportivo.aplicacion.obxectos.actividades.TipoActividade;
import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.aplicacion.obxectos.usuarios.Persoal;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;

import java.rmi.activation.ActivationID;
import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class DAOActividade extends AbstractDAO {

    public DAOActividade(Connection conexion, FachadaAplicacion fachadaAplicacion) {
        super(conexion, fachadaAplicacion);
    }

    public void EngadirActividade(Actividade actividade) throws ExcepcionBD {
        PreparedStatement stmActivide = null;
        ResultSet rsActividade;
        Connection con;
        //Recuperamos a conexión coa base de datos.
        con = super.getConexion();


        //Preparamos a inserción:
        try {
            stmActivide = con.prepareStatement("INSERT INTO Actividade (dataactividade, area, instalacion, tipoactividade, curso, profesor, nome, duracion) " +
                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            //Establecemos os valores:
            stmActivide.setTimestamp(1, actividade.getData());
            stmActivide.setInt(2, actividade.getArea().getCodArea());
            stmActivide.setInt(3, actividade.getArea().getInstalacion().getCodInstalacion());
            stmActivide.setInt(4, actividade.getTipoActividade().getCodTipoActividade());

            if (actividade.getCurso()!=null)
                stmActivide.setInt(5, actividade.getCurso().getCodCurso());
            else
                stmActivide.setNull(5, Types.INTEGER);

            stmActivide.setString(6, actividade.getProfesor().getLogin());
            stmActivide.setString(7, actividade.getNome());
            stmActivide.setFloat(8, actividade.getDuracion());

            //Realizamos a actualización:
            stmActivide.executeUpdate();

            //Facemos commit:
            con.commit();

        } catch (SQLException e) {
            //Lanzamos neste caso unha excepción cara a aplicación:
            throw new ExcepcionBD(con, e);
        } finally {
            //En calquera caso, téntase pechar os cursores.
            try {
                stmActivide.close();
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
        PreparedStatement stmActivide = null;
        ResultSet rsActividade;
        Connection con;
        //Recuperamos a conexión coa base de datos.
        con = super.getConexion();

        //Preparamos a consulta:
        try {
            String consulta = "SELECT dataactividade, area, instalacion, nome  " +
                    " FROM actividade " +
                    " WHERE area = ? and instalacion = ? and (" +
                    "       (dataactividade + (duracion * interval '1 hour') > ? and dataactividade <= ?) or" +
                    "       (dataactividade > ? and dataactividade < ? ))";

            if(actVella != null){
                consulta +=     "       and dataactividade != ? " +
                                "       and area != ? " +
                                "       and instalacion != ? ";
            }

            stmActivide = con.prepareStatement(consulta);

            //Establecemos os valores:
            stmActivide.setInt(1, actNova.getArea().getCodArea());
            stmActivide.setInt(2, actNova.getArea().getInstalacion().getCodInstalacion());
            stmActivide.setTimestamp(3, actNova.getData());
            stmActivide.setTimestamp(4, actNova.getData());
            stmActivide.setTimestamp(5, actNova.getData());
            //Calculamos o momento no que reamta a actividade
            stmActivide.setTimestamp(6, new Timestamp(actNova.getData().getTime() + TimeUnit.HOURS.toMillis((long) actNova.getDuracion().floatValue())));

            if(actVella != null){
                stmActivide.setTimestamp(7, actVella.getData());
                stmActivide.setInt(8, actVella.getArea().getCodArea());
                stmActivide.setInt(9, actVella.getArea().getInstalacion().getCodInstalacion());
            }

            //Facemos a consulta:
            rsActividade = stmActivide.executeQuery();

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
                stmActivide.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
        return false;
    }

    public void modificarActividade(Actividade actVella, Actividade actNova) throws ExcepcionBD {
        PreparedStatement stmActivide = null;
        ResultSet rsActividade;
        Connection con;
        //Recuperamos a conexión coa base de datos.
        con = super.getConexion();

        //Preparamos a inserción:
        try {
            stmActivide = con.prepareStatement("UPDATE actividade " +
                    " SET tipoactividade = ?, " +
                    "     curso = ?, " +
                    "     profesor = ?," +
                    "     nome = ? " +
                    "     duracion = ? " +
                    "     data = ? " +
                    " WHERE dataactividade = ? and area = ? and instalacion = ? ");

            //Establecemos os valores:
            stmActivide.setInt(1, actNova.getTipoActividade().getCodTipoActividade());
            stmActivide.setInt(2, actNova.getCurso().getCodCurso());
            stmActivide.setString(3, actNova.getProfesor().getLogin());
            stmActivide.setString(4, actNova.getNome());
            stmActivide.setFloat(5, actNova.getDuracion());
            stmActivide.setTimestamp(6, actNova.getData());
            stmActivide.setTimestamp(7, actVella.getData());
            stmActivide.setInt(8, actVella.getArea().getCodArea());
            stmActivide.setInt(9, actVella.getArea().getInstalacion().getCodInstalacion());


            //Realizamos a actualización:
            stmActivide.executeUpdate();

            //Facemos commit:
            con.commit();

        } catch (SQLException e) {
            //Lanzamos neste caso unha excepción cara a aplicación:
            throw new ExcepcionBD(con, e);
        } finally {
            //En calquera caso, téntase pechar os cursores.
            try {
                stmActivide.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }

    public void borrarActividade(Actividade actividade) throws ExcepcionBD {
        PreparedStatement stmActivide = null;
        ResultSet rsActividade;
        Connection con;
        //Recuperamos a conexión coa base de datos.
        con = super.getConexion();

        //Preparamos a inserción:
        try {
            stmActivide = con.prepareStatement("DELETE FROM actividade " +
                    " WHERE dataactividade = ? and instalacion = ? and area = ?");

            stmActivide.setTimestamp(1, actividade.getData());
            stmActivide.setInt(2, actividade.getArea().getInstalacion().getCodInstalacion());
            stmActivide.setInt(3, actividade.getArea().getCodArea());

            //Realizamos a actualización:
            stmActivide.executeUpdate();

            //Facemos commit:
            con.commit();

        } catch (SQLException e) {
            //Lanzamos neste caso unha excepción cara a aplicación:
            throw new ExcepcionBD(con, e);
        } finally {
            //En calquera caso, téntase pechar os cursores.
            try {
                stmActivide.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }

    public void apuntarseActividade(Actividade actividade, Usuario usuario) throws ExcepcionBD {
        PreparedStatement stmActivide = null;
        ResultSet rsActividade;
        Connection con;
        //Recuperamos a conexión coa base de datos.
        con = super.getConexion();

        //Preparamos a inserción:
        try {
            stmActivide = con.prepareStatement("INSERT INTO realizaractividade (dataactividade, area, instalacion, usuario) " +
                    " VALUES (?, ?, ?, ?)");
            //Establecemos os valores:
            stmActivide.setTimestamp(1, actividade.getData());
            stmActivide.setInt(2, actividade.getArea().getCodArea());
            stmActivide.setInt(3, actividade.getArea().getInstalacion().getCodInstalacion());
            stmActivide.setString(4, usuario.getLogin());

            //Realizamos a actualización:
            stmActivide.executeUpdate();

            //Facemos commit:
            con.commit();

        } catch (SQLException e) {
            //Lanzamos neste caso unha excepción cara a aplicación:
            throw new ExcepcionBD(con, e);
        } finally {
            //En calquera caso, téntase pechar os cursores.
            try {
                stmActivide.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }

    public void borrarseDeActividade(Actividade actividade, Usuario usuario) throws ExcepcionBD {
        PreparedStatement stmActivide = null;
        ResultSet rsActividade;
        Connection con;
        //Recuperamos a conexión coa base de datos.
        con = super.getConexion();

        //Preparamos a inserción:
        try {
            stmActivide = con.prepareStatement("DELETE FROM realizaractividade " +
                    " WHERE dataactividade = ? and instalacion = ? and area = ? and usuario = ?");
            //Establecemos os valores
            stmActivide.setTimestamp(1, actividade.getData());
            stmActivide.setInt(2, actividade.getArea().getInstalacion().getCodInstalacion());
            stmActivide.setInt(3, actividade.getArea().getCodArea());
            stmActivide.setString(4, usuario.getLogin());

            //Realizamos a actualización:
            stmActivide.executeUpdate();

            //Facemos commit:
            con.commit();

        } catch (SQLException e) {
            //Lanzamos neste caso unha excepción cara a aplicación:
            throw new ExcepcionBD(con, e);
        } finally {
            //En calquera caso, téntase pechar os cursores.
            try {
                stmActivide.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }

    public boolean estarApuntado(Actividade actividade, Usuario usuario) {
        PreparedStatement stmActivide = null;
        ResultSet rsActividade;
        Connection con;
        //Recuperamos a conexión coa base de datos.
        con = super.getConexion();

        //Preparamos a consulta:
        try {
            stmActivide = con.prepareStatement("SELECT dataactividade, area, instalacion, usuario " +
                    " FROM realizaractividade " +
                    " WHERE dataactividade = ? and area = ? and instalacion = ? and usuario = ?");

            //Establecemos os valores:
            stmActivide.setTimestamp(1, actividade.getData());
            stmActivide.setInt(2, actividade.getArea().getCodArea());
            stmActivide.setInt(3, actividade.getArea().getInstalacion().getCodInstalacion());
            stmActivide.setString(4, usuario.getLogin());

            //Facemos a consulta:
            rsActividade = stmActivide.executeQuery();

            if (rsActividade.next())
                if ((rsActividade.getTimestamp(1) == actividade.getData()) && (rsActividade.getInt(2) == actividade.getArea().getCodArea()) && (rsActividade.getInt(3) == actividade.getArea().getInstalacion().getCodInstalacion()) && (rsActividade.getString(4).equals(usuario.getLogin())))
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
                stmActivide.close();
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
        PreparedStatement stmActivide = null;
        ResultSet rsActividade;
        Connection con;

        //Recuperamos a conexión coa base de datos.
        con = super.getConexion();

        //Preparamos a consulta:
        try {
            stmActivide = con.prepareStatement(
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
            stmActivide.setTimestamp(1, actividade.getData());
            stmActivide.setInt(2, actividade.getArea().getCodArea());
            stmActivide.setInt(3, actividade.getArea().getInstalacion().getCodInstalacion());

            //Facemos a consulta:
            rsActividade = stmActivide.executeQuery();

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
                stmActivide.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
            return false;
        }
    }

    /*
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
            String consulta = "SELECT dataactividade, area, instalacion, actividade.tipoactividade as tipoactividade, curso, profesor, actividade.nome as nome, duracion, tipoactividade.nome as nomeactividade " +
                    " FROM actividade, tipoactividade " +
                    " WHERE actividade.tipoactividade=tipoactividade.codtipoactividade ";

            //A esta consulta, ademais do anterior, engadiremos os filtros se se pasa unha area non nula como
            //argumento:

            if (actividade != null) {
                consulta += " AND dataactividade=? AND area=? AND instalacion=? ";
                if (actividade.getCurso() == null)
                    consulta += " AND curso is null ";
                else
                    consulta += " AND curso=?";
            }

            //Ordenaremos o resultado polo código da área para ordenalas
            consulta += " ORDER BY nome asc";

            stmActividades = con.prepareStatement(consulta);

            //Pasando area non nula completase a consulta.
            if (actividade != null) {
                //Establecemos os valores da consulta segundo a instancia de instalación pasada:
                stmActividades.setTimestamp(1, actividade.getData());
                stmActividades.setInt(2, actividade.getArea().getCodArea());
                stmActividades.setInt(3, actividade.getArea().getInstalacion().getCodInstalacion());
                if (actividade.getCurso()!=null)
                    stmActividades.setInt(4, actividade.getCurso().getCodCurso());
            }

            //Realizamos a consulta:
            rsActividades = stmActividades.executeQuery();

            //Recibida a consulta, procesámola:
            while (rsActividades.next()) {
                //Imos engadindo ao ArrayList do resultado cada area consultada:
                actividades.add(new Actividade(rsActividades.getTimestamp(1), rsActividades.getString("nome"),
                        rsActividades.getFloat("duracion"), new Area(rsActividades.getInt("area"), new Instalacion(rsActividades.getInt("instalacion"))) ,new TipoActividade(rsActividades.getInt("tipoactividade"), rsActividades.getString("nomeactividade")), new Curso(rsActividades.getInt("curso")),new Persoal(rsActividades.getString("profesor")) ));
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
    }*/

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
            String consulta = "SELECT actividade.dataactividade, actividade.area, actividade.instalacion, actividade.tipoactividade as tipoactividade, curso, profesor, actividade.nome as nome, duracion, tipoactividade.nome as nomeactividade " +
                    " FROM actividade, tipoactividade, realizaractividade " +
                    " WHERE actividade.tipoactividade=tipoactividade.codtipoactividade "+
                    "   AND realizaractividade.dataactividade=actividade.dataactividade " +
                    "   AND realizaractividade.area=actividade.area " +
                    "   AND realizaractividade.instalacion=actividade.instalacion " +
                    "   AND usuario=? ";

            //A esta consulta, ademais do anterior, engadiremos os filtros se se pasa unha area non nula como
            //argumento:

            if (actividade != null) {
                consulta += " AND lower(actividade.nome) like lower(?)  ";

                if (actividade.getArea()!=null)
                    consulta += " AND area=? AND instalacion=? ";
            }

            //Ordenaremos o resultado polo código da área para ordenalas
            consulta += " ORDER BY nome asc";

            stmActividades = con.prepareStatement(consulta);

            stmActividades.setString(1, usuario.getLogin());

            //Pasando area non nula completase a consulta.
            if (actividade != null) {
                //Establecemos os valores da consulta segundo a instancia de instalación pasada:
                stmActividades.setTimestamp(2, actividade.getData());
                if (actividade.getArea() != null)
                {
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
                        rsActividades.getFloat("duracion"), new Area(rsActividades.getInt("area"), new Instalacion(rsActividades.getInt("instalacion"))) ,new TipoActividade(rsActividades.getInt("tipoactividade"), rsActividades.getString("nomeactividade")), new Curso(rsActividades.getInt("curso")),new Persoal(rsActividades.getString("profesor")) ));
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
            String consulta = "SELECT actividade.dataactividade, actividade.area, actividade.instalacion, actividade.tipoactividade as tipoactividade, curso, profesor, actividade.nome as nome, duracion, tipoactividade.nome as nomeactividade " +
                    " FROM actividade, tipoactividade, realizaractividade " +
                    " WHERE actividade.tipoactividade=tipoactividade.codtipoactividade "+
                    "   AND realizaractividade.dataactividade=actividade.dataactividade " +
                    "   AND realizaractividade.area=actividade.area " +
                    "   AND realizaractividade.instalacion=actividade.instalacion " +
                    "   AND usuario=? ";

            //A esta consulta, ademais do anterior, engadiremos os filtros se se pasa unha area non nula como
            //argumento:

            if (actividade != null) {
                consulta += " AND lower(actividade.nome) like lower(?)  ";

                if (actividade.getArea()!=null)
                    consulta += " AND area=? AND instalacion=? ";
            }

            //Ordenaremos o resultado polo código da área para ordenalas
            consulta += " ORDER BY nome asc";

            stmActividades = con.prepareStatement(consulta);

            stmActividades.setString(1, usuario.getLogin());

            //Pasando area non nula completase a consulta.
            if (actividade != null) {
                //Establecemos os valores da consulta segundo a instancia de instalación pasada:
                stmActividades.setTimestamp(2, actividade.getData());
                if (actividade.getArea() != null)
                {
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
                        rsActividades.getFloat("duracion"), new Area(rsActividades.getInt("area"), new Instalacion(rsActividades.getInt("instalacion"))) ,new TipoActividade(rsActividades.getInt("tipoactividade"), rsActividades.getString("nomeactividade")), new Curso(rsActividades.getInt("curso")),new Persoal(rsActividades.getString("profesor")) ));
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
}


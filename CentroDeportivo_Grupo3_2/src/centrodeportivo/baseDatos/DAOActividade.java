package centrodeportivo.baseDatos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.actividades.Actividade;
import centrodeportivo.aplicacion.obxectos.actividades.TipoActividade;
import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.aplicacion.obxectos.usuarios.Persoal;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class DAOActividade extends AbstractDAO {

    public DAOActividade (Connection conexion, FachadaAplicacion fachadaAplicacion){
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
            stmActivide.setInt(5, actividade.getCurso().getCodCurso());
            stmActivide.setString(6, actividade.getProfesor().getLogin());
            stmActivide.setString(7, actividade.getNome());
            stmActivide.setFloat(8, actividade.getDuracion());

            //Realizamos a actualización:
            stmActivide.executeUpdate();

            //Facemos commit:
            con.commit();

        } catch (SQLException e){
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

    public boolean existeActividade(Actividade actividade) {
        PreparedStatement stmActivide = null;
        ResultSet rsActividade;
        Connection con;
        //Recuperamos a conexión coa base de datos.
        con = super.getConexion();

        //Preparamos a inserción:
        try {
            stmActivide = con.prepareStatement("SELECT dataactividade, area, instalacion " +
                    " FROM actividade " +
                    " WHERE dataactividade = ? and area = ? and instalacion = ? ");

            //Establecemos os valores:
            stmActivide.setTimestamp(1, actividade.getData());
            stmActivide.setInt(2, actividade.getArea().getCodArea());
            stmActivide.setInt(3, actividade.getArea().getInstalacion().getCodInstalacion());

            //Facemos a consulta:
            rsActividade = stmActivide.executeQuery();

            if (rsActividade.next())
                if ((rsActividade.getTimestamp(1) == actividade.getData()) && (rsActividade.getInt(2) == actividade.getArea().getCodArea()) && (rsActividade.getInt(3) == actividade.getArea().getInstalacion().getCodInstalacion()))
                    return true;
            return false;

        } catch (SQLException e){
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

    public boolean horarioOcupadoActividade(Actividade actividade) throws ExcepcionBD {
        /*
        * Esta función permite avaliar se a actividade pasada se superporia con algunha das existentes
        * na base de datos.
        * */
        PreparedStatement stmActivide = null;
        ResultSet rsActividade;
        Connection con;
        //Recuperamos a conexión coa base de datos.
        con = super.getConexion();

        //Preparamos a inserción:
        try {
            stmActivide = con.prepareStatement("SELECT dataactividade, area, instalacion, nome  " +
                    " FROM actividade " +
                    " WHERE area = ? and instalacion = ? and ("+
                    "       (dataactividade + (duracion * interval '1 hour') > ? and dataactividade <= ?) or" +
                    "       (dataactividade > ? and dataactividade < ? ))"
            );

            //Establecemos os valores:
            stmActivide.setInt(1, actividade.getArea().getCodArea());
            stmActivide.setInt(2, actividade.getArea().getInstalacion().getCodInstalacion());
            stmActivide.setTimestamp(3, actividade.getData());
            stmActivide.setTimestamp(4, actividade.getData());
            stmActivide.setTimestamp(5, actividade.getData());
            //Calculamos o momento no que reamta a actividade
            stmActivide.setTimestamp(6, new Timestamp(actividade.getData().getTime() + TimeUnit.HOURS.toMillis((long)actividade.getDuracion().floatValue())));


            //Facemos a consulta:
            rsActividade = stmActivide.executeQuery();

            if (rsActividade.next())
                if ((rsActividade.getTimestamp(1) == actividade.getData()) && (rsActividade.getInt(2) == actividade.getArea().getCodArea()) && (rsActividade.getInt(3) == actividade.getArea().getInstalacion().getCodInstalacion()))
                    return true;
            return false;

        } catch (SQLException e){
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

        } catch (SQLException e){
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

        } catch (SQLException e){
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

        } catch (SQLException e){
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

        } catch (SQLException e){
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

    public boolean estarApuntado(Actividade actividade, Usuario usuario) throws ExcepcionBD {
        PreparedStatement stmActivide = null;
        ResultSet rsActividade;
        Connection con;
        //Recuperamos a conexión coa base de datos.
        con = super.getConexion();

        //Preparamos a inserción:
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

        } catch (SQLException e){
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

    public ArrayList<Persoal> buscarProfesores(TipoActividade tipoactividade)
    {
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
                    " FROM estarcapacitado " ;

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
}

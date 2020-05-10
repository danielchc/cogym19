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
import centrodeportivo.aplicacion.obxectos.usuarios.Socio;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Víctor Barreiro
 * Clase que conterá todos os métodos DAO relacionados na súa maioría con xestións das actividades.
 */
public class DAOActividade extends AbstractDAO {

    /**
     * Constructor do DAO de actividades
     *
     * @param conexion          Referencia á conexión coa base de datos.
     * @param fachadaAplicacion Referencia á fachada da parte da aplicación.
     */
    public DAOActividade(Connection conexion, FachadaAplicacion fachadaAplicacion) {
        // Asignaremos estes atributos no constructor da clase pai ao que chamamos:
        super(conexion, fachadaAplicacion);
    }


    /**
     * Método que permite engadir unha actividade na base de datos.
     *
     * @param actividade Actividade que se desexa engadir na base de datos.
     * @throws ExcepcionBD Excepción asociada a problemas ao tentar facer a inserción sobre a base de datos.
     */
    public void EngadirActividade(Actividade actividade) throws ExcepcionBD {
        PreparedStatement stmActividade = null;
        Connection con;

        // Recuperamos a conexión coa base de datos:
        con = super.getConexion();


        // Preparamos a inserción:
        try {
            stmActividade = con.prepareStatement("INSERT INTO Actividade (dataactividade, area, instalacion," +
                    " tipoactividade, curso, profesor, nome, duracion) " +
                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

            // Introducimos os datos da actividade que queremos insertar:
            stmActividade.setTimestamp(1, actividade.getData());
            stmActividade.setInt(2, actividade.getArea().getCodArea());
            stmActividade.setInt(3, actividade.getArea().getInstalacion().getCodInstalacion());
            stmActividade.setInt(4, actividade.getTipoActividade().getCodTipoActividade());

            if (actividade.getCurso() != null)
                stmActividade.setInt(5, actividade.getCurso().getCodCurso());
            else
                stmActividade.setNull(5, Types.INTEGER);

            stmActividade.setString(6, actividade.getProfesor().getLogin());
            stmActividade.setString(7, actividade.getNome());
            stmActividade.setFloat(8, actividade.getDuracion());

            // Realizamos a actualización:
            stmActividade.executeUpdate();

            // Facemos commit:
            con.commit();
        } catch (SQLException e) {
            // Lanzamos neste caso unha excepción cara a aplicación:
            throw new ExcepcionBD(con, e);
        } finally {
            // En calquera caso, téntase pechar os cursores.
            try {
                stmActividade.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }

    /**
     * Método que permite avaliar se a actividade pasada se superporía con algunha das existentes
     * na base de datos, sempre e cando sexa unha actividade con datos cambiados.
     *
     * @param actVella Datos da actividade antiga.
     * @param actNova  Datos da actividade que se desexa.
     * @return Devolve True cando hai incompatibilidades co horario ou ben da área ou do persoal.
     */
    public boolean horarioOcupadoActividade(Actividade actVella, Actividade actNova) {
        PreparedStatement stmActividade = null;
        ResultSet rsActividade;
        Connection con;
        // Recuperamos a conexión coa base de datos:
        con = super.getConexion();

        // Preparamos a consulta:
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

            if (actVella != null) {
                consulta += "       and (dataactividade != ? " +
                        "       or area != ? " +
                        "       or instalacion != ?)";
            }

            stmActividade = con.prepareStatement(consulta);

            Timestamp dataFin = new Timestamp(actNova.getData().getTime() + TimeUnit.HOURS.toMillis((long) actNova.getDuracion().floatValue()));

            // Establecemos os valores:
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

            if (actVella != null) {
                stmActividade.setTimestamp(12, actVella.getData());
                stmActividade.setInt(13, actVella.getArea().getCodArea());
                stmActividade.setInt(14, actVella.getArea().getInstalacion().getCodInstalacion());
            }

            // Facemos a consulta:
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
            // En calquera caso, téntase pechar os cursores:
            try {
                stmActividade.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
        return false;
    }

    /**
     * Método que permite modificar os datos dunha actividade.
     *
     * @param actVella Datos da actividade actualmente.
     * @param actNova  Datos da actividade polos que se desexan cambiar os actuais.
     * @throws ExcepcionBD Excepción asociada a problemas ó tentar facer a modificación na base de datos.
     */
    public void modificarActividade(Actividade actVella, Actividade actNova) throws ExcepcionBD {
        PreparedStatement stmActividade = null;
        ResultSet rsActividade;
        Connection con;
        // Recuperamos a conexión coa base de datos:
        con = super.getConexion();

        // Preparamos a inserción:
        try {
            String consulta = " UPDATE actividade " +
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

            // Establecemos os valores:
            stmActividade.setInt(1, actNova.getTipoActividade().getCodTipoActividade());
            if (actNova.getCurso() != null) {
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


            // Realizamos a actualización:
            stmActividade.executeUpdate();

            // Facemos commit:
            con.commit();

        } catch (SQLException e) {
            // Lanzamos neste caso unha excepción cara a aplicación:
            throw new ExcepcionBD(con, e);
        } finally {
            // En calquera caso, téntase pechar os cursores.
            try {
                stmActividade.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }

    /**
     * Método que permite eliminar os datos dunha actividade da base de datos.
     *
     * @param actividade Actividade que se desexa eliminar da base de datos.
     * @param mensaxe    Mensaxe que se enviara os usuarios que estaban apuntados a dita actividade.
     * @throws ExcepcionBD Excepción asociada a problemas ó tentar borrar a actividade da base de datos.
     */
    public void borrarActividade(Actividade actividade, Mensaxe mensaxe) throws ExcepcionBD {
        PreparedStatement stmActividade = null;
        // Ao cancelar a actividade, envíase unha mensaxe si ou si a todos os socios da actividade:
        PreparedStatement stmUsuarios = null;
        PreparedStatement stmMensaxes = null;
        ResultSet rsUsuarios;
        Connection con;
        // Recuperamos a conexión coa base de datos:
        con = super.getConexion();

        // Preparamos a inserción:
        try {
            // Comezaremos buscando os participantes da actividade borrada:
            stmUsuarios = con.prepareStatement("SELECT * FROM realizaractividade " +
                    " WHERE dataactividade = ? " +
                    "   AND area = ? " +
                    "   AND instalacion = ?");

            // Completamos esta primeira consulta:
            stmUsuarios.setTimestamp(1, actividade.getData());
            stmUsuarios.setInt(2, actividade.getArea().getCodArea());
            stmUsuarios.setInt(3, actividade.getArea().getInstalacion().getCodInstalacion());

            // Agora realizamos a consulta: é necesario facela aquí porque despois estará borrada a actividade e perderemos aos participantes:
            rsUsuarios = stmUsuarios.executeQuery();

            // O seguinte que faremos será o borrado da actividade:

            stmActividade = con.prepareStatement("DELETE FROM actividade " +
                    " WHERE dataactividade = ? and instalacion = ? and area = ?");

            stmActividade.setTimestamp(1, actividade.getData());
            stmActividade.setInt(2, actividade.getArea().getInstalacion().getCodInstalacion());
            stmActividade.setInt(3, actividade.getArea().getCodArea());

            // Realizamos a actualización:
            stmActividade.executeUpdate();

            // Para rematar, garantiremos que todos os usuarios anotados se enteren do borrado:
            stmMensaxes = con.prepareStatement("INSERT INTO enviarmensaxe (emisor, receptor, contido, lido) " +
                    " VALUES (?, ?, ?, ?)");
            stmMensaxes.setString(1, mensaxe.getEmisor().getLogin());
            stmMensaxes.setString(3, mensaxe.getContido());
            stmMensaxes.setBoolean(4, false);

            // Procesamos os usuarios participantes da actividade obtidos antes e imos engadíndoos:
            while (rsUsuarios.next()) {
                stmMensaxes.setString(2, rsUsuarios.getString("usuario"));
                // Facemos a actualización:
                stmMensaxes.executeUpdate();
            }

            // Facemos commit:
            con.commit();
        } catch (SQLException e) {
            // Lanzamos neste caso unha excepción cara a aplicación:
            throw new ExcepcionBD(con, e);
        } finally {
            // En calquera caso, téntase pechar os cursores.
            try {
                stmActividade.close();
                stmUsuarios.close();
                stmMensaxes.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }

    /**
     * Método que permite anotar un usuario a unha actividade como participante.
     *
     * @param actividade Actividade a que se desexa apuntar o usuario.
     * @param usuario    Usuario que se quer apuntar a dita actividade.
     * @throws ExcepcionBD Excepción asociada a inserción dunha nova tupla na táboa de realización de actividade.
     */
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

    /**
     * Método que permite listar as actividades filtrándoas a través dunha actividade modelo.
     *
     * @param actividade Actividade modelo que se empregará apra dito filtrado.
     * @return Retorna un ArrayList das actividades que cumpren dita condición ou, no caso de ser null,
     * un ArrayList con todas as posibles actividades.
     */
    public ArrayList<Actividade> buscarActividade(Actividade actividade) {
        PreparedStatement stmActividades = null;
        ResultSet rsActividades;
        Connection con;
        // Usaremos un ArrayList para almacenar as actividades:
        ArrayList<Actividade> actividades = new ArrayList<>();

        // Recuperamos a conexión:
        con = super.getConexion();

        // Preparamos a consulta:
        try {
            String consulta = "SELECT dataactividade, area, area.instalacion, tipoactividade, curso, profesor, " +
                    " actividade.nome, duracion, area.nome as areanome, instalacion.nome as instalacionnome " +
                    " FROM actividade JOIN area ON actividade.area=area.codarea  AND actividade.instalacion=area.instalacion " +
                    " JOIN instalacion ON area.codarea=instalacion.codinstalacion " +
                    " WHERE curso is NULL";

            // Engadimos o filtro de actividade polo que, se non é nula, filtramos polo nome:
            if (actividade != null) {
                consulta += " AND LOWER(actividade.nome) LIKE LOWER(?)  ";
            }

            // Ordenaremos o resultado pola data da actividade:
            consulta += " ORDER BY dataactividade asc";

            stmActividades = con.prepareStatement(consulta);

            // No caso de pasar a actividade non nula, completamos a consulta:
            if (actividade != null) {
                // Establecemos os valores da consulta segundo a actividade pasada:
                stmActividades.setString(1, "%" + actividade.getNome() + "%");
            }

            // Realizamos a consulta:
            rsActividades = stmActividades.executeQuery();

            // Recibida a consulta, procesámola:
            while (rsActividades.next()) {
                // Imos engadindo ao ArrayList do resultado cada área consultada:
                actividades.add(new Actividade(rsActividades.getTimestamp(1), rsActividades.getString("nome"),
                        rsActividades.getFloat("duracion"), new Area(rsActividades.getInt("area"), new Instalacion(rsActividades.getInt("instalacion"), rsActividades.getString("instalacionnome")), rsActividades.getString("areanome")), new TipoActividade(rsActividades.getInt("tipoactividade")), new Curso(rsActividades.getInt("curso")), new Persoal(rsActividades.getString("profesor"))));
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
            // Peche do statement:
            try {
                stmActividades.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
        return actividades;
    }

    /**
     * Método que permite recuperar os datos dunha actividade da base de datos a partir das súas claves primarias.
     *
     * @param actividade Actividade da que se obteñen os datos para realizar a consulta en función dos mesmos.
     * @return Retorna a Actividade cos datos actualizados.
     */
    public Actividade recuperarActividade(Actividade actividade) {
        PreparedStatement stmActividades = null;
        ResultSet rsActividades;
        Connection con;
        // Devolvemos unha Actividade como resultado:
        Actividade resultado = null;

        // Recuperamos a conexión:
        con = super.getConexion();

        // Preparamos a consulta:
        try {
            stmActividades = con.prepareStatement("SELECT dataactividade, area, area.instalacion, tipoactividade, curso, profesor, " +
                    " actividade.nome, duracion, area.nome as areanome, instalacion.nome as instalacionnome " +
                    " FROM actividade JOIN area ON actividade.area=area.codarea  AND actividade.instalacion=area.instalacion " +
                    " JOIN instalacion ON area.codarea=instalacion.codinstalacion " +
                    " WHERE curso is NULL AND dataactividade = ? AND area = ? AND actividade.instalacion = ? ORDER BY dataactividade asc");

            // Completamos ca información da actividade que, damos por feito que non é null (comprobase antes de tentar
            // recuperar a información que a actividade pasada non sexa null):
            stmActividades.setTimestamp(1, actividade.getData());
            stmActividades.setInt(2, actividade.getArea().getCodArea());
            stmActividades.setInt(3, actividade.getArea().getInstalacion().getCodInstalacion());

            // Realizamos a consulta:
            rsActividades = stmActividades.executeQuery();

            // Recibida a consulta, procesámola:
            if (rsActividades.next()) {
                // Creamos a actividade cos datos actualizados:
                resultado = new Actividade(rsActividades.getTimestamp(1), rsActividades.getString("nome"),
                        rsActividades.getFloat("duracion"), new Area(rsActividades.getInt("area"),
                        new Instalacion(rsActividades.getInt("instalacion"), rsActividades.getString("instalacionnome")),
                        rsActividades.getString("areanome")), new TipoActividade(rsActividades.getInt("tipoactividade")),
                        new Curso(rsActividades.getInt("curso")), new Persoal(rsActividades.getString("profesor")));
            }

            // Facemos commit:
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            // Peche do statement:
            try {
                stmActividades.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
        return resultado;
    }

    /**
     * Método que permite desapuntar un usuario dunha actividade.
     *
     * @param actividade Actividade a que se desexa desapuntar dito usuario.
     * @param usuario    Usuario que se desexa desapuntar de dita actividade.
     * @throws ExcepcionBD Excepción asociada o borrado dunha tupla na táboa de datos de realización de actividade.
     */
    public void borrarseDeActividade(Actividade actividade, Usuario usuario) throws ExcepcionBD {
        PreparedStatement stmActividade = null;
        ResultSet rsActividade;
        Connection con;
        // Recuperamos a conexión coa base de datos:
        con = super.getConexion();

        // Preparamos a inserción:
        try {
            stmActividade = con.prepareStatement("DELETE FROM realizaractividade " +
                    " WHERE dataactividade = ? and instalacion = ? and area = ? and usuario = ?");
            //Establecemos os valores
            stmActividade.setTimestamp(1, actividade.getData());
            stmActividade.setInt(2, actividade.getArea().getInstalacion().getCodInstalacion());
            stmActividade.setInt(3, actividade.getArea().getCodArea());
            stmActividade.setString(4, usuario.getLogin());

            // Realizamos a actualización:
            stmActividade.executeUpdate();

            // Facemos commit:
            con.commit();

        } catch (SQLException e) {
            // Lanzamos neste caso unha excepción cara a aplicación:
            throw new ExcepcionBD(con, e);
        } finally {
            // En calquera caso, téntase pechar os cursores.
            try {
                stmActividade.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }

    /**
     * Método que permite comprobar se un usuario esta apuntado.
     *
     * @param actividade Actividade na que se desexa comprobar se esta apuntado.
     * @param usuario    Usuario que se quere comprobar si esta apuntado.
     * @return Devolve true se o usuario esta apuntado en dita actividade e false en calquer outro caso.
     */
    public boolean estarApuntado(Actividade actividade, Usuario usuario) {
        PreparedStatement stmActividade = null;
        ResultSet rsActividade;
        Connection con;
        // Recuperamos a conexión coa base de datos:
        con = super.getConexion();

        // Preparamos a consulta:
        try {
            stmActividade = con.prepareStatement("SELECT dataactividade, area, instalacion, usuario " +
                    " FROM realizaractividade " +
                    " WHERE dataactividade = ? and area = ? and instalacion = ? and usuario = ?");

            // Establecemos os valores:
            stmActividade.setTimestamp(1, actividade.getData());
            stmActividade.setInt(2, actividade.getArea().getCodArea());
            stmActividade.setInt(3, actividade.getArea().getInstalacion().getCodInstalacion());
            stmActividade.setString(4, usuario.getLogin());

            // Facemos a consulta:
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
            // En calquera caso, téntase pechar os cursores.
            try {
                stmActividade.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
        return false;
    }

    /**
     * Método que permite listar os profesores ca posibilidade de listar en función dun tipo de actividade.
     *
     * @param tipoactividade Tipo de actividade para a que se comprobarán, de non ser nula, os profesores que hai.
     * @return Devolve un ArrayList cos profesores que cumpran ditas condicións de filtrado.
     */
    public ArrayList<Persoal> buscarProfesores(TipoActividade tipoactividade) {
        PreparedStatement stmAreas = null;
        ResultSet rsProfes;
        Connection con;
        // Usaremos un ArrayList para almacenar todos os profesores que correspondan:
        ArrayList<Persoal> profesores = new ArrayList<>();

        // Recuperamos a conexión:
        con = super.getConexion();

        // Preparamos a consulta:
        try {
            String consulta = "SELECT persoal " +
                    " FROM estarcapacitado ";

            // A esta consulta, ademais do anterior, engadiremos os filtros se o tipo de actividade non é nulo:
            if (tipoactividade != null) {
                consulta += " WHERE tipoactividade = ?";
            }

            // Ordenaremos o resultado según o persoal en orde ascendente:
            consulta += " ORDER BY persoal asc";

            stmAreas = con.prepareStatement(consulta);

            // Se o tipo de actividade pasado non é nulo, completase a consulta:
            if (tipoactividade != null) {
                stmAreas.setInt(1, tipoactividade.getCodTipoActividade());
            }

            // Realizamos a consulta:
            rsProfes = stmAreas.executeQuery();

            // Recibida a consulta, procesámola:
            while (rsProfes.next()) {
                // Imos engadindo ao ArrayList do resultado cada profesor consultado:
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
            // Peche do statement:
            try {
                stmAreas.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
        return profesores;
    }

    /**
     * Método que comproba se o aforo da actividade é o máximo.
     *
     * @param actividade Actividade da que se desexa comprobar o aforo.
     * @return Retorna true no caso de que o aforo non sexa o máximo e false en caso contrario.
     */
    public boolean NonEMaximoAforoActividade(Actividade actividade) {
        PreparedStatement stmActividade = null;
        ResultSet rsActividade;
        Connection con;

        // Recuperamos a conexión coa base de datos:
        con = super.getConexion();

        // Preparamos a consulta:
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

            // Establecemos os valores:
            stmActividade.setInt(1, actividade.getArea().getCodArea());
            stmActividade.setInt(2, actividade.getArea().getInstalacion().getCodInstalacion());
            stmActividade.setTimestamp(3, actividade.getData());
            stmActividade.setInt(4, actividade.getArea().getCodArea());
            stmActividade.setInt(5, actividade.getArea().getInstalacion().getCodInstalacion());

            //F acemos a consulta:
            rsActividade = stmActividade.executeQuery();


            if (rsActividade.next())
                return true;
            return false;

        } catch (SQLException e) {
            // Imprimimos en caso de excepción o stack trace e facemos o rollback:
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            // En calquera caso, téntase pechar os cursores.
            try {
                stmActividade.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
        return false;
    }

    /**
     * Método que permite listar as actividades onde NON participa certo usuario.
     *
     * @param actividade Actividade modelo que se empregará para realizar un filtrado.
     * @param usuario    Usuario que se desexa asegurar que NON particida nas actividades
     * @return Devolve un ArrayList que compre cas condicións de filtrado da actividade en función do usuario pasado.
     */
    public ArrayList<Actividade> buscarActividadeNONParticipa(Actividade actividade, Usuario usuario) {
        PreparedStatement stmActividades = null;
        ResultSet rsActividades;
        Connection con;
        // Usaremos un ArrayList para almacenar ditas actividades:
        ArrayList<Actividade> actividades = new ArrayList<>();

        // Recuperamos a conexión:
        con = super.getConexion();

        // Preparamos a consulta:
        try {
            String consulta = "SELECT actividade.dataactividade, actividade.area, actividade.instalacion, actividade.tipoactividade as tipoactividade, curso, profesor, actividade.nome as nome, duracion, tipoactividade.nome as nomeactividade, area.nome as nomearea, instalacion.nome as nomeinstalacion  " +
                    " FROM actividade, tipoactividade, area, instalacion " +
                    " WHERE actividade.tipoactividade=tipoactividade.codtipoactividade " +
                    "   AND area.instalacion=instalacion.codinstalacion " +
                    "   AND area.codarea=actividade.area " +
                    "   AND area.instalacion=actividade.instalacion " +
                    "   AND curso is null" +
                    "   AND actividade.dataactividade>NOW()" +
                    "   AND (actividade.dataactividade, actividade.area, actividade.instalacion) NOT IN " +
                    "                           (SELECT dataactividade, area, instalacion " +
                    "                            FROM realizaractividade " +
                    "                            WHERE dataactividade=actividade.dataactividade " +
                    "                            AND area=actividade.area " +
                    "                            AND instalacion=actividade.instalacion " +
                    "                            AND usuario=? )";


            // A esta consulta, ademais do anterior, engadiremos os filtros por se se pasa unha actividade non nula,
            // filtraremos en función de coincidencias co nome:
            if (actividade != null) {
                consulta += " AND lower(actividade.nome) like lower(?)  ";

                if (actividade.getArea() != null) {
                    consulta += " AND actividade.instalacion=? ";
                    if (actividade.getArea().getCodArea() >= 0) {
                        consulta += " AND actividade.area=? ";
                    }
                }
            }
            consulta += " ORDER BY actividade.dataactividade asc ";

            stmActividades = con.prepareStatement(consulta);

            stmActividades.setString(1, usuario.getLogin());

            // No caso de que a actividade non sexa nula, completamos ditos campos:
            if (actividade != null) {
                stmActividades.setString(2, "%" + actividade.getNome() + "%");

                if (actividade.getArea() != null) {
                    stmActividades.setInt(3, actividade.getArea().getInstalacion().getCodInstalacion());
                    if (actividade.getArea().getCodArea() >= 0) {
                        stmActividades.setInt(4, actividade.getArea().getCodArea());
                    }
                }
            }

            // Realizamos a consulta:
            rsActividades = stmActividades.executeQuery();

            // Recibida a consulta, procesámola:
            while (rsActividades.next()) {
                //Imos engadindo ao ArrayList do resultado as actividades que cumplan os criterios aplicados:
                actividades.add(new Actividade(rsActividades.getTimestamp(1), rsActividades.getString("nome"),
                        rsActividades.getFloat("duracion"), new Area(rsActividades.getInt("area"),
                        new Instalacion(rsActividades.getInt("instalacion"),
                                rsActividades.getString("nomeinstalacion")), rsActividades.getString("nomearea")),
                        new TipoActividade(rsActividades.getInt("tipoactividade"),
                                rsActividades.getString("nomeactividade")), new Curso(rsActividades.getInt("curso")),
                        new Persoal(rsActividades.getString("profesor"))));
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
            // Peche do statement:
            try {
                if (stmActividades != null) stmActividades.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
        return actividades;
    }

    /**
     * Método que permite listar as actividades nas que participa certo usuario.
     *
     * @param actividade Actividade modelo que se empregará para realizar un filtrado.
     * @param usuario    Usuario que se desexa asegurar que participa nas actividades.
     * @return Devolve un ArrayList que compre cas condicións de filtrado en función do usuario pasado.
     */
    public ArrayList<Actividade> buscarActividadeParticipa(Actividade actividade, Usuario usuario) {
        PreparedStatement stmActividades = null;
        ResultSet rsActividades;
        Connection con;
        // Usaremos un ArrayList para almacenar o resultado:
        ArrayList<Actividade> actividades = new ArrayList<>();

        // Recuperamos a conexión:
        con = super.getConexion();

        // Preparamos a consulta:
        try {
            String consulta =
                    "SELECT " +
                            "actividade.dataactividade, " +
                            "actividade.area, actividade.instalacion, " +
                            "actividade.tipoactividade as tipoactividade, " +
                            "curso, " +
                            "profesor, " +
                            "actividade.nome as nome, " +
                            "duracion, " +
                            "tipoactividade.nome as nomeactividade, " +
                            "area.nome as nomearea, " +
                            "instalacion.nome as nomeinstalacion " +
                            " FROM actividade, tipoactividade, realizaractividade, instalacion, area " +
                            " WHERE " +
                            "actividade.tipoactividade=tipoactividade.codtipoactividade " +
                            "   AND area.instalacion=instalacion.codinstalacion " +
                            "   AND area.codarea=actividade.area " +
                            "   AND area.instalacion=actividade.instalacion" +
                            "   AND realizaractividade.dataactividade=actividade.dataactividade " +
                            "   AND realizaractividade.area=actividade.area " +
                            "   AND realizaractividade.instalacion=actividade.instalacion " +
                            "   AND usuario=? ";

            if (actividade != null) {
                consulta += " AND lower(actividade.nome) like lower(?)  ";

                if (actividade.getArea() != null) {
                    consulta += " AND actividade.instalacion=? ";
                    if (actividade.getArea().getCodArea() >= 0) {
                        consulta += " AND actividade.area=? ";
                    }
                }
            }
            consulta += " ORDER BY actividade.dataactividade desc ";

            stmActividades = con.prepareStatement(consulta);

            stmActividades.setString(1, usuario.getLogin());

            if (actividade != null) {
                stmActividades.setString(2, "%" + actividade.getNome() + "%");

                if (actividade.getArea() != null) {
                    stmActividades.setInt(3, actividade.getArea().getInstalacion().getCodInstalacion());
                    if (actividade.getArea().getCodArea() >= 0) {
                        stmActividades.setInt(4, actividade.getArea().getCodArea());
                    }
                }
            }

            // Realizamos a consulta:
            rsActividades = stmActividades.executeQuery();

            // Recibida a consulta, procesámola:
            while (rsActividades.next()) {
                // Imos engadindo ao ArrayList do resultado as actividades devoltas:
                actividades.add(new Actividade(
                        rsActividades.getTimestamp(1),
                        rsActividades.getString("nome"),
                        rsActividades.getFloat("duracion"),
                        new Area(
                                rsActividades.getInt("area"),
                                new Instalacion(
                                        rsActividades.getInt("instalacion"),
                                        rsActividades.getString("nomeinstalacion")
                                ),
                                rsActividades.getString("nomearea")
                        ),
                        new TipoActividade(
                                rsActividades.getInt("tipoactividade"),
                                rsActividades.getString("nomeactividade")
                        ),
                        new Curso(rsActividades.getInt("curso")),
                        new Persoal(rsActividades.getString("profesor"))));
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
            // Peche do statement:
            try {
                if (stmActividades != null) stmActividades.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
        return actividades;
    }

    /**
     * Método que permite valorar por certo usuario unha actividade na que participará.
     *
     * @param valoracion Puntuación que lle será asignada a dita actividade.
     * @param actividade Actividade que se desexa valorar.
     * @param usuario    Usuario que esta a valorar dita actividade.
     * @throws ExcepcionBD Excepción asociada a actualización na base de datos.
     */
    public void valorarActividade(Integer valoracion, Actividade actividade, Usuario usuario) throws ExcepcionBD {
        // Neste metodo actualizamos a taboa realizar actividade para realizar unha valoración da mesma:
        PreparedStatement stmActividade = null;
        Connection con;

        // Recuperamos a conexión coa base de datos:
        con = super.getConexion();

        try {
            // Intentamos levar a cabo a actualización: modificacion na taboa realizar actividade:
            stmActividade = con.prepareStatement("UPDATE realizaractividade " +
                    "SET valoracion = ? " +
                    "WHERE dataactividade = ? " +
                    "AND area = ? " +
                    "AND instalacion = ? " +
                    "AND usuario = ? ");

            // Completamos a sentenza anterior cos '?':
            stmActividade.setInt(1, valoracion);
            stmActividade.setTimestamp(2, actividade.getData());
            stmActividade.setInt(3, actividade.getArea().getCodArea());
            stmActividade.setInt(4, actividade.getArea().getInstalacion().getCodInstalacion());
            stmActividade.setString(5, usuario.getLogin());

            // Realizamos a actualización:
            stmActividade.executeUpdate();

            // Unha vez feita, teremos rematado. Facemos o commit:
            con.commit();
        } catch (SQLException e) {
            // Lanzamos a excepción que se obteña:
            throw new ExcepcionBD(con, e);
        } finally {
            // Pechamos os statement.
            try {
                stmActividade.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }

    /**
     * Método que comproba se certo profesor está activo.
     *
     * @param profesor Profesor que se desexa comprobar se esta activo.
     * @return Retorna true se o profesor esta activo e false en caso contrario.
     */
    public boolean EProfesorActivo(Persoal profesor) {
        PreparedStatement stmActividade = null;
        ResultSet rsActividade;
        Connection con;

        // Recuperamos a conexión coa base de datos:
        con = super.getConexion();

        // Preparamos a consulta:
        try {
            stmActividade = con.prepareStatement(
                    " SELECT profesoractivo " +
                            " FROM persoal " +
                            " WHERE profesoractivo=TRUE AND login=? "
            );

            // Establecemos os valores:
            stmActividade.setString(1, profesor.getLogin());

            // Facemos a consulta:
            rsActividade = stmActividade.executeQuery();

            if (rsActividade.next())
                return true;
            return false;

        } catch (SQLException e) {
            // Imprimimos en caso de excepción o stack trace e facemos o rollback:
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            // En calquera caso, téntase pechar os cursores.
            try {
                stmActividade.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
        return false;
    }

    /**
     * Método que comproba se unha actividade foi valorada por un usuario.
     *
     * @param actividade Actividade que se comproba se foi valorada.
     * @param usuario    Usuario que se comproba se a valorou.
     * @return Devolve true se a actividade xa foi valorada.
     */
    public boolean isValorada(Actividade actividade, Usuario usuario) {
        // Neste metodo comprobamos se a actividade foi valorada
        PreparedStatement stmActividade = null;
        ResultSet rsActividade;
        Connection con;
        boolean resultado = true;

        // Recuperamos a conexión coa base de datos
        con = super.getConexion();

        try {
            // Intentamos levar a cabo a consulta
            stmActividade = con.prepareStatement("SELECT * " +
                    "FROM realizaractividade " +
                    "WHERE dataactividade = ? " +
                    "AND area = ? " +
                    "AND instalacion = ? " +
                    "AND usuario = ? AND valoracion IS NULL ");

            // Completamos a sentenza anterior cos '?':
            stmActividade.setTimestamp(1, actividade.getData());
            stmActividade.setInt(2, actividade.getArea().getCodArea());
            stmActividade.setInt(3, actividade.getArea().getInstalacion().getCodInstalacion());
            stmActividade.setString(4, usuario.getLogin());

            // Realizamos a consulta:
            rsActividade = stmActividade.executeQuery();
            resultado = !rsActividade.next();

            // Unha vez feita, teremos rematado. Facemos o commit:
            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            // Pechamos os statement.
            try {
                assert stmActividade != null;
                stmActividade.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
        return resultado;
    }

    /**
     * Método que permite listar todos os participantes dunha actividade.
     *
     * @param actividade Actividade da que se queren listar os seus participantes.
     * @return Retorna un ArrayList con todos os participantes que estan apuntados na mesma.
     */
    public ArrayList<Socio> listarParticipantes(Actividade actividade) {
        PreparedStatement stmActividades = null;
        ResultSet rsUsuario;
        Connection con;
        // Usaremos un ArrayList para gardar os participantes:
        ArrayList<Socio> usuarios = new ArrayList<>();

        // Recuperamos a conexión coa base de datos:
        con = super.getConexion();

        // Preparamos a consulta:
        try {
            stmActividades = con.prepareStatement("SELECT vs.login as login," +
                    "vs.nome as nome, " +
                    "vs.dificultades as dificultades, " +
                    "date_part('year', age(vs.datanacemento)) as idade " +
                    "FROM vistasocio as vs " +
                    "JOIN realizaractividade r on vs.login = r.usuario " +
                    "WHERE r.dataactividade = ? AND r.area = ? AND r.instalacion = ? ");

            stmActividades.setTimestamp(1, actividade.getData());
            stmActividades.setInt(2, actividade.getArea().getCodArea());
            stmActividades.setInt(3, actividade.getArea().getInstalacion().getCodInstalacion());

            // Realizamos a consulta:
            rsUsuario = stmActividades.executeQuery();

            // Recibida a consulta, procesámola:
            while (rsUsuario.next()) {
                // Imos engadindo ao ArrayList todos os Socios que nos son devoltos:
                usuarios.add(new Socio(rsUsuario.getString("login"), rsUsuario.getString("nome"),
                        rsUsuario.getString("dificultades"), rsUsuario.getInt("idade")));
            }
            // Facemos commit:
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            // Tentamos pechar os cursores:
            try {
                stmActividades.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
        return usuarios;
    }
}
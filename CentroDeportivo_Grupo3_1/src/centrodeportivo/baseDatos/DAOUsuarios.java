package centrodeportivo.baseDatos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
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

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public final class DAOUsuarios extends AbstractDAO {

    /**
     * @param conexion          Conexión coa base de datos
     * @param fachadaAplicacion fachada da aplicación
     */
    protected DAOUsuarios(Connection conexion, FachadaAplicacion fachadaAplicacion) {
        super(conexion, fachadaAplicacion);
    }

    /**
     * Método para comprobar se un login xa existe na base de datos.
     *
     * @param login login buscado
     * @return true se existe ese login, false se non.
     */
    protected boolean existeUsuario(String login) {
        PreparedStatement stmUsuario = null;
        ResultSet resultValidacion;
        try {
            stmUsuario = super.getConexion().prepareStatement("SELECT * FROM usuario WHERE LOWER(login)=LOWER(?)");
            stmUsuario.setString(1, login);
            resultValidacion = stmUsuario.executeQuery();
            return resultValidacion.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmUsuario.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return false;
    }

    /**
     * Método para comprobar se existe unha persoa concreta na base de datos.
     *
     * @param DNI DNI buscado.
     * @return true se existe esa persoa, false se non.
     */
    protected boolean existePersoaFisica(String DNI) {
        PreparedStatement stmUsuario = null;
        ResultSet resultValidacion;
        try {
            stmUsuario = super.getConexion().prepareStatement("SELECT * FROM persoafisica WHERE UPPER(DNI)=UPPER(?)");
            stmUsuario.setString(1, DNI);
            resultValidacion = stmUsuario.executeQuery();
            return resultValidacion.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmUsuario.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return false;
    }

    /**
     * Método para comprobar se xa existe un dni na base de datos.
     *
     * @param dni DNI buscado.
     * @return true se existe ese DNI, false se non.
     */
    protected boolean existeDNI(String dni) {
        PreparedStatement stmUsuario = null;
        ResultSet resultValidacion;
        try {
            stmUsuario = super.getConexion().prepareStatement("SELECT * FROM persoaFisica WHERE UPPER(DNI)=UPPER(?)");
            stmUsuario.setString(1, dni);
            resultValidacion = stmUsuario.executeQuery();
            return resultValidacion.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmUsuario.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return false;
    }

    /**
     * Método para comprobar se xa existe un número da seguridade social na base de datos.
     *
     * @param nuss nuss buscado.
     * @return true se existe ese nuss, false se non.
     */
    protected boolean existeNUSS(String nuss) {
        PreparedStatement stmUsuario = null;
        ResultSet resultValidacion;
        try {
            stmUsuario = super.getConexion().prepareStatement("SELECT * FROM usuario NATURAL JOIN persoal WHERE UPPER(nuss)=UPPER(?)");
            stmUsuario.setString(1, nuss);
            resultValidacion = stmUsuario.executeQuery();
            return resultValidacion.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmUsuario.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return false;
    }

    /**
     * Método para comprobar se unha persoa ten conta, e en caso afirmativo, que tipos de contas ten.
     *
     * @param dni dni da persoa bucada
     * @return enum indicando as contas que ten esa persoa
     */
    protected ContasPersoa contasPersoaFisica(String dni) {
        PreparedStatement stmUsuario = null;
        ResultSet resultValidacion;
        try {
            stmUsuario = super.getConexion().prepareStatement("SELECT * FROM persoaFisica WHERE UPPER(dni)=UPPER(?)");
            stmUsuario.setString(1, dni);
            resultValidacion = stmUsuario.executeQuery();
            if (resultValidacion.next()) {
                if (resultValidacion.getString("usuarioSocio") != null && resultValidacion.getString("usuarioPersoal") != null)
                    return ContasPersoa.Ambas;
                else if (resultValidacion.getString("usuarioSocio") == null && resultValidacion.getString("usuarioPersoal") != null)
                    return ContasPersoa.SoloPersoal;
                else if (resultValidacion.getString("usuarioSocio") != null && resultValidacion.getString("usuarioPersoal") == null)
                    return ContasPersoa.SoloSocio;
            } else {
                return ContasPersoa.Ningunha;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmUsuario.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return ContasPersoa.Ningunha;
    }

    /**
     * Método para comprobar se os datos introducidos corresponden cun usuario válido.
     *
     * @param login    login introducido
     * @param password contrasinal introducido
     * @return true se se valida, false se non
     */
    protected boolean validarUsuario(String login, String password) {
        PreparedStatement stmUsuario = null;
        ResultSet resultValidacion;
        try {
            stmUsuario = super.getConexion().prepareStatement("SELECT * FROM usuario WHERE LOWER(login)=LOWER(?) AND contrasinal=? AND dataBaixa IS NULL");
            stmUsuario.setString(1, login);
            stmUsuario.setString(2, password);
            resultValidacion = stmUsuario.executeQuery();
            return resultValidacion.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmUsuario.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return false;
    }


    /**
     * Método para consultar os datos dunha persoa física.
     *
     * @param DNI dni da persoa.
     * @return PersoaFisica cos datos da persoa.
     */
    protected PersoaFisica consultarPersoaFisica(String DNI) {
        PreparedStatement stmPersoa = null;
        ResultSet resultPersoa;
        try {
            stmPersoa = super.getConexion().prepareStatement("SELECT * FROM persoafisica WHERE UPPER(DNI)=UPPER(?)");
            stmPersoa.setString(1, DNI);
            resultPersoa = stmPersoa.executeQuery();
            if (resultPersoa.next()) {
                return new PersoaFisica(
                        resultPersoa.getString("DNI"),
                        resultPersoa.getString("nome"),
                        resultPersoa.getString("dificultades"),
                        resultPersoa.getDate("datanacemento")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmPersoa.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
        return null;

    }

    /**
     * Método para insertar un usuario na base de datos.
     *
     * @param usuario usuario a ser insertado
     * @throws ExcepcionBD
     */
    protected void insertarUsuario(Usuario usuario) throws ExcepcionBD {
        PreparedStatement stmUsuario = null;
        try {
            stmUsuario = super.getConexion().prepareStatement("INSERT INTO usuario (login,contrasinal,numTelefono,correoElectronico,IBAN)  VALUES (?,?,?,?,?);");
            stmUsuario.setString(1, usuario.getLogin());
            stmUsuario.setString(2, usuario.getContrasinal());
            stmUsuario.setString(3, usuario.getNumTelefono());
            stmUsuario.setString(4, usuario.getCorreoElectronico());
            stmUsuario.setString(5, usuario.getIBANconta());
            stmUsuario.executeUpdate();

            if (usuario instanceof Socio) {
                Socio socio = (Socio) usuario;
                stmUsuario = super.getConexion().prepareStatement("INSERT INTO socio (login,tarifa) values (?,?);");
                stmUsuario.setString(1, socio.getLogin());
                stmUsuario.setInt(2, socio.getTarifa().getCodTarifa());
                stmUsuario.executeUpdate();

            } else if (usuario instanceof Persoal) {
                Persoal persoal = (Persoal) usuario;
                stmUsuario = super.getConexion().prepareStatement("INSERT INTO persoal (login,NUSS,profesoractivo) VALUES (?,?,?);");
                stmUsuario.setString(1, persoal.getLogin());
                stmUsuario.setString(2, persoal.getNUSS());
                stmUsuario.setBoolean(3, persoal.getTipoUsuario() == TipoUsuario.Profesor);
                stmUsuario.executeUpdate();
            }

            if (!existePersoaFisica(usuario.getDNI())) {
                if ((usuario.getTipoUsuario() == TipoUsuario.Socio))
                    stmUsuario = super.getConexion().prepareStatement("INSERT INTO persoafisica(dni,datanacemento,dificultades,nome,usuariosocio) VALUES (UPPER(?),?,?,?,?);");
                else if ((usuario.getTipoUsuario() == TipoUsuario.Persoal || usuario.getTipoUsuario() == TipoUsuario.Profesor))
                    stmUsuario = super.getConexion().prepareStatement("INSERT INTO persoafisica(dni,datanacemento,dificultades,nome,usuariopersoal) VALUES (UPPER(?),?,?,?,?);");

                stmUsuario.setString(1, usuario.getDNI());
                stmUsuario.setDate(2, usuario.getDataNacemento());
                stmUsuario.setString(3, usuario.getDificultades());
                stmUsuario.setString(4, usuario.getNome());
                stmUsuario.setObject(5, usuario.getLogin());
            } else {
                if ((usuario.getTipoUsuario() == TipoUsuario.Socio))
                    stmUsuario = super.getConexion().prepareStatement("UPDATE persoafisica SET usuariosocio=? WHERE UPPER(dni)=UPPER(?);");
                else if ((usuario.getTipoUsuario() == TipoUsuario.Persoal || usuario.getTipoUsuario() == TipoUsuario.Profesor))
                    stmUsuario = super.getConexion().prepareStatement("UPDATE persoafisica SET usuariopersoal=? WHERE UPPER(dni)=UPPER(?);");
                stmUsuario.setString(1, usuario.getLogin());
                stmUsuario.setString(2, usuario.getDNI());
            }
            stmUsuario.executeUpdate();
            super.getConexion().commit();

        } catch (SQLException e) {
            throw new ExcepcionBD(super.getConexion(), e);
        } finally {
            try {
                if (stmUsuario != null) stmUsuario.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
    }

    /**
     * Método para actualizar a información dun usuario
     *
     * @param usuario usuario a ser actualizado
     * @throws ExcepcionBD
     */
    protected void actualizarUsuario(Usuario usuario) throws ExcepcionBD {
        PreparedStatement stmUsuario = null;

        try {
            stmUsuario = super.getConexion().prepareStatement("UPDATE usuario SET login=?,contrasinal=?,numTelefono=?,correoElectronico=?,IBAN=?,dataBaixa=NULL WHERE LOWER(login)=LOWER(?);");
            stmUsuario.setString(1, usuario.getLogin());
            stmUsuario.setString(2, usuario.getContrasinal());
            stmUsuario.setString(3, usuario.getNumTelefono());
            stmUsuario.setString(4, usuario.getCorreoElectronico());
            stmUsuario.setString(5, usuario.getIBANconta());
            stmUsuario.setString(6, usuario.getLogin());
            stmUsuario.executeUpdate();

            stmUsuario = super.getConexion().prepareStatement("UPDATE persoafisica SET nome=?,dificultades=?,datanacemento=? WHERE UPPER(dni)=UPPER(?);");
            stmUsuario.setString(1, usuario.getNome());
            stmUsuario.setString(2, usuario.getDificultades());
            stmUsuario.setDate(3, usuario.getDataNacemento());
            stmUsuario.setString(4, usuario.getDNI());
            stmUsuario.executeUpdate();

            if (usuario instanceof Socio) {
                Socio socio = (Socio) usuario;
                stmUsuario = super.getConexion().prepareStatement("UPDATE socio SET tarifa=? WHERE LOWER(login)=LOWER(?);");
                stmUsuario.setInt(1, socio.getTarifa().getCodTarifa());
                stmUsuario.setString(2, socio.getLogin());
                stmUsuario.executeUpdate();
            } else if (usuario instanceof Persoal) {
                Persoal persoal = (Persoal) usuario;
                stmUsuario = super.getConexion().prepareStatement("UPDATE persoal SET profesoractivo=? WHERE LOWER(login)=LOWER(?);");
                stmUsuario.setBoolean(1, persoal.getTipoUsuario() == TipoUsuario.Profesor);
                stmUsuario.setString(2, persoal.getLogin());
                stmUsuario.executeUpdate();
            }
            super.getConexion().commit();
        } catch (SQLException e) {
            throw new ExcepcionBD(super.getConexion(), e);
        } finally {
            try {
                if (stmUsuario != null) stmUsuario.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
    }

    /**
     * Método para dar de baixa un usuario da base de datos.
     *
     * @param usuario usuario a ser dado de baixa
     * @throws ExcepcionBD
     */
    protected void darBaixaUsuario(Usuario usuario) throws ExcepcionBD {
        PreparedStatement stmUsuario = null;

        try {
            stmUsuario = super.getConexion().prepareStatement(
                    "UPDATE usuario " +
                            "SET dataBaixa=NOW(), " +
                            "correoElectronico=NULL, " +
                            "numTelefono=NULL, " +
                            "contrasinal=NULL, " +
                            "iban=NULL " +
                            "WHERE LOWER(login)=LOWER(?);"
            );
            stmUsuario.setString(1, usuario.getLogin());
            stmUsuario.executeUpdate();

            if (usuario instanceof Socio) {
                stmUsuario = super.getConexion().prepareStatement(
                        "UPDATE persoaFisica " + "" +
                                "SET nome=NULL, " +
                                "dificultades=NULL, " +
                                "dataNacemento=NULL " +
                                "WHERE LOWER(usuarioSocio)=LOWER(?) AND " +
                                "(SELECT TRUE FROM usuario WHERE login=usuarioPersoal AND dataBaixa IS NOT NULL);"
                );
                stmUsuario.setString(1, usuario.getLogin());
                stmUsuario.executeUpdate();
            } else if (usuario instanceof Persoal) {
                stmUsuario = super.getConexion().prepareStatement(
                        "UPDATE persoaFisica " + "" +
                                "SET nome=NULL, " +
                                "dificultades=NULL, " +
                                "dataNacemento=NULL " +
                                "WHERE LOWER(usuarioPersoal)=LOWER(?) AND " +
                                "(SELECT TRUE FROM usuario WHERE login=usuarioSocio AND dataBaixa IS NOT NULL);"
                );
                stmUsuario.setString(1, usuario.getLogin());
                stmUsuario.executeUpdate();
            }
            super.getConexion().commit();
        } catch (SQLException e) {
            throw new ExcepcionBD(super.getConexion(), e);
        } finally {
            try {
                stmUsuario.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
    }

    /**
     * Método para engadir unha capacidade a un persoal
     *
     * @param login          login do persoal
     * @param tipoActividade tipo de actividade para engadirlle
     * @throws ExcepcionBD
     */
    protected void engadirCapadidade(String login, TipoActividade tipoActividade) throws ExcepcionBD {
        PreparedStatement stmAct = null;

        try {
            stmAct = super.getConexion().prepareStatement("INSERT INTO estarCapacitado (tipoActividade,persoal)  VALUES (?,?);");
            stmAct.setInt(1, tipoActividade.getCodTipoActividade());
            stmAct.setString(2, login);
            stmAct.executeUpdate();
            super.getConexion().commit();
        } catch (SQLException e) {
            throw new ExcepcionBD(super.getConexion(), e);
        } finally {
            try {
                stmAct.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
    }

    /**
     * Método para eliminar unha capacidade dun persoal
     *
     * @param login          login do persoal
     * @param tipoActividade tipo de actividade para eliminar
     * @throws ExcepcionBD
     */
    protected void eliminarCapacidade(String login, TipoActividade tipoActividade) throws ExcepcionBD {
        PreparedStatement stmAct = null;

        try {
            stmAct = super.getConexion().prepareStatement("DELETE FROM estarCapacitado WHERE tipoActividade=? AND LOWER(persoal)=LOWER(?);");
            stmAct.setInt(1, tipoActividade.getCodTipoActividade());
            stmAct.setString(2, login);
            stmAct.executeUpdate();
            super.getConexion().commit();
        } catch (SQLException e) {
            throw new ExcepcionBD(super.getConexion(), e);
        } finally {
            try {
                stmAct.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
    }

    /**
     * Método para listar os tipos de actividades que pode impartir un profesor.
     *
     * @param login login do persoal
     * @return lista de capacidades dun persoal
     */
    protected ArrayList<TipoActividade> listarCapacidades(String login) {
        PreparedStatement stmCapacidades = null;
        ArrayList<TipoActividade> tipoActividades = new ArrayList<TipoActividade>();
        ResultSet rsCapacidades;
        try {
            stmCapacidades = super.getConexion().prepareStatement("SELECT * FROM estarcapacitado AS ec JOIN tipoactividade AS ta  ON ec.tipoactividade=ta.codtipoactividade WHERE LOWER(ec.persoal)=LOWER(?);");
            stmCapacidades.setString(1, login);
            rsCapacidades = stmCapacidades.executeQuery();
            while (rsCapacidades.next()) {
                tipoActividades.add(new TipoActividade(
                        rsCapacidades.getInt("codtipoactividade"),
                        rsCapacidades.getString("nome"),
                        rsCapacidades.getString("descricion")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmCapacidades.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return tipoActividades;
    }

    /**
     * Método para consultar o tipo de usuario dun usuario.
     *
     * @param login login do usuario
     * @return tipo de usuario
     */
    protected TipoUsuario consultarTipo(String login) {
        PreparedStatement stmUsuario = null;
        ResultSet rsUsuarios;

        try {
            stmUsuario = super.getConexion().prepareStatement("SELECT " +
                    "login," +
                    "(SELECT 1 FROM socio AS s WHERE s.login=u.login) AS eSocio," +
                    "(SELECT 1 FROM persoal AS pe WHERE pe.login=u.login AND profesoractivo=FALSE) AS ePersoal, " +
                    "(SELECT 1 FROM persoal AS pe WHERE pe.login=u.login AND profesoractivo=TRUE) AS eProfesor  " +
                    "FROM usuario as u WHERE LOWER(u.login)=LOWER(?)"
            );
            stmUsuario.setString(1, login);

            rsUsuarios = stmUsuario.executeQuery();
            rsUsuarios.next();
            if (rsUsuarios.getBoolean("eSocio")) return TipoUsuario.Socio;
            if (rsUsuarios.getBoolean("ePersoal")) return TipoUsuario.Persoal;
            if (rsUsuarios.getBoolean("eProfesor")) return TipoUsuario.Profesor;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmUsuario.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }

        return null;
    }

    /**
     * Método para encontrar unha lista de usuarios que satisfagan as condicións pasadas como parámetros.
     *
     * @param login           login buscado
     * @param nome            nome buscado
     * @param filtroTipo      tipos de usuario buscados
     * @param usuariosDeBaixa incluir usuarios de baixa no resultado
     * @return lista de usuarios encontrados
     */
    protected ArrayList<Usuario> buscarUsuarios(String login, String nome, TipoUsuario filtroTipo, boolean usuariosDeBaixa) {
        String usBaixa = "";
        if (!usuariosDeBaixa) usBaixa = " AND (dataBaixa IS NULL) ";
        PreparedStatement stmUsuario = null;
        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
        ResultSet rsUsuarios;

        try {
            if (filtroTipo == TipoUsuario.Socio || filtroTipo == TipoUsuario.Todos) {
                stmUsuario = super.getConexion().prepareStatement("SELECT *,vs.nome AS nomeUsuario,t.nome AS nomeTarifa FROM vistasocio AS vs JOIN tarifa AS t ON vs.tarifa=t.codTarifa  " +
                        "WHERE ((LOWER(vs.login) LIKE LOWER(?) AND LOWER(vs.nome) LIKE LOWER(?)) OR (vs.nome IS NULL))  " + usBaixa +
                        "ORDER BY login ASC;"
                );
                stmUsuario.setString(1, "%" + login + "%");
                stmUsuario.setString(2, "%" + nome + "%");
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
                            rsUsuarios.getDate("dataAlta"),
                            rsUsuarios.getDate("dataBaixa"),
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
            if (filtroTipo == TipoUsuario.Profesor || filtroTipo == TipoUsuario.Persoal || filtroTipo == TipoUsuario.Todos) {
                String conds = "";
                if (filtroTipo == TipoUsuario.Profesor) conds = "AND profesoractivo=TRUE ";
                if (filtroTipo == TipoUsuario.Persoal) conds = "AND profesoractivo=FALSE ";

                stmUsuario = super.getConexion().prepareStatement("SELECT * FROM vistapersoal AS vp  " +
                        "WHERE ((LOWER(vp.login) LIKE LOWER(?) AND LOWER(vp.nome) LIKE LOWER(?))  OR (vp.nome IS NULL)) " + conds + usBaixa +
                        "ORDER BY login ASC;"
                );
                stmUsuario.setString(1, "%" + login + "%");
                stmUsuario.setString(2, "%" + nome + "%");
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
                            rsUsuarios.getDate("dataAlta"),
                            rsUsuarios.getDate("dataBaixa"),
                            rsUsuarios.getString("nuss"),
                            rsUsuarios.getBoolean("profesoractivo")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmUsuario.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return usuarios;
    }


    /**
     * Método para obter os datos dun usuario
     *
     * @param login login do usuario
     * @return Usuario coa información
     */
    protected Usuario consultarUsuario(String login) {
        PreparedStatement stmUsuario = null;
        ResultSet rsUsuarios;
        TipoUsuario tipoUsuario = consultarTipo(login);
        try {
            if (tipoUsuario == TipoUsuario.Socio) {
                stmUsuario = super.getConexion().prepareStatement(
                        "SELECT *, vs.nome AS nomeUsuario FROM vistasocio AS vs  WHERE LOWER(vs.login)=LOWER(?);"
                );
                //JOIN tarifa AS t ON vs.tarifa=t.codTarifa
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
                            rsUsuarios.getDate("dataAlta"),
                            rsUsuarios.getDate("dataBaixa"),
                            new Tarifa(
                                    rsUsuarios.getInt("tarifa")
                            )
                    );
                }
            } else {
                stmUsuario = super.getConexion().prepareStatement("SELECT * FROM vistapersoal AS vp WHERE LOWER(vp.login)=LOWER(?);");
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
                            rsUsuarios.getDate("dataAlta"),
                            rsUsuarios.getDate("dataBaixa"),
                            rsUsuarios.getString("nuss"),
                            rsUsuarios.getBoolean("profesoractivo")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmUsuario.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return null;
    }

    /**
     * Método para consultar qué cuota ten que pagar no mes actual un socio, así como información desranada sobre a mesma.
     *
     * @param socio Socio do socio
     * @return Cuota que ten o socio
     */
    protected Cuota consultarCuota(Socio socio) {
        PreparedStatement stm = null;
        ResultSet resultSet;
        Tarifa tarifa;
        ArrayList<Actividade> actividadesMes = new ArrayList<>();
        ArrayList<Curso> cursosMes = new ArrayList<>();
        float prezoActividadesExtra = 0.0f;
        float totalActividades = 0.0f;
        float totalCursos = 0.0f;
        float totalPrezo = 0.0f;

        tarifa = socio.getTarifa();

        try {
            stm = super.getConexion().prepareStatement(
                    "SELECT " +
                            "rA.*, act.*, " +
                            "act.nome as nomeAct, " +
                            "ar.nome as nomeArea, " +
                            "ar.codArea as codArea, " +
                            "tAct.nome as nomeTipo " +
                            "FROM realizarActividade as rA NATURAL JOIN actividade as act " +
                            "JOIN area AS ar ON act.area=ar.codArea AND act.instalacion=ar.instalacion " +
                            "JOIN tipoActividade as tAct ON tAct.codTipoActividade=act.tipoActividade " +
                            "WHERE rA.dataActividade BETWEEN to_date(format('%s-%s-%s',EXTRACT(YEAR from NOW()),EXTRACT(MONTH from NOW()),'01'),'YYYY-MM-DD') AND NOW() " +
                            "AND LOWER(rA.usuario)=LOWER(?) AND act.curso IS NULL ORDER BY act.dataActividade DESC;"
            );
            stm.setString(1, socio.getLogin());
            resultSet = stm.executeQuery();
            while (resultSet.next()) {

                Area area = new Area(
                        resultSet.getInt("codArea"),
                        new Instalacion(resultSet.getInt("instalacion")),
                        resultSet.getString("nomeArea")
                );

                TipoActividade tipoActividade = new TipoActividade(
                        resultSet.getInt("tipoActividade"),
                        resultSet.getString("nomeTipo")
                );

                actividadesMes.add(new Actividade(
                        resultSet.getTimestamp("dataActividade"),
                        area,
                        tipoActividade,
                        new Curso(resultSet.getInt("curso")),
                        resultSet.getString("nome"),
                        resultSet.getFloat("duracion"),
                        new Persoal(resultSet.getString("profesor"))
                ));
            }

            stm = super.getConexion().prepareStatement(
                    "SELECT * " +
                            "FROM curso " +
                            "WHERE codCurso IN (SELECT curso FROM realizarCurso WHERE LOWER(usuario)=LOWER(?)) " +
                            "AND codCurso IN (SELECT curso FROM actividade GROUP BY curso HAVING MAX(dataActividade)>=NOW());"
            );
            stm.setString(1, socio.getLogin());
            resultSet = stm.executeQuery();
            while (resultSet.next()) {
                cursosMes.add(new Curso(
                        resultSet.getInt("codCurso"),
                        resultSet.getString("nome"),
                        resultSet.getString("descricion"),
                        resultSet.getFloat("prezo")
                ));
            }

            if (actividadesMes.size() > tarifa.getMaxActividades()) {
                prezoActividadesExtra = tarifa.getPrezoExtras() * (actividadesMes.size() - tarifa.getMaxActividades());
            }
            totalActividades = tarifa.getPrezoBase() + prezoActividadesExtra;
            for (Curso c : cursosMes) {
                totalCursos += c.getPrezo();
            }
            totalPrezo = totalActividades + totalCursos;

            return new Cuota(socio, tarifa, prezoActividadesExtra, totalActividades, totalCursos, totalPrezo, actividadesMes, cursosMes);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stm.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return null;
    }

    /**
     * Método para insertar un novo rexistro fisiolóxico dun socio.
     *
     * @param rexistroFisioloxico rexistro cos datos
     * @throws ExcepcionBD
     */
    protected void insertarRexistro(RexistroFisioloxico rexistroFisioloxico) throws ExcepcionBD {
        PreparedStatement stm = null;

        try {
            stm = super.getConexion().prepareStatement(
                    "INSERT INTO rexistroFisioloxico (socio, dataMarca, peso, altura, bfp, tensionAlta, tensionBaixa, ppm, comentario)  " +
                            "VALUES (?,NOW(),?,?,?,?,?,?,?);"
            );
            stm.setString(1, rexistroFisioloxico.getSocio().getLogin());
            stm.setFloat(2, rexistroFisioloxico.getPeso());
            stm.setFloat(3, rexistroFisioloxico.getAltura());
            stm.setObject(4, rexistroFisioloxico.getBfp());
            stm.setObject(5, rexistroFisioloxico.getTensionAlta());
            stm.setObject(6, rexistroFisioloxico.getTensionBaixa());
            stm.setObject(7, rexistroFisioloxico.getPpm());
            stm.setString(8, rexistroFisioloxico.getComentario());
            stm.executeUpdate();
            super.getConexion().commit();
        } catch (SQLException e) {
            throw new ExcepcionBD(super.getConexion(), e);
        } finally {
            try {
                stm.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
    }

    /**
     * Método para eliminar un rexistro fisiolóxico dun socio.
     *
     * @param rexistroFisioloxico rexistro cos datos
     * @throws ExcepcionBD
     */
    protected void eliminarRexistro(RexistroFisioloxico rexistroFisioloxico) throws ExcepcionBD {
        PreparedStatement stm = null;

        try {
            stm = super.getConexion().prepareStatement(
                    "DELETE FROM rexistroFisioloxico WHERE socio=? AND dataMarca=?;"
            );
            stm.setString(1, rexistroFisioloxico.getSocio().getLogin());
            stm.setTimestamp(2, rexistroFisioloxico.getData());
            stm.executeUpdate();
            super.getConexion().commit();
        } catch (SQLException e) {
            throw new ExcepcionBD(super.getConexion(), e);
        } finally {
            try {
                stm.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
    }

    /**
     * Método para consultar todos os rexistros fisiolóxicos dun socio concreto.
     *
     * @param login login do socio
     * @return lista dos seus rexistros almacenados
     */
    protected ArrayList<RexistroFisioloxico> listarRexistros(String login) {
        PreparedStatement stm = null;
        ResultSet resultSet;
        ArrayList<RexistroFisioloxico> rexistros = new ArrayList<>();

        try {
            stm = super.getConexion().prepareStatement(
                    "SELECT * " +
                            "FROM rexistroFisioloxico " +
                            "WHERE LOWER(socio)=LOWER(?) " +
                            "ORDER BY dataMarca ASC;"
            );
            stm.setString(1, login);
            resultSet = stm.executeQuery();
            while (resultSet.next()) {
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
        } finally {
            try {
                stm.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return rexistros;
    }

    /**
     * Método para comprobar se un profesor ten clases pendentes dun tipo de actividade concreto.
     *
     * @param persoal        persoal
     * @param tipoActividade tipo de actividade
     * @return true se ten clases pendentes dese tipo, false se non.
     */
    protected boolean tenClasesPendentes(Persoal persoal, TipoActividade tipoActividade) {
        PreparedStatement stmUsuario = null;
        ResultSet resultValidacion;
        try {
            if (tipoActividade == null) {
                stmUsuario = super.getConexion().prepareStatement("SELECT (COUNT(*)>0) FROM actividade WHERE LOWER(profesor)=LOWER(?) AND dataactividade>NOW();");
            } else {
                stmUsuario = super.getConexion().prepareStatement("SELECT (COUNT(*)>0) FROM actividade WHERE LOWER(profesor)=LOWER(?)  AND dataactividade>NOW() AND tipoActividade=?;");
                stmUsuario.setInt(2, tipoActividade.getCodTipoActividade());
            }
            stmUsuario.setString(1, persoal.getLogin());
            resultValidacion = stmUsuario.executeQuery();
            resultValidacion.next();
            return resultValidacion.getBoolean(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmUsuario.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return false;
    }

}

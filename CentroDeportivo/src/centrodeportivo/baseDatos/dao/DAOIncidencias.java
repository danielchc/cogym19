package centrodeportivo.baseDatos.dao;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.aplicacion.obxectos.Incidencia;
import centrodeportivo.aplicacion.obxectos.area.Material;
import centrodeportivo.aplicacion.obxectos.tipos.TipoIncidencia;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;

import java.sql.*;
import java.util.ArrayList;

public class DAOIncidencias extends  AbstractDAO{
    Connection con;
    public DAOIncidencias(Connection conexion, FachadaAplicacion fachadaAplicacion) {
        super(conexion,fachadaAplicacion);
        this.con=super.getConexion();
    }

    public void insertarIncidencia(Incidencia incidencia) throws SQLException {
        PreparedStatement stmIncidencia=null;
        if(incidencia.getTipoIncidencia()== TipoIncidencia.Area){
            stmIncidencia=con.prepareStatement("INSERT INTO incidenciasAreas(usuario,descricion,area,instalacion)  VALUES (?,?,?,?);");
            stmIncidencia.setInt(3,incidencia.getArea().getCodArea());
            stmIncidencia.setInt(4,incidencia.getArea().getInstalacion().getCodInstalacion());
        }else{
            stmIncidencia=con.prepareStatement("INSERT INTO incidenciasMateriais(usuario,descricion,material)  VALUES (?,?,?);");
            stmIncidencia.setInt(3,incidencia.getMaterial().getCodMaterial());
        }
        stmIncidencia.setString(1,incidencia.getUsuario().getLogin());
        stmIncidencia.setString(2,incidencia.getDescricion());
        System.out.println(stmIncidencia);
        stmIncidencia.executeUpdate();
        con.commit();
    }

    public ArrayList<Incidencia> listarIncidencia() throws SQLException {
        PreparedStatement stmIncidencia = null;
        ArrayList<Incidencia> incidencias=new ArrayList<Incidencia>();
        ResultSet rsIncidencias;
        stmIncidencia = con.prepareStatement("SELECT *,ar.nome AS nombreArea,it.nome AS nombreInstalacion,ia.descricion AS descricionIncidencia,ar.descricion AS descricionArea FROM incidenciasAreas AS ia JOIN areas AS ar ON ia.area=ar.codArea JOIN instalacions AS it  ON ar.instalacion=it.codInstalacion");
        rsIncidencias = stmIncidencia.executeQuery();
        Area area;
        Instalacion instalacion;
        while(rsIncidencias.next()){
            instalacion=new Instalacion(
                    rsIncidencias.getInt("codInstalacion"),
                    rsIncidencias.getString("nombreInstalacion"),
                    rsIncidencias.getString("numTelefono"),
                    rsIncidencias.getString("direccion")
            );
            area=new Area(
                    rsIncidencias.getInt("codArea"),
                    instalacion,
                    rsIncidencias.getString("nombreArea"),
                    rsIncidencias.getString("descricionArea"),
                    rsIncidencias.getInt("aforoMaximo"),
                    rsIncidencias.getDate("dataBaixa")
            );
            incidencias.add(new Incidencia(
                    TipoIncidencia.Area,
                    new Usuario(rsIncidencias.getString("usuario")),
                    rsIncidencias.getString("descricionIncidencia"),
                    area
            ));
        }
        stmIncidencia = con.prepareStatement("SELECT *,im.descricion AS descricionIncidencia FROM incidenciasmateriais AS im JOIN materiais AS ma ON im.material=ma.codMaterial;");
        rsIncidencias = stmIncidencia.executeQuery();
        Material material;
        while(rsIncidencias.next()){
            material=new Material(
                    rsIncidencias.getInt("numero"),
                    new Area(rsIncidencias.getInt("area"),new Instalacion(rsIncidencias.getInt("instalacion"))),
                    rsIncidencias.getString("nome"),
                    rsIncidencias.getDate("dataCompra"),
                    rsIncidencias.getDouble("prezoCompra")
            );
            incidencias.add(new Incidencia(
                    TipoIncidencia.Material,
                    new Usuario(rsIncidencias.getString("usuario")),
                    rsIncidencias.getString("descricionIncidencia"),
                    material
            ));
        }
        return incidencias;
    }

    public void resolverIncidencia(Incidencia incidencia){

//        PreparedStatement stmUsuario=null,stmSocio=null,stmPersoal=null;
//        stmUsuario= con.prepareStatement("UPDATE usuarios SET login=?,contrasinal=?,nome=?,numTelefono=?,DNI=?,correoElectronico=?,IBAN=? WHERE login=?;");
//        stmUsuario.setString(1,usuario.getLogin());
//        stmUsuario.setString(2,usuario.getContrasinal());
//        stmUsuario.setString(3,usuario.getNome());
//        stmUsuario.setString(4,usuario.getNumTelefono());
//        stmUsuario.setString(5,usuario.getDNI());
//        stmUsuario.setString(6,usuario.getCorreoElectronico());
//        stmUsuario.setString(7,usuario.getIBANconta());
//        stmUsuario.setString(8,loginVello);
//        stmUsuario.executeUpdate();
    }

}

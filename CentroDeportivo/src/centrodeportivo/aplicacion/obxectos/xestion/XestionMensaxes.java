package centrodeportivo.aplicacion.obxectos.xestion;

import centrodeportivo.aplicacion.obxectos.Mensaxe;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.baseDatos.FachadaBD;
import centrodeportivo.gui.FachadaGUI;

import java.sql.SQLException;
import java.util.ArrayList;

public class XestionMensaxes {
    private FachadaGUI fachadaGUI;
    private FachadaBD fachadaBD;

    public XestionMensaxes(FachadaGUI fachadaGUI,FachadaBD fachadaBD) {
        this.fachadaGUI=fachadaGUI;
        this.fachadaBD=fachadaBD;
    }


    public void enviarMensaxe(Mensaxe m) throws SQLException {
        fachadaBD.enviarMensaxe(m);
    }

    public void enviarMensaxe(Usuario emisor, ArrayList<Usuario> receptores, String mensaxe) throws SQLException{
        fachadaBD.enviarMensaxe(emisor, receptores, mensaxe);
    }

    public void marcarMensaxeComoLido(Mensaxe m) throws SQLException{
        fachadaBD.marcarMensaxeComoLido(m);
    }

    public ArrayList<Mensaxe> listarMensaxesRecibidos(String loginReceptor) throws SQLException{
        return fachadaBD.listarMensaxesRecibidos(loginReceptor);
    }
}

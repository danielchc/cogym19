package centrodeportivo.aplicacion;

import centrodeportivo.aplicacion.obxectos.xestion.XestionUsuarios;
import centrodeportivo.baseDatos.FachadaBD;
import centrodeportivo.gui.FachadaGUI;

import java.io.IOException;
import java.sql.SQLException;

public class FachadaAplicacion {
    FachadaGUI fachadaGUI;
    FachadaBD fachadaBD;
    XestionUsuarios xestionUsuarios;

    public FachadaAplicacion() throws IOException, SQLException {
        this.fachadaGUI=new FachadaGUI(this);
        this.fachadaBD=new FachadaBD(this);
        this.xestionUsuarios=new XestionUsuarios(fachadaGUI,fachadaBD);
    }
}

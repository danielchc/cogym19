package centrodeportivo.aplicacion.obxectos.xestion;

import centrodeportivo.aplicacion.obxectos.Tarifa;
import centrodeportivo.baseDatos.FachadaBD;
import centrodeportivo.gui.FachadaGUI;

import java.sql.SQLException;
import java.util.ArrayList;

public class XestionTarifas {
    private FachadaGUI fachadaGUI;
    private FachadaBD fachadaBD;

    public XestionTarifas(FachadaGUI fachadaGUI,FachadaBD fachadaBD) {
        this.fachadaGUI=fachadaGUI;
        this.fachadaBD=fachadaBD;
    }


    public void insertarTarifa(Tarifa t) throws SQLException {
        fachadaBD.insertarTarifa(t);
    }

    public void borrarTarifa(Integer codTarifa) throws SQLException{
        fachadaBD.borrarTarifa(codTarifa);
    }

    public void actualizarTarifa(Tarifa t) throws SQLException{
        fachadaBD.actualizarTarifa(t);
    }

    public boolean estaEnUsoTarifa(Integer codTarifa) throws SQLException{
        return fachadaBD.estaEnUsoTarifa(codTarifa);
    }

    public ArrayList<Tarifa> listarTarifas() throws SQLException{
        return fachadaBD.listarTarifas();
    }

    public Tarifa consultarTarifaSocio(String loginSocio) throws SQLException{
        return fachadaBD.consultarTarifaSocio(loginSocio);
    }
}

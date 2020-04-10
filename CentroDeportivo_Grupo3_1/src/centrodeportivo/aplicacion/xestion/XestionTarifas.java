package centrodeportivo.aplicacion.xestion;

import centrodeportivo.aplicacion.obxectos.tarifas.Tarifa;
import centrodeportivo.baseDatos.FachadaBD;
import centrodeportivo.gui.FachadaGUI;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public class XestionTarifas {
    private FachadaGUI fachadaGUI;
    private FachadaBD fachadaBD;

    public XestionTarifas(FachadaGUI fachadaGUI,FachadaBD fachadaBD) {
        this.fachadaGUI=fachadaGUI;
        this.fachadaBD=fachadaBD;
    }


    public void insertarTarifa(Tarifa t)  {
        fachadaBD.insertarTarifa(t);
    }

    public void borrarTarifa(Integer codTarifa) {
        fachadaBD.borrarTarifa(codTarifa);
    }

    public void actualizarTarifa(Tarifa t) {
        fachadaBD.actualizarTarifa(t);
    }

    public boolean estaEnUsoTarifa(Integer codTarifa) {
        return fachadaBD.estaEnUsoTarifa(codTarifa);
    }

    public ArrayList<Tarifa> listarTarifas() {
        return fachadaBD.listarTarifas();
    }

    public Tarifa consultarTarifaSocio(String loginSocio) {
        return fachadaBD.consultarTarifaSocio(loginSocio);
    }
    public boolean existeTarifa(String nome){
        return fachadaBD.existeTarifa(nome);
    }
}

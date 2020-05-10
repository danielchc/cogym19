package centrodeportivo.aplicacion.xestion;

import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.tarifas.Tarifa;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.baseDatos.FachadaBD;
import centrodeportivo.gui.FachadaGUI;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public class XestionTarifas {

    /**
     * Atributos
     */
    private FachadaGUI fachadaGUI;
    private FachadaBD fachadaBD;

    /**
     * @param fachadaGUI Fachada da gui.
     * @param fachadaBD  Fachada da base de datos.
     */
    public XestionTarifas(FachadaGUI fachadaGUI, FachadaBD fachadaBD) {
        this.fachadaGUI = fachadaGUI;
        this.fachadaBD = fachadaBD;
    }

    /**
     * Métodos
     */
    public void insertarTarifa(Tarifa t) throws ExcepcionBD {
        fachadaBD.insertarTarifa(t);
    }

    public void borrarTarifa(Integer codTarifa) throws ExcepcionBD {
        fachadaBD.borrarTarifa(codTarifa);
    }

    public void actualizarTarifa(Tarifa t) throws ExcepcionBD {
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

    public boolean existeTarifa(String nome) {
        return fachadaBD.existeTarifa(nome);
    }

    public ArrayList<Usuario> listarSociosTarifa(Tarifa t) {
        return fachadaBD.listarSociosTarifa(t);
    }
}

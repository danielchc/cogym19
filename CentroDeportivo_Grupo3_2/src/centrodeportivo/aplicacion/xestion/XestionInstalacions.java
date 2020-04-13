package centrodeportivo.aplicacion.xestion;

import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.tipos.TipoResultados;
import centrodeportivo.baseDatos.FachadaBD;
import centrodeportivo.gui.FachadaGUI;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;

import java.util.ArrayList;

public class XestionInstalacions {
    private FachadaGUI fachadaGUI;
    private FachadaBD fachadaBD;

    public XestionInstalacions(FachadaGUI fachadaGUI, FachadaBD fachadaBD){
        this.fachadaGUI = fachadaGUI;
        this.fachadaBD = fachadaBD;
    }

    public TipoResultados darAltaInstalacion(Instalacion instalacion) throws ExcepcionBD {
        //Se a instalación non existe, dase de alta:
        if(!fachadaBD.comprobarExistencia(instalacion)) {
            fachadaBD.darAltaInstalacion(instalacion);
            //Se se completa a execución do método sen lanzamento de excepcións, devolvemos que foi ben:
            return TipoResultados.correcto;
        } else {
            return TipoResultados.datoExiste;
        }

    }

    public TipoResultados borrarInstalacion(Instalacion instalacion) throws ExcepcionBD {
        if(!fachadaBD.tenAreas(instalacion)){
            fachadaBD.borrarInstalacion(instalacion);
            //Se se completou o método correctamente, devolvemos o enum que indica corrección:
            return TipoResultados.correcto;
        } else {
            //Se houbese áreas asociadas a instalacións, devolvemos o enum de erro por referencias co restrict.
            return TipoResultados.referenciaRestrict;
        }

    }

    public void modificarInstalacion(Instalacion instalacion){
        if(!fachadaBD.comprobarExistencia(instalacion)) {
            fachadaBD.modificarInstalacion(instalacion);
            //Imprimimos mensaxe de éxito:
            fachadaGUI.mostrarInformacion("Administración de Instalacións", "Datos da instalación "
                    + instalacion.getCodInstalacion() + " modificados correctamente." );
        } else {
            fachadaGUI.mostrarErro("Administración de Instalacións", "Xa hai outra instalación co nome '" + instalacion.getNome() + "'.");
        }
    }

    public ArrayList<Instalacion> buscarInstalacions(Instalacion instalacion){
        return fachadaBD.buscarInstalacions(instalacion);
    }

    public ArrayList<Instalacion> listarInstalacions(){
        return fachadaBD.listarInstalacions();
    }
}

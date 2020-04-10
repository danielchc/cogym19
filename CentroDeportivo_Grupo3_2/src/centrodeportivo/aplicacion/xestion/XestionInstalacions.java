package centrodeportivo.aplicacion.xestion;

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

    public void darAltaInstalacion(Instalacion instalacion){
        //Se a instalación non existe, dase de alta:
        if(!fachadaBD.comprobarExistencia(instalacion)) {
            fachadaBD.darAltaInstalacion(instalacion);
        } else {
            fachadaGUI.mostrarErro("Administración de Instalacións", "Xa hai unha instalación co nome '" + instalacion.getNome().toLowerCase() + "'.");
        }

    }

    public void borrarInstalacion(Instalacion instalacion){
        if(!fachadaBD.tenAreas(instalacion)){
            fachadaBD.borrarInstalacion(instalacion);
            fachadaGUI.mostrarInformacion("Administración de Instalacións", "Instalalción eliminada.");
        } else {
            fachadaGUI.mostrarErro("Administración de Instalacións", "A instalación non se pode borrar!");
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

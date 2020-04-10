package centrodeportivo.aplicacion.xestion;

import centrodeportivo.aplicacion.obxectos.actividades.TipoActividade;
import centrodeportivo.baseDatos.FachadaBD;
import centrodeportivo.gui.FachadaGUI;

import java.util.ArrayList;

//Clase para todos os métodos relacionados coa xestión das actividades (tipos de actividades e actividades).
public class XestionActividades {
    //Atributos: coma sempre nas clases de xestión as fachadas:
    private FachadaBD fachadaBD;
    private FachadaGUI fachadaGUI;

    //Constructor:
    public XestionActividades (FachadaGUI fachadaGUI, FachadaBD fachadaBD){
        this.fachadaBD = fachadaBD;
        this.fachadaGUI = fachadaGUI;
    }

    public void crearTipoActividade (TipoActividade tipoActividade){
        if(!this.fachadaBD.comprobarExistencia(tipoActividade)){
            this.fachadaBD.crearTipoActividade(tipoActividade);
        } else {
            this.fachadaGUI.mostrarErro("Administración de Tipos de Actividades", "Xa existe un tipo de actividade de nome '" + tipoActividade.getNome().toLowerCase() + "'.");
        }
    }

    public void modificarTipoActividade(TipoActividade tipoActividade){
        this.fachadaBD.modificarTipoActividade(tipoActividade);
    }

    public void eliminarTipoActividade(TipoActividade tipoActividade){
        if(!this.fachadaBD.tenActividades(tipoActividade)){
            this.fachadaBD.eliminarTipoActividade(tipoActividade);
            this.fachadaGUI.mostrarConfirmacion("Administración de Tipos de Actividades", "Eliminación correcta.");
        } else {
            this.fachadaGUI.mostrarErro("Administración de Tipos de Actividades", "O tipo '" + tipoActividade.getNome() + "' ten actividades asociadas! Non se pode borrar.");
        }

    }

    public ArrayList<TipoActividade> listarTiposActividades(){
        return this.fachadaBD.listarTiposActividades();
    }

    public ArrayList<TipoActividade> buscarTiposActividades(TipoActividade tipoActividade){
        return this.fachadaBD.buscarTiposActividades(tipoActividade);
    }
}

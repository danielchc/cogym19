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
        this.fachadaBD.crearTipoActividade(tipoActividade);
    }

    public void modificarTipoActividade(TipoActividade tipoActividade){
        this.fachadaBD.modificarTipoActividade(tipoActividade);
    }

    public void eliminarTipoActividade(TipoActividade tipoActividade){
        this.fachadaBD.eliminarTipoActividade(tipoActividade);
    }

    public ArrayList<TipoActividade> listarTiposActividades(){
        return this.fachadaBD.listarTiposActividades();
    }

    public ArrayList<TipoActividade> buscarTiposActividades(TipoActividade tipoActividade){
        return this.fachadaBD.buscarTiposActividades(tipoActividade);
    }
}

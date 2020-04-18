package centrodeportivo.aplicacion.xestion;

import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.actividades.Curso;
import centrodeportivo.aplicacion.obxectos.tipos.TipoResultados;
import centrodeportivo.baseDatos.FachadaBD;
import centrodeportivo.gui.FachadaGUI;

public class XestionCursos {
    private FachadaGUI fachadaGUI;
    private FachadaBD fachadaBD;

    public XestionCursos(FachadaGUI fachadaGUI, FachadaBD fachadaBD){
        this.fachadaBD = fachadaBD;
        this.fachadaGUI = fachadaGUI;
    }

    public TipoResultados rexistrarCurso(Curso curso) throws ExcepcionBD {
        //Primeiro, verificamos que non exista xa un curso co mesmo nome:
        if(!fachadaBD.comprobarExistencia(curso)){
            //Se non existe, hai que verificar que todas as actividades 
        }
    }
}

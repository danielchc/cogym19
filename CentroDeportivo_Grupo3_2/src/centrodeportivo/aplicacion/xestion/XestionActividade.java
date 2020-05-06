package centrodeportivo.aplicacion.xestion;

import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.actividades.Actividade;
import centrodeportivo.aplicacion.obxectos.tipos.TipoResultados;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.baseDatos.FachadaBD;
import centrodeportivo.gui.FachadaGUI;

public class XestionActividade {

    private FachadaGUI fachadaGUI;
    private FachadaBD fachadaBD;

    public XestionActividade(FachadaGUI fachadaGUI, FachadaBD fachadaBD){
        this.fachadaGUI = fachadaGUI;
        this.fachadaBD = fachadaBD;
    }

    public TipoResultados EngadirActividade(Actividade actividade) throws ExcepcionBD {
        //Se a instalación non existe, dase de alta:
        if(!fachadaBD.existeActividade(actividade) && !fachadaBD.horarioOcupadoActividade(actividade)) {
            fachadaBD.EngadirActividade(actividade);
            //Se se completa a execución do método sen lanzamento de excepcións, devolvemos que foi ben:
            return TipoResultados.correcto;
        } else {
            return TipoResultados.datoExiste;
        }
    }

    public TipoResultados borrarActividade(Actividade actividade) throws ExcepcionBD {
        if(fachadaBD.existeActividade(actividade)){ //Comprobar eliminable
            fachadaBD.borrarActividade(actividade);
            //Se se completou o método correctamente, devolvemos o enum que indica corrección:
            return TipoResultados.correcto;
        } else {
            return TipoResultados.referenciaRestrict;
        }
    }

    public TipoResultados modificarActividade(Actividade actVella, Actividade actNova) throws ExcepcionBD {
        if(fachadaBD.existeActividade(actVella)) {
            fachadaBD.modificarActividade(actVella, actNova);
            //Se se completa a execución do método sen lanzamento de excepcións, devolvemos que foi ben:
            return TipoResultados.correcto;
        } else {
            return TipoResultados.datoExiste;
        }
    }

    public TipoResultados apuntarseActividade(Actividade actividade, Usuario usuario) throws ExcepcionBD {
        //Se a instalación non existe, dase de alta:
        if(fachadaBD.existeActividade(actividade) && !fachadaBD.estarApuntado(actividade, usuario)) {
            fachadaBD.apuntarseActividade(actividade, usuario);
            //Se se completa a execución do método sen lanzamento de excepcións, devolvemos que foi ben:
            return TipoResultados.correcto;
        } else {
            return TipoResultados.sitIncoherente;
        }
    }

    public TipoResultados borrarseDeActividade(Actividade actividade, Usuario usuario) throws ExcepcionBD {
        //Se a instalación non existe, dase de alta:
        if(fachadaBD.existeActividade(actividade) && fachadaBD.estarApuntado(actividade, usuario)) {
            fachadaBD.borrarseDeActividade(actividade, usuario);
            //Se se completa a execución do método sen lanzamento de excepcións, devolvemos que foi ben:
            return TipoResultados.correcto;
        } else {
            return TipoResultados.sitIncoherente;
        }
    }
}

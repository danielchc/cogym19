package centrodeportivo.aplicacion.xestion;

import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.actividades.Actividade;
import centrodeportivo.aplicacion.obxectos.actividades.TipoActividade;
import centrodeportivo.aplicacion.obxectos.tipos.TipoResultados;
import centrodeportivo.aplicacion.obxectos.usuarios.Persoal;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.baseDatos.FachadaBD;
import centrodeportivo.gui.FachadaGUI;

import java.sql.Timestamp;
import java.util.ArrayList;

public class XestionActividade {

    private FachadaGUI fachadaGUI;
    private FachadaBD fachadaBD;

    public XestionActividade(FachadaGUI fachadaGUI, FachadaBD fachadaBD) {
        this.fachadaGUI = fachadaGUI;
        this.fachadaBD = fachadaBD;
    }

    public TipoResultados EngadirActividade(Actividade actividade) throws ExcepcionBD {
        //Se a actividade non existe, dase de engadimola:
        if (!fachadaBD.horarioOcupadoActividade(null, actividade)) {
            fachadaBD.EngadirActividade(actividade);
            //Se se completa a execución do método sen lanzamento de excepcións, devolvemos que foi ben:
            return TipoResultados.correcto;
        } else {
            return TipoResultados.datoExiste;
        }
    }

    public TipoResultados borrarActividade(Actividade actividade) throws ExcepcionBD {
        //Só se pode borrar unha actividade se inda non comezou
        if (actividade.getData().after(new Timestamp(System.currentTimeMillis()))) { //Comprobar eliminable
            fachadaBD.borrarActividade(actividade);
            //Se se completou o método correctamente, devolvemos o enum que indica corrección:
            return TipoResultados.correcto;
        } else {
            //Se a actividade se realizou xa, ou se está niso, tense unha situación incoherente:
            return TipoResultados.sitIncoherente;
        }
    }

    public TipoResultados modificarActividade(Actividade actVella, Actividade actNova) throws ExcepcionBD {
        if (!fachadaBD.horarioOcupadoActividade(actVella, actNova)) {
            fachadaBD.modificarActividade(actVella, actNova);
            //Se se completa a execución do método sen lanzamento de excepcións, devolvemos que foi ben:
            return TipoResultados.correcto;
        } else {
            return TipoResultados.datoExiste;
        }
    }

    public TipoResultados apuntarseActividade(Actividade actividade, Usuario usuario) throws ExcepcionBD {
        //Se a actividade non existe, non estas apuntado e non é maxio o aforo podese apuntar, apuntamos o usuario na actividade::
        if (!fachadaBD.estarApuntado(actividade, usuario) && fachadaBD.NonEMaximoAforoActividade(actividade)) {
            fachadaBD.apuntarseActividade(actividade, usuario);
            //Se se completa a execución do método sen lanzamento de excepcións, devolvemos que foi ben:
            return TipoResultados.correcto;
        } else {
            return TipoResultados.sitIncoherente;
        }
    }

    public TipoResultados borrarseDeActividade(Actividade actividade, Usuario usuario) throws ExcepcionBD {
        //Se a Actividade existe, borramola:
        if (fachadaBD.estarApuntado(actividade, usuario)) {
            fachadaBD.borrarseDeActividade(actividade, usuario);
            //Se se completa a execución do método sen lanzamento de excepcións, devolvemos que foi ben:
            return TipoResultados.correcto;
        } else {
            return TipoResultados.sitIncoherente;
        }
    }

    public ArrayList<Actividade> buscarActividade(Actividade actividade) {
        return fachadaBD.buscarActividade(actividade);
    }

    public ArrayList<Persoal> buscarProfesores(TipoActividade tipoactividade) {
        return fachadaBD.buscarProfesores(tipoactividade);
    }

    public ArrayList<Actividade> buscarActividadeParticipa(Actividade actividade, Usuario usuario)
    {
        if(usuario != null)
            return fachadaBD.buscarActividadeparticipa(actividade, usuario);
        else
            return null;
    }

}

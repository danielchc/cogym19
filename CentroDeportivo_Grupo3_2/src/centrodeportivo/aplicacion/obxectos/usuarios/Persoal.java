package centrodeportivo.aplicacion.obxectos.usuarios;

import centrodeportivo.aplicacion.obxectos.tipos.TipoUsuario;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;

import java.sql.Date;

public class Persoal extends Usuario {

    private String NUSS;
    private boolean profesorActivo;

    public Persoal(String login){
        super(login);
        super.setTipoUsuario(TipoUsuario.Persoal);
    }

    public Persoal(String login, String contrasinal, String DNI, String nome, String dificultades, Date dataNacemento, String numTelefono, String correoElectronico, String IBANconta, String NUSS, boolean profesorActivo) {
        super(login, contrasinal, DNI, nome, dificultades, dataNacemento, numTelefono, correoElectronico, IBANconta);
        this.NUSS = NUSS;
        this.profesorActivo= profesorActivo;
        super.setTipoUsuario(TipoUsuario.Socio);
    }

    public String getNUSS() {
        return NUSS;
    }

    public void setNUSS(String NUSS) {
        this.NUSS = NUSS;
    }

    public boolean getProfesorActivo(){
        return profesorActivo;
    }

    public void setProfesorActivo(boolean profesorActivo){
        this.profesorActivo = profesorActivo;
    }
}

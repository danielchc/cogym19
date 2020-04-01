package centrodeportivo.aplicacion.obxectos.usuarios;

import centrodeportivo.aplicacion.obxectos.tipos.TipoUsuario;

import java.sql.Date;

public final class Profesor extends Persoal {

    public Profesor(String login){
        super(login);
        super.setTipoUsuario(TipoUsuario.Profesor);
    }
    public Profesor(String login,String contrasinal,String nome,String numTelefono,String DNI,String correoElectronico,String IBANconta,Date dataAlta,String NUSS){
        super(login, contrasinal, nome, numTelefono, DNI, correoElectronico, IBANconta, dataAlta, NUSS);
    }
    public Profesor(String login,String contrasinal,String nome,String numTelefono,String DNI,String correoElectronico,String IBANconta,String NUSS){
        super(login, contrasinal, nome, numTelefono, DNI, correoElectronico, IBANconta, NUSS);
    }
}

package centrodeportivo.aplicacion.obxectos.usuarios;

import java.sql.Date;

public final class Profesor extends Persoal {
    public Profesor(String login,String contrasinal,String nome,String numTelefono,String DNI,String correoElectronico,String IBANconta,Date dataAlta,Date dataIncorporacion,String NUSS){
        super(login, contrasinal, nome, numTelefono, DNI, correoElectronico, IBANconta, dataAlta, dataIncorporacion, NUSS);
    }
    public Profesor(String login,String contrasinal,String nome,String numTelefono,String DNI,String correoElectronico,String IBANconta,String NUSS){
        super(login, contrasinal, nome, numTelefono, DNI, correoElectronico, IBANconta, NUSS);
    }
}

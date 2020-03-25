package aplicacion;

import java.util.Date;

public final class Profesor extends Persoal {
    public Profesor(String login,String contrasinal,String nome,String numTelefono,String DNI,String correoElectronico,String IBANconta,Date dataAlta,Date dataIncorporacion,String NUSS){
        super(login, contrasinal, nome, numTelefono, DNI, correoElectronico, IBANconta, dataAlta, dataIncorporacion, NUSS);
    }
}

package centrodeportivo.aplicacion.obxectos.usuarios;

import centrodeportivo.aplicacion.obxectos.tipos.TipoUsuario;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;

import java.sql.Date;

public class Persoal extends Usuario {
    private String NUSS;

    public Persoal(String login){
        super(login);
        super.setTipoUsuario(TipoUsuario.Persoal);
    }

    public Persoal(String login,String contrasinal,String nome,String numTelefono,String DNI,String correoElectronico,String IBANconta,Date dataAlta,String NUSS){
        super(login, contrasinal, nome, numTelefono, DNI, correoElectronico, IBANconta, dataAlta);
        this.NUSS=NUSS;
    }

    public Persoal(String login,String contrasinal,String nome,String numTelefono,String DNI,String correoElectronico,String IBANconta,String NUSS){
        super(login, contrasinal, nome, numTelefono, DNI, correoElectronico, IBANconta);
        this.NUSS=NUSS;
    }


    public String getNUSS() {
        return NUSS;
    }

    public void setNUSS(String NUSS) {
        this.NUSS = NUSS;
    }
}

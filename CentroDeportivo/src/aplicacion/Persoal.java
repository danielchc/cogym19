package aplicacion;

import java.util.Date;

public class Persoal extends Usuario {
    private Date dataIncorporacion;
    private String NUSS;

    public Persoal(String login,String contrasinal,String nome,String numTelefono,String DNI,String correoElectronico,String IBANconta,Date dataAlta,Date dataIncorporacion,String NUSS){
        super(login, contrasinal, nome, numTelefono, DNI, correoElectronico, IBANconta, dataAlta);
        this.dataIncorporacion=dataIncorporacion;
        this.NUSS=NUSS;
    }

    public Date getDataIncorporacion() {
        return dataIncorporacion;
    }

    public void setDataIncorporacion(Date dataIncorporacion) {
        this.dataIncorporacion = dataIncorporacion;
    }

    public String getNUSS() {
        return NUSS;
    }

    public void setNUSS(String NUSS) {
        this.NUSS = NUSS;
    }
}

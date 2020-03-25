package aplicacion;

import java.util.Date;

public final class Socio extends Usuario {
    private Date dataNacemento;
    private String dificultades;

    public Socio(String login, String contrasinal, String nome, String numTelefono, String DNI, String correoElectronico, String IBANconta, Date dataAlta,Date dataNacemento,String dificultades){
        super(login, contrasinal, nome, numTelefono, DNI, correoElectronico, IBANconta, dataAlta);
        this.dataNacemento=dataNacemento;
        this.dificultades=dificultades;
    }

    public Date getDataNacemento() {
        return dataNacemento;
    }

    public void setDataNacemento(Date dataNacemento) {
        this.dataNacemento = dataNacemento;
    }

    public String getDificultades() {
        return dificultades;
    }

    public void setDificultades(String dificultades) {
        this.dificultades = dificultades;
    }
}

package centrodeportivo.aplicacion.obxectos.usuarios;

import centrodeportivo.aplicacion.obxectos.tarifas.Tarifa;
import centrodeportivo.aplicacion.obxectos.tipos.TipoUsuario;

import java.sql.Date;

public final class Socio extends Usuario {
    private Date dataNacemento;
    private String dificultades;
    private Tarifa tarifa;

    public Socio(String login){
        super(login);
        super.setTipoUsuario(TipoUsuario.Socio);
    }

    public Socio(String login, String contrasinal, String nome, String numTelefono, String DNI, String correoElectronico, String IBANconta, Date dataAlta,Date dataNacemento,String dificultades){
        super(login, contrasinal, nome, numTelefono, DNI, correoElectronico, IBANconta, dataAlta);
        this.dataNacemento=dataNacemento;
        this.dificultades=dificultades;
    }

    public Socio(String login, String contrasinal, String nome, String numTelefono, String DNI, String correoElectronico, String IBANconta, Date dataAlta,Date dataNacemento,String dificultades,Tarifa tarifa){
        super(login, contrasinal, nome, numTelefono, DNI, correoElectronico, IBANconta);
        this.dataNacemento=dataNacemento;
        this.dificultades=dificultades;
        this.tarifa=tarifa;
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

    public Tarifa getTarifa() {
        return tarifa;
    }

    public void setTarifa(Tarifa tarifa) {
        this.tarifa = tarifa;
    }
}

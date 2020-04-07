package centrodeportivo.aplicacion.obxectos.usuarios;


import centrodeportivo.aplicacion.obxectos.tipos.TipoUsuario;

import java.sql.Date;

public final class Socio extends Usuario {
    private Date dataNacemento;
    private String dificultades;

    public Socio(String login){
        super(login);
        super.setTipoUsuario(TipoUsuario.Socio);
    }

    public Socio(String login, String contrasinal, String DNI, String nome, String dificultades, Date dataNacemento, String numTelefono, String correoElectronico, String IBANconta) {
        super(login, contrasinal, DNI, nome, dificultades, dataNacemento, numTelefono, correoElectronico, IBANconta);
        super.setTipoUsuario(TipoUsuario.Socio);
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

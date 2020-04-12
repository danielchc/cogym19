package centrodeportivo.aplicacion.obxectos.usuarios;


import centrodeportivo.aplicacion.obxectos.tipos.TipoUsuario;

import java.sql.Date;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Victor Barreiro
 * Clase que permite recoller os datos específicos de socios do centro deportivo.
 */
public final class Socio extends Usuario {
    /**
     * Atributos da clase
     */
    private Date dataNacemento;
    private String dificultades;

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

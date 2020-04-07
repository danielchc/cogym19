package centrodeportivo.aplicacion.obxectos.usuarios;

import centrodeportivo.aplicacion.obxectos.tarifas.Tarifa;
import centrodeportivo.aplicacion.obxectos.tipos.TipoUsuario;

import java.sql.Date;

public final class Socio extends Usuario {

    /**
     * Atributos da clase Socio
     */
    private Tarifa tarifa;


    /**
     * Constructor coa clave primaria
     * @param login login do Socio
     */
    public Socio(String login){
        super(login);
        super.setTipoUsuario(TipoUsuario.Socio);
    }

    /**
     * Contructor con todos os datos do socio como parámetros
     * @param login login do persoal
     * @param contrasinal contrasinal
     * @param numTelefono número de teléfono
     * @param correoElectronico correo electrónico
     * @param IBANconta IBAN da conta
     * @param dataAlta data de ingreso
     * @param tarifa tarifa do socio
     */
    public Socio(String login, String contrasinal, String numTelefono, String correoElectronico, String IBANconta, Date dataAlta,Tarifa tarifa){
        super(login, contrasinal, numTelefono, correoElectronico, IBANconta,dataAlta);
        this.tarifa=tarifa;
        super.setTipoUsuario(TipoUsuario.Socio);
    }

    /**
     * Contructor con todos os datos do socio como parámetros menos a data de alta,
     * que se inserta directamente na transacción de insercción
     * @param login login do persoal
     * @param contrasinal contrasinal
     * @param numTelefono número de teléfono
     * @param correoElectronico correo electrónico
     * @param IBANconta IBAN da conta
     * @param tarifa tarifa do socio
     */
    public Socio(String login, String contrasinal, String numTelefono, String correoElectronico, String IBANconta,Tarifa tarifa){
        super(login, contrasinal, numTelefono, correoElectronico, IBANconta);
        this.tarifa=tarifa;
        super.setTipoUsuario(TipoUsuario.Socio);
    }


    /**
     * Getters e Setters
     */
    public Tarifa getTarifa() {
        return tarifa;
    }

    public void setTarifa(Tarifa tarifa) {
        this.tarifa = tarifa;
    }

    @Override
    public String toString() {
        return super.toString()+"Socio{" +
                "tarifa=" + tarifa +
                '}';
    }
}

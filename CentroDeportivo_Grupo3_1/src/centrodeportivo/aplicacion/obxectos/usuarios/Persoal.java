package centrodeportivo.aplicacion.obxectos.usuarios;

import centrodeportivo.aplicacion.obxectos.tipos.TipoUsuario;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;

import java.sql.Date;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public final class Persoal extends Usuario {


    /**
     * Atributos da clase Persoal
     */
    private String NUSS;

    /**
     * Constructor coa clave primaria
     * @param login login do persoal
     */
    public Persoal(String login){
        super(login);
        super.setTipoUsuario(TipoUsuario.Persoal);
    }

    /**
     * Constructor con todos os datos do Usuario
     *
     * @param login             login do Usuario
     * @param contrasinal       contrasinal
     * @param DNI
     * @param nome
     * @param dificultades
     * @param dataNacemento
     * @param numTelefono       número de teléfono
     * @param correoElectronico correo electrónico
     * @param IBANconta         IBAN do usuario
     * @param dataAlta          data de alta no sistema
     */
    public Persoal(String login, String contrasinal, String DNI, String nome, String dificultades, Date dataNacemento, String numTelefono, String correoElectronico, String IBANconta, Date dataAlta,Date dataBaixa, String NUSS, boolean profesorActivo) {
        super(login, contrasinal, DNI, nome, dificultades, dataNacemento, numTelefono, correoElectronico, IBANconta, dataAlta,dataBaixa);
        this.NUSS = NUSS;
        super.setTipoUsuario((profesorActivo)?TipoUsuario.Profesor:TipoUsuario.Persoal);
    }

    /**
     * Constructor sen a data de alta no sistema para enviar datos dende a aplicación á insercción na base de datos.
     * A data de alta introdúcese na transacción, por eso non se pasa como parámetro.
     *
     * @param login             login do Usuario
     * @param contrasinal       contrasinal
     * @param DNI
     * @param nome
     * @param dificultades
     * @param dataNacemento
     * @param numTelefono       número de teléfono
     * @param correoElectronico correo electrónico
     * @param IBANconta         IBAN do usuario
     */
    public Persoal(String login, String contrasinal, String DNI, String nome, String dificultades, Date dataNacemento, String numTelefono, String correoElectronico, String IBANconta, String NUSS, boolean profesorActivo) {
        super(login, contrasinal, DNI, nome, dificultades, dataNacemento, numTelefono, correoElectronico, IBANconta);
        this.NUSS = NUSS;
        super.setTipoUsuario((profesorActivo)?TipoUsuario.Profesor:TipoUsuario.Persoal);
    }

    /**
     * Getters e Setters
     */
    public String getNUSS() {
        return NUSS;
    }

    public void setNUSS(String NUSS) {
        this.NUSS = NUSS;
    }

    /**
     * toString
     */
    @Override
    public String toString() {
        return "Persoal{" +
                "NUSS='" + NUSS + '\'' +
                "} " + super.toString();
    }
}

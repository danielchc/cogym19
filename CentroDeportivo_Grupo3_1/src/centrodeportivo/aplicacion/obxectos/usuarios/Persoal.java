package centrodeportivo.aplicacion.obxectos.usuarios;

import centrodeportivo.aplicacion.obxectos.tipos.TipoUsuario;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;

import java.sql.Date;

public final class Persoal extends Usuario {


    /**
     * Atributos da clase Persoal
     */
    private String NUSS;
    private boolean profesorActivo;

    /**
     * Constructor coa clave primaria
     * @param login login do persoal
     */
    public Persoal(String login){
        super(login);
        super.setTipoUsuario(TipoUsuario.Persoal);
    }

    /**
     * Contructor con todos os datos do persoal como parámetros
     * @param login login do persoal
     * @param contrasinal contrasinal
     * @param numTelefono número de teléfono
     * @param correoElectronico correo electrónico
     * @param IBANconta IBAN da conta
     * @param dataAlta data de ingreso
     * @param NUSS Número da Seguridade Social
     */
    public Persoal(String login,String contrasinal,String numTelefono,String correoElectronico,String IBANconta,Date dataAlta,String NUSS){
        super(login, contrasinal, numTelefono, correoElectronico, IBANconta, dataAlta);
        this.NUSS=NUSS;
        super.setTipoUsuario(TipoUsuario.Persoal);
    }

    /**
     * Contructor con todos os datos do persoal como  menos a data de alta,
     * que se inserta na transacción de inserción na base de datos.
     * @param login login do persoal
     * @param contrasinal contrasinal
     * @param numTelefono número de teléfono
     * @param correoElectronico correo electrónico
     * @param IBANconta IBAN da conta
     * @param NUSS Número da Seguridade Social
     */
    public Persoal(String login,String contrasinal,String numTelefono,String correoElectronico,String IBANconta,String NUSS){
        super(login, contrasinal, numTelefono, correoElectronico, IBANconta);
        this.NUSS=NUSS;
        super.setTipoUsuario(TipoUsuario.Persoal);
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

    public boolean isProfesorActivo() {
        return profesorActivo;
    }

    public void setProfesorActivo(boolean profesorActivo) {
        this.profesorActivo = profesorActivo;
    }




    /**
     * toString
     */
    @Override
    public String toString() {
        return super.toString() +
                "NUSS='" + NUSS + '\'' +
                ", profesorActivo=" + profesorActivo +
                '}';
    }
}

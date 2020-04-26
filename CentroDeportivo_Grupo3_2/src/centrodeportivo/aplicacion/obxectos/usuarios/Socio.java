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

    //Canto aos atributos, non temos nada adicional.

    /**
     * Constructor con todos os atributos que se extenden do persoal.
     * @param login O login para o inicio de sesión do usuario
     * @param contrasinal Contrasinal usada polo usuario para o inicio da sesión
     * @param DNI DNI do usuario
     * @param nome Nome real do usuario
     * @param dificultades Dificultades que presenta este usuario
     * @param dataNacemento Data de nacemento
     * @param numTelefono Número de teléfono
     * @param correoElectronico Dirección de correo electrónico
     * @param IBANconta IBAN da conta bancaria do socio
     */
    public Socio(String login, String contrasinal, String DNI, String nome, String dificultades, Date dataNacemento,
                 String numTelefono, String correoElectronico, String IBANconta) {
        //Chamamos ao constructor da clase pai:
        super(login, contrasinal, DNI, nome, dificultades, dataNacemento, numTelefono, correoElectronico, IBANconta);
        //Establecemos o seu tipo de usuario, campo da clase pai (xa dixemos: é unha maneira de manter o tipo para calquera
        //usuario sen recorrer ao instanceof).
        super.setTipoUsuario(TipoUsuario.Socio);
    }

    /**
     * Constructor reducido, cando non se queira recopilar toda a información:
     * @param login Login para o inicio de sesión do usuario
     * @param nome Nome completo do usuario
     * @param dificultades Dificultades que presenta o usuario
     * @param dataNacemento Data de nacemento do usuario
     * @param numTelefono Número de teléfono do usuario
     * @param correoElectronico Dirección de correo electrónico do usuario.
     */
    public Socio(String login, String nome, String dificultades, Date dataNacemento, String numTelefono, String correoElectronico){
        //Chamamos ao constructor da clase pai:
        super(login, nome, dificultades, dataNacemento, numTelefono, correoElectronico);
        //Establecemos o tipo de usuario, coma no outro constructor.
        super.setTipoUsuario(TipoUsuario.Socio);
    }

}

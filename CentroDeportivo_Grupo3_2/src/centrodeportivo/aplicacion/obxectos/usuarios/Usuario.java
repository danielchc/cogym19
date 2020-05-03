package centrodeportivo.aplicacion.obxectos.usuarios;

import centrodeportivo.aplicacion.obxectos.tipos.TipoUsuario;

import java.sql.Date;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Victor Barreiro
 * Clase que nos permite representar aos usuarios da base de datos.
 */
public class Usuario {

    /**
     * Atributos da clase: é importante sinalar que no noso caso non precisamos considerar ás persoas físicas, como se
     * ten no MER.
     * Nós simplemente nos encargaremos de que os usuarios se apunten en actividades e cursos, non nos interesa ter toda
     * a súa información separada dende o que é esta aplicación. Non imos a controlar iso.
     */
    private String login; //Login do usuario
    private String contrasinal; //Contrasinal do usuario.
    private String nome; //Nome real da persoa que ten este usuario.
    private String numTelefono; //Número de teléfono asociado ao usuario.
    private String DNI; //DNI da persoa que ten este usuario.
    private String correoElectronico; //Dirección de correo electrónico.
    private String IBANconta; //IBAN da conta bancaria.
    private Date dataAlta; //Data de alta na aplicación
    private Date dataBaixa; //Data de baixa.
    private TipoUsuario tipoUsuario; //Tipo de usuario
    private String dificultades; //Dificultades que poida ter este usuario.
    private Date dataNacemento; //Data de nacemento do usuario.
    private Integer idade; //Idade do usuario

    /**
     * Constructor únicamente co login do usuario, necesario dende certas buscas onde simplemente queremos recoller o
     * login do usuario (inda que para facer adaptable o programa deixamos igual o atributo do Usuario completo por se
     * se queren meter máis cousas).
     *
     * @param login O login para o inicio de sesión do usuario.
     */
    public Usuario(String login) {
        this.login = login;
    }

    public Usuario(String nome, String DNI, String login) {
        this.nome = nome;
        this.DNI = DNI;
        this.login = login;
    }

    /**
     * Constructor sen datos persoais comprometedores:
     *
     * @param login             O login para o inicio de sesión do usuario.
     * @param nome              O nome real do usuario.
     * @param dificultades      Dificultades que presenta o usuario.
     * @param idade             Idade do usuario.
     * @param numTelefono       Número de teléfono do usuario.
     * @param correoElectronico Correo electrónico do usuario.
     */
    public Usuario(String login, String nome, String dificultades, Integer idade, String numTelefono,
                   String correoElectronico) {
        //Imos asignando todos os atributos dispoñibles aos que se pasaron:
        this.login = login;
        this.nome = nome;
        this.dificultades = dificultades;
        this.idade = idade;
        this.numTelefono = numTelefono;
        this.correoElectronico = correoElectronico;
    }

    /**
     * Constructor con máis atributos, máis privados de cada usuario.
     *
     * @param login             O login para o inicio de sesión do usuario.
     * @param contrasinal       O contrasinal empregado para o inicio de sesión.
     * @param DNI               O DNI do usuario.
     * @param nome              O nome real do usuario.
     * @param dificultades      Dificultades que presenta o usuario.
     * @param dataNacemento     Data de nacemento do usuario.
     * @param numTelefono       Número de teléfono do usuario.
     * @param correoElectronico Dirección de correo electrónico do usuario.
     * @param IBANconta         IBAN da conta bancaria do usuario.
     */
    public Usuario(String login, String contrasinal, String DNI, String nome, String dificultades, Date dataNacemento,
                   String numTelefono, String correoElectronico, String IBANconta) {
        this(nome, DNI, login);
        this.dificultades = dificultades;
        this.dataNacemento = dataNacemento;
        this.numTelefono = numTelefono;
        this.correoElectronico = correoElectronico;
        this.contrasinal = contrasinal;
        this.IBANconta = IBANconta;
    }


    /**
     * Getter do login do usuario.
     *
     * @return O login almacenado do usuario.
     */
    public String getLogin() {
        return login;
    }

    /**
     * Setter do login do usuario
     *
     * @param login O login a asignar ao usuario.
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Getter do contrasinal do usuario.
     *
     * @return O contrasinal almacenado do usuario.
     */
    public String getContrasinal() {
        return contrasinal;
    }

    /**
     * Setter do contrasinal do usuario
     *
     * @param contrasinal O contrasinal a asignar ao usuario.
     */
    public void setContrasinal(String contrasinal) {
        this.contrasinal = contrasinal;
    }

    /**
     * Getter do nome do usuario.
     *
     * @return O nome almacenado do usuario.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Setter do nome do usuario.
     *
     * @param nome O nome a asignar ao usuario.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Getter do número de teléfono do usuario.
     *
     * @return O número de teléfono almacenado do usuario.
     */
    public String getNumTelefono() {
        return numTelefono;
    }

    /**
     * Setter do número de teléfono do usuario.
     *
     * @param numTelefono O número de teléfono a asignar ao usuario.
     */
    public void setNumTelefono(String numTelefono) {
        this.numTelefono = numTelefono;
    }

    /**
     * Getter do DNI do usuario.
     *
     * @return O DNI almacenado do usuario.
     */
    public String getDNI() {
        return DNI;
    }

    /**
     * Setter do DNI do usuario.
     *
     * @param DNI O dni a asignar ao usuario.
     */
    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    /**
     * Getter do correo electrónico do usuario.
     *
     * @return A dirección de correo electrónico almacenada do usuario.
     */
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    /**
     * Setter do correo electrónico do usuario.
     *
     * @param correoElectronico A dirección de correo electrónico a asignar ao usuario.
     */
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    /**
     * Getter do IBAN da conta bancaria do usuario.
     *
     * @return O IBAN da conta bancaria almacenado do usuario.
     */
    public String getIBANconta() {
        return IBANconta;
    }

    /**
     * Setter do IBAN da conta bancaria do usuario.
     *
     * @param IBANconta O IBAN da conta bancaria a asignar ao usuario.
     */
    public void setIBANconta(String IBANconta) {
        this.IBANconta = IBANconta;
    }

    /**
     * Getter da data de alta do usuario.
     *
     * @return A data de alta almacenada do usuario.
     */
    public Date getDataAlta() {
        return dataAlta;
    }

    /**
     * Setter da data de alta do usuario.
     *
     * @param dataAlta A data de alta a asignar ao usuario.
     */
    public void setDataAlta(Date dataAlta) {
        this.dataAlta = dataAlta;
    }

    /**
     * Getter da data de baixa do usuario.
     *
     * @return A data de baixa almacenada do usuario.
     */
    public Date getDataBaixa() {
        return dataBaixa;
    }

    /**
     * Setter da data de baixa do usuario.
     *
     * @param dataBaixa A data de baixa a asignar ao usuario.
     */
    public void setDataBaixa(Date dataBaixa) {
        this.dataBaixa = dataBaixa;
    }

    /**
     * Getter do tipo de usuario.
     *
     * @return O tipo deste usuario.
     */
    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    /**
     * Setter do tipo de usuario.
     *
     * @param tipoUsuario O tipo a asignar a este usuario.
     */
    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    /**
     * Getter das dificultades do usuario.
     *
     * @return As dificultades almacenadas do usuario.
     */
    public String getDificultades() {
        return dificultades;
    }

    /**
     * Setter das dificultades do usuario.
     *
     * @param dificultades As dificultades a asignar ao usuario.
     */
    public void setDificultades(String dificultades) {
        this.dificultades = dificultades;
    }

    /**
     * Getter da data de nacemento do usuario.
     *
     * @return A data de nacemento almacenada do usuario.
     */
    public Date getDataNacemento() {
        return dataNacemento;
    }

    /**
     * Setter da data de nacemento do usuario.
     *
     * @param dataNacemento A data de nacemento a asignar ao usuario.
     */
    public void setDataNacemento(Date dataNacemento) {
        this.dataNacemento = dataNacemento;
    }

    /**
     * Getter da idade do usuario.
     *
     * @return A idade almacenada do usuario.
     */
    public Integer getIdade() {
        return idade;
    }

    /**
     * Setter da idade do usuario
     *
     * @param idade A idade a asignar ao usuario
     */
    public void setIdade(Integer idade) {
        this.idade = idade;
    }


    /**
     * Método que comproba cando dous usuarios son iguais.
     *
     * @param o O obxecto a comparar co usuario.
     * @return booleano indicador de se os dous obxectos son o mesmo realmente.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Usuario) {
            return ((Usuario) o).getLogin().equals(this.login);
        }
        return false;
    }

    /**
     * Método que permite amosar un usuario como cadea de caracteres.
     *
     * @return O usuario representado cun String.
     */
    @Override
    public String toString() {
        //Utilidade ao amosar usuarios nas táboas: amosamos en principio só o seu login.
        return getLogin();
    }


}

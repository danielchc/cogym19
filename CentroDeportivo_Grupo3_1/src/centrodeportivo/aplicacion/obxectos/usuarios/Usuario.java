package centrodeportivo.aplicacion.obxectos.usuarios;

import centrodeportivo.aplicacion.obxectos.tipos.TipoUsuario;

import java.sql.Date;

public class Usuario {

    /**
     * Atributos da clase Usuario
     */
    private String login;
    private String contrasinal;
    private String numTelefono;
    private String correoElectronico;
    private String IBANconta;
    private Date dataAlta;
    private Date dataBaixa;
    private String DNI;
    private String nome;
    private String dificultades;
    private Date dataNacemento;
    private TipoUsuario tipoUsuario;

    /**
     * Constructor coa clave primaria
     * @param login login do Usuario
     */
    public Usuario(String login){
        this.login=login;
    }

    /**
     * Constructor con todos os datos do Usuario
     * @param login login do Usuario
     * @param contrasinal contrasinal
     * @param numTelefono número de teléfono
     * @param correoElectronico correo electrónico
     * @param IBANconta IBAN do usuario
     * @param dataAlta data de alta no sistema
     */
    public Usuario(String login,String contrasinal,String DNI, String nome, String dificultades, Date dataNacemento,String numTelefono,String correoElectronico,String IBANconta,Date dataAlta){
        this(login, contrasinal, DNI, nome, dificultades, dataNacemento, numTelefono, correoElectronico, IBANconta);
        this.dataAlta=dataAlta;
    }

    /**
     * Constructor sen a data de alta no sistema para enviar datos dende a aplicación á insercción na base de datos.
     * A data de alta introdúcese na transacción, por eso non se pasa como parámetro.
     * @param login login do Usuario
     * @param contrasinal contrasinal
     * @param numTelefono número de teléfono
     * @param correoElectronico correo electrónico
     * @param IBANconta IBAN do usuario
     */
    public Usuario(String login,String contrasinal,String DNI, String nome, String dificultades, Date dataNacemento,String numTelefono,String correoElectronico,String IBANconta){
        this.login=login;
        this.contrasinal=contrasinal;
        this.numTelefono=numTelefono;
        this.correoElectronico=correoElectronico;
        this.IBANconta=IBANconta;
        this.DNI = DNI;
        this.nome = nome;
        this.dificultades = dificultades;
        this.dataNacemento = dataNacemento;
    }

    public boolean estaDeBaixa(){
        return this.dataBaixa!=null;
    }

    /**
     * Getters e Setters
     */

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getContrasinal() {
        return contrasinal;
    }

    public void setContrasinal(String contrasinal) {
        this.contrasinal = contrasinal;
    }

    public String getNumTelefono() {
        return numTelefono;
    }

    public void setNumTelefono(String numTelefono) {
        this.numTelefono = numTelefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getIBANconta() {
        return IBANconta;
    }

    public void setIBANconta(String IBANconta) {
        this.IBANconta = IBANconta;
    }

    public Date getDataAlta() {
        return dataAlta;
    }

    public void setDataAlta(Date dataAlta) {
        this.dataAlta = dataAlta;
    }

    public Date getDataBaixa() {
        return dataBaixa;
    }

    public void setDataBaixa(Date dataBaixa) {
        this.dataBaixa = dataBaixa;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDificultades() {
        return dificultades;
    }

    public void setDificultades(String dificultades) {
        this.dificultades = dificultades;
    }

    public Date getDataNacemento() {
        return dataNacemento;
    }

    public void setDataNacemento(Date dataNacemento) {
        this.dataNacemento = dataNacemento;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    /**
     * Equals e toString
     */
    @Override
    public boolean equals(Object o){
        if(o instanceof Usuario){
            return (((Usuario) o).getLogin().equals(this.login))&&(((Usuario) o).tipoUsuario==this.tipoUsuario);
        }
        return false;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "login='" + login + '\'' +
                ", contrasinal='" + contrasinal + '\'' +
                ", numTelefono='" + numTelefono + '\'' +
                ", correoElectronico='" + correoElectronico + '\'' +
                ", IBANconta='" + IBANconta + '\'' +
                ", dataAlta=" + dataAlta +
                ", dataBaixa=" + dataBaixa +
                ", DNI='" + DNI + '\'' +
                ", nome='" + nome + '\'' +
                ", dificultades='" + dificultades + '\'' +
                ", dataNacemento=" + dataNacemento +
                ", tipoUsuario=" + tipoUsuario +
                '}';
    }
}

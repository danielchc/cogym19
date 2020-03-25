package aplicacion;

import java.sql.Date;

public class Usuario {
    private String login;
    private String contrasinal;
    private String nome;
    private String numTelefono;
    private String DNI;
    private String correoElectronico;
    private String IBANconta;
    private Date dataAlta;
    private Date dataBaixa;

    public Usuario(String login,String contrasinal,String nome,String numTelefono,String DNI,String correoElectronico,String IBANconta,Date dataAlta){
        this(login, contrasinal, nome, numTelefono, DNI, correoElectronico, IBANconta);
        this.dataAlta=dataAlta;
    }

    public Usuario(String login,String contrasinal,String nome,String numTelefono,String DNI,String correoElectronico,String IBANconta){
        this.login=login;
        this.contrasinal=contrasinal;
        this.nome=nome;
        this.numTelefono=numTelefono;
        this.DNI=DNI;
        this.correoElectronico=correoElectronico;
        this.IBANconta=IBANconta;
    }

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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumTelefono() {
        return numTelefono;
    }

    public void setNumTelefono(String numTelefono) {
        this.numTelefono = numTelefono;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
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

    @Override
    public boolean equals(Object o){
        if(o instanceof Usuario){
            return ((Usuario) o).getLogin().equals(this.login);
        }
        return false;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "login='" + login + '\'' +
                ", contrasinal='" + contrasinal + '\'' +
                ", nome='" + nome + '\'' +
                ", numTelefono='" + numTelefono + '\'' +
                ", DNI='" + DNI + '\'' +
                ", correoElectronico='" + correoElectronico + '\'' +
                ", IBANconta='" + IBANconta + '\'' +
                ", dataAlta=" + dataAlta +
                ", dataBaixa=" + dataBaixa +
                '}';
    }
}

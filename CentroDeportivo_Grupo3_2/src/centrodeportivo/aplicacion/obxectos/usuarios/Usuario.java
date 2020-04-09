package centrodeportivo.aplicacion.obxectos.usuarios;

import centrodeportivo.aplicacion.obxectos.tipos.TipoUsuario;

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
    private TipoUsuario tipoUsuario;
    private String dificultades;
    private Date dataNacemento;


    public Usuario(String login){
        this.login=login;
    }

    public Usuario(String login,String contrasinal,String DNI, String nome, String dificultades, Date dataNacemento,String numTelefono,String correoElectronico,String IBANconta,Date dataAlta){
        this(login, contrasinal, DNI, nome, dificultades, dataNacemento, numTelefono, correoElectronico, IBANconta);
        this.dataAlta=dataAlta;
    }

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

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getDificultades(){
        return dificultades;
    }

    public void setDificultades(String dificultades){
        this.dificultades = dificultades;
    }

    public Date getDataNacemento(){
        return dataNacemento;
    }

    public void setDataNacemento(Date dataNacemento){
        this.dataNacemento = dataNacemento;
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
                ", tipoUsuario=" + tipoUsuario +
                "}\n";
    }
}

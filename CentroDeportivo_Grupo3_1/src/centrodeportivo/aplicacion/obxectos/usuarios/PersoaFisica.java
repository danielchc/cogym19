package centrodeportivo.aplicacion.obxectos.usuarios;

import java.sql.Date;

public class PersoaFisica  {
    private String DNI;
    private String nome;
    private String dificultades;
    private Date dataNacemento;

    public PersoaFisica() {

    }

    public PersoaFisica(String DNI, String nome, String dificultades, Date dataNacemento) {
        this.DNI = DNI;
        this.nome = nome;
        this.dificultades = dificultades;
        this.dataNacemento = dataNacemento;
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
}

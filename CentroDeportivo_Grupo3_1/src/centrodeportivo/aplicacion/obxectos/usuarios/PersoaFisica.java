package centrodeportivo.aplicacion.obxectos.usuarios;

import java.sql.Date;

public final class PersoaFisica {

    /**
     * Atributos da clase PersoaFisica
     */
    private String DNI;
    private String nome;
    private String dificultades;
    private Date dataNacemento;


    /**
     * Constructo con so a clave primaria como parámetro
     * @param DNI DNI da persoa
     */
    public PersoaFisica(String DNI) {
        this.DNI = DNI;
    }

    /**
     * Contructor con todos os datos dunha persoa como parámetros
     * @param DNI DNI da persoa
     * @param nome nome da persoa
     * @param dificultades dificultades da persoa
     * @param dataNacemento data de nacemento
     */
    public PersoaFisica(String DNI, String nome, String dificultades, Date dataNacemento) {
        this.DNI = DNI;
        this.nome = nome;
        this.dificultades = dificultades;
        this.dataNacemento = dataNacemento;
    }


    /**
     * Getters e Settes
     */
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


    /**
     * Equals e toString
     */
    @Override
    public String toString() {
        return "PersoaFisica{" +
                "DNI='" + DNI + '\'' +
                ", nome='" + nome + '\'' +
                ", dificultades='" + dificultades + '\'' +
                ", dataNacemento=" + dataNacemento +
                '}';
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof PersoaFisica){
            return ((PersoaFisica) o).getDNI().equals(this.getDNI());
        }
        return false;
    }
}

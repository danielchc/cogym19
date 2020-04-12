package centrodeportivo.aplicacion.obxectos.usuarios;

import java.sql.Date;
import java.util.Objects;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public class PersoaFisica  {

    /**
     * Atributos da clase Persoa Física
     */
    private String DNI;
    private String nome;
    private String dificultades;
    private Date dataNacemento;

    /**
     * Constructor vacía.
     * Úsase nas clases fillas.
     */
    public PersoaFisica() {

    }

    /**
     * Constructor cos datos dunha persoa
     * @param DNI dni da persoa.
     * @param nome nome da persoa
     * @param dificultades dificultades físicas
     * @param dataNacemento data de nacemento
     */
    public PersoaFisica(String DNI, String nome, String dificultades, Date dataNacemento) {
        this.DNI = DNI;
        this.nome = nome;
        this.dificultades = dificultades;
        this.dataNacemento = dataNacemento;
    }

    /**
     *  Getters e Setters
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersoaFisica that = (PersoaFisica) o;
        return Objects.equals(DNI, that.DNI);
    }

    @Override
    public int hashCode() {
        return Objects.hash(DNI, nome, dificultades, dataNacemento);
    }
}

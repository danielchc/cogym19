package centrodeportivo.aplicacion.obxectos.actividades;

import java.util.Objects;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public class TipoActividade {

    /**
     * Atributos da clase Tipo de Actividade
     */
    private Integer codTipoActividade;
    private String nome;
    private String descricion;

    /**
     * @param codTipoActividade código
     * @param nome              nome do tipo
     * @param descricion        descrición
     */
    public TipoActividade(Integer codTipoActividade, String nome, String descricion) {
        this.codTipoActividade = codTipoActividade;
        this.nome = nome;
        this.descricion = descricion;
    }

    /**
     * Constructor coa clave primaria.
     *
     * @param codTipoActividade código do tipo
     * @param nome              nome do tipo
     */
    public TipoActividade(Integer codTipoActividade, String nome) {
        this.codTipoActividade = codTipoActividade;
        this.nome = nome;
    }


    /**
     * Getters e Setters
     */
    public Integer getCodTipoActividade() {
        return codTipoActividade;
    }

    public void setCodTipoActividade(Integer codTipoActividade) {
        this.codTipoActividade = codTipoActividade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricion() {
        return descricion;
    }

    public void setDescricion(String descricion) {
        this.descricion = descricion;
    }

    /**
     * Equals e toString
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoActividade that = (TipoActividade) o;
        return Objects.equals(codTipoActividade, that.codTipoActividade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codTipoActividade);
    }
}

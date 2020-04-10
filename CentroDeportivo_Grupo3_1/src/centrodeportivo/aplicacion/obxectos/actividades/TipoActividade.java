package centrodeportivo.aplicacion.obxectos.actividades;

import java.util.Objects;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public class TipoActividade{

    private Integer codTipoActividade;
    private String nome;
    private String descricion;

    public TipoActividade(Integer codTipoActividade, String nome, String descricion) {
        this.codTipoActividade = codTipoActividade;
        this.nome = nome;
        this.descricion = descricion;
    }

    public TipoActividade(Integer codTipoActividade){
        this.codTipoActividade=codTipoActividade;
    }

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

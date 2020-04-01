package centrodeportivo.aplicacion.obxectos.actividades;

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
}

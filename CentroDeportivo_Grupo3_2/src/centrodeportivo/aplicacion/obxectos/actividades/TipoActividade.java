package centrodeportivo.aplicacion.obxectos.actividades;

//Clase que almacenará información sobre tipos de actividades:
public class TipoActividade{

    //Atributos:
    private int codTipoActividade;
    private String nome;
    private String descricion;

    //Faremos dous construtores: un que involucre o código e outro que non.
    public TipoActividade(Integer codTipoActividade, String nome, String descricion){
        this.codTipoActividade=codTipoActividade;
        this.nome = nome;
        this.descricion = descricion;
    }

    public TipoActividade(String nome, String descricion){
        this.nome = nome;
        this.descricion = descricion;
    }

    public TipoActividade(String nome){
        this.nome = nome;
    }

    //Getters e setters
    public Integer getCodTipoActividade() {
        return codTipoActividade;
    }

    public void setCodTipoActividade(Integer codTipoActividade) {
        this.codTipoActividade = codTipoActividade;
    }

    public String getNome(){
        return this.nome;
    }

    public void setNome(String nome) {this.nome = nome;}

    public String getDescricion(){
        return this.descricion;
    }

    public void setDescricion(String descricion) {
        this.descricion = descricion;
    }
}

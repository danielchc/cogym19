package centrodeportivo.aplicacion.obxectos.actividades;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Víctor Barreiro
 * Clase que almacenará información sobre os tipos de actividade que se recuperarán da base de datos.
 */
public class TipoActividade {

    /**
     * Atributos dun tipo de actividade: coinciden coa información que se ten almacenada na base de datos.
     */
    private int codTipoActividade; //Código do tipo de actividade.
    private String nome; //Nome do tipo de actividade.
    private String descricion; //Descrición do tipo de actividade.


    /**
     * Constructor que involucra todos os atributos. Usarémolo cando se coñeza toda a información sobre o tipo de actividade
     * de antemán (por exemplo, cando se recuperen datos da base de datos).
     * @param codTipoActividade O código do tipo de actividade.
     * @param nome O nome do tipo de actividade
     * @param descricion A descrición asociada ao tipo de actividade.
     */
    public TipoActividade(int codTipoActividade, String nome, String descricion) {
        this.codTipoActividade = codTipoActividade;
        this.nome = nome;
        this.descricion = descricion;
    }

    /**
     * Constructor que involucra sómentes o nome e a descrición, que son os atributos que pode introducir o usuario.
     * Usarémolo precisamente cando se queira insertar un novo tipo de actividade na base de datos (e aínda non se coñeza
     * o código xerado).
     * @param nome O nome do tipo de actividade
     * @param descricion A descrición asociada ao tipo de actividade.
     */
    public TipoActividade(String nome, String descricion) {
        this.nome = nome;
        this.descricion = descricion;
    }


    /**
     * Constructor que involucra somentes o nome do tipo de actividade, que usaremos para cando se fagan buscas (pois
     * só se busca polo nome, tanto nos teñen o resto de atributos da clase).
     * @param nome O nome do tipo de actividade considerado.
     */
    public TipoActividade(String nome) {
        this.nome = nome;
    }


    /**
     * Constructor que involucra só o código do tipo de actividade, necesario cando se fai referencia ao tipo de actividade
     * dende outras clases e non se quere almacenar nada máis.
     * @param codTipoActividade O código do tipo de actividade considerado.
     */
    public TipoActividade(int codTipoActividade) {
        this.codTipoActividade = codTipoActividade;
    }

    /**
     * Getter do código do tipo de actividade.
     * @return O código que contén esta instancia como atributo.
     */
    public int getCodTipoActividade() {
        return codTipoActividade;
    }

    /**
     * Setter do código do tipo de actividade.
     * @param codTipoActividade O código do tipo de actividade a asignar.
     */
    public void setCodTipoActividade(int codTipoActividade) {
        this.codTipoActividade = codTipoActividade;
    }


    /**
     * Getter do nome do tipo de actividade.
     * @return O nome que ten esa instancia como atributo.
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * Setter do nome do tipo de actividade
     * @param nome O nome do tipo de actividade a asignar.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Getter da descrición do tipo de actividade
     * @return A descrición que ten esta instancia como atributo.
     */
    public String getDescricion() {
        return this.descricion;
    }

    /**
     * Setter da descrición dun tipo de actividade.
     * @param descricion A descrición do tipo de actividade a asignar.
     */
    public void setDescricion(String descricion) {
        this.descricion = descricion;
    }

    /**
     * Método que permite convertir un tipo de actividade a cadea de caracteres.
     * @return cadea de caracteres que representa un tipo de actividade.
     */
    @Override
    public String toString(){
        return "TipoActividade{" +
                "codTipoActividade= " + codTipoActividade +
                ", nome= '" + nome + "\'" +
                ", descrición= '" + descricion + "\'" +
                "}";
    }

    /**
     * Método que permite determinar se dous obxectos son iguais
     * @param obj O obxecto a comparar con este tipo de actividade.
     * @return Booleano que indica se dúas instancias de tipo de actividade son iguais.
     */
    @Override
    public boolean equals(Object obj){
        if(obj instanceof TipoActividade){
            //Consideramos que dúas instancias do tipo de actividade son iguais se teñen o mesmo código.
            if(((TipoActividade)obj).getCodTipoActividade() == codTipoActividade){
                return true;
            }
        }
        return false;
    }
}

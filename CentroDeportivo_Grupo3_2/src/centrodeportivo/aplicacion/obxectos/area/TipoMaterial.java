package centrodeportivo.aplicacion.obxectos.area;

// Clase que almacenará información sobre tipos de material:
public class TipoMaterial {


    // Atributos
    private int codTipoMaterial;
    private String nome;


    // Constructores
    public TipoMaterial(int codTipoMaterial, String nome) {
        this.codTipoMaterial = codTipoMaterial;
        this.nome = nome;
    }

    public TipoMaterial(String nome) {
        this.nome = nome;
    }


    // Getters e setters
    public void setCodTipoMaterial(int codTipoMaterial) {
        this.codTipoMaterial = codTipoMaterial;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCodTipoMaterial() {
        return codTipoMaterial;
    }

    public String getNome() {
        return nome;
    }


}


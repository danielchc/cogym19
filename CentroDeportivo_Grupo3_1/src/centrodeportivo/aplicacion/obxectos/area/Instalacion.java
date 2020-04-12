package centrodeportivo.aplicacion.obxectos.area;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public class Instalacion {

    /**
     * Atributos da clase Instalación.
     */
    private int codInstalacion;
    private String nome;
    private String numTelefono;
    private String direccion;

    /**
     * Constructor coa clave primaria
     * @param codInstalacion  código da instalación
     */
    public Instalacion(int codInstalacion) {
        this.codInstalacion = codInstalacion;
    }

    /**
     * Constructor de instalación
     * @param nome nome da instalación
     * @param numTelefono número de contacto
     * @param direccion dirección da instalación
     */
    public Instalacion(String nome, String numTelefono, String direccion) {
        this.nome = nome;
        this.numTelefono = numTelefono;
        this.direccion = direccion;
    }

    /**
     * Constructor con todos os datos da instalación
     * @param codInstalacion código
     * @param nome nome da instalación
     * @param numTelefono número de contacto
     * @param direccion dirección da instalación
     */
    public Instalacion(int codInstalacion, String nome, String numTelefono, String direccion) {
        this(nome, numTelefono, direccion);
        this.codInstalacion = codInstalacion;
    }

    /**
     * Getter e Setters
     */
    public int getCodInstalacion() {
        return codInstalacion;
    }

    public void setCodInstalacion(int codInstalacion) {
        this.codInstalacion = codInstalacion;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Equals e toString
     */
    @Override
    public String toString() {
        return "Instalacion{" +
                "codInstalacion=" + codInstalacion +
                ", nome='" + nome + '\'' +
                ", numTelefono='" + numTelefono + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Instalacion){
            if(((Instalacion)obj).getCodInstalacion() == this.codInstalacion){
                return true;
            }
        }
        return false;
    }
}

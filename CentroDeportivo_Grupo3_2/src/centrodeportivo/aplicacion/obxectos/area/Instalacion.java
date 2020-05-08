package centrodeportivo.aplicacion.obxectos.area;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Victor Barreiro
 * Clase que almacenará información sobre as instalacións que se extraian da base de datos.
 */
public class Instalacion {

    /**
     * Atributos dunha instalación: coinciden coa información que se ten gardada na base de datos.
     */
    private Integer codInstalacion; //Código dunha instalación
    private String nome; //Nome dunha instalación
    private String numTelefono; //Número de telefono
    private String direccion; //Dirección.

    /**
     * Constructor cun só argumento: o código da instalación (que é a clave primaria).
     *
     * @param codInstalacion O código identificador da instalación.
     */
    public Instalacion(Integer codInstalacion) {
        this.codInstalacion = codInstalacion;
    }

    /**
     * Constructor con tres argumentos: usarase cando un usuario queira introducir unha nova instalación, dado que
     * inicialmente non se coñecerá o seu código (ata que se inserte).
     *
     * @param nome O nome da instalación
     * @param numTelefono O número de teléfono da instalación
     * @param direccion A dirección da instalación
     */
    public Instalacion(String nome, String numTelefono, String direccion) {
        //Asignamos os atributos corespondentes:
        this.nome = nome;
        this.numTelefono = numTelefono;
        this.direccion = direccion;
    }


    /**
     * Constructor con todos os argumentos, para os casos nos que se teña toda a información (ao recuperala).
     *
     * @param codInstalacion O código identificador da instalación.
     * @param nome O nome da instalación
     * @param numTelefono O número de teléfono da instalación
     * @param direccion A dirección da instalación
     */
    public Instalacion(Integer codInstalacion, String nome, String numTelefono, String direccion) {
        this(nome, numTelefono, direccion);
        this.codInstalacion = codInstalacion;
    }

    /**
     * Getter do código da instalación.
     *
     * @return codInstalación -> O código da instalación
     */
    public Integer getCodInstalacion() {
        return codInstalacion;
    }

    /**
     * Setter do código da instalación
     *
     * @param codInstalacion O código da instalación a asignar
     */
    public void setCodInstalacion(Integer codInstalacion) {
        this.codInstalacion = codInstalacion;
    }

    /**
     * Getter do nome da instalación
     *
     * @return nome -> Nome da instalación
     */
    public String getNome() {
        return nome;
    }

    /**
     * Setter do nome da instalación
     *
     * @param nome O nome da instalación a asignar
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Getter do número de teléfono da instalación
     *
     * @return numTelefono -> O número de teléfono da instalación.
     */
    public String getNumTelefono() {
        return numTelefono;
    }

    /**
     * Setter do número de teléfono da instalación
     *
     * @param numTelefono O número de teléfono da instalación a asignar.
     */
    public void setNumTelefono(String numTelefono) {
        this.numTelefono = numTelefono;
    }

    /**
     * Getter da dirección da instalación:
     *
     * @return direccion -> A dirección da instalación
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Setter da dirección da instalación
     *
     * @param direccion A dirección da instalación a asignar
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Método que permite convertir unha instalación a cadea de caracteres.
     *
     * @return instalacion representada en forma de caracteres polos seus campos.
     */
    @Override
    public String toString() {
        return nome;
    }

    /**
     * Método que comproba se dúas instalacións son iguais.
     *
     * @param obj O obxecto a comparar coa instalación.
     * @return booleano que indica se a instalación coincide coa pasada como argumento.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Instalacion) {
            if (((Instalacion) obj).getCodInstalacion() == this.codInstalacion) {
                return true;
            }
        }
        return false;
    }
}

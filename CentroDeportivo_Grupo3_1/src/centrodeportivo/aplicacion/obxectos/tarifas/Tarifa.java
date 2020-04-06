package centrodeportivo.aplicacion.obxectos.tarifas;

/**
 *
 */
public final class Tarifa {


    /**
     * Atributos da clase Tarifa
     * Esta clase utilízase para gardar os datos dunha Tarifa dun socio.
     */
    private int codTarifa;
    private String nome;
    private Integer maxActividades;
    private float prezoBase;
    private float prezoExtras;

    /**
     * Constructor con todos os parámetros para recuperar os datos da base e envialos á aplicación.
     * @param codTarifa  Código da tarifa
     * @param nome Nome da Tarifa
     * @param maxActividades Máximo de actividades dunha tarifa
     * @param prezoBase Prezo mensual dunha tarifa
     * @param prezoExtras Prezo extra por cada tarfia a maiores de maxActividades
     */
    public Tarifa(int codTarifa,String nome,Integer maxActividades,float prezoBase,float prezoExtras){
        this(nome, maxActividades, prezoBase, prezoExtras);
        this.codTarifa=codTarifa;
    }

    /**
     * Constructor sen o parámetro codTarifa.
     * Este constructor emprégase para enviar datos dende a aplicación ao DAO.
     * O código non fai falta xa que se xera na inserción na base de datos
     * @param nome Nome da Tarifa
     * @param maxActividades Máximo de actividades dunha tarifa
     * @param prezoBase Prezo mensual dunha tarifa
     * @param prezoExtras Prezo extra por cada tarfia a maiores de maxActividades
     */
    public Tarifa(String nome,Integer maxActividades,float prezoBase,float prezoExtras){
        this.nome=nome;
        this.maxActividades=maxActividades;
        this.prezoBase=prezoBase;
        this.prezoExtras=prezoExtras;
    }


    /**
     * Getters e Setters da clase Tarifa.
     */
    public int getCodTarifa() {
        return codTarifa;
    }

    public void setCodTarifa(int codTarifa) {
        this.codTarifa = codTarifa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getMaxActividades() {
        return maxActividades;
    }

    public void setMaxActividades(Integer maxActividades) {
        this.maxActividades = maxActividades;
    }

    public float getPrezoBase() {
        return prezoBase;
    }

    public void setPrezoBase(float prezoBase) {
        this.prezoBase = prezoBase;
    }

    public float getPrezoExtras() {
        return prezoExtras;
    }

    public void setPrezoExtras(float prezoExtras) {
        this.prezoExtras = prezoExtras;
    }


    /**
     * @param o Obxecto a comparar.
     * @return true se son iguais, false se non.
     */
    @Override
    public boolean equals(Object o){
        if(o instanceof Tarifa){
            return ((Tarifa) o).getCodTarifa()==(this.codTarifa);
        }
        return false;
    }

    /**
     * @return String coa información básica dunha tarifa.
     */
    @Override
    public String toString() {
        return  "["+nome+"]:" +
                " maxActividades=" + maxActividades +
                ", prezoBase=" + prezoBase +
                ", prezoExtras=" + prezoExtras;
    }
}

package centrodeportivo.aplicacion.obxectos.tarifas;

public final class Tarifa {
    private int codTarifa;
    private String nome;
    private Integer maxActividades;
    private float prezoBase;
    private float prezoExtras;

    public Tarifa(int codTarifa,String nome,Integer maxActividades,float prezoBase,float prezoExtras){
        this(nome, maxActividades, prezoBase, prezoExtras);
        this.codTarifa=codTarifa;
    }

    public Tarifa(String nome,Integer maxActividades,float prezoBase,float prezoExtras){
        this.nome=nome;
        this.maxActividades=maxActividades;
        this.prezoBase=prezoBase;
        this.prezoExtras=prezoExtras;
    }

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

    @Override
    public boolean equals(Object o){
        if(o instanceof Tarifa){
            return ((Tarifa) o).getCodTarifa()==(this.codTarifa);
        }
        return false;
    }

    @Override
    public String toString() {
        return  "["+nome+"]:" +
                " maxActividades=" + maxActividades +
                ", prezoBase=" + prezoBase +
                ", prezoExtras=" + prezoExtras;
    }
}

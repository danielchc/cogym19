package aplicacion;

public final class Tarifa {
    private String codTarifa;
    private String nome;
    private Integer maxActividades;
    private float prezoBase;
    private float prezoExtras;

    public Tarifa(String codTarifa,String nome,Integer maxActividades,float prezoBase,float prezoExtras){
        this.codTarifa=codTarifa;
        this.nome=nome;
        this.maxActividades=maxActividades;
        this.prezoBase=prezoBase;
        this.prezoExtras=prezoExtras;
    }

    public String getCodTarifa() {
        return codTarifa;
    }

    public void setCodTarifa(String codTarifa) {
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
            return ((Tarifa) o).getCodTarifa().equals(this.codTarifa);
        }
        return false;
    }
}

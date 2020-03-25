package centrodeportivo.aplicacion.obxectos;


import java.sql.Timestamp;

public final class Mensaxe {
    private String emisor;
    private String receptor;
    private Timestamp dataEnvio;
    private String contido;
    private boolean lido;

    public Mensaxe(String emisor,String receptor,Timestamp dataEnvio,String contido,boolean lido){
        this(emisor, receptor, contido);
        this.dataEnvio=dataEnvio;
        this.lido=lido;
    }

    public Mensaxe(String emisor,String receptor,String contido){
        this.emisor=emisor;
        this.receptor=receptor;
        this.contido=contido;
        this.lido=false;
    }


    public String getEmisor() {
        return emisor;
    }

    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    public String getReceptor() {
        return receptor;
    }

    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }

    public Timestamp getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(Timestamp dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public String getContido() {
        return contido;
    }

    public void setContido(String contido) {
        this.contido = contido;
    }

    public boolean isLido() {
        return lido;
    }

    public void setLido(boolean lido) {
        this.lido = lido;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Mensaxe){
            return ((Mensaxe) o).getEmisor().equals(this.emisor) && ((Mensaxe) o).getReceptor().equals(this.receptor) && ((Mensaxe) o).getDataEnvio().equals(this.dataEnvio);
        }
        return false;
    }

    @Override
    public String toString() {
        return "Mensaxe{" +
                "emisor='" + emisor + '\'' +
                ", receptor='" + receptor + '\'' +
                ", dataEnvio=" + dataEnvio +
                ", contido='" + contido + '\'' +
                ", lido=" + lido +
                '}';
    }
}

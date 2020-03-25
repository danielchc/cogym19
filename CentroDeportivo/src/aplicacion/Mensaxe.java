package aplicacion;


import java.sql.Date;

public final class Mensaxe {
    private String emisor;
    private String receptor;
    private Date dataEnvio;
    private String contido;

    public Mensaxe(String emisor,String receptor,Date dataEnvio,String contido){
        this.emisor=emisor;
        this.receptor=receptor;
        this.dataEnvio=dataEnvio;
        this.contido=contido;
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

    public Date getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(Date dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public String getContido() {
        return contido;
    }

    public void setContido(String contido) {
        this.contido = contido;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Mensaxe){
            return ((Mensaxe) o).getEmisor().equals(this.emisor) && ((Mensaxe) o).getReceptor().equals(this.receptor) && ((Mensaxe) o).getDataEnvio().equals(this.dataEnvio);
        }
        return false;
    }
}

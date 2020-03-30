package centrodeportivo.aplicacion.obxectos;


import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;

import java.sql.Timestamp;

public final class Mensaxe {
    private Usuario emisor;
    private Usuario receptor;
    private Timestamp dataEnvio;
    private String contido;
    private boolean lido;

    public Mensaxe(Usuario emisor,Usuario receptor,Timestamp dataEnvio,String contido,boolean lido){
        this(emisor, receptor, contido);
        this.dataEnvio=dataEnvio;
        this.lido=lido;
    }

    public Mensaxe(Usuario emisor,Usuario receptor,String contido){
        this.emisor=emisor;
        this.receptor=receptor;
        this.contido=contido;
        this.lido=false;
    }


    public Usuario getEmisor() {
        return emisor;
    }

    public void setEmisor(Usuario emisor) {
        this.emisor = emisor;
    }

    public Usuario getReceptor() {
        return receptor;
    }

    public void setReceptor(Usuario receptor) {
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

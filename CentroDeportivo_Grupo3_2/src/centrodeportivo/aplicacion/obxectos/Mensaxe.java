package centrodeportivo.aplicacion.obxectos;


import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;

import java.sql.Timestamp;

/**
 * No noso caso non temos que xestionar o envío de mensaxes entre pares, pero si que nos resultará interesante o envío
 * de mensaxes a modo de aviso, por exemplo, cando se crea unha actividade.
 *
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Victor Barreiro
 */
public class Mensaxe {
    /**
     * Atributos que necesitamos: non contemplamos o receptor no noso caso.
     */
    private Usuario emisor;
    private Timestamp dataEnvio;
    private String contido;
    private boolean lido;

    /**
     * Constructor
     */
    public Mensaxe(Usuario emisor,String contido){
        this.emisor=emisor;
        this.contido=contido;
        this.lido=false;
    }

    /**
     * Getters e setters
     */
    public Usuario getEmisor() {
        return emisor;
    }

    public void setEmisor(Usuario emisor) {
        this.emisor = emisor;
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
    public String toString() {
        return "'" + emisor + '\'' +
                ", lido=" + lido;
    }
}

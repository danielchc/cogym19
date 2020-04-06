package centrodeportivo.aplicacion.obxectos;


import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;

import java.sql.Timestamp;

public final class Mensaxe {

    /**
     * Atributos da clase Mensaxe
     */
    private Usuario emisor;
    private Usuario receptor;
    private Timestamp dataEnvio;
    private String contido;
    private boolean lido;

    /**
     * Contructor con todos os parámetros para devolver os datos da base a aplicación.
     * @param emisor Usuario que manda o mensaxe
     * @param receptor Usuario que recibe o mensaxe
     * @param dataEnvio Data de envío do mensaxe
     * @param contido Contido do mensaxe
     * @param lido boolean que indica se un mensaxe está lido ou non
     */
    public Mensaxe(Usuario emisor,Usuario receptor,Timestamp dataEnvio,String contido,boolean lido){
        this(emisor, receptor, contido);
        this.dataEnvio=dataEnvio;
        this.lido=lido;
    }

    /**
     * Contructor sen atributos dataEnvio nin lido.
     * Este constructor serve para insertar datos na base, xa que a data insértase á hora de realizar
     * a insercción na base. Lido será false sempre á hora de crear un mensaxe novo.
     * @param emisor
     * @param receptor
     * @param contido
     */
    public Mensaxe(Usuario emisor,Usuario receptor,String contido){
        this.emisor=emisor;
        this.receptor=receptor;
        this.contido=contido;
        this.lido=false;
    }


    /**
     * Getters e Setters
     */
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


    /**
     * Equals e toString
     */

    @Override
    public boolean equals(Object o){
        if(o instanceof Mensaxe){
            return ((Mensaxe) o).getEmisor().equals(this.emisor) && ((Mensaxe) o).getReceptor().equals(this.receptor) && ((Mensaxe) o).getDataEnvio().equals(this.dataEnvio);
        }
        return false;
    }

    @Override
    public String toString() {
        return "'" + emisor + '\'' +
                ", lido=" + lido;
    }
}

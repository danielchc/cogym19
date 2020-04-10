package centrodeportivo.aplicacion.obxectos;

import centrodeportivo.aplicacion.obxectos.usuarios.Socio;

import java.sql.Timestamp;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public class RexistroFisioloxico {

    /**
     * Atributos da clase RexistroFisioloxico
     */
    private Timestamp data;
    private Socio socio;
    private Float peso;
    private Float altura;
    private Float bfp;
    private Integer tensionAlta;
    private Integer tensionBaixa;
    private Integer ppm;
    private String comentario;

    /**
     * Constructor con só as claves primarias como parámetros.
     * @param data Data de creación do rexistro
     * @param socio Socio que o rexistra.
     */
    public RexistroFisioloxico(Timestamp data, Socio socio) {
        this.data = data;
        this.socio = socio;
    }

    /**
     * Constructor con todos os parámetros para devolver os datos da base á aplicación.
     * @param data Data de creación do rexistro.
     * @param socio Socio que o rexistra.
     * @param peso Peso
     * @param altura Altura
     * @param bfp Porcentaxe de grasa
     * @param tensionAlta Tensión alta
     * @param tensionBaixa Tensión baixa
     * @param ppm Pulsacións
     * @param comentario Comentario sobre o rexistro
     */
    public RexistroFisioloxico(Timestamp data, Socio socio, Float peso, Float altura, Float bfp, Integer tensionAlta, Integer tensionBaixa, Integer ppm, String comentario) {
        this.data = data;
        this.socio = socio;
        this.peso = peso;
        this.altura = altura;
        this.bfp = bfp;
        this.tensionAlta = tensionAlta;
        this.tensionBaixa = tensionBaixa;
        this.ppm = ppm;
        this.comentario = comentario;
    }

    /**
     * Constructor con todos os parámetros menos a data para insertar os datos da base á aplicación.
     * A data introdúcese á hora da inserción na base de datos, por eso non fai falta pasala como parametro.
     * @param socio Socio que o rexistra.
     * @param peso Peso
     * @param altura Altura
     * @param bfp Porcentaxe de grasa
     * @param tensionAlta Tensión alta
     * @param tensionBaixa Tensión baixa
     * @param ppm Pulsacións
     * @param comentario Comentario sobre o rexistro
     */
    public RexistroFisioloxico(Socio socio, Float peso, Float altura, Float bfp, Integer tensionAlta, Integer tensionBaixa, Integer ppm, String comentario) {
        this.socio = socio;
        this.peso = peso;
        this.altura = altura;
        this.bfp = bfp;
        this.tensionAlta = tensionAlta;
        this.tensionBaixa = tensionBaixa;
        this.ppm = ppm;
        this.comentario = comentario;
    }


    /**
     * Getters e Setters
     */
    public Timestamp getData() {
        return data;
    }

    public void setData(Timestamp data) {
        this.data = data;
    }

    public Socio getSocio() {
        return socio;
    }

    public void setSocio(Socio socio) {
        this.socio = socio;
    }

    public Float getPeso() {
        return peso;
    }

    public void setPeso(Float peso) {
        this.peso = peso;
    }

    public Float getAltura() {
        return altura;
    }

    public void setAltura(Float altura) {
        this.altura = altura;
    }

    public Float getBfp() {
        return bfp;
    }

    public void setBfp(Float bfp) {
        this.bfp = bfp;
    }

    public Integer getTensionAlta() {
        return tensionAlta;
    }

    public void setTensionAlta(Integer tensionAlta) {
        this.tensionAlta = tensionAlta;
    }

    public Integer getTensionBaixa() {
        return tensionBaixa;
    }

    public void setTensionBaixa(Integer tensionBaixa) {
        this.tensionBaixa = tensionBaixa;
    }

    public Integer getPpm() {
        return ppm;
    }

    public void setPpm(Integer ppm) {
        this.ppm = ppm;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }


    /**
     * Equals e toString
     */
    @Override
    public String toString() {
        return "RexistroMarca{" +
                "data=" + data +
                ", socio=" + socio +
                ", peso=" + peso +
                ", altura=" + altura +
                ", bfp=" + bfp +
                ", tensionAlta=" + tensionAlta +
                ", tensionBaixa=" + tensionBaixa +
                ", ppm=" + ppm +
                ", comentario='" + comentario + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof RexistroFisioloxico){
            return ((RexistroFisioloxico) o).getData().equals(this.data) && ((RexistroFisioloxico) o).getSocio().equals(this.socio);
        }
        return false;
    }
}

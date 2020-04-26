package centrodeportivo.aplicacion.obxectos.tipos;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Victor Barreiro
 *
 * Este é o tipo enumerado que empregamos para ir devolvendo diferentes resultados a raíz de insercións/modificacións
 * sobre a base de datos.
 * Imos engadindo a el diferentes tipos de resultado que iremos devolvendo e cos que xestionaremos o que facer dende
 * os controladores das ventás (en lugar de facelo máis abaixo).
 */
public enum TipoResultados {
    correcto,
    datoExiste,
    referenciaRestrict,
    incoherenciaBorrado,
    foraTempo,
    sitIncoherente

}

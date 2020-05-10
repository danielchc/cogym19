package centrodeportivo.funcionsAux;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Víctor Barreiro
 * Clase asociada a funcións que empregamos para encriptar datos.
 * Básicamente encriptaremos dúas cuestións:
 * - O contrasinal, usando o Hash SHA256.
 * - O arquivo baseDatos.properties.
 */
public abstract class Criptografia {
    //Clave
    final static String key = "3cdMAqREs7hd7c3SzDq56pgcvnXHE9MN";

    /**
     * Método que nos permite realizar o Hash SHA256 dun contrasinal.
     *
     * @param pass o contrasinal a hashear
     * @return O contrasinal hasheado
     */
    public static String hashSHA256(String pass) {
        try {
            //Tentamos aplicar o hash:
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(pass.getBytes(StandardCharsets.UTF_8));
            //Se se completa, devólvese como resultado (convertindo a cadea de bytes a caracteres):
            return convertByteArrayToHexString(hashedBytes);
        } catch (Exception ex) {
            //Se non, amosamos un erro.
            System.err.println("Erro no cálculo do hash");
        }
        return null;
    }

    /**
     * Método que nos permite convertir unha cadea de bytes a un String
     *
     * @param arrayBytes array de bytes a convertir
     * @return String correspondente ao array de bytes pasado como argumento.
     */
    private static String convertByteArrayToHexString(byte[] arrayBytes) {
        //Creamos un enteiro grande co array de bytes pasado:
        BigInteger number = new BigInteger(1, arrayBytes);
        //Realizamos a conversión con StringBuilder:
        StringBuilder hexString = new StringBuilder(number.toString(16));
        //Se a lonxitude fose menor que 32, entón engadimos 0s ata que o sexa.
        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }
        //Devolvemos o string resultante:
        return hexString.toString();
    }

}

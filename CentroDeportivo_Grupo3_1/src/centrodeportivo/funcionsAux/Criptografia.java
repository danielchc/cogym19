package centrodeportivo.funcionsAux;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public abstract class Criptografia {
    final static String key = "YGHH0!7XUHs5";

    /**
     * Método para facer o hash SHA256 dunha contrasinal.
     * @param pass contrasinal a hashear
     * @return contrasinal hasheado
     */
    public static String hashSHA256(String pass){
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(pass.getBytes(StandardCharsets.UTF_8));
            return convertByteArrayToHexString(hashedBytes);
        }catch (Exception ex) {
            System.err.println("Error calculando hash");
        }
        return null;
    }

    /**
     * Método para convertir un array de bytes a un String
     * @param arrayBytes array de bytes a convertir
     * @return String correspondente ao array de bytes
     */
    private static String convertByteArrayToHexString(byte[] arrayBytes) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < arrayBytes.length; i++) {
            stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16).substring(2));
        }
        return stringBuffer.toString();
    }

    /**
     * Método para encriptar o arquivo properties.
     * @param text array a encriptar
     * @return array encriptado
     * @throws Exception
     */
    public static byte[] encriptar(byte[] text) throws Exception {
        SecretKey myDesKey = new SecretKeySpec(hashSHA256(key).getBytes(), "AES");
        Cipher desCipher = Cipher.getInstance("AES");
        desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);
        return desCipher.doFinal(text);
    }

    /**
     * Método para desencriptar o arquivo properties.
     * @param text array a desencriptar
     * @return array desencriptado
     * @throws Exception
     */
    public static byte[] desencriptar(byte[] text) throws Exception {
        SecretKey myDesKey = new SecretKeySpec(hashSHA256(key).getBytes(), "AES");
        Cipher desCipher = Cipher.getInstance("AES");
        desCipher.init(Cipher.DECRYPT_MODE, myDesKey);
        return desCipher.doFinal(text);
    }

}

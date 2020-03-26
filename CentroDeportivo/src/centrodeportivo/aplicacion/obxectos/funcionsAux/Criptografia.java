package centrodeportivo.aplicacion.obxectos.funcionsAux;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public abstract class Criptografia {

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

    private static String convertByteArrayToHexString(byte[] arrayBytes) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < arrayBytes.length; i++) {
            stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16).substring(2));
        }
        return stringBuffer.toString();
    }

}

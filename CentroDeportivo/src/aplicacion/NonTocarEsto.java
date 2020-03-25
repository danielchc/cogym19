package aplicacion;

import javax.swing.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.concurrent.ExecutionException;

public class NonTocarEsto {

    private static String convertByteArrayToHexString(byte[] arrayBytes) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < arrayBytes.length; i++) {
            stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16).substring(2));
        }
        return stringBuffer.toString();
    }

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


    public static void mostarExcepcion(Exception e){
        JOptionPane.showMessageDialog(null,e.getStackTrace(),"Notificación",JOptionPane.ERROR_MESSAGE);
    }

    public static void mostrarMensaxe(String mensaxe){
        JOptionPane.showMessageDialog(null,mensaxe,"Notificación",JOptionPane.INFORMATION_MESSAGE);
    }

    public static void a(){
        throw new NullPointerException();
    }
}

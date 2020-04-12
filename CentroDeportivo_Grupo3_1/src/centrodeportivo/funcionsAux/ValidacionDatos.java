package centrodeportivo.funcionsAux;

import javafx.scene.Node;
import javafx.scene.control.TextInputControl;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public abstract class ValidacionDatos {

    /**
     * Método para comprobar se un DNI ten o formato correcto.
     * @param dni
     * @return
     */
    public static boolean isCorrectoDNI(String dni){
        if(dni.length()!=9 || !Character.isLetter(dni.charAt(8))) return false;
        for(int i=0;i<dni.length()-1;i++){
            if(!Character.isDigit(dni.charAt(i))) return false;
        }
        return true;
    }

    /**
     * Método para comprobar se un IBAN ten o formato correcto.
     * @param iban
     * @return
     */
    public static boolean isCorrectoIBAN(String iban){
        String ibanUpper=iban.toUpperCase();
        if(iban.length()!=24) return false;
        return ibanUpper.matches("([A-Z]{2})([0-9]{22})");
    }

    /**
     * Método para comprobar se un número da seguridade social ten o formato correcto.
     * @param nuss
     * @return
     */
    public static boolean isCorrectoNUSS(String nuss){
        if(nuss.length()<9 || nuss.length()>12) return false;
        return nuss.matches("[0-9]+");
    }

    /**
     * Método para comprobar se un número de teléfono ten o formato correcto.
     * @param tlf
     * @return
     */
    public static boolean isCorrectoTelefono(String tlf){
        return tlf.matches("[0-9]+") && tlf.length()==9;
    }

    /**
     * Método para comprobar se un correo ten o formato correcto.
     * @param mail
     * @return
     */
    public static boolean isCorrectoCorreo(String mail){
        return mail.contains("@") && mail.contains(".");
    }

    /**
     * Método para comprobar se todos os nodos de tipo insercción de texto pasados como parámetro están cubertos.
     * @param nodes lista de nodos a comprobar
     * @return true se están todos cubertos,false se non.
     */
    public static boolean estanCubertosCampos(Node...nodes){
        for(Node n:nodes){
            if(!(n instanceof TextInputControl) || ((TextInputControl) n).getText().trim().equals("")) return false;
        }
        return true;
    }
}

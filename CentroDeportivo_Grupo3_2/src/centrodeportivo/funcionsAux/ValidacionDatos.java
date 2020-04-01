package centrodeportivo.funcionsAux;

import javafx.scene.Node;
import javafx.scene.control.TextInputControl;

public abstract class ValidacionDatos {

    public static boolean isCorrectoDNI(String dni){
        if(dni.length()!=9 || !Character.isLetter(dni.charAt(8))) return false;
        for(int i=0;i<dni.length()-1;i++){
            if(!Character.isDigit(dni.charAt(i))) return false;
        }
        return true;
    }

    public static boolean isCorrectoIBAN(String iban){
        if(iban.length()!=24) return false;
        return iban.matches("([A-Z]{2})([0-9]{22})");
    }

    public static boolean isCorrectoNUSS(String nuss){
        if(nuss.length()<9 || nuss.length()>12) return false;
        return nuss.matches("[0-9]+");
    }

    public static boolean isCorrectoTelefono(String tlf){
        return tlf.matches("[0-9]+") && tlf.length()==9;
    }

    public static boolean isCorrectoCorreo(String mail){
        return mail.contains("@") && mail.contains(".");
    }

    public static boolean estanCubertosCampos(Node...nodes){
        for(Node n:nodes){
            if(!(n instanceof TextInputControl) || ((TextInputControl) n).getText().trim().equals("")) return false;
        }
        return true;
    }
}

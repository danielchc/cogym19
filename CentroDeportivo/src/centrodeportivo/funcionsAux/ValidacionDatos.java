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
        if(iban.length()!=22) return false;
        if(!Character.isLetter(iban.charAt(0)) || !Character.isLetter(iban.charAt(1))) return false;
        if(!Character.isDigit(iban.charAt(2)) || !Character.isDigit(iban.charAt(3))) return false;
        for(int i=4;i<8;i++){
            if(!Character.isLetter(iban.charAt(i))) return false;
        }
        for(int i=8;i<22;i++){
            if(!Character.isDigit(iban.charAt(i))) return false;
        }
        return true;
    }

    public static boolean isCorrectoNUSS(String nuss){
        if(nuss.length()<9 || nuss.length()>12) return false;
        return nuss.matches("[0-9]+");
    }

    public static boolean isCorrectoTelefono(String tlf){
        return tlf.matches("[0-9]+") && tlf.length()==9;
    }

    public static boolean estanCubertosCampos(Node...nodes){
        for(Node n:nodes){
            if(!(n instanceof TextInputControl) || ((TextInputControl) n).getText().trim().equals("")) return false;
        }
        return true;
    }
}

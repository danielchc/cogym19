package centrodeportivo.funcionsAux;

import javafx.scene.Node;
import javafx.scene.control.TextInputControl;

public abstract class ValidacionDatos {

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

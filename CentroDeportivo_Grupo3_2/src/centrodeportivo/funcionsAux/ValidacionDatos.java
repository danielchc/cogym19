package centrodeportivo.funcionsAux;

import javafx.scene.Node;
import javafx.scene.control.TextInputControl;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Victor Barreiro
 * Clase na que faremos métodos para a validación de datos. Serán métodos estáticos que poderemos ir chamando dende
 * diferentes partes da aplicación (enfocado realmente para a parte de interface gráfica) e poder facer algunhas
 * comprobacións comúns).
 */
public abstract class ValidacionDatos {

    public static boolean isCorrectoTelefono(String tlf){
        return tlf.matches("[0-9]+") && tlf.length()==9;
    }

    /**
     * Método que nos permite comprobar se os campos pasados están cubertos.
     * @param nodes Os campos que se quere validar que están cubertos.
     * @return True se todos eles teñen algo cuberto. False en caso contrario.
     */
    public static boolean estanCubertosCampos(Node...nodes){
        //Para cada un dos nodos pasados, comprobamos que sexa un campo de entrada de texto e que non esté baleiro.
        //Se non se cumpre ningunha das dúas, devolveremos directamente que non estan cubertos todos os campos.
        for(Node n:nodes){
            if(!(n instanceof TextInputControl) || ((TextInputControl) n).getText().trim().equals("")) return false;
        }
        //Se chegamos a superar o bucle, todos os campos estarán cubertos.
        return true;
    }
}

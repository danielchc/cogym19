package centrodeportivo.gui.controladores;

import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Victor Barreiro
 * Clase na que se implementarán algunhas funcións auxiliares da interface gráfica.
 * Todos os métodos a implementar nesta clase serán abstractos.
 */
public abstract class AuxGUI {
    /**
     * Método que nos permite vaciar os campos de introdución de datos:
     *
     * @param nodes Os diferentes campos de texto:
     */
    public static void vaciarCamposTexto(Node... nodes) {
        //Para cada un dos elementos pasados, validamos que sexa un campo de texto:
        for (Node n : nodes) {
            if (n instanceof TextField) {
                //Cada campo de texto sustituirase por baleiro.
                ((TextField) n).setText("");
            } else if (n instanceof TextArea) {
                //Tamén se se trata dun TextArea:
                ((TextArea) n).setText("");
            }
        }
    }

    /**
     * Método que nos permitirá ocultar os campos que lle pasemos.
     *
     * @param nodes Aqueles campos que haxa que ocultar.
     */
    public static void ocultarCampos(Node... nodes) {
        //Imos percorrendo todos os nodos pasados:
        for (Node n : nodes) {
            //Ocultamos cada un dos nodos:
            n.setVisible(false);
        }
    }

    /**
     * Método que nos permitirá facer visibles os campos que lle pasemos:
     *
     * @param nodes Aqueles campos que haxa que facer visibles.
     */
    public static void amosarCampos(Node... nodes) {
        //Percorremos cada un dos nodos:
        for (Node n : nodes) {
            //Imos facendo visibles cada un dos nodos:
            n.setVisible(true);
        }
    }

    /**
     * Método que nos permitirá poñer certos campos deshabilitados.
     *
     * @param nodes Os campos que se queren deshabilitar.
     */
    public static void inhabilitarCampos(Node... nodes) {
        //Para cada un dos nodos pasados faremos o seguinte:
        for (Node n : nodes) {
            //Poñemolo deshabilitado:
            n.setDisable(true);
        }
    }

    /**
     * Método que nos permitirá poñer os campos habilitados
     *
     * @param nodes Os campos que se queren rehabilitar.
     */
    public static void habilitarCampos(Node... nodes) {
        //Para cada un dos nodos pasados agora facemos o contrario ca no metodo anterior:
        for (Node n : nodes) {
            //Habilitámolo de novo:
            n.setDisable(false);
        }
    }


    /**
     * Método que nos permite convertir un campo de texto a enteiro se é posible, se non, devolve un 0.
     *
     * @param campoTexto O campo de texto a convertir.
     * @return O enteiro derivado (o que corresponda ou cero).
     */
    public static int pasarEnteiro(TextField campoTexto) {
        try {
            return Integer.parseInt(campoTexto.getText());
        } catch (Exception e) {
            return 0;
        }
    }

}

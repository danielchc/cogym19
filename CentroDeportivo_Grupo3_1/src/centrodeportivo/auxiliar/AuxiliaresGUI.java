package centrodeportivo.auxiliar;

import javafx.scene.layout.HBox;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public final class AuxiliaresGUI {

    /**
     * Método para amosar/esconder os campos do formulario.
     *
     * @param mode  true para mostrar as caixas, false para non.
     * @param hboxs Caixas a ser tratadas.
     */
    public static void visibilidadeHBoxs(boolean mode, HBox... hboxs) {
        for (HBox h : hboxs) {
            h.setVisible(mode);
        }
    }

    /**
     * Método para activar/desactivar os campos do formulario.
     *
     * @param mode  true para mostrar as caixas, false para non.
     * @param hboxs Caixas a ser tratadas.
     */
    public static void managedHBoxs(boolean mode, HBox... hboxs) {
        for (HBox h : hboxs) {
            h.setManaged(mode);
        }
    }
}

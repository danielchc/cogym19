package centrodeportivo.auxiliar;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextInputControl;


/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public final class ListenerMaxLogitud implements ChangeListener<String> {

    /**
     * Atributos
     */
    private TextInputControl textField;
    private Integer maxLoxitude;

    /**
     * Constructor do listener
     * @param textField campo de texto asociado
     * @param maxLoxitude número de caractéres máximo
     */
    public ListenerMaxLogitud(TextInputControl textField, Integer maxLoxitude) {
        this.textField = textField;
        this.maxLoxitude = maxLoxitude;
    }


    /**
     * Este método comprobar a lonxitude do texto do campo. Se se pasa, non permite escribir máis.
     * @param observableValue
     * @param valorAnterior Texto anterior a escribir.
     * @param valorNovo Texto posterior.
     */
    @Override
    public void changed(ObservableValue<? extends String> observableValue, String valorAnterior, String valorNovo) {
        if(valorAnterior==null || valorAnterior.equals("")) return;
        if (valorNovo.length() > maxLoxitude) {
            textField.setText(valorAnterior);
        }
    }
}

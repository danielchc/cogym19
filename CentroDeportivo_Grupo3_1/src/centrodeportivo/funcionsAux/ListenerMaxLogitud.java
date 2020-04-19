package centrodeportivo.funcionsAux;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;


/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public final class ListenerMaxLogitud implements ChangeListener<String> {

    private TextInputControl textField;
    private Integer maxLoxitude;

    public ListenerMaxLogitud(TextInputControl textField, Integer maxLoxitude) {
        this.textField = textField;
        this.maxLoxitude = maxLoxitude;
    }


    @Override
    public void changed(ObservableValue<? extends String> observableValue, String valorAnterior, String valorNovo) {
        if (valorNovo.length() > maxLoxitude) {
            textField.setText(valorAnterior);
        }
    }
}

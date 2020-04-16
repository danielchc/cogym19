package centrodeportivo.funcionsAux;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public final class ListenerMaxLogitud implements ChangeListener<String> {

    private TextField textField;
    private Integer maxLoxitude;

    public ListenerMaxLogitud(TextField textField, Integer maxLoxitude) {
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

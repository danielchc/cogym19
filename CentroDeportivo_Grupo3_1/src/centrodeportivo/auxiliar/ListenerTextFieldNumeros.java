package centrodeportivo.auxiliar;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;


/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public final class ListenerTextFieldNumeros implements ChangeListener<String> {

    private TextField textField;

    public ListenerTextFieldNumeros(TextField textField) {
        this.textField = textField;
    }


    @Override
    public void changed(ObservableValue<? extends String> observableValue, String valorAnterior, String valorNovo) {
        if (!valorNovo.matches("\\d{0,5}([\\.]\\d{0,2})?")) {
            textField.setText(valorAnterior);
        }
    }
}

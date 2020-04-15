package centrodeportivo.funcionsAux;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public final class ListenerTextFieldNumeros implements EventHandler<KeyEvent> {

    private TextField textField;

    public ListenerTextFieldNumeros(TextField textField){
        this.textField=textField;
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        if(
                (!textField.getText().isEmpty()&&
                !textField.getText().matches("[0-9]+(\\.)?[0-9]{0,1}")) ||
                keyEvent.getCharacter().toLowerCase().matches("[a-z]")

        ) keyEvent.consume();
    }
}

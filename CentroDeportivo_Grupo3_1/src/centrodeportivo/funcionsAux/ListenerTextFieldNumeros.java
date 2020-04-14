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
        String texto=textField.getText();

        if(texto.length()==0) return;

        if(!texto.matches("[0-9]+(\\.)?[0-9]{0,4}")){
            textField.setText(texto.substring(0,texto.length()-1));
        }
        textField.end();
    }
}

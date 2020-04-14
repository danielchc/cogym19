package centrodeportivo.funcionsAux;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

import java.util.function.Consumer;

public class ListenerEnterPulsado implements EventHandler<KeyEvent> {

    private Consumer metodo;

    public ListenerEnterPulsado(Consumer metodo) {
        this.metodo = metodo;
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        metodo.accept(null);
    }
}

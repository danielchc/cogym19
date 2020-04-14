package centrodeportivo.funcionsAux;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import test.Main;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

public class ListenerEnterPulsado implements EventHandler<KeyEvent> {

    private Callable metodo;

    public ListenerEnterPulsado(Callable metodo) {
        this.metodo = metodo;
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        try {
            metodo.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

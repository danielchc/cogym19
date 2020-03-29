package centrodeportivo.gui.controladores.persoal;

import javafx.animation.TranslateTransition;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public final class Transicion{
    private VBox slider;
    private TranslateTransition transicionAbrir;
    private TranslateTransition transicionCerrar;

    public Transicion(VBox slider) {
        this.slider = slider;
        this.transicionAbrir = new TranslateTransition(Duration.millis(100),slider);
        this.transicionAbrir.setToX(slider.getTranslateX()-slider.getWidth());
        this.transicionCerrar = new TranslateTransition(Duration.millis(100), slider);
    }

    public VBox getSlider() {
        return slider;
    }

    public TranslateTransition getTransicionAbrir() {
        return transicionAbrir;
    }

    public TranslateTransition getTransicionCerrar() {
        return transicionCerrar;
    }
}

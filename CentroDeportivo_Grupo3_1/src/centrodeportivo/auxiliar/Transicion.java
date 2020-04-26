package centrodeportivo.auxiliar;

import javafx.animation.TranslateTransition;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public final class Transicion{

    /**
     * Atributos necesarios para animar unha transición.
     */
    private VBox slider;
    private TranslateTransition transicionAbrir;
    private TranslateTransition transicionCerrar;

    /**
     * Constructor para as transicións.
     * Inicializa as transcións de peche e apertura asociadas a un desplegable (vbox).
     * @param slider Desplegable asociado ás transicións.
     */
    public Transicion(VBox slider) {
        this.slider = slider;
        this.transicionAbrir = new TranslateTransition(Duration.millis(100),slider);
        this.transicionAbrir.setToX(slider.getTranslateX()-slider.getWidth());
        this.transicionCerrar = new TranslateTransition(Duration.millis(100), slider);
    }

    /**
     * Getters e Setters
     */
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

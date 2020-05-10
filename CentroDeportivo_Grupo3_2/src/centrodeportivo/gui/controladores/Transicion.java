package centrodeportivo.gui.controladores;

import javafx.animation.TranslateTransition;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Víctor Barreiro
 * Nesta clase modelaranse as animacións para as transicións:
 */
public final class Transicion {
    /**
     * Atributos que son necesarios. Como non se permite modificalos, facémolos finais:
     */
    private final VBox slider;
    private final TranslateTransition transicionAbrir;
    private final TranslateTransition transicionCerrar;

    /**
     * Constructor dunha transición:
     *
     * @param slider O despregable que estará asociado á clase e polo tanto á transición
     */
    public Transicion(VBox slider) {
        //Asociamos o slider:
        this.slider = slider;
        //Creamos as transicións de apertura e de peche:
        this.transicionAbrir = new TranslateTransition(Duration.millis(100), slider);
        this.transicionAbrir.setToX(slider.getTranslateX() - slider.getWidth());
        this.transicionCerrar = new TranslateTransition(Duration.millis(100), slider);
    }

    /**
     * Getter do despregable:
     *
     * @return o slider asociado á clase.
     */
    public VBox getSlider() {
        return slider;
    }

    /**
     * Getter da transición de apertura.
     *
     * @return a transición que foi creada no constructor.
     */
    public TranslateTransition getTransicionAbrir() {
        return transicionAbrir;
    }

    /**
     * Getter da transición de peche.
     *
     * @return a transición que foi creada no constructor.
     */
    public TranslateTransition getTransicionCerrar() {
        return transicionCerrar;
    }
}

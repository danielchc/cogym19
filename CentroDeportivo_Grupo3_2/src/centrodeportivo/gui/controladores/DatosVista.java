package centrodeportivo.gui.controladores;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Víctor Barreiro
 * Clase na que se acoplará por un lado o controlador da clase, e o fxml. Así teremos asociadas gráficas e controladores
 * de cada pantalla.
 */
public final class DatosVista {
    /**
     * Atributos da clase: controlador e path do fxml:
     */
    private AbstractController controlador;
    private String pathFXML;

    /**
     * Constructor da clase: dous parámetros que son os dous atributos da clase:
     * @param pathFXML O path do fxml da vista
     * @param controlador O controlador correspondente.
     */
    public DatosVista(String pathFXML, AbstractController controlador) {
        //Asociámolos.
        this.controlador = controlador;
        this.pathFXML = pathFXML;
    }

    /**
     * Getter do controlador
     * @return O controlador da ventá contido na clase.
     */
    public AbstractController getControlador() {
        return controlador;
    }

    /**
     * Setter do controlador
     * @param controlador O controlador da ventá a asignar.
     */
    public void setControlador(AbstractController controlador) {
        this.controlador = controlador;
    }

    /**
     * Getter do path do fxml
     * @return O path fxml contido nesta clase.
     */
    public String getPathFXML() {
        return pathFXML;
    }

    /**
     * Setter do path do fxml.
     * @param pathFXML O path fxml contido nesta clase.
     */
    public void setPathFXML(String pathFXML) {
        this.pathFXML = pathFXML;
    }
}

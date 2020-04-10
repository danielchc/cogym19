package centrodeportivo.gui.controladores;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public final class DatosVista {

    /**
     * Atributos básicos para o javafx dunha vista.
     */
    private AbstractController controlador;
    private String pathFXML;

    /**
     * Constructor dos datos dunha vista.
     * @param pathFXML URL do arquivo FXML.
     * @param controlador Controlador asociado ao fxml.
     */
    public DatosVista(String pathFXML, AbstractController controlador) {
        this.controlador = controlador;
        this.pathFXML = pathFXML;
    }

    /**
     * Getters e Setters.
     */
    public AbstractController getControlador() {
        return controlador;
    }

    public void setControlador(AbstractController controlador) {
        this.controlador = controlador;
    }

    public String getPathFXML() {
        return pathFXML;
    }

    public void setPathFXML(String pathFXML) {
        this.pathFXML = pathFXML;
    }
}

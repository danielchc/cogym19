package centrodeportivo.gui.controladores;

public final class DatosVista {
    private AbstractController controlador;
    private String pathFXML;

    public DatosVista(String pathFXML, AbstractController controlador) {
        this.controlador = controlador;
        this.pathFXML = pathFXML;
    }

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

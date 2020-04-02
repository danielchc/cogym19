package centrodeportivo.gui.controladores.socios;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.tarifas.Cuota;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.gui.controladores.AbstractController;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class vCuotaController extends AbstractController implements Initializable {

    public TextField campoSocio;
    public TextField campoTarifa;
    public TextField campoPrezoBase;
    public TextField campoPrezoExtras;
    public TextField campoMaxActividades;
    public MenuButton campoActividades;
    public MenuButton campoCursos;
    public Label campoPrezoTotal;
    public TreeTableView campoPrezos;

    private Usuario usuario;

    public vCuotaController(FachadaAplicacion fachadaAplicacion, Usuario usuario) {
        super(fachadaAplicacion);
        this.usuario=usuario;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Cuota cuota=super.getFachadaAplicacion().consultarCuota(this.usuario.getLogin());
        this.campoSocio.setText(cuota.getUsuario().getNome());
        this.campoTarifa.setText(cuota.getTarifa().getNome());
        this.campoPrezoBase.setText(String.valueOf(cuota.getTarifa().getPrezoBase())+" €");
        this.campoPrezoExtras.setText(String.valueOf(cuota.getTarifa().getPrezoExtras())+" €");
        this.campoMaxActividades.setText(String.valueOf(cuota.getTarifa().getMaxActividades()));

    }
}

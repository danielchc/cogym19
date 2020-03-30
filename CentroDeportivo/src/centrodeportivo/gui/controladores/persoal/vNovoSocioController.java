package centrodeportivo.gui.controladores.persoal;

import centrodeportivo.aplicacion.FachadaAplicacion;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class vNovoSocioController implements Initializable {

    public ComboBox comboTarifa;
    public TextField campoNome;
    public TextField campoLogin;
    public PasswordField campoPassword;
    public TextField campoTelf;
    public TextField campoDNI;
    public TextField campoCorreo;
    public TextField campoIBAN;
    public TextField campoData;
    public TextField campoDificultades;
    public Button btnGadar;
    public Label labelError;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            FachadaAplicacion fa=new FachadaAplicacion();
            this.comboTarifa.getItems().addAll(fa.listarTarifas());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

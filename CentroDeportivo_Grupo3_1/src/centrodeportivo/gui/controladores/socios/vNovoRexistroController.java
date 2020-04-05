package centrodeportivo.gui.controladores.socios;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.RexistroFisioloxico;
import centrodeportivo.aplicacion.obxectos.usuarios.Socio;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.gui.controladores.AbstractController;
import centrodeportivo.gui.controladores.principal.vPrincipalController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class vNovoRexistroController extends AbstractController implements Initializable {


    public Slider sliderPeso;
    public TextField campoPeso;
    public TextField campoAltura;
    public Slider sliderAltura;
    public Slider sliderGrasa;
    public TextField campoGrasa;
    public Slider sliderTensionAlta;
    public TextField campoTensionAlta;
    public Slider sliderTensionBaixa;
    public TextField campoTensionBaixa;
    public Slider sliderPulsacions;
    public TextField campoPulsacions;
    public TextArea campoComentario;
    public CheckBox chkGrasa;
    public CheckBox chkAlta;
    public CheckBox chkBaixa;
    public CheckBox chkPPM;
    public Label labelError;
    public Button btnGardar;
    /**
     * Atributos
     */
    private Usuario usuario;
    private vPrincipalController vPrincipalController;
    private HashMap<CheckBox,Slider> sliders;
    private HashMap<Slider,TextField> campos;

    public vNovoRexistroController(FachadaAplicacion fachadaAplicacion, vPrincipalController vPrincipalController, Usuario usuario) {
        super(fachadaAplicacion);
        this.usuario=usuario;
        this.vPrincipalController=vPrincipalController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inicializarHashMaps();
    }

    private void inicializarHashMaps(){
        this.sliders=new HashMap<>();
        this.campos=new HashMap<>();

        this.sliders.put(chkGrasa,sliderGrasa);         this.campos.put(sliderGrasa,campoGrasa);
        this.sliders.put(chkAlta,sliderTensionAlta);    this.campos.put(sliderTensionAlta,campoTensionAlta);
        this.sliders.put(chkBaixa,sliderTensionBaixa);  this.campos.put(sliderTensionBaixa,campoTensionBaixa);
        this.sliders.put(chkPPM,sliderPulsacions);      this.campos.put(sliderPulsacions,campoPulsacions);

        this.campos.put(sliderPeso,campoPeso);
        this.campos.put(sliderAltura,campoAltura);
    }

    public void listenerCheckBox(MouseEvent mouseEvent) {
        CheckBox src=(CheckBox)mouseEvent.getSource();
        Slider slider=this.sliders.get(src);
        TextField campo=this.campos.get(slider);
        if(src.isSelected()){
            slider.setDisable(false);
            campo.setDisable(false);
        }else{
            slider.setDisable(true);
            campo.setDisable(true);
        }
    }

    public void listenerSlider(MouseEvent mouseEvent) {
        Slider src=(Slider)mouseEvent.getSource();

        if(src.equals(sliderTensionAlta) || src.equals(sliderTensionBaixa) || src.equals(sliderPulsacions)){
            this.campos.get(src).setText(String.valueOf((int)src.getValue()));
        }else{
            String s=String.valueOf(src.getValue());
            int coma=s.indexOf(".");
            this.campos.get(src).setText(s.substring(0,coma+2));
        }
    }

    public void btnGardarAction(ActionEvent actionEvent){

        Float peso, altura, grasa=null;
        Integer tAlta=null, tBaixa=null, ppm=null;

        if(campoPeso.getText().equals("-") || campoAltura.getText().equals("-")){
            this.labelError.setText("Debes introducir polo menos o teu peso e altura.");
            return;
        }
        peso=Float.valueOf(campoPeso.getText());
        altura=Float.valueOf(campoAltura.getText());
        if(chkGrasa.isSelected()){
            if(campoGrasa.getText().equals("-")){
                this.labelError.setText("Valor incorrecto para a % de graxa.");
                return;
            }
            grasa=Float.valueOf(campoGrasa.getText());
        }
        if(chkAlta.isSelected()){
            if(campoTensionAlta.getText().equals("-")){
                this.labelError.setText("Valor incorrecto para a tensión alta.");
                return;
            }
            tAlta=Integer.valueOf(campoTensionAlta.getText());
        }
        if(chkBaixa.isSelected()){
            if(campoTensionBaixa.getText().equals("-")){
                this.labelError.setText("Valor incorrecto para a tensión baixa.");
                return;
            }
            tBaixa=Integer.valueOf(campoTensionBaixa.getText());
        }
        if(chkPPM.isSelected()){
            if(campoPulsacions.getText().equals("-")){
                this.labelError.setText("Valor incorrecto para as pulsacións medias.");
                return;
            }
            ppm=Integer.valueOf(campoPulsacions.getText());
        }
        RexistroFisioloxico rexistro=new RexistroFisioloxico(
                (Socio)this.usuario,
                peso,
                altura,
                grasa,
                tAlta,
                tBaixa,
                ppm,
                this.campoComentario.getText()
        );
        super.getFachadaAplicacion().insertarRexistro(rexistro);
    }
}

package centrodeportivo.gui.controladores.socios;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.actividades.Actividade;
import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.aplicacion.obxectos.tarifas.Cuota;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.gui.controladores.AbstractController;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class vCuotaController extends AbstractController implements Initializable {

    public TextField campoSocio;
    public TextField campoTarifa;
    public TextField campoPrezoBase;
    public TextField campoPrezoExtras;
    public TextField campoMaxActividades;
    public TreeView campoActividades;
    public TreeView campoCursos;
    public Label campoPrezoTotal;
    public TreeView campoPrezos;

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
        this.campoPrezoBase.setText(cuota.getTarifa().getPrezoBase() +" €");
        this.campoPrezoExtras.setText(cuota.getTarifa().getPrezoExtras() +" €");
        this.campoMaxActividades.setText(String.valueOf(cuota.getTarifa().getMaxActividades()));
        this.campoPrezoTotal.setText(cuota.getTotalPrezo()+" €");

        //actividades
        ArrayList<Actividade> a=new ArrayList<>();
        Actividade ac=new Actividade(null,new Area(1,new Instalacion(1)));
        ac.setNome("actividade pocha");
        a.add(ac);
        a.add(ac);
        a.add(ac);
        a.add(ac);
        a.add(ac);
        a.add(ac);
        a.add(ac);
        a.add(ac);

        cuota.setActividadesMes(a);

        /*

            REVISAR A QUERY DE CONSULTAR CUOTA
            AS ACTIVIDADES ESTAAS DEVOLVENDO MAL

         */
        /*
            Esto non me convence
         */

        TreeItem<String> actividadesItem=new TreeItem<>("Actividades...");
        for(Actividade act:cuota.getActividadesMes()){
            actividadesItem.getChildren().add(new TreeItem<>(act.getNome()));
        }
        this.campoActividades.setRoot(actividadesItem);

        //cursos



        //tabla precios
        TreeItem<String> actItem=new TreeItem<String>("Prezos Actividades");
        actItem.getChildren().add(new TreeItem<String>("Total actividades realizadas: "+cuota.getActividadesMes().size()));
        actItem.getChildren().add(new TreeItem<String>("Base actividades: "+cuota.getTarifa().getPrezoBase()));
        actItem.getChildren().add(new TreeItem<String>("Extra actividades: "+cuota.getPrezoActividadesExtra()));
        actItem.getChildren().add(new TreeItem<String>("Total actividades: "+cuota.getTotalActividades()));

        TreeItem<String> curItem=new TreeItem<String>("Prezos Cursos");
        curItem.getChildren().add(new TreeItem<String>("Total cursos realizados: "+cuota.getCursosMes().size()));
        curItem.getChildren().add(new TreeItem<String>("Total cursos: "+cuota.getTotalCursos()));

        TreeItem<String> root=new TreeItem<>("Desglose");
        root.getChildren().add(actItem);
        root.getChildren().add(curItem);
        this.campoPrezos.setRoot(root);
    }
}

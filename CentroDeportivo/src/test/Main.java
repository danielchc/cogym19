package test;

import aplicacion.Socio;
import aplicacion.Tarifa;
import aplicacion.Usuario;
import baseDatos.FachadaBD;

import java.util.Date;

public class Main {
    public static void main(String [] args) {
        try{
            FachadaBD fb=new FachadaBD();
            Tarifa t=new Tarifa(1,"",9,39.0f,39.0f);
            //Socio usu=new Socio("pocho","abc123..","Juan","9999","46523h","correo@correo","aaaaa",new Date(),new Date(),"",t);
            //fb.getDaoUsuarios().insertarUsuario(usu);
            //fb.getDaoTarifas().insertarTarifa(new Tarifa("tarifatest",40,50.0f,2.0f));
            System.out.println(fb.getDaoTarifas().estaEnUsoTarifa(new Tarifa(1,"",0,0,0)));
            System.out.println(fb.getDaoTarifas().estaEnUsoTarifa(new Tarifa(2,"",0,0,0)));
            fb.getDaoTarifas().actualizarTarifa(new Tarifa(1,"Pochísima",10,15.0f,3.5f));
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}

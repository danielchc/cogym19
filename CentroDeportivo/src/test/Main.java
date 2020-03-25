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
            Usuario usu=new Socio("pocho","abc123..","Juan","9999","46523h","correo@correo","aaaaa",new Date(),new Date(),"",t);
            fb.getDaoUsuarios().insertarUsuario(usu);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}

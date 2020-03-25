package test;

import aplicacion.Persoal;
import aplicacion.Socio;
import aplicacion.Tarifa;
import aplicacion.Usuario;
import baseDatos.FachadaBD;

import java.sql.Date;

public class Main {
    public static void main(String [] args) {
        try{
            FachadaBD fb=new FachadaBD();
            Tarifa t=new Tarifa(1,"",9,39.0f,39.0f);
            Socio usu=new Socio("pocha",
                    "abc123..",
                    "Juan",
                    "9999",
                    "46523h",
                    "correo@correo",
                    "aaaaa",
                    Date.valueOf("1990-07-19"),
                    "",
                    t);
            Persoal p=new Persoal("manolinha",
                    "abc123..",
                    "Juan",
                    "9999",
                    "akjldñf",
                    "correo@correo",
                    "aaaaa",
                    "aaaaaaaa"
                );
            fb.getDaoUsuarios().insertarUsuario(p);
            fb.getDaoUsuarios().darBaixaUsuario("manolinha");
            //fb.getDaoUsuarios().actualizarUsuario("pocho",usu);
            /*
            //fb.getDaoTarifas().insertarTarifa(new Tarifa("tarifatest",40,50.0f,2.0f));
            System.out.println(fb.getDaoUsuarios().validarUsuario("pocho","abc123.."));
            System.out.println(fb.getDaoTarifas().estaEnUsoTarifa(new Tarifa(1,"",0,0,0)));
            System.out.println(fb.getDaoTarifas().estaEnUsoTarifa(new Tarifa(2,"",0,0,0)));
            fb.getDaoTarifas().actualizarTarifa(new Tarifa(1,"Pochísima",10,15.0f,3.5f));
            */
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }finally {
        }
    }
}

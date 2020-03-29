package test;

import centrodeportivo.aplicacion.*;
import centrodeportivo.aplicacion.funcionsAux.Criptografia;
import centrodeportivo.aplicacion.funcionsAux.ValidacionDatos;
import centrodeportivo.aplicacion.obxectos.usuarios.Persoal;
import centrodeportivo.aplicacion.obxectos.usuarios.Profesor;
import centrodeportivo.aplicacion.obxectos.usuarios.Socio;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.baseDatos.FachadaBD;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.SQLException;

public class Main {
    public static void main(String [] args) {
        FachadaBD fb=null;
        try{
            fb=new FachadaBD(new FachadaAplicacion());
            //Tarifa t=new Tarifa(1,"",9,39.0f,39.0f);
            /*Socio usu=new Socio("pocha",
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
            Profesor prof=new Profesor("pepe",
                    "abc123..",
                    "Juan",
                    "9999",
                    "asdsa",
                    "correo@correo",
                    "aaaaa",
                    "aaaaaa"
            );*/
            //fb.getDaoUsuarios().insertarUsuario(prof);
            //fb.getDaoMensaxes().enviarMensaxe(new Mensaxe("pocha","manolinha","Que pasa puto mono!!!"));
            //ArrayList<Usuario> a=new ArrayList<>();
            //a.add(p);
            //a.add(prof);

            //fb.getDaoMensaxes().enviarMensaxe(usu,a,"Mensaxe 255.255.255.255 (para todos os usuarios xd)");
            //fb.getDaoUsuarios().actualizarUsuario("pocho",usu);
            /*
            fb.getDaoTarifas().insertarTarifa(new Tarifa("tarifatest",40,50.0f,2.0f));
            System.out.println(fb.getDaoUsuarios().validarUsuario("pocho","abc123.."));
            System.out.println(fb.getDaoTarifas().estaEnUsoTarifa(new Tarifa(1,"",0,0,0)));
            System.out.println(fb.getDaoTarifas().estaEnUsoTarifa(new Tarifa(2,"",0,0,0)));
            fb.getDaoTarifas().actualizarTarifa(new Tarifa(1,"Pochísima",10,15.0f,3.5f));
            */
            /*for(Mensaxe m:fb.getDaoMensaxes().listarMensaxesRecibidos("manolinha")){
                System.out.println(m);
                fb.getDaoMensaxes().marcarMensaxeComoLido(m);
            }
            System.out.println(fb.getDaoTarifas().consultarTarifaSocio("pocha"));*/

            /*System.out.println(NonTocarEsto.hashSHA256("puto mono verde"));
            System.out.println(NonTocarEsto.hashSHA256("puto  verde"));
            System.out.println(NonTocarEsto.hashSHA256("sadASDadadde"));
            System.out.println(NonTocarEsto.hashSHA256("sssadadqd2qwqjndsjfnkdsajfnaskdjfnskdjfnskafnsdkjfnsjdfnksdjnfakjfdnsajfnksfnnksdjfanjdse"));*/
            /*char[] a={'p','a','s','s','w','o','r','d'};
            System.out.println(String.valueOf(a));
            System.out.println(NonTocarEsto.hashSHA256(String.valueOf(a)));
            System.out.println(NonTocarEsto.hashSHA256("password"));*/
            //System.out.println(fb.getDaoUsuarios().listarProfesores().size());
            //System.out.println(Criptografia.hashSHA256("adfasdda"));
            //byte[] k=Criptografia.encriptar(Files.readAllBytes(Paths.get("baseDatos.properties")));
            //Files.write(Paths.get("baseDatos.encrypted"),k);

            //System.out.println(fb.getDaoUsuarios().consultarCuota("pocha"));


            for(Usuario u:fb.listarUsuarios()){
                System.out.println(u.getLogin()+"->"+fb.consultarTipo(u.getLogin()));
            }

        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }finally {
            try {
                fb.getConexion().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

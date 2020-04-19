package centrodeportivo.aplicacion.obxectos.actividades;

import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;

import java.sql.Array;
import java.sql.Timestamp;
import java.util.ArrayList;


/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Victor Barreiro
 *
 * Esta clase modela a información sobre os cursos almacenada na base de datos.
 */
public class Curso {
    /**
     * Atributos da clase:
     */
    private int codCurso;
    private String nome;
    private String descricion;
    private float prezo;
    private boolean aberto;
    private ArrayList<Actividade> actividades;
    private ArrayList<Usuario> participantes;

    /**
     * Estes atrributos non están almacenados como tal na base de datos, pero son cuestións que poderemos recuperar
     * mediante as consultas que fagamos.
     * No MER só metimos algún calculado para non enchelo deles, por exemplo puxemos o de duración, pero o resto son
     * cuestións que tamén fomos sacando a posteriori e que cremos que inundarían o modelo sen ter por que facelo.
     */
    private float duracion;
    private int numActividades;
    private Timestamp dataInicio;

    //Constructores
    public Curso(String nome, String descricion, float prezo){
        this.nome = nome;
        this.descricion = descricion;
        this.prezo = prezo;
        this.actividades = new ArrayList<>();
        this.participantes = new ArrayList<>();
    }

    public Curso(int codCurso, String nome, String descricion, float prezo){
        this(nome, descricion, prezo);
        this.codCurso = codCurso;
    }

    public Curso(int codCurso, String nome, String descricion, float prezo, boolean aberto, float duracion, int numActividades, Timestamp dataInicio){
        this(codCurso, nome, descricion, prezo);
        this.duracion = duracion;
        this.numActividades = numActividades;
        this.dataInicio = dataInicio;
    }

    //Getters e setters:
    public void setCodCurso(int codCurso){
        this.codCurso = codCurso;
    }

    public int getCodCurso(){
        return codCurso;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getNome(){
        return nome;
    }

    public void setDescricion(String descricion){
        this.descricion = descricion;
    }

    public String getDescricion(){
        return descricion;
    }

    public void setDuracion(float duracion){
        this.duracion = duracion;
    }

    public float getDuracion(){
        return duracion;
    }

    public void setPrezo(float prezo){
        this.prezo = prezo;
    }

    public float getPrezo(){
        return prezo;
    }

    public void setActividades(ArrayList<Actividade> actividades){
        this.actividades = actividades;
    }

    public ArrayList<Actividade> getActividades(){
        return this.actividades;
    }

    public int getNumActividades() {
        return numActividades;
    }

    public void setNumActividades(int numActividades) {
        this.numActividades = numActividades;
    }

    public Timestamp getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Timestamp dataInicio) {
        this.dataInicio = dataInicio;
    }

    public ArrayList<Usuario> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(ArrayList<Usuario> participantes) {
        this.participantes = participantes;
    }
}

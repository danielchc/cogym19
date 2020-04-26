package centrodeportivo.aplicacion.obxectos.actividades;

import centrodeportivo.aplicacion.obxectos.usuarios.Persoal;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;

import java.sql.Array;
import java.sql.Date;
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
    private Date dataInicio;
    private Date dataFin;
    private int numProfesores;
    private ArrayList<Persoal> profesores;

    //Constructores
    public Curso(String nome){
        this.nome = nome;
        this.actividades = new ArrayList<>();
        this.participantes = new ArrayList<>();
        this.profesores = new ArrayList<>();
    }

    public Curso(String nome, String descricion, float prezo){
        this(nome);
        this.descricion = descricion;
        this.prezo = prezo;
    }

    public Curso(int codCurso, String nome, String descricion, float prezo){
        this(nome, descricion, prezo);
        this.codCurso = codCurso;
    }

    public Curso(int codCurso, String nome, String descricion, float prezo, boolean aberto, float duracion, int numActividades, Date dataInicio){
        this(codCurso, nome, descricion, prezo);
        this.aberto = aberto;
        this.duracion = duracion;
        this.numActividades = numActividades;
        this.dataInicio = dataInicio;
    }

    public Curso(int codCurso, String nome, String descricion, float prezo, boolean aberto, float duracion, int numActividades, Date dataInicio,
                 Date dataFin, int numProfesores){
        this(codCurso, nome, descricion, prezo, aberto, duracion, numActividades, dataInicio);
        this.dataFin = dataFin;
        this.numProfesores = numProfesores;
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

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public ArrayList<Usuario> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(ArrayList<Usuario> participantes) {
        this.participantes = participantes;
    }

    public boolean isAberto() {
        return aberto;
    }

    public void setAberto(boolean aberto) {
        this.aberto = aberto;
    }

    public Date getDataFin() {
        return dataFin;
    }

    public void setDataFin(Date dataFin) {
        this.dataFin = dataFin;
    }

    public int getNumProfesores() {
        return numProfesores;
    }

    public void setNumProfesores(int numProfesores) {
        this.numProfesores = numProfesores;
    }

    public ArrayList<Persoal> getProfesores() {
        return profesores;
    }

    public void setProfesores(ArrayList<Persoal> profesores) {
        this.profesores = profesores;
    }
}

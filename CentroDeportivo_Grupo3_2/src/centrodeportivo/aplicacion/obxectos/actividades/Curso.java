package centrodeportivo.aplicacion.obxectos.actividades;

import centrodeportivo.aplicacion.obxectos.usuarios.Persoal;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;

import java.sql.Date;
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
    private Integer codCurso; //Código do curso
    private String nome; //Nome do curso
    private String descricion; //Descrición do curso
    private Float prezo; //Prezo do curso
    private boolean aberto; //Indicador de se o curso foi aberto ao público ou non.
    private ArrayList<Actividade> actividades; //Actividades do curso.
    private ArrayList<Usuario> participantes; //Participantes do curso.

    /**
     * Estes atrributos non están almacenados como tal na base de datos, pero son cuestións que poderemos recuperar
     * mediante as consultas que fagamos.
     * No MER só metimos algún calculado para non enchelo deles, por exemplo puxemos o de duración, pero o resto son
     * cuestións que tamén fomos sacando a posteriori e que cremos que inundarían o modelo sen ter por que facelo.
     */
    private Float duracion; //Duración total do curso.
    private Integer numActividades; //Número de actividades do curso.
    private Date dataInicio; //Data de comezo do curso.
    private Date dataFin; //Data de finalización do curso.
    private Integer numProfesores; //Número de profesores que ten o curso.
    private ArrayList<Persoal> profesores; //Profesores do curso.
    private Float valMedia; //Valoración media total do curso.

    //Constructores
    public Curso(String nome){
        this.nome = nome;
        this.actividades = new ArrayList<>();
        this.participantes = new ArrayList<>();
        this.profesores = new ArrayList<>();
    }

    /**
     * Constructor que empregaremos no caso de querer listar os cursos: recollemos datos xerais de cada un
     * @param codCurso O código do curso
     * @param nome O nome que ten o curso
     * @param aberto Indicador de se o curso está aberto ou non
     * @param duracion Duración do curso
     * @param numActividades Número de actividades que ten o curso.
     * @param dataInicio Data de comezo do curso
     * @param dataFin Data de fin do curso
     */
    public Curso(Integer codCurso, String nome, boolean aberto, Float duracion, Integer numActividades, Date dataInicio, Date dataFin){
        this(nome);
        this.codCurso = codCurso;
        this.aberto = aberto;
        this.duracion = duracion;
        this.numActividades = numActividades;
        this.dataInicio = dataInicio;
        this.dataFin = dataFin;
    }

    public Curso(String nome, String descricion, Float prezo){
        this(nome);
        this.descricion = descricion;
        this.prezo = prezo;
    }

    public Curso(Integer codCurso, String nome, String descricion, Float prezo){
        this(nome, descricion, prezo);
        this.codCurso = codCurso;
    }

    public Curso(Integer codCurso, String nome, String descricion, Float prezo, boolean aberto, Float duracion, Integer numActividades, Date dataInicio){
        this(codCurso, nome, descricion, prezo);
        this.aberto = aberto;
        this.duracion = duracion;
        this.numActividades = numActividades;
        this.dataInicio = dataInicio;
    }

    public Curso(Integer codCurso, String nome, String descricion, Float prezo, boolean aberto, Float duracion, Integer numActividades, Date dataInicio,
                 Date dataFin, Integer numProfesores, Float valMedia){
        this(codCurso, nome, descricion, prezo, aberto, duracion, numActividades, dataInicio);
        this.dataFin = dataFin;
        this.numProfesores = numProfesores;
        this.valMedia = valMedia;
    }

    //Getters e setters:
    public void setCodCurso(Integer codCurso){
        this.codCurso = codCurso;
    }

    public Integer getCodCurso(){
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

    public void setDuracion(Float duracion){
        this.duracion = duracion;
    }

    public Float getDuracion(){
        return duracion;
    }

    public void setPrezo(Float prezo){
        this.prezo = prezo;
    }

    public Float getPrezo(){
        return prezo;
    }

    public void setActividades(ArrayList<Actividade> actividades){
        this.actividades = actividades;
    }

    public ArrayList<Actividade> getActividades(){
        return this.actividades;
    }

    public Integer getNumActividades() {
        return numActividades;
    }

    public void setNumActividades(Integer numActividades) {
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

    public Integer getNumProfesores() {
        return numProfesores;
    }

    public void setNumProfesores(Integer numProfesores) {
        this.numProfesores = numProfesores;
    }

    public ArrayList<Persoal> getProfesores() {
        return profesores;
    }

    public void setProfesores(ArrayList<Persoal> profesores) {
        this.profesores = profesores;
    }

    public Float getValMedia() {
        return valMedia;
    }

    public void setValMedia(Float valMedia) {
        this.valMedia = valMedia;
    }
}

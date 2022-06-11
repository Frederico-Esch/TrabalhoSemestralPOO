package Models;

import javax.swing.text.html.HTML;
import java.awt.image.AreaAveragingScaleFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public abstract class Midia implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nome;
    private MidiaStates state;
    private String review;
    private ArrayList<String> lugaresDisponiveis;
    private HashSet<Tags> tags;
    private MidiaType tipo;

    public Midia(String nome, MidiaType tipo, ArrayList<String> lugaresDisponiveis){
        setNome(nome);
        setLugaresDisponiveis(lugaresDisponiveis);
        setTipo(tipo);
        setState(MidiaStates.TO_READ);
        tags = new HashSet<>();
    }
    public Midia(String nome, MidiaType tipo, ArrayList<String> lugaresDisponiveis, HashSet<Tags> tags){
        setNome(nome);
        setLugaresDisponiveis(lugaresDisponiveis);
        setTags(tags);
        setTipo(tipo);
        setState(MidiaStates.TO_READ);
    }

    public abstract String getStateToString();
    public abstract String getStateToString(MidiaStates state);

    public MidiaStates getState(){
        return state;
    };
    public void setState(MidiaStates state){
        this.state = state;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getReview() {
        if(getState() == MidiaStates.FINISHED) return review;
        else return "Não há review para midias não terminadas";
    }
    public void setReview(String review) {
        this.review = review;
    }

    public ArrayList<String> getLugaresDisponiveis() {
        return lugaresDisponiveis;
    }
    public void setLugaresDisponiveis(ArrayList<String> lugaresDisponiveis) {
        this.lugaresDisponiveis = lugaresDisponiveis;
    }
    public void addLugarDisponivel(String lugarDisponivel){
        lugaresDisponiveis.add(lugarDisponivel);
    }

    public HashSet<Tags> getTags() {
        return tags;
    }
    public void setTags(HashSet<Tags> tags) {
        this.tags = tags;
    }
    public void addTag(Tags tag){
        this.tags.add(tag);
    }
    public void removeTag(Tags tag){
        this.tags.remove(tag);
    }

    public MidiaType getTipo() {
        return tipo;
    }

    public void setTipo(MidiaType tipo) {
        this.tipo = tipo;
    }
}

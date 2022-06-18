package Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Quadrinho extends Midia{
    private HashMap<String, String> links;

    public Quadrinho(String nome, ArrayList<String> lugaresDisponíveis) {
        super(nome, MidiaType.Quadrinho, lugaresDisponíveis);
        links = new HashMap<>();
    }
    public Quadrinho(String nome, ArrayList<String> lugaresDisponíveis, HashSet<Tags> tags) {
        super(nome, MidiaType.Quadrinho, lugaresDisponíveis, tags);
        links = new HashMap<>();
    }


    public void setLink(String lugarDisponivel, String link) throws Exception {
        if(getLugaresDisponiveis().contains(lugarDisponivel)) {
            links.put(lugarDisponivel, link);
        }else{
            throw new Exception("Link referente a uma fonte não existente");
        }
    }

    public String getLink(String lugarDisponivel) {
        var result = links.get(lugarDisponivel);
        if(result == null) result = "Sem link";
        return result;
    }


    @Override
    public String getStateToString() {
        String result = switch (getState()){
            case TO_READ -> "Não lido";
            case PROGRESS -> "Lendo";
            case FINISHED -> "Lido";
        };
        return result;
    }

    @Override
    public String getStateToString(MidiaStates state) {
        String result = switch (state){
            case TO_READ -> "Não lido";
            case PROGRESS -> "Lendo";
            case FINISHED -> "Lido";
        };
        return result;
    }
}

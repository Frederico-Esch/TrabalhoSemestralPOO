package Models;

public enum MidiaType {
    Quadrinho("Quadrinho"),
    Livro("Livro"),
    Serie("Série"),
    Filme("Filme");

    String value;
    MidiaType(String value){
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}

package Models;

import java.util.ArrayList;
import java.util.HashMap;

public enum Tags {
    DC("DC", 0),
    Marvel("Marvel", 1),
    RadiantBlack("Radiant Black", 2),
    Nova("Guerra Civil", 3),
    Rebirth("Rebirth", 4),
    New52("New 52", 5),

    Terror("Terror", 6),
    Comedia("Comédia", 7),
    Acao("Ação", 8),
    Romance("Romance", 9),
    SciFi("Ficção Científica", 10),
    Fantasia("Fantasia", 11),
    Suspence("Suspence", 12),
    Serios("Sérios", 13);

    int value;
    String key;
    Tags(String key, int value){
        this.value = value;
        this.key = key;
    }

    public int getValue() {
        return value;
    }
    @Override
    public String toString() {
        return key;
    }

}

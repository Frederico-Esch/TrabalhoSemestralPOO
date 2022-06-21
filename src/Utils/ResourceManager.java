package Utils;

import Models.Midia;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


public class ResourceManager {
    static final String filename = "resources";

    public static void save(ArrayList<Midia> data) throws Exception{
        try(ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(filename)))){
            oos.writeObject(data);
        }
    }

    public static ArrayList<Midia> load() throws Exception{
        try(ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(filename)))){
            return (ArrayList<Midia>) ois.readObject();
        }
    }

    public static String getFilename() {
        return filename;
    }
}

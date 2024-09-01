import java.io.*;
import java.util.List;

public class ResultManager {
    private final String filePath;

    public ResultManager(String filePath) {
        this.filePath = filePath;
    }

    public void serializeResults(List<Document> documents) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {

            oos.writeObject(documents);
            System.out.println("we are here");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Document> deserializeResults() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (List<Document>) ois.readObject();
        }
    }
}

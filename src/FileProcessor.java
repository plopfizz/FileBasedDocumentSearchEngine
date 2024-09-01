import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Stream;

public class FileProcessor implements Callable<Document> {
    private String filePath;

    public FileProcessor(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Document call() throws Exception {
        List<String> lines = Files.readAllLines(Path.of(filePath));
        int wordCount = lines.stream()
                .flatMap(line -> Stream.of(line.split("\\s+")))
                .filter(word -> !word.isEmpty())
                .toList()
                .size();
        LocalDateTime lastModified = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(Files.getLastModifiedTime(Path.of(filePath)).toMillis()),
                ZoneId.systemDefault());
        Path fileName = Paths.get(filePath);
        return new Document(
                fileName.toString(),
                lastModified,
                wordCount,
                filePath
        );
    }
}

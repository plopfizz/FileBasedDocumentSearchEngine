import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class SearchEngine {
    private ExecutorService executorService;

    public SearchEngine(int threadPoolSize) {
        this.executorService = Executors.newFixedThreadPool(threadPoolSize);
    }

    public List<Document> indexDocuments(List<Path> paths) throws InterruptedException, ExecutionException {
        List<Callable<Document>> tasks = paths.stream()
                .map(path -> new FileProcessor(path.toString()))
                .collect(Collectors.toList());

        List<Future<Document>> futures = this.executorService.invokeAll(tasks);
        return futures.stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<Document> search(List<Document> documents, String keywordToSearch) throws IOException {
        return documents.parallelStream()
                .filter(doc -> doc.getFilePath() != null)
                .filter(doc -> {
                    try {
                        String content = Files.readString(Paths.get(doc.getFilePath()));
                        return content.contains(keywordToSearch);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return false;
                    }
                })
                .collect(Collectors.toList());
    }

    public void shutdown() {
        executorService.shutdown();
    }
}

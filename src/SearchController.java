import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class SearchController {
    private final SearchEngine searchEngine;
    private final ResultManager resultManager;

    public SearchController(int threadPoolSize, String serializationPath) {
        this.searchEngine = new SearchEngine(threadPoolSize);
        this.resultManager = new ResultManager(serializationPath);
    }

    public void execute() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the directory to index:");
        String directoryPath = sc.nextLine();
        try {
            Path dir = Paths.get(directoryPath);
            /*

Files.walk(dir) will traverse the directory tree recursively, visiting all subdirectories.
Files.list(dir) only lists the files and directories directly within the specified directory, without recursion.
             */
            Stream<Path> pathStream = Files.walk(dir);
            List<Path> paths = pathStream.filter(path -> {
                        try {
                            return Files.isRegularFile(path);
                        } catch (Exception e) {
                            System.err.println("Skipping file due to exception: " + path + " - " + e);
                            return false;
                        }
                    })
                    .filter((path) -> path.toString().endsWith(".txt")).toList();
            System.out.println(paths + " this is the directory");
            System.out.println("Indexing documents...");
            List<Document> documents = searchEngine.indexDocuments(paths);
            System.out.println("Indexed " + documents.size() + " documents.");

            // Serialize the indexed documents
            resultManager.serializeResults(documents);
            System.out.println("Serialized indexed documents.");

//             Load the documents (for demonstration)
            List<Document> loadedDocuments = resultManager.deserializeResults();
            System.out.println("Deserialized " + loadedDocuments.size() + " documents.");

            // Search
            System.out.println("Enter keyword to search:");
            String keyword = sc.nextLine();


            List<Document> results = searchEngine.search(loadedDocuments, keyword);
            System.out.println("Found " + results.size() + " matching documents:");
            results.forEach(System.out::println);


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Caught an exception" + e.getMessage());
        }

    }

    public static void main(String[] args) {

        SearchController sc = new SearchController(4, "searchResults.ser");
        sc.execute();

    }
}

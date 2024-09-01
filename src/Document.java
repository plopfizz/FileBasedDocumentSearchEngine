import java.io.Serializable;
import java.time.LocalDateTime;

public class Document implements Serializable {
    private String fileName;
    private LocalDateTime lastModifiedTime;
    private int wordCount;
    private String filePath;


    public Document(String fileName, LocalDateTime lastModifiedTime, int wordCount, String filePath) {
        this.fileName = fileName;
        this.lastModifiedTime = lastModifiedTime;
        this.wordCount = wordCount;
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public LocalDateTime getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(LocalDateTime lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "Document{" +
                "fileName='" + fileName + '\'' +
                ", lastModifiedTime=" + lastModifiedTime +
                ", wordCount=" + wordCount +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}

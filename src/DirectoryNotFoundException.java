package WebCrawler.tema1_ip.src;

public class DirectoryNotFoundException extends Exception {
    private String message;

    public DirectoryNotFoundException(String message) {
        this.message = message;
    }

    public String getMessage()
    {
        return this.message;
    }
}

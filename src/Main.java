package WebCrawler.tema1_ip.src;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws DirectoryNotFoundException, IOException {
        //Crawler.run(args);
        Task task1=new FilterBySizeTask("exemple/",173402);
        task1.execute();

    }

}

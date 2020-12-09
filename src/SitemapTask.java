import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class SitemapTask implements Task {
    public String path;

    public SitemapTask(String path) {
        this.path = path;
    }

    public void generateSitemap(File dirPath) {

    }


    @Override
    public void execute() throws DirectoryNotFoundException{

    }
}

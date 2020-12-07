import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Main {
<<<<<<< HEAD
    private static Crawler Crawler;

    public static void main(String[] args) throws DirectoryNotFoundException {
        Crawler.run(args);
        URL myUrl = null;
        try {
            myUrl = new URL("http://cezarplesca.blogspot.com/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        DownloadTask dw = new DownloadTask(2, myUrl);
=======
    public static void main(String[] args)  {
        String myUrl="https://www.emag.ro/";
        DownloadTask dw=new DownloadTask(4,myUrl,".pdf#.png",1);
>>>>>>> fc6d453 (Descărcare recursivă finalizată!NETESTATĂ)
        dw.execute();
        dw.listURLQ();

    }
}

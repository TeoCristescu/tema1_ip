import java.net.MalformedURLException;
import java.net.URL;

public class Main {
    public static void main(String[] args) throws MalformedURLException {
        URL myUrl=new URL("http://cezarplesca.blogspot.com/");
        DownloadTask dw=new DownloadTask(2,myUrl);
        dw.execute();
        dw.listURLQ();

    }

}

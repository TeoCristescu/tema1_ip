import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Main {
    public static void main(String[] args)  {
        String myUrl="https://www.emag.ro/";
        DownloadTask dw=new DownloadTask(4,myUrl,".pdf#.png",1);
        dw.execute();
        dw.listURLQ();

    }

}

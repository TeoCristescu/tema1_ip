import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Main {


    public static void main(String[] args)  {

        Crawler.run(args);
        String myUrl="http://www.dhs.state.il.us/OneNetLibrary/27897/documents/Initiatives/IITAA/Sample-Document.docx";
        DownloadTask dw=new DownloadTask(4,myUrl,".pdf#.png#.docx#.jpg",1);
        dw.execute();
        dw.listURLQ();

    }

}

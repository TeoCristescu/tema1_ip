import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.InputStreamReader;
import java.net.URL;

import static java.lang.Math.round;


public class DownloadTask implements Task {
    private int numThreads;

    URL toDownload;
    URLQueue downloadPending;
    @Override
    public void execute()   {

        downloadPending.addToList(toDownload);
        int indexQ=0;
        int depthLevel=2;
        try {
            int auxIndexQ=indexQ;
            for(int i=0;i<depthLevel;i++)
            {
                    int qsize=this.downloadPending.getSize();
                for(int j=indexQ;j<qsize;j=j+1)
                {
                    System.out.println("depth "+i+" j "+j+" qsize "+qsize);
                    URL toDownAux = this.downloadPending.getLinksList(j);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(toDownAux.openStream()));
                    String urlRegex = "(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|].com/";
                    Pattern urlPattern = Pattern.compile(urlRegex);
                    String line;
                    Matcher regexMatcher;


                    while ((line = reader.readLine()) != null)
                    {
                        Pattern r = Pattern.compile(urlRegex);
                        Matcher m = r.matcher(line);
                        if (m.find())//while sau if :-?
                        {
                            if (m.group().length() != 0) {
                                // System.out.println("Linia " + m.group(0));
                                URL aux = new URL(m.group());

                                if (this.downloadPending.exists_elem(aux) == 0)
                                {
                                    this.downloadPending.addToList(aux);
                                    auxIndexQ++;
                                }
                            }
                        }
                        //System.out.println(line);
                    }
                }



            }
            int finalQSize=this.downloadPending.getSize();
            for(int i=0;i<numThreads;i++)
            {
                int start=(i)*round(((float)finalQSize/numThreads));
                int end=(i+1)*round(((float)finalQSize/numThreads));
                if(end>finalQSize)
                {
                    end=finalQSize;
                }
                CrawlerThread aux=new CrawlerThread();
                aux.downloadTask(this.downloadPending,start,end);

            }







        } catch ( Exception e1) { System.out.println(e1.toString());}




    }



    public DownloadTask(int numThreads,URL _URL) {
        this.numThreads = numThreads;
        this.toDownload=_URL;
        this.downloadPending=new URLQueue();
    }
    public void listURLQ()
    {
        this.downloadPending.listQ();
        System.out.println("Q size este "+this.downloadPending.getSize());
    }
}

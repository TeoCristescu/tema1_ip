import java.io.BufferedReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.InputStreamReader;
import java.net.URL;

import static java.lang.Math.ceil;
import static java.lang.Math.round;


public class DownloadTask implements Task {
    private int numThreads;
    private     String extensions;
    private  int depthLevel;
    String domain;
    URLQueue downloadPending;
    @Override
    public void execute()  {
        URL toDownload=null;
        try {
             toDownload=new URL(domain);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return;
        }
        String line = null;
        downloadPending.addToList(toDownload);
        int indexQ=0;


            int auxIndexQ=indexQ;
            for(int i=0;i<depthLevel;i++)
            {
                int qsize=this.downloadPending.getSize();
                for(int j=indexQ;j<qsize;j=j+1)
                {
                    //System.out.println("depth "+i+" j "+j+" qsize "+qsize);
                    URL toDownAux = this.downloadPending.getLinksList(j);
                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(toDownAux.openStream()));
                        //String urlRegex = "(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
                        String urlRegex=this.domain;
                        urlRegex+="[a-zA-Z0-9+&@#/%?=~_|!:,.;\\-]*";
                       //System.out.println(urlRegex);
                        Pattern urlPattern = Pattern.compile(urlRegex);
                        line="";
                        Matcher regexMatcher;


                        while ((line = reader.readLine()) != null) {
                            Pattern r = Pattern.compile(urlRegex);
                            Matcher m = r.matcher(line);
                            if (m.find())
                            {
                                if (m.group(0).length() != 0)
                                {
                                    URL aux = new URL(m.group(0));
                                     if(this.downloadPending.exists_elem(aux)==0)
                                     {
                                        this.downloadPending.addToList(aux);
                                        auxIndexQ++;
                                     }
                                }
                            }
                            //System.out.println(line);
                        }
                    }
                    catch ( Exception e1)
                    {
                        System.out.println("Pagina nu există:");
                        System.out.println(e1.toString());


                    }
                }



            }
            int finalQSize=this.downloadPending.getSize();
            ArrayList<CrawlerThread>myThreads=new ArrayList<CrawlerThread>();
            for(int i=0;i<numThreads;i++)
            {

                //int start= (int) ((i)*ceil(finalQSize/numThreads));
                //int end= (int) ((i+1)*ceil(finalQSize/numThreads));
                //System.out.println("start "+start+" end "+end);
                CrawlerThread aux=new CrawlerThread();
                aux.set_CrawlerThread(this.downloadPending,i,numThreads);
                myThreads.add(aux);
                aux.start();


            }

            for(int i=0;i<numThreads;i++)
            {
                try {
                    myThreads.get(i).join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


    }



    public DownloadTask(int numThreads,String _domain,String _extensions,int _depthLevel) {
        this.numThreads = numThreads;
        this.domain=_domain;
        this.downloadPending=new URLQueue();
        this.extensions=_extensions;
        this.depthLevel=_depthLevel;
    }
    public void listURLQ()
    {
        this.downloadPending.listQ();
        System.out.println("Q size este "+this.downloadPending.getSize());
    }
}

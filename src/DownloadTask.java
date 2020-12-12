import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
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
    private String extensions;
    private int depthLevel;
    private ArrayList<String>disalowList;
    String domain;
    URLQueue downloadPending;
    public void getDisalowList(URL toDownloadURL)
    {
        this.disalowList=new ArrayList<String>();
        URL robotsURL=null;
        try
        {
            robotsURL = new URL(toDownloadURL.getProtocol() + "://" + toDownloadURL.getHost()+"/robots.txt");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(robotsURL.openStream()));
            String line;
            int start=0;
            while ((line = reader.readLine()) != null) {



                if(line.equals("User-agent: *"))
                {   start=1;

                }
                if(start==1)
                {
                    if(line.contains("Disallow:"))
                    {
                        String[] auxLines=line.split(" ");
                         
                        this.disalowList.add(auxLines[1]);

                    }
                    if(line.length()==0)
                    {
                        break;
                    }
                }

            }

        }
        catch ( Exception e1)
        {
            System.out.println("Pagina robots nu exista:");
            System.out.println(e1.toString());
        }



    }

    public boolean  isAllowed(URL toCheck)
    {
        int size=this.disalowList.size();

        for(int i=0;i<size;i++)
        {
            String pattern = this.disalowList.get(i);
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(toCheck.toString());
            if (m.find( ))
            {
            return false;
            }
        }
        return true;
    }
    @Override


    public void execute()  {
        URL toDownload=null;
        try {
             toDownload=new URL(domain);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return;
        }
        //String line = null;
        downloadPending.addToList(toDownload);
        String urlRegex= toDownload.getProtocol() + "://" + toDownload.getHost();

        System.out.println("domeniu"+this.domain);

        urlRegex+="[a-zA-Z0-9+&@#/%?=~_|!:,.;\\-]*";

        int indexQ=0;

        this.getDisalowList(toDownload);
            int auxIndexQ=indexQ;
            for(int i=0;i<depthLevel;i++)
            {
                int qsize=this.downloadPending.getSize();


                for(int j=indexQ;j<qsize;j=j+1)
                {
                    //System.out.println("depth "+i+" j "+j+" qsize "+qsize);
                    URL toDownAux = this.downloadPending.getLinksList(j);
                    String line;
                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(toDownAux.openStream()));
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
                                        if(isAllowed(aux)==true) {
                                            this.downloadPending.addToList(aux);
                                            auxIndexQ++;
                                        }
                                    }
                                }
                            }

                        }
                    }
                    catch ( Exception e1)
                    {
                        System.out.println("Pagina nu exista:");
                        System.out.println(e1.toString());


                    }
                }



            }
            int finalQSize=this.downloadPending.getSize();
            ArrayList<CrawlerThread>myThreads=new ArrayList<CrawlerThread>();
            for(int i=0;i<numThreads;i++)
            {

                CrawlerThread aux=new CrawlerThread();
                aux.set_CrawlerThread(this.downloadPending,i,numThreads);
                aux.set_extensions(this.extensions);
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

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import static java.lang.Math.round;
public   class CrawlerThread extends Thread {


    private     URLQueue mydownloadPending;
    private     int startIndex;
    private     int endIndex;
    private     int threadID;
    private     int numThreads;

    @Override
    public void run(){
        downloadTask(mydownloadPending,startIndex,endIndex);
    }


    public void downloadTask(URLQueue downloadPending,int start,int end)  {
        BufferedReader reader;
        for(int i=start;i<end;i++)
        {
            URL url = downloadPending.getLinksList(i);
            try {
                 reader = new BufferedReader(new InputStreamReader(url.openStream()));
                BufferedWriter writer = new BufferedWriter(new FileWriter("page"+i+".html"));
                String line;
                while ((line = reader.readLine()) != null)
                {

                    writer.write(line);
                    if(i==0)
                    {
                    System.out.println(line);
                    }
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
            String[] arrOfStr = url.toString().split("\\?", 2);
            String withoutAsk=arrOfStr[0];
            String[] calea=withoutAsk.split("//",0);
            System.out.println("calea "+calea[1]);
            String names[]=calea[1].split("/",0);
            int nrNames=names.length;
            String calea2=calea[1].replaceAll("/","\\\\");



            System.out.println("nume " +calea2);
            int lenCalea2=calea2.length();
            if(calea2.charAt(lenCalea2-1)=='\\')
            {
             //System.out.println("calea2 "+calea2+" are caracterul \\");
            }
            File f = new File(calea2+"index"+i+".html");
            f.getParentFile().mkdirs();
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }


            try {
                reader = new BufferedReader(new InputStreamReader(url.openStream()));
                BufferedWriter writer;

                writer = new BufferedWriter(new FileWriter(calea2+"index"+i+".html"));



                // System.out.println("pagina "+i+" este "+url.toString());
                String line;
                while ((line = reader.readLine()) != null)
                {
                    writer.write(line);
                }
            }
            catch ( Exception e1)
            {
                System.out.println("Pagina nu există:");
                System.out.println(e1.toString());


            }


        }


    }


    public void set_CrawlerThread(URLQueue _downloadPending,int _threadId,int _nrThreads)
    {
        mydownloadPending=_downloadPending;
        this.threadID=_threadId;
        numThreads=_nrThreads;
        int finalQSize=_downloadPending.getSize();
        startIndex=round(threadID*(finalQSize/numThreads));
        endIndex=round((threadID+1)*(finalQSize/numThreads));
        if(endIndex>finalQSize)
        {
            endIndex=finalQSize;
        }
        if(threadID==numThreads-1)
        {
            endIndex=finalQSize;
        }
    }
}




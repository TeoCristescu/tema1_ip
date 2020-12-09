import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import static java.lang.Math.round;

public   class CrawlerThread extends Thread {


    private     URLQueue mydownloadPending;
    private     int mystart;
    private     int myend;
    private     int threadID;
    private     int numThreads;
    @Override
    public void run(){
        downloadTask(mydownloadPending,mystart,myend);
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
        }


    }

    public void set_CrawlerThread(URLQueue _downloadPending,int _threadId,int _nrThreads)
    {
        mydownloadPending=_downloadPending;
        this.threadID=_threadId;
        numThreads=_nrThreads;
        int finalQSize=_downloadPending.getSize();
        mystart=round(threadID*(finalQSize/numThreads));
        myend=round((threadID+1)*(finalQSize/numThreads));
        if(myend>finalQSize)
        {
            myend=finalQSize;
        }
        if(threadID==numThreads-1)
        {
            myend=finalQSize;
        }
    }
}


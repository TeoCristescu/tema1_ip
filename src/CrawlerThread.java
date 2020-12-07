import java.io.*;
import java.net.URL;
import java.util.ArrayList;

public   class CrawlerThread {
    void downloadTask(URLQueue downloadPending,int start,int end)
    {
        for(int i=start;i<end;i++)
        {
            URL url = downloadPending.getLinksList(i);
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
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
}


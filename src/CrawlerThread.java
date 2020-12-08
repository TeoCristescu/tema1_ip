import java.io.*;
import java.net.URL;
import java.util.ArrayList;

public   class CrawlerThread extends Thread {


    private     URLQueue mydownloadPending;
    private     int mystart;
    private     int myend;

    @Override
    public void run(){
        downloadTask(mydownloadPending,mystart,myend);
    }


    public void downloadTask(URLQueue downloadPending,int start,int end)  {
        BufferedReader reader;
        for(int i=start;i<end;i++)
        {
            URL url = downloadPending.getLinksList(i);
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

    public void set_CrawlerThread(URLQueue _downloadPending,int _start,int _end)
    {
        mydownloadPending=_downloadPending;mystart=_start; myend=_end;
    }
}

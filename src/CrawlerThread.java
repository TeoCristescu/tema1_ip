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
    private     String extensions;
    @Override
    public void run(){
        downloadTask(mydownloadPending,startIndex,endIndex);
    }


    public void downloadTask(URLQueue downloadPending,int start,int end)  {
        //BufferedReader reader;
        for(int i=start;i<end;i++)
        {
            URL url = downloadPending.getLinksList(i);
            String[] arrOfStr = url.toString().split("\\?", 2); //cut off GET parameters
            String withoutAsk=arrOfStr[0];
            String[] calea=withoutAsk.split("//",0);//cut off http or https
            //System.out.println("calea "+calea[1]);
            //String names[]=calea[1].split("/",0);//
            //int nrNames=names.length;
            String calea2=calea[1].replaceAll("/","\\\\");
            int lenCalea2=calea2.length();



            try {

                //reader = new BufferedReader(new InputStreamReader(url.openStream()));
               // BufferedWriter writer = null;
                InputStream in = url.openStream();
                int length = -1;
                byte[] buffer = new byte[4096];
                String theExtension=".html";
                String[]extensionsString=this.extensions.split("#",0);

                    for (int k = 0; k < extensionsString.length; k++)
                    {
                        int sizeExtensie = extensionsString[k].length();
                        String aux_url = calea2.substring(calea2.length() - sizeExtensie);
                        System.out.println("extensionsString[k] "+extensionsString[k]+" aux_url "+aux_url);
                        if ((extensionsString[k].equals(aux_url)))
                        {
                            theExtension = extensionsString[k];

                            break;
                        }
                    }
                System.out.println("extension "+theExtension);
                System.out.println("calea "+calea2);
                //writer = new BufferedWriter(new FileWriter(calea2+"index"+i+theExtension));
                File f = new File(calea2+"index"+i+theExtension);
                f.getParentFile().mkdirs();
                try {
                    f.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                FileOutputStream fos = new FileOutputStream(f);


                // System.out.println("pagina "+i+" este "+url.toString());
                String line;
                while ((length = in.read(buffer)) > -1)//(line=reader.readLine())!=null
                {
                    fos.write(buffer,0,length);
                }
                fos.close();
                in.close();
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
    public void set_extensions(String _extensions)
    {
        this.extensions=_extensions;
    }
}




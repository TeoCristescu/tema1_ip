import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import static java.lang.Math.round;

/**
 *Aceasta clasa reprezinta ajuta la asignarea unor funcții threadurilor.
 *
 * @author Vasi Oniga partea de  download
 */
public   class CrawlerThread extends Thread {

    /**
     * Descrierea membrilor
     * mydownloadPending reprezinta lista cu URL-uri corespunzatoare paginilor care urmeaza sa fie descarcate
     * startIndex și endIndex indica threadului care URL-uri din mydownloadPending ii revin lui ca sarcini
     * threadID este ID-ul threadului
     * extensions este un Sting care conține tipurile de fișiere care trebuie descarcate
     */

    private     URLQueue mydownloadPending;
    private     int startIndex;
    private     int endIndex;
    private     int threadID;
    private     int numThreads;
    private     String extensions;
    private Semaphore mutex = new Semaphore(1);
    @Override
    public void run(){
        downloadTask(mydownloadPending,startIndex,endIndex);
    }
    public File createFile(String calea2,int index,String theExtension)
    {



        System.out.println("calea "+calea2+"index"+index+theExtension);

        File f = new File(calea2 + "index" + index + theExtension);
        f.getParentFile().mkdirs();
        try {
            f.createNewFile();
        } catch (IOException e) {

            e.printStackTrace();
            try {
                mutex.acquire();
                CrawlerManager.write2logfile("ERR Nu poate fi creat fișierul "+calea2 + "index" + index + theExtension);
            } catch (InterruptedException e1) {
                System.out.println("Eroare la mutex");
            } finally {
                mutex.release();
            }

        }
        return f;

    }

    public void downloadTask(URLQueue downloadPending,int start,int end)  {
        //BufferedReader reader;
        CrawlerManager crawler_instance = CrawlerManager.getInstance();
        String root_dir = crawler_instance.getRoot_dir();

        for(int i=start;i<end;i++)
        {
            URL url = downloadPending.getLinksList(i);
            String[] arrOfStr = url.toString().split("\\?", 2); //șterg parametrii de GET
            String withoutAsk=arrOfStr[0];
            String[] calea=withoutAsk.split("//",0);//șterg protocolul http/https
            String calea2=calea[1].replaceAll("/","\\\\");//inlocuiesc / cu \ pentru a putea crea fișiere
            calea2=root_dir+"\\"+calea2;
            String lastCH=calea2.substring(calea2.length() - 1);
            if(lastCH.equals("\\")==false)//daca fișierul nu se termina cu \ adaug unul pentru organizarea in subdirectoare
            {
                calea2+="\\";

            }
            String theExtension=".html";
            try {

                //reader = new BufferedReader(new InputStreamReader(url.openStream()));
                // BufferedWriter writer = null;


                String[]extensionsString=this.extensions.split("#",0);

                for (int k = 0; k < extensionsString.length; k++)
                {
                    int sizeExtensie = extensionsString[k].length();
                    String aux_url = calea2.substring(calea2.length() - sizeExtensie);
                    //System.out.println("extensionsString[k] "+extensionsString[k]+" aux_url "+aux_url);
                    if ((extensionsString[k].equals(aux_url)))
                    {
                        theExtension = extensionsString[k];

                        break;
                    }
                }


                //documentele doc,pdf etc. se descarca diferit fața de cele html
                if(theExtension.equals(".html"))
                {
                    File f=createFile(calea2,i,theExtension);
                    BufferedReader reader =  new BufferedReader(new InputStreamReader(url.openStream()));

                    BufferedWriter writer = new BufferedWriter(new FileWriter(calea2 + "index" + i + theExtension));
                    //    System.out.println("extension "+theExtension);
                    //    System.out.println("Calea: "+ calea2  + "index" + i + theExtension);
                    //    System.out.println("FromURL "+url.toString());

                    String line;
                    while ((line = reader.readLine()) != null) {

                        String myLink = line;
                        boolean needs_replacement = false;
                        if (myLink.contains("href=\"http://")) {
                            myLink = myLink.replace("href=\"http://", "href=\"" + root_dir + "\\");
                            needs_replacement = true;
                        }
                        if (myLink.contains("href=\"https://")) {
                            myLink = myLink.replace("href=\"https://", "href=\"" + root_dir + "\\");
                            needs_replacement = true;
                        }

                        if (needs_replacement) {

                            writer.write(myLink);
                        }
                        else {

                            writer.write(line);
                        }
                    }

                    try {
                        mutex.acquire();
                        CrawlerManager.write2logfile("INFO Pagina a fost salvata in fișierul"+calea2);
                        System.out.println("INFO Pagina a fost salvata in fișierul"+calea2);
                    } catch (InterruptedException e) {
                        System.out.println("Eroare la mutex");
                    } finally {
                        mutex.release();
                    }

                }
                else {

                    InputStream in = url.openStream();
                    int length = -1;
                    byte[] buffer = new byte[4096];
                    //writer = new BufferedWriter(new FileWriter(calea2+"index"+i+theExtension));

                    File f=createFile(calea2,i,theExtension);
                    // System.out.println("extension "+theExtension);
                    // System.out.println("Calea: "+ calea2  + "index" + i + theExtension);
                    // System.out.println("FromURL "+url.toString());
                    FileOutputStream fos = new FileOutputStream(f);

                    // System.out.println("pagina "+i+" este "+url.toString());
                    while ((length = in.read(buffer)) > -1) //(line=reader.readLine())!=null
                    {
                        String myLink = new String(buffer);
                        boolean needs_replacement = false;
                        if (myLink.contains("href=\"http://")) {
                            myLink = myLink.replace("href=\"http://", "href=\"" + root_dir + "\\");
                            needs_replacement = true;
                        }
                        if (myLink.contains("href=\"https://")) {
                            myLink = myLink.replace("href=\"https://", "href=\"" + root_dir + "\\");
                            needs_replacement = true;
                        }

                        if (needs_replacement)
                            fos.write(myLink.getBytes(), 0, myLink.length());
                        else
                            fos.write(buffer, 0, length);
                    }
                    fos.close();
                    in.close();
                    try {
                        mutex.acquire();
                        CrawlerManager.write2logfile("INFO Pagina a fost salvata in fișierul"+calea2);
                        System.out.println("INFO Pagina a fost salvata in fișierul"+calea2);
                    } catch (InterruptedException e) {
                        System.out.println("Eroare la mutex");
                    } finally {
                        mutex.release();
                    }
                }
            }
            catch ( Exception e1)
            {
                System.out.println("Pagina "+url  + " nu exista/nu a putut fi deschisa sau fișierul nu a putut fi creat:");
                try {
                    mutex.acquire();
                    CrawlerManager.write2logfile("Pagina "+url+ " nu exista/nu a putut fi deschisa sau fișierul nu a putut fi creat:");
                } catch (InterruptedException e) {
                    System.out.println("Eroare la mutex");
                } finally {
                    mutex.release();
                }

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

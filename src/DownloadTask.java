import java.io.BufferedReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.InputStreamReader;
import java.net.URL;

import static java.lang.Math.round;
/**
 *Această clasă moștenește interfața Task
 * și suprascrie metoda execute astfel încât
 * metoda să descarce în mod recursiv o pagină din
 * internet
 *
 * @author Vasi Oniga
 */

public class DownloadTask implements Task {
    /**
     * Descrierea membrilor
     * numThreads reprezintă numărul de threaduri care descarcă pagini
     * extensions este un string care conține ,despărțite prin #, extensiile fișierelor care trebuie descărcate
     * depthlevel este adâncimea până la care se merge recursiv
     * disalowList reprezintă regurile regex ale paginilor care nu trebuie descărcate conform robots.txt
     * rootPage este domeniul paginii "root" de la care se pleacă cu descărcare recursivă
     * În downloadPending se adaugă recursiv paginile care trebuie descărcate înainte ca acestea să fie descărcate
     * spider este managerul prin care este folosit crawlerul
     */
    private int numThreads;
    private String extensions;
    private int depthLevel;
    private ArrayList<String>disalowList;
    String rootPage;
    URLQueue downloadPending;
    CrawlerManager spider;

    /**
     *
     * @param domain pornind de la aces domeniu se obține fișierul robots.txt
     * @return
     */
    public int getDisalowList(URL domain) 
    {

        this.disalowList=new ArrayList<String>();
        URL robotsURL=null;
        try
        {
            robotsURL = new URL(domain.getProtocol() + "://" + domain.getHost()+"/robots.txt");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return 1;
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
                        String withoutStar=auxLines[1].replaceAll("\\*","");

                        this.disalowList.add(withoutStar);
                        

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
            return 1;
        }


        return 0;
    }

    /**
     *isAllowed
     * Verifică dacă pagina corespunzătoare URL_ului dat ca parametru
     * poate fi descărcată conform regulile din robots.txt
     * @param toCheck URL-ul ce urmează să fie verificat
     * @return true sau false în funcție de răspuns
     */
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
             toDownload=new URL(rootPage);
        } catch (MalformedURLException e) {
            e.printStackTrace();

        }
        //String line = null;
        downloadPending.addToList(toDownload);
        String urlRegex= toDownload.getProtocol() + "://" + toDownload.getHost();
        URL mydomain=null;
        try {
            mydomain=new URL(urlRegex);
        } catch (MalformedURLException e) {
            e.printStackTrace();

        }
        System.out.println("domeniu "+mydomain);

        urlRegex+="[a-zA-Z0-9+&@#/%?=~_|!:,.;\\-]*";

        int indexQ=0;
        /*@<code>getDisalowList returnează parsează regulile din robots.txt</code>*/
        this.getDisalowList(mydomain);
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
                                        //dacă pagina aparține domeniului, nu există în listă și e nu e disallowed in
                                        //robots.txt, o adaug în listă
                                        //pentru a fi descărcată mai târziu,
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
                        System.out.println("Pagina nu există:");
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



    public DownloadTask(int numThreads,String _root,String _extensions,int _depthLevel) {
        this.numThreads = numThreads;
        this.rootPage =_root;
        this.downloadPending=new URLQueue();
        this.extensions=_extensions;
        this.depthLevel=_depthLevel;
        this.spider = CrawlerManager.getInstance();
    }
    public void listURLQ()
    {
        this.downloadPending.listQ();
        System.out.println("Numărul de pagini descărcate "+this.downloadPending.getSize());
    }
}

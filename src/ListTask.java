import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.io.File;
import java.io.FilenameFilter;

/**
 * Clasa ListTask implementeaza afisarea recursiva dintr-un director a
 * tuturor fisierelor de un anumit tip.
 * @author Cristescu Teodor
 */
public class ListTask implements Task{
    /**
     *membrul mTip reprezinta tipul de fisier cautat.
     * membrul mCale reprezinta calea unde se va cauta.
     * lista_fisiere reprezinta o lista unde se vor stoca numele fisierelor de tipul cautat.
     *
     */
    private String mTip;
    private String mCale;
    ArrayList<String> lista_fisiere = new ArrayList<String>();

    /**
     * Constructorul clasei ListTask
     * @param type reprezinta tipul de fisier cautat.
     * @param path reprezinta calea unde se va cauta.
     */
    public ListTask(String type, String path)
    {
        this.mTip = type;
        this.mCale = path;
    }

    /**
     * Functia execute realizeaza cautarea recursiva a fisierelor de tipul dat ca argument.
     * @throws IOException
     */
    public void execute() throws IOException {

        File directoryPath = new File(mCale);
        try {

            searchInDirector(directoryPath, mTip);
            if(lista_fisiere.size()==0)
            {
                System.out.print("\n Nu s-au gasit fisiere de acest tip \n");
                String content= "INFO: Nu s-au gasit fisiere de acest tip! \n";
                CrawlerManager.write2logfile(content);
            }
            else
            {
                System.out.print("\n Fisierele de acest tip sunt:  \n");
                String content="INFO: au fost gasite fisiere de acest tip! \n";
               CrawlerManager.write2logfile(content);
                System.out.print(lista_fisiere);
                System.out.print("\n");
            }

        } catch (Exception e) {

            System.out.print("\n Directorul nu exista \n");
            String content="ERROR: Directorul nu exista! \n";
            CrawlerManager.write2logfile(content);

        }
    };

    /**
     * Functia SearchInDIrector va face cautarea efectiva in director
     * @param f reprezinta directorul unde sa caute
     * @param tip reprezinta tipul cautat
     */
    private void searchInDirector(File f,String tip) {
        File[] a = f.listFiles();

        for (File x : a) {
            if (x.isDirectory()) {
                searchInDirector(x, tip);
            } else if (x.getName().endsWith(tip)) {
                lista_fisiere.add(x.getName());
            }
        }
    }
}

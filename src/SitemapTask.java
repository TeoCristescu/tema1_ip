import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Aceasta clasa implementeaza crearea unui task prin
 * care se va genera un fisier sitemap.txt,care va contine sitemap-ul
 * creat dupa descarcarea paginilor.
 * Acest sitemap va contine structura arborescenta a site-urilor descarcate
 * si va fi localizat in directorul radacina.
 *
 * @author Lacramioara Runcan
 */

public class SitemapTask implements Task {
    /**
     * Descrierea membrilor
     */
    public String path;

    /**
     * Constructorul clasei SitemapTask
     * @param path Acest parametru reprezinta calea catre directorul
     *             radacina care reprezinta pagina principala de la care
     *             a pornit descarcarea recursiva.
     *
     */

    public SitemapTask(String path) {
        this.path = path;
    }

    /**
     * Functia generateSitemap este o functie de tip String in care se
     * creaza un StringBuilder,cu ajutorul caruia se va returna sub forma unui string
     * structura arborescenta a paginilor descarcate.
     * Acesta functie apeleaza functia suprascrisa generateSitemap.
     *
     * @param dirPath Acest parametru reprezinta calea catre directorul
     *                radacina care reprezinta pagina principala de la care
     *                a pornit descarcarea recursiva.
     * @exception DirectoryNotFoundException Exceptiile returnate din cauza folosirii
     * unui director care nu exista.
     *
     */

    public  String generateSitemap(File dirPath) throws DirectoryNotFoundException
    {
        if(!dirPath.isDirectory())
        {
            String message = "ERROR: This folder is not a directory.";
            CrawlerManager.write2logfile(message);
            throw new DirectoryNotFoundException("This folder is not a directory.");
        }
        int index=0;
        StringBuilder sb=new StringBuilder();
        generateSitemap(dirPath,index,sb);
        return sb.toString();
    }

    /**
     * Functia generateSitemap este o functie suprascrisa in care se construeiste
     * StringBuilder-ul cu ajutorul caruia se va returna sub forma unui string
     * structura arborescenta a paginilor descarcate.
     * Acesta functie este una recursiva,deoarece,StringBuilder-ul va contine si
     * fisierele din subdirectoarele directorului radacina.
     *
     * @param dirpath Acest parametru reprezinta calea catre directorul
     *                sau subdirectorul in care se face cautarea si scrierea
     *                fisierelor in StringBuilder.
     * @exception DirectoryNotFoundException Exceptiile returnate din cauza folosirii
     * unui director care nu exista.
     *
     */
    private void generateSitemap(File dirpath, int index, StringBuilder sb) throws DirectoryNotFoundException {
        if(!dirpath.isDirectory())
        {
            String message = "ERROR: This folder is not a directory.";
            CrawlerManager.write2logfile(message);
            throw new DirectoryNotFoundException("This folder is not a directory.");
        }
        sb.append(getIndentString(index));
        sb.append("\t");
        sb.append(dirpath.getName());
        sb.append("/");
        sb.append("\n");
        for(File file :dirpath.listFiles())
        {
            if(file.isDirectory())
            {
                generateSitemap(file,index+1,sb);
            }
            else
            {
                printFile(file,index+1,sb);
            }
        }
    }

    /**
     * Functia printFile este folosita pentru a afisa pe nivele continutul
     * subdirectoarelor din directorul radacina.
     *
     * @param file Acest parametru este folosit  pentru a returna numele fisierului
     * @param index Acest parametru este folosit pentru a returna indexul de la care
     *              se face printarea(pozitia din StringBuilder)
     * @param sb Acest parametru este folosit pentru a retine numele fisierelor
     *           sub despartite astfel incat sa se evidentieze originea lor.
     *
     *
     */
    private static void printFile(File file, int index, StringBuilder sb)
    {
        sb.append(getIndentString(index));
        sb.append("\t");
        sb.append(file.getName());
        sb.append("\n");
    }

    /**
     * Aceasta functie este folosita pentru a realiza identarea corecta
     * a numelor fisierelor.
     *
     * @param index Acest parametru este folosit pentru a retine pozitia
     *              la care s-a ajuns.Reprezinta nivelul de identare.
     *
     */
    private static String getIndentString(int index) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < index; i++) {
            sb.append("\t");
        }
        return sb.toString();
    }


    /**
     * Functia execute() este o functie suprascrisa a celei din interfata Task,
     * descrie task-ul pe care thread-ul il va executa.In interiorul acestei functii
     * se va crea un fisier sitemap.txt,daca acesta exista deja,va fi sters si va fi creat unul nou.
     * In interiorul acestui fisier se va scrie continutul StringBuilder-ului creat anterior.
     *
     * @exception DirectoryNotFoundException Exceptiile returnate din cauza folosirii
     * unui director care nu exista.
     * @exception  IOException Exceptiile returnate in momentul scrierii in fisierul sitemap.txt.
     *
     */

    @Override
    public void execute() throws DirectoryNotFoundException, IOException{
        File dirPath=new File(this.path);
        String sitemap= generateSitemap(dirPath);
        String newpath=dirPath + "/" + "sitemap.txt";
        File sitemap_file=new File(newpath);
        String message = "INFO: Creating the sitemap file.";
        CrawlerManager.write2logfile(message);
        if(sitemap_file.exists()) {
            sitemap_file.delete();
        }
        if(!sitemap_file.createNewFile())
        {
            message = "ERROR: Error creating sitemap file.";
            CrawlerManager.write2logfile(message);
            throw new DirectoryNotFoundException("Error creating file.");
        }
        else {
            FileWriter myWriter = new FileWriter(newpath);
            myWriter.write(sitemap.toString());
            myWriter.close();
            message = "INFO: Writing to the sitemap file.";
            CrawlerManager.write2logfile(message);
        }
    }
}

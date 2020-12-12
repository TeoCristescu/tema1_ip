import java.io.File;
import java.io.IOException;

/**
 * Aceasta clasa implementeaza crearea unui task prin
 * care se vor filtra toate fisierele descarcate
 * in functie de marimea acestora.
 *
 * @author Lacramioara Runcan
 */
public class FilterBySizeTask implements Task {
    /**
     * Descrierea membrilor
     */
    private String path;
    private int size;


    /**
     * Constructorul clasei FilterBySizeTask
     * @param path Acest parametru reprezinta calea catre directorul
     *             in care se a aplica filtrul de marime a fisierelor.
     * @param size Acest parametru reprezinta marimea cu care se vor
     *             compara marimile fisierelor descarcate.
     */
    public FilterBySizeTask(String path, int size) {
        this.path = path;
        this.size = size;
    }


    /**
     * Functia de filtrare a fisierelor.
     * Se vor afisa toate fisierele care au marimea mai mare decat cea specificata.
     * Filtrarea acestor fisiere se va face recursiv,astfel,toate fisierele din toate
     * subdirectoarele directorului radacina,vor fi analizate in functie de marimea lor.
     *
     * @param dirPath Acest parametru reprezinta directorul radacina de la care
     *                porneste filtrarea fisierelor.
     * @exception  DirectoryNotFoundException Exceptiile returnate din cauza folosirii
     * unui director care nu exista.
     *
     */
    public void filterBySize(File dirPath) throws DirectoryNotFoundException, IOException {
        if (!dirPath.isDirectory()) {

            String message = "ERROR: Directory not found.";
            CrawlerManager.write2logfile(message);
            throw new DirectoryNotFoundException("Directory not found.");

        }
        File filesList[] = dirPath.listFiles();
        for (File file : filesList) {
            if (file.isDirectory()) {
                filterBySize(file);
            } else if (file.length() > this.size) {
                System.out.println(file.getName());
            }
        }

    }

    /**
     * Functia execute() este o functie suprascrisa a celei din interfata Task,
     * descrie task-ul pe care thread-ul il va executa.
     *
     * @exception DirectoryNotFoundException Exceptiile returnate din cauza folosirii
     * unui director care nu exista.
     *
     */

    @Override
    public void execute() throws DirectoryNotFoundException, IOException {
        File dirPath = new File(this.path);
        String message = "INFO: Searching for files larger than " + Integer.toString(this.size);
        CrawlerManager.write2logfile(message);
        filterBySize(dirPath);
    }
}







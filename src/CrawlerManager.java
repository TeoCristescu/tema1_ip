import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
public class CrawlerManager {
    private int threads_nr;
    private String root_dir;
    private int log_level;
    private int max_depth;
    private String type;
    private static CrawlerManager obj;

    private CrawlerManager(){


    }

    public static CrawlerManager getInstance(){
        if(obj==null)
        {
            obj=new CrawlerManager();
        }
        return obj;
    }
    public void crawl() throws FileNotFoundException {
        File f=new File("config.txt");
        Scanner scan = new Scanner(f);
        String line="";
        while(scan.hasNextLine())
            try {


                line = scan.nextLine();
                if (line.contains("n_threads")) {
                    threads_nr = Integer.parseInt(line.substring(9);
                } else if (line.contains("root_dir")) {
                    root_dir = line.substring(8);
                } else if (line.contains("log_level")) {
                    log_level = Integer.parseInt(line.substring(9));
                } else if (line.contains("max_depth")) {
                    max_depth = Integer.parseInt(line.substring(9));
                }
            } catch (ExceptieArgumente e) {
                e.printStackTrace();

            }
    }

    public int getThreads_nr() {
        return threads_nr;
    }

    public String getRoot_dir() {
        return root_dir;
    }

    public int getLog_level() {
        return log_level;
    }

    public int getMax_depth() {
        return max_depth;
    }

    public String getType() {
        return type;
    }
}

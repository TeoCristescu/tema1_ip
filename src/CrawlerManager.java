import java.io.FileNotFoundException;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
public class CrawlerManager {

    private int threads_nr;
    private String root_dir;
    private int log_level;
    private int max_depth;
    private String type;
    private static CrawlerManager obj;
    private  String URL;
    private String list_argument;
    private String search_argument;
    private String extensions;
    private int filter_size;
    private static LogManager log= new LogManager();
    

    private CrawlerManager() { }

    static void write2logfile(String message)  {
        try{
            log.writeToLogFile(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static CrawlerManager getInstance(){
        if(obj==null)
        {
            obj=new CrawlerManager();
        }
        return obj;
    }

    public void search_config(String searcharg,String root){
        search_argument=searcharg;
        root_dir=root;
    }
    public void list_config(String item,String root){
        list_argument=item;root_dir=root;
    }
    public void crawl_config(String configfile) throws FileNotFoundException, ExceptieArgumente {

        File f=new File(configfile);
        Scanner scan = new Scanner(f);
        String line="";
        while(scan.hasNextLine()) {
            line = scan.nextLine();
            if (line.contains("n_threads")) {
                threads_nr = Integer.parseInt(line.substring(10));
            } else if (line.contains("root_dir")) {
                root_dir = line.substring(9);
            } else if (line.contains("log_level")) {
                log_level = Integer.parseInt(line.substring(10));
            } else if (line.contains("max_depth")) {
                max_depth = Integer.parseInt(line.substring(10));
            } else if (line.contains("URL")) {
                URL = line.substring(4);
            } else if (line.contains("extensions")) {
                extensions = line.substring(11);
            } else {
                throw new ExceptieArgumente("Option not recognised in config file");
            }
        }
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setRoot_dir(String root_dir) {
        this.root_dir = root_dir;
    }
    public void setFilter_size(int filter_size) {
        this.filter_size = filter_size;
    }


    public int getThreads_nr() {
        return threads_nr;
    }
    public String getRoot_dir() {
        return root_dir;
    }
    public int getMax_depth() {
        return max_depth;
    }
    public String getListArg() {
        return list_argument;
    }
    public String getType() {
        return type;
    }
    public String getURL()
    {
        return URL;
    }
    public int getFilter_size() {
        return filter_size;
    }
    public String getExtensions() {
        return extensions;
    }
    public String getSearch_argument() {
        return search_argument;
    }
}

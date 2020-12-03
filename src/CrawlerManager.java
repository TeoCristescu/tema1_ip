import java.net.URL;
public class CrawlerManager {
    private int threads_nr;
    private String root_dir;
    private int log_level;
    private int max_depth;
    private String type;

    public CrawlerManager(int threads_nr, String root_dir, int log_level, int max_depth, String type) {
        this.threads_nr = threads_nr;
        this.root_dir = root_dir;
        this.log_level = log_level;
        this.max_depth = max_depth;
        this.type = type;
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

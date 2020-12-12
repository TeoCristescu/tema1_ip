import java.io.IOException;

public class Crawler
{
    public static void run(String[] args ){
        try {
            ArgumentsManager.initCrawler(args);
            CrawlerManager spider = CrawlerManager.getInstance();
            switch (spider.getType()) {
                case "crawler":
                    DownloadTask dw = new DownloadTask(spider.getThreads_nr(),spider.getURL(),spider.getExtensions(),spider.getMax_depth());
                    dw.execute();
                    break;
                case "sitemap":
                    SitemapTask sitemap = new SitemapTask(spider.getRoot_dir());
                    sitemap.execute();
                    break;
                case "list":
                    ListTask list = new ListTask(spider.getListArg(),spider.getRoot_dir());
                    list.execute();
                    break;
                case "search":
                    SearchTask search = new SearchTask(spider.getSearch_argument(),spider.getRoot_dir());
                    search.execute();
                    break;
                case "filter":
                    FilterBySizeTask filter = new FilterBySizeTask(spider.getRoot_dir(), spider.getFilter_size());
                    filter.execute();
                    break;
            }
        }
        catch(Exception e) // CATCH EVERYTHING
        {
            CrawlerManager.write2logfile("Ala bala portocala");
            System.out.println(e.getMessage());

        }
    }
}

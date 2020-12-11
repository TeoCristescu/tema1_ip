public class Crawler
{
    public static void run(String[] args )
    {
        try {
            ArgumentsManager.initCrawler(args);
            //String myUrl="https://www.emag.ro/";
            //CrawlerManager spider=CrawlerManager.getInstance();
            //DownloadTask dw=new DownloadTask(spider.getThreads_nr(),spider.getURL(),".pdf#.png#.docx#.jpg",spider.getMax_depth());
            //dw.execute();
            //dw.listURLQ();
            //ListTask list= new ListTask("png","www.emag.ro");
            //list.execute();
            //SearchTask search=new SearchTask("telefon","www.emag.ro");
            //search.execute();
            //SitemapTask sitemap= new SitemapTask("www.emag.ro");
            //sitemap.execute();
            //FilterBySizeTask filter=new FilterBySizeTask("www.emag.ro",600000);
            //filter.execute();
            CrawlerManager spider=CrawlerManager.getInstance();
            switch (spider.getType()) {
                case "crawler":
                    DownloadTask dw=new DownloadTask(spider.getThreads_nr(),spider.getURL(),spider.getExtensions(),spider.getMax_depth());
                    dw.execute();
                case "sitemap":
                    SitemapTask sitemap= new SitemapTask(spider.getRoot_dir());
                    sitemap.execute();
                case "list":
                    ListTask list= new ListTask(spider.getListArg(),spider.getRoot_dir());
                    list.execute();
                    break;
                case "search":
                    SearchTask search=new SearchTask(spider.getSearch_argument(),spider.getRoot_dir());
                    search.execute();
                    break;
                case "filter":
                    FilterBySizeTask filter=new FilterBySizeTask(spider.getRoot_dir(), spider.getFilter_size());
                    filter.execute();
                    break;
            }

        }
        catch(Exception e) // CATCH EVERYTHING
        {
            System.out.println(e.getMessage());
        }
    }
}

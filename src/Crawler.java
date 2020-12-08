public class Crawler
{
    public static void run(String[] args )
    {
        try {
            ArgumentsManager.initCrawler(args);
        }
        catch(Exception e) // CATCH EVERYTHING
        {
            System.out.println(e.getMessage());
        }
    }
}

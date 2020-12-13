import java.io.FileNotFoundException;

/**
 * Aceasta clasa implementeaza verificarea argumentelor
 * date programului de catre utilizator
 * Aceasta este o clasa care contine numai metode statice fiind
 * folosita numai la inceputul programului
 *
 * @author Scraba Cristian
 */
public class ArgumentsManager
{
    /* Membrul firstArg este utilizat pentru comparatiile cu primul argument */
    private enum firstArg {
        crawler,
        list,
        search,
        sitemap,
        filter
    }

    /**
     * Aceasta functie intoarce un string folosit in aruncarea unei exceptii
     * Stringul se compune din toate posibilitatile de folosire a primului argument
     */
    private static String exceptionString1stArgs()
    {
        String ret = "Insufficient args! Try:\n";

        for (firstArg arg : firstArg.values())
            ret += arg + "\n";

        CrawlerManager.write2logfile("WARNING: " + ret);

        return ret;
    }

    /**
     * Aceasta functie intoarce un string folosit in aruncarea unei exceptii
     * Stringul se compune din toate warningurile date utilizatorului in cazul fiecarui prim argument dat
     */
    private static String exceptionString2ndArgs(String firstArgument)
    {
        String ret = "Insufficient args!\n";

        switch (firstArgument) {
            case "filter":
                ret += "Missing size and path!\n";
                break;
            case "list":
                ret += "Missing type and path!\n";
                break;
            case "search":
                ret += "Missing search word and path!\n";
                break;
            case "crawler":
                ret += "Missing config file!\n";
                break;
            case "sitemap":
                ret += "Missing path!\n";
                break;
            default:
                ret += "First argument error! Try:\n";
                for (firstArg arg : firstArg.values())
                    ret += arg + "\n";
                break;
        }

        CrawlerManager.write2logfile("WARNING: " + ret);

        return ret;
    }

    private static String exceptionString3rdArgs(String firstArgument)
    {
        String ret = "Insufficient args!\n";

        switch (firstArgument) {
            case "filter":
            case "list":
            case "search":
                ret += "Missing path!\n";
                break;
            default:
                ret += "First argument error! Try:\n";
                for (firstArg arg : firstArg.values())
                    ret += arg + "\n";
                break;
        }

        CrawlerManager.write2logfile("WARNING: " + ret);

        return ret;
    }

    /**
     * Aceasta functie va porni rularea programului in cazul tuturor argumentelor date corect
     * sau in celalalt caz va arunca o exceptie specifica.
     */
    private static void validateArgs(String[] args) throws ExceptieArgumente, FileNotFoundException
    {
        CrawlerManager spider=CrawlerManager.getInstance();

        if(args.length == 2)
        {
            switch (args[0]) {
                case "crawler":
                    spider.setType(args[0]);
                    spider.crawl_config(args[1]);
                    break;
                case "sitemap":
                    spider.setType(args[0]);
                    spider.setRoot_dir(args[1]);
                    break;
                case "list":
                case "filter":
                case "search":
                    throw new ExceptieArgumente(exceptionString3rdArgs(args[0]));
                default:
                    String ret = "First argument error! Try:\n";
                    for (firstArg arg : firstArg.values())
                        ret += arg + "\n";
                    CrawlerManager.write2logfile("WARNING: " + ret);
                    throw new ExceptieArgumente(ret);
            }
        }

        if(args.length == 3)
        {
            switch (args[0]) {
                case "crawler":
                case "sitemap":
                    throw new ExceptieArgumente("Too many arguments!\n");
                case "list":
                    spider.setType(args[0]);
                    spider.list_config(args[1], args[2]);
                    break;
                case "search":
                    spider.setType(args[0]);
                    spider.search_config(args[1], args[2]);
                    break;
                case "filter":
                    spider.setType(args[0]);
                    spider.setRoot_dir(args[2]);
                    spider.setFilter_size(Integer.parseInt(args[1]));
                    break;
                default:
                    String ret = "First argument error! Try:\n";
                    for (firstArg arg : firstArg.values())
                        ret += arg + "\n";
                    CrawlerManager.write2logfile("WARNING: " + ret);
                    throw new ExceptieArgumente(ret);
            }
        }
    }

    /**
     * Aceasta functie este apelata la inceputul programului
     * si verifica numarul de argumente pentru a arunca exceptiile potrivite.
     */
    public static void initCrawler(String[] args) throws ExceptieArgumente, FileNotFoundException
    {
        switch (args.length) {
            case 0:
                throw new ExceptieArgumente(exceptionString1stArgs());
            case 1:
                throw new ExceptieArgumente(exceptionString2ndArgs(args[0]));
            case 2:
            case 3:
                validateArgs(args);
                break;
            default:
                CrawlerManager.write2logfile("WARNING: Too many arguments!");
                throw new ExceptieArgumente("Too many arguments!\n");
        }
    }
}

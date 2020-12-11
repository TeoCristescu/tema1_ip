import java.io.FileNotFoundException;
import java.io.IOException;

public class ArgumentsManager
{
    private enum firstArg {
        crawler,
        list,
        search,
        sitemap,
        filter
    }

    private static String exceptionString1stArgs()
    {
        String ret = "Insufficient args! Try:\n";

        for (firstArg arg : firstArg.values())
            ret += arg + "\n";

        return ret;
    }

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

        return ret;
    }
    private static String exceptionString3rdArgs(String firstArgument)
    {
        String ret = "Insufficient args!\n";

        switch (firstArgument) {
            case "filter":
                ret += "Missing path!\n";
                break;
            case "list":
                ret += "Missing path!\n";
                break;
            case "search":
                ret += "Missing path!\n";
                break;
            default:
                ret += "First argument error! Try:\n";
                for (firstArg arg : firstArg.values())
                    ret += arg + "\n";
                break;
        }

        return ret;
    }

    private static void validateArgs(String[] args,int nr_of_args) throws ExceptieArgumente, IOException, DirectoryNotFoundException {
        CrawlerManager spider=CrawlerManager.getInstance();

        if(nr_of_args==2) {
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
                        throw new ExceptieArgumente(exceptionString3rdArgs(args[0]));
                    case "search":
                        throw new ExceptieArgumente(exceptionString3rdArgs(args[0]));
                    case "filter":
                        throw new ExceptieArgumente(exceptionString3rdArgs(args[0]));
                    default:
                        String ret = "First argument error! Try:\n";
                        for (firstArg arg : firstArg.values())
                            ret += arg + "\n";
                        throw new ExceptieArgumente(ret);
                }
        }
           else if(nr_of_args==3) {

            switch (args[0]) {
                case "crawler":
                    throw new ExceptieArgumente("Too many arguments!\n");
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
                    throw new ExceptieArgumente(ret);
            }
        }



    }

    public static void initCrawler(String[] args) throws ExceptieArgumente, IOException, DirectoryNotFoundException {

        switch (args.length) {
            case 0:
                throw new ExceptieArgumente(exceptionString1stArgs());
            case 1:
                throw new ExceptieArgumente(exceptionString2ndArgs(args[0]));
            case 2:
                validateArgs(args,2);
                break;
            case 3:
                validateArgs(args,3);
                break;


            default:
                throw new ExceptieArgumente("Too many arguments!\n");
        }
    }
}

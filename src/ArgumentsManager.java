public class ArgumentsManager
{
    private enum firstArg {
        crawler,
        list,
        search
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
            case "crawler":
                ret += "Missing config file!\n";
                break;
            case "list":
                ret += "Missing type file!\n";
                break;
            case "search":
                ret += "Missing searched word!\n";
                break;
            default:
                ret += "First argument error! Try:\n";
                for (firstArg arg : firstArg.values())
                    ret += arg + "\n";
                break;
        }

        return ret;
    }

    private static void validateArgs(String arg1, String arg2) throws ExceptieArgumente
    {
        switch (arg1) {
            case "crawler":
                /* TODO: set crawler manager values in order to start the download */
                break;
            case "list":
                /* TODO: set crawler manager values in order to start the list */
                break;
            case "search":
                /* TODO: set crawler manager values in order to start the search */
                break;
            default:
                String ret = "First argument error! Try:\n";
                for (firstArg arg : firstArg.values())
                    ret += arg + "\n";
                throw new ExceptieArgumente(ret);
        }
    }

    public static void initCrawler(String[] args) throws ExceptieArgumente
    {
        switch (args.length) {
            case 0:
                throw new ExceptieArgumente(exceptionString1stArgs());
            case 1:
                throw new ExceptieArgumente(exceptionString2ndArgs(args[0]));
            case 2:
                validateArgs(args[0], args[1]);
                break;
            default:
                throw new ExceptieArgumente("Too many arguments!\n");
        }
    }
}

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class SitemapTask implements Task {
    public String path;

    public SitemapTask(String path) {
        this.path = path;
    }

    public  String generateSitemap(File dirPath) throws DirectoryNotFoundException
    {
        if(!dirPath.isDirectory())
        {
            throw new DirectoryNotFoundException("This folder is not a directory.");
        }
        int index=0;
        StringBuilder sb=new StringBuilder();
        generateSitemap(dirPath,index,sb);
        return sb.toString();
    }

    private void generateSitemap(File dirpath, int index, StringBuilder sb) throws DirectoryNotFoundException {
        if(!dirpath.isDirectory())
        {
            throw new DirectoryNotFoundException("This folder is not a directory.");
        }
        sb.append(getIndentString(index));
        sb.append("\t");
        sb.append(dirpath.getName());
        sb.append("/");
        sb.append("\n");
        for(File file :dirpath.listFiles())
        {
            if(file.isDirectory())
            {
                generateSitemap(file,index+1,sb);
            }
            else
            {
                printFile(file,index+1,sb);
            }
        }
    }

    private static void printFile(File file, int index, StringBuilder sb)
    {
        sb.append(getIndentString(index));
        sb.append("\t");
        sb.append(file.getName());
        sb.append("\n");
    }

    private static String getIndentString(int index) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < index; i++) {
            sb.append("\t");
        }
        return sb.toString();
    }



    @Override
    public void execute() throws DirectoryNotFoundException, IOException{
        File dirPath=new File(this.path);
        String sitemap= generateSitemap(dirPath);
        String newpath=dirPath + "/" + "sitemap.txt";
        File sitemap_file=new File(newpath);
        if(sitemap_file.exists()) {
            sitemap_file.delete();
        }
        if(!sitemap_file.createNewFile())
        {
            throw new DirectoryNotFoundException("Error creating file.");
        }
        else {
            FileWriter myWriter = new FileWriter(newpath);
            myWriter.write(sitemap.toString());
            myWriter.close();
        }
    }
}

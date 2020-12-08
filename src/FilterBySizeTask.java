package WebCrawler.tema1_ip.src;

import WebCrawler.tema1_ip.src.DirectoryNotFoundException;

import java.io.File;

public class FilterBySizeTask implements Task {
    private  String path;
    private int size;

    public FilterBySizeTask(String path,int size) {
        this.path=path;
        this.size=size;
    }
    public  void filterBySize(File dirPath) throws DirectoryNotFoundException {
        if(!dirPath.isDirectory())
        {
            throw new DirectoryNotFoundException("Directory not found.");
        }
        File filesList[] = dirPath.listFiles();
        for (File file : filesList) {
            if(file.isDirectory())
            {
                filterBySize(file);
            }
            else if(file.length() > this.size)
            {
                System.out.println(file.getName());
            }
        }

    }



    @Override
    public void execute() throws DirectoryNotFoundException {
        File dirPath=new File(this.path);
        filterBySize(dirPath);
    }
}

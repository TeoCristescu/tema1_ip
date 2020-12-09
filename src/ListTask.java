import java.util.ArrayList;
import java.util.Random;
import java.io.File;
import java.io.FilenameFilter;
public class ListTask implements Task{
    private String mTip;
    private String mCale;
    ArrayList<String> lista_fisiere = new ArrayList<String>();
    public ListTask(String type, String path)
    {
        this.mTip = type;
        this.mCale = path;
    }
    public void execute()
    {
        File directoryPath = new File(mCale);
        try {

            searchInDirector(directoryPath, mTip);
            if(lista_fisiere.size()==0)
            {
                System.out.print("\n Nu s-au gasit fisiere de acest tip \n");
            }
            else
            {
                System.out.print("\n Fisierele de acest tip sunt:  \n");
                System.out.print(lista_fisiere);
                System.out.print("\n");
            }

        } catch (Exception e) {
            System.out.print("\n Directorul nu exista \n");
        }
    };
    private void searchInDirector(File f,String tip) {
        File[] a = f.listFiles();

        for (File x : a) {
            if (x.isDirectory()) {
                searchInDirector(x, tip);
            } else if (x.getName().endsWith(tip)) {
                lista_fisiere.add(x.getName());
            }
        }
    }
}

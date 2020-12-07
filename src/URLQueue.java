import java.net.URL;
import java.util.ArrayList;
public class URLQueue {
    private ArrayList<URL>linksList;
    public void addToList(URL _toAdd){
        linksList.add(_toAdd);
    }
    public void removeFromList(URL _toDelete){
        linksList.remove(_toDelete);
    }
    public URL getLinksList(int _index){
       URL to_ret=linksList.get(_index);
       return  to_ret;
    }

    public URLQueue() {
        this.linksList =new ArrayList<URL>() ;
    }

    public int exists_elem(URL _toTest)
    {
        if(this.linksList.contains(_toTest))
        {
            return 1;
        }
        return  0;
    }

    public void listQ()
    {
        System.out.println(this.linksList);
    }

    int getSize()
    {
        return this.linksList.size();
    }
}

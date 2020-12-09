import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Main {
<<<<<<< HEAD

    public static void main(String[] args)  {

        Crawler.run(args);
        String myUrl="https://www.emag.ro/set-bakugan-battle-pack-ventus-phaedrus-si-pyrus-hydranoid-cu-5-figurine-incluse-6427037016615/pd/DVD4K7MBM/?ref=graph_profiled_similar_d_1_2&provider=rec&recid=rec_49_16_u2029049839059478178_807_D_3512debeebc80a515c5f233e1061b95d3d7b77546541c53a779832d517ac6563_1607520357&scenario_ID=49#product-gallery";
        DownloadTask dw=new DownloadTask(4,myUrl,".pdf#.png",1);
        dw.execute();
        dw.listURLQ();
=======
    public static void main(String[] args)  {
        String myUrl="https://www.emag.ro/";
        DownloadTask dw=new DownloadTask(4,myUrl,".pdf#.png",1);
        dw.execute();
        dw.listURLQ();

    }
>>>>>>> fc6d453858bfef910be920f0995aa834a7272cd2

    }
}

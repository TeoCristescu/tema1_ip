import java.io.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
public class SearchTask implements Task{
    private String mKeyWord;
    private String mCale;
    int counter;
    ArrayList<String> lista_fisiere = new ArrayList<String>();
    public SearchTask(String keyword, String path)
    {
        this.mKeyWord = keyword;
        this.mCale = path;
        counter = 0;
    }

    public void execute()
    {
        File directoryPath = new File(mCale);
        if (directoryPath.exists()) // Directory exists then proceed.
        {
            searchInDirector(directoryPath, mKeyWord);
            if (counter > 0) {
                System.out.print("\n\t List de fisiere care contin cuvantul cheie : \n");
                System.out.print(lista_fisiere);
            } else {
                System.out.print("\n\t Keyword not found ! ");
            }
        } else {
            System.out.print("\n Directorul nu exista\n");
        }
    };

    private void searchInDirector(File dir, String keyword)
    {

        for (File x : dir.listFiles()) {
            if (x.isDirectory()) {
                searchInDirector(x, keyword);
            } else {
                searchInFile(x, keyword);
            }
        }
    }
    private void searchInFile(File fisier, String keyword)
    {

        try {
            Pattern p = Pattern.compile(keyword);
            FileInputStream fis = new FileInputStream(fisier);
            byte[] data = new byte[fis.available()];
            fis.read(data);
            String text = new String(data);
            Matcher m = p.matcher(text);
            if (m.find()) {
                lista_fisiere.add(fisier.getName()); // add file to found-keyword list.
                counter++;
            }
            fis.close();
        } catch (Exception e) {
            System.out.print("\n\t Error processing file : " + fisier.getName());
        }
    }

}

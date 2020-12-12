import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
/**
 * Aceasta clasa este o clasa care implementeaza cautarea
 * unui cuvant cheie dat de utilizator
 * in toate fisierele paginilor descarcate
 *
 * @author Cristescu Teodor
 */
public class SearchTask implements Task{
    /**
     * membrul mKeyWord reprezinta cuvantul ce va fi cautat
     * membrul mCale reprezinta calea de unde va incepece cautarea recursiva
     * membrul counter reprezinta numarul de cate ori a fost gasit cuvantul cautat
     * membrul lista_fisiere reprezinta o lista unde vor fi stocate fisierele unde este gasit cuvantul
     */
    private String mKeyWord;
    private String mCale;
    int counter;
    ArrayList<String> lista_fisiere = new ArrayList<String>();

    /**
     * Construltorul clasei
     * @param keyword reprezinta cuvantul ce va fi cautat
     * @param path reprezinta calea de unde va incepe cautarea recursiva
     */
    public SearchTask(String keyword, String path)
    {
        this.mKeyWord = keyword;
        this.mCale = path;
        counter = 0;
    }

    /**
     *  Functia execute() este functia care va face cautarea cuvantului
     *  In functie de calea data se cauta in acel fisier sau se cauta recursiv in
     *  fisierele din director
     * @throws IOException
     */
    public void execute() throws IOException {


        File directoryPath = new File(mCale);
        if (directoryPath.exists())
        {
            searchInDirector(directoryPath, mKeyWord);
            if (counter > 0) {

                CrawlerManager.write2logfile("INFO: s-a gasit cuvantul cautat!\n");

                System.out.print("\n\t Lista de fisiere care contin cuvantul cheie : \n");
                System.out.print(lista_fisiere);
            } else {

                CrawlerManager.write2logfile("INFO: nu s-a gasit cuvantul cautat!\n");

                System.out.print("INFO: nu s-a gasit cuvantul cautat");
            }
        } else {
            System.out.print("\n Directorul nu exista\n");
            CrawlerManager.write2logfile("INFO: directorul nu exista!\n");


        }
    };

    /**
     * Aceasta functie este apelata de functia execute in cazul in care
     * calea data ca argument duce la un director.
     * In aceasta functie se apeleaza functia de cautare in fisier daca fisierele din director nu sunt directoare.
     * In caz contrar se va apela recursiv.
     * @param dir reprezinta directorul unde se va face cautarea
     * @param keyword reprezinta cuvantul cheie ce se cauta
     * @throws IOException
     */
    private void searchInDirector(File dir, String keyword) throws IOException {

        for (File x : dir.listFiles()) {
            if (x.isDirectory()) {
                searchInDirector(x, keyword);
            } else {
                searchInFile(x, keyword);
            }
        }
    }

    /**
     * Aceasta functie realizeaza cautarea cuvantului cheie intr-un fisier.
     * Continutul fisierului dat ca parametru se citeste si apoi se cauta cuvantul cheie.
     * Daca acesta este gasit, counterul este incrementat, iar numele fisierului este adaugat in lista.
     *
     * @param fisier reprezinta fisierul unde se cauta cuvantul cheie.
     * @param keyword reprezinta cuvantul cheie cautat.
     * @throws IOException
     */
    private void searchInFile(File fisier, String keyword) throws IOException {

        try {
            Pattern p = Pattern.compile(keyword);
            FileInputStream fis = new FileInputStream(fisier);
            byte[] data = new byte[fis.available()];
            fis.read(data);
            String text = new String(data);
            Matcher m = p.matcher(text);
            if (m.find()) {
                lista_fisiere.add(fisier.getName());
                counter++;
            }
            fis.close();
        } catch (Exception e) {
            System.out.print("\n\t Error processing file : " + fisier.getName());
           CrawlerManager.write2logfile("ERROR: eroare la procesarea paginii! \n");

        }
    }

}

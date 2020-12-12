import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Aceasta clasa este folosita pentru a crea un fisier log.txt
 * in cadrul caruia se vor stoca mesajele de informare si cele
 * de eroare.
 *
 * @author Lacramioara Runcan
 */

public class LogManager {

    /**
     * Functia de scriere in fiserul log.txt a mesajelor specifice.
     * In acesta functie se apeleaza functia de adaugare a mesajelor in fisier.
     *
     * @param message Acest parametru reprezinta mesajul ce trebuie stocat in fisierul de log.
     * @exception IOException Exceptiile returnate de crearea/scrierea in fisier.
     *
     */
    public void writeToLogFile(String message) throws IOException {
        appendToFile("log.txt",message);
    }

    /**
     * Functia care returneaza data si timpul in care sunt
     * returnate acele mesaje de informare sau de eroare.
     *
     *
     */
    public String getDateAndTime()
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd - HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String strDateTime = dtf.format(now).toString();
        String newStr="[ " + strDateTime + " ] ";
        return newStr;
    }

    /**
     * Functia de adaugare a mesajelor in fisier.
     * Scrierea in fisier se face prin adaugarea unor linii
     * noi la cele deja existente in fisier.
     *
     * @param filename Acest parametru face referire la numele fisierului
     *                 in care vor fi stocate mesajele.
     * @param message Acest parametru reprezinta mesajul care se scrie in fisier.
     *
     * @exception IOException Exceptiile returnate de crearea/scrierea in fisier.
     *
     */
    private void appendToFile(String filename,String message) throws IOException {
        File file=new File(filename);
        FileWriter fwr=null;
        fwr =new FileWriter(file,true);
        String datetime=getDateAndTime();
        String line= datetime+ message + "\n";
        fwr.write(line);
        fwr.close();
    }
}

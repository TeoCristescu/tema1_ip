import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogManager {

    public void writeToLogFile(String message) throws IOException {
        appendToFile("log.txt",message);
    }

    public String getDateAndTime()
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd - HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String strDateTime = dtf.format(now).toString();
        String newStr="[ " + strDateTime + " ] ";
        return newStr;
    }

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

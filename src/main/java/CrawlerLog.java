import java.io.*;
import java.util.Date;

/**
 * Created by Christian Gibbs on 9/30/2016.
 */
public class CrawlerLog {
    private static String sep = File.separator;
    private static String filePath = "src"+ sep + "main" + sep +"resources"+ sep + "logfiles";

    private static String getTimeStamp()
    {
        return new Date().toString();
    }

    public static void logException(String message, Exception e)
    {

        //create timestamp and log entry
        String now = getTimeStamp();
        String logEntry = now + " : " + message + " : "+ e.getMessage();

        String fileName = filePath+sep+"exceptionlog.txt";

        //put everything in file
        File logFile = new File(fileName);
        try {
            FileWriter writer = new FileWriter(fileName, true);
            BufferedWriter textWriter = new BufferedWriter(writer);
            textWriter.write(logEntry);
            textWriter.newLine();
            textWriter.close();
        }
        catch(IOException err)
        {
            System.out.println("Log File could not be opened: "+ fileName);
        }

    }



}

/**
 * Created by Lomova Natalia.
 */
import java.io.*;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Writer {
    String fs = System.getProperty("file.separator");

    FileWriter fw = new FileWriter("files"+ fs + "out.txt");

    public Writer() throws IOException {

    }

    public void writeTofile(String word, String url) throws IOException {
        fw.write("Found word " + word + " on page " + url + " at " + getCurrentTimeStamp());
        fw.write('\n');
        fw.flush();
    }

    public void cleanUp() throws IOException {
        fw.close();
    }
    //get time catching data
    public String getCurrentTimeStamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
    }
}

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Calendar;

/**
 * This is to log the request to a file.
 * 
 * @author 170026060
 *
 */
public class LogRequest {
    private String type = null; // the type of the request.
    private String response = null; // the content of the response in the form
                                    // of String.

    /**
     * This is the constructor.
     * 
     * @param requestType
     *            the request type
     * 
     * @param responseCode
     *            the response code
     */
    public LogRequest(String requestType, String responseCode) {
        this.type = requestType;
        this.response = responseCode;
        logRequest();
    }

    /**
     * This is to log the information into log.txt.
     * 
     * @exception IOException
     * 
     */
    public void logRequest() {
        try {
            // set the content of this log operation.
            String content = new String();
            content = Calendar.getInstance().getTime() + "\r\n\r\n" + "Request type: " + type + "\r\n\r\n"
                    + "Response code:\r\n\r\n" + response + "\r\n\r\n";

            // check if the file exist. Add the new data into that file if it
            // exist otherwise create a new file and add content.
            File f = new File("../log.txt");
            if (f.exists()) {
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f, true)));
                bw.write(content);
                bw.close();
            } else {
                PrintWriter pw = new PrintWriter(
                        new FileWriter(Configuration.DIRECTORY_OF_LOG_FILE + Configuration.LOG_FILE_NAME));
                pw.println(content);
                pw.close();
            }

        } catch (IOException e) {
            e.getStackTrace();
        }
    }

}

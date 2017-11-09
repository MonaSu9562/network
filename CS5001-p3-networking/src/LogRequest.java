import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

/**
 * This is to log the request to a file.
 * 
 * @author 170026060
 *
 */
public class LogRequest {
    private String data = null;
    private String type = null;
    private String response = null;

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

    public void logRequest() {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter("../log.txt"));
            pw.println(Calendar.getInstance().getTime() + "\r\n\r\n");
            pw.println("Request type: " + type + "\r\n\r\n");
            pw.println("Response code:\r\n\r\n" + response + "\r\n\r\n");
            pw.close();
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

}

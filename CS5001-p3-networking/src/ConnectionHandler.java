import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 
 */

/**
 * @author 170026060
 *
 */
public class ConnectionHandler extends Thread {

    // socket representing TCP/IP connection to Client
    private Socket conn;
    // get data from client on this input stream
    private InputStream is;
    // can send data back to the client on this output
    // stream
    private OutputStream os;
    // use buffered reader to read client data
    BufferedReader br;
    // path
    String directory;

    private PrintWriter pw;

    public ConnectionHandler(String directory, Socket conn) {
        this.directory = directory;
        this.conn = conn;
        try {
            is = conn.getInputStream();
            os = conn.getOutputStream();
            br = new BufferedReader(new InputStreamReader(is));
            pw = new PrintWriter(os, true);
        } catch (IOException ioe) {
            System.out.println("ConnectionHandler: " + ioe.getMessage());
        }
    }

    public void run() {
        System.out.println("new ConnectionHandler thread started .... ");
        try {
            responseToClient(getRequest(), directory);
        } catch (Exception e) {
            System.out.println("ConnectionHandler:run " + e.getMessage());
            cleanup();
        }
    }

    private String[] getRequest() {
        String[] request = null;
        try {
            // read the request header
            String line = br.readLine();
            request = line.split(" ");
            for (int i = 0; i < request.length; i++) {
                System.out.println(request[i]);
            }

        } catch (IOException e) {
            e.getStackTrace();
        }
        return request;
    }

    private void responseToClient(String[] request, String directory) {
        File f = new File(directory + request[1]);
        String fname = f.getName();

        String response = null;
        String protocol = request[2];
        String code = null;
        String length = String.valueOf(f.length());
        String type = null;
        String content = null;

        String ftype = fname.substring(fname.lastIndexOf(".") + 1);

        System.out.println(f.exists());
        System.out.println(f.getPath());

        if (!f.exists()) {
            code = " 404 Not Found";
        } else {
            type = getType(ftype);
            switch (request[0].toUpperCase()) {
            case "GET":
                code = " 200 OK";
                break;
            case "HEAD":
                code = " 200 OK";
                break;
            default:
                code = " 501 Not Implemented";
                break;
            }
        }

        // if (!f.exists()) {
        // code = " 404 Not Found";
        // }
        // if (Configuration.requireList.contains(request[0].toUpperCase())) {
        // switch (request[0].toUpperCase()) {
        // case "GET":
        // break;
        // case "HEAD":
        // break;
        // default:
        // }
        // code = " 200 OK";
        // } else {
        // code = " 501 Not Implemented";
        // }

        response = protocol + code + "\r\nServer: MySimpleServer written in Java 6\r\nContent-Length: " + length
                + "\r\nContent-Type: " + type + "\r\n\r\n" + content;
        System.out.println(response);

        pw.println(response);
        while(){};

    }

    private String getType(String ftype) {
        String type = null;
        switch (ftype) {
        case "txt":
            type = "text/html\r\n";
            break;
        case "html":
            type = "text/html\r\n";
            break;
        case "jpg":
            type = "image/jpg\r\n";
            break;
        case "jpeg":
            type = "image/jpeg\r\n";
            break;
        case "gif":
            type = "image/gif\r\n";
            break;
        case "png":
            type = "image/png\r\n";
            break;
        default:
            type = "unknown type\r\n";
            break;
        }
        return type;
    }

    private String getContent(String type, byte[] bytes) {

        return null;
    }

    private void cleanup() {
        System.out.println("ConnectionHandler: ... cleaning up and exiting ... ");
        try {
            br.close();
            is.close();
            conn.close();
        } catch (IOException ioe) {
            System.out.println("ConnectionHandler:cleanup " + ioe.getMessage());
        }
    }
}

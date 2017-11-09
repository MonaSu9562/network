import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * This class is to handle the connection between server and client.
 * 
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
    private BufferedReader br;
    // use this to response to client
    // private PrintWriter pw;
    // path
    private String directory;
    //
    private BufferedOutputStream bos;

    /**
     * The constructor of this class. This is to initialise.
     * 
     * @param directory
     *            the directory from which your server will serve documents to
     *            client.
     * 
     * @param conn
     *            the Socket.
     * 
     * @exception IOException
     */
    public ConnectionHandler(String directory, Socket conn) {
        this.directory = directory;
        this.conn = conn;
        try {
            is = conn.getInputStream();
            os = conn.getOutputStream();
            br = new BufferedReader(new InputStreamReader(is));
            // pw = new PrintWriter(os, true);
            bos = new BufferedOutputStream(os);
        } catch (IOException ioe) {
            System.out.println("ConnectionHandler: " + ioe.getMessage());
        }
    }

    /**
     * Run function.
     * 
     * @exception Exception
     */
    @Override
    public void run() {
        System.out.println("new ConnectionHandler thread started .... ");
        try {
            responseToClient(getRequest(), directory);
        } catch (Exception e) {
            System.out.println("ConnectionHandler:run " + e.getMessage());
            cleanup();
        }
    }

    /**
     * This is to get the request from client.
     * 
     * @exception IOException
     */
    private String[] getRequest() {
        String[] request = null;
        try {
            // read the request header
            String line = br.readLine();
            // show the request
            System.out.println(line);
            request = line.split(" ");
        } catch (IOException e) {
            e.getStackTrace();
        }
        return request;
    }

    /**
     * Response to client in this function.
     * 
     * @param request
     *            the request got from the client.
     * 
     * @param directory
     *            the directory from which your server will serve documents to
     *            client.
     * @throws IOException
     */
    private void responseToClient(String[] request, String directory) throws IOException {
        File f = new File(directory + request[1]);
        String fname = f.getName();

        String response = null;
        String header = null;
        String protocol = request[2];
        String code = null;
        String length = String.valueOf(f.length());
        String type = null;

        String ftype = fname.substring(fname.lastIndexOf(".") + 1);

        if (!f.exists()) {
            code = " 404 Not Found";
            header = protocol + code + "\r\nServer: MySimpleServer written in Java 6\r\nContent-Length: " + length
                    + "\r\nContent-Type: " + type + "\r\n";
            response = header;
            // System.out.println(response);
            // pw.println(response);
        } else {
            type = getType(ftype);
            switch (request[0].toUpperCase()) {
            case "GET":
                code = " 200 OK";
                header = protocol + code + "\r\nServer: MySimpleServer written in Java 6\r\nContent-Length: " + length
                        + "\r\nContent-Type: " + type + "\r\n";
                response = header + "\r\n" + getResponseContent(f);
                // System.out.println(header);
                // pw.println(header);
                // System.out.println(getResponseContent(f));
                // pw.println(getResponseContent(f));

                byte[] b = header.getBytes("UTF8");
                System.out.println(b);
                bos.write(b);
                bos.write(getResponseContent(f));
                bos.flush();
                conn.close();
                bos.close();
                break;
            case "HEAD":
                code = " 200 OK";
                header = protocol + code + "\r\nServer: MySimpleServer written in Java 6\r\nContent-Length: " + length
                        + "\r\nContent-Type: " + type + "\r\n";
                response = header;
                // System.out.println(response);
                // pw.println(response);
                break;
            default:
                code = " 501 Not Implemented";
                header = protocol + code + "\r\nServer: MySimpleServer written in Java 6\r\nContent-Length: " + length
                        + "\r\nContent-Type: " + type + "\r\n";
                response = header;
                // System.out.println(response);
                // pw.println(response);
                break;
            }
        }
        // System.out.println(response);
        // pw.println(response);

        // System.out.println(response);
        // byte[] b = response.getBytes("UTF8");
        // System.out.println(b);
        // pw.println(b);

        System.out.println(response);
        try {
            response.toString();
            byte[] b = response.getBytes("UTF8");
            System.out.println(b);
            bos.write(b);
            bos.flush();
            conn.close();
            bos.close();
        } catch (Exception e) {
            e.getStackTrace();
        }

        LogRequest log = new LogRequest(request[0], response);
    }

    /**
     * Get the content of requested file to the client.
     * 
     * @exception IOException
     */
    private byte[] getResponseContent(File f) {
        // String content = new String();
        // FileReader fr;
        // BufferedReader bufferedReader;
        //
        // try {
        // fr = new FileReader(f);
        // bufferedReader = new BufferedReader(fr);
        // // return the content of the argued file
        // String str = null;
        // while ((str = bufferedReader.readLine()) != null) {
        // str += "\r\n";
        // str = URLDecoder.decode(str, "UTF-8");
        // content += str;
        // }
        // } catch (IOException e) {
        // e.getStackTrace();
        // }
        // return content;

        byte[] content = null;

        // String content = null;
        try {
            // Read file stream by bytes
            FileInputStream fis = new FileInputStream(f);
            byte[] bytes = new byte[fis.available()];
            fis.read(bytes);
            fis.close();
            // content = new String(bytes, "UTF8");
            content = bytes;
        } catch (Exception e) {
            e.getMessage();
        }
        // System.out.println(content);
        return content;
    }

    /**
     * Get the output type of the selected file.
     * 
     * @param ftype
     *            the type of the file.
     * 
     * @return String the output type of the selected file.
     */
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

    /**
     * Clean up.
     * 
     * @exception IOException
     */
    private void cleanup() {
        System.out.println("ConnectionHandler: ... cleaning up and exiting ... ");
        try {
            br.close();
            is.close();
            os.close();
            // pw.flush();
            // pw.close();
            bos.flush();
            conn.close();
            bos.close();
        } catch (IOException ioe) {
            System.out.println("ConnectionHandler:cleanup " + ioe.getMessage());
        }
    }
}

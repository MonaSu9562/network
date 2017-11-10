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
    private BufferedOutputStream bos;
    // path
    private String directory;

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
            // execute the response procedure.
            responseToClient(getRequest(), directory);
            cleanup();
        } catch (Exception e) {
            System.out.println("ConnectionHandler:run " + e.getMessage());
            // clean up and exit if have exception.
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
            // show the request header
            System.out.println(line);
            // split the request
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
        System.out.println(fname);
        String ftype = fname.substring(fname.lastIndexOf(".") + 1);

        String response = null;
        String header = null;
        String protocol = request[2];
        String code = null;
        String length = String.valueOf(f.length());
        String type = getType(ftype);

        byte[] b = null; // this is to save the bytes form of the header

        switch (request[0].toUpperCase()) {
        case "GET":
            if (!f.exists()) {
                code = " 404 Not Found";
                header = protocol + code + "\r\nServer: MySimpleServer written in Java 6\r\nContent-Length: " + length
                        + "\r\nContent-Type: " + type + "\r\n";
                // change the form of header
                b = header.getBytes("UTF8");
                // send response to the client
                bos.write(b);

                response = header;
            } else {
                code = " 200 OK";
                header = protocol + code + "\r\nServer: MySimpleServer written in Java 6\r\nContent-Length: " + length
                        + "\r\nContent-Type: " + type + "\r\n";
                // change the form of header
                b = header.getBytes("UTF8");
                // send response to the client
                bos.write(b);
                bos.write(getResponseContent(f));

                response = header + "\r\n" + new String(getResponseContent(f), "UTF8");
            }
            break;

        case "HEAD":
            if (!f.exists()) {
                code = " 404 Not Found";
                header = protocol + code + "\r\nServer: MySimpleServer written in Java 6\r\nContent-Length: " + length
                        + "\r\nContent-Type: " + type + "\r\n";
                // change the form of header
                b = header.getBytes("UTF8");
                // send response to the client
                bos.write(b);

                response = header;
            } else {
                code = " 200 OK";
                header = protocol + code + "\r\nServer: MySimpleServer written in Java 6\r\nContent-Length: " + length
                        + "\r\nContent-Type: " + type + "\r\n";
                // change the form of header
                b = header.getBytes("UTF8");
                // send response to the client
                bos.write(b);

                response = header;
            }

            break;

        case "DELETE":
            if (!f.exists()) {
                System.out.println(fname + " undeleted.");

                code = " 204 No Content";
                header = protocol + code + "\r\nServer: MySimpleServer written in Java 6\r\nContent-Length: " + length
                        + "\r\nContent-Type: " + type + "\r\n";
                // change the form of header
                b = header.getBytes("UTF8");
                // send response to the client
                bos.write(b);

                response = header;
            } else {
                code = " 200 OK";
                header = protocol + code + "\r\nServer: MySimpleServer written in Java 6\r\nContent-Length: " + length
                        + "\r\nContent-Type: " + type + "\r\n";
                // delete the file
                f.delete();
                System.out.println(fname + " deleted.");
                // change the form of header
                b = header.getBytes("UTF8");
                // send response to the client
                bos.write(b);
                bos.write(getResponseContent(f));

                response = header + "\r\n" + getResponseContent(f);
            }
            break;

        default:
            code = " 501 Not Implemented";
            header = protocol + code + "\r\nServer: MySimpleServer written in Java 6\r\nContent-Length: " + length
                    + "\r\nContent-Type: " + type + "\r\n";
            // change the form of header
            b = header.getBytes("UTF8");
            // send response to the client
            bos.write(b);

            response = header;
            break;
        }

        // test if the response is correct
        System.out.println(response);
        // log the information into log.txt
        LogRequest log = new LogRequest(request[0], response);
    }

    /**
     * Get the content of requested file to the client.
     * 
     * @exception IOException
     */
    private byte[] getResponseContent(File f) {

        byte[] content = null;

        try {
            // Read file stream by bytes
            FileInputStream fis = new FileInputStream(f);
            byte[] bytes = new byte[fis.available()];
            fis.read(bytes);
            fis.close();
            content = bytes;
        } catch (Exception e) {
            e.getMessage();
        }
        return content;
    }

    /**
     * Get the asked form of output type of the selected file.
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
            bos.flush();
            conn.close();
            bos.close();
            br.close();
            is.close();
            os.close();
        } catch (IOException ioe) {
            System.out.println("ConnectionHandler:cleanup " + ioe.getMessage());
        }
    }
}

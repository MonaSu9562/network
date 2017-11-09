import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class is the server.
 * 
 * @author 170026060
 *
 */
public class Server {

    private ServerSocket ss;

    /**
     * The constructor of this class. This is to initialise.
     * 
     * @param directory
     *            the directory from which your server will serve documents to
     *            client.
     * 
     * @param port
     *            the port of the server you listened.
     * 
     * @exception IOException
     */
    public Server(String directory, int port) {
        try {
            ss = new ServerSocket(port);
            System.out.println("Server started ... listening on port " + port + " ...");
            while (true) {
                Socket conn = ss.accept();
                System.out.println("Server got new connection request from " + conn.getInetAddress());
                ConnectionHandler ch = new ConnectionHandler(directory, conn);
                ch.start();
            }
        } catch (IOException ioe) {
            System.out.println("Ooops " + ioe.getMessage());
        }
    }
}

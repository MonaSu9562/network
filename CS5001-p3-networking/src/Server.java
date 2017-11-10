import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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
        // create a thread pool to limit the number of threads.
        Executor ec = Executors.newFixedThreadPool(Configuration.MAX_NUM_OF_THREADS);
        try {
            ss = new ServerSocket(port);
            System.out.println("Server started ... listening on port " + port + " ...");
            // listen to the socket persistently.
            while (true) {
                Socket conn = ss.accept();
                System.out.println("Server got new connection request from " + conn.getInetAddress());
                // new a connection handler.
                ConnectionHandler ch = new ConnectionHandler(directory, conn);
                // start this thread.
                ec.execute(ch);
            }
        } catch (IOException ioe) {
            System.out.println("Server: " + ioe.getMessage());
        }
    }
}

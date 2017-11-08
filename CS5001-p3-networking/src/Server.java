import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket ss;

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

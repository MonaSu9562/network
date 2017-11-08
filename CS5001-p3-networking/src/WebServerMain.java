/**
 * 
 */

/**
 * @author 170026060
 *
 */
public class WebServerMain {
    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            // The directory from which your server will serve documents to
            // clients.
            String directory = args[0];
            // The port of the server you listened.
            int port = Integer.parseInt(args[1]);

            // new WebServerMain(directory, port);
            Server s = new Server(directory, port);
        } catch (Exception e) {
            System.out.println("Usage: java WebServerMain <document_root> <port>");
        }
    }
}

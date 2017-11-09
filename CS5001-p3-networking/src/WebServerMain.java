/**
 * This program implements a web server that could support and response
 * correctly to HEAD and GET requests. It could also return HTML documents
 * requested by a client.
 */

/**
 * This class is the WebServerMain class, it contains the main method.
 * 
 * @author 170026060
 *
 */
public class WebServerMain {
    /**
     * The main function of this project.
     * 
     * @param args
     *            the input value, the first one is the directory from which
     *            your server will serve documents to clients. And the second
     *            input is the port of the server you listened.
     * 
     * @exception Exception
     *                wrong input form.
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

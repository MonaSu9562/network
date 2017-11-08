/**
 * 
 */

/**
 * @author 170026060
 *
 */
public class WebServerMain {
    // public class WebServerMain implements Runnable {
    // ServerSocket ss = null;
    // Socket client = null;
    // InputStreamReader isr = null;
    // BufferedReader br = null;
    // PrintWriter pw = null;
    // String directory;
    // int port;
    //
    // public WebServerMain(String directory, int port) {
    // this.directory = directory;
    // this.port = port;
    // try {
    // ss = new ServerSocket(port);
    // if (ss == null) {
    // System.exit(1);
    // new Thread(this).start();
    // }
    // client = ss.accept();
    // } catch (IOException e) {
    //
    // }
    // }
    //
    // @Override
    // public void run() {
    // // TODO Auto-generated method stub
    // while (true) {
    // try {
    // client = ss.accept();
    // if (client != null) {
    // try {
    // System.out.println("connected!");
    //
    // isr = new InputStreamReader(client.getInputStream());
    // br = new BufferedReader(isr);
    // pw = new PrintWriter(client.getOutputStream(), true);
    //
    // String line = br.readLine();
    // System.out.println("line: " + line);
    // // *************don't understand why -5???
    // String resource = line.substring(line.indexOf('/'), line.indexOf('/') -
    // 5);
    // System.out.println("the resource you request is:" + resource);
    // // ***********
    // resource = URLDecoder.decode(resource, "UTF-8");
    //
    // String method = new StringTokenizer(line).nextElement().toString();
    // System.out.println("the request method you send is: " + method);
    //
    // // while or if ?
    // while ((line = br.readLine()) != null) {
    // if (line.equals("")) {
    // break;
    // }
    // System.out.println("the Http Header is : " + line);
    // }
    //
    // if ("post".equals(method.toLowerCase())) {
    // System.out.println("the post request body is: " + br.readLine());
    // }
    //
    // if ("get".equals(method.toLowerCase())) {
    //
    // }
    //
    // if ("head".equals(method.toLowerCase())) {
    //
    // }
    //
    // } catch (Exception e) {
    // System.out.println("wrong!" + e.getLocalizedMessage());
    // }
    //
    // }
    //
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }
    //
    // }

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

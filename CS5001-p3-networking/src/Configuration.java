import java.util.ArrayList;

/**
 * 
 */

/**
 * @author 170026060
 *
 */
public abstract class Configuration {

    public static final String exitString = "exit";
    public static final int defaultPort = 12345;
    public static final byte ackByte = 1;
    public static final ArrayList<String> requireList = new ArrayList<String>() {
        {
            add("GET");
            add("HEAD");
        }
    };
}
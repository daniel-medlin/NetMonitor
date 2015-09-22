package NetMon;

import java.net.*;
import java.io.*;

/**
 * @author Daniel
 */
public class RemoteIP {

    public static boolean getIP(String hostname, boolean website) {
        boolean result = false;
        if (website) {
            Socket socket = null;

            try {
                socket = new Socket(hostname, 80); //initially build a socket to a website.
                result = true;
                socket.close();
            } catch (IOException ex) { //if socket fails...
                result = isReachableByPing(hostname);
            }
        } else {
            result = isReachableByPing(hostname);
        }
        return result;

    }

    public static boolean isReachableByPing(String host) { //from best answer on stackOverflow http://stackoverflow.com/questions/18321118/best-alternative-for-inetaddress-getbynamehost-isreachabletimeout
        try {
            String cmd = "";

            if (System.getProperty("os.name").startsWith("Windows")) {
                cmd = "ping -n 1 " + host; // For Windows
            } else {
                cmd = "ping -c 1 " + host; // For Linux and OSX
            }
            Process myProcess = Runtime.getRuntime().exec(cmd);
            myProcess.waitFor();

            return myProcess.exitValue() == 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}

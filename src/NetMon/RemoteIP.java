package NetMon;

import java.net.*;
import java.io.*;

/**
 * @author Daniel
 */

public class RemoteIP {
    public static String getIP(String hostname){
        Socket socket = null;
        boolean online = false;
        String result = null;
        
        try {
            try {
                socket = new Socket(hostname, 80); //initially build a socket to a website.
                online = true;
                result = "Online";
            } catch (IOException ex) { //if socket fails...
                try {
                    online = InetAddress.getByName(hostname).isReachable(10000); //check if reachable
                    result = "Online";
                } catch (IOException ex1) {
                    result = "Offline";
                }
            }
            
        } 
        finally {
            if (socket != null) try { socket.close(); } catch(IOException e){}
        }
        return result;
        
    }
    
}

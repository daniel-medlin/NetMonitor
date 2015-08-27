package NetMon; 

import java.net.*;
import java.io.*;

/**
 * @author Daniel
 */ 

public class RemoteIP {
    public static boolean getIP(String hostname, boolean website){
        boolean online = false;
        boolean result = false;
        if (website){
            Socket socket = null;

            try {
                try {
                    socket = new Socket(hostname, 80); //initially build a socket to a website.
                    online = true;
                    result = true;
                } catch (IOException ex) { //if socket fails...
                    try {
                        online = InetAddress.getByName(hostname).isReachable(1000); //check if reachable
                        result = true;
                    } catch (IOException ex1) {
                        result = false;
                    }
                }

            } 
            finally {
                if (socket != null) try { socket.close(); } catch(IOException e){}
            }
        } else try {
                        online = InetAddress.getByName(hostname).isReachable(1000); //check if reachable
                        result = true;
                    } catch (IOException ex1) {
                        result = false;
                    }
        return result;
        
    }
    
}

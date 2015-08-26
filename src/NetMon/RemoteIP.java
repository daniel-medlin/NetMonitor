/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NetMon;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Daniel
 */
public class RemoteIP {
    public static String getIP(String hostname){
        InetAddress address = null;
        String IP = null;
        try{
            address = InetAddress.getByName(hostname);
            IP = address.getHostAddress();
        }
        catch (UnknownHostException e){
                        //System.out.println(e);
                        IP = "Offline";
                    }
        return IP;
    }
    
}

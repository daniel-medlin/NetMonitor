package NetMon;
import java.util.*;
/**
 * @author Daniel
 */
public class NetMon {

    public static void main(String[] args) {
        Scanner k = new Scanner(System.in);
        boolean loop=true;
        
        while(loop){
            System.out.print("Enter an address to check or 'exit': ");
            String remoteHost=k.next();
            if (remoteHost.equalsIgnoreCase("exit"))
                System.exit(0);
            System.out.println(remoteHost + ": " + RemoteIP.getIP(remoteHost));
            System.out.println("Local IP Address: " + LocalIP.getIP());
        }
        System.exit(0);
        
    }
}

package NetMon;
import java.util.*;
import javax.swing.JOptionPane;
/**
 * @author Daniel
 */
public class NetMon {

    public static void main(String[] args) {
        //Scanner k = new Scanner(System.in);
        boolean loop=true;
        boolean online=false;
        boolean prevStatus=false;
        
        SysTray.buildIcon(); //build tray icon
        String remoteHost=JOptionPane.showInputDialog(null,"Enter a Hostname or Web Address.",
                null,JOptionPane.OK_CANCEL_OPTION); //User input for address.
        System.err.println("Hostname: " + remoteHost);
        if (remoteHost==null){
                SysTray.showMessage("Notice", "No hostname was entered.  Exiting now.");
            timeout(3000);
            System.exit(0);
            
        }
        while(loop){ //main loop
            online=RemoteIP.getIP(remoteHost); //online-true offline-false
            System.err.println(remoteHost + " connectivity status: " + online);
            if (online && !prevStatus){ //device is online but was offline last I checked...or this is the first run.
                System.err.println("Device is online but prevState is offline...Rechecking");
                timeout(5000);
                if (RemoteIP.getIP(remoteHost)){
                    System.err.println(remoteHost + " is online");
                    prevStatus=true;
                    SysTray.showMessage(remoteHost, "Online");
                }
            } else if (!online && prevStatus){ //device is offline but was online last I checked...
                System.err.println("Device is offline but prevState is online...Rechecking");
                timeout(5000);
                if (!RemoteIP.getIP(remoteHost)){
                    System.err.println(remoteHost + " is Offline");
                    prevStatus=false;
                    SysTray.showMessage(remoteHost, "Offline");
                }
            }
            timeout(5000);//check every 5 secs.
        } //end main loop
        System.exit(0);
    }
    
    public static void timeout(int ms){
        try{Thread.sleep(ms);}
        catch (Exception e){}
    }
}

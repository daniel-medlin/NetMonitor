package NetMon; 
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
/**
 * @author Daniel
 */
public class NetMon {

    public static void main(String[] args) {
        JCheckBox checkbox = new JCheckBox("This is a web address."); //checkbox for user to tell if website or local.
        Object[] params = {"Enter a Hostname or Web Address.", checkbox};
        String remoteHost=JOptionPane.showInputDialog(null,params,
                null,JOptionPane.OK_CANCEL_OPTION); //User input for address.
        boolean website = checkbox.isSelected();
        System.err.println("Hostname: " + remoteHost);
        System.err.println("User input is a website: " + website);
        if (remoteHost==null){
                SysTray.showMessage("Notice", "No hostname was entered.  Exiting now.");
            timeout(3000);
            System.exit(0);
        }
        SysTray.buildIcon(remoteHost); //build tray icon
        mainLoop(remoteHost, website);
        System.exit(0);
    }
    
    public static void timeout(int ms){
        try{Thread.sleep(ms);}
        catch (Exception e){}
    }
    
    public static void mainLoop(String remoteHost, boolean website){
        boolean loop=true;
        boolean online=false;
        boolean prevStatus=false;
        
        while(loop){ //main loop
            online=RemoteIP.getIP(remoteHost,website); //online-true offline-false
            System.err.println(remoteHost + " connectivity status: " + online);
            if (online && !prevStatus){ //device is online but was offline last I checked...or this is the first run.
                System.err.println("Device is online but prevState is offline...Rechecking");
                timeout(5000);
                if (RemoteIP.getIP(remoteHost,website)){
                    System.err.println(remoteHost + " is online");
                    prevStatus=true;
                    SysTray.showMessage(remoteHost, "Online");
                }
            } else if (!online && prevStatus){ //device is offline but was online last I checked...
                System.err.println("Device is offline but prevState is online...Rechecking");
                timeout(5000);
                if (!RemoteIP.getIP(remoteHost,website)){
                    System.err.println(remoteHost + " is Offline");
                    prevStatus=false;
                    SysTray.showMessage(remoteHost, "Offline");
                }
            }
            timeout(5000);//check every 5 secs.
        } //end main loop
    }
}

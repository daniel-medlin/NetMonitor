package NetMon;

import static NetMon.NetMon.timeout;
import javax.swing.*;

/**
 *
 * @author Daniel
 */
public class Daemon {
    public static MainLoop main;
    public static String remoteHost;
    public static boolean website;
    
    
    public static void init(MainLoop m){
        main=m;
    }

    public static ReturningValues userInput() {
        ReturningValues rv = new ReturningValues();
        JCheckBox checkbox = new JCheckBox("This is a web address."); //checkbox for user to tell if website or local.
        Object[] params = {"Enter a Hostname or Web Address.", checkbox};
        rv.setRemoteHost(JOptionPane.showInputDialog(null, params,
                null, JOptionPane.OK_CANCEL_OPTION)); //User input for address.
        rv.setWebsite(checkbox.isSelected());
        remoteHost=rv.getRemoteHost();
        website=rv.getWebsite();
        System.err.println("Hostname: " + rv.getRemoteHost());
        System.err.println("User input is a website: " + rv.getWebsite());
        if (rv.getRemoteHost() == null) {
            SysTray.showMessage("Notice", "No hostname was entered.  Exiting now.");
            timeout(3000);
            System.err.println("No user input");
            System.exit(1);
        }
        return rv;
    }

    public void start(String host) {
        if (host=="previous_host")
            host=remoteHost;
        main.running(true, website, host);//TODO method for starting the main loop with required fields
        System.err.println("Starting");
    }

    public void stop() {
        main.running(false, website, remoteHost);//TODO method for stopping the main loop
        System.err.println("Stopping");
        //userInput();
    }

    public static void changeHost(boolean website, String host) {
        //TODO method for changing current remote host and restart scanning.
    }

}


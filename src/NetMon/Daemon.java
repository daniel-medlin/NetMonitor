package NetMon;

import static NetMon.NetMon.timeout;
import javax.swing.*;

/**
 *
 * @author Daniel
 */
public class Daemon {

    //public static MainLoop main;
    public static SysTray tray;
    public static String remoteHost;
    public static boolean website;
    public static boolean run;
    public static MainLoop main;

    //public static void init(MainLoop m, SysTray t) {
    public static void init(SysTray t) {
        //main = m;
        tray = t;
    }

    public static ReturningValues userInput() { //get user input and return values using the ReturningValues Class.
        ReturningValues rv = new ReturningValues();
        JCheckBox checkbox = new JCheckBox("This is a web address."); //checkbox for user to tell if website or local.
        Object[] params = {"Enter a Hostname or Web Address.", checkbox};
        rv.setRemoteHost(JOptionPane.showInputDialog(null, params,
                null, JOptionPane.OK_CANCEL_OPTION)); //User input for address.
        rv.setWebsite(checkbox.isSelected());
        remoteHost = rv.getRemoteHost();
        website = rv.getWebsite();
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

    public void start(String host) { //run with new host

        if (!run) {
            if (host != null) {
                remoteHost = host;
            }
            System.err.println("Starting");
            run = true;
            main = new MainLoop(true, website, remoteHost);
            main.start();
            //main.run();

        } else {
            System.err.println("Scan is already running.");
        }
    }

    public void stop() {
        if (run) {
            System.err.println("Stopping");
            main.run = false;
            run = false;
            //main.stop();
        } else {
            System.err.println("Scan is already stopped.");
        }
    }

    public void changeHost() {
        if (run) {
            stop();
        }

        SysTray.tray.remove(SysTray.icon);//attempt to remove tray Icon
        ReturningValues rv = userInput();
        remoteHost = rv.getRemoteHost();
        tray.buildIcon(rv.getRemoteHost());
        start(remoteHost);
    }

}

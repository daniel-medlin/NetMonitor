package NetMon;

import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;

/**
 *
 * @author Daniel
 */
public class Daemon {

    //public static MainLoop main;
    public static SysTray tray;
    public static String remoteHost;
    public static boolean website;
    public static String deviceName;
    public static boolean run;
    public static MainLoop main;

    //public static void init(MainLoop m, SysTray t) {
    public static void init(SysTray t) {
        //main = m;
        tray = t;
    }

    public static ReturningValues userInput() { //get user input and return values using the ReturningValues Class.
        ReturningValues rv = new ReturningValues();
        JTextField name = new JTextField();
            name.setToolTipText("Enter a name for the device or website you want to monitor(ie.: Google)");
        JTextPane hostLabel = new JTextPane();
            hostLabel.setEditorKit(new HTMLEditorKit());
            hostLabel.setText("<html><span color='red'>*</span>Hostname or Web Address:</html>");
            hostLabel.setOpaque(false);//makes background of the label transparent.
            hostLabel.setFocusable(false);
        JTextField host = new JTextField();
            host.setToolTipText("Enter the address fro the device you want to monitor (ie.: www.google.com or 192.168.0.1)");
        JCheckBox checkbox = new JCheckBox("This is a web address."); //checkbox for user to tell if website or local.
        Object[] params = {
            "Display Name:", name, "\n",
            checkbox,
            hostLabel, host
        };
        JOptionPane.showConfirmDialog(null, params, "Network Monitor", JOptionPane.OK_CANCEL_OPTION); //User input for address.

        rv.setWebsite(checkbox.isSelected());

        rv.setRemoteHost(host.getText());

        if (!name.getText().equals("")) {
            rv.setName(name.getText());

        } else {
            rv.setName(null);
        }
        website = rv.getWebsite();
        remoteHost = rv.getRemoteHost();
        deviceName = rv.getName();
        System.err.println("User input is a website: " + website);
        System.err.println("Hostname: " + remoteHost);
        System.err.println("Device Name: " + deviceName);

        if (remoteHost.equals("")) {
            JOptionPane.showMessageDialog(null, "Error: No hostname was entered.  Exiting now.", "Error", JOptionPane.ERROR_MESSAGE);
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
            main = new MainLoop(true, website, remoteHost, deviceName);
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
        tray.buildIcon(rv.getRemoteHost(), rv.getName());
        start(remoteHost);
    }

    public static void timeout(int ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception e) {
        }
    }

}

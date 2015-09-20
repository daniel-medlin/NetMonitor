package NetMon;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.plaf.metal.MetalIconFactory;

/**
 * @author Daniel
 */
public class SysTray {

    public static Daemon daemon;
    public static SystemTray tray;
    public static TrayIcon icon;
    public static String hostName;
    public static String msgBody;
    public static TrayIcon myIcon;
    public static String title;

    public static void init(Daemon d) {
        daemon = d;
        //tray=t;
    }

    public void buildIcon(String host, String name) {
        if (name == null)
            name=host;
        title = name;
        hostName = host;
        if (SystemTray.isSupported()) {
            tray = SystemTray.getSystemTray();
            icon = new TrayIcon(getImage(), title, createPopupMenu());
            myIcon = icon;
            icon.addActionListener((ActionEvent e) -> {
                System.err.println(e);
                JOptionPane.showMessageDialog(null, title + "\n" + msgBody);
            } //Creates a click listener for Tray Icon
            ); //end listener);
            try {
                tray.add(icon);
            } catch (AWTException e) {
                System.err.println("System Tray Icon could not be added.");
            }
        }
    }

    public static void showMessage(String Title, String Body) {
        msgBody = Body;
        myIcon.displayMessage(Title, Body, TrayIcon.MessageType.NONE);
    }

    private static Image getImage() throws HeadlessException {
        Icon defaultIcon = MetalIconFactory.getTreeComputerIcon();
        Image img = new BufferedImage(defaultIcon.getIconWidth(),
                defaultIcon.getIconHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        defaultIcon.paintIcon(new Panel(), img.getGraphics(), 0, 0);

        return img;
    }

    private static PopupMenu createPopupMenu() throws HeadlessException {
        PopupMenu menu = new PopupMenu();

        MenuItem exit = new MenuItem("Exit");//menu item button
        exit.addActionListener((ActionEvent e) -> {
            System.err.println(e);
            System.exit(0); //action on click
        } //start listener
        );//end listener
        MenuItem stop = new MenuItem("Stop"); //new menu item same setup
        stop.addActionListener((ActionEvent e) -> {
            System.err.println(e);
            daemon.stop();
            showMessage(title, "Stopped monitoring");
        });
        MenuItem start = new MenuItem("Start"); //new menu item same setup
        start.addActionListener((ActionEvent e) -> {
            System.err.println(e);
            daemon.start(null);
        });
        MenuItem changeHost = new MenuItem("Change Host");
        changeHost.addActionListener((ActionEvent e) -> {
            System.err.println(e);
            daemon.changeHost();

        });
        MenuItem about = new MenuItem("About");
        about.addActionListener((ActionEvent e) -> {
            System.err.println(e);
            JOptionPane.showMessageDialog(null, "This software was written by b00st3d on HackForums\n\nAll options are common sense with stop halting the scan, \nstart restarting the scan on the previous host, \nand Change Host allowing a new host entry.");
        });

        //create menu here
        menu.add(start);
        menu.add(stop);
        menu.add(changeHost);
        menu.addSeparator();
        menu.add(about);
        menu.add(exit);

        return menu;
    }
}

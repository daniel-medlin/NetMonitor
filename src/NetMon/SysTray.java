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

    public static MainLoop mainLoop;
    public static Daemon daemon;

    public static void init(MainLoop m, Daemon d) {
        mainLoop = m;
        daemon = d;
    }

    public void buildIcon(String host) {
        String title = host;
        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();
            TrayIcon icon = new TrayIcon(getImage(), title, createPopupMenu());
            icon.addActionListener(new ActionListener() { //Creates a click listener for Tray Icon
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(null, title);
                }
            }); //end listener);
            try {
                tray.add(icon);
            } catch (AWTException e) {
                System.err.println("System Tray Icon could not be added.");
            }
        }
    }

    public static void showMessage(String Title, String Body) {
        TrayIcon[] icon = SystemTray.getSystemTray().getTrayIcons();
        icon[0].displayMessage(Title, Body, TrayIcon.MessageType.NONE); //create a message.
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
        Daemon d = new Daemon();

        MenuItem exit = new MenuItem("Exit");//menu item button
        exit.addActionListener(new ActionListener() { //start listener
            public void actionPerformed(ActionEvent e) {
                System.exit(0); //action on click
            }
        });//end listener
        MenuItem start = new MenuItem("Start"); //new menu item same setup
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                daemon.start("previous_host");
            }
        });
        MenuItem stop = new MenuItem("Stop"); //new menu item same setup
        stop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                daemon.stop();
            }
        });
        menu.add(exit); //create menu here
        menu.add(start);
        menu.add(stop);

        return menu;
    }
}

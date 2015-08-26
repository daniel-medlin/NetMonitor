package NetMon;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.plaf.metal.MetalIconFactory;

/**
 * @author Daniel
 */
public class SysTray {
   public static void main(String[] args) throws Exception {
      TrayIcon icon = new TrayIcon(getImage(), "Java application as a tray icon", //Title on hover
            createPopupMenu());
      icon.addActionListener(new ActionListener() { //Creates a click listener for Tray Icon
         public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "Hey, you activated me!"); 
         }
      }); //end listener
      SystemTray.getSystemTray().add(icon); 

      Thread.sleep(1000);

      icon.displayMessage("Attention", "Please click here", 
            TrayIcon.MessageType.INFO);//popup message
   }
   private static Image getImage() throws HeadlessException {
      Icon defaultIcon = MetalIconFactory.getTreeHardDriveIcon();
      Image img = new BufferedImage(defaultIcon.getIconWidth(), 
            defaultIcon.getIconHeight(), BufferedImage.TYPE_4BYTE_ABGR);
      defaultIcon.paintIcon(new Panel(), img.getGraphics(), 0, 0);

      return img;
   }

   private static PopupMenu createPopupMenu() throws HeadlessException {
      PopupMenu menu = new PopupMenu();

      MenuItem exit = new MenuItem("Exit");//menu item button
      exit.addActionListener(new ActionListener() { //start listener
         public void actionPerformed(ActionEvent e) {
            System.exit(0); //action on click
         }
      });//end listener
      MenuItem hello = new MenuItem("Hello"); //new menu item same setup
      hello.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              JOptionPane.showMessageDialog(null, "Hello!");
          }
      });
      menu.add(exit); //create menu here
      menu.add(hello);

      return menu;
   }
}
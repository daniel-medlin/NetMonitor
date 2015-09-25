package NetMon;

/**
 * @author Daniel
 */
public class NetMon {

    public static void main(String[] args) {
        SysTray tray = new SysTray();
        Daemon daemon = new Daemon();
        ReturningValues rv = null;
        boolean valid = false;

        daemon.init(tray);
        tray.init(daemon);

        while (!valid) {
            rv = Daemon.userInput(true); //get start values for user input.
            if (!rv.getRemoteHost().equals("")) //check userinput if user fails to enter a hostname, recall the userInput.
            {
                valid = true;
            }
        }
        tray.buildIcon(rv.getRemoteHost(), rv.getName());
        daemon.start(rv.getRemoteHost());
    }
}

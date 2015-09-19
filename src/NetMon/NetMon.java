package NetMon;

/**
 * @author Daniel
 */
public class NetMon {

    public static void main(String[] args) {
        SysTray tray = new SysTray();
        Daemon daemon = new Daemon();

        daemon.init(tray);
        tray.init(daemon);

        ReturningValues rv = Daemon.userInput(); //get start values for user input.
        tray.buildIcon(rv.getRemoteHost(), rv.getName());
        daemon.start(rv.getRemoteHost());
    }
}

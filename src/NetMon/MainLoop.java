package NetMon;

import static NetMon.NetMon.timeout;

/**
 *
 * @author Daniel
 */
public class MainLoop {

    public static boolean run;

    public void running(boolean running, boolean website, String remoteHost) {
        run = running;
        boolean online;
        boolean prevStatus = false;
        while (true) { //infinite loop to run until program exits
            while (run) { //main loop
                online = RemoteIP.getIP(remoteHost, website); //online-true offline-false
                System.err.println(remoteHost + " connectivity status: " + online);
                if (online && !prevStatus) { //device is online but was offline last I checked...or this is the first run.
                    System.err.println("Device is online but prevState is offline...Rechecking");
                    timeout(5000);
                    if (RemoteIP.getIP(remoteHost, website)) {
                        System.err.println(remoteHost + " is online");
                        prevStatus = true;
                        SysTray.showMessage(remoteHost, "Online");
                    }
                } else if (!online && prevStatus) { //device is offline but was online last I checked...
                    System.err.println("Device is offline but prevState is online...Rechecking");
                    timeout(5000);
                    if (!RemoteIP.getIP(remoteHost, website)) {
                        System.err.println(remoteHost + " is Offline");
                        prevStatus = false;
                        SysTray.showMessage(remoteHost, "Offline");
                    }
                }
                timeout(5000);//check every 5 secs.
            } //end main loop
        }

    }

}

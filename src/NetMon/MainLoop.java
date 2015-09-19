package NetMon;

import static NetMon.Daemon.timeout;

/**
 *
 * @author Daniel
 */
class MainLoop implements Runnable {

    private Thread t;
    public boolean run;
    private boolean prevStatus = false;
    private boolean web;
    private String host;
    private String name;

    MainLoop(boolean go, boolean website, String remoteHost, String deviceName) {
        run = go;
        web = website;
        host = remoteHost;
        if (deviceName != null) {
            name = deviceName;
        } else {
            name = host;
        }
    }

    public void run() {
        boolean online;
        boolean first = true;
        while (run) {
            online = RemoteIP.getIP(host, web); //online-true offline-false
            System.err.println(host + " connectivity status: " + online);
            if (online && !prevStatus) { //device is online but was offline last I checked...or this is the first run.
                System.err.println("Device is online but prevState is offline...Rechecking");
                timeout(5000);
                if (RemoteIP.getIP(host, web)) {
                    System.err.println(host + " is online");
                    prevStatus = true;
                    SysTray.showMessage(name, "Online");
                }
            } else if (!online && prevStatus) { //device is offline but was online last I checked...
                System.err.println("Device is offline but prevState is online...Rechecking");
                timeout(5000);
                if (!RemoteIP.getIP(host, web)) {
                    System.err.println(host + " is Offline");
                    prevStatus = false;
                    SysTray.showMessage(name, "Offline");
                }
            }
            if (first) {
                if (!online) {
                    SysTray.showMessage(name, "Offline");
                    first = false;
                }
            }
            timeout(5000);//check every 5 secs.

        }
    }

    public void start() {
        if (t == null) {
            System.err.println("Scanning " + host);
            {
                t = new Thread(this, host);
                t.start();
            }
        }
    }

    public void stop() {
        run = false;
    }

}

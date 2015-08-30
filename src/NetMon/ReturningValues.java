package NetMon;

/**
 *
 * @author Daniel
 */
public class ReturningValues {

    private boolean website;
    private String remoteHost;

    public void setWebsite(boolean web) {
        website = web;
    }

    public boolean getWebsite() {
        return website;
    }

    public void setRemoteHost(String host) {
        remoteHost = host;
    }

    public String getRemoteHost() {
        return remoteHost;
    }

}

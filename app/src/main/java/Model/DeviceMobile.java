package Model;

import android.content.Context;
import android.net.wifi.ScanResult;

import java.util.List;

public class DeviceMobile {

    public enum IFACE{
        IF_BLUETOOTH,
        IF_WIFI,
        IF_5G
    }

    public enum IDCONTEXT{
        TROUGHPUT {
            @Override
            public String toString() {
                return "Troughput";
            }
        },
        POWERSAVE {
            @Override
            public String toString() {
                return "Powersave";
            }
        },
        COVERAGE {
            @Override
            public String toString() {
                return "Coverage";
            }
        }
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    String hostname;

    public IDCONTEXT getCurrentContext() {
        return currentContext;
    }

    public void setCurrentContext(IDCONTEXT currentContext) {
        this.currentContext = currentContext;
    }

    IDCONTEXT currentContext;

    public IFACE getBestInterface() {
        return bestInterface;
    }

    public void setBestInterface(IFACE bestInterface) {
        this.bestInterface = bestInterface;
    }

    IFACE bestInterface;

    float speed;
    boolean display;
    long bandwith;

    public int getAcConnector() {
        return acConnector;
    }

    public void setAcConnector(int acConnector) {
        this.acConnector = acConnector;
    }

    int acConnector;
    double powerlevel;

    WirelessNet current_wifinet;

    public List<WirelessNet> getWifiNeighrs() {
        return this.wifiNeighrs;
    }

    public void setWifiNeighrs(List<WirelessNet> wifiNeighrs) {
        this.wifiNeighrs = wifiNeighrs;
    }

    List<WirelessNet> wifiNeighrs;

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public boolean getDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    public long getBandwith() {
        return bandwith;
    }

    public void setBandwith(long bandwith) {
        this.bandwith = bandwith;
    }

    public double getPowerlevel() {
        return powerlevel;
    }

    public void setPowerlevel(double powerlevel) {
        this.powerlevel = powerlevel;
    }


}

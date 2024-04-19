package Model;

import android.content.Context;
import android.net.wifi.ScanResult;

import java.util.List;

public class DeviceMobile {

    public DeviceMobile() {
        this.current_5G = new WirelessNet();
        this.current_wifinet = new WirelessNet();
        this.current_bluetooth = new WirelessNet();
    }

    public enum IFACE{
        IF_BLUETOOTH {
            @Override
            public String toString() {
                return "Bluetooth";
            }
        },
        IF_WIFI{
            @Override
            public String toString() {
                return "Wi-Fi";
            }
        },
        IF_5G{
            @Override
            public String toString() {
                return "5G";
            }
        }
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

    public WirelessNet getCurrent_bluetooth() {
        return current_bluetooth;
    }

    public void setCurrent_bluetooth(WirelessNet current_bluetooth) {
        this.current_bluetooth = current_bluetooth;
    }

    public WirelessNet getCurrent_wifinet() {
        return current_wifinet;
    }

    public void setCurrent_wifinet(WirelessNet current_wifinet) {
        this.current_wifinet = current_wifinet;
    }

    public WirelessNet getCurrent_5G() {
        return current_5G;
    }

    public void setCurrent_5G(WirelessNet current_5G) {
        this.current_5G = current_5G;
    }

    WirelessNet current_bluetooth;
    WirelessNet current_wifinet;
    WirelessNet current_5G;

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

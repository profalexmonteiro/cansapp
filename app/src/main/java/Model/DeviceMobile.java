package Model;

import android.content.Context;
import android.net.wifi.ScanResult;

import java.util.List;

public class DeviceMobile {

    float speed;
    boolean display;
    long bandwith;
    double powerlevel;

    List<ScanResult> wifiNeighrs;

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

    public List<ScanResult> getWifiNeihgbohrs() {
        return wifiNeighrs;
    }

    public void setWifiNeighrs(List<ScanResult> wifiNeighrs) {
        this.wifiNeighrs = wifiNeighrs;
    }







}

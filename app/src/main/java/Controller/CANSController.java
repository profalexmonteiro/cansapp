package Controller;

import static android.content.Context.LOCATION_SERVICE;


import static Model.DeviceMobile.IDCONTEXT.*;
import static Model.DeviceMobile.IFACE.*;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.PowerManager;
import android.util.Log;

import androidx.core.app.NotificationCompatSideChannelService;

import java.util.ArrayList;
import java.util.List;
import Model.DeviceMobile;
import Model.WirelessNet;

public class CANSController {

    final long walkSpeedMan = 5;
    Context context;
    DeviceMobile dm;
    LocationManager locationManager;
    LocationListener locationListener;

    public CANSController(Context context, DeviceMobile dm) {
        this.dm = dm;
        this.context = context;

        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        locationListener = location -> dm.setSpeed(location.getSpeed());
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, locationListener);

    }

    public void gathering_information(){

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        dm.setDisplay(pm.isInteractive());

        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, ifilter);

        assert batteryStatus != null;
        float level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        float scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        dm.setAcConnector(batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1));

        dm.setPowerlevel(level * 100.0/scale);

        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        int downSpeed = 0;

        NetworkCapabilities nc = connMgr.getNetworkCapabilities(connMgr.getActiveNetwork());
        assert nc != null;

        dm.getCurrent_wifinet().setConected(connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isAvailable());
        dm.getCurrent_5G().setConected(connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isAvailable());
        dm.getCurrent_bluetooth().setConected(connMgr.getNetworkInfo(ConnectivityManager.TYPE_BLUETOOTH).isAvailable());

        downSpeed += nc.getLinkDownstreamBandwidthKbps();
        downSpeed += nc.getLinkUpstreamBandwidthKbps();

        dm.setBandwith(downSpeed);

    }

    public void print_contextinformation(){
        Log.d("[CANSAPP]", "Display: " + String.valueOf(dm.getDisplay()) + " | Power Level: "
                + String.valueOf(dm.getPowerlevel()) + " | Bandwith: " + dm.getBandwith() + "|  Speed: " + dm.getSpeed());
        Log.d("[CANSAPP]","Context: "+ dm.getCurrentContext().toString());

        Log.d("[CANSAPP]","Best Interface: "+ dm.getBestInterface().toString());

    }

    public DeviceMobile.IDCONTEXT identifyContext(){

        if(dm.getAcConnector() != 0 && dm.getSpeed() <= walkSpeedMan){
            dm.setCurrentContext(TROUGHPUT);
            return TROUGHPUT;
        }

        if(dm.getAcConnector() != 0 && dm.getSpeed() > walkSpeedMan){
            dm.setCurrentContext(COVERAGE);
            return COVERAGE;
        }

        if(dm.getPowerlevel() < 20 && dm.getAcConnector() == 0){
            dm.setCurrentContext(POWERSAVE);
            return POWERSAVE;
        }

        if(dm.getSpeed() == 0){
            if(dm.getDisplay()){
                    dm.setCurrentContext(TROUGHPUT);
                    return TROUGHPUT;
            }else{
                if(dm.getBandwith()<=1024) {
                    dm.setCurrentContext(POWERSAVE);
                    return POWERSAVE;
                }
                else{
                    dm.setCurrentContext(TROUGHPUT);
                    return TROUGHPUT;
                }
            }
        }

        if(dm.getSpeed()>0 && dm.getSpeed() <= walkSpeedMan){
            if(dm.getDisplay()){
                dm.setCurrentContext(TROUGHPUT);
                return TROUGHPUT;
            }
            else{
                dm.setCurrentContext(COVERAGE);
                return COVERAGE;
            }
        }

        if(dm.getSpeed() > walkSpeedMan){
                dm.setCurrentContext(COVERAGE);
                return COVERAGE;
        }

        dm.setCurrentContext(TROUGHPUT);
        return TROUGHPUT;
    }

    public DeviceMobile.IFACE selectInterface(){

        switch (dm.getCurrentContext()){
            case COVERAGE:
                            if(dm.getCurrent_5G().isConected()) {
                                dm.setBestInterface(IF_5G);
                                return IF_5G;
                            }
                            if(dm.getCurrent_wifinet().isConected()) {
                                dm.setBestInterface(IF_WIFI);
                                return IF_WIFI;
                            }
                            if(dm.getCurrent_bluetooth().isConected()) {
                                dm.setBestInterface(IF_BLUETOOTH);
                                return IF_BLUETOOTH;
                            }
                            break;
            case POWERSAVE:
                if(dm.getCurrent_bluetooth().isConected()) {
                    dm.setBestInterface(IF_BLUETOOTH);
                    return IF_BLUETOOTH;
                }
                if(dm.getCurrent_wifinet().isConected()){
                    dm.setBestInterface(IF_WIFI);
                    return IF_WIFI;
                }
                if(dm.getCurrent_5G().isConected()) {
                    dm.setBestInterface(IF_5G);
                    return IF_5G;
                }
                break;

            case TROUGHPUT:
                if(dm.getCurrent_wifinet().isConected()) {
                    dm.setBestInterface(IF_WIFI);
                    return IF_WIFI;
                }
                if(dm.getCurrent_5G().isConected()) {
                    dm.setBestInterface(IF_5G);
                    return IF_5G;
                }
                if(dm.getCurrent_bluetooth().isConected()) {
                    dm.setBestInterface(IF_BLUETOOTH);
                    return IF_BLUETOOTH;
                }
                break;

            default:
                dm.setBestInterface(IF_WIFI);
                return IF_WIFI;

        }

        dm.setBestInterface(IF_WIFI);
        return IF_WIFI;
    }

    public void scanWifi() {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        List<WirelessNet> temsplist = new ArrayList<WirelessNet>();

        for (ScanResult result : wifiManager.getScanResults()) {

            //Log.d("[CANSAPP]",result.toString());
            WirelessNet wifinet = new WirelessNet();

            wifinet.setSSID(result.SSID);
            wifinet.setMacAddress(result.BSSID);
            wifinet.setFrequency(result.frequency);
            wifinet.setLevel(result.level);

            temsplist.add(wifinet);

        }

        dm.setWifiNeighrs(temsplist);

    }

    public void scoreWifi() {

        List<WirelessNet> temsplist = new ArrayList<WirelessNet>(dm.getWifiNeighrs());

        for (WirelessNet result : temsplist) {

            Log.d("[CANSAPP]", result.getSSID());
            Log.d("[CANSAPP]", result.getMacAddress());
            Log.d("[CANSAPP]", String.valueOf(result.getFrequency()));
            Log.d("[CANSAPP]", String.valueOf(result.getLevel()));
        }


    }

    public void printWifi() {

        for (WirelessNet result : dm.getWifiNeighrs()) {

            Log.d("[CANSAPP]", result.getSSID());
            Log.d("[CANSAPP]", result.getMacAddress());
            Log.d("[CANSAPP]", String.valueOf(result.getFrequency()));
            Log.d("[CANSAPP]", String.valueOf(result.getLevel()));
        }
    }




}

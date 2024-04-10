package Controller;

import static android.content.Context.LOCATION_SERVICE;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.BatteryManager;
import android.os.PowerManager;
import android.util.Log;
import androidx.annotation.NonNull;
import Model.DeviceMobile;
public class DeviceController {

    Context context;
    private DeviceMobile deviceMobile;
    LocationManager locationManager;
    LocationListener locationListener;

    public DeviceController(Context ctx) {
        this.context = ctx;
        this.deviceMobile = new DeviceMobile();
        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        locationListener =new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                deviceMobile.setSpeed(location.getSpeed());
            }
        };
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5,locationListener);
    }

    public boolean getStateDisplay() {

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);

        return pm.isInteractive();
    }

    public long getBandwith() {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        int downSpeed = 0;

        NetworkCapabilities nc = connMgr.getNetworkCapabilities(connMgr.getActiveNetwork());
        Log.d("[CANSAPP]: ","NETWORK: " + nc.toString());
        assert nc != null;

        downSpeed += nc.getLinkDownstreamBandwidthKbps();
        downSpeed += nc.getLinkUpstreamBandwidthKbps();

        deviceMobile.setBandwith(downSpeed/1024);

        return deviceMobile.getBandwith();
    }

    public double getLevelPower() {

        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, ifilter);

        assert batteryStatus != null;
        float level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        float scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        deviceMobile.setPowerlevel(level * 100.0/scale);

        return deviceMobile.getPowerlevel();
    }

    public float getSpeedMove() {
        return deviceMobile.getSpeed();
    }



}

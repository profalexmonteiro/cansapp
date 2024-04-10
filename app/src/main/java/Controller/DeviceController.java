package Controller;

import static android.content.Context.LOCATION_SERVICE;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
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
import Model.DeviceMobile;

public class DeviceController {

    Context context;
    DeviceMobile deviceMobile;
    LocationManager locationManager;
    LocationListener locationListener;
    BluetoothManager bm;
    BluetoothAdapter bluetoothAdapter;

    public DeviceController(Context ctx) {
        this.context = ctx;
        this.deviceMobile = new DeviceMobile();
        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        locationListener = location -> deviceMobile.setSpeed(location.getSpeed());

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, locationListener);

        bm = ctx.getSystemService(BluetoothManager.class);
        bluetoothAdapter = bm.getAdapter();

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        ctx.registerReceiver(receiver, filter);

        bluetoothAdapter.startDiscovery();

    }

    public BroadcastReceiver getReceiver() {
        return receiver;
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                assert device != null;
                Log.d("[CANSAPP]: ", "BLUETOOTH: " + device.getName() + " " + device.getAddress() + " " + device.getBluetoothClass() + " " + device.getType() + " " + device.getBondState());

            }
        }
    };


    public boolean getStateDisplay() {

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);

        deviceMobile.setDisplay(pm.isInteractive());

        return deviceMobile.getDisplay();
    }

    public long getBandwidth() {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        int downSpeed = 0;

        NetworkCapabilities nc = connMgr.getNetworkCapabilities(connMgr.getActiveNetwork());

        assert nc != null;
        Log.d("[CANSAPP]: ","NETWORK: " + nc.toString());

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

    public void scanWifi() {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        deviceMobile.setWifiNeighrs(wifiManager.getScanResults());

        for (ScanResult result: deviceMobile.getWifiNeihgbohrs()){
            Log.d("[CANSAPP]:","Wi-Fi: " + result.toString());
        }

    }



}

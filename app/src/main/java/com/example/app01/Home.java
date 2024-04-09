package com.example.app01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.List;

public class Home extends AppCompatActivity implements LocationListener {

    private Context context;
    EditText etSpeed;
    EditText etBW;
    EditText etPWL;
    EditText etDisplay;
    LocationManager locationManager;

    BluetoothManager bm;
    BluetoothAdapter bluetoothAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        context = getApplicationContext();
        etBW = findViewById(R.id.editTextBW);
        etSpeed = findViewById(R.id.editTextSpeed);
        etDisplay = findViewById(R.id.editTextDisplay);
        etPWL = findViewById(R.id.editTextPowerLevel);

        bm = getSystemService(BluetoothManager.class);
        bluetoothAdapter = bm.getAdapter();

        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e) {
            Log.d("[CANSAPP]: ","ERROR" + "Wi-Fi scan");
        }

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter);

        bluetoothAdapter.startDiscovery();

    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                Log.d("[CANSAPP]: ","BLUETOOTH: " + device.getName() + " " + device.getAddress()  + " " + device.getBluetoothClass() + " " + device.getType()  + " " + device.getBondState()  + " " + device.getAlias());

            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Don't forget to unregister the ACTION_FOUND receiver.
        unregisterReceiver(receiver);
    }



    public int getBandwith() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        int downSpeed = 0;

        NetworkCapabilities nc = connMgr.getNetworkCapabilities(connMgr.getActiveNetwork());
        assert nc != null;

        downSpeed += nc.getLinkDownstreamBandwidthKbps();
        downSpeed += nc.getLinkUpstreamBandwidthKbps();

        return downSpeed/1024;
    }

    public void getVelocidade() {
        try {
            locationManager = (LocationManager) getApplication().getApplicationContext().getSystemService(LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, Home.this);
        } catch (Exception e) {
            Log.d("[CANSAPP]:","ERRO: " + "Error GPS");
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        etSpeed.setText(String.valueOf(location.getSpeed()));
        Log.d("[CANSAPP]:","VELOCIDADE: " + String.valueOf(location.getSpeed()));
    }

    public boolean getStateDisplay() {

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);

        return pm.isInteractive();
    }

    public float getLevelPower() {

        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, ifilter);

        assert batteryStatus != null;
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        return level * 100 / (float) scale;
    }

    public void scanWifi() {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        List<ScanResult> results = wifiManager.getScanResults();

        for (ScanResult result: results){
            Log.d("[CANSAPP]:","Wi-Fi: " + result.toString());
        }

    }


    public void updateParameters(View v){

        scanWifi();
        getVelocidade();
    //    etSpeed.setText(Float.toString(getSpeedMove()));
        etBW.setText(String.valueOf(getBandwith()));
        if(getStateDisplay())
            etDisplay.setText(R.string.ligado);
        else
            etDisplay.setText(R.string.desligado);
        etPWL.setText(String.valueOf(getLevelPower()));



    }



}
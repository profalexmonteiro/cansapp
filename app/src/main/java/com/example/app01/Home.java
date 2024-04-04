package com.example.app01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiSsid;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.EditText;

import java.math.BigDecimal;
import java.util.List;

public class Home extends AppCompatActivity implements LocationListener {

    private static Context context;
    EditText etSpeed;
    EditText etBW;
    EditText etPWL;
    EditText etDisplay;

    LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        context = getApplicationContext();
        etBW = (EditText) findViewById(R.id.editTextBW);
        etSpeed = (EditText) findViewById(R.id.editTextSpeed);
        etDisplay = (EditText) findViewById(R.id.editTextDisplay);
        etPWL = (EditText) findViewById(R.id.editTextPowerLevel);

        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public int getBandwith() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        int downSpeed = 0;
        int upSpeed;

        //for (Network network : connMgr.getAllNetworks()) {

        //NetworkInfo networkInfo = connMgr.getNetworkInfo(network);

        NetworkCapabilities nc = connMgr.getNetworkCapabilities(connMgr.getActiveNetwork());
        downSpeed = downSpeed + nc.getLinkDownstreamBandwidthKbps();
        upSpeed = nc.getLinkUpstreamBandwidthKbps();

        //}

        return downSpeed;
    }

    public void getVelocidade() {
        try {
            locationManager = (LocationManager) getApplication().getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, Home.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        etSpeed.setText(String.valueOf(location.getSpeed()));
        Log.d("VELOCIDADE", String.valueOf(location.getSpeed()));
    }

    public boolean getStateDisplay() {

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = pm.isInteractive();

        return isScreenOn;
    }

    public float getLevelPower() {

        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, ifilter);

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        float batteryPct = level * 100 / (float) scale;

        return batteryPct;
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
            Log.d("SSID: ",result.SSID);
        }

    }

    public void updateParameters(View v){

        scanWifi();
        getVelocidade();
    //    etSpeed.setText(Float.toString(getSpeedMove()));
        etBW.setText(Integer.toString(getBandwith()));
        if(getStateDisplay())
            etDisplay.setText("ligado");
        else
            etDisplay.setText("desligado");
        etPWL.setText(Float.toString(getLevelPower()));

    }



}
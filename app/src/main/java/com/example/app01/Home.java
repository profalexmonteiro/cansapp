package com.example.app01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.Display;
import android.view.View;
import android.widget.EditText;

public class Home extends AppCompatActivity {

    private static Context context;

    EditText etSpeed;
    EditText etBW;
    EditText etPWL;
    EditText etDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        context = getApplicationContext();
        etBW = (EditText) findViewById(R.id.editTextBW);
        etSpeed = (EditText) findViewById(R.id.editTextSpeed);
        etDisplay = (EditText) findViewById(R.id.editTextDisplay);
        etPWL = (EditText) findViewById(R.id.editTextPowerLevel);
    }


    public int getBandwith(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        int downSpeed=0;
        int upSpeed;

        for (Network network : connMgr.getAllNetworks()) {

            NetworkInfo networkInfo = connMgr.getNetworkInfo(network);

            NetworkCapabilities nc = connMgr.getNetworkCapabilities(connMgr.getActiveNetwork());
            downSpeed = downSpeed + nc.getLinkDownstreamBandwidthKbps();
            upSpeed = nc.getLinkUpstreamBandwidthKbps();

        }

        return downSpeed;
    }

    public boolean getStateDisplay(){

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = pm.isInteractive();

        return isScreenOn;
    }

    public float getLevelPower(){

        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, ifilter);

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        float batteryPct = level * 100 / (float)scale;

        return batteryPct;
    }

    public float getSpeedMove(){

        Location location = new Location("teste");

        float speed=0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            speed = location.getSpeedAccuracyMetersPerSecond();
        }

        return speed;
    }


    public void updateParameters(View v){

        etSpeed.setText(Float.toString(getSpeedMove()));
        etBW.setText(Integer.toString(getBandwith()));
        if(getStateDisplay())
            etDisplay.setText("ligado");
        else
            etDisplay.setText("desligado");
        etPWL.setText(Float.toString(getLevelPower()));

    }






}
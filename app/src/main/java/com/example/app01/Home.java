package com.example.app01;

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
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import Controller.DeviceController;

public class Home extends AppCompatActivity {

    Context context;
    EditText etSpeed;
    EditText etBW;
    EditText etPWL;
    EditText etDisplay;
    DeviceController deviceController;
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

        deviceController = new DeviceController(context);

        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH}, 101);
            }
        } catch (Exception e) {
            Log.d("[CANSAPP]: ", "ERROR" + "Wi-Fi scan");
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Don't forget to unregister the ACTION_FOUND receiver.
        unregisterReceiver(deviceController.getReceiver());
    }


    public void updateParameters(View v){

        deviceController.scanWifi();

        etSpeed.setText(String.valueOf(deviceController.getSpeedMove()));
        etBW.setText(String.valueOf(deviceController.getBandwidth()));
        if(deviceController.getStateDisplay())
            etDisplay.setText(R.string.ligado);
        else
            etDisplay.setText(R.string.desligado);
        etPWL.setText(String.valueOf(deviceController.getLevelPower()));


    }



}
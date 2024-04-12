package Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import java.time.Clock;

import Controller.CANSController;
import Controller.DeviceController;
import Model.DeviceMobile;


public class ServiceCANS extends Service {

    CANSController cc;
    DeviceMobile md;
    final Handler handler = new Handler();
    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            try{
                //do your code here

                cc.gathering_information();
                cc.print_contextinformation();
                DeviceMobile.IDCONTEXT saida = cc.identifyContext();
                Log.d("[CANSAPP]","Context: "+ saida.toString());

                //cc.scanWifi();
                //cc.printWifi();
                //cc.scoreWifi();




            }
            catch (Exception e) {
                // TODO: handle exception
            }
            finally{
                //also call the same runnable to call it at regular interval
                handler.postDelayed(this, 5000);
            }
        }
    };


    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

       DeviceMobile md = new DeviceMobile();
       cc = new CANSController(ServiceCANS.this,md);

       Log.d("[CANSAPP]", " onStartCommand - iniciando");
       //runnable must be execute once
       handler.post(runnable);

        // START_STICKY serve para executar seu serviço até que você pare ele, é reiniciado automaticamente sempre que termina
       return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}

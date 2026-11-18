package edu.oakland.roadconditiondetector.gyroscope;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import edu.oakland.roadconditiondetector.EventBus;
import edu.oakland.roadconditiondetector.gyroscope.GyroscopeController;

public class GyroscopeService extends Service implements EventBus.EventListener {

    private GyroscopeController controller;
    private final IBinder binder = new LocalBinder();


//    public class LocalBinder extends Binder {
//        public GyroscopeService getService() {
//            return GyroscopeService.this;
//        }
//    }
    public class LocalBinder extends Binder {
        public GyroscopeService getService() {
            return GyroscopeService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("GyroscopeService Started", "GyroscopeService Started");
        EventBus.getInstance().register(this);

        controller = new GyroscopeController(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Delegate starting the sensor listener to the controller
        if (controller != null) {
            controller.startListening();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        EventBus.getInstance().unregister(this);

        // Delegate stopping the sensor listener to the controller
        if (controller != null) {
            controller.stopListening();
        }
        super.onDestroy();
    }

    @Override
    public void onEvent(Object event) {

    }
}
package edu.oakland.roadconditiondetector.accelerometer;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import edu.oakland.roadconditiondetector.EventBus;

public class AccelerometerService extends Service implements EventBus.EventListener {

    private AccelerometerController controller;
    private final IBinder binder = new LocalBinder();

    public class LocalBinder extends Binder {
        public AccelerometerService getService() {
            return AccelerometerService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("started service", "1");
        EventBus.getInstance().register(this);

        // Initialize the controller with the service context
        controller = new AccelerometerController(this);
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
        super.onDestroy();
        EventBus.getInstance().unregister(this);

        // Delegate stopping the sensor listener to the controller
        if (controller != null) {
            controller.stopListening();
        }
    }

    @Override
    public void onEvent(Object event) {
        // Handle incoming EventBus events here if needed
//        if (event instanceof NameEvent) {
//            // boolean newStatus = controller.update();
//            // EventBus.getInstance().post(new UpdateUIEvent(newStatus));
//        }
    }
}
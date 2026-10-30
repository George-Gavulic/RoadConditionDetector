package edu.oakland.roadconditiondetector.accelerometer;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import edu.oakland.roadconditiondetector.EventBus;

public class AccelerometerService extends Service implements EventBus.EventListener {

    private final AccelerometerController controller = new AccelerometerController();
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
        EventBus.getInstance().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getInstance().unregister(this);
    }
    
    @Override
    public void onEvent(Object event) {
        /* Example:
        if (event instanceof NameEvent) {
            boolean newStatus = controller.update();
            EventBus.getInstance().post(new UpdateUIEvent(newStatus));
        }
         */
    }
}
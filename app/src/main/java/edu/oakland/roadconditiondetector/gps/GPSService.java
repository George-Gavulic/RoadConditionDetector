package edu.oakland.roadconditiondetector.gps;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import edu.oakland.roadconditiondetector.EventBus;

public class GPSService extends Service implements EventBus.EventListener {

    private final GPSController controller = new GPSController();
    private final IBinder binder = new LocalBinder();

    public class LocalBinder extends Binder {
        public GPSService getService() {
            return GPSService.this;
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

    }
}
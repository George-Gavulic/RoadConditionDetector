package edu.oakland.roadconditiondetector.gyroscope;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import edu.oakland.roadconditiondetector.EventBus;

public class GyroscopeService extends Service implements EventBus.EventListener {

    private final GyroscopeController controller = new GyroscopeController();
    private final IBinder binder = new LocalBinder();

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
package edu.oakland.roadconditiondetector.accelerometer;

import static android.content.ContentValues.TAG;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import edu.oakland.roadconditiondetector.EventBus;

public class AccelerometerService extends Service implements EventBus.EventListener, SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
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

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            //TODO: THIS IS FOR OTHER SERVICE will NOT be implemented here gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        }

//        //TODO: move VVV to on start???
//        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Register the sensor listener when the service officially starts
        if (sensorManager != null && accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
        // Use START_STICKY so the system tries to recreate the service if it gets killed
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getInstance().unregister(this);
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }
    
    @Override
    public void onEvent(Object event) {
//        if (event instanceof NameEvent) {
//            boolean newStatus = controller.update();
//            EventBus.getInstance().post(new UpdateUIEvent(newStatus));
//        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        AccelerometerDataEvent dataEvent = new AccelerometerDataEvent(x, y, z);
        //TODO: publish data to event buss
        EventBus.getInstance().post(dataEvent);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
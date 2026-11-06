package edu.oakland.roadconditiondetector.accelerometer;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import edu.oakland.roadconditiondetector.EventBus;

public class AccelerometerController implements SensorEventListener {

    private final SensorManager sensorManager;
    private Sensor accelerometer;
    public AccelerometerController(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
    }

    public void startListening() {
        if (sensorManager != null && accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void stopListening() {
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        AccelerometerDataEvent dataEvent = new AccelerometerDataEvent(x, y, z);
        EventBus.getInstance().post(dataEvent);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
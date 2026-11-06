package edu.oakland.roadconditiondetector.gyroscope;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import edu.oakland.roadconditiondetector.EventBus;
import edu.oakland.roadconditiondetector.gyroscope.GyroscopeDataEvent;

public class GyroscopeController implements SensorEventListener {

    private final SensorManager sensorManager;
    private Sensor gyroscope;
    public GyroscopeController(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        }
    }

    public void startListening() {
        if (sensorManager != null && gyroscope != null) {
            sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
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
        GyroscopeDataEvent dataEvent = new GyroscopeDataEvent(x, y, z);
        EventBus.getInstance().post(dataEvent);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
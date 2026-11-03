package edu.oakland.roadconditiondetector;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import edu.oakland.road_condition_detector_built_by_gg_and_jp.R;
//import edu.oakland.roadconditiondetector.R;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    private TextView accelerometerXTextView;
    private TextView accelerometerYTextView;
    private TextView accelerometerZTextView;

    private TextView latitudeTextView;
    private TextView longitudeTextView;
    private TextView speedTextView;
    private TextView gyroscopeXTextView;
    private TextView gyroscopeYTextView;
    private TextView gyroscopeZTextView;

    private SensorManager sensorManager;
    private Sensor accelerometer;
    // private Sensor gyroscope;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.accelerometerXTextView = findViewById(R.id.accelerometerXTextView);
        this.accelerometerYTextView = findViewById(R.id.accelerometerYTextView);
        this.accelerometerZTextView = findViewById(R.id.accelerometerZTextView);
        this.latitudeTextView = findViewById(R.id.latitudeTextView);
        this.longitudeTextView = findViewById(R.id.longitudeTextView);
        this.speedTextView = findViewById(R.id.speedTextView);
        this.gyroscopeXTextView = findViewById(R.id.gyroscopeXTextView);
        this.gyroscopeYTextView = findViewById(R.id.gyroscopeYTextView);
        this.gyroscopeZTextView = findViewById(R.id.gyroscopeZTextView);

        if (accelerometer ==  null){
            accelerometerXTextView.setText("accelerometer is not available on this device");
        }

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            // gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        accelerometerXTextView.setText(String.format(Locale.US, getString(R.string.accelerometer_value), "X", x));
        accelerometerYTextView.setText(String.format(Locale.US, getString(R.string.accelerometer_value), "Y", y));
        accelerometerZTextView.setText(String.format(Locale.US, getString(R.string.accelerometer_value), "Z", z));


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }


}
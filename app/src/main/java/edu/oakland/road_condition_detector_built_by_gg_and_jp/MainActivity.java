package edu.oakland.road_condition_detector_built_by_gg_and_jp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

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

    SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        Sensor gyroscope = sensorManager.getDefaultSensor(Sensor.Sensor.TYPE_GYROSCOPE);

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

//        xTextView = findViewById(R.id.xTextView);
//        yTextView = findViewById(R.id.yTextView);
//        zTextView = findViewById(R.id.zTextView);


//        Sensor.TYPE_ACCELEROMETER
//        Sensor.TYPE_GYROSCOPE
//        Sensor.TYPE_LIGHT
//        Sensor.TYPE_PROXIMITY
//        Sensor.TYPE_MAGNETIC_FIELD
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
        accelerometerXTextView.setText(String.format("X: %.2f", x));
        accelerometerYTextView.setText(String.format("Y: %.2f", y));
        accelerometerZTextView.setText(String.format("Z: %.2f", z));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }


}
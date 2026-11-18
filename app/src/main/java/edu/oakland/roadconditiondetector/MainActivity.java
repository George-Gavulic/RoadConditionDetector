package edu.oakland.roadconditiondetector;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;

import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;

import java.util.Locale;

import edu.oakland.roadconditiondetector.R;
import edu.oakland.roadconditiondetector.UI.SensorGraphManager;
import edu.oakland.roadconditiondetector.accelerometer.AccelerometerDataEvent;
import edu.oakland.roadconditiondetector.accelerometer.AccelerometerService;
import edu.oakland.roadconditiondetector.gyroscope.GyroscopeDataEvent;
import edu.oakland.roadconditiondetector.gyroscope.GyroscopeService;

public class MainActivity extends AppCompatActivity implements EventBus.EventListener{
    private TextView accelerometerXTextView;
    private TextView accelerometerYTextView;
    private TextView accelerometerZTextView;

    private TextView latitudeTextView;
    private TextView longitudeTextView;
    private TextView speedTextView;
    private TextView gyroscopeXTextView;
    private TextView gyroscopeYTextView;
    private TextView gyroscopeZTextView;

    private SensorGraphManager graphManager;

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

        // Initialize UI graph manager; it automatically listens to the EventBus
        LineChart chartAccel = findViewById(R.id.chartAccel);
        LineChart chartGyro = findViewById(R.id.chartGyro);
        LineChart chartGps = findViewById(R.id.chartGps);
        graphManager = new SensorGraphManager(chartAccel, chartGyro, chartGps);

        //Start the background service so it begins collecting sensor data
        Intent accelerometerServiceIntent = new Intent(this, AccelerometerService.class);
        Intent gyroscopeServiceIntent = new Intent(this, GyroscopeService.class);
        startService(accelerometerServiceIntent);
        startService(gyroscopeServiceIntent);

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Register to listen to the EventBus when the Activity is active
        EventBus.getInstance().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister when the Activity goes to the background to prevent memory leaks
        EventBus.getInstance().unregister(this);
    }

    @Override
    protected void onDestroy() {
        graphManager.unregister();
        super.onDestroy();
    }
    @Override
    public void onEvent(Object event) {
        if (event instanceof AccelerometerDataEvent) {
            AccelerometerDataEvent data = (AccelerometerDataEvent) event;
            accelerometerXTextView.setText(String.format(Locale.US, getString(R.string.accelerometer_value), "X", data.getX()));
            accelerometerYTextView.setText(String.format(Locale.US, getString(R.string.accelerometer_value), "Y", data.getY()));
            accelerometerZTextView.setText(String.format(Locale.US, getString(R.string.accelerometer_value), "Z", data.getZ()));
        } else if (event instanceof GyroscopeDataEvent) {
            GyroscopeDataEvent data = (GyroscopeDataEvent) event;
            gyroscopeXTextView.setText(String.format(Locale.US, getString(R.string.gyroscope_value), "X", data.getX()));
            gyroscopeYTextView.setText(String.format(Locale.US, getString(R.string.gyroscope_value), "Y", data.getY()));
            gyroscopeZTextView.setText(String.format(Locale.US, getString(R.string.gyroscope_value), "Z", data.getZ()));
        }

    }
}
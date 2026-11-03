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

import java.util.Locale;

import edu.oakland.roadconditiondetector.R;
import edu.oakland.roadconditiondetector.accelerometer.AccelerometerDataEvent;
import edu.oakland.roadconditiondetector.accelerometer.AccelerometerService;

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

//    private SensorManager sensorManager;
//    private Sensor accelerometer; //moved to the controller
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


        //Start the background service so it begins collecting sensor data
        Intent serviceIntent = new Intent(this, AccelerometerService.class);
        startService(serviceIntent);

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
    public void onEvent(Object event) {
        // 4. Check if the incoming event is your AccelerometerDataEvent
        if (event instanceof AccelerometerDataEvent) {
            AccelerometerDataEvent data = (AccelerometerDataEvent) event;

        accelerometerXTextView.setText(String.format(Locale.US, getString(R.string.accelerometer_value), "X", data.getX()));
        accelerometerYTextView.setText(String.format(Locale.US, getString(R.string.accelerometer_value), "Y", data.getY()));
        accelerometerZTextView.setText(String.format(Locale.US, getString(R.string.accelerometer_value), "Z", data.getZ()));


//            Update the UI safely on the Main (UI) Thread
//            runOnUiThread(() -> {

//            });
//            });
        }
    }

    //TODO: Update these when we hear back from the a subscribed event listener
//    accelerometerXTextView.setText(String.format(Locale.US, getString(R.string.accelerometer_value), "X", x));
//    accelerometerYTextView.setText(String.format(Locale.US, getString(R.string.accelerometer_value), "Y", y));
//    accelerometerZTextView.setText(String.format(Locale.US, getString(R.string.accelerometer_value), "Z", z));


}
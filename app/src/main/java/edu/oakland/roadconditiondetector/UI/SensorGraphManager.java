package edu.oakland.roadconditiondetector.UI;

import android.graphics.Color;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import java.util.ArrayList;
import edu.oakland.roadconditiondetector.EventBus;
import edu.oakland.roadconditiondetector.accelerometer.AccelerometerDataEvent;
import edu.oakland.roadconditiondetector.gyroscope.GyroscopeDataEvent;

// Import your Gyro and GPS data events when created
// import edu.oakland.roadconditiondetector.gps.GpsDataEvent;

//This java code was created with AI, I hope you enjoy the flair this adds

public class SensorGraphManager implements EventBus.EventListener {

    private final LineChart chartAccelerometer;
    private final LineChart chartGyroscope;
    private final LineChart chartGps;

    private LineData AccelerometerData, GyroscopeData, gpsData;
    private int AccelerometerEntryCount = 0, GyroscopeEntryCount = 0, gpsEntryCount = 0;

    public SensorGraphManager(LineChart chartAccelerometer, LineChart chartGyroscope, LineChart chartGps) {
        this.chartAccelerometer = chartAccelerometer;
        this.chartGyroscope = chartGyroscope;
        this.chartGps = chartGps;

        setupCharts();
        EventBus.getInstance().register(this);
    }

    private void setupCharts() {
        // Setup Accelerometer Chart Datasets (X, Y, Z)
        LineDataSet accX = createDataSet("Accelerometer X", Color.RED);
        LineDataSet accY = createDataSet("Accelerometer Y", Color.GREEN);
        LineDataSet accZ = createDataSet("Accelerometer Z", Color.BLUE);
        AccelerometerData = new LineData(accX, accY, accZ);
        chartAccelerometer.setData(AccelerometerData);

        // Setup Gyroscope Chart Datasets (X, Y, Z)
        LineDataSet gyroX = createDataSet("Gyroscope X", Color.MAGENTA);
        LineDataSet gyroY = createDataSet("Gyroscope Y", Color.CYAN);
        LineDataSet gyroZ = createDataSet("Gyroscope Z", Color.YELLOW);
        GyroscopeData = new LineData(gyroX, gyroY, gyroZ);
        chartGyroscope.setData(GyroscopeData);

        // Setup GPS Chart Dataset (e.g., Speed over time)
        LineDataSet gpsSpeed = createDataSet("Speed", Color.BLACK);
        gpsData = new LineData(gpsSpeed);
        chartGps.setData(gpsData);
    }

    private LineDataSet createDataSet(String label, int color) {
        LineDataSet dataSet = new LineDataSet(new ArrayList<>(), label);
        dataSet.setColor(color);
        dataSet.setDrawValues(false);
        dataSet.setDrawCircles(false);
        return dataSet;
    }

    @Override
    public void onEvent(Object event) {
        if (event instanceof AccelerometerDataEvent) {
            AccelerometerDataEvent d = (AccelerometerDataEvent) event;
            addAccelerometerEntry(d.getX(), d.getY(), d.getZ());
        } else if (event instanceof GyroscopeDataEvent) {
            GyroscopeDataEvent d = (GyroscopeDataEvent) event;
            addGyroscopeEntry(d.getX(), d.getY(), d.getZ());
        } /*
        else if (event instanceof GpsDataEvent) {

            GpsDataEvent d = (GpsDataEvent) event;
            addGpsEntry(d.getSpeed());
        }
        */
    }

    private void addAccelerometerEntry(float x, float y, float z) {
        AccelerometerData.addEntry(new Entry(AccelerometerEntryCount, x), 0);
        AccelerometerData.addEntry(new Entry(AccelerometerEntryCount, y), 1);
        AccelerometerData.addEntry(new Entry(AccelerometerEntryCount, z), 2);
        AccelerometerEntryCount++;

        if (AccelerometerData.getDataSetByIndex(0).getEntryCount() > 500) {
            for (int i = 0; i < 3; i++) AccelerometerData.getDataSetByIndex(i).removeFirst();
        }
        chartAccelerometer.notifyDataSetChanged();
        chartAccelerometer.invalidate();
    }

    private void addGyroscopeEntry(float x, float y, float z) {
        GyroscopeData.addEntry(new Entry(GyroscopeEntryCount, x), 0);
        GyroscopeData.addEntry(new Entry(GyroscopeEntryCount, y), 1);
        GyroscopeData.addEntry(new Entry(GyroscopeEntryCount, z), 2);
        GyroscopeEntryCount++;

        if (GyroscopeData.getDataSetByIndex(0).getEntryCount() > 500) {
            for (int i = 0; i < 3; i++) GyroscopeData.getDataSetByIndex(i).removeFirst();
        }
        chartGyroscope.notifyDataSetChanged();
        chartGyroscope.invalidate();
    }

    public void unregister() {
        EventBus.getInstance().unregister(this);
    }
}
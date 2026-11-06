package edu.oakland.roadconditiondetector.UI;

import android.graphics.Color;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import java.util.ArrayList;
import edu.oakland.roadconditiondetector.EventBus;
import edu.oakland.roadconditiondetector.accelerometer.AccelerometerDataEvent;
// Import your Gyro and GPS data events when created
// import edu.oakland.roadconditiondetector.gyroscope.GyroscopeDataEvent;
// import edu.oakland.roadconditiondetector.gps.GpsDataEvent;

//This java code was created with AI, I hope you enjoy the flair this adds

public class SensorGraphManager implements EventBus.EventListener {

    private final LineChart chartAccel;
    private final LineChart chartGyro;
    private final LineChart chartGps;

    private LineData accelData, gyroData, gpsData;
    private int accelEntryCount = 0, gyroEntryCount = 0, gpsEntryCount = 0;

    public SensorGraphManager(LineChart chartAccel, LineChart chartGyro, LineChart chartGps) {
        this.chartAccel = chartAccel;
        this.chartGyro = chartGyro;
        this.chartGps = chartGps;

        setupCharts();
        EventBus.getInstance().register(this);
    }

    private void setupCharts() {
        // Setup Accelerometer Chart Datasets (X, Y, Z)
        LineDataSet accX = createDataSet("Accel X", Color.RED);
        LineDataSet accY = createDataSet("Accel Y", Color.GREEN);
        LineDataSet accZ = createDataSet("Accel Z", Color.BLUE);
        accelData = new LineData(accX, accY, accZ);
        chartAccel.setData(accelData);

        // Setup Gyroscope Chart Datasets (X, Y, Z)
        LineDataSet gyroX = createDataSet("Gyro X", Color.MAGENTA);
        LineDataSet gyroY = createDataSet("Gyro Y", Color.CYAN);
        LineDataSet gyroZ = createDataSet("Gyro Z", Color.YELLOW);
        gyroData = new LineData(gyroX, gyroY, gyroZ);
        chartGyro.setData(gyroData);

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
            addAccelEntry(d.getX(), d.getY(), d.getZ());
        }
        /*
        else if (event instanceof GyroscopeDataEvent) {
            GyroscopeDataEvent d = (GyroscopeDataEvent) event;
            addGyroEntry(d.getX(), d.getY(), d.getZ());
        } else if (event instanceof GpsDataEvent) {
            GpsDataEvent d = (GpsDataEvent) event;
            addGpsEntry(d.getSpeed());
        }
        */
    }

    private void addAccelEntry(float x, float y, float z) {
        accelData.addEntry(new Entry(accelEntryCount, x), 0);
        accelData.addEntry(new Entry(accelEntryCount, y), 1);
        accelData.addEntry(new Entry(accelEntryCount, z), 2);
        accelEntryCount++;

        if (accelData.getDataSetByIndex(0).getEntryCount() > 50) {
            for (int i = 0; i < 3; i++) accelData.getDataSetByIndex(i).removeFirst();
        }
        chartAccel.notifyDataSetChanged();
        chartAccel.invalidate();
    }

    public void unregister() {
        EventBus.getInstance().unregister(this);
    }
}
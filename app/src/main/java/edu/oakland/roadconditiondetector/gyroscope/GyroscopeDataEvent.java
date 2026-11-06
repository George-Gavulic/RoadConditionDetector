package edu.oakland.roadconditiondetector.gyroscope;

public class GyroscopeDataEvent {
    private final float x;
    private final float y;
    private final float z;

    public GyroscopeDataEvent(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }
}

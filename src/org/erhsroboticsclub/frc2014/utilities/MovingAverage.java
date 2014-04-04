package org.erhsroboticsclub.frc2014.utilities;

public class MovingAverage {
    private final double[] data;
    private int index = 0;
    private boolean initialized = false;

    public MovingAverage(int size) {
        data = new double[size];
    }

    public void clear() {
        initialized = false;
    }

    public void add(double x) {
        if (!initialized) {
            for (int i = 0; i < data.length; i++) {
                data[i] = x;
            }
        } else {
            data[index++] = x;
            index = index % data.length;
        }
        initialized = true;
    }

    public double evaluate() {
        double sum = 0;
        for (int i = 0; i < data.length; i++) {
            sum += data[i];
        }
        return sum / data.length;
    }
}

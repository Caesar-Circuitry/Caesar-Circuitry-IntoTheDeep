package org.firstinspires.ftc.teamcode.Robot.CustomMath;
public class RingBuffer {
    private double[] buffer;
    private int index;
    private int size;

    public RingBuffer(int size, double initialValue) {
        buffer = new double[size];
        for (int i = 0; i < size; i++) {
            buffer[i] = initialValue;
        }
        index = 0;
        this.size = size;
    }

    public void add(double value) {
        buffer[index] = value;
        index = (index + 1) % size;
    }

    public double getValue(int position) {
        return buffer[(index - position + size) % size];
    }

    public int getSize() {
        return size;
    }

    public void fill(double value) {
        for (int i = 0; i < size; i++) {
            buffer[i] = value;
        }
    }
}
package org.firstinspires.ftc.teamcode.Robot.CustomMath;

import com.qualcomm.robotcore.util.ElapsedTime;

public class PDFL {

    private double kP, kD, kF, kL;

    private double deadzone;

    private double homedConstant;

    private boolean homed = false;

    private ElapsedTime timer;

    private RingBuffer timeBuffer = new RingBuffer(3, 0.0);
    private RingBuffer errorBuffer = new RingBuffer(3, 0.0);

    public PDFL(double kP, double kD, double kF, double kL) {
        this.kP = kP;
        this.kD = kD;
        this.kF = kF;
        this.kL = kL;
        timer = new ElapsedTime();
    }

    public void updateConstants(double kP, double kD, double kF, double kL) {
        this.kP = kP;
        this.kD = kD;
        this.kF = kF;
        this.kL = kL;
    }

    public void setDeadzone(double deadzone) {
        this.deadzone = deadzone;
    }

    public void setHomed(boolean homed) {
        this.homed = homed;
    }

    public void setHomedConstant(double constant) {
        homedConstant = constant;
    }

    public void reset() {
        timeBuffer.fill(0.0);
        errorBuffer.fill(0.0);
        timer.reset();
    }

    public double run(double error) {
        if (homed) {
            return homedConstant;
        }

        double deltaTime = timer.time();
        timer.reset();

        double time = timeBuffer.getValue(timeBuffer.getSize() - 1) + deltaTime;
        timeBuffer.add(time);

        double previous_error = errorBuffer.getValue(errorBuffer.getSize() - 1);
        errorBuffer.add(error);

        double delta_error = error - previous_error;

        // If the PDFL hasn't been updated, reset it
        if (deltaTime > 200) {
            reset();
            return run(error);
        }

        double p = pComponent(error);
        double d = dComponent(delta_error, deltaTime);
        double f = fComponenet();
        double l = lComponent(error);

        double response = p + d + f + l;

        if (Math.abs(error) < deadzone) {
            // same response but without lower limit
            response = p + d + f;
        }

        return response;
    }

    private double pComponent(double error) {
        double response = kP * error;
        return response;
    }

    private double dComponent(double delta_error, double delta_time) {
        double derivative = delta_error / delta_time;
        double response = derivative * kD;
        return response;
    }

    private double fComponenet() {
        double response = kF;
        return response;
    }

    private double lComponent(double error) {
        double direction = Math.signum(error);
        double response = direction * kL;
        return response;
    }
}

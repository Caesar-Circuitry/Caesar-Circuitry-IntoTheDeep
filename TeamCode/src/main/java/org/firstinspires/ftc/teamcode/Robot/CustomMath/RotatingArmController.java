package org.firstinspires.ftc.teamcode.Robot.CustomMath;

import com.qualcomm.robotcore.util.ElapsedTime;

public class RotatingArmController {
    private double kP, kI, kD; // PID gains

    private double targetAngle; // target angle for the arm
    private double currentAngle; // current angle of the arm
    private double previousError; // previous error value
    private double integral; // integral term

    private double deadzone;

    private double homedConstant;

    private boolean homed = false;

    private ElapsedTime timer;

    public RotatingArmController(double kP, double kI, double kD) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        timer = new ElapsedTime();
    }

    public void updateConstants(double kP, double kI, double kD) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
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
        previousError = 0;
        integral = 0;
        timer.reset();
    }

    public double run(double currentAngle, double targetAngle) {
        if (homed) {
            return homedConstant;
        }

        this.currentAngle = currentAngle;
        this.targetAngle = targetAngle;

        double error = targetAngle - currentAngle;
        double deltaTime = timer.time();
        timer.reset();

        // Calculate PID terms
        double proportional = kP * error;
        integral += kI * error * deltaTime;
        double derivative = kD * (error - previousError) / deltaTime;

        previousError = error;

        // Calculate response
        double response = proportional + integral + derivative;

        if (Math.abs(error) < deadzone) {
            // same response but without lower limit
            response = proportional + integral;
        }

        return response;
    }
}
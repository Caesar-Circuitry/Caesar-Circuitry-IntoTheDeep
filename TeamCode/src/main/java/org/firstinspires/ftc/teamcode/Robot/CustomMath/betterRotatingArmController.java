package org.firstinspires.ftc.teamcode.Robot.CustomMath;

import com.qualcomm.robotcore.util.ElapsedTime;

public class betterRotatingArmController {
    private double lKP, lKI, lKD, lKF, sKP, sKI, sKD, sKF; // PID gains L is the large error s is the small gain

    private double targetAngle; // target angle for the arm
    private double currentAngle; // current angle of the arm
    private double previousError; // previous error value
    private double integral; // integral term
    private double proportional, derivative, feedForward;

    private double deadzone;

    private double homedConstant;

    private boolean homed = false;
    private double errorZone = 5;

    private ElapsedTime timer;

    public betterRotatingArmController(double lKP, double lKI, double lKD, double lKF, double sKP, double sKI, double sKD, double sKF) {
        this.lKP = lKP;
        this.lKI = lKI;
        this.lKD = lKD;
        this.lKF = lKF;
        this.sKP = sKP;
        this.sKI = sKI;
        this.sKD = sKD;
        this.sKF = sKF;
        timer = new ElapsedTime();
    }

    public void updateConstants(double lKP, double lKI, double lKD, double lKF) {
        this.lKP = lKP;
        this.lKI = lKI;
        this.lKD = lKD;
        this.lKF = lKF;
    }

    public void setErrorZone(double errorZone){
        this.errorZone = errorZone;
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
        if(error > errorZone) {
            proportional = lKP * error;
            integral += lKI * error * deltaTime;
            derivative = lKD * (error - previousError) / deltaTime;
            feedForward = lKF * Math.signum(currentAngle);
        }else{
            proportional = sKP * error;
            integral += sKI * error * deltaTime;
            derivative = sKD * (error - previousError) / deltaTime;
            feedForward = sKF * Math.signum(currentAngle);
        }

        previousError = error;

        // Calculate response
        double response = proportional + integral + derivative + feedForward;

        if (Math.abs(error) < deadzone) {
            // same response but without lower limit
            response = proportional + integral + derivative;
        }

        return response;
    }
}
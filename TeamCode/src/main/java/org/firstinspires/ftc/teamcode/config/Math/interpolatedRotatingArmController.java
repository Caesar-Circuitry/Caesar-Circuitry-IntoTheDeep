package org.firstinspires.ftc.teamcode.config.Math;

import com.arcrobotics.ftclib.util.InterpLUT;
import com.qualcomm.robotcore.util.ElapsedTime;
/*EXPERIMENTAL DON'T USE
*
* Controller that interpolates PID Coefficents based on angle
*
* use for non-linear systems(arm on Aires)
*
*
*
*/
//TODO solve out of bounds error
public class interpolatedRotatingArmController {
    private double kP,kI,kD,kF;

    private double targetAngle; // target angle for the arm
    private double currentAngle; // current angle of the arm
    private double previousError; // previous error value
    private double integral; // integral term
    private double proportional, derivative, feedForward;
    private InterpLUT pCoefficients, iCoefficients, dCoefficients;
    private double deadzone;

    private double homedConstant;
    private double middlePoint = 110;
    private boolean homed = false;
    private double errorZone = 5;
    private double ticksPerDeg = 0;

    private ElapsedTime timer;

    public interpolatedRotatingArmController(double kPAt40, double kIAt40, double kDAt40, double kPAt110, double kIAt110, double kDAt110, double kF, double ticksPerDeg) {
        pCoefficients = new InterpLUT();
        iCoefficients = new InterpLUT();
        dCoefficients = new InterpLUT();

        pCoefficients.add(0,kDAt40);
        pCoefficients.add(40,kPAt40);
        pCoefficients.add(110, kPAt110);
        pCoefficients.add(235, .09);

        iCoefficients.add(0,kDAt40);
        iCoefficients.add(40,kIAt40);
        iCoefficients.add(110,kIAt110);
        iCoefficients.add(235,0);

        dCoefficients.add(0,0);
        dCoefficients.add(40,kDAt40);
        dCoefficients.add(110,kDAt110);
        dCoefficients.add(235,0);

        pCoefficients.createLUT();
        iCoefficients.createLUT();
        dCoefficients.createLUT();

        this.kF = kF;
        this.ticksPerDeg = ticksPerDeg;

        timer = new ElapsedTime();
    }

    public void setMiddlePoint(double middlePoint){
        this.middlePoint = middlePoint;
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
            proportional = pCoefficients.get(currentAngle /ticksPerDeg) * error;
            integral += iCoefficients.get(currentAngle / ticksPerDeg) * error * deltaTime;
            derivative = dCoefficients.get(currentAngle / ticksPerDeg) * (error - previousError) / deltaTime;
            feedForward = kF * Math.signum(currentAngle - middlePoint);

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


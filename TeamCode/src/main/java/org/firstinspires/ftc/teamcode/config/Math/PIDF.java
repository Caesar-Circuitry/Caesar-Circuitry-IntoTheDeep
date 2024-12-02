package org.firstinspires.ftc.teamcode.config.Math;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;


@Config
public class PIDF{
    private PIDController controller;

    public static double p = 0.05, i = 0, d = 0.0008;
    public static double f = 0.1;
    private final double ticks_in_degree = 2027/ 90.0;
    private final double deadzone = 1.5;

    public PIDF(){
        controller = new PIDController(p,i,d);
    }

    public double run(double targetPos, double actualPos){
        controller.setPID(p,i,d);
        if (Math.abs(targetPos-actualPos)>deadzone) {
            double pid = controller.calculate(actualPos, targetPos);
            double ff = Math.cos(Math.toRadians(targetPos / ticks_in_degree)) * f;

            double power = pid + ff;
            return power;
        }else{
            double ff = Math.cos(Math.toRadians(actualPos / ticks_in_degree)) * f;
            return ff;
        }
    }
    public void reset(){
        controller.reset();
    }
    public double hold(double targetPos){
        double ff = Math.cos(Math.toRadians(targetPos)) * f;
        return ff;
    }
}

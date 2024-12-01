package org.firstinspires.ftc.teamcode.config.Math;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;


@Config
@TeleOp
public class PIDF{
    private PIDController controller;

    public static double p = 0.05, i = 0, d = 0.0008;
    public static double f = 0.1;
    private final double ticks_in_degree = 2027/ 90.0;

    private DcMotorEx arm_motor;

    public PIDF(HardwareMap hardwareMap){
        controller = new PIDController(p,i,d);

        arm_motor = hardwareMap.get(DcMotorEx.class,"armRotate");
        arm_motor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public double run(double targetPos, double actualPos){
        controller.setPID(p,i,d);
        double pid = controller.calculate(actualPos, targetPos * ticks_in_degree);
        double ff = Math.cos(Math.toRadians(targetPos)) * f;

        double power  = pid + ff;
        return power;
    }
}

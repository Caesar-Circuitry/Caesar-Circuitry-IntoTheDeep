package org.firstinspires.ftc.teamcode.Robot;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp
@Config
public class armMotorMover extends LinearOpMode {
    private DcMotor motor;
    public static String motorName = "";
    public static double speed = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        motor = hardwareMap.get(DcMotor.class, motorName);
        waitForStart();
        while (opModeIsActive()) {
            motor.setPower(speed);
        }
    }
}

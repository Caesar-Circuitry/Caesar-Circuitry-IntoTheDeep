package org.firstinspires.ftc.teamcode.opmode.example;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Config
@TeleOp
public class EncoderArmTester extends LinearOpMode {
    private DcMotor arm;
    public String name = "armRotate";
    public static boolean Reverse = false;
    public static double traveldist = 90, holdPow = .1;
    private double prevMotorPow = 0;
    private MultipleTelemetry Telemetry;
    @Override
    public void runOpMode() throws InterruptedException {
        Telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        arm = hardwareMap.get(DcMotor.class,name);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        if (Reverse) {
            arm.setDirection(DcMotorSimple.Direction.REVERSE);
        }else{
            arm.setDirection(DcMotorSimple.Direction.FORWARD);
        }
        waitForStart();
        while (opModeIsActive()){
            if(prevMotorPow != holdPow) {
                arm.setPower(holdPow);
                prevMotorPow = holdPow;
            }
            double pos = arm.getCurrentPosition();
            Telemetry.addData("encoderTicks", pos);
            Telemetry.addData("ticks per degree", pos/traveldist);
            Telemetry.addData("ticks per radian", pos/Math.toRadians(traveldist));
            Telemetry.update();
        }
    }
}

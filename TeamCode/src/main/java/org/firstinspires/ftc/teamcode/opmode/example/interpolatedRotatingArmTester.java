package org.firstinspires.ftc.teamcode.opmode.example;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.config.Math.interpolatedRotatingArmController;
@Disabled
/*
*
* Tester for Interpolated rotating arm controller see file for description
*
*/
@Config
@TeleOp
public class interpolatedRotatingArmTester extends LinearOpMode {
    private DcMotor rotate;
    private interpolatedRotatingArmController controller;
    public static double targetAngle =0, currentAngleDeg = 0;
    private double ticksPer90 = 1424, ticksPerDegree = ticksPer90/90, CurrentAngle = 0, EncoderCount = 0,rotatePow = 0, rotatePowPrev= 0, targetAngleEnc = 0;
    public static double
            kPAt40 = 0.05,
            kIAt40 = 0,
            kDAt40 = 0.0013,
            kPAt110 = 0.08,
            kIAt110 = 0.001,
            kDAt110 = 0.0001,
            kF = .1;
    private MultipleTelemetry Telemetry;
    @Override
    public void runOpMode() throws InterruptedException {
        Telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        rotate = hardwareMap.get(DcMotor.class,"armRotate");
        rotate.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rotate.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rotate.setDirection(DcMotorSimple.Direction.REVERSE);
        controller = new interpolatedRotatingArmController(kPAt40,kIAt40,kDAt40,kPAt110,kIAt110,kDAt110,kF,ticksPerDegree);
        waitForStart();
        while (opModeIsActive()){
            targetAngleEnc = targetAngle * ticksPerDegree;
            try {
                EncoderCount = rotate.getCurrentPosition();
                currentAngleDeg = EncoderCount / ticksPerDegree;
                Telemetry.addData("armPos", currentAngleDeg);
                Telemetry.addData("targetPos", targetAngle);
            }catch (Exception e){
                EncoderCount = 0;
            }
            CurrentAngle = EncoderCount;
            if(CurrentAngle != 0) {
                rotatePow = controller.run(CurrentAngle, targetAngleEnc);
                if (rotatePow != rotatePowPrev) {
                    rotate.setPower(rotatePow);
                    rotatePowPrev = rotatePow;
                }
            }
            Telemetry.update();
        }
    }
}

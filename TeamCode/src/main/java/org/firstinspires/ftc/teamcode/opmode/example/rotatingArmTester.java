package org.firstinspires.ftc.teamcode.opmode.example;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.config.Math.rotatingArmController;
import org.firstinspires.ftc.teamcode.pedroPathing.localization.Encoder;

@Config
@TeleOp
public class rotatingArmTester extends LinearOpMode {
    private DcMotor rotate;
    private rotatingArmController controller;
    public static double targetAngle =0, currentAngleDeg;
    private double ticksPer90 = 1424, ticksPerDegree = ticksPer90/90, CurrentAngle = 0, EncoderCount = 0,rotatePow = 0, rotatePowPrev= 0, targetAngleEnc = 0;
    public static double
            lKP = 0.03,
            lKI = 0,
            lKD = 0.001,
            lKF = .1;
    private MultipleTelemetry Telemetry;
    @Override
    public void runOpMode() throws InterruptedException {
        Telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        rotate = hardwareMap.get(DcMotor.class,"armRotate");
        rotate.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rotate.setDirection(DcMotorSimple.Direction.REVERSE);
        rotate.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        controller = new rotatingArmController(lKP, lKI, lKD, lKF, lKP, lKI, lKD, lKF);
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
            updateConstants();
            rotatePow = controller.run(CurrentAngle,targetAngleEnc);
            if (rotatePow != rotatePowPrev) {
                rotate.setPower(rotatePow);
                rotatePowPrev = rotatePow;
            }
            Telemetry.update();
        }
    }
    public void updateConstants(){
        this.controller.updateConstants(lKP, lKI, lKD, lKF, lKP, lKI, lKD, lKF);
    }
}

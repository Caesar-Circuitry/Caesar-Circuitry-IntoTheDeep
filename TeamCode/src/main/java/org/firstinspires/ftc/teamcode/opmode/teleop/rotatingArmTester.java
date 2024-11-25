package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.config.Math.rotatingArmController;

@Config
@TeleOp
public class rotatingArmTester extends LinearOpMode {
    private DcMotor rotate;
    private rotatingArmController controller;
    public static double targetAngle =0;
    private double ticksPer90 = -1498, ticksPerDegree = ticksPer90/90, CurrentAngle = 0, EncoderCount = 0,rotatePow = 0, rotatePowPrev= 0, targetAngleEnc = 0;
    public static double
            lKP = 0.03,
            lKI = 0,
            lKD = 0,
            lKF = .1,
            sKP = .03,
            sKI = 0,
            sKD = 0,
            sKF = .1;
    @Override
    public void runOpMode() throws InterruptedException {
        rotate = hardwareMap.get(DcMotor.class,"armRotate");
        rotate.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rotate.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        controller = new rotatingArmController(lKP, lKI, lKD, lKF, sKP, sKI, sKD, sKF);
        waitForStart();
        while (opModeIsActive()){
            targetAngleEnc = targetAngle * ticksPerDegree;
            try {
                EncoderCount = rotate.getCurrentPosition();
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
        }
    }
    public void updateConstants(){
        this.controller.updateConstants(lKP, lKI, lKD, lKF, sKP, sKI, sKD, sKF);
    }
}

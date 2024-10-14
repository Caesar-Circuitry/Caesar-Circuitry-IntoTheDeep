package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Robot.CustomMath.PDFL;

public class teleOp extends LinearOpMode {
    private double Kp, Kd, Kf, Kl;
    private PDFL ArmMove = new PDFL(Kp, Kd, Kf, Kl);
    private double front;
    private double up;
    private double rear;
    private DcMotor armmotor;

    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
        while (opModeIsActive()){
            if (gamepad1.a){ //This creates the gamepad object and runs code based on the outcome//
                telemetry.addLine("Setting arm to base");
                telemetry.update();
                armmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        armmotor.setPower(0.3);
                        armmotor.setTargetPosition(10000);

            }
        }
    }
}

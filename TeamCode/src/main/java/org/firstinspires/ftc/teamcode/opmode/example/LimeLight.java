package org.firstinspires.ftc.teamcode.opmode.example;



import com.acmerobotics.dashboard.FtcDashboard;

import com.qualcomm.hardware.limelightvision.LLResult;

import com.qualcomm.hardware.limelightvision.Limelight3A;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.hardware.IMU;

import com.qualcomm.robotcore.util.ElapsedTime;



import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

import org.firstinspires.ftc.robotcore.external.stream.CameraStreamSource;



@TeleOp
@Disabled
public class LimeLight extends LinearOpMode {
    private Limelight3A limelight;

    IMU imu;

    ElapsedTime timer = new ElapsedTime();

    @Override

    public void runOpMode() throws InterruptedException {

        imu = hardwareMap.get(IMU.class, "imu");

        RevHubOrientationOnRobot.LogoFacingDirection logoDirection = RevHubOrientationOnRobot.LogoFacingDirection.RIGHT;

        RevHubOrientationOnRobot.UsbFacingDirection  usbDirection  = RevHubOrientationOnRobot.UsbFacingDirection.FORWARD;

        RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logoDirection, usbDirection);
        imu.initialize(new IMU.Parameters(orientationOnRobot));
        limelight = hardwareMap.get(Limelight3A.class, "limelight");

//        FtcDashboard.getInstance().startCameraStream((CameraStreamSource) limelight, 0); // To test

        telemetry.setMsTransmissionInterval(11);

        limelight.pipelineSwitch(0);

        limelight.start();



        imu.resetYaw();

        timer.reset();

        waitForStart();

        while (opModeIsActive()) {

            YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();

            telemetry.addData("Yaw (Z)", "%.2f Deg. (Heading)", orientation.getYaw(AngleUnit.DEGREES));

            limelight.updateRobotOrientation(orientation.getYaw(AngleUnit.DEGREES));

            LLResult result = limelight.getLatestResult();

            if (result != null) {

                if (result.isValid()) {

                    Pose3D botPoseMT1 = result.getBotpose();

                    Pose3D botPoseMT2 = result.getBotpose_MT2();

                    double x_MT1 = botPoseMT1.getPosition().x ;

                    double y_MT1 = botPoseMT1.getPosition().y;

                    double x_MT2 = botPoseMT2.getPosition().x;

                    double y_MT2 = botPoseMT2.getPosition().y;

                    telemetry.addData("MT1 Location:", "(" + x_MT1 + ", " + y_MT1 + ")");

                    telemetry.addData("MT2 Location:", "(" + x_MT2 + ", " + y_MT2 + ")");
                    telemetry.addData("HZ", 1/timer.time());

                }

            }



            if (gamepad1.a) {

                imu.resetYaw();

            }

            telemetry.update();
            timer.reset();

            if (isStopRequested()){
                limelight.stop();
            }
        }

    }

}
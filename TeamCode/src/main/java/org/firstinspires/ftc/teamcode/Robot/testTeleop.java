package org.firstinspires.ftc.teamcode.Robot;


import com.outoftheboxrobotics.photoncore.Photon;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp
@Photon
public class testTeleop extends LinearOpMode {
    private Robot robot;
    private double voltage;
    private ElapsedTime time = new ElapsedTime();
    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot(hardwareMap);
        waitForStart();
        while (opModeIsActive()){
            time.reset();
            robot.driveTrain.setDrivePowers(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
            voltage = robot.getVoltage();
            telemetry.addData("voltage", voltage);
            robot.periodic();
            telemetry.addData("RefreshRate", 1/time.time());
        }
    }
}

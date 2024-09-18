package org.firstinspires.ftc.teamcode.Robot;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
@Config
public class ServoTest extends LinearOpMode {
    public static String servoName = "";
    private Servo servo;
    public static double pos1 = 0, pos2 = 0, pos3 = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        servo = hardwareMap.get(Servo.class, servoName);
        waitForStart();
        while (opModeIsActive()){
            if(gamepad1.b){
                servo.setPosition(pos1);
            }
            if(gamepad1.a){
                servo.setPosition(pos2);
            }
            if(gamepad1.x){
                servo.setPosition(pos3);
            }
        }
    }
}

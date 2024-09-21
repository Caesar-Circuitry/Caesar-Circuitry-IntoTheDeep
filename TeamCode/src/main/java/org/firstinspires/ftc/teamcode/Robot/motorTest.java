package org.firstinspires.ftc.teamcode.Robot;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
@Config
public class motorTest extends LinearOpMode {
    private DcMotor motor;
    public static String motorName = "";
    public static boolean Reversed = false;
    public static boolean on = false;
    public static boolean tickTuner = false;
    public static double speed = .5;

    @Override
    public void runOpMode() throws InterruptedException {
        motor = hardwareMap.get(DcMotor.class, motorName);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        waitForStart();
        while (opModeIsActive()) {
            if (tickTuner) {
                motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                telemetry.addData("encoderTicks", motor.getCurrentPosition());
                telemetry.update();
            } else {
                if (Reversed) {
                    motor.setDirection(DcMotorSimple.Direction.REVERSE);
                }
                if (on) {
                    motor.setPower(speed);
                } else {
                    motor.setPower(0);
                }

            }
        }
    }
}

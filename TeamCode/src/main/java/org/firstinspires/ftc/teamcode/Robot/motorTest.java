package org.firstinspires.ftc.teamcode.Robot;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp
@Config
public class motorTest extends LinearOpMode {
    private DcMotor motor;
    public static String motorName = "";
    public static boolean Reversed = false;
    public static boolean on = false;
    public static boolean tickTuner = false;
    public static double speed = 0;
    private FtcDashboard dashboard = FtcDashboard.getInstance();
    private Telemetry dashboardTelemetry = dashboard.getTelemetry();

    @Override
    public void runOpMode() throws InterruptedException {
        motor = hardwareMap.get(DcMotor.class, motorName);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        waitForStart();
        while (opModeIsActive()) {
//            if (tickTuner) {
//                motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//                telemetry.addData("encoderTicks", motor.getCurrentPosition());
//                dashboardTelemetry.addData("encoderTicks", motor.getCurrentPosition());
//                dashboardTelemetry.update();
//                telemetry.update();
//            } else {
                motor.setPower(speed);

//            }
        }
    }
}

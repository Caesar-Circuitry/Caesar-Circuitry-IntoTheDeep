package org.firstinspires.ftc.teamcode.Robot;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.hardware.motors.Motor;
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
        motor = hardwareMap.get(DcMotor.class, "viper");
        if(Reversed){
            motor.setDirection(DcMotorSimple.Direction.REVERSE);
        }else{
            motor.setDirection(DcMotorSimple.Direction.FORWARD);
        }
        waitForStart();
        while (opModeIsActive()) {
                motor.setPower(speed);
        }
    }
}

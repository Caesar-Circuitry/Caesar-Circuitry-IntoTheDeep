package org.firstinspires.ftc.teamcode.Robot.CustomMath;


import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class PID {
    private static final double LIFT_TICKS_PER_IN = 125.5; // Example value, adjust based on your motor and gearing
    private DcMotor liftMotor;
    private Motor.Encoder liftEncoder;
    private double liftTargetPos_ticks;
    private double liftLastPos_ticks = 0;
    private double liftPower = 0;
    private double prevLiftPower = 0;
    public static double kp = 0,ki = 0,kd = 0;
    private PIDController liftController; // Assume you have a PIDController class implemented
    public PID(HardwareMap hardwareMap){
        // Initialize the hardware components
        liftMotor = hardwareMap.get(DcMotor.class, "viper");
        liftEncoder = new Motor(hardwareMap, "viper", Motor.GoBILDA.RPM_312).encoder;
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        liftController = new PIDController(kp, ki, kd); // Example PID constants, adjust as needed
        liftEncoder.reset();
    }
    public void liftRunToPosition(double pos_in, double speed_0to1) {
        liftTargetPos_ticks = pos_in * LIFT_TICKS_PER_IN;
        liftPower = liftController.calculate(liftLastPos_ticks, liftTargetPos_ticks) * speed_0to1;

            liftLastPos_ticks = liftEncoder.getPosition();

        if (liftPower != prevLiftPower) {
            liftMotor.setPower(liftPower);
        }

        prevLiftPower = liftPower;
    }
    public void updateConstants(){
        liftController.setPID(kp, ki, kd);
    }
}

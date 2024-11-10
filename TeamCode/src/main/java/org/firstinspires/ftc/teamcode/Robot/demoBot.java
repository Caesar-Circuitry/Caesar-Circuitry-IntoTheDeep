package org.firstinspires.ftc.teamcode.Robot;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
@Config
public class demoBot extends LinearOpMode {
    private DcMotor FRM,BRM,FLM, BLM;
    public static double multiplier = .5;
    @Override
    public void runOpMode() throws InterruptedException {
        FRM = hardwareMap.get(DcMotor.class, "FRM");
        BRM = hardwareMap.get(DcMotor.class, "BRM");
        FLM = hardwareMap.get(DcMotor.class,"FLM");
        BLM = hardwareMap.get(DcMotor.class,"BLM");
        FRM.setDirection(DcMotorSimple.Direction.REVERSE); //probaly need to adjust depending on omnis
        BRM.setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart();
        while (opModeIsActive()){
            drive();
        }
    }
    private void drive(){
        double Power = gamepad1.left_stick_y * multiplier;  // Forward and backward movement

// Optional: If you want to add turning capability using the right stick
        double turn = -gamepad1.right_stick_x * multiplier; // Turning left and right

// Combine the driving and turning for left and right motors
        double lf_power = Power + turn; // Left front power
        double lb_power = Power + turn; // Left back power
        double rf_power = Power - turn; // Right front power
        double rb_power = Power - turn; // Right back power

// Normalize the power values if they exceed 1
        double maxPower = Math.max(Math.abs(lf_power), Math.max(Math.abs(lb_power), Math.max(Math.abs(rf_power), Math.abs(rb_power))));
        if (maxPower > 1) {
            lf_power /= maxPower;
            lb_power /= maxPower;
            rf_power /= maxPower;
            rb_power /= maxPower;
        }

        FLM.setPower(lf_power);
        BLM.setPower(lb_power);
        FRM.setPower(rf_power);
        BRM.setPower(rb_power);

    }
}

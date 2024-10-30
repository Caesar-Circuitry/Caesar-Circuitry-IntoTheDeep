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
        FLM.setDirection(DcMotorSimple.Direction.REVERSE);
        BLM.setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart();
        while (opModeIsActive()){
            drive();
        }
    }
    private void drive(){
        double x = -gamepad1.left_stick_x * multiplier;
        double y = gamepad1.left_stick_y * multiplier;
        double turn = gamepad1.right_stick_x/1.2 * multiplier;

        double theta = Math.atan2(y,x);
        double power = Math.hypot(x,y);

        double sin = Math.sin(theta -Math.PI/4);
        double cos = Math.cos(theta -Math.PI/4);
        double max = Math.max(Math.abs(sin), Math.abs(cos));

        double lf_power = power * cos/max + turn;
        double lb_power = power * sin/max + turn;
        double rf_power = power * sin/max - turn;
        double rb_power = power * cos/max - turn;

        if((power + Math.abs(turn)) > 1) {
            lf_power /= power + turn;
            lb_power /= power + turn;
            rf_power /= power + turn;
            rb_power /= power + turn;
        }
        FLM.setPower(lf_power);
        BLM.setPower(lb_power);
        FRM.setPower(rf_power);
        BRM.setPower(rb_power);

    }
}

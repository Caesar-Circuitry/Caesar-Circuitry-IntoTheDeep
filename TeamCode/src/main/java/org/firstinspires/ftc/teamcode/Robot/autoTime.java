package org.firstinspires.ftc.teamcode.Robot;


import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.List;

@Autonomous
public class autoTime extends LinearOpMode {
    private List<LynxModule> allHubs;
    private DcMotor FRM,BRM,FLM,BLM,armRotate;
    private viperRotate rotate;
    private Servo intakeWrist;
    private double lf_power, lb_power, rf_power, rb_power;
    public double neutral = 25;
    double x = 0;
    private ElapsedTime time = new ElapsedTime();
    @Override
    public void runOpMode() throws InterruptedException {
        allHubs = hardwareMap.getAll(LynxModule.class);
        for (LynxModule hub : allHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }
        FRM = hardwareMap.get(DcMotor.class, "FRM");
        BRM = hardwareMap.get(DcMotor.class, "BRM");
        FLM = hardwareMap.get(DcMotor.class,"FLM");
        BLM = hardwareMap.get(DcMotor.class,"BLM");
        FRM.setDirection(DcMotorSimple.Direction.REVERSE);
        BRM.setDirection(DcMotorSimple.Direction.REVERSE);
        FRM.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BRM.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FLM.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BLM.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armRotate = hardwareMap.get(DcMotor.class,"armRotate");
        armRotate.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rotate = new viperRotate(armRotate);

        intakeWrist = hardwareMap.get(Servo.class, "intakeWrist"); //port 1 control hub//
        waitForStart();
        while (opModeIsActive()){
            FRM.setPower(1);
            FLM.setPower(1);
            sleep(3000);
            FRM.setPower(0);
            FLM.setPower(0);
            break;
        }

    }
    private void drive() {
        double y = 0;
        double turn = 0;

        double theta = Math.atan2(y, x);
        double power = Math.hypot(x, y);

        double sin = Math.sin(theta - Math.PI / 4);
        double cos = Math.cos(theta - Math.PI / 4);
        double max = Math.max(Math.abs(sin), Math.abs(cos));

        lf_power = power * cos / max + turn;
        lb_power = power * sin / max + turn;
        rf_power = power * sin / max - turn;
        rb_power = power * cos / max - turn;

        if ((power + Math.abs(turn)) > 1) {
            lf_power /= power + turn;
            lb_power /= power + turn;
            rf_power /= power + turn;
            rb_power /= power + turn;
        }
    }
    }

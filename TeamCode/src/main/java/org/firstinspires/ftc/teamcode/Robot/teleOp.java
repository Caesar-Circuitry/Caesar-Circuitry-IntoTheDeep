package org.firstinspires.ftc.teamcode.Robot;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Robot.CustomMath.PDFL;

import java.util.List;

@TeleOp
@Config
public class teleOp extends LinearOpMode {
    private List<LynxModule> allHubs;
    private DcMotor FRM,BRM,FLM,BLM,armRotate,viper;
    private viperRotate rotate;
    private Servo intakeWrist;
    private CRServo intake;
    private double lf_power, lb_power, rf_power, rb_power;
    public static double tunePos = 0;
    private enum rotatePos{
        ZERO,
        NEUTRAL,
        BASKET,
        BAR,
        BARDOWN,
        HANG,
        HANGDOWN

    }
    private double zeroAngle = 230, neutralAngle = 25, intakeSample  = 255,basketAngle = 150, barAngle = 150, barDownAngle = 155, HangAngle = 125, HANGDOWN = 360, wristLeft = .35, wristCenter = .67, wristRight = 1, multiplier =1;
    private rotatePos rotatePosition = rotatePos.ZERO, targetRotatePosition = rotatePos.ZERO;
    private boolean tog = false;
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
        intake = hardwareMap.get(CRServo.class, "intake"); //port 0 control hub//


        waitForStart();

        while (opModeIsActive()){
            drive();
            if (gamepad1.right_trigger>0 || gamepad2.right_trigger>0){
                intake.setPower(-1);
            } else if (gamepad1.left_trigger>0 || gamepad2.left_trigger>0) {
                intake.setPower(1);
            }else{
                intake.setPower(0);
            }

            if(gamepad1.dpad_left || gamepad2.dpad_left){
                intakeWrist.setPosition(wristLeft);
            } else if (gamepad1.dpad_up || gamepad2.dpad_up) {
                intakeWrist.setPosition(wristCenter);
            } else if (gamepad1.dpad_right || gamepad2.dpad_right) {
                intakeWrist.setPosition(wristRight);
            }

            if(gamepad1.b){
                rotate.setTargetAngle(neutralAngle);
                intakeWrist.setPosition(wristCenter);
            } else if (gamepad2.a) {
                rotate.setTargetAngle(basketAngle);
                intakeWrist.setPosition(wristCenter);
            } else if (gamepad2.left_bumper) {
                rotate.setTargetAngle(barAngle);
                intakeWrist.setPosition(wristLeft);
            } else if (gamepad2.right_bumper) {
                rotate.setTargetAngle(barDownAngle);
            } else if (gamepad2.y) {
                rotate.setTargetAngle(HANGDOWN);
                intakeWrist.setPosition(wristCenter);
            } else if (gamepad2.x) {
                rotate.setTargetAngle(HangAngle);
                intakeWrist.setPosition(wristCenter);
            } else if (gamepad2.dpad_down) {
                rotate.setTargetAngle(zeroAngle);
                intakeWrist.setPosition(wristCenter);
            } else if (gamepad2.b) {
                rotate.setTargetAngle(intakeSample);
                intakeWrist.setPosition(wristCenter);
            }
            if(gamepad1.left_bumper){
                multiplier = .2;
            }else{
                multiplier = 1;
            }
            update();
            for (LynxModule hub : allHubs) {
                hub.clearBulkCache();
            }
        }

    }
    private void drive(){
        double x = -gamepad1.left_stick_x * multiplier;
        double y = gamepad1.left_stick_y * multiplier;
        double turn = -gamepad1.right_stick_x/1.2;

        double theta = Math.atan2(y,x);
        double power = Math.hypot(x,y);

        double sin = Math.sin(theta -Math.PI/4);
        double cos = Math.cos(theta -Math.PI/4);
        double max = Math.max(Math.abs(sin), Math.abs(cos));

        lf_power = power * cos/max + turn;
        lb_power = power * sin/max + turn;
        rf_power = power * sin/max - turn;
        rb_power = power * cos/max - turn;

        if((power + Math.abs(turn)) > 1) {
            lf_power /= power + turn;
            lb_power /= power + turn;
            rf_power /= power + turn;
            rb_power /= power + turn;
        }

    }
    private void tuneViper(){
        rotate.setTargetAngle(tunePos);
        rotate.periodic();
    }
    private void update(){
        FLM.setPower(lf_power);
        BLM.setPower(lb_power);
        FRM.setPower(rf_power);
        BRM.setPower(rb_power);


        rotate.periodic();
    }
}

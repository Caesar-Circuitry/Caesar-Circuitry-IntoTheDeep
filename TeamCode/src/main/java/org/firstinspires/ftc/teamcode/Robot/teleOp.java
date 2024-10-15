package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Robot.CustomMath.PDFL;

import java.util.List;


public class teleOp extends LinearOpMode {
    private List<LynxModule> allHubs;
    private DcMotor FRM,BRM,FLM,BLM,armRotate,viper;
    private viperRotate rotate;
    private Servo intakeWrist;
    private CRServo intake;
    private double lf_power, lb_power, rf_power, rb_power;
    private enum rotatePos{
        ZERO,
        NEUTRAL,
        BASKET,
        BAR,
        HANG
    }
    private double zeroAngle = 0, neutralAngle = 45,basketAngle = 130, barAngle = 90, HangAngle = 130, wristLeft = 0, wristCenter = .5, wristRight = 1;
    private rotatePos rotatePosition = rotatePos.NEUTRAL, targetRotatePosition = rotatePos.NEUTRAL;
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
        FLM.setDirection(DcMotorSimple.Direction.REVERSE);
        BLM.setDirection(DcMotorSimple.Direction.REVERSE);
        FRM.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BRM.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FLM.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BLM.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armRotate = hardwareMap.get(DcMotor.class,"armRotate");
        rotate = new viperRotate(armRotate);

        intakeWrist = hardwareMap.get(Servo.class, "intakeWrist");
        intake = hardwareMap.get(CRServo.class, "intake");


        waitForStart();

        while (opModeIsActive()){
            drive();
            if (gamepad1.right_trigger>0 || gamepad2.right_trigger>0){
                intake.setPower(1);
            } else if (gamepad1.left_trigger>0 || gamepad2.left_trigger>0) {
                intake.setPower(-1);
            }else{
                intake.setPower(0);
            }

            if(gamepad1.dpad_left || gamepad2.dpad_left){
                intakeWrist.setPosition(wristLeft);
            } else if (gamepad1.dpad_up || gamepad2.dpad_up) {
                intakeWrist.setPosition(wristCenter);
            } else if (gamepad1.dpad_right || gamepad2.dpad_up) {
                intakeWrist.setPosition(wristRight);
            }

            if(gamepad2.b || gamepad1.b){
                targetRotatePosition = rotatePos.NEUTRAL;
                intakeWrist.setPosition(wristCenter);
            } else if (gamepad2.a) {
                targetRotatePosition = rotatePos.BASKET;
            } else if (gamepad2.x) {
                targetRotatePosition =rotatePos.BAR;
            } else if (gamepad2.y) {
                targetRotatePosition = rotatePos.ZERO;
            } else if (gamepad1.a) {
                targetRotatePosition = rotatePos.HANG;
            }
            update();

            for (LynxModule hub : allHubs) {
                hub.clearBulkCache();
            }
        }

    }
    private void drive(){
        double x = -gamepad1.left_stick_x;
        double y = gamepad1.left_stick_y;
        double turn = -gamepad1.right_stick_x;

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

    private void update(){
        FLM.setPower(lf_power);
        BLM.setPower(lb_power);
        FRM.setPower(rf_power);
        BRM.setPower(rb_power);
        switch (rotatePosition){
            case ZERO:
                if(targetRotatePosition != rotatePos.ZERO){
                    rotate.setTargetAngle(neutralAngle);
                    if (rotate.getAngle() == neutralAngle){
                        rotatePosition = rotatePos.NEUTRAL;
                    }
                }
                break;
            case NEUTRAL:
                switch (targetRotatePosition){
                    case HANG:
                        rotate.setTargetAngle(HangAngle);
                        if (rotate.getAngle() == HangAngle){
                            rotatePosition = rotatePos.HANG;
                        }
                        break;
                    case BASKET:
                        rotate.setTargetAngle(basketAngle);
                        if (rotate.getAngle() == basketAngle){
                            rotatePosition = rotatePos.BASKET;
                        }
                        break;
                    case ZERO:
                        rotate.setTargetAngle(zeroAngle);
                        if (rotate.getAngle() == zeroAngle){
                            rotatePosition = rotatePos.ZERO;
                        }
                        break;
                    case BAR:
                        rotate.setTargetAngle(barAngle);
                        if (rotate.getAngle() == barAngle){
                            rotatePosition = rotatePos.BAR;
                        }
                        break;
                    case NEUTRAL:
                        break;
                }
                break;
            case BASKET:
                if(targetRotatePosition != rotatePos.BASKET){
                    rotate.setTargetAngle(neutralAngle);
                    if (rotate.getAngle() == neutralAngle){
                        rotatePosition = rotatePos.NEUTRAL;
                    }
                }
                break;
            case BAR:
                if(targetRotatePosition != rotatePos.BAR){
                    rotate.setTargetAngle(neutralAngle);
                    if (rotate.getAngle() == neutralAngle){
                        rotatePosition = rotatePos.NEUTRAL;
                    }
                }
                break;
            case HANG:
                if(targetRotatePosition != rotatePos.HANG){
                    rotate.setTargetAngle(neutralAngle);
                    if (rotate.getAngle() == neutralAngle){
                        rotatePosition = rotatePos.NEUTRAL;
                    }
                }
                break;
        }


        rotate.periodic();
    }
}

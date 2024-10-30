package org.firstinspires.ftc.teamcode.Robot;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Robot.CustomMath.PDFL;
import org.firstinspires.ftc.teamcode.Robot.CustomMath.PID;

import java.util.List;

@TeleOp
@Config
public class teleOp extends LinearOpMode {
    private List<LynxModule> allHubs;
    private DcMotor FRM,BRM,FLM,BLM,armRotate,viper;
    private viperRotate rotate;
    private double lf_power, lb_power, rf_power, rb_power;
    public static double tunePos = 0, pos_in = 0;
    private static final double LIFT_TICKS_PER_IN = -104.7; // Example value, adjust based on your motor and gearing
    private DcMotor liftMotor;
    private Motor.Encoder liftEncoder;
    private Servo clawWrist,claw;
    private double liftTargetPos_ticks;
    private double liftLastPos_ticks = 0;
    private double liftPower = 0;
    private double prevLiftPower = 0;
    public static double kp = 0.01,ki = 0,kd = 0;
    private PIDController liftController; // Assume you have a PIDController class implemented

    public static double zeroAngle = 230, neutralAngle = 25, floorAngle = 13, intakeSample  = 255, intakeSpecimen = 45,basketAngle = 125, HangAngle = 150, barDownAngle = 158,
    BarUpAngle = 90, HANGDOWN = 360, clawOpen =.9, clawClosed = .62, clawWristPickup = .05, clawWristBucket = 1, clawWristSpecimen = .5, clawWristFloor= .05, multiplier =1,
    viperbasket = 17, viperZero = 0, viperBar = 6;
    @Override
    public void runOpMode() throws InterruptedException {
        allHubs = hardwareMap.getAll(LynxModule.class);
        for (LynxModule hub : allHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }
        liftMotor = hardwareMap.get(DcMotor.class, "viper");
        liftEncoder = new Motor(hardwareMap, "viper", Motor.GoBILDA.RPM_312).encoder;
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        liftController = new PIDController(kp, ki, kd); // Example PID constants, adjust as needed
        liftEncoder.reset();
        viper = hardwareMap.get(DcMotor.class, "viper");
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

        clawWrist = hardwareMap.get(Servo.class, "clawWrist"); //port 1 control hub//
        claw = hardwareMap.get(Servo.class, "claw"); //port 0 control hub//


        waitForStart();

        while (opModeIsActive()){
            drive();
            if (gamepad1.right_trigger>0 || gamepad2.right_trigger>0){
                claw.setPosition(clawClosed);
            } else if (gamepad1.left_trigger>0 || gamepad2.left_trigger>0) {
                claw.setPosition(clawOpen);
            }

            if(gamepad1.dpad_left || gamepad2.dpad_left){
                clawWrist.setPosition(clawWristBucket);
            } else if (gamepad1.dpad_right || gamepad2.dpad_right) {
                clawWrist.setPosition(clawWristPickup);
            }else if (gamepad2.dpad_up) {
                clawWrist.setPosition(clawWristSpecimen);
            }

            if(gamepad1.b || gamepad2.b){
                pos_in = viperZero;
                rotate.setTargetAngle(neutralAngle);
                clawWrist.setPosition(clawWristPickup);
            } else if (gamepad2.a) {
                pos_in = viperbasket;
                rotate.setTargetAngle(basketAngle);
                clawWrist.setPosition(clawWristBucket);
            } else if (gamepad2.left_bumper) {
                clawWrist.setPosition(clawWristFloor);
                pos_in = viperZero;
                rotate.setTargetAngle(floorAngle);
            } else if (gamepad2.right_bumper) {
                pos_in = viperZero;
                rotate.setTargetAngle(intakeSpecimen);
                clawWrist.setPosition(clawWristSpecimen);
            } else if (gamepad2.y) {
                pos_in = viperZero;
                rotate.setTargetAngle(BarUpAngle);
                clawWrist.setPosition(clawWristPickup);
            } else if (gamepad2.x) {
                pos_in = viperBar;
                rotate.setTargetAngle(BarUpAngle);
                clawWrist.setPosition(clawWristPickup);
            } else if (gamepad2.dpad_down) {
                rotate.setTargetAngle(zeroAngle);
                clawWrist.setPosition(clawWristBucket);
            }
            if(gamepad1.left_bumper){
                multiplier = .2;
            }else{
                multiplier = 1;
            }
            update();
            liftRunToPosition(1);
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
    public void liftRunToPosition(double speed_0to1) {
        liftTargetPos_ticks = pos_in * LIFT_TICKS_PER_IN;
        liftPower = liftController.calculate(liftLastPos_ticks, liftTargetPos_ticks) * speed_0to1;

        liftLastPos_ticks = liftEncoder.getPosition();

        if (liftPower != prevLiftPower) {
            liftMotor.setPower(liftPower);
        }

        prevLiftPower = liftPower;
    }
    private void update(){
        FLM.setPower(lf_power);
        BLM.setPower(lb_power);
        FRM.setPower(rf_power);
        BRM.setPower(rb_power);


        rotate.periodic();
    }
}

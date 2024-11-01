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
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Robot.CustomMath.PDFL;
import org.firstinspires.ftc.teamcode.Robot.CustomMath.PID;

import java.util.List;
import java.util.Timer;

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
    private ElapsedTime time;
    private enum Pos{
        ZERO,
        NEUTRAL,
        FLOOR,
        INT_SAMPLE,
        INT_SPECIMEN,
        BASKET_ANGLE,
        HANG_ANGLE,
        BAR_ANGLE
    }
    private Pos targetPos = Pos.ZERO;
    private Pos actualPos = Pos.ZERO;

    public static double neutralAngle = 25, floorAngle = 13, intakeSample  = 255, intakeSpecimen = 45,basketAngle = 125, HangAngle = 150, BarUpAngle = 90, HANGDOWNANGLE = 360,
            clawOpen =.9, clawClosed = .62, clawWristPickup = .05, clawWristBucket = 1, clawWristSpecimen = .5, clawWristFloor= .05, multiplier =1,
    viperbasket = 17, viperZero = 0, viperBar = 6;
    private boolean firstTime = true, dirState = true; //dirState true up false down
    @Override
    public void runOpMode() throws InterruptedException {
        time = new ElapsedTime();
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
        time.reset();
        waitForStart();

        while (opModeIsActive()){
            drive();
           if(gamepad2.a){
               targetPos = Pos.BASKET_ANGLE;
               firstTime = true;
           } else if (gamepad2.x) {
               targetPos = Pos.BAR_ANGLE;
               firstTime = true;
           } else if (gamepad2.y) {
               pos_in = 0;
           } else if (gamepad2.b && gamepad1.b) {
               targetPos = Pos.NEUTRAL;
               firstTime = true;
           } else if (gamepad2.right_bumper) {
               pos_in+=1;
           } else if (gamepad2.left_bumper) {
               pos_in-=1;
           } else if (gamepad2.dpad_up) {
               targetPos = Pos.INT_SPECIMEN;
               firstTime = true;
           }else if (gamepad2.dpad_down){
               targetPos = Pos.INT_SAMPLE;
               firstTime = true;
           }
            telemetry.addData("target pos", targetPos);
            telemetry.addData("actual pos", actualPos);
            telemetry.addData("viper pos", liftLastPos_ticks * LIFT_TICKS_PER_IN);
            states();
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

//    boolean firstTime = true;
//    dirState = //Can be up or down

    private void states(){
        switch (targetPos){

//            case NEUTRAL:
//                if (firstTime == true){
//
//                    firstTime = false;
//                    if (viper slide needs to go){
//                        dirState = up;
//                    }
//                    else{
//                        dirState = down;
//                    }
//                }
//
//                if (dirState = up){
//                    //Set ViperSlide
//                    if (viper slide has reached final postion)
//                    {
//                        //Set Angle
//                    }
//                }
//                else (dirState = down) {
//                    //Set Angle
//                    if(angle has reached final postion){
//                     //Set Viper Slide
//                    }
//                }
//            }

            case NEUTRAL:
                if (firstTime){
                    firstTime = false;
                    if(liftLastPos_ticks * LIFT_TICKS_PER_IN <= viperZero) {
                        dirState = true;
                    }
                    else{
                        dirState = false;
                    }
                }
                dirCode(neutralAngle, viperZero);
                break;
            case FLOOR:
                if (firstTime){
                    firstTime = false;
                    if(liftLastPos_ticks * LIFT_TICKS_PER_IN <= viperZero) {
                        dirState = true;
                    }
                    else{
                        dirState = false;
                    }
                }
                dirCode(floorAngle, viperZero);
                break;
            case HANG_ANGLE:
                if (actualPos != targetPos){
                    pos_in =viperZero;
                    clawWrist.setPosition(clawWristBucket);

                }
                break;
            case INT_SAMPLE:
                if (firstTime){
                    firstTime = false;
                    if(liftLastPos_ticks * LIFT_TICKS_PER_IN <= viperZero) {
                        dirState = true;
                    }
                    else{
                        dirState = false;
                    }
                }
                dirCode(intakeSample, viperZero);
                break;
            case BAR_ANGLE:
                if (firstTime){
                    firstTime = false;
                    if(liftLastPos_ticks * LIFT_TICKS_PER_IN <= viperZero) {
                        dirState = true;
                    }
                    else{
                        dirState = false;
                    }
                }
                dirCode(BarUpAngle, viperBar);
                break;
            case BASKET_ANGLE:
                if (firstTime){
                    firstTime = false;
                    if(liftLastPos_ticks * LIFT_TICKS_PER_IN <= viperZero) {
                        dirState = true;
                    }
                    else{
                        dirState = false;
                    }
                }
                dirCode(basketAngle, viperbasket);
                break;
            case INT_SPECIMEN:
                if (firstTime){
                    firstTime = false;
                    if(liftLastPos_ticks * LIFT_TICKS_PER_IN <= viperZero) {
                        dirState = true;
                    }
                    else{
                        dirState = false;
                    }
                }
                dirCode(intakeSpecimen, viperZero);
                break;
        }
    }
    private void dirCode(double rotPos, double viperPos){
        if(!dirState){//down
            pos_in = viperPos;
            if(liftLastPos_ticks * LIFT_TICKS_PER_IN == viperPos){
                rotate.setTargetAngle(rotPos);
            }
        }else{//up
            rotate.setTargetAngle(rotPos);
            if (rotate.getAngle() == rotPos){
                pos_in = viperPos;
            }
        }
    }
}

package org.firstinspires.ftc.teamcode.paths.actions;


import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Robot.viperRotate;
@Config
public class armActions {
    private viperRotate rotate;
    private static final double LIFT_TICKS_PER_IN = -125.5; // Example value, adjust based on your motor and gearing
    private DcMotor liftMotor, armRotate, viper;
    public static double viperTolerence = 1;
    private Motor.Encoder liftEncoder;
    private Servo clawWrist, claw;
    private double liftTargetPos_ticks;
    private double liftLastPos_ticks = 0;
    private double liftPower = 0;
    private double prevLiftPower = 0;
    private boolean overide;
    public static double kp = 0.01, ki = 0, kd = 0;
    private PIDController liftController; // Assume you have a PIDController class implemented
    private double rotateAngle = 0, viperPos = 0, pos_in = 0, clawWristPos = 0, clawPos = 0;
    public static double neutralAngle = 25, SUBAngle = 13, intakeSample = 255, intakeSpecimen = 30, basketAngle = 125, HangAngle = 150, BarUpAngle = 78, HANGDOWNANGLE = 360,
            clawOpen = .4, clawClosed = 0, clawWristPickup = .05, clawWristBucket = 1, clawWristSpecimen = 0.5, clawWristSUB = .05, multiplier = 1,
            viperbasket = 17, viperZero = .1, viperBar = 5.5;
    private boolean firstTime = true, dirState = true; //dirState true up false down

    public armActions(HardwareMap hardwareMap) {
        liftMotor = hardwareMap.get(DcMotor.class, "viper");
        liftEncoder = new Motor(hardwareMap, "viper", Motor.GoBILDA.RPM_312).encoder;
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        liftController = new PIDController(kp, ki, kd); // Example PID constants, adjust as needed
        liftEncoder.reset();
        viper = hardwareMap.get(DcMotor.class, "viper");
        armRotate = hardwareMap.get(DcMotor.class, "armRotate");
        armRotate.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armRotate.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rotate = new viperRotate(armRotate);
        overide = false;
        clawWrist = hardwareMap.get(Servo.class, "clawWrist"); //port 1 control hub//
        claw = hardwareMap.get(Servo.class, "claw"); //port 0 control hub//
    }

    public class barUp implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (liftLastPos_ticks / LIFT_TICKS_PER_IN <= viperBar) {
                dirState = true;
            } else {
                dirState = false;
            }
            overide = false;
            rotateAngle = BarUpAngle;
            viperPos = viperBar;
            clawWristPos = clawWristPickup;
            return false;
        }
    }
    public class traverse implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (liftLastPos_ticks / LIFT_TICKS_PER_IN <= viperBar) {
                dirState = true;
            } else {
                dirState = false;
            }
            overide = false;
            rotateAngle = 20;
            viperPos = 0;
            clawWristPos = 0;
            return false;
        }
    }
    public class hold implements Action{

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            overide = true;
            return false;
        }
    }

    public class barDown implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (liftLastPos_ticks / LIFT_TICKS_PER_IN <= viperZero) {
                dirState = true;
            } else {
                dirState = false;
            }
            overide = false;
            rotateAngle = BarUpAngle;
            viperPos = viperZero;
            clawWristPos = clawWristPickup;
            return false;
        }
    }
    public class intake implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (liftLastPos_ticks / LIFT_TICKS_PER_IN <= viperZero) {
                dirState = true;
            } else {
                dirState = false;
            }
            overide = false;
            rotateAngle = intakeSpecimen;
            viperPos = viperZero;
            clawWristPos = clawWristSpecimen;
            return false;
        }
    }
    public class clawOpen implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            claw.setPosition(clawOpen);
            return false;
        }
    }

    public class clawClose implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            claw.setPosition(clawClosed);
            return false;
        }
    }
    public class wristIntake implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            clawWrist.setPosition(clawWristSpecimen + .14);
            return false;
        }
    }


    public class periodic implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            dirCode(rotateAngle, viperPos, clawWristPos);
            if (overide){
                armRotate.setPower(-.1);
            }else {
                rotate.periodic();
                liftRunToPosition(1);
            }
            return true;
        }
    }

    private void liftRunToPosition(double speed_0to1) {
        liftTargetPos_ticks = pos_in * LIFT_TICKS_PER_IN;
        liftPower = liftController.calculate(liftLastPos_ticks, liftTargetPos_ticks) * speed_0to1;

        liftLastPos_ticks = liftEncoder.getPosition();

        if (liftPower != prevLiftPower) {
            liftMotor.setPower(liftPower);
        }

        prevLiftPower = liftPower;
    }

    private void dirCode(double rotPos, double viperPos, double wristPos) {
        if (!dirState) {//down
            clawWrist.setPosition(wristPos);
            pos_in = viperPos;
            if (liftLastPos_ticks / LIFT_TICKS_PER_IN <= viperPos + viperTolerence && liftLastPos_ticks / LIFT_TICKS_PER_IN >= viperPos - viperTolerence) {
                rotate.setTargetAngle(rotPos);
            }
        } else if (dirState) {//up
            rotate.setTargetAngle(rotPos);
            if (rotate.getAngle() <= rotPos + 1 && rotate.getAngle() >= rotPos - 1) {
                pos_in = viperPos;
                if (liftLastPos_ticks / LIFT_TICKS_PER_IN <= viperPos + viperTolerence && liftLastPos_ticks / LIFT_TICKS_PER_IN >= viperPos - viperTolerence) {
                    clawWrist.setPosition(wristPos);
                }
            }
        }

    }
    public Action barUp(){
        return new barUp();
    }
    public Action barDown(){
        return new barDown();
    }
    public Action intake(){
        return new intake();
    }
    public Action periodic(){
        return new periodic();
    }
    public Action clawOpen(){
        return new clawOpen();
    }
    public Action clawClose(){
        return new clawClose();
    }
    public Action traverse(){
        return new traverse();
    }
    public Action hold(){
        return new hold();
    }
    public Action wristIntake(){return new wristIntake();}
}

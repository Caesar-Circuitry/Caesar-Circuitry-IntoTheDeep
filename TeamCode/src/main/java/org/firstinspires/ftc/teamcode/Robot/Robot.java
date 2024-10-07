package org.firstinspires.ftc.teamcode.Robot;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.List;


@Config
public class Robot {
    private ViperSlide viperVertical;
    private List<LynxModule> allHubs;
    public driveTrain driveTrain;
    public viperRotate rotate;
    private DcMotor
    /*Drive Train*/ frontLeftMotor,frontRightMotor,backLeftMotor,backRightMotor,
    /*Viper Slides*/ viperVerticalMotor, viperRotate,
    /*Intake*/ intakeMotor;
    private Servo
    /*Claw*/ claw, clawPivot, clawRotate,
    /*v4bar*/ v4BarRotateLeft, v4BarRotateRight,
    /*Intake*/ intakePivot;
    private double voltage;
    public double viperRotateHome = 10, viperRotateMiddle = 90,viperRotateBucket = 140;
    public static double targetRotate = 0, targetViper = 0;

    public Robot(HardwareMap hardwareMap){
        allHubs = hardwareMap.getAll(LynxModule.class);
        for (LynxModule hub : allHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }
        frontLeftMotor = hardwareMap.get(DcMotor.class,"FLM");
        frontRightMotor = hardwareMap.get(DcMotor.class,"FRM");
        backLeftMotor = hardwareMap.get(DcMotor.class,"BLM");
        backRightMotor = hardwareMap.get(DcMotor.class,"BRM");

        viperRotate = hardwareMap.get(DcMotor.class,"armRotate");
        viperRotate.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        viperRotate.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        viperRotate.setDirection(DcMotorSimple.Direction.REVERSE);

        viperVerticalMotor = hardwareMap.get(DcMotor.class,"viper");
        viperVerticalMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        viperVerticalMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        rotate = new viperRotate(viperRotate);
        viperVertical = new ViperSlide(viperVerticalMotor);

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        
        driveTrain = new driveTrain(frontLeftMotor,frontRightMotor,backLeftMotor,backRightMotor);
    }

    public void resetEncoders(){
        viperVerticalMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        viperRotate.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        viperVerticalMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        viperRotate.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void periodic(){//recommended that used at end of loop
        viperVertical.periodic();
        driveTrain.periodic();
        rotate.setTargetAngle(targetRotate);
        viperVertical.setTargetPos(targetViper);
        rotate.periodic();

        //Should be last thing
        for (LynxModule hub : allHubs) {
            hub.clearBulkCache();
        }
    }
    public double getVoltage(){
        return voltage;
    }
    public void rotateHome(){
        //this.rotate.setTargetAngle(viperRotateHome);
    }
    public void rotateMiddle(){
        //this.rotate.setTargetAngle(viperRotateMiddle);
    }
    public void rotateBucket(){
        //this.rotate.setTargetAngle(viperRotateBucket);
    }


}

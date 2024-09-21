package org.firstinspires.ftc.teamcode.Robot;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.outoftheboxrobotics.photoncore.Photon;
import com.outoftheboxrobotics.photoncore.hardware.PhotonLynxVoltageSensor;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.List;


@Config
public class Robot {
    private GlobalVars vars;
    private ViperSlide viperVertical, viperHoriontal;
    private List<LynxModule> allHubs;
    //private PhotonLynxVoltageSensor voltageSensor;
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

    public Robot(HardwareMap hardwareMap){
        allHubs = hardwareMap.getAll(LynxModule.class);
        for (LynxModule hub : allHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }
        //voltageSensor = hardwareMap.getAll(PhotonLynxVoltageSensor.class).iterator().next();
        vars = new GlobalVars(new Pose2d(0,0,0));
        //viperVertical = new ViperSlide(viperVerticalMotor, vars);
        frontLeftMotor = hardwareMap.get(DcMotor.class,"FLM");
        frontRightMotor = hardwareMap.get(DcMotor.class,"FRM");
        backLeftMotor = hardwareMap.get(DcMotor.class,"BLM");
        backRightMotor = hardwareMap.get(DcMotor.class,"BRM");

        viperRotate = hardwareMap.get(DcMotor.class,"armRotate");
        viperRotate.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rotate = new viperRotate(viperRotate);

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        
        driveTrain = new driveTrain(frontLeftMotor,frontRightMotor,backLeftMotor,backRightMotor);
    }

    public void periodic(){//recommended that used at end of loop
        //viperVertical.periodic();
        //viperHoriontal.periodic();

        //driveTrain.periodic();
        //voltage = voltageSensor.getCachedVoltage();
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
        this.rotate.setTargetAngle(viperRotateHome);
    }
    public void rotateMiddle(){
        this.rotate.setTargetAngle(viperRotateMiddle);
    }
    public void rotateBucket(){
        this.rotate.setTargetAngle(viperRotateBucket);
    }


}

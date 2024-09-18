package org.firstinspires.ftc.teamcode.Robot;

import com.acmerobotics.roadrunner.Pose2d;
import com.outoftheboxrobotics.photoncore.Photon;
import com.outoftheboxrobotics.photoncore.hardware.PhotonLynxVoltageSensor;
import com.outoftheboxrobotics.photoncore.hardware.motor.PhotonAdvancedDcMotor;
import com.outoftheboxrobotics.photoncore.hardware.servo.PhotonServo;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;

import java.util.List;

@Photon
public class Robot {
    private GlobalVars vars;
    private ViperSlide viperVertical, viperHoriontal;
    private List<LynxModule> allHubs;
    private PhotonLynxVoltageSensor voltageSensor;
    public driveTrain driveTrain;
    private DcMotor
    /*Drive Train*/ frontLeftMotor,frontRightMotor,backLeftMotor,backRightMotor,
    /*Viper Slides*/ viperVerticalMotor, viperHorizontalMotor,
    /*Intake*/ intakeMotor;
    private Servo
    /*Claw*/ claw, clawPivot, clawRotate,
    /*v4bar*/ v4BarRotateLeft, v4BarRotateRight,
    /*Intake*/ intakePivot;
    private double voltage;

    public Robot(HardwareMap hardwareMap){
        allHubs = hardwareMap.getAll(LynxModule.class);
        for (LynxModule hub : allHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }
        vars = new GlobalVars(new Pose2d(0,0,0));
        //viperVertical = new ViperSlide(viperVerticalMotor, vars);
        frontLeftMotor = hardwareMap.get(DcMotor.class,"FLM");
        frontRightMotor = hardwareMap.get(DcMotor.class,"FRM");
        backLeftMotor = hardwareMap.get(DcMotor.class,"BLM");
        backRightMotor = hardwareMap.get(DcMotor.class,"BRM");

        driveTrain = new driveTrain(frontLeftMotor,frontRightMotor,backLeftMotor,backRightMotor);
    }

    public void periodic(){//recommended that used at end of loop
        //viperVertical.periodic();
        //viperHoriontal.periodic();
        driveTrain.periodic();
        voltage = voltageSensor.getCachedVoltage();



        //Should be last thing
        for (LynxModule hub : allHubs) {
            hub.clearBulkCache();
        }
    }
    public double getVoltage(){
        return voltage;
    }

}

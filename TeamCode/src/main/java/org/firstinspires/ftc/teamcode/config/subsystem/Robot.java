package org.firstinspires.ftc.teamcode.config.subsystem;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.ImuOrientationOnRobot;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.List;


public class Robot {

    RevHubOrientationOnRobot orientation = new RevHubOrientationOnRobot(
            RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
            RevHubOrientationOnRobot.UsbFacingDirection.UP);

    private HardwareMap hardwareMap;
    List<LynxModule> allHubs;
    /*motors*/
        /*Drive Motors*/
            private DcMotorEx FRM; // Port -
            private DcMotorEx FLM; // Port -
            private DcMotorEx BRM; // Port -
            private DcMotorEx BLM; // Port -
        /*ViperMotors*/
            /*Vertical*/
                private DcMotorEx vertLeft; // Port -
                private DcMotorEx vertRight; // Port -
            /*Horizontal*/
                private DcMotorEx horzLeft; // Port -
                private DcMotorEx horzRight; // Port -
        /*ViperEncoders*/
            /*Vertical*/
                private Motor.Encoder vertEnc; // Port -
            /*Horizontal*/
                private Motor.Encoder horzEnc; // Port -

    /*Servos*/
        /*Intake*/
            private Servo intClaw; // Port -
            private Servo intClawRotate; // Port -
            private Servo intClawPivot; // Port -
            private Servo intPivot; // Port -
        /*Outtake*/
            private Servo outClaw; // Port -
            private Servo outClawPivot; // Port -
            private Servo out4BarPivot; // Port -
            //TODO: change name once servo is figured out -ask Zac-
            private Servo outUnknown; // Port -

    /*Sensors*/
        Limelight3A limelight;
        ColorSensor colorSensor;
        IMU imu;


    public Robot(HardwareMap hardwareMap){
        this.hardwareMap = hardwareMap;
        initMotors();
        initServos();
        initOther();
    }
    private void initMotors(){
        FRM = hardwareMap.get(DcMotorEx.class,"FRM");
        FLM = hardwareMap.get(DcMotorEx.class,"FLM");
        BRM = hardwareMap.get(DcMotorEx.class,"BRM");
        BLM = hardwareMap.get(DcMotorEx.class,"BLM");

        vertLeft = hardwareMap.get(DcMotorEx.class, "vertLeft");
        vertRight = hardwareMap.get(DcMotorEx.class, "vertRight");
        vertEnc = new Motor(hardwareMap,"vertRight", Motor.GoBILDA.RPM_1150).encoder;

        horzLeft = hardwareMap.get(DcMotorEx.class, "horzLeft");
        horzRight = hardwareMap.get(DcMotorEx.class, "horzRight");
        horzEnc = new Motor(hardwareMap,"vertRight", Motor.GoBILDA.RPM_1150).encoder;
    }

    private void initServos(){
        intClaw = hardwareMap.get(Servo.class, "intClaw");
        intClawRotate = hardwareMap.get(Servo.class, "intClawRotate");
        intClawPivot = hardwareMap.get(Servo.class, "intClawPivot");
        intPivot = hardwareMap.get(Servo.class, "intPivot");

        outClaw = hardwareMap.get(Servo.class, "outClaw");
        out4BarPivot = hardwareMap.get(Servo.class, "out4BarPivot");
        outClawPivot = hardwareMap.get(Servo.class, "outClawPivot");
        outUnknown = hardwareMap.get(Servo.class, "outUnknown");

    }

    private void initOther(){
        allHubs = hardwareMap.getAll(LynxModule.class);
        for (LynxModule hub : allHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }
    }

    private void Periodic(){
        for (LynxModule hub : allHubs) {
            hub.clearBulkCache();
        }
    }
}

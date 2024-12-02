package org.firstinspires.ftc.teamcode.config.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.config.Math.PIDF;
import org.firstinspires.ftc.teamcode.config.Math.rotatingArmController;
import org.firstinspires.ftc.teamcode.config.RobotConstants;

@Config
public class armSubsystem extends SubsystemBase {
    private DcMotor rotate;
    private Motor.Encoder rotateEncoder;
    private PIDF controller;
    private double targetAngle =0;
    private double ticksPer90 = 2027, ticksPerDegree = ticksPer90/90, CurrentAngle = 0, EncoderCount = 0,rotatePow = 0, rotatePowPrev= 0, targetAngleEnc = 0;
    private double lKP = 0.03, lKI = 0, lKD = 0.001, lKF = .1, sKP = 0.027, sKI = 0, sKD = 0.0011, sKF = .1;

    private double holdingPow = .1, middlePoint = 125, armTolerence = 1;
    private RobotConstants.armPos targetPos = RobotConstants.armPos.ZERO;
    public armSubsystem(HardwareMap hardwareMap){
        rotate = hardwareMap.get(DcMotor.class,"armRotate");
        rotateEncoder = new Motor(hardwareMap,"armRotate", Motor.GoBILDA.RPM_117).encoder;
        rotateEncoder.reset();
        rotate.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rotateEncoder.setDirection(Motor.Direction.REVERSE);
        rotate.setDirection(DcMotorSimple.Direction.REVERSE);
        controller = new PIDF();
    }

    public void run(){
        targetAngleEnc = targetAngle * ticksPerDegree;
        try {
            EncoderCount = rotate.getCurrentPosition();
        }catch (Exception e){
            EncoderCount = 0;
        }
        CurrentAngle = EncoderCount;
        rotatePow = controller.run(targetAngleEnc,CurrentAngle);
        if (rotatePow != rotatePowPrev) {
            rotate.setPower(rotatePow);
        }
        rotatePowPrev = rotatePow;
    }

    public void setTargetAngle(RobotConstants.armPos pos){
        this.targetPos = pos;
        switch (targetPos){
            case ZERO:
                this.targetAngle = RobotConstants.zeroAngle;
                break;
            case SUB:
                this.targetAngle = RobotConstants.SUBAngle;
                break;
            case NEUTRAL:
                this.targetAngle = RobotConstants.neutralAngle;
                break;
            case WALL:
                this.targetAngle = RobotConstants.intakeSpecimen;
                break;
            case HIGH_CHAMBER_PLACE:
                this.targetAngle = RobotConstants.BarUpAngle;
                break;
            case HIGH_CHAMBER_RELEASE:
                this.targetAngle = RobotConstants.BarUpAngle;
                break;
            case HIGH_BASKET:
                this.targetAngle = RobotConstants.basketAngle;
                break;
        }
    }
    public boolean reachedAngle(){
        if (rotatePow == controller.hold(CurrentAngle/ticksPerDegree)){
            hold();
            return true;
        }
        return false;
    }
    public void hold(){
        double pow = controller.hold(CurrentAngle/ticksPerDegree);
        if(rotatePowPrev != pow){
            rotate.setPower(pow);
            rotatePowPrev = pow;
        }
    }
    public double getPos(){
        return rotate.getCurrentPosition()/ ticksPerDegree;
    }


}

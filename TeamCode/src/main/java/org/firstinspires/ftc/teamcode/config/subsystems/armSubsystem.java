package org.firstinspires.ftc.teamcode.config.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.config.Math.rotatingArmController;
import org.firstinspires.ftc.teamcode.config.RobotConstants;

@Config
public class armSubsystem extends SubsystemBase {
    private DcMotor rotate;
    private rotatingArmController controller;
    private double targetAngle =0;
    private double ticksPer90 = 1424, ticksPerDegree = ticksPer90/90, CurrentAngle = 0, EncoderCount = 0,rotatePow = 0, rotatePowPrev= 0, targetAngleEnc = 0;
    private double lKP = 0.03, lKI = 0, lKD = 0.001, lKF = .1, sKP = 0.027, sKI = 0, sKD = 0.0011, sKF = .1;

    private double holdingPow = .1, middlePoint = 125, armTolerence = 1;
    private RobotConstants.armPos targetPos = RobotConstants.armPos.ZERO;
    public armSubsystem(HardwareMap hardwareMap){
        rotate = hardwareMap.get(DcMotor.class,"armRotate");
        rotate.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rotate.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rotate.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rotate.setDirection(DcMotorSimple.Direction.REVERSE);
        controller = new rotatingArmController(lKP, lKI, lKD, lKF, sKP, sKI, sKD, sKF);
        controller.reset();
        controller.setMiddlePoint(middlePoint);
    }

    public void run(){
        targetAngleEnc = targetAngle * ticksPerDegree;
        try {
            EncoderCount = rotate.getCurrentPosition();
        }catch (Exception e){
            EncoderCount = 0;
        }
        CurrentAngle = EncoderCount;
        rotatePow = controller.run(CurrentAngle,targetAngleEnc);
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
        switch (targetPos){
            case ZERO:
                if(this.CurrentAngle/ticksPerDegree < RobotConstants.zeroAngle + armTolerence &&this.CurrentAngle/ticksPerDegree > RobotConstants.zeroAngle - armTolerence){
                    hold();
                    return true;
                }
                return false;
            case SUB:
                if(this.CurrentAngle/ticksPerDegree < RobotConstants.SUBAngle + armTolerence &&this.CurrentAngle/ticksPerDegree > RobotConstants.SUBAngle - armTolerence){
                    hold();
                    return true;
                }
                return false;
            case NEUTRAL:
                if(this.CurrentAngle/ticksPerDegree < RobotConstants.neutralAngle + armTolerence &&this.CurrentAngle/ticksPerDegree > RobotConstants.neutralAngle - armTolerence){
                    hold();
                    return true;
                }
                return false;
            case WALL:
                if(this.CurrentAngle/ticksPerDegree < RobotConstants.intakeSpecimen + armTolerence &&this.CurrentAngle/ticksPerDegree > RobotConstants.intakeSpecimen - armTolerence){
                    hold();
                    return true;
                }
                return false;
            case HIGH_CHAMBER_PLACE:
                if(this.CurrentAngle/ticksPerDegree < RobotConstants.BarUpAngle + armTolerence &&this.CurrentAngle/ticksPerDegree > RobotConstants.BarUpAngle - armTolerence){
                    hold();
                    return true;
                }
                return false;
            case HIGH_CHAMBER_RELEASE:
                if(this.CurrentAngle/ticksPerDegree < RobotConstants.BarUpAngle + armTolerence &&this.CurrentAngle/ticksPerDegree > RobotConstants.BarUpAngle - armTolerence){
                    hold();
                    return true;
                }
                return false;
            case HIGH_BASKET:
                if(this.CurrentAngle/ticksPerDegree < RobotConstants.basketAngle + armTolerence &&this.CurrentAngle/ticksPerDegree > RobotConstants.basketAngle - armTolerence){
                    hold();
                    return true;
                }
                return false;
        }
        return false;
    }
    public void hold(){
        double pos = rotate.getCurrentPosition()/ ticksPerDegree;
        pos = Math.signum(middlePoint - pos);
//        if(pos>0) {
//            rotatePow = holdingPow;
//        } else if (pos<0) {
//            rotatePow = -holdingPow;
//        }else{
//            rotatePow = 0;
//        }
        rotatePow = holdingPow * pos;
        if (rotatePow != rotatePowPrev) {
            rotate.setPower(rotatePow);
        }
        rotatePowPrev = rotatePow;
    }
    public double getPos(){
        return rotate.getCurrentPosition()/ ticksPerDegree;
    }


}

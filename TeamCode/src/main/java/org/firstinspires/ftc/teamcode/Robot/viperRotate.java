package org.firstinspires.ftc.teamcode.Robot;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Robot.CustomMath.PDFL;
@Config
public class viperRotate {
    private DcMotor rotate;
    private PDFL PDFLController;
    public static double
            kP = 0.01,//the value that actually corrects error
            kD = 0, //dampens the aggressiveness of P
            kFP = .3, //adjusts for gravity up
            kFN = -.3, //adjusts for gravity down
            kL = 0; // adjusts for friction
    public double kF = 0;//adjusted gravity val
    private double TargetAngle = 0, CurrentAngle = 0, Error = 0, EncoderCount = 0, rotatePow = 0, rotatePowPrev= 0;
    private double ticksPer90 = 1438, ticksPerDegree = ticksPer90/90;

    public viperRotate(DcMotor rotate){
        this.rotate = rotate;
        this.rotate.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.PDFLController = new PDFL(kP,kD,kF,kL);
    }
    public void updateConstants(){
        this.PDFLController.updateConstants(kP,kD,kF,kL);
    }

    public void setTargetAngle(double TargetAngle){ //in degrees
        this.TargetAngle = TargetAngle * ticksPerDegree;

    }
    public double getAngle(){
        return EncoderCount / ticksPerDegree;
    }

    public void periodic() {
        try {
            EncoderCount = rotate.getCurrentPosition();
        }catch (Exception e){
            EncoderCount = 0;
        }
        CurrentAngle = EncoderCount;
        if(CurrentAngle > (106 * ticksPerDegree)){
            kF = kFN;
        }else{
            kF = kFP;
        }
        updateConstants();
        Error = TargetAngle - CurrentAngle;
        rotatePow = PDFLController.run(Error);
        if (rotatePow != rotatePowPrev) {
            rotate.setPower(rotatePow);
            rotatePowPrev = rotatePow;
        }
    }

}

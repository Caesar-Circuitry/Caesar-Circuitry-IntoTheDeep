package org.firstinspires.ftc.teamcode.Robot;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Robot.CustomMath.PDFL;
@Config
public class viperRotate {
    private DcMotor rotate;
    private PDFL PDFLController;
    public static double
            kP = 0.001,//the value that actually corrects error
            kD = 0, //dampens the aggressiveness of P
            kF = 0.2, //adjusts for gravity up
            kFN = -0.2, //adjusts for gravity down
            kL = .3; // adjusts for friction
    private double TargetAngle = 0, CurrentAngle = 0, Error = 0, EncoderCount = 0, rotatePow = 0, rotatePowPrev= 0;
    private double ticksPer90 = 1692, ticksPerDegree = ticksPer90/90;

    public viperRotate(DcMotor rotate){
        this.rotate = rotate;
        this.PDFLController = new PDFL(kP,kD,kF,kL);
    }
    public void updateConstants(double KF){
        this.PDFLController.updateConstants(kP,kD,KF,kL);
    }

    public void setTargetAngle(double TargetAngle){ //in degrees
        this.TargetAngle = TargetAngle * ticksPerDegree;

    }
    public double getAngle(){
        return EncoderCount / ticksPerDegree;
    }

    public void periodic() {
        EncoderCount = rotate.getCurrentPosition();
        CurrentAngle = EncoderCount;
        if(CurrentAngle > (106 * ticksPerDegree)){
            updateConstants(kFN);
        }else{
            updateConstants(kF);
        }
        Error = TargetAngle - CurrentAngle;
        rotatePow = PDFLController.run(Error);
        if (rotatePow != rotatePowPrev) {
            rotate.setPower(rotatePow);
            rotatePowPrev = rotatePow;
        }
    }

}

package org.firstinspires.ftc.teamcode.Robot;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Robot.CustomMath.PDFL;
import org.firstinspires.ftc.teamcode.Robot.CustomMath.RotatingArmController;

@Config
public class viperRotate {
    private DcMotor rotate;
    private RotatingArmController PDFLController;
    public static double
            kP = 0.03,//the value that actually corrects error
            kD = 0, //dampens the aggressiveness of P
            kI = 0; // adjusts for always error
    private double TargetAngle = 0, CurrentAngle = 0, Error = 0, EncoderCount = 0, rotatePow = 0, rotatePowPrev= 0;
    private double ticksPer90 = -1498, ticksPerDegree = ticksPer90/90;

    public viperRotate(DcMotor rotate){
        this.rotate = rotate;
        this.rotate.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.PDFLController = new RotatingArmController(kP,kI,kD);
    }
    public void updateConstants(){
        this.PDFLController.updateConstants(kP,kI,kD);
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
        updateConstants();
        rotatePow = PDFLController.run(CurrentAngle,TargetAngle);
        if (rotatePow != rotatePowPrev) {
            rotate.setPower(rotatePow);
            rotatePowPrev = rotatePow;
        }
    }

}

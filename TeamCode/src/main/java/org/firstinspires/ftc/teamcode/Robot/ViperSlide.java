package org.firstinspires.ftc.teamcode.Robot;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Robot.CustomMath.PDFL;
@Config
public class ViperSlide {
    /*TODO add a bucket apriltag calculate viper Pos function*/
    private DcMotor Slide;
    private double ticksPerIn = 10.96;
    private PDFL PDFLController;
    public static double kP = 0, kD = 0, kF = 0, kL = 0;
    private double TargetPos = 0, CurrentPos = 0, Error = 0, EncoderPos = 0, slidePow = 0, slidePowPrev = 0;


    public ViperSlide(DcMotor Slide) {
        this.Slide = Slide;
        this.Slide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        PDFLController = new PDFL(kP, kD, kF, kL);
    }

    public void updateVariables() {
        PDFLController.updateConstants(kP, kD, kF, kL);
    }

    public void setTargetPos(double TargetPos) {
        this.TargetPos = TargetPos * ticksPerIn;

    }

    public void periodic() {
        updateVariables();
        try {
            EncoderPos = Slide.getCurrentPosition();
        } catch (Exception e) {
            EncoderPos = 0;
        }
        CurrentPos = EncoderPos;
        Error = TargetPos - CurrentPos;
        slidePow = PDFLController.run(Error);
        if (slidePow != slidePowPrev) {
            Slide.setPower(slidePow);
            slidePowPrev = slidePow;
        }
    }
}
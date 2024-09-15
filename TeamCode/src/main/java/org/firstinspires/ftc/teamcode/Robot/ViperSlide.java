package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Robot.CustomMath.PDFL;

public class ViperSlide {
    //Sample Viper slide code with PDFL and kalmanFilter
    /*TODO add a bucket apriltag calculate viper Pos function*/
    private DcMotor Slide;
    private double inPerTick = 0;
    private PDFL PDFLController;
    private GlobalVars vars;
    private double kP = 0,kD = 0,kF = 0,kL = 0;
    private double TargetPos = 0, CurrentPos = 0, Error = 0, EncoderPos = 0, slidePow = 0, slidePowPrev= 0;

    // Define the initial state, model covariance, sensor covariance, and initial covariance guess
    double x = 0; // Initial motor speed (from -1 to 1)
    double Q = 0.1; // Model covariance
    double R = 0.4; // Sensor covariance
    double p = 1; // Initial covariance guess

    // Define the initial Kalman gain guess
    double K = 1;

    // Store previous values
    double x_previous = x;
    double p_previous = p;

    // Store the motor speed measurement (from -1 to 1)
    double z = 0;

    // Store the target motor speed (from -1 to 1)
    double targetMotorSpeed = 0;

    public ViperSlide(DcMotor Slide, GlobalVars vars){
        this.Slide = Slide;
        this.Slide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        PDFLController = new PDFL(kP,kD,kF,kL);
        this.vars = vars;
    }
    public void setTargetPos(double TargetPos){
        this.TargetPos = TargetPos;

    }
    public void periodic(){
        EncoderPos = Slide.getCurrentPosition();
        CurrentPos = EncoderPos * inPerTick;
        Error = TargetPos - CurrentPos;
        slidePow = PDFLController.run(Error);
        updateKalmanFilter(Slide.getPower(), slidePow);
        if(slidePow != slidePowPrev) {
            Slide.setPower(slidePow);
            slidePowPrev = slidePow;
        }
    }
    public void updateKalmanFilter(double motorSpeedMeasurement, double targetMotorSpeedInput) {
        // Update the motor speed measurement
        z = motorSpeedMeasurement;

        // Predict the new motor speed
        x = x_previous;

        // Update the covariance
        p = p_previous + Q;

        // Calculate the Kalman gain
        K = p / (p + R);

        // Update the target motor speed
        targetMotorSpeed = targetMotorSpeedInput;

        // Update the motor speed estimate
        x = x + K * (z - x);

        // Update the covariance
        p = (1 - K) * p;

        // Store the previous values
        x_previous = x;
        p_previous = p;

        // Limit the motor speed estimate to the range [-1, 1]
        slidePow = Math.max(-1, Math.min(1, x));
    }

}

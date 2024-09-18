package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.robotcore.hardware.DcMotor;

public class driveTrain {
    DcMotor frontLeft,frontRight,backLeft,backRight;
    private double fLPowPrevious = 0,fRPowPrevious = 0,bLPowPrevious = 0,bRPowPrevious = 0;
    private double fl_power,bl_power,fr_power,br_power;
    public driveTrain(DcMotor fL,DcMotor fR,DcMotor bL,DcMotor bR){
        this.frontLeft = fL;
        this.frontRight = fR;
        this.backLeft = bL;
        this.backRight = bR;
        this.fl_power = 0;
        this.bl_power = 0;
        this.fr_power = 0;
        this.br_power = 0;
    }
    public void setDrivePowers(double x,double y,double turn){
        double theta = (float)Math.atan2(y,x);
        double power = (float)Math.hypot(x,y);

        double sin = Math.sin(theta -Math.PI/4);
        double cos = Math.cos(theta -Math.PI/4);
        double max = Math.max(Math.abs(sin), Math.abs(cos));

        fl_power = power * cos/max + turn;
        bl_power = power * sin/max + turn;
        fr_power = power * sin/max - turn;
        br_power = power * cos/max - turn;

        if((power + Math.abs(turn)) > 1) {
            fl_power /= power + turn;
            bl_power /= power + turn;
            fr_power /= power + turn;
            br_power /= power + turn;
        }
    }
    public void periodic(){
        if(fl_power != fLPowPrevious) {
            frontLeft.setPower(fl_power);
            fLPowPrevious = fl_power;
        }
        if(bl_power != bLPowPrevious) {
            backLeft.setPower(bl_power);
            bLPowPrevious = bl_power;
        }
        if(fr_power != fRPowPrevious) {
            frontRight.setPower(fr_power);
            fRPowPrevious = fr_power;
        }
        if(br_power != bRPowPrevious) {
            backRight.setPower(br_power);
            bRPowPrevious = br_power;
        }
    }

}

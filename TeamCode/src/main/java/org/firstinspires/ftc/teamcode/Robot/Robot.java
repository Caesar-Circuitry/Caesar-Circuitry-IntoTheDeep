package org.firstinspires.ftc.teamcode.Robot;

import com.acmerobotics.roadrunner.Pose2d;
import com.outoftheboxrobotics.photoncore.Photon;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;

public class Robot {
    public GlobalVars vars;
    public MecanumDrive drive;
    public ViperSlide viper;
    public Robot(HardwareMap hardwareMap){
        vars = new GlobalVars(new Pose2d(0,0,0));
        drive = new MecanumDrive(hardwareMap, vars.getRobotPosition());
        viper = new ViperSlide(hardwareMap, vars);

    }
}

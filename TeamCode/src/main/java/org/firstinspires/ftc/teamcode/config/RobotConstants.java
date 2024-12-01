package org.firstinspires.ftc.teamcode.config;

import com.acmerobotics.dashboard.config.Config;

@Config
public class RobotConstants {
    public static double
            zeroAngle = 0,
            neutralAngle = 25,
            SUBAngle = 13,
            intakeSample  = 255,
            intakeSpecimen = 45,
            basketAngle = 125,
            HangAngle = 150,
            BarUpAngle = 60,
            HANGDOWNANGLE = 360,
            clawOpen =.9,
            clawClosed = .62,
            clawWristPickup = .05,
            clawWristBucket = 1,
            clawWristSpecimen = 0.55,
            clawWristSUB= .05,
            clawWristIntSpecimen = .55,
            multiplier =1,
            viperbasket = 17,
            viperSub = 5,
            viperZero = .1,
            viperBar = 10.5;

    public enum armPos{
        ZERO,
        SUB,
        NEUTRAL,
        WALL,
        HIGH_CHAMBER_PLACE,
        HIGH_CHAMBER_RELEASE,
        HIGH_BASKET
    }
    public static armPos prevArmPos = armPos.ZERO;
    public enum OpModeType
    {
        AUTO,
        TELEOP
    }
}

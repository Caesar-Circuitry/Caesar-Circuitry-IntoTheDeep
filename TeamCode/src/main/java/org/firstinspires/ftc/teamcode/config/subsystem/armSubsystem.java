package org.firstinspires.ftc.teamcode.config.subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class armSubsystem {
    private DcMotor armMoter, viperMoter;

    public armSubsystem(HardwareMap hardwareMap){
        //TODO: change names as needed
        armMoter = hardwareMap.get(DcMotor.class,"viperRotate");
        viperMoter = hardwareMap.get(DcMotor.class,"viper");
    }

}

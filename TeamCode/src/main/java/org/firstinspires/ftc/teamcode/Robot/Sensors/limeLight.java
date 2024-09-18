package org.firstinspires.ftc.teamcode.Robot.Sensors;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class limeLight {
    Limelight3A limeLight;
    public limeLight(HardwareMap hardwareMap){
        limeLight = hardwareMap.get(Limelight3A.class,"LimeLight");
    }
}

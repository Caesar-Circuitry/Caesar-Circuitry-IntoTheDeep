package org.firstinspires.ftc.teamcode.config.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.config.RobotConstants;


public class clawSubsystem extends SubsystemBase {
    private Servo claw;

    public clawSubsystem(HardwareMap hardwareMap) {
        claw = hardwareMap.get(Servo.class, "claw");
    }
    //------------------------------Close Claw------------------------------//

    public void grab() {
        claw.setPosition(RobotConstants.clawClosed);
    }

    //------------------------------Open Claw------------------------------//
    public void release() {
        claw.setPosition(RobotConstants.clawOpen);
    }
}
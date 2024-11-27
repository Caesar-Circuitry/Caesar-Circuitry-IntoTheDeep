package org.firstinspires.ftc.teamcode.config.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.config.RobotConstants;

public class clawWristSubsystem extends SubsystemBase {
    private Servo clawWrist;

    public clawWristSubsystem(HardwareMap hardwareMap) {
        clawWrist = hardwareMap.get(Servo.class, "clawWrist");
    }

    public void setTargetAngle(RobotConstants.armPos pos) {
        switch (pos) {
            case ZERO:
                this.clawWrist.setPosition(RobotConstants.clawWristPickup);
                break;
            case SUB:
                this.clawWrist.setPosition(RobotConstants.clawWristSUB);
                break;
            case NEUTRAL:
                this.clawWrist.setPosition(RobotConstants.clawWristPickup);
                break;
            case WALL:
                this.clawWrist.setPosition(RobotConstants.clawWristIntSpecimen);
                break;
            case HIGH_CHAMBER_PLACE:
                this.clawWrist.setPosition(RobotConstants.clawWristSpecimen);
                break;
            case HIGH_CHAMBER_RELEASE:
                this.clawWrist.setPosition(RobotConstants.clawWristSpecimen);
                break;
            case HIGH_BASKET:
                this.clawWrist.setPosition(RobotConstants.clawWristBucket);
                break;
        }
    }
}

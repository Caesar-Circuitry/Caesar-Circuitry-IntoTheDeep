package org.firstinspires.ftc.teamcode.config.subsystem;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.config.RobotConstants;

/** This is a subsystem, for the claw of our robot
 * Here we make methods to manipulate the servos
 * We also import RobotConstants to get the positions of the servos.
 *
 * @author Baron Henderson - 20077 The Indubitables
 * @version 2.0, 9/8/2024
 */

public class ClawSubsystem {
    private Servo claw, clawWrist;

    /** This is the constructor for the subsystem, it maps the servos to the hardwareMap.
     * The device names should align with the configuration names on the driver hub.
     * To use this subsystem, we have to import this file, declare the subsystem (private ClawSubsystem claw;),
     * and then call the below constructor in the init() method. */

    public ClawSubsystem(HardwareMap hardwareMap) {
        claw = hardwareMap.get(Servo.class, "claw");
        clawWrist = hardwareMap.get(Servo.class, "clawWrist");
    }
    //------------------------------Close Claws------------------------------//

    public void closeClaw() {
        claw.setPosition(RobotConstants.clawClosed);
    }

    //------------------------------Open Claws------------------------------//
    public void openClaw() {
        claw.setPosition(RobotConstants.clawOpen);
    }

    //------------------------------Claw Rotate------------------------------//

    public void clawWristBasket() {
        clawWrist.setPosition(RobotConstants.clawWristBucket);
    }

    public void clawWristSpecimen() {
        clawWrist.setPosition(RobotConstants.clawWristSpecimen);
    }

    public void clawWristSub() {
        clawWrist.setPosition(RobotConstants.clawWristSUB);
    }

}
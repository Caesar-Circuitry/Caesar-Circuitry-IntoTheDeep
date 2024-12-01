package org.firstinspires.ftc.teamcode.config.Commands.CommandGroups.armGroup;

import static org.firstinspires.ftc.teamcode.config.RobotConstants.prevArmPos;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.ClawWristCommands.clawWristHighChamberRelease;
import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.armCommands.armHighChamberRelease;
import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.clawCommands.clawRelease;
import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.viperCommands.viperHighChamberRelease;
import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.viperCommands.viperZero;
import org.firstinspires.ftc.teamcode.config.RobotConstants;
import org.firstinspires.ftc.teamcode.config.subsystems.armSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystems.clawSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystems.clawWristSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystems.viperSubsystem;

public class armHighChamberReleaseGroup extends SequentialCommandGroup {

    public armHighChamberReleaseGroup(clawWristSubsystem clawWrist, armSubsystem arm, viperSubsystem viper, clawSubsystem claw){
        if(prevArmPos == RobotConstants.armPos.HIGH_BASKET){
            addCommands(
                    new SequentialCommandGroup(
                            new ParallelCommandGroup(
                                    new clawWristHighChamberRelease(clawWrist),
                                    new viperZero(viper)
                            ),
                            new armHighChamberRelease(arm),
                            new viperHighChamberRelease(viper),
                            new clawRelease(claw)
                    )
            );
        }else{
            addCommands(
                    new SequentialCommandGroup(
                            new armHighChamberRelease(arm),
                            new ParallelCommandGroup(
                                    new clawWristHighChamberRelease(clawWrist),
                                    new viperHighChamberRelease(viper)
                            ),
                            new clawRelease(claw)
                    )
            );
        }
        prevArmPos = RobotConstants.armPos.HIGH_CHAMBER_RELEASE;
        addRequirements(clawWrist,arm,viper,claw);
    }

}
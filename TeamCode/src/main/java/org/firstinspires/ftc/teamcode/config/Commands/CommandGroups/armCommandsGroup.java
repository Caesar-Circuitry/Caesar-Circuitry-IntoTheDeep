package org.firstinspires.ftc.teamcode.config.Commands.CommandGroups;

import static org.firstinspires.ftc.teamcode.config.RobotConstants.*;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.ClawWristCommands.clawWristZero;
import org.firstinspires.ftc.teamcode.config.subsystems.ClawSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystems.armSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystems.clawWristSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystems.viperSubsystem;


public class armCommandsGroup {
    //TODO: combine claw wrist, claw, viper and arm Commands into command Groups
    public class armZero extends SequentialCommandGroup{

        public armZero(ClawSubsystem claw, clawWristSubsystem clawWrist, armSubsystem arm, viperSubsystem viper){
            switch (prevArmPos){
                case ZERO:
                    break;
                case NEUTRAL:
                    addCommands(
                            new clawWristZero(clawWrist)
                    );
                case SUB:
                case WALL:
                case HIGH_BASKET:
                case HIGH_CHAMBER_PLACE:
                case HIGH_CHAMBER_RELEASE:
            }
            addRequirements(claw,clawWrist,arm,viper);
        }
    }
}

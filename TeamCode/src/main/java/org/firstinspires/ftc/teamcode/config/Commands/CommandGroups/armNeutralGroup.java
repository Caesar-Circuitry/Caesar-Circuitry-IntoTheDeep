package org.firstinspires.ftc.teamcode.config.Commands.CommandGroups;

import static org.firstinspires.ftc.teamcode.config.RobotConstants.prevArmPos;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.ClawWristCommands.clawWristNeutral;
import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.armCommands.armNeutral;
import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.viperCommands.viperNeutral;
import org.firstinspires.ftc.teamcode.config.RobotConstants;
import org.firstinspires.ftc.teamcode.config.subsystems.armSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystems.clawWristSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystems.viperSubsystem;

public class armNeutralGroup extends SequentialCommandGroup {

        public armNeutralGroup(clawWristSubsystem clawWrist, armSubsystem arm, viperSubsystem viper){
            if(prevArmPos == RobotConstants.armPos.ZERO || prevArmPos == RobotConstants.armPos.SUB){
                addCommands(
                        new SequentialCommandGroup(
                                new armNeutral(arm),
                                new ParallelCommandGroup(
                                        new clawWristNeutral(clawWrist),
                                        new viperNeutral(viper)
                                )
                        )
                );
            }else{
                addCommands(
                        new SequentialCommandGroup(
                                new ParallelCommandGroup(
                                        new clawWristNeutral(clawWrist),
                                        new viperNeutral(viper)
                                ),
                                new armNeutral(arm)
                        )
                );
            }
            prevArmPos = RobotConstants.armPos.NEUTRAL;
            addRequirements(clawWrist,arm,viper);
        }
    }

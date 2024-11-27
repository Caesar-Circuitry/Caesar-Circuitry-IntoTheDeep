package org.firstinspires.ftc.teamcode.config.Commands.CommandGroups;

import static org.firstinspires.ftc.teamcode.config.RobotConstants.prevArmPos;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.ClawWristCommands.clawWristSub;
import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.armCommands.armSub;
import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.viperCommands.viperSub;
import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.viperCommands.viperZero;
import org.firstinspires.ftc.teamcode.config.RobotConstants;
import org.firstinspires.ftc.teamcode.config.subsystems.armSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystems.clawWristSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystems.viperSubsystem;

public class armSubGroup extends SequentialCommandGroup {

        public armSubGroup(clawWristSubsystem clawWrist, armSubsystem arm, viperSubsystem viper){
            if (prevArmPos == RobotConstants.armPos.ZERO){
                addCommands(
                        new SequentialCommandGroup(
                                new armSub(arm),
                                new ParallelCommandGroup(
                                        new clawWristSub(clawWrist),
                                        new viperSub(viper)
                                )
                        )
                );
            }else{
                addCommands(
                        new SequentialCommandGroup(
                                new ParallelCommandGroup(
                                        new clawWristSub(clawWrist),
                                        new viperZero(viper)
                                ),
                                new armSub(arm),
                                new viperSub(viper)
                        )
                );
            }
            prevArmPos = RobotConstants.armPos.SUB;
            addRequirements(clawWrist,arm,viper);
        }
    }

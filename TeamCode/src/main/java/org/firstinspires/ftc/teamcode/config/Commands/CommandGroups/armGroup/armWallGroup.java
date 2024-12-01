package org.firstinspires.ftc.teamcode.config.Commands.CommandGroups.armGroup;

import static org.firstinspires.ftc.teamcode.config.RobotConstants.prevArmPos;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.ClawWristCommands.clawWristWall;
import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.armCommands.armWall;
import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.viperCommands.viperWall;
import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.viperCommands.viperZero;
import org.firstinspires.ftc.teamcode.config.RobotConstants;
import org.firstinspires.ftc.teamcode.config.subsystems.armSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystems.clawWristSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystems.viperSubsystem;

public class armWallGroup extends SequentialCommandGroup {

        public armWallGroup(clawWristSubsystem clawWrist, armSubsystem arm, viperSubsystem viper){
            if(prevArmPos == RobotConstants.armPos.ZERO|| prevArmPos == RobotConstants.armPos.SUB|| prevArmPos == RobotConstants.armPos.NEUTRAL){
                addCommands(
                        new SequentialCommandGroup(
                                new armWall(arm),
                                new ParallelCommandGroup(
                                        new clawWristWall(clawWrist),
                                        new viperWall(viper)
                                )
                        )
                );
            }else{
                addCommands(
                        new SequentialCommandGroup(
                                new ParallelCommandGroup(
                                        new clawWristWall(clawWrist),
                                        new viperZero(viper)
                                ),
                        new armWall(arm),
                        new viperWall(viper)
                    )
                );
            }
            prevArmPos = RobotConstants.armPos.WALL;
            addRequirements(clawWrist,arm,viper);
        }

    }

package org.firstinspires.ftc.teamcode.config.Commands.CommandGroups.armGroup;

import static org.firstinspires.ftc.teamcode.config.RobotConstants.prevArmPos;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.ClawWristCommands.clawWristHighChamberPlace;
import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.armCommands.armHighChamberPlace;
import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.viperCommands.viperHighChamberPlace;
import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.viperCommands.viperZero;
import org.firstinspires.ftc.teamcode.config.RobotConstants;
import org.firstinspires.ftc.teamcode.config.subsystems.armSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystems.clawWristSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystems.viperSubsystem;

public class armHighChamberPlaceGroup extends SequentialCommandGroup {

        public armHighChamberPlaceGroup(clawWristSubsystem clawWrist, armSubsystem arm, viperSubsystem viper){
            if(prevArmPos == RobotConstants.armPos.HIGH_CHAMBER_RELEASE || prevArmPos == RobotConstants.armPos.HIGH_BASKET){
                addCommands(
                    new SequentialCommandGroup(
                            new ParallelCommandGroup(
                                    new clawWristHighChamberPlace(clawWrist),
                                    new viperZero(viper)
                            ),
                            new armHighChamberPlace(arm),
                            new viperHighChamberPlace(viper)
                    )
                );
            }else{
                addCommands(
                        new SequentialCommandGroup(
                                new armHighChamberPlace(arm),
                                new ParallelCommandGroup(
                                        new clawWristHighChamberPlace(clawWrist),
                                        new viperHighChamberPlace(viper)
                                )
                        )
                );
            }
            prevArmPos = RobotConstants.armPos.HIGH_CHAMBER_PLACE;
            addRequirements(clawWrist,arm,viper);
        }
    }

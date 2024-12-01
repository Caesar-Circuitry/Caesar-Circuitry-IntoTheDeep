package org.firstinspires.ftc.teamcode.config.Commands.CommandGroups.armGroup;

import static org.firstinspires.ftc.teamcode.config.RobotConstants.*;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.ClawWristCommands.clawWristZero;
import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.armCommands.armZero;
import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.viperCommands.viperZero;
import org.firstinspires.ftc.teamcode.config.subsystems.armSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystems.clawWristSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystems.viperSubsystem;



    public class armZeroGroup extends SequentialCommandGroup{

        public armZeroGroup(clawWristSubsystem clawWrist, armSubsystem arm, viperSubsystem viper){
            addCommands(
                    new SequentialCommandGroup(
                            new ParallelCommandGroup(
                                    new clawWristZero(clawWrist),
                                    new viperZero(viper)
                            ),
                            new armZero(arm)
                    )
            );
            prevArmPos = armPos.ZERO;
            addRequirements(clawWrist,arm,viper);
        }
    }


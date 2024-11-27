package org.firstinspires.ftc.teamcode.config.Commands.CommandGroups;

import static org.firstinspires.ftc.teamcode.config.RobotConstants.*;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.ClawWristCommands.clawWristHighBasket;
import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.ClawWristCommands.clawWristHighChamberPlace;
import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.ClawWristCommands.clawWristHighChamberRelease;
import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.ClawWristCommands.clawWristNeutral;
import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.ClawWristCommands.clawWristSub;
import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.ClawWristCommands.clawWristWall;
import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.ClawWristCommands.clawWristZero;
import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.armCommands.armHighBasket;
import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.armCommands.armHighChamberPlace;
import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.armCommands.armHighChamberRelease;
import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.armCommands.armNeutral;
import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.armCommands.armSub;
import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.armCommands.armWall;
import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.armCommands.armZero;
import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.clawCommands.clawRelease;
import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.viperCommands.viperHighBasket;
import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.viperCommands.viperHighChamberPlace;
import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.viperCommands.viperHighChamberRelease;
import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.viperCommands.viperNeutral;
import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.viperCommands.viperSub;
import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.viperCommands.viperWall;
import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.viperCommands.viperZero;
import org.firstinspires.ftc.teamcode.config.subsystems.armSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystems.clawSubsystem;
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


package org.firstinspires.ftc.teamcode.config.Commands.CommandGroups.armGroup;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.ClawWristCommands.clawWristHighBasket;
import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.armCommands.armHighBasket;
import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.viperCommands.viperHighBasket;
import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.viperCommands.viperZero;
import org.firstinspires.ftc.teamcode.config.RobotConstants;
import org.firstinspires.ftc.teamcode.config.subsystems.armSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystems.clawWristSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystems.viperSubsystem;
import static org.firstinspires.ftc.teamcode.config.RobotConstants.prevArmPos;

public class armHighBasketGroup extends SequentialCommandGroup {

        public armHighBasketGroup(clawWristSubsystem clawWrist, armSubsystem arm, viperSubsystem viper){
            addCommands(
                    new SequentialCommandGroup(
                            new viperZero(viper),
                            new armHighBasket(arm),
                            new viperHighBasket(viper),
                            new clawWristHighBasket(clawWrist)
                    )
            );
            prevArmPos = RobotConstants.armPos.HIGH_BASKET;
            addRequirements(clawWrist,arm,viper);
        }

    }

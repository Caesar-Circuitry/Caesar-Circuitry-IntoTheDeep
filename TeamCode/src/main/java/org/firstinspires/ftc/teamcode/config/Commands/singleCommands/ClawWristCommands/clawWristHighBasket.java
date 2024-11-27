package org.firstinspires.ftc.teamcode.config.Commands.singleCommands.ClawWristCommands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.config.RobotConstants;
import org.firstinspires.ftc.teamcode.config.subsystems.clawWristSubsystem;

public class clawWristHighBasket extends CommandBase {

        private final clawWristSubsystem subsystem;

        public clawWristHighBasket(clawWristSubsystem subsystem){
            this.subsystem = subsystem;
            addRequirements(subsystem);
        }

        @Override
        public void initialize() {
            subsystem.setTargetAngle(RobotConstants.armPos.HIGH_BASKET);
        }

        @Override
        public boolean isFinished() {
            return true;
        }
    }


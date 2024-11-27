package org.firstinspires.ftc.teamcode.config.Commands.singleCommands.ClawWristCommands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.config.RobotConstants;
import org.firstinspires.ftc.teamcode.config.subsystems.clawWristSubsystem;

public class clawWristNeutral extends CommandBase {

        private final clawWristSubsystem subsystem;

        public clawWristNeutral(clawWristSubsystem subsystem){
            this.subsystem = subsystem;
            addRequirements(subsystem);
        }

        @Override
        public void initialize() {
            subsystem.setTargetAngle(RobotConstants.armPos.NEUTRAL);
        }

        @Override
        public boolean isFinished() {
            return true;
        }
    }

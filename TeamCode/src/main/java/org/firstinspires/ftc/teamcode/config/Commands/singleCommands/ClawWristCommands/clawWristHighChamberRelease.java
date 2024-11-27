package org.firstinspires.ftc.teamcode.config.Commands.singleCommands.ClawWristCommands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.config.RobotConstants;
import org.firstinspires.ftc.teamcode.config.subsystems.clawWristSubsystem;

public class clawWristHighChamberRelease extends CommandBase {

        private final clawWristSubsystem subsystem;

        public clawWristHighChamberRelease(clawWristSubsystem subsystem){
            this.subsystem = subsystem;
            addRequirements(subsystem);
        }

        @Override
        public void initialize() {
            subsystem.setTargetAngle(RobotConstants.armPos.HIGH_CHAMBER_RELEASE);
        }

        @Override
        public boolean isFinished() {
            return true;
        }
    }

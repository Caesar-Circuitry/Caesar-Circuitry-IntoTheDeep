package org.firstinspires.ftc.teamcode.config.Commands.singleCommands.ClawWristCommands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.config.RobotConstants;
import org.firstinspires.ftc.teamcode.config.subsystems.clawWristSubsystem;

public class clawWristSub extends CommandBase {

        private final clawWristSubsystem subsystem;

        public clawWristSub(clawWristSubsystem subsystem){
            this.subsystem = subsystem;
            addRequirements(subsystem);
        }

        @Override
        public void initialize() {
            subsystem.setTargetAngle(RobotConstants.armPos.SUB);
        }

        @Override
        public boolean isFinished() {
            return true;
        }
    }

package org.firstinspires.ftc.teamcode.config.Commands.singleCommands.armCommands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.config.RobotConstants;
import org.firstinspires.ftc.teamcode.config.subsystems.armSubsystem;

public class armHighChamberRelease extends CommandBase {
        armSubsystem subsystem;
        public armHighChamberRelease(armSubsystem subsystem){
            this.subsystem = subsystem;
            addRequirements(subsystem);
        }

        @Override
        public void initialize(){
            subsystem.setTargetAngle(RobotConstants.armPos.HIGH_CHAMBER_RELEASE);
        }
        @Override
        public void execute(){
            subsystem.run();
        }
        @Override
        public boolean isFinished(){
            return subsystem.reachedAngle();
        }
        @Override
        public void end(boolean interrupted){
            subsystem.hold();
        }
    }
